package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import util.Constantes;

public class TestChoferTemporario {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testConstructor() {
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		assertEquals("Problema en el dni", c1.getDni(), "123456");
		assertEquals("Problema en el nombre", c1.getNombre(), "nombreChofer");
		
	}
	@Test
	public void testgetDni() {
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		assertEquals("",c1.getDni(), "123456");
	}
	
	@Test
	public void testgetNombre() {
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		assertEquals("",c1.getNombre(), "nombreChofer");
	}
	
	@Test
	public void testSetSueldoBasico() {
		double sueldo = 1000;
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		c1.setSueldoBasico(sueldo);
		assertEquals(Constantes.SUELDO_DE_CHOFER, c1.getSueldoBasico(), sueldo);
	}
	
	@Test
	public void testgetSueldoNeto() {
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		c1.setSueldoBasico(1000);
		double sueldoNeto = 0.86*c1.getSueldoNeto();
		
	}
	
	@Test
	public void testgetSueldoBruto() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		c1.setSueldoBasico(1000);
		double sueldoBruto = c1.getSueldoBasico();
		assertEquals("Sueldos brutos iguales", c1.getSueldoBruto(), sueldoBruto);
	}

}

