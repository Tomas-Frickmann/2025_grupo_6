package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestAdministrador {
	Administrador admin;
	@Before
	public void setUp() throws Exception {
		this.admin = Administrador.getInstance();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testgetNombreUsuario() {
		assertEquals("","admin",admin.getNombreUsuario());
		
	}
	
	@Test
	public void testgetPass() {
		assertEquals("","admin",admin.getPass());
	}

	@Test
	public void testgetInstance() {
		Administrador ad = admin.getInstance();
		assertEquals("",ad,admin);
	}
}
