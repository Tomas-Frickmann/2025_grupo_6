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


public class Escenario2 {
	Empresa empresa;
	Chofer chofer1 = new ChoferPermanente("12345678","nombreRealChofer1",2020,4);
	Auto auto1= new Auto("AAA111",4,false);
	Moto moto1 = new Moto("AAA222");
	Combi combi1 = new Combi("AAA333",10,true);
	
	Pedido pedido_cliente1 = null;
	@Before
	public void setUp() throws Exception {
		this.escenario2();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void escenario2(){
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
			this.empresa.login("admin", "admin");
			
			this.empresa.agregarCliente("Usuario1", "12345678", "NombreReal1");
			Chofer chofer1 = this.chofer1;
			this.empresa.agregarChofer(chofer1);
			
			Auto auto1 = this.auto1;
			Moto moto1=this.moto1;
			Combi combi1 = this.combi1;
			
			this.empresa.agregarVehiculo(auto1);
			this.empresa.agregarVehiculo(moto1);
			this.empresa.agregarVehiculo(combi1);
			//Este escenario no tiene pedidos ni viajes iniciados
			this.pedido_cliente1 = new Pedido(this.empresa.getClientes().get("Usuario1"),4,false,false,1,Constantes.ZONA_PELIGROSA);
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}


	@Test	
	public void testSingletonInstance(){
		Empresa otraInstancia= Empresa.getInstance();
		if (empresa!=otraInstancia) 
			fail("Deberia devolver la misma instancia del Singleton");
		
	}
	
	@Test
	public void testLoginUserNoExiste() {
		try {
			this.empresa.login("UserInexistente", "12345678");
			fail("Deberia haber saltado excepción");
		}
		catch (excepciones.UsuarioNoExisteException e) {
			assertEquals("Debio lanzar "+Mensajes.USUARIO_DESCONOCIDO, Mensajes.USUARIO_DESCONOCIDO.getValor(), e.getMessage());
			assertEquals("Almacena mal el usuario inexistente",e.getUsuarioPretendido(),"UserInexistente");
		}
		catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoginPassIncorrecta() {
		try {
			this.empresa.login("admin", "12345678"); 
			fail("Deberia haber saltado excepción");
		}
		catch (excepciones.PasswordErroneaException e) {
			assertEquals("Debio lanzar "+Mensajes.PASS_ERRONEO, Mensajes.PASS_ERRONEO.getValor(), e.getMessage());
			assertEquals("Almacena mal el usuario ",e.getUsuarioPretendido(),"admin");		
			assertEquals("Almacena mal el usuario ",e.getPasswordPretendida(),"12345678");
		}
		catch(excepciones.UsuarioNoExisteException e) {
			fail("No deberia lanzar UsuarioNoExisteException");
		}
		
	}
	
		
	@Test
	public void testAgregarChofer_ya_existente() { 
		Chofer chofer = new ChoferPermanente("11111111","nombreRealChofer1",2020,4);
		try {
			this.empresa.agregarChofer(chofer);
			fail("Deberia haber saltado ChoferRepetidoException");
		}
		catch(excepciones.ChoferRepetidoException e) {
			
			assertEquals("Debio lanzar "+Mensajes.CHOFER_YA_REGISTRADO, Mensajes.CHOFER_YA_REGISTRADO.getValor(), e.getMessage());
			assertEquals("Almacena mal el chofer ",e.getChoferExistente(),chofer);
			assertEquals("Almacena mal el dni",e.getDniPrentendido(),"11111111");
					
		}
	}
	
	@Test
	public void testAgregarVehiculo_ya_existente() {
		Auto veh = new Auto("AAA111",4,false);
		try {
			this.empresa.agregarVehiculo(veh);
			fail("Vehiculo agregado. Deberia saltar excepción");			
		}
		catch(excepciones.VehiculoRepetidoException e){
			assertEquals("Debio lanzar "+Mensajes.VEHICULO_YA_REGISTRADO, Mensajes.VEHICULO_YA_REGISTRADO.getValor(), e.getMessage());
			assertEquals("Almacena mal el vehiculo ",e.getVehiculoExistente(),veh);
			assertEquals("Almacena mal la patente",e.getPatentePrentendida(),"AAA111");
			
			
		}
		
	}
	
	
	@Test
	public void testcrearViaje_pedido_inexistente(){
		Cliente c1 = this.empresa.getClientes().get("Usuario1");
		Pedido pedido= new Pedido(c1,5,false,true,3,Constantes.ZONA_PELIGROSA);
		Chofer chofer= this.empresa.getChoferesDesocupados().get(0);
		Vehiculo vehiculo=this.empresa.getVehiculosDesocupados().get(0);
		try{
			this.empresa.crearViaje(pedido, chofer, vehiculo);
			fail("Deberia lanzar PedidoInexistenteException");
		}
		catch(excepciones.PedidoInexistenteException e){
			assertEquals("Debio lanzar "+Mensajes.PEDIDO_INEXISTENTE.getValor(), Mensajes.PEDIDO_INEXISTENTE.getValor(), e.getMessage());
			assertEquals("Almacena mal el pedido",e.getPedido(),pedido);
			
			
		
		}
		catch(excepciones.ChoferNoDisponibleException e){
			fail("No deberia haber lanzado ChoferNoDisponibleException ");
		}
		catch(excepciones.VehiculoNoDisponibleException e){
			fail("No deberia haber lanzado VehiculoNoDisponibleException ");
		}
		catch(excepciones.VehiculoNoValidoException e){
			fail("No deberia haber lanzado VehiculoNoValidoException ");
		}
		catch(excepciones.ClienteConViajePendienteException e){
			fail("No deberia haber lanzado ClienteConViajePendienteException ");
				
		}
	}
	

	@Test 
	public void testAgregarCliente_ya_existente() {
		try {
			this.empresa.agregarCliente("Usuario1", "12345678", "NombreReal1");
			fail("Error debería lanzar excepcion UsuarioYaExisteException ");
		}
		catch(excepciones.UsuarioYaExisteException e){
				assertEquals("Debio lanzar "+Mensajes.USUARIO_REPETIDO, Mensajes.USUARIO_REPETIDO.getValor(), e.getMessage());
				assertEquals("Almacena mal el cliente existente",e.getUsuarioPretendido(),"Usuario1");
				
			}
	}
 
    @Test
    public void testAgregarPedido() {		
    try {
    	this.empresa.agregarPedido(this.pedido_cliente1);    	
    }
	catch(excepciones.ClienteNoExisteException e) {
		fail("No deberia lanzar ClienteNoExisteException");
	}
    catch(excepciones.ClienteConPedidoPendienteException e) {
    	fail("No deberia lanzar ClienteConPedidoPendienteException");
	}
	catch(excepciones.ClienteConViajePendienteException e) {
		fail("No deberia lanzar ClienteConViajePendienteException");
	}
	catch(excepciones.SinVehiculoParaPedidoException e) {
		fail("No deberia lanzar SinVehiculoParaPedidoException");
	}
	
    }

    @Test
    public void testAgregarPedido_A_CLIENTE_QUE_YA_POSEE_() {
    	try {
			this.empresa.agregarPedido(this.pedido_cliente1);    	
			this.empresa.agregarPedido(this.pedido_cliente1);    	
			fail("Debería haber lanzado ClienteConPedidoPendienteException");
		}
		catch(excepciones.ClienteNoExisteException e) {
			fail("No deberia lanzar ClienteNoExisteException");
		}
		catch(excepciones.ClienteConPedidoPendienteException e) {
			assertEquals("Debio lanzar "+Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(), Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(), e.getMessage());
			
		}
		catch(excepciones.ClienteConViajePendienteException e) {
			fail("No deberia lanzar ClienteConViajePendienteException");
		}
		catch(excepciones.SinVehiculoParaPedidoException e) {
			fail("No deberia lanzar SinVehiculoParaPedidoException");
		}
    	
    	
    }
    
	
}




