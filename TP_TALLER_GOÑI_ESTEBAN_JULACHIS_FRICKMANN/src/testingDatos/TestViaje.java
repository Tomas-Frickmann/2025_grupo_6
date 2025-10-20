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
	@Test
	public void testgetCalifcacion() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		v.finalizarViaje(5);
		
		assertEquals(5.0,v.getCalificacion(),0.0001);
	}
	@Test
	public void testgetChofer(){
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
		
		assertEquals("No se obtuvo bien el atributo",c1,v.getChofer());
		
	}
	@Test
	public void testgetVehiculo() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		

		assertEquals("No se obtuvo bien el atributo",combi,v.getVehiculo());
		
	}
	@Test
		public void testisFinalizado() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		
	
		assertFalse("No se obtuvo bien el atributo",v.isFinalizado());
	}
		@Test
		public void testgetValorBase() {
		
		Viaje.setValorBase(1000.0);
		assertEquals("No se obtuvo bien el atributo",1000.0,Viaje.getValorBase(),0.0001);
	
		}
		@Test
		public void testFinalizarViaje() {
		Cliente cliente = new Cliente("nombre","pass","nombreReal");
		Pedido ped = new Pedido(cliente, 4, false, true, 20, Constantes.ZONA_STANDARD);
		Combi combi = new Combi("AA000FF",10, true);
		ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
		Viaje v = new Viaje(ped,c1,combi);
		v.finalizarViaje(5);
	
		assertTrue("No se obtuvo bien el atributo",v.isFinalizado());
	}
		
		@Test
	    public void testgetValor_Estandar() {
	       
	        double valorEsperado = 1300.0;
	        Viaje.setValorBase(1000.0);
	        Cliente cliente = new Cliente("nombre","pass","nombreReal");
			Pedido ped = new Pedido(cliente, 2, false, false, 1, Constantes.ZONA_STANDARD);
			Auto auto = new Auto("AA000FF",4, true);
			ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
			Viaje v = new Viaje(ped,c1,auto);
	        
	        assertEquals("Se calcula mal el valor del viaje",valorEsperado,v.getValor(), 0.0001); 
	    }
		@Test
	    public void testgetValor_SIN_ASFALTAR() {
	       
	        double valorEsperado = 1550.0;
	        Viaje.setValorBase(1000.0);
	        Cliente cliente = new Cliente("nombre","pass","nombreReal");
			Pedido ped = new Pedido(cliente, 2, false, false, 1, Constantes.ZONA_SIN_ASFALTAR);
			Auto auto = new Auto("AA000FF",4, true);
			ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
			Viaje v = new Viaje(ped,c1,auto);
	        
	        assertEquals("Se calcula mal el valor del viaje",valorEsperado,v.getValor(), 0.0001); 
	    }
		@Test
	    public void testgetValor_Peligrosa() {
	       
	        double valorEsperado = 1400.0;
	        Viaje.setValorBase(1000.0);
	        Cliente cliente = new Cliente("nombre","pass","nombreReal");
			Pedido ped = new Pedido(cliente, 2, false, false, 1, Constantes.ZONA_PELIGROSA);
			Auto auto = new Auto("AA000FF",4, true);
			ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
			Viaje v = new Viaje(ped,c1,auto);
	        
	        assertEquals("Se calcula mal el valor del viaje",valorEsperado,v.getValor(), 0.0001); 
	    }
		@Test
	    public void testgetValor_con_Mascota() {
	       
	        double valorEsperado = 1950.0;
	        Viaje.setValorBase(1000.0);
	        Cliente cliente = new Cliente("nombre","pass","nombreReal");
			Pedido ped = new Pedido(cliente, 2, true, false, 1, Constantes.ZONA_SIN_ASFALTAR);
			Auto auto = new Auto("AA000FF",4, true);
			ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
			Viaje v = new Viaje(ped,c1,auto);
	       
	        assertEquals("Se calcula mal el valor del viaje",valorEsperado,v.getValor(), 0.0001); 
	    }
		@Test
	    public void testgetValor_con_Baul() {
	       
	        double valorEsperado = 1800.0;
	        Viaje.setValorBase(1000.0);
	        Cliente cliente = new Cliente("nombre","pass","nombreReal");
			Pedido ped = new Pedido(cliente, 2, false, true, 1, Constantes.ZONA_SIN_ASFALTAR);
			Auto auto = new Auto("AA000FF",4, true);
			ChoferPermanente c1 = new ChoferPermanente("123456", "nombreChofer", 2024, 2);
			Viaje v = new Viaje(ped,c1,auto);
	       
	        assertEquals("Se calcula mal el valor del viaje",valorEsperado,v.getValor(), 0.0001); 
	    }
  }

