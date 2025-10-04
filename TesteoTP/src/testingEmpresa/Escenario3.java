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
			
			this.empresa.agregarChofer(new ChoferPermanente("11111111","nombreRealChofer1",2020,4));
			this.empresa.agregarChofer(new ChoferTemporario("33333333","NombreRealChofer2"));
			
			this.empresa.agregarVehiculo(new Auto("AAA111",4,false));
			this.empresa.agregarVehiculo(new Moto("AAA222"));
			
			Cliente cliente1 = this.empresa.getClientes().get("Usuario1");
			this.empresa.agregarPedido(new Pedido(cliente1, 4, false, false, 1, Constantes.ZONA_STANDARD));
			
			this.empresa.login("Usuario1", "12345678");
			//Este escenario no tiene viajesIniciados
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}
	
	@Test
	public void testAgregarPedido() {
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
	public void testgetChoferes(){
		
		HashMap <String,Chofer> choferes = new HashMap <>();
		choferes=this.empresa.getChoferes();
		assertTrue(choferes.containsKey("11111111"));
	}
	
	
	
	@Test
	public void testcrearViaje(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		Pedido pedido= this.empresa.getPedidoDeCliente(c1);
		Chofer chofer= this.empresa.getChoferesDesocupados().get(0);
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(0);
		try{
			this.empresa.crearViaje(pedido, chofer, vehiculo);
			assertTrue("Viaje creado con exito", this.empresa.getViajeDeCliente(c1) != null);
		}
		catch(excepciones.PedidoInexistenteException e){
			fail("No deberia lanzar excepcion");
		
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail("No deberia lanzar excepcion");
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			fail("No deberia lanzar excepcion");
		}
		catch(excepciones.VehiculoNoValidoException e){
			fail("No deberia lanzar excepcion");
		}
		catch(excepciones.ClienteConViajePendienteException e){
			fail("No deberia lanzar excepcion");
				
		}
	}
	
	@Test
	public void testgetChoferesDesocupados(){
		ArrayList <Chofer> choferesDesocupados = new ArrayList <>();
		choferesDesocupados=this.empresa.getChoferesDesocupados();
		Chofer chofer1= this.empresa.getChoferes().get("11111111");
		Chofer chofer2= this.empresa.getChoferes().get("33333333");
		assertTrue(choferesDesocupados.contains(chofer1) && choferesDesocupados.contains(chofer2));
		
		
	}
	
}
