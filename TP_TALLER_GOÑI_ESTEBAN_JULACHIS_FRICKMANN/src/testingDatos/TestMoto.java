package testingDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Constantes;

public class TestMoto {
	Moto moto= new Moto("Patente2");
	/*Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");*/
	/*Pedido pedido= new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_STANDARD);*/
	/*Pedido pedido_mas_pasajeros= new Pedido(cliente,2,false,false,10,Constantes.ZONA_STANDARD);*/
	/*Pedido pedido_con_mascota= new Pedido (cliente,1,true,false,10,Constantes.ZONA_STANDARD); */
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test 
	public void ConstructorMoto(){
		Moto moto= new Moto("Patente2");	
		assertEquals(moto.getPatente(), "Patente2");
		assertEquals(moto.getCantidadPlazas(), 1);
	    assertEquals(moto.isMascota(), false);	
	}
	
	
	@Test
	public void ConstructorMotoVacia(){
		Moto moto= new Moto("");	
		assertEquals(moto.getPatente(), "");
		assertEquals(moto.getCantidadPlazas(), 1);
	    assertEquals(moto.isMascota(), false);	
	}
	
	
	@Test	
	public void getPatenteTest(){
		Moto moto= new Moto("Patente2");
    	assertEquals(moto.getPatente(), "Patente2");
        
    }

	@Test
	public void getCantidadPlazasTest(){
		Moto moto= new Moto("Patente2");
        assertEquals(moto.getCantidadPlazas(), 1);
    }

	@Test
    public void isMascotaTest(){
		Moto moto= new Moto("Patente2");
    	assertEquals(moto.isMascota(), false);
  
    }
    
    @Test
    public void testgetPuntajePedidoMoto(){
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido= new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_STANDARD);
		assertEquals(Integer.valueOf(1000),moto.getPuntajePedido(pedido));		
	}
	
	@Test
	public void testgetPuntajePedidoMotoMasPasajeros(){
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido_mas_pasajeros= new Pedido(cliente,2,false,false,10,Constantes.ZONA_STANDARD);
		assertNull(moto.getPuntajePedido(pedido_mas_pasajeros));		
	}
	
	@Test
	public void testgetPuntajePedidoMotoConMascota(){ 
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido_con_mascota= new Pedido (cliente,1,true,false,10,Constantes.ZONA_STANDARD);
		assertEquals(false,moto.getPuntajePedido(pedido_con_mascota));			
	}
	
	
	


}
