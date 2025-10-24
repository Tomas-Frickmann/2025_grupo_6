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
		Cliente cliente=new Cliente("LAU1234","1234","Lautaro");
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
		
		assertEquals("Error en chofer",e.getChoferes(), edto.getChoferes());
		assertEquals("Error en chofer desocupado",e.getChoferesDesocupados(), edto.getChoferesDesocupados());
		assertEquals("Error en clientes",e.getClientes(), edto.getClientes());
		assertEquals("Error en pedidos",e.getPedidos(), edto.getPedidos());
		assertEquals("Error en vehiculos",e.getVehiculos(), edto.getVehiculos());
		assertEquals("Error en vehiculos desocupados",e.getVehiculosDesocupados(), edto.getVehiculosDesocupados());
		assertEquals("Error en viajes iniciados",e.getViajesIniciados(), edto.getViajesIniciados());
		assertEquals("Error en viajes terminados",e.getViajesTerminados(), edto.getViajesTerminados());
		assertEquals("Error en usuario log",e.getUsuarioLogeado(), edto.getUsuarioLogeado());
		
	}
	@Test
	public void testDeEmpresaDTOAEmpresa() {
		
		EmpresaDTO edto =new EmpresaDTO();		
		ChoferPermanente chofer = new ChoferPermanente("aaaa","1234",2000,2);
		
		Cliente cliente=new Cliente("LAU1234","1234","Lautaro");
		
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
		
		assertEquals("Error en chofer",e.getChoferes(), edto.getChoferes());
		assertEquals("Error en chofer desocupados",e.getChoferesDesocupados(), edto.getChoferesDesocupados());
		assertEquals("Error en cliente",e.getClientes(), edto.getClientes());
		assertEquals("Error en pedidos",e.getPedidos(), edto.getPedidos());
		assertEquals("Error en vhiuculos",e.getVehiculos(), edto.getVehiculos());
		assertEquals("Error en vehiculo desocupado",e.getVehiculosDesocupados(), edto.getVehiculosDesocupados());
		assertEquals("Error en viaje iniciado",e.getViajesIniciados(), edto.getViajesIniciados());
		assertEquals("Error en viajes terminados",e.getViajesTerminados(), edto.getViajesTerminados());
		assertEquals("Error en Usuario logueado ",e.getUsuarioLogeado(), edto.getUsuarioLogeado());
		
	}
	
}
