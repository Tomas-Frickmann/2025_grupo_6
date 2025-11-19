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
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.SinViajesException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import modeloDatos.*;
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

public class TestAdminVisualizacion {
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

	
	public TestAdminVisualizacion() {
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
		this.empresa.getChoferesDesocupados().clear();
		this.buildEscenario();
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
			this.empresa.login("luchi", "1234");
			this.empresa.pagarYFinalizarViaje(4);
			this.empresa.logout();
			
			
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
	
	
	//////////Visualización de información
	@Test
	public void testlistaChoferesTotalesCorrecto() {
		//Se fija que la JList contenga los choferes correctamente
		this.logeaVentana("admin", "admin");
		
		//choferes totales List
		JList ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		ArrayList <Chofer> ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		
		robot.delay(this.delay);
		assertTrue("La lista no es correcta", this.verificarLista(ctEmpresa, ctList));
		
		
		
	}
	
	@Test
	public void testListaChoferesTotales_SinSeleccionar() {
		//Los TextField 22 y 23 y la JList 21 deben estar vacios si no se selecciona ningun chofer
		this.logeaVentana("admin", "admin");
		
		JTextField puntText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);
		JTextField sueldoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);
		//Lista viaje chofer List
		JList lvcList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
		robot.delay(this.delay);
		assertTrue("Los TextField 22 y 23 y la JList 21 deben estar vacios si", puntText.getText().isEmpty() && sueldoText.getText().isEmpty() && (lvcList.getModel().getSize() == 0));
		
	}
	
	@Test
	public void testListaViajesHistoricos_Vacio() {
		//Corroborar que la JList 21 este vacio
		//Esto esta chequeado en la función anterior
		
		this.logeaVentana("admin", "admin");
		
		//choferes totales List
		JList lvhList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_HISTORICOS);
		robot.delay(this.delay);
		assertTrue("La lista no es correcta",(lvhList.getModel().getSize() == 0));
		
		
	}
	
	@Test
	public void testSueldoChoferCorrecto() {
		//Hacer pedidos con baul y toda la bola y corroborar que el sueldo se calcule correctamente
		this.logeaVentana("admin", "admin");
		
		//choferes totales List
		JList ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
		TestUtil.clickComponent(ctList, robot);

		robot.delay(this.delay);
		JTextField sueldoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);
		robot.delay(this.delay);
		Double sueldo = Double.valueOf(((Chofer) ctList.getSelectedValue()).getSueldoNeto());
		
		String sueldoEmpresaText = sueldoText.getText().replace(",", ".");
		Double sueldoEmpresa = Double.valueOf(sueldoEmpresaText);
				
		assertEquals("El sueldo no coincide", sueldo, sueldoEmpresa,0.01);
	}
	
	@Test
	public void testSueldosTotales() {
		this.logeaVentana("admin","admin");
		
		JTextField sueldosTotales = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.TOTAL_SUELDOS_A_PAGAR);
		Double sueldos = Double.valueOf(sueldosTotales.getText().replaceAll(",", "."));
		
		assertEquals("Los sueldos totales no estan siendo calculados correctamente", this.empresa.getTotalSalarios(), sueldos,0.01);
		
	}

	@Test
	public void testCalificacionChoferCorrecto() {

		this.logeaVentana("admin", "admin");
		
		//choferes totales List
		JList ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
		TestUtil.clickComponent(ctList, robot);

		robot.delay(this.delay);
		JTextField sueldoText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);
		robot.delay(this.delay);
		Double sueldo=0.0;
		
		try {
			sueldo = Double.valueOf( this.empresa.calificacionDeChofer(((Chofer)ctList.getSelectedValue())));
		} catch (SinViajesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sueldoEmpresaText = sueldoText.getText().replace(",", ".");
		Double sueldoEmpresa = Double.valueOf(sueldoEmpresaText);
				
		assertEquals("La calificacion no coincide", sueldo, sueldoEmpresa,0.01);
	}

}
