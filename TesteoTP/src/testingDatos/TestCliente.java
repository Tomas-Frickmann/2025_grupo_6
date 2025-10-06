package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
public class TestCliente {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

		
	@Test
	public void testConstructorCliente() {
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals(cliente.getNombreUsuario(),"nombreUsuario1");
		assertEquals(cliente.getPass(),"pass1");
		assertEquals(cliente.getNombreReal(),"nombreReal");
	}
	@Test
	public void testgetNombreUsuario(){
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals(cliente.getNombreUsuario(),"nombreUsuario1");		
	}
	@Test
	public void testgetPass(){
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals(cliente.getPass(),"pass1");
	}
	@Test
	public void testgetNombreReal(){
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals(cliente.getNombreReal(),"nombreReal");
	}
}
