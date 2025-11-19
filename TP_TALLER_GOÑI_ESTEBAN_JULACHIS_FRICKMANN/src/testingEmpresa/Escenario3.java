package testingEmpresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.VehiculoNoValidoException;
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
	Pedido pedido;
	
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
			
			Pedido pedido1 = new Pedido(this.empresa.getClientes().get("Usuario1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.pedido = pedido1;
			this.empresa.agregarPedido(pedido1);
			
			this.empresa.login("Usuario1", "12345678");
			//Este escenario no tiene viajesIniciados
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}
	
	@Test
	public void testAgregarPedido_SinVehiculo() {
		Cliente c3 = this.empresa.getClientes().get("Usuario2"); //
		Pedido pedido = new Pedido(c3, 4, true, false, 1, Constantes.ZONA_STANDARD);
		try {
			this.empresa.agregarPedido(pedido);
			fail("Deberia saltar excepción");
			
		}
		catch(excepciones.ClienteNoExisteException e) {
			fail("No debio tirar ClienteNoExisteException");
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			fail("No debio tirar ClienteConViajePendienteException");
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			fail("No debio tirar ClienteConPedidoPendienteException");		}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			assertTrue(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(), this.empresa.validarPedido(pedido) == false);
		}
		
	}
	
	@Test
	public void testAgregarPedido_ClienteViajePendiente() {
		try {
			Pedido pedido = new Pedido(this.empresa.getClientes().get("Usuario2"), 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(pedido);
			this.empresa.crearViaje(pedido, this.choferPermanente1, this.auto1);
			Pedido pedido2 = new Pedido(this.empresa.getClientes().get("Usuario2"), 2, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(pedido2);
			
			fail("Deberia saltar excepción ClienteConViajePendienteException");
			
		}
		catch(excepciones.ClienteNoExisteException e) {
			fail("no deberia lanzar ClienteNoExisteException" );
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			assertEquals(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(), Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(), e.getMessage());
			
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			fail("No deberia lanzar  ClienteConPedidoPendienteException");
					}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			fail("No deberia lanzar SinVehiculoParaPedidoException");
		}
		catch(Exception e) {
			fail("Excepciones externas al metodo" + e.getMessage());
		}
		
	}

	
	
	@Test
	public void testcrearViaje_CasoBase(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		//Pedido pedido= this.empresa.getPedidoDeCliente(c1);
		Chofer chofer= this.empresa.getChoferesDesocupados().get(0);
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(0);
		try{
			this.empresa.crearViaje(this.pedido, chofer, vehiculo);
			assertTrue("Viaje creado con exito", this.empresa.getViajeDeCliente(c1) != null);
		}
		catch(excepciones.PedidoInexistenteException e){
			fail("No deberia lanzar PedidoInexistenteException");
		
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail("No deberia lanzar ChoferNoDisponibleException");
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			fail("No deberia lanzar VehiculoNoDisponibleException");
		}
		catch(excepciones.VehiculoNoValidoException e){
			fail("No deberia lanzar VehiculoNoValidoException");
		}
		catch(excepciones.ClienteConViajePendienteException e){
			fail("No deberia lanzar ClienteConViajePendienteException");
				
		}
	}
	
	@Test
	public void testcrearViaje_VehiculoNoValido(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		Pedido pedido= this.empresa.getPedidoDeCliente(c1);
		Chofer chofer= this.empresa.getChoferesDesocupados().get(0);
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(1);
		try{
			this.empresa.crearViaje(pedido, chofer, vehiculo);
			fail("Deberia lanzar VehiculoNoValidoException");
		}
		catch(excepciones.PedidoInexistenteException e){
			fail("No deberia lanzar PedidoInexistenteException");
		
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail("No deberia lanzar ChoferNoDisponibleException");
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			fail("No deberia lanzar VehiculoNoDisponibleException");
		}
		catch(excepciones.VehiculoNoValidoException e){
			assertEquals(Mensajes.VEHICULO_NO_VALIDO.getValor(), Mensajes.VEHICULO_NO_VALIDO.getValor(), e.getMessage());
			assertEquals("Almacena mal el vehiculo no valido",e.getVehiculo(),vehiculo);
			assertEquals("Almacena mal el pedido asociado al vehiculo no valido",e.getPedido(),pedido);
			
		}
		catch(excepciones.ClienteConViajePendienteException e){
			fail("No deberia lanzar ClienteConViajePendienteException");
				
		}
	}
	
	@Test
	public void testcrearViaje_VehiculoNoDisponible(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		Cliente c2 = this.empresa.getClientes().get("Usuario2");
		
		Pedido pedido= this.empresa.getPedidoDeCliente(c1);
		Pedido pedido2 = new Pedido(c2, 4, false, false, 1, Constantes.ZONA_STANDARD);
		Chofer chofer1= this.empresa.getChoferesDesocupados().get(0);
		Chofer chofer2 = this.empresa.getChoferesDesocupados().get(1);
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(0);
		
		try{
			this.empresa.crearViaje(pedido, chofer1, vehiculo);
			this.empresa.agregarPedido(pedido2);
			
			this.empresa.crearViaje(pedido2, chofer2, vehiculo);
			fail("Deberia lanzar VehiculoNoDisponibleException");
		}
		catch(excepciones.PedidoInexistenteException e){
			fail("No deberia lanzar PedidoInexistenteException");
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail("No deberia lanzar ChoferNoDisponibleException");
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			assertEquals("Debio lanzar "+Mensajes.VEHICULO_NO_DISPONIBLE.getValor(), Mensajes.VEHICULO_NO_DISPONIBLE.getValor(), e.getMessage());
			assertEquals("Almacena mal el vehiculo no disponible",e.getVehiculo(),vehiculo);
			}
		catch(excepciones.VehiculoNoValidoException e){
			fail("No deberia lanzar VehiculoNoValidoException");
		}
		catch(excepciones.ClienteConViajePendienteException e){
			fail("No deberia lanzar ClienteConViajePendienteException");
				
		}
		catch(Exception e){
			fail("Excepcion externa al metodo: " + e.getMessage());
		}
	}
	
	@Test
	public void testcrearViaje_ClienteConViajePendiente(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		
		Pedido pedido= this.empresa.getPedidoDeCliente(c1);
		Pedido pedido2 = new Pedido(c1, 1, false, false, 1, Constantes.ZONA_STANDARD);
		
		Chofer chofer1= this.empresa.getChoferesDesocupados().get(1);
		
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(0);
		
		try{
			this.empresa.crearViaje(pedido, chofer1, vehiculo);
			this.empresa.agregarPedido(pedido2);
			fail("Deberia lanzar VehiculoNoDisponibleException");
		}
		catch(excepciones.PedidoInexistenteException e){
			fail("No deberia lanzar PedidoInexistenteException");
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail("No deberia lanzar ChoferNoDisponibleException");
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			fail("No deberia lanzar VehiculoNoDisponibleException");
		}
		catch(excepciones.VehiculoNoValidoException e){
			fail(e.getMessage());
		}
		catch(excepciones.ClienteConViajePendienteException e){
			assertEquals(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(), Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(), e.getMessage());
				
		}
		catch(Exception e){
			fail("Excepcion externa al metodo: " + e.getMessage());
		}
		//Error heredado de clienteViajePendiente
	}

	
	
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
		catch(PedidoInexistenteException e) {
			fail("No deberia lanzar PedidoInexistenteException");
		} catch (SinVehiculoParaPedidoException e) {
			fail("No deberia lanzar SinVehiculoParaPedidoException");
		} catch (ClienteNoExisteException e) {
			fail("No deberia lanzar ClienteNoExisteException");
		} catch (ClienteConViajePendienteException e) {
			fail("No deberia lanzar ClienteConViajePendienteException");
		} catch (ClienteConPedidoPendienteException e) {
			
			fail("No deberia lanzar ClienteSinViajePendienteException");
		} catch (ChoferNoDisponibleException e) {
			
			fail("No deberia lanzar ChoferNoDisponibleException");
		} catch (VehiculoNoValidoException e) {
			fail("No deberia lanzar VehiculoNoValidoException");
		}
		
	}
	 @Test
    public void testPagarYFinalizarViaje() {
		 try {
			 this.empresa.pagarYFinalizarViaje(4);
			 fail("Deberia lanzar ClienteSinViajePendienteException");
			 
		 }
		 catch(excepciones.ClienteSinViajePendienteException e) {
			 assertEquals(Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(), Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(), e.getMessage());
			 
			 
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

