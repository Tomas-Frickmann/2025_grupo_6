package testingDatos;

import static org.junit.Assert.*;


import org.junit.Test;

import modeloDatos.*;
public class TestCliente {

	

		
	@Test
	public void testConstructorCliente() {
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals("No creo correctamente elñ cliente",cliente.getNombreUsuario(),"nombreUsuario1");
		assertEquals("No creo correctamente elñ cliente",cliente.getPass(),"pass1");
		assertEquals("No creo correctamente elñ cliente",cliente.getNombreReal(),"nombreReal");
	}
	@Test
	public void testgetNombreUsuario(){
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals("No se obtuvo el campo correctamente",cliente.getNombreUsuario(),"nombreUsuario1");		
	}
	@Test
	public void testgetPass(){
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals("No se obtuvo el campo correctamente",cliente.getPass(),"pass1");
	}
	@Test
	public void testgetNombreReal(){
		Cliente cliente = new Cliente ("nombreUsuario1","pass1","nombreReal");
		assertEquals("No se obtuvo el campo correctamente",cliente.getNombreReal(),"nombreReal");
	}
}
