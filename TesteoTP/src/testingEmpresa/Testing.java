package testingEmpresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

public class Testing {
	private Empresa empresa;

	@Before
	public void setUp() throws Exception {
		this.Escenario1();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public void Escenario1() {
		this.empresa = Empresa.getInstance();
		this.empresa.setChoferes(new HashMap <String,Chofer>());
		this.empresa.setChoferesDesocupados(new ArrayList <Chofer>());
		this.empresa.setClientes(new HashMap <String,Cliente>());
		this.empresa.setPedidos(new HashMap <Cliente,Pedido>());
		this.empresa.setVehiculos(new HashMap <String,Vehiculo>());
		this.empresa.setVehiculosDesocupados(new ArrayList <Vehiculo>());
		this.empresa.setViajesIniciados(new HashMap <Cliente,Viaje>());
		this.empresa.setViajesTerminados(new ArrayList <Viaje>());	
		//Escenario vacio
	}

	@Test
	public void testAgregarCliente() {
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
	
	@Test
	public void testAgregarPedido() {
		try {
			Cliente cliente = new Cliente("Usuario1", "pass1", "nombreReal1");
			this.empresa.agregarPedido(new Pedido(cliente, 4, false, false, 1, Constantes.ZONA_STANDARD));
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
	
	

}
