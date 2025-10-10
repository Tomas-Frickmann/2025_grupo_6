package testingDatos;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import util.Constantes;

public class TestChoferPermanente {
	
	public static int anioActual = 2025;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("Problema en el dni", c1.getDni(), "123456");
		assertEquals("Problema en el nombre", c1.getNombre(), "nombreChofer");
		assertEquals("Problema en el anio", c1.getAnioIngreso(), 2024);
		assertEquals("Problema en la cantidad de hijos", c1.getCantidadHijos(), 2);
		
	}
	
	@Test
	public void testgetAnioIngreso() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("",c1.getAnioIngreso(), 2024);
	}
	
	@Test
	public void testgetAntiguedad() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("",c1.getAntiguedad(), anioActual - c1.getAnioIngreso());
	}
	
	@Test
	public void testgetCantidadHijos() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("",c1.getCantidadHijos(), 2);
	}
	
	@Test
	public void testgetSueldoBruto() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		int antiguedad;
		
		c1.setSueldoBasico(1000);
		if (c1.getAntiguedad() <= 20){
			antiguedad = c1.getAntiguedad();			
		}
		else {
			antiguedad = 20;
		}
		double sueldoBruto = c1.getSueldoBasico() + 0.05*(antiguedad)*c1.getSueldoBasico() + 0.07*c1.getCantidadHijos()*c1.getSueldoBasico();
		
		
		assertEquals(Double.valueOf(c1.getSueldoBruto()), Double.valueOf(sueldoBruto));
	}
	
	@Test
	public void testgetSueldoBasico() {
		double sueldo = 1000.0;
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		c1.setSueldoBasico(sueldo);
		
		assertEquals(Double.valueOf(c1.getSueldoBasico()), Double.valueOf(sueldo) );
	}
	
	@Test
	public void testgetSueldoNeto() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		c1.setSueldoBasico(1000);
		double sueldoNeto = 0.86*c1.getSueldoBruto();
		assertEquals(Double.valueOf(c1.getSueldoNeto()), Double.valueOf(sueldoNeto) );
	}
	
	@Test
	public void testgetDni() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("",c1.getDni(), "123456");
	}
	
	@Test
	public void testgetNombre() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("",c1.getNombre(), "nombreChofer");
	}
	
}
