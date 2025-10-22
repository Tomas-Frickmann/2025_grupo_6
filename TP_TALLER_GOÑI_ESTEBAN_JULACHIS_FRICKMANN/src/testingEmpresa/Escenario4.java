package testingEmpresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;


public class Escenario4 {
	Empresa empresa;

	@Before
	public void setUp() throws Exception {
		this.escenario4();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void escenario4(){
		this.empresa = Empresa.getInstance();
		this.empresa.setChoferes(new HashMap <String,Chofer>());
		this.empresa.setChoferesDesocupados(new ArrayList <Chofer>());
		this.empresa.setClientes(new HashMap <String,Cliente>());
		this.empresa.setPedidos(new HashMap <Cliente,Pedido>());
		this.empresa.setVehiculos(new HashMap <String,Vehiculo>());
		this.empresa.setVehiculosDesocupados(new ArrayList <Vehiculo>());
		this.empresa.setViajesIniciados(new HashMap <Cliente,Viaje>());
		this.empresa.setViajesTerminados(new ArrayList <Viaje>());	
		
		try {
			this.empresa.agregarCliente("Usuario1", "12345678", "NombreReal1");
			this.empresa.agregarCliente("Usuario2", "12345677", "nombreRealCliente2");
			
			this.empresa.agregarChofer(new ChoferPermanente("22222222","nombreRealChofer1",2020,4));
			this.empresa.agregarVehiculo(new Auto("BBB111",4,false));
			this.empresa.agregarVehiculo(new Moto("BBB222"));
			
			//Todas las variables creadas hacelo como atributo
			
			Cliente cliente1 = this.empresa.getClientes().get("Usuario1");
			Cliente cliente2 = this.empresa.getClientes().get("Usuario2");
			this.empresa.agregarPedido(new Pedido(cliente2, 4, false, false, 1, Constantes.ZONA_STANDARD));
			
			this.empresa.agregarPedido(new Pedido(cliente1, 4, false, false, 1, Constantes.ZONA_STANDARD)); 
			
			Pedido p = this.empresa.getPedidoDeCliente(cliente2);
			Chofer c = this.empresa.getChoferesDesocupados().get(0);
			Vehiculo a = this.empresa.getVehiculosDesocupados().get(0);
			
			this.empresa.crearViaje(p, c, a);
			
			
			
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}
	
	
	@Test
	public void testAgregarPedidoSobrante() {
	Cliente cliente= this.empresa.getClientes().get("Usuario1");
	Pedido pedido=new Pedido(cliente, 4, false, false, 1, Constantes.ZONA_STANDARD);
  try {
	this.empresa.agregarPedido(pedido);
	fail("Debería lanzar ClienteConPedidoPendienteException");	
	}
	catch(excepciones.ClienteNoExisteException e) {
		fail("ClienteNoExisteException");
	}
	catch(excepciones.ClienteConViajePendienteException e) {
		fail("ClienteConViajePendienteException");
	}
	catch(excepciones.ClienteConPedidoPendienteException e) {
		assertTrue(Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(), this.empresa.getPedidoDeCliente(cliente) != null );
	}
	catch(excepciones.SinVehiculoParaPedidoException e) {
		fail("SinVehiculoParaPedidoException");
	}
	
}

	@Test 
	public void testcalificacionDeChofer(){
		
		Chofer chofer= this.empresa.getChoferes().get("22222222");
		
		try{
			this.empresa.login("Usuario2", "12345677");
			this.empresa.pagarYFinalizarViaje(4);
			double calificaciones = this.empresa.calificacionDeChofer(chofer);
			//Le cree un pedido a cada usuario, son ambos pedidos iguales, un usuario lo tiene como viaje, por lo visto pagaryfinalizar no lo hace correctamente
			//loguee al usuario con el viaje y pagué el viaje pero el promedio (7/1) no resulta. 
			
			assertTrue("Cálculo correcto", calificaciones == this.empresa.getViajesTerminados().get(0).getCalificacion()/this.empresa.getViajesTerminados().size());
		}
		catch(excepciones.SinViajesException e){
			fail("Deberia lanzar SinViajesException");
		}
		catch(Exception e) {
			fail("Deberia lanzar otra excepcion");
		}
	}
	@Test 
	public void testcalificacionDeChofer_sin_viajes(){
		
		Chofer chofer= this.empresa.getChoferes().get("22222222");
		
		try{
			
			double calificaciones = this.empresa.calificacionDeChofer(chofer);
			
			fail("Deberia lanzar SinViajesException");
			
		}
		catch(excepciones.SinViajesException e){
			assertEquals("Debio lanzar el siguiente mensaje"+ Mensajes.CHOFER_SIN_VIAJES.getValor(),Mensajes.CHOFER_SIN_VIAJES.getValor(), e.getMessage());
		}
		
	}
    @Test
    public void testCrearViaje() {
    	try {
    		this.empresa.agregarChofer(new ChoferPermanente("33333","nombreRealChofer1",2020,4)); //Chofer nuevo 
    		Pedido p = this.empresa.getPedidoDeCliente(this.empresa.getClientes().get("Usuario1")); //Pedido del cliente1
			Chofer c = this.empresa.getChoferes().get(0);
			Vehiculo a = this.empresa.getVehiculos().get(1);
			this.empresa.crearViaje(p, c, a);
			
			fail("Deberia lanzar ChoferNoDisponibleException");
    	}
    	catch(excepciones.ChoferNoDisponibleException e) {
			assertTrue("Chofer correctamente no disponible", true);
		}
    	catch(Exception e) {
    		fail("Deberia lanzar ChoferNoDisponibleException");
    	}
    	
    	
    }

    @Test
    public void testPagarYFinalizarViaje() {
    	try{
			this.empresa.login("Usuario2", "12345677");
			this.empresa.pagarYFinalizarViaje(7);
			assertTrue("Viaje finalizado correctamente", this.empresa.getViajesTerminados().get(0).getCalificacion() == 7);
    	}
		catch(Exception e) {
			fail("No deberia lanzar excepcion");
		}
    }
    

    @Test
    public void testLoginUsuario() {
    	this.empresa.setUsuarioLogeado(this.empresa.getClientes().get("Usuario2"));
    	Usuario u = this.empresa.getUsuarioLogeado();
    	assertTrue("Usuario logueado correctamente", u.getNombreUsuario().equals("Usuario2"));
    }

    @Test
    public void testLogout() {
    	this.empresa.logout();
		assertTrue("Usuario deslogueado correctamente", this.empresa.getUsuarioLogeado() == null);
    }
}

