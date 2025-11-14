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
import excepciones.UsuarioYaExisteException;
import modeloDatos.Administrador;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

	Empresa empresa = Empresa.getInstance();
	Ventana ventana;
	
	public TestCliente(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}

	@Before
	public void setUp() throws Exception {
		this.ventana = new Ventana();
		this.ventana.setOptionPane(op);
		controlador = new Controlador();
		controlador.setVista(this.ventana);
		this.ventana.setVisible(true);
		
		robot.delay(this.delay);
		
		
	}

	@After
	public void tearDown() throws Exception {
		ventana.setVisible(false);
	}
	
	public void setUpEscenario() {
		try {
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
		} catch (UsuarioYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto(this.pass, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto(this.usuario, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		
	}
	
	@Test
	public void testCerrarSesionHab() {
		this.setUpEscenario();
		JButton closeButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
		assertTrue("El boton debe estar habilitado",closeButton.isEnabled());
	}
	
	
	@Test 
	public void testnombre() {
		this.setUpEscenario();
		JPanel cliente = (JPanel) TestUtil.getComponentForName((Ventana) controlador.getVista(), Constantes.PANEL_CLIENTE);
		TitledBorder border = (TitledBorder) cliente.getBorder();
		assertEquals("El título debe ser el nombre del cliente",
				this.nombreReal,border.getTitle());
	}
	
	@Test
	public void testVuelveLogin() {
		this.setUpEscenario();
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
		this.setUpEscenario();
		JPanel paneCliente = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_CLIENTE);
		JTextArea POVA = (JTextArea) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);

		JTextField califText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		//POVA debe estar vacio y todas las componentes de nuevo pedido tienen que estar habilitadas
		assertTrue("JTextArea debe estar vacio",POVA.getText().isEmpty());
		
		
	}
	

}
