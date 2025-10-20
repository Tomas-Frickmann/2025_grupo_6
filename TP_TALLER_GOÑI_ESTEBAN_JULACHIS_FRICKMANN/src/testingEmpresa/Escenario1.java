package testingEmpresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.*;
import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

public class Escenario1 {
	private Empresa empresa;

	@Before
	public void setUp() throws Exception {
		this.escenario1();
	}

	@After
	public void tearDown() throws Exception {
		this.empresa.logout();
	}
	
	public void escenario1() {
		try {
			this.empresa = Empresa.getInstance();
			this.empresa.login("admin", "admin");
			
			this.empresa.setChoferes(new HashMap <String,Chofer>());
			this.empresa.setChoferesDesocupados(new ArrayList <Chofer>());
			this.empresa.setClientes(new HashMap <String,Cliente>());
			this.empresa.setPedidos(new HashMap <Cliente,Pedido>());
			this.empresa.setVehiculos(new HashMap <String,Vehiculo>());
			this.empresa.setVehiculosDesocupados(new ArrayList <Vehiculo>());
			this.empresa.setViajesIniciados(new HashMap <Cliente,Viaje>());
			this.empresa.setViajesTerminados(new ArrayList <Viaje>());	
		//Escenario vacio
		} catch (Exception e) {
			System.out.println("Fallo al registrar admin");
		
			}
	}
	
	@Test
	public void testLoginAdmin() {
		//No se que hacer aca la verdad. Funciona, por ende pueden haber varios admin al mismo tiempo.(?
		try {
			this.empresa.login("admin", "admin");
			assertTrue("Admin logeado", empresa.isAdmin());
		}
		catch(UsuarioNoExisteException   e) {
			fail("Lanzada excepcion no esperada UsuarioNoExisteException");
		}
		catch( PasswordErroneaException  e) {
			fail("Lanzada excepcion no esperada PasswordErroneaException");
		}
	}
	
//HOWEVER, DO NOT DELETE THIS COMMENT, IF YOU DELETE THIS COMMENT EVERYTHING WILL FAIL.

	@Test
	public void testAgregarCliente() {
		try {
			this.empresa.agregarCliente("Usuario1", "pass1", "nombreReal1");
			assertTrue("Cliente agregado", !this.empresa.getClientes().isEmpty());
			
		}
		catch(UsuarioYaExisteException e){
			fail("Lanzada excepcion no esperada UsuarioYaExisteException");
		}
		
	}
	@Test
	public void testAgregarChofer() {
		try {
			this.empresa.agregarCliente("Usuario1", "pass1", "nombreReal1");
			assertTrue("Cliente agregado", !this.empresa.getClientes().isEmpty());
			//assertTrue("No coincide el resultado", !(Math.abs(SalidaMedia-7.382)>0.01));
		}
		catch(Exception e){
			String mensaje = e.getMessage();
			assertTrue("No debio lanzar UsuarioYaExisteException", mensaje.equals(Mensajes.USUARIO_REPETIDO));
		}
		
	}
	
	@Test //Agregar pedido podria testearse en otro escenario, este escenario tiene un administrador y el no se ocupa de eso.
	public void testAgregarPedido() { 
		try {
			this.empresa.agregarPedido(new Pedido(new Cliente("Usuario1", "pass1", "nombreReal1"), 4, false, false, 1, Constantes.ZONA_STANDARD));
			fail("Deberia haber saltado excepción");
			
		}
		catch(excepciones.ClienteNoExisteException e) {
			assertTrue("Salto correcto de excepcion, cliente inexistente", this.empresa.getClientes().get("cliente1") == null);
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			fail("ClienteConViajePendienteException. Deberia haber saltado otra excepcion");
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			fail("ClienteConPedidoPendienteException. Deberia haber saltado otra excepcion");
		}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			fail("SinVehiculoParaPedidoException. Deberia haber saltado otra excepcion");
		}
		
	}	
	
	@Test
	public void testAgregarVehiculo() {
		Auto veh = new Auto("AAA111",4,false);
		try {
			this.empresa.agregarVehiculo(veh);
			assertTrue("Vehiculo agregado", !this.empresa.getVehiculos().isEmpty());			
		}
		catch(excepciones.VehiculoRepetidoException e){
			fail("No deberia haber saltado la excepción");
		}
		
	}
@Test
	public void testisAdmin() {
		assertTrue("Admin logueado",this.empresa.isAdmin());
	}
	
	
	//Deberia devolver true, en otro escenario hay que loguear a un cliente
	
}//Geters y seters aca no por que no tendria sentido testearlos
	
