package testingPersistencia;

import static org.junit.Assert.assertEquals;

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
import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import persistencia.UtilPersistencia;
import util.Constantes;

public class TestUtilPersistencia {


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeEmpresaAEmpresaDTO() {
		
		EmpresaDTO edto =new EmpresaDTO();
		Empresa e =Empresa.getInstance();
		
		ChoferPermanente chofer = new ChoferPermanente("aaaa","1234",2000,2);
		Cliente cliente=new Cliente("aaaa","1234","aaaa");
		Pedido pedido= new Pedido(cliente,1,true,true,1,Constantes.ZONA_STANDARD);
		Vehiculo vehiculo= new Auto("aaaa",2,true);
		Viaje viaje=new Viaje(pedido,chofer,vehiculo);
		
		HashMap<String,Chofer> choferes = new HashMap<>();
		ArrayList<Chofer> choferesDesocupados =new ArrayList<>();
		HashMap<String,Cliente> clientes = new HashMap<>();
		HashMap <Cliente,Pedido> pedidos =new HashMap<>();
		HashMap<String, Vehiculo> vehiculos = new HashMap<>();
		ArrayList<Vehiculo> vehiculosDesocupados =new ArrayList<>();
		HashMap<Cliente,Viaje> viajes = new HashMap<>();
		ArrayList<Viaje> viajesTerminados =new ArrayList<>();
				
		choferes.put("aaaa", chofer);		
		choferesDesocupados.add(chofer);		
		clientes.put("aaaa", cliente);		
		pedidos.put(cliente, pedido);		
		vehiculos.put("aaaa",vehiculo);		
		vehiculosDesocupados.add(vehiculo);		
		viajes.put(cliente, viaje);		
		viajesTerminados.add(viaje);
		
		e.setChoferes(choferes);
		e.setChoferesDesocupados(choferesDesocupados);
		e.setClientes(clientes);
		e.setPedidos(pedidos);
		e.setUsuarioLogeado(cliente);
		e.setVehiculos(vehiculos);
		e.setVehiculosDesocupados(vehiculosDesocupados);
		e.setViajesIniciados(viajes);
		e.setViajesTerminados(viajesTerminados);
		
		edto = UtilPersistencia.EmpresaDtoFromEmpresa();
		
		assertEquals(e.getChoferes(), edto.getChoferes());
		assertEquals(e.getChoferesDesocupados(), edto.getChoferesDesocupados());
		assertEquals(e.getClientes(), edto.getClientes());
		assertEquals(e.getPedidos(), edto.getPedidos());
		assertEquals(e.getVehiculos(), edto.getVehiculos());
		assertEquals(e.getVehiculosDesocupados(), edto.getVehiculosDesocupados());
		assertEquals(e.getViajesIniciados(), edto.getViajesIniciados());
		assertEquals(e.getViajesTerminados(), edto.getViajesTerminados());
		assertEquals(e.getUsuarioLogeado(), edto.getUsuarioLogeado());
		
	}
	@Test
	public void testDeEmpresaDTOAEmpresa() {
		
		EmpresaDTO edto =new EmpresaDTO();

		
		ChoferPermanente chofer = new ChoferPermanente("aaaa","1234",2000,2);
		Cliente cliente=new Cliente("aaaa","1234","aaaa");
		Pedido pedido= new Pedido(cliente,1,true,true,1,Constantes.ZONA_STANDARD);
		Vehiculo vehiculo= new Auto("aaaa",2,true);
		Viaje viaje=new Viaje(pedido,chofer,vehiculo);
		
		HashMap<String,Chofer> choferes = new HashMap<>();
		ArrayList<Chofer> choferesDesocupados =new ArrayList<>();
		HashMap<String,Cliente> clientes = new HashMap<>();
		HashMap <Cliente,Pedido> pedidos =new HashMap<>();
		HashMap<String, Vehiculo> vehiculos = new HashMap<>();
		ArrayList<Vehiculo> vehiculosDesocupados =new ArrayList<>();
		HashMap<Cliente,Viaje> viajes = new HashMap<>();
		ArrayList<Viaje> viajesTerminados =new ArrayList<>();
				
		choferes.put("aaaa", chofer);		
		choferesDesocupados.add(chofer);		
		clientes.put("aaaa", cliente);		
		pedidos.put(cliente, pedido);		
		vehiculos.put("aaaa",vehiculo);		
		vehiculosDesocupados.add(vehiculo);		
		viajes.put(cliente, viaje);		
		viajesTerminados.add(viaje);
		
		edto.setChoferes(choferes);
		edto.setChoferesDesocupados(choferesDesocupados);
		edto.setClientes(clientes);
		edto.setPedidos(pedidos);
		edto.setUsuarioLogeado(cliente);
		edto.setVehiculos(vehiculos);
		edto.setVehiculosDesocupados(vehiculosDesocupados);
		edto.setViajesIniciados(viajes);
		edto.setViajesTerminados(viajesTerminados);
		
		UtilPersistencia.empresaFromEmpresaDTO(edto);
		
		Empresa e =Empresa.getInstance();
		assertEquals(e.getChoferes(), edto.getChoferes());
		assertEquals(e.getChoferesDesocupados(), edto.getChoferesDesocupados());
		assertEquals(e.getClientes(), edto.getClientes());
		assertEquals(e.getPedidos(), edto.getPedidos());
		assertEquals(e.getVehiculos(), edto.getVehiculos());
		assertEquals(e.getVehiculosDesocupados(), edto.getVehiculosDesocupados());
		assertEquals(e.getViajesIniciados(), edto.getViajesIniciados());
		assertEquals(e.getViajesTerminados(), edto.getViajesTerminados());
		assertEquals(e.getUsuarioLogeado(), edto.getUsuarioLogeado());
		
	}
	
}
