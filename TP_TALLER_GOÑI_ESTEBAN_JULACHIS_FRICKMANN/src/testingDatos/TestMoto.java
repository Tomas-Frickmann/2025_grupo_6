package testingDatos;

import static org.junit.Assert.*;


import org.junit.Test;


import modeloDatos.*;

import util.Constantes;

public class TestMoto {
	Moto moto= new Moto("Patente2");
	
	

	
	
	@Test 
	public void ConstructorMoto(){
		Moto moto= new Moto("Patente2");	
		assertEquals("No se creo una moto con la patente correcta ",moto.getPatente(), "Patente2");
		assertEquals("No se creo una moto con 1 PLAZA",moto.getCantidadPlazas(), 1);
	    assertEquals("No se creo una moto que no acepta mascota",moto.isMascota(), false);
	}
	
	
	@Test
	public void ConstructorMotoVacia(){
		Moto moto= new Moto("");	
		assertEquals("No se creo una moto con la patente correcta ",moto.getPatente(), "");
		assertEquals("No se creo una moto con 1 PLAZA",moto.getCantidadPlazas(), 1);
	    assertEquals("No se creo una moto que no acepta mascota",moto.isMascota(), false);
	}
	
	
	@Test	
	public void getPatenteTest(){
		Moto moto= new Moto("Patente2");
    	assertEquals("No se obtuvo correctamente el campo",moto.getPatente(), "Patente2");
        
    }

	@Test
	public void getCantidadPlazasTest(){
		Moto moto= new Moto("Patente2");
        assertEquals("No se obtuvo correctamente el campo",moto.getCantidadPlazas(), 1);
    }

	@Test
    public void isMascotaTest(){
		Moto moto= new Moto("Patente2");
    	assertEquals("No se obtuvo correctamente el campo",moto.isMascota(), false);
  
    }
    
    @Test
    public void testgetPuntajePedidoMoto(){
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido= new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_STANDARD);
		assertEquals("Puntaje mal calculado",Integer.valueOf(1000),moto.getPuntajePedido(pedido));		
	}
	
	@Test
	public void testgetPuntajePedidoMotoMasPasajeros(){
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido_mas_pasajeros= new Pedido(cliente,2,false,false,10,Constantes.ZONA_STANDARD);
		assertNull("No deberia calcular puntaje",moto.getPuntajePedido(pedido_mas_pasajeros));		
	}
	
	@Test
	public void testgetPuntajePedidoMotoConMascota(){ 
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido_con_mascota= new Pedido (cliente,1,true,false,10,Constantes.ZONA_STANDARD);
		assertNull("No deberia calcular puntaje",moto.getPuntajePedido(pedido_con_mascota));			
	}
	
	@Test
	public void testgetPuntajePedidoMotoConBaul(){ 
		Moto moto= new Moto("Patente2");
		Cliente cliente = new Cliente("Nombre","12345678", "NombreReal");
		Pedido pedido_con_Baul= new Pedido (cliente,1,false,true,10,Constantes.ZONA_STANDARD);
		assertNull("No deberia calcular puntaje",moto.getPuntajePedido(pedido_con_Baul));			
	}
	


}
