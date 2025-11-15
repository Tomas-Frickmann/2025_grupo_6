package test_GUI;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Component;

import controlador.Controlador;
import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.SinViajesException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import modeloDatos.Administrador;
import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import vista.*;
import modeloNegocio.Empresa;

public class TestCliente {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	String usuario = "Usuario1";
	String pass = "123456";
	String nombreReal = "NombreReal";
	ChoferPermanente choferPermanente;
	Auto auto;
	Empresa empresa = Empresa.getInstance();
	Pedido pedido;
	
	public TestCliente(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}

	@Before
	public void setUp() throws Exception {
		
		
		controlador = new Controlador();
		controlador.getVista().setOptionPane(op);
		
		this.buildEscenario();
	}

	
	@After
	public void tearDown() throws Exception {
		this.empresa.getClientes().clear();
		this.empresa.getChoferes().clear();
		this.empresa.getVehiculos().clear();
	}
	
	//Escenario derivado del 3
	public void buildEscenario() {
		try {
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
			this.choferPermanente = new ChoferPermanente("11111111","nombreRealChofer1",2020,4);
			this.empresa.agregarChofer(this.choferPermanente);
			this.auto = new Auto("AAA111",4,false);
			this.empresa.agregarVehiculo(this.auto);
			this.pedido = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedido);
			
			this.empresa.crearViaje(this.pedido, choferPermanente, auto);
		}
		catch(Exception e) {
			System.out.println("Problemas en el escenario" + e.getMessage());
		}
	}
	
	public void logeaVentana(String user,String password) {
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto(user, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto(password, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		
		robot.delay(this.delay);
		
	}
	
	@Test
	public void testCerrarSesionHab() {
		this.logeaVentana(this.usuario, this.pass);
		JButton closeButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
		assertTrue("El boton debe estar habilitado",closeButton.isEnabled());
	}
	
	@Test 
	public void testNombreVentana() {
		this.logeaVentana(this.usuario, this.pass);
		JPanel cliente = (JPanel) TestUtil.getComponentForName((Ventana) controlador.getVista(), Constantes.PANEL_CLIENTE);
		TitledBorder border = (TitledBorder) cliente.getBorder();
		assertEquals("El título debe ser el nombre del cliente",
				this.nombreReal,border.getTitle());
	}
	
	@Test
	public void testVuelveLogin() {
		
		this.logeaVentana(this.usuario, this.pass);
		robot.delay(this.delay);
		JButton closeButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
		
		TestUtil.clickComponent(closeButton, robot);
		robot.delay(this.delay);
		
		JPanel loginPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
		
		assertTrue("La ventana final no es la correcta",loginPane != null && loginPane.isVisible());	
	}
	
	//POVA = Pedido_O_Viaje_Actual
	@Test
	public void testPOVA_vacio() {
		this.logeaVentana(this.usuario, this.pass);
		//POVA debe estar vacio y todas las componentes de nuevo pedido tienen que estar habilitadas
		

		JTextArea POVA = (JTextArea) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertTrue("JTextArea debe estar vacio",POVA.getText().isEmpty());
		
		
		
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		JTextField costoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		assertTrue("Campo calificacion debe estar deshabilitado y el campo costo vacio",!califText.isEnabled() && costoText.getText().isEmpty());
		
		
		//panel nuevo pedido
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		assertTrue("Campo cantidad de pasajeros y cantidad de kilometros deben estar habilitados", paxText.isEnabled() && kmText.isEnabled());
		
		JRadioButton standRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
		JRadioButton asfRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		JRadioButton pelRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
		assertTrue("Los RadioButton deben estar habilitados", standRadio.isEnabled() && asfRadio.isEnabled() && pelRadio.isEnabled());
		
		JCheckBox baulCheck = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		JCheckBox mascotaCheck = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		assertTrue("Las CheckBox deben estar habilitados", baulCheck.isEnabled() && mascotaCheck.isEnabled());
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar deshabilitado",!nuevButton.isEnabled());
		
		
	}
	
	@Test
	public void testPedidoExiste() throws Exception{
		this.empresa.agregarPedido(this.pedido);
		
		this.logeaVentana(this.usuario, this.pass);
		
		JTextArea POVA = (JTextArea) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertTrue("JTextArea no debe estar vacio",!POVA.getText().isEmpty());
		
		
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		JTextField costoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		assertTrue("Campo calificacion debe estar deshabilitado y el campo costo vacio",!califText.isEnabled() && costoText.getText().isEmpty());
		
		
		//Panel nuevo pedido debe estar todo deshabilitado
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		assertTrue("Campo cantidad de pasajeros y cantidad de kilometros no deben estar habilitados", !paxText.isEnabled() && !kmText.isEnabled());
		
		JRadioButton standRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
		JRadioButton asfRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		JRadioButton pelRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
		assertTrue("Los RadioButton deben no estar habilitados", !standRadio.isEnabled() && !asfRadio.isEnabled() && !pelRadio.isEnabled());
		
		JCheckBox baulCheck = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		JCheckBox mascotaCheck = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		assertTrue("Las CheckBox no deben estar habilitados", !baulCheck.isEnabled() && !mascotaCheck.isEnabled());
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar deshabilitado",!nuevButton.isEnabled());
		
	}
	
	@Test
	public void testViajeExiste() throws Exception {
		this.empresa.agregarPedido(this.pedido);
		this.empresa.crearViaje(this.pedido, this.choferPermanente, this.auto);
		this.logeaVentana(this.usuario, this.pass);
		
		
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		JTextField costoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		assertTrue("Campo calificacion debe estar habilitado y el campo costo distinto de vacio",califText.isEnabled() && !costoText.getText().isEmpty());
		
		//Panel nuevo pedido
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		assertTrue("Campo cantidad de pasajeros y cantidad de kilometros no deben estar habilitados", !paxText.isEnabled() && !kmText.isEnabled());
		
		JRadioButton standRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
		JRadioButton asfRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		JRadioButton pelRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
		assertTrue("Los RadioButton deben no estar habilitados", !standRadio.isEnabled() && !asfRadio.isEnabled() && !pelRadio.isEnabled());
		
		JCheckBox baulCheck = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		JCheckBox mascotaCheck = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		assertTrue("Las CheckBox no deben estar habilitados", !baulCheck.isEnabled() && !mascotaCheck.isEnabled());
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar deshabilitado",!nuevButton.isEnabled());
		
		
		
	}
	
	@Test
	public void testRealizaPedido_defecto_habilitado() throws Exception {
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("22", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar habilitado",nuevButton.isEnabled());
		
		
	}
	
	@Test
	public void testRealizaPedido_paxZero() throws Exception {
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("0", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("22", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar deshabilitado",!nuevButton.isEnabled());
		
		
	}
	
	@Test
	public void testRealizaPedido_kmNeg() throws Exception {
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("-22", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar deshabilitado",!nuevButton.isEnabled());
		
		
	}

	@Test
	public void testRealizaPedido_kmZero() throws Exception {
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("0", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar habilitado",nuevButton.isEnabled());
		
		
	}
	
	@Test
	public void testRealizaPedido_defecto_fallido() throws Exception {
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("8", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("50", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		TestUtil.clickComponent(nuevButton, robot);
		robot.delay(this.delay);
		assertTrue(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),op.getMensaje().equals(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor()));
		
		
	}
	
	@Test
	public void testRealizaViaje() throws Exception {
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("20", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		TestUtil.clickComponent(nuevButton,robot);
		robot.delay(this.delay);
		
		assertTrue("Campo cantidad de pasajeros y cantidad de kilometros deben estar vacios", paxText.getText().isEmpty() && kmText.getText().isEmpty());
		
		
		
		Cliente cliente = this.empresa.getClientes().get(this.usuario);
		
		//Es necesario que salga de la ventana cliente para que haga algun cambio en la disponibilidad de los JText
		JButton cerrarButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
		TestUtil.clickComponent(cerrarButton, robot);
		
		this.empresa.crearViaje(this.empresa.getPedidoDeCliente(cliente), this.choferPermanente, this.auto); //La documentación no aclara pero asumo que de ser posible, casi instantaneamente, se crea el viaje.
		
		
		this.logeaVentana(this.usuario, this.pass);
		
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		JTextField costoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		
		robot.delay(this.delay);
		assertTrue("Campo calificacion debe estar habilitado y el campo costo distinto de vacio",califText.isEnabled() && !costoText.getText().isEmpty());


	}
	
	@Test
	public void testPagar_Habilitado() throws Exception{

		this.empresa.agregarPedido(this.pedido);
		this.empresa.crearViaje(this.pedido, this.choferPermanente, this.auto);
		this.logeaVentana(this.usuario, this.pass);
		
		robot.delay(this.delay);
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		
		TestUtil.clickComponent(califText, robot);
		TestUtil.tipeaTexto("1", robot);
		
		JButton pagarButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		robot.delay(this.delay);
		
		assertTrue("Boton de pagar deberia estar habilitado", pagarButton.isEnabled());
	}
	
	@Test
	public void testPagar_Desabilitado() throws Exception{
		this.empresa.agregarPedido(this.pedido);
		this.empresa.crearViaje(this.pedido, this.choferPermanente, this.auto);
		this.logeaVentana(this.usuario, this.pass);
		
		robot.delay(this.delay);
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		
		TestUtil.clickComponent(califText, robot);
		TestUtil.tipeaTexto("6", robot);
		
		JButton pagarButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		robot.delay(this.delay);
		
		assertTrue("Boton de pagar deberia estar deshabilitado", !pagarButton.isEnabled());
	}

	
	@Test
	public void testPagoExitoso() {
		this.logeaVentana(this.usuario, this.pass);
		
		
		robot.delay(this.delay);
		JTextArea pedidoArea = (JTextArea) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		JList historArea = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_CLIENTE);
		
		ListModel historAntes = historArea.getModel();
		String pedidoAntes = pedidoArea.getText();
		
		robot.delay(this.delay);
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		
		System.out.println(pedidoArea.getText());
		
		
		TestUtil.clickComponent(califText, robot);
		TestUtil.tipeaTexto("1", robot);
		
		JButton pagarButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		
		robot.delay(this.delay);
		TestUtil.clickComponent(pagarButton, robot);
		
		
		robot.delay(this.delay);
		
		assertTrue("El pago no se realizo con exitos",pedidoArea.getText().isEmpty());
		
		
	}
	@Test
	public void testActualizacionListas() throws Exception {

		this.empresa.login(usuario, pass);
		this.empresa.pagarYFinalizarViaje(5);
		this.empresa.logout();
		
		

		this.pedido = new Pedido(this.empresa.getClientes().get("Usuario1"), 3, false, false, 1, Constantes.ZONA_STANDARD);
		this.empresa.agregarPedido(this.pedido);
		this.empresa.crearViaje(this.pedido, this.choferPermanente, this.auto);
		
		
		this.logeaVentana(this.usuario, this.pass);
		
		
		robot.delay(this.delay);
		JTextArea pedidoArea = (JTextArea) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		JList historArea = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_CLIENTE);
		
		ListModel historAntes = historArea.getModel();
		String pedidoAntes = pedidoArea.getText();
		
		robot.delay(this.delay);
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		
		
		
		TestUtil.clickComponent(califText, robot);
		TestUtil.tipeaTexto("1", robot);
		
		JButton pagarButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		
		robot.delay(this.delay);
		TestUtil.clickComponent(pagarButton, robot);
		
		
		robot.delay(this.delay);
		
		Object texto = historArea.getModel().getElementAt(0);
		
		
		robot.delay(this.delay);
		
		assertTrue("El text del area de pedidos y viajes debe ser distinto", pedidoAntes != pedidoArea.getText());
		assertTrue("La lista de historico debe ser distinta", historAntes.toString() != historArea.getModel().toString());
		assertTrue("El pedido no es el mismo", verificarLista(this.empresa.getHistorialViajeCliente(this.empresa.getClientes().get(this.usuario)),historArea));
		
	}
	
	public boolean verificarLista(ArrayList<Viaje> viajesCorrectos, JList<Viaje> historArea) {

	    // 1. Obtener el modelo de la JList
	    ListModel<Viaje> model = historArea.getModel();

	    // 2. Comparar tamaños (la verificación más rápida)
	    if (model.getSize() != viajesCorrectos.size()) {
	        System.out.println("Error de verificación: El número de viajes no coincide.");
	        System.out.println("Modelo tiene: " + model.getSize() + ", Lista correcta tiene: " + viajesCorrectos.size());
	        return false; // No son iguales
	    }

	    // 3. Comparar elemento por elemento
	    for (int i = 0; i < model.getSize(); i++) {

	        Viaje viajeEnLista = model.getElementAt(i);
	        Viaje viajeCorrecto = viajesCorrectos.get(i);

	        // 4. ¡AQUÍ ESTÁ LA CLAVE!
	        // Debes tener implementado el método .equals() en tu clase "Viaje"
	        if (!viajeEnLista.equals(viajeCorrecto)) {
	            System.out.println("Error de verificación: El viaje en la posición " + i + " no coincide.");
	            System.out.println("Lista dice: " + viajeEnLista);
	            System.out.println("Debería decir: " + viajeCorrecto);
	            return false; // No son iguales
	        }
	    }
	    return true; // Si llegamos aquí, son idénticos.
	}

}
