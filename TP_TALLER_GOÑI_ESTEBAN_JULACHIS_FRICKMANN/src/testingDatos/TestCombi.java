package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import util.Constantes;
public class TestCombi {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCombi() {
		Combi combi = new Combi("AA000FF",7, true);
		assertEquals("No se creo una combi con la patente correcta ", combi.getPatente(), "AA000FF");
		assertEquals("No se creo una combi con 7 plazas", combi.getCantidadPlazas(), 7);		
		assertTrue("No se creo una combi que acepta mascota", combi.isMascota());
		
	}
	@Test
	public void testCombiCon5Plazas() {
		Combi combi = new Combi("AA000FF",5, true);
		assertEquals("No se creo una combi con la patente correcta ", combi.getPatente(), "AA000FF");
		assertEquals("No se creo una combi con 5 plazas", combi.getCantidadPlazas(), 5);		
		assertTrue("No se creo una combi que acepta mascota", combi.isMascota());
		
	}
	@Test
	public void testCombiCon10Plazas() {
		Combi combi = new Combi("AA000FF",10, false);
		assertEquals("No se creo una combi con la patente correcta ", combi.getPatente(), "AA000FF");
		assertEquals("No se creo una combi con 10 plazas", combi.getCantidadPlazas(), 10);		
		assertFalse("No se creo una combi que acepta mascota", combi.isMascota());
		
	}
	@Test
	public void testCombiConPatenteVacia() {
		Combi combi = new Combi("",5, true);
		assertEquals("No se creo una combi con la patente correcta ", combi.getPatente(), "");
		assertEquals("No se creo una combi con 5 plazas", combi.getCantidadPlazas(), 5);		
		assertTrue("No se creo una combi que acepta mascota", combi.isMascota());
		
	}
	
	@Test
	public void testgetCantidaddePlazas(){
		
		Combi combi = new Combi("AA000FF",7, true);
		assertEquals("No se obtuvo la cantidad de plazas correcta", combi.getCantidadPlazas(), 7);
	}
	
	@Test
	public void testIsMascota(){
		
		Combi combi = new Combi("AA000FF",7, true);
		assertTrue("No se obtuvo el valor correcto de si acepta mascotas", combi.isMascota());

	}
	
	@Test
	public void testgetPatente(){
		
		Combi combi = new Combi("AA000FF",7, true);
		assertEquals("No se obtuvo la patente correcta", combi.getPatente(), "AA000FF");

	}
	
	@Test
	public void testPuntajePedido5Personas(){
		Combi combi = new Combi("AA123BB", 5, true); //1° mascota 2° baul
		Pedido pedido = new Pedido(new Cliente("Octi", "1234","Octavio"), 5, false, false, 3, Constantes.ZONA_STANDARD);
		 assertEquals("Puntaje mal calculado",combi.getPuntajePedido(pedido), Integer.valueOf(10*pedido.getCantidadPasajeros()));
		
	}
@Test
	public void testPuntajePedido10Personas(){
		Combi combi = new Combi("AA123BB", 10, true); //1° mascota 2° baul
		Pedido pedido = new Pedido(new Cliente("Octi", "1234","Octavio"), 10, false, false, 3, Constantes.ZONA_STANDARD);
		 assertEquals("Puntaje mal calculado",combi.getPuntajePedido(pedido), Integer.valueOf(10*pedido.getCantidadPasajeros()));
		
	}
	@Test
	public void testPuntajePedidoConbaul(){
		Combi combi = new Combi("AA123BB", 10, true); //1° mascota 2° baul
		Pedido pedido = new Pedido(new Cliente("Octi", "1234","Octavio"), 9, false, true, 3, Constantes.ZONA_STANDARD);
		System.out.println(combi.getPuntajePedido(pedido));
		 assertEquals("Puntaje mal calculado",combi.getPuntajePedido(pedido), Integer.valueOf(10*pedido.getCantidadPasajeros()+100));
		
	}
	
	@Test
	public void testPuntajePedidoConMasPersonasQuePlazas(){
		Combi combi = new Combi("AA123BB", 5, true); //1° mascota 2° baul
		Pedido pedido = new Pedido(new Cliente("Octi", "1234","Octavio"), 6, false, false, 3, Constantes.ZONA_STANDARD);
		assertNull("No deberia calcular puntaje",combi.getPuntajePedido(pedido));
		
	}
	@Test
	public void testPuntajePedidoCon3Personas(){
		Combi combi = new Combi("AA123BB", 5, true); //1° mascota 2° baul
		Pedido pedido = new Pedido(new Cliente("Octi", "1234","Octavio"), 3, false, false, 3, Constantes.ZONA_STANDARD);
		assertNull("No deberia calcular puntaje",combi.getPuntajePedido(pedido));
		
	}
	@Test
	public void testPuntajePedidoconMascota(){
		Combi combi = new Combi("AA123BB", 5, true); //1° mascota 2° baul
		Pedido pedido = new Pedido(new Cliente("Octi", "1234","Octavio"), 3, true, false, 3, Constantes.ZONA_STANDARD);
		assertNull("No deberia calcular puntaje",combi.getPuntajePedido(pedido));
		
	}
}
