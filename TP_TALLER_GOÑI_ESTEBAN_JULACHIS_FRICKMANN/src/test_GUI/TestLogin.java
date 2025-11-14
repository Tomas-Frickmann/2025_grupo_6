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
import modeloNegocio.Empresa;
import test_GUI.FalsoOptionPane;
import test_GUI.TestUtil;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modeloNegocio.Empresa;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Administrador;
import vista.*;
public class TestLogin {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	Empresa empresa = Empresa.getInstance();
	
	
	public TestLogin() {
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
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSoloNombre() {
		robot.delay(this.delay);
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("hola", robot);
		
		Assert.assertFalse("El boton de login deberia estar deshabilitado", aceptarLog.isEnabled());
		
	}
	
	@Test
	public void testSoloContraseña() {
		robot.delay(this.delay);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("123456", robot);
		
		Assert.assertFalse("El boton de login deberia estar deshabilitado", aceptarLog.isEnabled());
		
	}

	@Test
	public void testIntentoLogin() {
		robot.delay(this.delay);
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("1234567", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("Nombre", robot);
		
		Assert.assertTrue("El boton de login deberia estar habilitado", aceptarLog.isEnabled());
	}
	
	@Test
	public void testLoginFallido() {
		robot.delay(this.delay);
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("1234567", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("NombreDesconocido", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		Assert.assertTrue(Mensajes.USUARIO_DESCONOCIDO.getValor(), op.getMensaje().equals(Mensajes.USUARIO_DESCONOCIDO.getValor()));
	}
	
	@Test
	public void testLoginCorrecto() {
		try {
			empresa.agregarCliente("NombreUnico", "123456", "NombreReal1");
		}
		catch(excepciones.UsuarioYaExisteException e) {System.out.println("??");}
		robot.delay(this.delay);
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("NombreUnico", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		
		JPanel clientPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_CLIENTE);
		Assert.assertTrue("Usuario logeado con exíto",clientPane != null && clientPane.isVisible());
		
		
	}
	
	@Test
	public void testLoginCorrectoAdmin() {
		robot.delay(this.delay);
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("admin", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("admin", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		JPanel adminPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_ADMINISTRADOR);
		Assert.assertTrue(Mensajes.USUARIO_DESCONOCIDO.getValor(), adminPane != null && adminPane.isVisible());		
	}
	
	@Test
	public void testLoginVacios() {
		
		robot.delay(this.delay);
		
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		
		TestUtil.clickComponent(regButton, robot);
		robot.delay(this.delay);
		
		
		JTextField regNomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		TestUtil.clickComponent(regNomText, robot);
		TestUtil.tipeaTexto("Text", robot);//Texto creado para testear que vuelve vacio
		robot.delay(this.delay);
		
		JButton cancButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
		
		TestUtil.clickComponent(cancButton, robot);
		robot.delay(this.delay);
		
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		boolean bool = nombre.getText().isEmpty() && pass.getText().isEmpty() && !aceptarLog.isEnabled();
		Assert.assertTrue("Los campos de login estan vacios y el boton deshabilitado", bool);
	}
}
