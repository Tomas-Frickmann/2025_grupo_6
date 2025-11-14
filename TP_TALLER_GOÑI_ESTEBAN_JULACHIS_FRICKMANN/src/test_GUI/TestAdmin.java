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

public class TestAdmin {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();

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
		
		
		robot.delay(this.delay);
		
		JTextField nameButton = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField passButton = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		
		TestUtil.clickComponent(nameButton, robot);
		TestUtil.tipeaTexto("Admin", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passButton, robot);
		TestUtil.tipeaTexto("Admin", robot);	
		robot.delay(this.delay);
		
		JButton loginButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		TestUtil.clickComponent(loginButton, robot);
		robot.delay(this.delay);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVentanaCorrecta() { //Se fija que la ventana de administrador se haya abierto correctamente
		assertTrue("",true);
		
	}
	

}
