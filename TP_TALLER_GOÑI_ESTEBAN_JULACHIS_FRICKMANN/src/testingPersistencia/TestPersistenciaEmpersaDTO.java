package testingPersistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import persistencia.EmpresaDTO;
import util.Constantes;

public class TestPersistenciaEmpersaDTO implements Serializable {


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorDTO() {
		EmpresaDTO edto = new EmpresaDTO();
		assertNotNull("Los choferes deberían estar inicializados", edto.getChoferes());
        assertNotNull("Los choferes desocupados deberían estar inicializados", edto.getChoferesDesocupados());
        assertNotNull("Los clientes deberían estar inicializados", edto.getClientes());
        assertNotNull("Los pedidos deberían estar inicializados", edto.getPedidos());
        assertNotNull("Los vehículos deberían estar inicializados", edto.getVehiculos());
        assertNotNull("Los vehículos desocupados deberían estar inicializados", edto.getVehiculosDesocupados());
        assertNotNull("Los viajes iniciados deberían estar inicializados", edto.getViajesIniciados());
        assertNotNull("Los viajes terminados deberían estar inicializados", edto.getViajesTerminados());
        
        assertTrue("Los choferes deberían comenzar vacíos", edto.getChoferes().isEmpty());
        assertTrue("Los choferes desocupados deberían comenzar vacíos", edto.getChoferesDesocupados().isEmpty());
        assertTrue("Los clientes deberían comenzar vacíos", edto.getClientes().isEmpty());
        assertTrue("Los pedidos deberían comenzar vacíos", edto.getPedidos().isEmpty());
        assertTrue("Los vehículos deberían comenzar vacíos", edto.getVehiculos().isEmpty());
        assertTrue("Los vehículos desocupados deberían comenzar vacíos", edto.getVehiculosDesocupados().isEmpty());
        assertTrue("Los viajes iniciados deberían comenzar vacíos", edto.getViajesIniciados().isEmpty());
        assertTrue("Los viajes terminados deberían comenzar vacíos", edto.getViajesTerminados().isEmpty());
        //no se en que empieza usuario por defecto, asumo que en null
        assertNull("El usuario logueado debería ser null al inicio", edto.getUsuarioLogeado());
	}
	
	@Test
	public void testGetYSet() {
		EmpresaDTO edto= new EmpresaDTO();
		ChoferPermanente chofer = new ChoferPermanente("aaaa","1234",2000,2);
		Cliente cliente=new Cliente("aaaa","1234","aaaa");
		Pedido pedido= new Pedido(cliente,1,true,true,1,Constantes.ZONA_STANDARD);
		Vehiculo vehiculo= new Auto("aaaa",2,true);
		Viaje viaje=new Viaje(pedido,chofer,vehiculo);
		
		HashMap<String,Chofer> choferes = new HashMap<>();
		choferes.put("aaaa", chofer);
		edto.setChoferes(choferes);
		assertEquals("error en choferes",choferes,edto.getChoferes());
		
		ArrayList<Chofer> choferesDesocupados =new ArrayList<>();
		choferesDesocupados.add(chofer);
		edto.setChoferesDesocupados(choferesDesocupados);
		assertEquals("error en choferes desocupados",choferesDesocupados, edto.getChoferesDesocupados());
		
		HashMap<String,Cliente> clientes = new HashMap<>();
		clientes.put("aaaa", cliente);
		edto.setClientes(clientes);
		assertEquals("error en clientes",clientes,edto.getClientes());
		
		HashMap <Cliente,Pedido> pedidos =new HashMap<>();
		pedidos.put(cliente, pedido);
		edto.setPedidos(pedidos);
		assertEquals("error en pedidos",pedidos, edto.getPedidos());

		edto.setUsuarioLogeado(cliente);
		assertEquals("error en usuario logueado",cliente,edto.getUsuarioLogeado());
		
		HashMap<String, Vehiculo> vehiculos = new HashMap<>();
		vehiculos.put("aaaa",vehiculo);
		edto.setVehiculos(vehiculos);
		assertEquals("error en vehiculos",vehiculos,edto.getVehiculos());
		
		ArrayList<Vehiculo> vehiculosDesocupados =new ArrayList<>();
		vehiculosDesocupados.add(vehiculo);
		edto.setVehiculosDesocupados(vehiculosDesocupados);
		assertEquals("error en vehiculos desocupados",vehiculosDesocupados,edto.getVehiculosDesocupados());
		
		HashMap<Cliente,Viaje> viajes = new HashMap<>();
		viajes.put(cliente, viaje);
		edto.setViajesIniciados(viajes);
		assertEquals("error en viajes iniciados",viajes,edto.getViajesIniciados());
		
		ArrayList<Viaje> viajesTerminados =new ArrayList<>();
		viajesTerminados.add(viaje);
		edto.setViajesTerminados(viajesTerminados);
		assertEquals("error en viajes terminados",viajesTerminados,edto.getViajesTerminados());
		
		

		
	}
	

}
