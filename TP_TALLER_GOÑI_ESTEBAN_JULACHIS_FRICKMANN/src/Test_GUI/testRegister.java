package Test_GUI;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Component;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import vista.*;
import modeloNegocio.Empresa;

public class testRegister {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	Empresa empresa = Empresa.getInstance();
	String nomEx = "NombreExistente", passEx = "123456", nomRealEx = "NombreRealExistente";
	
	
	
	public testRegister() {
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
		
		robot.delay(this.delay);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtil.clickComponent(regButton, robot);
		robot.delay(this.delay);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void registroCorrecto() {
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
		
		JPanel logPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
		assertTrue("Deberia volver al panel de login", logPane.isVisible());
		
	}
	
	@Test
	public void registroIncorrectoContraseñasNoCoinciden() {
		
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
		TestUtil.tipeaTexto("654321", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(regButton, robot);	
		robot.delay(this.delay);
		
		assertTrue(Mensajes.PASS_NO_COINCIDE.getValor(), op.getMensaje().equals(Mensajes.PASS_NO_COINCIDE.getValor()) );
	}
	
	@Test
	public void registroIncorrectoUsuarioExistente() {
		
		try {
			this.empresa.agregarCliente(this.nomEx, this.passEx, this.nomRealEx);
		}catch(Exception e){}
		
		
		robot.delay(this.delay);
		
		JTextField nomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		
		
		TestUtil.clickComponent(nomText, robot);
		TestUtil.tipeaTexto(this.nomEx, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passText, robot);
		TestUtil.tipeaTexto(this.passEx, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passTextAgain, robot);
		TestUtil.tipeaTexto(this.passEx, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto(this.nomRealEx, robot);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(regButton, robot);	
		robot.delay(this.delay);
		
		assertTrue(Mensajes.USUARIO_REPETIDO.getValor(), op.getMensaje().equals(Mensajes.USUARIO_REPETIDO.getValor()) );
	}

	@Test
	public void registroValidezRegistrar_RealName() {
		robot.delay(this.delay);
		
		JTextField nomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
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
		
		assertTrue("El boton registrar deberia estar deshabilitado",regButton.isValid());
	}
	
	@Test
	public void registroValidezRegistrar_NameUser() {
		robot.delay(this.delay);
		
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		
		
		
		TestUtil.clickComponent(passText, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passTextAgain, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		assertTrue("El boton registrar deberia estar deshabilitado",regButton.isValid());
	}
	
	@Test
	public void registroValidezRegistrar_pass() {
		robot.delay(this.delay);
		

		JTextField UserText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		
		TestUtil.clickComponent(UserText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passTextAgain, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		assertTrue("El boton registrar deberia estar deshabilitado",regButton.isValid());
	}
	
	@Test
	public void registroValidezRegistrar_passAgain() {
		robot.delay(this.delay);
		

		JTextField UserText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		
		TestUtil.clickComponent(UserText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passText, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		assertTrue("El boton registrar deberia estar deshabilitado",regButton.isValid());
	}
	
	@Test
	public void registroCancelar() {
		robot.delay(this.delay);
		
		JTextField nomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton canButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
		
		
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
		
		
		TestUtil.clickComponent(canButton, robot);	
		robot.delay(this.delay);
		
		JPanel logPane = (JPanel) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
		assertTrue("Deberia volver al panel de login", logPane.isVisible());

		
	}
}
