package test_GUI;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferRepetidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

public class TestAdminGestionPedidos {

	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	String usuario = "Usuario1";
	String pass = "123456";
	String nombreReal = "NombreReal";
	ChoferPermanente choferPermanente;
	ChoferTemporario choferTemporal;
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
			
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
			
			this.choferTemporal= new ChoferTemporario("11111111","nombreRealChofer1");
			this.choferPermanente= new ChoferPermanente("22222222","nombreRealChofer2", 2020, 2);
			
			this.empresa.agregarChofer(this.choferTemporal);
			this.empresa.agregarChofer(this.choferPermanente);
			
			this.auto = new Auto("AAA111",4,true);
			this.empresa.agregarVehiculo(this.auto);
			
			this.moto = new Moto("MMM222");
			this.empresa.agregarVehiculo(this.moto);
			
			this.combi = new Combi("CCC333",8,true);
			this.empresa.agregarVehiculo(this.combi);
			
			this.pedido = new Pedido(this.empresa.getClientes().get(this.usuario), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedido);
			
			this.empresa.agregarCliente("Octavio","1234", "Octavio esteban ");	
			this.empresa.agregarCliente("tom","1234", "Tomas Frickmann");
			this.empresa.agregarCliente("luchi","1234", "Lucioano julachis");	
			
			this.pedido = new Pedido(this.empresa.getClientes().get("tom"), 2, true, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedido);
			
			this.pedido = new Pedido(this.empresa.getClientes().get("luchi"), 1, false, false, 1, Constantes.ZONA_PELIGROSA);
			this.empresa.agregarPedido(this.pedido);
			
			
			this.empresa.crearViaje(this.pedido, this.choferPermanente, auto);
			
			
			}
			
		catch(Exception e) {
			System.out.println("Problemas en el escenario " + e.getMessage());
		}
	}
	public void buildEscenario_Desocupado() {
		try {
			
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
			
			this.choferTemporal= new ChoferTemporario("11111111","nombreRealChofer1");
			this.choferPermanente= new ChoferPermanente("22222222","nombreRealChofer2", 2020, 2);
			
			this.empresa.agregarChofer(this.choferTemporal);
			this.empresa.agregarChofer(this.choferPermanente);
			
			this.auto = new Auto("AAA111",4,true);
			this.empresa.agregarVehiculo(this.auto);
			
			this.moto = new Moto("MMM222");
			this.empresa.agregarVehiculo(this.moto);
			
			this.combi = new Combi("CCC333",8,true);
			this.empresa.agregarVehiculo(this.combi);
			
			this.pedido = new Pedido(this.empresa.getClientes().get(this.usuario), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedido);
			
			this.empresa.agregarCliente("Octavio","1234", "Octavio esteban ");	
			this.empresa.agregarCliente("tom","1234", "Tomas Frickmann");
			this.empresa.agregarCliente("luchi","1234", "Lucioano julachis");	
			
			this.pedido = new Pedido(this.empresa.getClientes().get("tom"), 2, true, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedido);
			
			this.pedido = new Pedido(this.empresa.getClientes().get("luchi"), 1, false, false, 1, Constantes.ZONA_PELIGROSA);
			this.empresa.agregarPedido(this.pedido);
			
			
			
			}
			
		catch(Exception e) {
			System.out.println("Problemas en el escenario " + e.getMessage());
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

	public void logeaVentana() {
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("admin", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("admin", robot);
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

	public void creaChofer(String DNI, String nombre) {
		JTextField dnicText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nameText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		
		TestUtil.clickComponent(dnicText, robot);
		TestUtil.tipeaTexto(DNI, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nameText, robot);
		TestUtil.tipeaTexto(nombre, robot);
		robot.delay(this.delay);
		
		JRadioButton tempRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.TEMPORARIO);
		
		TestUtil.clickComponent(tempRadio, robot);
		//Nuevo chofer
		robot.delay(this.delay);
		
		
	}
	
	public void creaChofer(String DNI, String nombre,int anio, int hijos) {
		JTextField dnicText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nameText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		
		TestUtil.clickComponent(dnicText, robot);
		TestUtil.tipeaTexto(DNI, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nameText, robot);
		TestUtil.tipeaTexto(nombre, robot);
		robot.delay(this.delay);

		JRadioButton permRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		TestUtil.clickComponent(permRadio, robot);
		robot.delay(this.delay);
		
		JTextField anioText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JTextField hijosText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		
		if(anio >= 0) {
			TestUtil.clickComponent(anioText, robot);
			TestUtil.tipeaTexto(String.valueOf(anio), robot);
			robot.delay(this.delay);			
		}
		
		if(hijos >= -1) {
			TestUtil.clickComponent(hijosText, robot);
			TestUtil.tipeaTexto(String.valueOf(hijos), robot);
			robot.delay(this.delay);			
		}

		
		
	}
	
	
	@Test
	public void testListaPedidosPendientesCorrecta() {
		//Se fija que la JList 16 contenga los pedidos pendientes correctamente
		this.buildEscenario();
		this.logeaVentana();
		
		JList pedPendJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		//ArrayList <Chofer> ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		ArrayList <Pedido> pedEmpresa = new ArrayList<Pedido> (this.empresa.getPedidos().values());
		
		robot.delay(this.delay);
		assertTrue("Las listas no son iguales", this.verificarLista(pedEmpresa, pedPendJList));
		
	}
	
	@Test
	public void testListaChoferesLibresCorrecta() {
		//Se fija que la JList 17 contenga los choferes libres correctamente al seleccionar un pedido
		
		this.buildEscenario();
		this.logeaVentana();
		
		JList chofLibreJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		//ArrayList <Chofer> ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		ArrayList <Chofer> chofLibreEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		
		robot.delay(this.delay);
		assertTrue("Las listas no son iguales", this.verificarLista(chofLibreEmpresa, chofLibreJList));
		
	}
	
	@Test
	public void testNuevoViajeHab() {
		//Hay que seleccionar un pedido, un chofer y un vehiculo. Verificar que el JButton 19 se habilite correctamente
		
		this.buildEscenario();
		this.logeaVentana();
		
		JList pedidosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList choferesJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList vehiculosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		pedidosJList.setSelectedIndex(0);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(choferesJList, robot);
		robot.delay(this.delay*2);
		
		TestUtil.clickComponent(vehiculosJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		assertTrue("El boton nuevo viaje deberia estar habilitado", nvButton.isEnabled());
		
		
	}
	
	@Test
	public void testNuevoViajeHab_pedidos() {
		//Hay que seleccionar un pedido, un chofer y un vehiculo. Verificar que el JButton 19 se habilite correctamente
		
		this.buildEscenario();
		this.logeaVentana();
		
		
		JList choferesJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList vehiculosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
				
		
		TestUtil.clickComponent(choferesJList, robot);
		robot.delay(this.delay*2);
		
		TestUtil.clickComponent(vehiculosJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		assertTrue("El boton nuevo viaje deberia estar deshabilitado", !nvButton.isEnabled());
		
		
	}
	
	@Test
	public void testNuevoViajeHab_choferes() {
		//Hay que seleccionar un pedido, un chofer y un vehiculo. Verificar que el JButton 19 se habilite correctamente
		
		this.buildEscenario();
		this.logeaVentana();
		
		JList pedidosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);

		JList vehiculosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		pedidosJList.setSelectedIndex(0);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(vehiculosJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		assertTrue("El boton nuevo viaje deberia estar deshabilitado", !nvButton.isEnabled());
		
		
	}
	
	@Test
	public void testNuevoViajeHab_vehiculo() {
		//Hay que seleccionar un pedido, un chofer y un vehiculo. Verificar que el JButton 19 se habilite correctamente
		
		this.buildEscenario();
		this.logeaVentana();
		
		JList pedidosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList choferesJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		
		pedidosJList.setSelectedIndex(0);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(choferesJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		assertTrue("El boton nuevo viaje deberia estar deshabilitado", !nvButton.isEnabled());
		
		
	}
	
	@Test
	public void testPulsarNuevoViaje_pedidos() {
		//Seleccionar un pedido, un chofer y un vehiculo. Pulsar el JButton 19 y verificar que se asigne correctamente el viaje
		//Verificar que el pedido desaparezca de la JList 16, el chofer de la JList 17 y el vehiculo de la JList 18
	
		this.buildEscenario_Desocupado();
		this.logeaVentana();
		
		JList pedidosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList choferesJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList vehiculosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		pedidosJList.setSelectedIndex(0);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(choferesJList, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(vehiculosJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		TestUtil.clickComponent(nvButton, robot);
		robot.delay(this.delay);
		
		ArrayList <Pedido> pedidosEmpresa = new ArrayList<Pedido> (this.empresa.getPedidos().values());
		
		robot.delay(this.delay);
		assertTrue("Las listas no son iguales", this.verificarLista(pedidosEmpresa, pedidosJList));
		
			
		}
	
	@Test
	public void testPulsarNuevoViaje_chofer() {
		//Seleccionar un pedido, un chofer y un vehiculo. Pulsar el JButton 19 y verificar que se asigne correctamente el viaje
		//Verificar que el pedido desaparezca de la JList 16, el chofer de la JList 17 y el vehiculo de la JList 18
	
		this.buildEscenario_Desocupado();
		this.logeaVentana();
		
		JList pedidosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList choferesJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList vehiculosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		pedidosJList.setSelectedIndex(0);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(choferesJList, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(vehiculosJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		TestUtil.clickComponent(nvButton, robot);
		robot.delay(this.delay);
		
		
		ArrayList <Chofer> choferesEmpresa = this.empresa.getChoferesDesocupados();
		
		robot.delay(this.delay*50);
		assertTrue("Las listas no son iguales", this.verificarLista(choferesEmpresa, choferesJList));
		
			
		}
	
	@Test
	public void testPulsarNuevoViaje_vehiculos() {
		//Seleccionar un pedido, un chofer y un vehiculo. Pulsar el JButton 19 y verificar que se asigne correctamente el viaje
		//Verificar que el pedido desaparezca de la JList 16, el chofer de la JList 17 y el vehiculo de la JList 18
	
		this.buildEscenario_Desocupado();
		this.logeaVentana();
		
		JList pedidosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList choferesJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList vehiculosJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		pedidosJList.setSelectedIndex(0);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(choferesJList, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(vehiculosJList, robot);
		robot.delay(this.delay);
		
		JButton nvButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		TestUtil.clickComponent(nvButton, robot);
		robot.delay(this.delay);
		
		robot.delay(this.delay);
		assertTrue("Las lista de vehiculos deberias estar vacia", vehiculosJList.getModel().getSize() == 0);
		
			
		}
		
	}


