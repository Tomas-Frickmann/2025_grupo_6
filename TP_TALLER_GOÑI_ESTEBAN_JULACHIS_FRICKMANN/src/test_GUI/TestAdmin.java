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
import modeloDatos.Chofer;
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
	
	@Test
	public void testVentanaCorrecta() { 
		//Se fija que la ventana de administrador se haya abierto correctamente
		this.logeaVentana("admin", "admin");
		JPanel adminPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_ADMINISTRADOR);
		robot.delay(this.delay*50);
		assertTrue("No se abrio correctamente el panel", adminPane != null && adminPane.isVisible());
		
	}
	
	//////////Visualización de información: Archivo correspondiente
	
	//////////Sección de listados: Archivo correspondiente
	

	//////////Sección de altas choferes: Archivo correspondiente

	
	/////////Sección de altas vehiculos: Archivo correspondiente
	
	/////////Sección de gestión de pedidos: Archivo correspondiente
}

