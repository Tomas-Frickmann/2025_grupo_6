package testingontrolador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import vista.IOptionPane;
import vista.IVista;
import util.Constantes;

public class TestIntegracionActionPerformed {
	
	Empresa empresa;
	Controlador controlador;
	Usuario usuariologeado;
	IOptionPane optionPaneMock; 
	IVista vistaMock; 

	@Before
	public void setUp() throws Exception {
		Field f = Empresa.class.getDeclaredField("instance");
		f.setAccessible(true);
		f.set(null, null);
		empresa = Empresa.getInstance();
        
		controlador = new Controlador();
		optionPaneMock = mock(IOptionPane.class);
		vistaMock = mock(IVista.class);
		
		controlador.setVista(vistaMock);
		when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);
		
		empresa.setClientes(new HashMap<String, Cliente>());
		empresa.getChoferes().clear();
		empresa.getVehiculos().clear();
        empresa.setUsuarioLogeado(null);
	}

	@After
	public void tearDown() throws Exception {
		empresa.getChoferes().clear();
		empresa.getClientes().clear();
		empresa.getPedidos().clear();
		empresa.getVehiculos().clear();
		empresa.setUsuarioLogeado(null);
	}

	@Test
	public void testActionPerformed_LoginExitoso() {
		try {
			empresa.agregarCliente("usuario1", "contrasenia1", "nombre");
		} catch (Exception e) {
			fail("Setup login: " + e.getMessage()); }
		
		when(vistaMock.getUsserName()).thenReturn("usuario1");
		when(vistaMock.getPassword()).thenReturn("contrasenia1");

		ActionEvent login = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.LOGIN);
		controlador.actionPerformed(login);

		assertNotNull("El usuario debe estar logueado después del login", this.empresa.getUsuarioLogeado());
		verify(optionPaneMock, never()).ShowMessage(anyString());
	}
    
    @Test
	public void testActionPerformedLogoutCliente() {
		try {
			empresa.agregarCliente("usuario1", "contrasenia1", "nombre");
			empresa.setUsuarioLogeado(empresa.login("usuario1", "contrasenia1"));
		} catch (Exception e) { 
			fail("Setup logout: " + e.getMessage()); }

		ActionEvent logoutcliente = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.CERRAR_SESION_CLIENTE);
		controlador.actionPerformed(logoutcliente);
		
		assertNull("El usuario debe ser null después del logout", empresa.getUsuarioLogeado());
		verify(optionPaneMock, never()).ShowMessage(anyString());
	}

	@Test
	public void testActionPerformedRegistroExitoso() {
		try {
			when(vistaMock.getRegNombreReal()).thenReturn("nombreNuevo");
			when(vistaMock.getRegUsserName()).thenReturn("usuarioNuevo");
			when(vistaMock.getRegPassword()).thenReturn("passNuevo");
			when(vistaMock.getRegConfirmPassword()).thenReturn("passNuevo");

			ActionEvent registrarUsuario = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.REG_BUTTON_REGISTRAR);
			controlador.actionPerformed(registrarUsuario);
			
			Usuario nuevoUsuario = empresa.getClientes().get("usuarioNuevo");
			assertNotNull("El nuevo cliente debe haber sido agregado a la empresa", nuevoUsuario);
			verify(optionPaneMock, never()).ShowMessage(anyString());
		} catch (Exception e) {
			fail("No tendria que haber lanzado una excepcion: " + e.getMessage());
		}
	}
    
	@Test
	public void testActionPerformedNuevoChoferExitoso() {
		try {
			empresa.setUsuarioLogeado(empresa.login("admin", "admin"));
			when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
			when(vistaMock.getNombreChofer()).thenReturn("choferTemp");
			when(vistaMock.getDNIChofer()).thenReturn("123456778");

			ActionEvent nuevoChoferAgregado = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_CHOFER);
			this.controlador.actionPerformed(nuevoChoferAgregado);
			
			Chofer choferAgregado = empresa.getChoferes().get("123456778");
			assertNotNull("El chofer debe haber sido agregado a la empresa", choferAgregado);

		} catch (Exception e) {
			fail("No tendria que haber lanzado una exception: " + e.getMessage());
		}
	}
    
	@Test
	public void testActionPerformedNuevoVehiculoExitoso() {
		try {
			empresa.setUsuarioLogeado(empresa.login("admin", "admin"));

			when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
			when(vistaMock.getPatente()).thenReturn("ABC123");
			when(vistaMock.getPlazas()).thenReturn(4);
			when(vistaMock.isVehiculoAptoMascota()).thenReturn(false);

			ActionEvent nuevoVehiculoAgregado = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_VEHICULO);
			this.controlador.actionPerformed(nuevoVehiculoAgregado);

			Vehiculo vehiculoAgregado = empresa.getVehiculos().get("ABC123");
			assertNotNull(vehiculoAgregado);
			verify(optionPaneMock, never()).ShowMessage(anyString());

		} catch (Exception e) {
			fail("No tendria que haber lanzado una exception: " + e.getMessage());
		}
	}

	@Test
	public void testActionPerformedNuevoPedidoExitoso() {
		try {
			empresa.agregarVehiculo(new Auto("ZXY987", 4, false)); 
			empresa.agregarCliente("user", "pass", "user");
			Cliente cliente = empresa.getClientes().get("user");
			empresa.setUsuarioLogeado(cliente);
			
			when(vistaMock.getCantidadPax()).thenReturn(3);
			when(vistaMock.isPedidoConMascota()).thenReturn(false);
			when(vistaMock.isPedidoConBaul()).thenReturn(false);
			when(vistaMock.getCantKm()).thenReturn(10);
			when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);

			ActionEvent nuevoPedidoUsuario = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_PEDIDO);
			controlador.actionPerformed(nuevoPedidoUsuario);

			Pedido pedidoAgregado = empresa.getPedidoDeCliente(cliente);
			assertNotNull("El pedido debe haber sido agregado a la lista de pedidos", pedidoAgregado);
			verify(optionPaneMock, never()).ShowMessage(anyString());
		} catch (Exception e) {
			fail("No tendria que haber lanzado una excepcion: " + e.getMessage());
		}
	}

	@Test
	public void testActionPerformedCalificarPagar() {
		try {
			empresa.agregarCliente("user", "pass", "user");
			Cliente cliente = empresa.getClientes().get("user");
			empresa.setUsuarioLogeado(cliente);
			empresa.agregarVehiculo(new Auto("123-456", 3, false));
			Chofer chofer = new ChoferTemporario("234455", "nombreChofer");
			empresa.agregarChofer(chofer);
			Pedido pedido = new Pedido(cliente, 2, false, false, 5, Constantes.ZONA_STANDARD);
			empresa.agregarPedido(pedido);
			empresa.crearViaje(empresa.getPedidos().get(cliente), chofer, empresa.getVehiculos().get("123-456"));
			
			when(vistaMock.getCalificacion()).thenReturn(5);
			
			ActionEvent nuevaCalificacionUsuario = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.CALIFICAR_PAGAR);
			controlador.actionPerformed(nuevaCalificacionUsuario);

			assertNull("El viaje debe haber finalizado y removido de la lista de activos", empresa.getViajeDeCliente(cliente));
			verify(optionPaneMock, never()).ShowMessage(anyString());

		} catch (Exception e) {
			fail("No deberia lanzarse la excepcion: " + e.getMessage());
		}
	}
	
	@Test
	public void testActionPerformedNuevoViaje(){
		try{
			empresa.agregarCliente("cliente1", "pass", "cliente1");
			Cliente cliente1 = empresa.getClientes().get("cliente1");
			Chofer choferTemp = new ChoferTemporario("123456", "choferTemp");
			empresa.agregarChofer(choferTemp);
			Vehiculo auto = new Auto("ABC123", 3, false);
			empresa.agregarVehiculo(auto);
			Pedido pedido = new Pedido(cliente1, 3, false, false, 10, Constantes.ZONA_STANDARD);
			empresa.agregarPedido(pedido);

			when(vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
			when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(choferTemp);
			when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(auto);

			ActionEvent nuevoViajeAgregado = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_VIAJE);
			this.controlador.actionPerformed(nuevoViajeAgregado);

			Viaje viajeAgregado = empresa.getViajeDeCliente(cliente1);
			assertNotNull(viajeAgregado);
			verify(optionPaneMock, never()).ShowMessage(anyString());

		} catch (Exception e){
			fail("No tendria que haber lanzado una excepcion: " + e.getMessage());
		}
	}

	@Test
	public void testActionPerformedLoginFallaUsuarioNoExiste() {
		when(vistaMock.getUsserName()).thenReturn("usuarioFalso");
		when(vistaMock.getPassword()).thenReturn("pass");
		ActionEvent login = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.LOGIN);
		controlador.actionPerformed(login);

		assertNull(this.empresa.getUsuarioLogeado());
		verify(optionPaneMock).ShowMessage(contains("Usuario")); 
	}
	
	@Test
	public void testActionPerformedRegistroFallaPasswordsNoCoinciden() {
		when(vistaMock.getRegNombreReal()).thenReturn("nombre");
		when(vistaMock.getRegUsserName()).thenReturn("usuario");
		when(vistaMock.getRegPassword()).thenReturn("pass1");
		when(vistaMock.getRegConfirmPassword()).thenReturn("pass2"); // No coinciden
		ActionEvent registrarUsuario = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.REG_BUTTON_REGISTRAR);
		controlador.actionPerformed(registrarUsuario);
		
		assertNull("El cliente no debe agregarse al modelo", empresa.getClientes().get("usuario"));
		verify(optionPaneMock).ShowMessage(contains("no coincide"));
	}

	@Test
	public void testActionPerformedNuevoChoferFallaRepetido() {
		try {
			empresa.setUsuarioLogeado(empresa.login("admin", "admin"));
			empresa.agregarChofer(new ChoferTemporario("12345", "Chofer Existente"));
			when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
			when(vistaMock.getNombreChofer()).thenReturn("Chofer Duplicado");
			when(vistaMock.getDNIChofer()).thenReturn("12345");
			
			ActionEvent nuevoChoferAgregado = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_CHOFER);
			this.controlador.actionPerformed(nuevoChoferAgregado);
			
			assertEquals("Solo debe haber 1 chofer en la empresa", 1, empresa.getChoferes().size());
			verify(optionPaneMock).ShowMessage(contains("Chofer"));

		} catch (Exception e) {
			fail("No tendria que haber lanzado una exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testActionPerformedNuevoVehiculoFallaRepetido() {
		try {
			empresa.setUsuarioLogeado(empresa.login("admin", "admin"));
			empresa.agregarVehiculo(new Auto("AAA111", 4, false));
			when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
			when(vistaMock.getPatente()).thenReturn("AAA111");
			when(vistaMock.getPlazas()).thenReturn(3);

			ActionEvent nuevoVehiculoAgregado = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_VEHICULO);
			this.controlador.actionPerformed(nuevoVehiculoAgregado);
			
			assertEquals("Solo debe haber 1 vehículo en la empresa", 1, empresa.getVehiculos().size());
			verify(optionPaneMock).ShowMessage(contains("Vehiculo"));

		} catch (Exception e) {
			fail("No tendria que haber lanzado una exception: " + e.getMessage());
		}
	}

	@Test
	public void testActionPerformedPedidoFallaConPedidoPendiente() {
		try {
			empresa.agregarVehiculo(new Auto("AAA111", 4, false)); 
			empresa.agregarCliente("user", "pass", "user");
			Cliente cliente = empresa.getClientes().get("user");
			empresa.setUsuarioLogeado(cliente);
			empresa.agregarPedido(new Pedido(cliente, 2, false, false, 5, Constantes.ZONA_STANDARD));
			
			when(vistaMock.getCantidadPax()).thenReturn(3);
			when(vistaMock.getCantKm()).thenReturn(10);

			ActionEvent nuevoPedidoUsuario = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_PEDIDO);
			controlador.actionPerformed(nuevoPedidoUsuario);

			assertEquals("Solo debe existir el pedido inicial", 1, empresa.getPedidos().size());
			verify(optionPaneMock).ShowMessage(contains("Pedido")); 
			
		} catch (Exception e) {
			fail("Error inesperado en el test: " + e.getMessage());
		}
	}
    
	@Test
	public void testActionPerformedCalificarPagarFallaSinViaje() {
		try {
			empresa.agregarCliente("user", "pass", "user");
			Cliente cliente = empresa.getClientes().get("user");
			empresa.setUsuarioLogeado(cliente);
			
			when(vistaMock.getCalificacion()).thenReturn(5);
			
			ActionEvent nuevaCalificacionUsuario = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.CALIFICAR_PAGAR);
			controlador.actionPerformed(nuevaCalificacionUsuario);

			verify(optionPaneMock).ShowMessage(contains("Viaje")); 
		} catch (Exception e) {
			fail("No deberia lanzarse la excepcion: " + e.getMessage());
		}
	}
	@Test
    public void testActionPerformedNuevoViajeFallaChoferOcupado() {
        try {
            empresa.agregarCliente("cliente1", "pass", "cliente1");
            empresa.agregarCliente("cliente2", "pass", "cliente2");
            Cliente cliente1 = empresa.getClientes().get("cliente1");
            Cliente cliente2 = empresa.getClientes().get("cliente2");  
            Chofer chofer = new ChoferTemporario("111", "Chofer");
            empresa.agregarChofer(chofer);
            Vehiculo auto1 = new Auto("AAA111", 4, false);
            Vehiculo auto2 = new Auto("BBB222", 4, false);
            empresa.agregarVehiculo(auto1);
            empresa.agregarVehiculo(auto2);
            
            Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_STANDARD);
            Pedido pedido2 = new Pedido(cliente2, 2, false, false, 10, Constantes.ZONA_STANDARD);
            empresa.agregarPedido(pedido1);
            empresa.agregarPedido(pedido2);
            empresa.crearViaje(pedido1, chofer, auto1);
            when(vistaMock.getPedidoSeleccionado()).thenReturn(pedido2);
            when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
            when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(auto2);

            ActionEvent nuevoViajeAccion = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_VIAJE);
            controlador.actionPerformed(nuevoViajeAccion);
            assertNull("El segundo viaje no debería crearse", empresa.getViajeDeCliente(cliente2));
            verify(optionPaneMock).ShowMessage(contains("Chofer")); 

        } catch (Exception e) {
            fail("Error en test de chofer ocupado: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedNuevoViajeFallaClienteConViaje() {
        try {
            empresa.agregarCliente("cliente1", "pass", "cliente1");
            Cliente cliente1 = empresa.getClientes().get("cliente1");
            Chofer chofer1 = new ChoferTemporario("111", "Chofer1");
            Chofer chofer2 = new ChoferTemporario("222", "Chofer2");
            empresa.agregarChofer(chofer1);
            empresa.agregarChofer(chofer2);            
            Vehiculo auto1 = new Auto("AAA111", 4, false);
            Vehiculo auto2 = new Auto("BBB222", 4, false);
            empresa.agregarVehiculo(auto1);
            empresa.agregarVehiculo(auto2);
            
            Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_STANDARD);

            empresa.agregarPedido(pedido1);
            empresa.crearViaje(pedido1, chofer1, auto1);
            Pedido pedido2 = new Pedido(cliente1, 2, false, false, 5, Constantes.ZONA_STANDARD); 
            when(vistaMock.getPedidoSeleccionado()).thenReturn(pedido2);
            when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer2);
            when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(auto2);

            ActionEvent nuevoViajeAccion = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, Constantes.NUEVO_VIAJE);
            controlador.actionPerformed(nuevoViajeAccion);

            verify(optionPaneMock).ShowMessage(contains("viaje"));
        } catch (Exception e) {
        	fail("no deberia lanzarse exepcion");
        }
    }
	
	@Test
	public void testActionPerformedComandoIgnorado() {
	    try {
	        empresa.agregarCliente("user", "pass", "user");
	        empresa.agregarVehiculo(new Auto("111", 4, false));
	    } catch (Exception e) { fail("Setup comando ignorado: " + e.getMessage()); }
	    
	    int clientesInicial = empresa.getClientes().size();
	    int vehiculosInicial = empresa.getVehiculos().size();
	    
	    ActionEvent comandoInexistente = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, "COMANDO_INEXISTENTE_O_INVALIDO");
	    this.controlador.actionPerformed(comandoInexistente);
	    
	    assertEquals("El número de clientes no debe cambiar", clientesInicial, empresa.getClientes().size());
	    assertEquals("El número de vehículos no debe cambiar", vehiculosInicial, empresa.getVehiculos().size());
	    
	    verify(optionPaneMock, never()).ShowMessage(anyString());
	}
}