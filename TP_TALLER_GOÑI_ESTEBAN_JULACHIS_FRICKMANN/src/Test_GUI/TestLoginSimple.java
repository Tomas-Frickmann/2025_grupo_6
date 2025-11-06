package Test_GUI;

// O: Simple es por que no se va a logear a ningun usuario en este Test





import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Test_GUI.TestUtil;
import Test_GUI.FalsoOptionPane;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Component;

import controlador.Controlador;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modeloNegocio.Empresa;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Administrador;
import vista.*;
public class TestLoginSimple {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	Empresa empresa = Empresa.getInstance();
	
	
	public TestLoginSimple() {
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
		
		PanelCliente clientPane = (vista.PanelCliente) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_CLIENTE);
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
		PanelAdmin adminPane = (vista.PanelAdmin) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_ADMINISTRADOR);
		Assert.assertTrue(Mensajes.USUARIO_DESCONOCIDO.getValor(), adminPane != null && adminPane.isVisible());		
	}
}
