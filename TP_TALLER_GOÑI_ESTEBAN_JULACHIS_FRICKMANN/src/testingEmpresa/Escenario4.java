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
	Pedido pedidoCliente2, pedidoCliente1;
	ChoferPermanente choferPermanente;
	Auto auto1;
	Moto moto1;

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
			
			this.choferPermanente = new ChoferPermanente("22222222","nombreRealChofer1",2020,4);
			this.empresa.agregarChofer(this.choferPermanente);
			this.auto1 = new Auto("BBB111",4,false);
			this.moto1 = new Moto("BBB222");
			
			this.empresa.agregarVehiculo(this.auto1);
			this.empresa.agregarVehiculo(this.moto1);
			
			
			Cliente cliente1 = this.empresa.getClientes().get("Usuario1");
			Cliente cliente2 = this.empresa.getClientes().get("Usuario2");
			
			
			this.pedidoCliente2 = new Pedido(cliente2, 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedidoCliente2);
			this.pedidoCliente1 = new Pedido(cliente1, 4, false, false, 1, Constantes.ZONA_STANDARD);
			this.empresa.agregarPedido(this.pedidoCliente1); 
			
			
			this.empresa.crearViaje(this.pedidoCliente2, this.choferPermanente, this.auto1);
			
			
			
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}
	
	
	@Test
	public void testAgregar_ClienteViajePendiente() {
	Cliente cliente= this.empresa.getClientes().get("Usuario1");
	Pedido pedido=new Pedido(cliente, 4, false, false, 1, Constantes.ZONA_STANDARD);
  try {
	this.empresa.agregarPedido(pedido);
	fail("Debería lanzar ClienteConPedidoPendienteException");	
	}
	catch(excepciones.ClienteNoExisteException e) {
		fail("No debería lanzar ClienteNoExisteException");
	}
	catch(excepciones.ClienteConViajePendienteException e) {
		fail("No debería lanzar ClienteConViajePendienteException");
	}
	catch(excepciones.ClienteConPedidoPendienteException e) {
		assertEquals("Debería lanzar el siguiente mensaje: "+Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(),
				Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(), e.getMessage());
		
	}
	catch(excepciones.SinVehiculoParaPedidoException e) {
		fail("No debería lanzar SinVehiculoParaPedidoException");
	}
	
}

	@Test 
	public void testcalificacionDeChofer_casoBase(){
		try{
			this.empresa.login("Usuario2", "12345677");
			this.empresa.pagarYFinalizarViaje(4);
			double calificaciones = this.empresa.calificacionDeChofer(this.choferPermanente);
			
			assertTrue("Cálculo correcto", calificaciones == this.empresa.getViajesTerminados().get(0).getCalificacion()/this.empresa.getViajesTerminados().size());
		}
		catch(excepciones.SinViajesException e){
			fail("No deberia lanzar SinViajesException");
		}
		catch(Exception e) {
			fail("No deberia lanzar ninguna excepcion: ");
		}
	}
	@Test 
	public void testcalificacionDeChofer_sin_viajes() {
		try{
			Chofer chofer = new ChoferPermanente("33333","nombreRealChofer3",2020,4);
    		this.empresa.agregarChofer(chofer); //Chofer nuevo
			double calificaciones = this.empresa.calificacionDeChofer(chofer);
			
			fail("Deberia lanzar SinViajesException");
			
		}
		catch(excepciones.SinViajesException e){
			assertEquals("Debio lanzar el siguiente mensaje"+ Mensajes.CHOFER_SIN_VIAJES.getValor(),Mensajes.CHOFER_SIN_VIAJES.getValor(), e.getMessage());
		}
		catch(Exception e) {
			fail("No deberia lanzar ninguna otra excepcion");
		}
		
	}
    @Test
    public void testCrearViaje_ChoferNoDisponible_porinexistencia() {
    	//Chofer chofer = new ChoferPermanente("33333","nombreRealChofer1",2020,4);
    	Auto autoNuevo = new Auto("CCC333",4,false);
    	try {
    		//this.empresa.agregarChofer(chofer); //Chofer nuevo 
    		this.empresa.agregarVehiculo(autoNuevo); //Auto nuevo
    		Pedido p = this.empresa.getPedidoDeCliente(this.empresa.getClientes().get("Usuario1")); //Pedido del cliente1
    		
			this.empresa.crearViaje(this.pedidoCliente1, this.choferPermanente, autoNuevo); //con auto1 resulta igual
			
			fail("Deberia lanzar ChoferNoDisponibleException");
    	}
    	catch(excepciones.ChoferNoDisponibleException e) {
    		boolean bool = (e.getMessage() == Mensajes.CHOFER_NO_DISPONIBLE.getValor() && (e.getChofer() == choferPermanente));
    		
			assertTrue("Esta mal construida la exception", bool);
		}
    	catch(Exception e) {
    		
    		fail(e.getMessage());
    	}
    }

    @Test
    public void testPagarYFinalizarViaje() {
    	try{
			this.empresa.login("Usuario2", "12345677");
			this.empresa.pagarYFinalizarViaje(4);
			assertTrue("Viaje finalizado correctamente", this.empresa.getViajesTerminados().get(0).getCalificacion() == 4);
    	}
		catch(Exception e) {
			fail(e.getMessage());
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

