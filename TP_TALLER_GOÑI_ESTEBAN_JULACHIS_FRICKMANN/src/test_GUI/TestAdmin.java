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
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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

public class TestAdmin {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	String usuario = "Usuario1";
	String pass = "123456";
	String nombreReal = "NombreReal";
	ChoferTemporario choferTemporal;
	ChoferPermanente choferPermanente;
	Auto auto;
	Moto moto;
	Combi combi;
	Empresa empresa = Empresa.getInstance();
	Pedido pedido;

	public TestAdmin() {
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
		this.empresa.getClientes().clear();
		this.empresa.getVehiculos().clear();
		this.empresa.getChoferes().clear();
		this.buildEscenario();
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	public void buildEscenario() {
		try {
			
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
			
			this.choferTemporal= new ChoferTemporario("11111111","nombreRealChofer1");
			this.choferPermanente= new ChoferPermanente("22222222","nombreRealChofer2", 2020, 2);
			
			this.empresa.agregarChofer(this.choferTemporal);
			this.empresa.agregarChofer(this.choferPermanente);
			
			this.auto = new Auto("AAA111",4,false);
			this.empresa.agregarVehiculo(this.auto);
			
			this.moto = new Moto("MMM222");
			this.empresa.agregarVehiculo(this.moto);
			
			this.combi = new Combi("CCC333",8,false);
			this.empresa.agregarVehiculo(this.combi);
			
			this.pedido = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarCliente("Octavio","1234", "Octavio esteban ");	
			this.empresa.agregarCliente("tom","1234", "Tomas Frickmann");
			this.empresa.agregarCliente("luchi","1234", "Lucioano julachis");	
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
	public void testVentanaCorrecta() { 
		//Se fija que la ventana de administrador se haya abierto correctamente
		this.logeaVentana("admin", "admin");
		JPanel adminPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_ADMINISTRADOR);
		robot.delay(this.delay*50);
		assertTrue("No se abrio correctamente el panel", adminPane != null && adminPane.isVisible());
		
	}
	
	//////////Visualización de información
	@Test
	public void testlistaChoferesTotalesCorrecto() {
		//Se fija que la JList contenga los choferes correctamente
	}
	
	@Test
	public void testListaChoferesTotales_SinSeleccionar() {
		//Los TextField 22 y 23 y la JList 21 deben estar vacios si no se selecciona ningun chofer
		
	}
	
	@Test
	public void testListaViajesHistoricos() {
		//Corrobora que al seleccionar un chofer, se muestren sus viajes historicos en la JList 21 correctamente
	}
	
	@Test
	public void testSueldoChoferCorrecto() {
		//Hacer pedidos con baul y toda la bola y corroborar que el sueldo se calcule correctamente
	}
	
	//////////Sección de listados
	@Test
	public void testListaClientesTotalesCorrecto() {
		//Se fija que la JList 24 contenga los clientes correctamente
	}
	
	@Test
	public void testListaVehiculosTotalesCorrecto() {
		//Se fija que la JList 25 contenga los vehiculos correctamente, por eso hay de los 3 tipos
	}
	
	@Test
	public void testListaViajesHistoricosCorrecta() {
		//Se fija que la JList 26 contenga todos los viajes historicos de la empresa
	}
	
	@Test 
	public void testSueldosTotales() {
		//El JTextField 15 debe mostrar la suma de todos los sueldos de los choferes
	}

	//////////Sección de altas choferes

	@Test
	public void testAltaChoferTemporal() {
		//Dar de alta un chofer temporario y corroborar que se haya agregado correctamente
	}
	
	@Test
	public void testAltaChoferPermanente() {
		//Dar de alta un chofer permanente y corroborar que se haya agregado correctamente
		//Año de ingreso 1900<x<3000? ; cantHijos >=0
	}
	
	@Test
	public void testButtonNuevoChofer_DNInuevo() {
		//Se crea un chofer nuevo, verificar que se vacien los JTextFields 1, 2, 5 y 6 deben vaciarse
	}
	
	@Test
	public void testButtonNuevoChofer_DNIexistente() {
		//Se intenta crear un chofer con un DNI ya existente, verificar que salte el mensaje de error. Los JText 1, 2, 5 y 6 deben vaciarse
	}
	
	@Test 
	public void testAltaChofer_letrasDNI() {
		//El JTextField 1 no debe aceptar letras
	}
	
	/////////Sección de altas vehiculos
		
	@Test
	public void testAltaMoto_AceptarVehiculoHab() {
		//El JTextField 8 debe tener al menos un caracter para habilitar el JButton 14
	}
	
	@Test
	public void testAltaAuto_AceptarVehiculoHab() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 0 y 4
	}
		
	@Test
	public void testAltaCombi_AceptarVehiculoHab() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 5 y 10
	}
	
	@Test
	public void testAltaMoto_pulsaBoton() {
		//Dar de alta una moto con el robot, verificar que se haya agregado correctamente
		//El JTextField 8 debe tener al menos un caracter para habilitar el JButton 14
	}
	
	@Test
	public void testAltaAuto_pulsaBoton() {
		//Dar de alta un auto con el robot, verificar que se haya agregado correctamente
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 0 y 4
	}
	
	@Test
	public void testAltaCombi_pulsaBoton() {
		//Dar de alta una combi con el robot, verificar que se haya agregado correctamente
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 5 y 10
	}
	
	/////////Sección de gestión de pedidos

	@Test
	public void testListaPedidosPendientesCorrecta() {
		//Se fija que la JList 16 contenga los pedidos pendientes correctamente
	}
	
	@Test
	public void testListaChoferesLibresCorrecta() {
		//Se fija que la JList 17 contenga los choferes libres correctamente al seleccionar un pedido
	}
	
	@Test
	public void testListaVehiculosDisponiblesCorrecta() {
		//Se fija que la JList 18 contenga los vehiculos disponibles correctamente al seleccionar un pedido
	
	}
	
	@Test
	public void testNuevoViajeHab() {
		//Hay que seleccionar un pedido, un chofer y un vehiculo. Verificar que el JButton 19 se habilite correctamente
		
	}
	
	@Test
	public void testPulsarNuevoViaje() {
		//Seleccionar un pedido, un chofer y un vehiculo. Pulsar el JButton 19 y verificar que se asigne correctamente el viaje
		//Verificar que el pedido desaparezca de la JList 16, el chofer de la JList 17 y el vehiculo de la JList 18
		
	}
}

