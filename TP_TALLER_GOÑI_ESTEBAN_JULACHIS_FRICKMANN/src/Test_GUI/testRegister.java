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
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JButton;


public class testRegister {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	public void registro() {
		robot.delay(this.delay);
		
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		JTextField nomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);

		JButton canButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
		
		TestUtil.clickComponent(regButton, robot);
		regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		robot.delay(this.delay);
		
		
		
		
	}

}
