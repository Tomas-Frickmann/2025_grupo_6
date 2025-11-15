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
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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
	Empresa empresa;
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
		this.empresa = Empresa.getInstance();
		this.empresa.setClientes(new HashMap<String, modeloDatos.Cliente>());
		
		
		controlador = new Controlador();
		controlador.getVista().setOptionPane(op);
		
		try {
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
		} catch (UsuarioYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@After
	public void tearDown() throws Exception {
		this.empresa = null;
	}
	
	//Escenario derivado del 3
	public void buildEscenario() {
		try {
			this.choferPermanente = new ChoferPermanente("11111111","nombreRealChofer1",2020,4);
			this.empresa.agregarChofer(this.choferPermanente);
			this.auto = new Auto("AAA111",4,false);
			this.empresa.agregarVehiculo(this.auto);
			this.pedido = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
		}
		catch(Exception e) {
			System.out.println("Problemas en el escenario");
		}
	}
	public void logeaVentana() {
		System.out.println(this.controlador.toString());
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto(this.usuario, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto(this.pass, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		System.out.println(this.controlador.toString());
		
		robot.delay(this.delay);
		
	}
	
	@Test
	public void testCerrarSesionHab() {
		this.logeaVentana();
		JButton closeButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
		assertTrue("El boton debe estar habilitado",closeButton.isEnabled());
	}
	
	
	@Test 
	public void testnombre() {
		this.logeaVentana();
		JPanel cliente = (JPanel) TestUtil.getComponentForName((Ventana) controlador.getVista(), Constantes.PANEL_CLIENTE);
		TitledBorder border = (TitledBorder) cliente.getBorder();
		assertEquals("El título debe ser el nombre del cliente",
				this.nombreReal,border.getTitle());
	}
	
	@Test
	public void testVuelveLogin() {
		this.logeaVentana();
		JButton closeButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
		
		TestUtil.clickComponent(closeButton, robot);
		robot.delay(this.delay);
		
		JPanel loginPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
		
		assertTrue("La ventana final no es la correcta",loginPane != null && loginPane.isVisible());	
	}
	
	//POVA = Pedido_O_Viaje_Actual
	@Test
	public void testPOVA_vacio() {
		this.logeaVentana();
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
	public void testPedidoEx() throws Exception{
		this.buildEscenario();	
		
		this.empresa.agregarPedido(this.pedido);
		
		this.logeaVentana();
		
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
	public void testViajeEx() throws Exception {
		this.buildEscenario();
		
		this.empresa.agregarPedido(this.pedido);
		this.empresa.crearViaje(this.pedido, this.choferPermanente, this.auto);
		this.logeaVentana();
		
		
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
		this.buildEscenario();
		this.logeaVentana();
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertTrue("El boton de nuevo pedido debe estar habilitado",nuevButton.isEnabled());
		
		
	}
	
	@Test
	public void testRealizaPedido_defecto_fallido() throws Exception {
		this.buildEscenario();
		this.logeaVentana();
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("8", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		TestUtil.clickComponent(nuevButton, robot);
		robot.delay(this.delay);
		assertTrue(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),op.getMensaje().equals(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor()));
		
		
	}
	
	@Test
	public void testRealizaPedido_defecto_correcto() throws Exception {
		this.buildEscenario();
		this.logeaVentana();
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField kmText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);

		TestUtil.clickComponent(paxText,robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(kmText, robot);
		TestUtil.tipeaTexto("4", robot);
		robot.delay(this.delay);
		
		//Con esto alcanza porque el resto de componentes tienen valores por defecto
		
		
		JButton nuevButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		TestUtil.clickComponent(nuevButton,robot);
		robot.delay(this.delay);
		
		assertTrue("Campo cantidad de pasajeros y cantidad de kilometros deben estar vacios", paxText.getText().isEmpty() && kmText.getText().isEmpty());
		
		JTextArea POVA = (JTextArea) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		Cliente cliente = this.empresa.getClientes().get(this.usuario);
		
		this.empresa.crearViaje(this.empresa.getPedidoDeCliente(cliente), this.choferPermanente, this.auto); //La documentación no aclara pero asumo que de ser posible, casi instantaneamente, se crea el viaje.
		
		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		JTextField costoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		
		assertTrue("Campo calificacion debe estar habilitado y el campo costo distinto de vacio",califText.isEnabled() && !costoText.getText().isEmpty());


		
		
		
		
		
		
	}

}
