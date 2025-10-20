package testingDatos;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.*;
import util.Constantes;


public class TestAuto {
  
@Test
	public void testauto() {
		Auto auto = new Auto("ABC123",4,true);
		assertEquals("No se creo una auto con la patente correcta ", auto.getPatente(), "ABC123");
		assertEquals("No se creo una auto con 4 plazas", auto.getCantidadPlazas(), 4);		
		assertTrue("No se creo una auto que acepta mascota", auto.isMascota());
		
	}
	
    @Test
    public void getPatenteTest(){
    	Auto auto = new Auto("ABC123",4,true);
        assertEquals("No se obtuvo el campo correctamente",auto.getPatente(), "ABC123");
    }

    @Test
    public void getCantidadPlazasTest(){
    	Auto auto = new Auto("ABC123",4,true);
    	assertEquals("No se obtuvo el campo correctamente",auto.getCantidadPlazas(), 4);
    }

    @Test
    public void isMascotaTest(){
    	Auto auto = new Auto("ABC123",4,true);
        assertEquals("No se obtuvo el campo correctamente",auto.isMascota(), true);
    }

    @Test
    public void testgetPuntajePedidoConBaul(){
    	Auto auto = new Auto("ABC123",4,true);
    	Cliente cliente = new Cliente("Nombre","12345678", "NombreReal"); 
    	Pedido pedidoBaul = new Pedido(cliente, 3, false, true, 20, Constantes.ZONA_STANDARD);
    	
        assertEquals("No se calcula el puntaje correctamente",auto.getPuntajePedido(pedidoBaul), Integer.valueOf(40 * pedidoBaul.getCantidadPasajeros()));
        //si solicita uso de baul, valor = 40 * cantPasajeros
    }  

    @Test
    public void testgetPuntajePedidoSinBaul(){        	
    	Auto auto = new Auto("ABC123",4,true);
    	Cliente cliente = new Cliente("Nombre","12345678", "NombreReal"); 
    	Pedido pedidoSinBaul = new Pedido(cliente, 4, false, false, 20, Constantes.ZONA_STANDARD);
    	assertEquals("No se calcula el puntaje correctamente",auto.getPuntajePedido(pedidoSinBaul), Integer.valueOf(30 * pedidoSinBaul.getCantidadPasajeros()));
    } 
    
    @Test
    public void testgetPuntajePedidosPasajerosMalPax(){
    	Auto auto = new Auto("ABC123",4,true);
    	Cliente cliente = new Cliente("Nombre","12345678", "NombreReal"); 
    	Pedido pedidoMalPax = new Pedido(cliente, 6, false, false, 20, Constantes.ZONA_STANDARD);
            assertEquals("No debe asignar pedido",auto.getPuntajePedido(pedidoMalPax), null);
    } 
    //Dudoso, terminar.
    @Test
public void testgetPuntajePedidosConMascota(){
    	Auto auto = new Auto("ABC123",4,false);
    	Cliente cliente = new Cliente("Nombre","12345678", "NombreReal"); 
    	Pedido pedidoMalPax = new Pedido(cliente, 4, true, true, 20, Constantes.ZONA_STANDARD);
        assertNull("No debe asignar pedido",auto.getPuntajePedido(pedidoMalPax));
    } 
    
}

