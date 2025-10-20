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
		assertEquals("No se obtuvo el campo correctamente",c1.getDni(), "123456");
	}
	
	@Test
	public void testgetNombre() {
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		assertEquals("No se obtuvo el campo correctamente",c1.getNombre(), "nombreChofer");
	}
	
	@Test
	public void testGetSueldoBasico() {
		double sueldo = 1000;
		
		ChoferTemporario.setSueldoBasico(sueldo);
		assertEquals("Calcula mal el sueldo basico",Double.valueOf(ChoferTemporario.getSueldoBasico()), Double.valueOf(sueldo));
	}
	
	
	
	@Test
	public void testgetSueldoBruto() {
			double sueldo = 1000;
			ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		ChoferTemporario.setSueldoBasico(sueldo);
		assertEquals("Calcula mal el sueldo bruto",Double.valueOf(c1.getSueldoBruto()), Double.valueOf(sueldo));

	}
	@Test
	public void testgetSueldoNeto() {
		double sueldo = 1000;
		ChoferTemporario c1 = new ChoferTemporario("123456", "nombreChofer");
		ChoferTemporario.setSueldoBasico(sueldo);
		double sueldoNeto = 0.86*c1.getSueldoBruto();
		assertEquals(Double.valueOf(c1.getSueldoNeto()), Double.valueOf(sueldoNeto) );
	}
	
}

