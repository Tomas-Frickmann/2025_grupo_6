package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import util.Constantes;

public class TestPedido {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPedido() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en cliente", cliente, ped.getCliente());
		assertEquals("Problema en cantidad pasajeros",ped.getCantidadPasajeros(), 4);
		assertEquals("Problema en mascota",ped.isMascota(), false);
		assertEquals("Problema en baul",ped.isBaul(), true);
		assertEquals("Problema en km",ped.getKm(), 20);
		assertEquals("Problema en zona",ped.getZona(), Constantes.ZONA_STANDARD);
	}
	
	@Test
	public void testgetCliente() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en cliente", cliente, ped.getCliente());
	}
	
	@Test
	public void testgetCantPasajeros() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en cantidad pasajeros",ped.getCantidadPasajeros(), 4);
	}
	
	@Test
	public void testIsMascota() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en mascota",ped.isMascota(), false);
	}
	
	@Test
	public void testIsBaul() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en baul",ped.isBaul(), true);
	}
	@Test
	public void testgetKmPedido() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en km",ped.getKm(), 20);
		
	}
	
	@Test
	public void testgetZona() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		
		assertEquals("Problema en zona",ped.getZona(), Constantes.ZONA_STANDARD);
	}
}

