package testingEmpresa;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bin.modeloDatos.Moto;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
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
		this.empresa = null;
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
	public void testSingletonInstance(){
		Empresa otraInstancia= Empresa.getInstance();
		if (empresa!=otraInstancia) 
			fail("Deberia devolver la misma instancia del Singleton");
	}
	
	@Test 
	public void testLoginAdmin() {
		
		try {
			this.empresa.login("admin", "admin");
			assertTrue("Admin logeado", empresa.isAdmin());
		}
		catch(UsuarioNoExisteException   e) {
			fail(e.getMessage());
		}
		catch( PasswordErroneaException  e) {
			fail(e.getMessage());
		}
	}
	
//HOWEVER, DO NOT DELETE THIS COMMENT, IF YOU DELETE THIS COMMENT EVERYTHING WILL FAIL.

	@Test
	public void testAgregarCliente_sin_Clientes() {
		try {
			this.empresa.agregarCliente("Usuario1", "pass1", "nombreReal1");
			assertTrue("Cliente agregado", !this.empresa.getClientes().isEmpty());
			
		}
		catch(UsuarioYaExisteException e){
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testAgregarChofer_sin_Chofer() {
	
		Chofer chofer= new ChoferTemporario("dni1","nombre1"); 
		try {
			this.empresa.agregarChofer(chofer);
			assertTrue("Chofer agregado", !this.empresa.getChoferes().isEmpty());
		}
		catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	
	@Test 
	public void testAgregarPedido_sin_Cliente() { 
		try {
			this.empresa.agregarPedido(new Pedido(new Cliente("Usuario1", "pass1", "nombreReal1"), 4, false, false, 1, Constantes.ZONA_STANDARD));
			fail("Deberia haber saltado excepción");
			
		}
		catch(excepciones.ClienteNoExisteException e) {
			assertEquals("Debio lanzar el mensaje"+ Mensajes.CLIENTE_NO_EXISTE.getValor(), e.getMessage(),Mensajes.CLIENTE_NO_EXISTE.getValor());
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			fail("No debio tirar ClienteConViajePendienteException");
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			fail("No debio tirar ClienteConPedidoPendienteException");
		}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			fail("No debio tirar SinVehiculoParaPedidoException");
		}
		
	}	
	
	@Test
	public void testAgregarVehiculo_sin_Vehiculo() {
		Auto veh = new Auto("AAA111",4,false);
		try {
			this.empresa.agregarVehiculo(veh);
			assertTrue("Vehiculo agregado", !this.empresa.getVehiculos().isEmpty());			
		}
		catch(excepciones.VehiculoRepetidoException e){
			fail("No debio tirar VehiculoRepetidoException");
		}
		
	}
	
	@Test
	public void testisAdmin() {
		assertTrue("Admin logueado",this.empresa.isAdmin());
	}
	
	@Test
	public void testgetHistorialViajeCliente_setViajesTerminados() {
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
		ArrayList <Viaje> historialViajesTerminados= new ArrayList <Viaje>();
		Chofer chofer= new ChoferTemporario("dni1","nombre1");
		Chofer chofer2= new ChoferTemporario("dni2","nombre2");
		Auto vehiculo1= new Auto ("AAA111",4,false);
		Auto vehiculo2= new Auto ("AAA111",4,false);
		Pedido pedido1= new Pedido (cliente, 3, false, false, 1, Constantes.ZONA_STANDARD);
		Pedido pedido2= new Pedido (cliente, 2, false, false, 2, Constantes.ZONA_STANDARD);
		Viaje viaje1= new Viaje(pedido1,chofer, vehiculo1);
		Viaje viaje2= new Viaje(pedido2,chofer2 , vehiculo2);
		viaje1.finalizarViaje(4);
		viaje2.finalizarViaje(5);
		historialViajesTerminados.add(viaje1);
		historialViajesTerminados.add(viaje2);
		this.empresa.setViajesTerminados(historialViajesTerminados);
		ArrayList <Viaje> historialDevuelto= this.empresa.getHistorialViajeCliente(cliente);
		assertEquals("Mal funcionamiento de get/set", historialViajesTerminados, historialDevuelto);
		
		
		
		
	}
	@Test
	public void testgetHistorialViajeCliente_setViajesTerminados_sinviajes() {
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
		ArrayList <Viaje> historialViajesTerminados= new ArrayList <Viaje>();
		
		this.empresa.setViajesTerminados(historialViajesTerminados);
		ArrayList <Viaje> historialDevuelto= this.empresa.getHistorialViajeCliente(cliente);
		assertEquals("Mal funcionamiento de get/set", historialViajesTerminados, historialDevuelto);
		
		
		
		
	}
	@Test  
	public void TestgetViajeDeCliente_setViajesIniciados() {
		HashMap<Cliente, Viaje> hashviajesinicados= new HashMap <Cliente, Viaje>();
		
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
		Chofer chofer= new ChoferTemporario("dni1","nombre1");
		Auto vehiculo1= new Auto ("AAA111",4,false);
		Pedido pedido1= new Pedido (cliente, 3, false, false, 1, Constantes.ZONA_STANDARD);
		Viaje viaje1= new Viaje(pedido1,chofer, vehiculo1);
		hashviajesinicados.put(cliente, viaje1);
		this.empresa.setViajesIniciados(hashviajesinicados);
		Viaje viajeDevuelto= this.empresa.getViajeDeCliente(cliente);
		assertEquals("Mal funcionamiento de get/set", viaje1, viajeDevuelto);
	
	}
	@Test  
	public void TestgetViajeDeCliente_setViajesIniciados_Sin_viaje() {
		
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
		
		Viaje viajeDevuelto= this.empresa.getViajeDeCliente(cliente);
		assertNull("Mal funcionamiento de get/set", viajeDevuelto);
	
	}
	@Test
  public void 	testgetHistorialViajeChofer() {
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
		ArrayList <Viaje> historialViajesTerminados= new ArrayList <Viaje>();
		Chofer chofer= new ChoferTemporario("dni1","nombre1");
		Chofer chofer2= new ChoferTemporario("dni2","nombre2");
		Auto vehiculo1= new Auto ("AAA111",4,false);
		Auto vehiculo2= new Auto ("AAA111",4,false);
		Pedido pedido1= new Pedido (cliente, 3, false, false, 1, Constantes.ZONA_STANDARD);
		Pedido pedido2= new Pedido (cliente, 2, false, false, 2, Constantes.ZONA_STANDARD);
		Viaje viaje1= new Viaje(pedido1,chofer, vehiculo1);
		Viaje viaje2= new Viaje(pedido2,chofer2 , vehiculo2);
		viaje1.finalizarViaje(4);
		viaje2.finalizarViaje(5);
		historialViajesTerminados.add(viaje1);
		historialViajesTerminados.add(viaje2);
		ArrayList <Viaje> historialViajesTerminadosChofer= new ArrayList <Viaje>();
		historialViajesTerminadosChofer.add(viaje1);
		this.empresa.setViajesTerminados(historialViajesTerminados);
		ArrayList <Viaje> historialDevuelto= this.empresa.getHistorialViajeChofer(chofer);
		assertEquals("Mal funcionamiento de get/set", historialViajesTerminadosChofer, historialDevuelto);
	}
	@Test
	  public void 	testgetPedidoDeCliente() {
			HashMap<Cliente, Pedido> mapaPedidos = new HashMap<Cliente, Pedido>();
			Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
			Pedido pedido = new Pedido(cliente, 4, false, false, 1, Constantes.ZONA_STANDARD);
			
			mapaPedidos.put(pedido.getCliente(), pedido);
			
			this.empresa.setPedidos(mapaPedidos);
			
			
			assertEquals("Mal funcionamiento de get/set", this.empresa.getPedidoDeCliente(cliente), pedido);
		}
	@Test
	  public void 	testgetPedidoDeCliente_SinPedidos() {
			
			Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");
			
			
			assertNull("Mal funcionamiento de get/set", this.empresa.getPedidoDeCliente(cliente));
		}
	
	
	@Test
	public void SetygetClientes (){

		HashMap<String, Cliente> mapaClientes = new HashMap<String, Cliente>();
		Cliente cliente1 = new Cliente("Usuario1", "pass1", "nombreReal1");
		Cliente cliente2 = new Cliente("Usuario2", "pass2", "nombreReal2");
		mapaClientes.put(cliente1.getNombreUsuario(), cliente1);
		mapaClientes.put(cliente2.getNombreUsuario(), cliente2);
		this.empresa.setClientes(mapaClientes);
		assertSame("Clientes seteado y getteado correctamente", mapaClientes, this.empresa.getClientes());
	}
	public void SetygetChoferes (){

		HashMap<String, Chofer> mapaChoferes = new HashMap<String, Chofer>();
		Chofer chofer1 = new ChoferTemporario("dni1", "nombre1");
		Chofer chofer2 = new ChoferTemporario("dni2", "nombre2");
		mapaChoferes.put(chofer1.getDni(), chofer1);
		mapaChoferes.put(chofer2.getDni(), chofer2);
		this.empresa.setChoferes(mapaChoferes);
		assertSame("Choferes seteado y getteado correctamente", mapaChoferes, this.empresa.getChoferes());
	}
	@Test
	public void setygetChoferesDesocupados_solo_desocupados (){

		ArrayList <Chofer> choferesDesocupados = new ArrayList <Chofer>();
		Chofer chofer1 = new ChoferTemporario("dni1", "nombre1");
		Chofer chofer2 = new ChoferTemporario("dni2", "nombre2");
		choferesDesocupados.add(chofer1);
		choferesDesocupados.add(chofer2);
		this.empresa.setChoferesDesocupados(choferesDesocupados);
		assertSame("Choferes desocupados seteado y getteado correctamente", choferesDesocupados, this.empresa.getChoferesDesocupados());
	}
	@Test
	public void setygetChoferesDesocupados_con_choferes_ocupados(){

		ArrayList <Chofer> choferesDesocupados = new ArrayList <Chofer>();
		Chofer chofer1 = new ChoferTemporario("dni1", "nombre1");
		Chofer chofer2 = new ChoferTemporario("dni2", "nombre2");
		choferesDesocupados.add(chofer1);
		choferesDesocupados.add(chofer2);
		
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");	
		Chofer chofer3= new ChoferTemporario("dni1","nombre1");
		Auto vehiculo1= new Auto ("AAA111",4,false);
		Pedido pedido1= new Pedido (cliente, 3, false, false, 1, Constantes.ZONA_STANDARD);
		Viaje viaje1= new Viaje(pedido1,chofer3, vehiculo1);
		HashMap<Cliente, Viaje> hashviajesinicados= new HashMap <Cliente, Viaje>();
		hashviajesinicados.put(cliente, viaje1);
		
		
		this.empresa.setChoferesDesocupados(choferesDesocupados);
		this.empresa.setViajesIniciados(hashviajesinicados);
		
		assertEquals("Mal funcionamiento de getter y setter ",choferesDesocupados, this.empresa.getChoferesDesocupados());
		assertTrue("Añade chofer ocupado", !this.empresa.getChoferesDesocupados().contains(chofer3));
		assertTrue("Añade chofer ocupado", this.empresa.getChoferesDesocupados().size()==2);
	}
	
	@Test
	public void SetygetPedidos (){

		HashMap<Cliente, Pedido> mapaPedidos = new HashMap<Cliente, Pedido>();
		Pedido pedido = new Pedido(new Cliente("Usuario1", "pass1", "nombreReal1"), 4, false, false, 1, Constantes.ZONA_STANDARD);
		Pedido pedido2 = new Pedido(new Cliente("Usuario2", "pass2", "nombreReal2"), 4, true, false, 1, Constantes.ZONA_STANDARD);
		mapaPedidos.put(pedido.getCliente(), pedido);
		mapaPedidos.put(pedido2.getCliente(), pedido2);
		this.empresa.setPedidos(mapaPedidos);
		assertSame("Pedidos seteado y getteado correctamente", mapaPedidos, this.empresa.getPedidos());
	}

	@Test
	public void SetygetVehiculos (){

		HashMap<String, Vehiculo> mapaVehiculos = new HashMap<String, Vehiculo>();
		Auto veh1 = new Auto("AAA111", 4, false);
		Moto veh2 = new Moto("BBB222");
		mapaVehiculos.put(veh1.getPatente(), veh1);
		mapaVehiculos.put(veh2.getPatente(), veh2);
		this.empresa.setVehiculos(mapaVehiculos);
		assertSame("Vehiculos seteado y getteado correctamente", mapaVehiculos, this.empresa.getVehiculos());
	}
	
	@Test
		public void SetygetUsuarioLogeado() {
		Cliente cliente = new Cliente("Usuario1", "pass1", "nombreReal1");
		this.empresa.setUsuarioLogeado(cliente);
		assertSame("Usuario logeado seteado y getteado correctamente", cliente, this.empresa.getUsuarioLogeado());
	
	}

	@Test
	public void SetygetVehiculosDesocupados(){
		ArrayList<Vehiculo> vehiculosDesocupados = new ArrayList<Vehiculo>();
		Auto veh1 = new Auto("AAA111", 4, false);
		Moto veh2 = new Moto("BBB222");
		Combi veh3 = new Combi ("CCC333",8,false);
		
		vehiculosDesocupados.add(veh1);
		vehiculosDesocupados.add(veh2);
		vehiculosDesocupados.add(veh3);
		
		
		
		Cliente cliente= new Cliente("Usuario1", "pass1", "nombreReal1");	
		Chofer chofer= new ChoferTemporario("dni1","nombre1");
		Auto vehiculo1= new Auto ("AAA211",4,false);
		Pedido pedido1= new Pedido (cliente, 3, false, false, 1, Constantes.ZONA_STANDARD);
		Viaje viaje1= new Viaje(pedido1,chofer, vehiculo1);
		HashMap<Cliente, Viaje> hashviajesinicados= new HashMap <Cliente, Viaje>();
		hashviajesinicados.put(cliente, viaje1);
		
		
		
		this.empresa.setViajesIniciados(hashviajesinicados);
		this.empresa.setVehiculosDesocupados(vehiculosDesocupados);
		assertSame("Vehiculos desocupados seteado y getteado correctamente", vehiculosDesocupados, this.empresa.getVehiculosDesocupados());
		assertTrue("Añade chofer ocupado", !this.empresa.getVehiculosDesocupados().contains(vehiculo1));
		assertTrue("Añade chofer ocupado", this.empresa.getVehiculosDesocupados().size()==3);
	}
	
	
	@Test
	public void TestgetTotalSalarios_sin_choferes() {
		
		
		assertTrue("Deberia dar 0", this.empresa.getTotalSalarios()==0.0);
		
	}

	

	@Test 
	
	public void testgetsalarios() {

		double sueldoBasico = 100000.0;
		int antiguedad = 3;
		int cantidadHijos = 2;
		
		ChoferTemporario.setSueldoBasico(sueldoBasico);
		ChoferPermanente.setSueldoBasico(sueldoBasico);
		
		ChoferTemporario ct = new ChoferTemporario("dniT", "nombreT");
		double BrutoTemp = ChoferTemporario.getSueldoBasico();
		double NetoTemp = 0.86 * BrutoTemp;
		assertEquals(Double.valueOf(BrutoTemp), Double.valueOf(ct.getSueldoBruto()));
		assertEquals(Double.valueOf(NetoTemp), Double.valueOf(ct.getSueldoNeto()));

		int anioactual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		ChoferPermanente cp = new ChoferPermanente("dniP", "nombreP", anioactual - antiguedad, cantidadHijos);
	
		int antiguedadCalc = cp.getAntiguedad();
		int antiguedadUsada = (antiguedadCalc <= 20) ? antiguedadCalc : 20;
		double SueldoBruto = ChoferPermanente.getSueldoBasico() + 0.05 * antiguedadUsada * ChoferPermanente.getSueldoBasico() + 0.07 * cp.getCantidadHijos() * ChoferPermanente.getSueldoBasico();
		double SueldoNeto = 0.86 * SueldoBruto;
				
		assertEquals(Double.valueOf(SueldoBruto), Double.valueOf(cp.getSueldoBruto()));
		assertEquals(Double.valueOf(SueldoNeto), Double.valueOf(cp.getSueldoNeto()));	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}