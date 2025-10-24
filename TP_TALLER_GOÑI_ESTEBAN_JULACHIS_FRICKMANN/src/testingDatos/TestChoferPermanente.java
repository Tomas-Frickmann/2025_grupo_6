package testingDatos;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;


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
	public void testConstructor_con_fecha3000() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 3000, 2);
		assertEquals("Problema en el dni", c1.getDni(), "123456");
		assertEquals("Problema en el nombre", c1.getNombre(), "nombreChofer");
		assertEquals("Problema en el anio", c1.getAnioIngreso(), 3000);
		assertEquals("Problema en la cantidad de hijos", c1.getCantidadHijos(), 2);
		
	}
	@Test
	public void testConstructor_con_fecha1900() {
	ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 1900, 2);
	assertEquals("Problema en el dni", c1.getDni(), "123456");
	assertEquals("Problema en el nombre", c1.getNombre(), "nombreChofer");
	assertEquals("Problema en el anio", c1.getAnioIngreso(), 1900);
	assertEquals("Problema en la cantidad de hijos", c1.getCantidadHijos(), 2);
	
}
	@Test
	public void testgetAnioIngreso() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("No se obtuvo el campo correctamente",c1.getAnioIngreso(), 2024);
	}
	
	@Test
	public void testgetAntiguedad() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		assertEquals("No se obtuvo el campo correctamente",c1.getAntiguedad(), anioActual - c1.getAnioIngreso());
	}
	
	@Test
	public void testgetCantidadHijos() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 0);
		c1.setCantidadHijos(2);
		assertEquals("No se obtuvo el campo correctamente",c1.getCantidadHijos(), 2);
	}
	
	@Test
	public void testgetSueldoBruto_con_antiguedadmenor_a_20() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 0);
		int antiguedad;
		
		ChoferPermanente.setSueldoBasico(1000);
		if (c1.getAntiguedad() <= 20){
			antiguedad = c1.getAntiguedad();			
		}
		else {
			antiguedad = 20;
		}
		double sueldoBruto = ChoferPermanente.getSueldoBasico() + 0.05*(antiguedad)*ChoferPermanente.getSueldoBasico() + 0.07*c1.getCantidadHijos()*ChoferPermanente.getSueldoBasico();
		
		
		assertEquals("Calcula mal el sueldo bruto",Double.valueOf(c1.getSueldoBruto()), Double.valueOf(sueldoBruto));
	}
	@Test
	public void testgetSueldoBruto_con_antiguedad_mayor_a_20() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 1980, 0);
		int antiguedad;
		
		ChoferPermanente.setSueldoBasico(1000);
		if (c1.getAntiguedad() <= 20){
			antiguedad = c1.getAntiguedad();			
		}
		else {
			antiguedad = 20;
		}
		
		
		double sueldoBruto = ChoferPermanente.getSueldoBasico() + 0.05*(antiguedad)*ChoferPermanente.getSueldoBasico() + 0.07*c1.getCantidadHijos()*ChoferPermanente.getSueldoBasico();
		
		
		assertEquals("Calcula mal el sueldo bruto",Double.valueOf(c1.getSueldoBruto()), Double.valueOf(sueldoBruto));
	}
	@Test
	public void testgetSueldoBruto_con_hijos() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		int antiguedad;
		
		ChoferPermanente.setSueldoBasico(1000);
		if (c1.getAntiguedad() <= 20){
			antiguedad = c1.getAntiguedad();			
		}
		else {
			antiguedad = 20;
		}
		double sueldoBruto = ChoferPermanente.getSueldoBasico() + 0.05*(antiguedad)*ChoferPermanente.getSueldoBasico() + 0.07*c1.getCantidadHijos()*ChoferPermanente.getSueldoBasico();
		
		
		assertEquals("Calcula mal el sueldo bruto",Double.valueOf(c1.getSueldoBruto()), Double.valueOf(sueldoBruto));
	}
	@Test
	public void testgetSueldoBasico() {
		double sueldo = 1000.0;
		
		ChoferPermanente.setSueldoBasico(sueldo);
		
		assertEquals(Double.valueOf(ChoferPermanente.getSueldoBasico()), Double.valueOf(sueldo) );
	}
	
	@Test
	public void testgetSueldoNeto() {
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		ChoferPermanente.setSueldoBasico(1000);
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
