package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import util.Constantes;
public class TestViaje {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVieaje() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
		assertEquals(ped,v.getPedido());
		assertEquals(c1,v.getChofer());
		assertEquals(combi,v.getVehiculo());
		assertEquals(false,v.isFinalizado());
		assertEquals(0.0,v.getCalificacion(),0.0001);
	}

	public void testgetCalifcacion() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
		
		assertEquals(0.0,v.getCalificacion(),0.0001);
	}
	public void testgetChofer(){
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
		
		assertEquals(c1,v.getChofer());
		
	}
	
	public void testgetVehiculo() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		

		assertEquals(combi,v.getVehiculo());
		
	}
		public void testisFinalizado() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
	
		assertFalse(v.isFinalizado());
		
	/*public void testgetValor(){
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
		assertEquals(ped,v.getPedido());
		assertEquals(c1,v.getChofer());
		assertEquals(combi,v.getVehiculo());
		assertEquals(false,v.isFinalizado());
		assertEquals(0.0,v.getCalificacion(),0.0001);
	}*/
  }
}
