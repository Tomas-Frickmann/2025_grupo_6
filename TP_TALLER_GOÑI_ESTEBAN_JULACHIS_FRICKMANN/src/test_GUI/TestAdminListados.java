package test_GUI;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;

public class TestAdminListados {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	String usuario = "Usuario1";
	String pass = "123456";
	String nombreReal = "NombreReal";
	
	ChoferPermanente choferPermanente;
	ChoferTemporario choferTemporario;
	Moto moto;
	Combi combi;
	Auto auto;
	Empresa empresa = Empresa.getInstance();
	Pedido pedido;

	@Before
	public void setUp() throws Exception {
		controlador = new Controlador();
		controlador.getVista().setOptionPane(op);
		this.empresa.getClientes().clear();
		this.empresa.getVehiculos().clear();
		this.empresa.getChoferes().clear();
		this.empresa.getChoferesDesocupados().clear();
		try {
			robot = new Robot(); 
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}

	@After
	public void tearDown() throws Exception {
		this.empresa.getClientes().clear();
		this.empresa.getVehiculos().clear();
		this.empresa.getChoferes().clear();
		this.empresa.getChoferesDesocupados().clear();
	}
	
	public void buildEscenario() {
		try {
			
			this.empresa.agregarCliente("Usuario1", "12345678", "NombreReal1");
			this.empresa.agregarCliente("Usuario2", "12345677", "nombreRealCliente2");
			
			this.choferPermanente = new ChoferPermanente("11111111","nombreRealChofer1",2020,4);
			this.empresa.agregarChofer(this.choferPermanente);
			
			this.auto= new Auto("AAA111",4,false);
			this.moto = new Moto("AAA222");
			this.empresa.agregarVehiculo(auto);
			this.empresa.agregarVehiculo(moto);
			
			Pedido pedido1 = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.pedido = pedido1;
			this.empresa.agregarPedido(pedido1);
			
			this.empresa.crearViaje(pedido1, choferPermanente, auto);
			this.empresa.login("Usuario1", "12345678");
			this.empresa.pagarYFinalizarViaje(3);
			
			this.empresa.logout();
			
			pedido1 = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 2, Constantes.ZONA_PELIGROSA);
			this.pedido = pedido1;
			this.empresa.agregarPedido(pedido1);
			
			this.empresa.crearViaje(pedido1, choferPermanente, auto);
			this.empresa.login("Usuario1", "12345678");
			this.empresa.pagarYFinalizarViaje(3);
			
			this.empresa.logout();
			}
			
		catch(Exception e) {
			System.out.println("Problemas en el escenario " + e.getMessage());
		}
	}
	
	public void buildEscenarioClientes() {
		try {
			
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
			
			this.empresa.agregarCliente("Usuario2", "654321", "OtroNombre");
			this.empresa.agregarCliente("Usuario3", "111111", "Nombre3");
			
			
			}
			
		catch(Exception e) {
			System.out.println("Problemas en el escenario " + e.getMessage());
		}
	}
	
	public void buildExcenarioVehiculos() {
		this.auto = new Auto("AAA111",4,true);
		this.moto = new Moto("MMM222");
		this.combi = new Combi("CCC333",8,true);
		
		try {
			this.empresa.agregarVehiculo(this.auto);
			this.empresa.agregarVehiculo(this.moto);
			this.empresa.agregarVehiculo(this.combi);
		
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public boolean verificarLista(ArrayList listaEmpresa, JList<Object> historArea) {


	    ListModel<Object> model = historArea.getModel();


	    if (model.getSize() != listaEmpresa.size()) {
	        System.out.println("Error de verificación: El número de viajes no coincide.");
	        System.out.println("Modelo tiene: " + model.getSize() + ", Lista correcta tiene: " + listaEmpresa.size());
	        return false; // No son iguales
	    }


	    for (int i = 0; i < model.getSize(); i++) {

	        Object objetoEnLista = model.getElementAt(i);
	        Object objetoCorrecto = listaEmpresa.get(i);

	        if (!objetoEnLista.equals(objetoCorrecto)) {
	            System.out.println("Error de verificación: El viaje en la posición " + i + " no coincide.");
	            System.out.println("Lista dice: " + objetoEnLista);
	            System.out.println("Debería decir: " + objetoCorrecto);
	            return false; 
	        }
	    }
	    return true;
	}
	
	public void agregaVehiculo(String patente, int tipo, int plazas, boolean mascota) {
		
		JTextField patentes = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		
		TestUtil.clickComponent(patentes, robot);
		TestUtil.tipeaTexto(patente, robot);
		robot.delay(this.delay);
		
		JRadioButton rad1 = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JRadioButton rad2 = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.MOTO);
		JRadioButton rad3 = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		
		if (tipo == 1)
			TestUtil.clickComponent(rad1, robot);
		else
			if(tipo ==2)
				TestUtil.clickComponent(rad2, robot);
			else
				TestUtil.clickComponent(rad3, robot);
		
		robot.delay(this.delay);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		if(tipo != 2) {
			if(mascota) {
				JCheckBox mascBox = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_VEHICULO_ACEPTA_MASCOTA);
				TestUtil.clickComponent(mascBox, robot);
				robot.delay(this.delay);
			}
			
			
			TestUtil.clickComponent(paxText, robot);
			TestUtil.tipeaTexto(String.valueOf(plazas), robot);
			robot.delay(this.delay);
		}
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		/*
		TestUtil.clickComponent(patentes, robot);
		TestUtil.borraJTextField(patentes, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(paxText, robot);
		TestUtil.borraJTextField(paxText, robot);
		robot.delay(this.delay);
		Comentado por que imposibilita el testing
		*/
		
		
	}
	
	public void agregaCliente() {
		JButton regButtoninicial = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		
		TestUtil.clickComponent(regButtoninicial, robot);
		robot.delay(this.delay);
		
		JTextField nomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		
		
		TestUtil.clickComponent(nomText, robot);
		TestUtil.tipeaTexto("UsuarioNuevo", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passText, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passTextAgain, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(regButton, robot);	
		robot.delay(this.delay);
	}
	
	@Test
	public void testListaClientesTotalesCorrecto() {
			//añande un usuario
			//Se fija que la JList 24 contenga los clientes correctamente
		
	
		buildEscenarioClientes();
		this.agregaCliente();
		
		
		logeaVentana("admin", "admin");
		
		JList jlistclientes = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTADO_DE_CLIENTES);
		ArrayList <Cliente> clEmpresa = new ArrayList<Cliente> (this.empresa.getClientes().values());
		
		robot.delay(this.delay);
		
				
		assertTrue("La lista clientes no es correcta", this.verificarLista(clEmpresa, jlistclientes));
		
		
	}
	
	@Test
	public void testListaVehiculosTotalesCorrecto() {
		//Se fija que la JList 25 contenga los vehiculos correctamente, por eso hay de los 3 tipos
		this.buildExcenarioVehiculos();
		this.logeaVentana("admin", "admin");
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		assertTrue("Las listas no son iguales", this.verificarLista(vtEmpresa, vehJList));
	}
	
	@Test
	public void testListaViajesChoferSelec() {
		//Se fija que la JList 26 contenga todos los viajes historicos de la empresa
		this.buildEscenario();
		this.logeaVentana("admin","admin");
		
		JList histJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
		
		JList chofJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		chofJList.setSelectedIndex(0);
		
		ArrayList <Viaje> vhEmpresa = this.empresa.getHistorialViajeChofer(choferPermanente);
		robot.delay(this.delay*50);
		assertTrue("Las listas no son iguales", this.verificarLista(vhEmpresa, histJList));
		
		
		
	}
	
}
