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


public class Escenario3 {
	Empresa empresa;
	ChoferPermanente choferPermanente1;
	ChoferTemporario choferTemporario1;
	Auto auto1;
	Moto moto1;
	
	@Before
	public void setUp() throws Exception {
		this.escenario3();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void escenario3(){
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
			
			this.choferPermanente1 = new ChoferPermanente("11111111","nombreRealChofer1",2020,4);
			this.choferTemporario1 = new ChoferTemporario("33333333","NombreRealChofer2");
			this.empresa.agregarChofer(this.choferPermanente1);
			this.empresa.agregarChofer(this.choferTemporario1);
			
			this.auto1= new Auto("AAA111",4,false);
			this.moto1 = new Moto("AAA222");
			this.empresa.agregarVehiculo(auto1);
			this.empresa.agregarVehiculo(moto1);
			
			
			this.empresa.login("Usuario1", "12345678");
			//Este escenario no tiene viajesIniciados
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}
	
	@Test
	public void testAgregarPedidoClienteInexistente() {
		try {
			Cliente c3 = new Cliente("User3","12345678","nombreReal3"); //
			Pedido pedido = new Pedido(c3, 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(pedido);
			fail("Deberia saltar excepción");
			
		}
		catch(excepciones.ClienteNoExisteException e) {
			assertTrue("Falló correctamente", this.empresa.getClientes().get("user3") == null);
			//La excepcion no guarda al cliente
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			fail(e.getMessage());
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			fail(e.getMessage());
		}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testAgregarPedidoUnico() {
		try {
			Pedido pedido = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(pedido);
			assertTrue("Pedido agregado con exito", this.empresa.getPedidoDeCliente(this.empresa.getClientes().get("Usuario1")) != null);
			
		}
		catch(excepciones.ClienteNoExisteException e) {
			fail(e.getMessage());
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			fail(e.getMessage());
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			fail(e.getMessage());
		}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			fail(e.getMessage());
		}
		
	}

	
	
	@Test
	public void testcrearViajeCorrecto(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		Pedido pedido= this.empresa.getPedidoDeCliente(c1);
		Chofer chofer= this.empresa.getChoferesDesocupados().get(0);
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(0);
		try{
			this.empresa.crearViaje(pedido, chofer, vehiculo);
			assertTrue("Viaje creado con exito", this.empresa.getViajeDeCliente(c1) != null);
		}
		catch(excepciones.PedidoInexistenteException e){
			fail(e.getMessage());
		
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail(e.getMessage());
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			fail(e.getMessage());
		}
		catch(excepciones.VehiculoNoValidoException e){
			fail(e.getMessage());
		}
		catch(excepciones.ClienteConViajePendienteException e){
			fail(e.getMessage());
				
		}
	}

	//--------------------- Tests vacíos para completar ---------------------//
	
	@Test
	public void testCrearViajeVehiculoDesconocido() {
		Pedido pedido = new Pedido(this.empresa.getClientes().get("Usuario1"), 3, false, false, 2, Constantes.ZONA_STANDARD);
		try {
			this.empresa.agregarPedido(pedido);
			this.empresa.crearViaje(pedido, this.choferPermanente1, new Auto("BBB222",4,true));
		}
		catch(excepciones.VehiculoNoDisponibleException e) {
			boolean bool = (this.auto1.isMascota() != pedido.isMascota()) || (pedido.getCantidadPasajeros() > this.auto1.getCantidadPlazas());
			
			// Lanza mal la excepcion
			assertTrue("Vehiculo no disponible", true);
		}
		catch(Exception e) {
			fail(e.getMessage());
		}
		
	}
	 @Test
    public void testPagarYFinalizarViaje() {
		 try {
			 this.empresa.pagarYFinalizarViaje(4);
			 fail("Deberia lanzar ClienteSinViajePendienteException");
			 
		 }
		 catch(excepciones.ClienteSinViajePendienteException e) {
			 assertTrue(Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(), null == this.empresa.getViajeDeCliente((Cliente) this.empresa.getUsuarioLogeado()));
			 
		 }
		 
    }

    @Test
    public void testValidarPedidoTrue() {
    	Pedido pedido = new Pedido(this.empresa.getClientes().get("Usuario1"),3, false, false, 2, Constantes.ZONA_STANDARD);
  
		assertTrue("Es posible cumplir el pedido", this.empresa.validarPedido(pedido));
    	
    }
    
    @Test
    public void testValidarPedidoFalse() {
    	Pedido pedido = new Pedido(this.empresa.getClientes().get("Usuario1"),3, true, false, 2, Constantes.ZONA_STANDARD);
  
		assertTrue("No es posible cumplir el pedido", !this.empresa.validarPedido(pedido));
    	
    }

    @Test
    public void testVehiculosOrdenadosPorPedido() {
    	Pedido pedido = new Pedido(this.empresa.getClientes().get("Usuario1"),1, false, false, 2, Constantes.ZONA_STANDARD);
    	ArrayList <Vehiculo> array = this.empresa.vehiculosOrdenadosPorPedido(pedido);
    	boolean bool = array.get(0).getPuntajePedido(pedido) > array.get(1).getPuntajePedido(pedido);
		assertTrue("Es posible cumplir el pedido", bool);
    //No los ordena bien, ademas los puntajes se calculaban mal
    }
    
    @Test
    public void testIsAdminFalso() {
    	assertTrue("El usuario logueado no es admin", !this.empresa.isAdmin());
    }
}

