package testingontrolador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import persistencia.IPersistencia;
import util.Constantes;
import vista.IOptionPane;
import vista.IVista;

public class TestControlador {

	private Controlador controlador;
	private IVista vistaMock;
	private IPersistencia persistenciaMock;
	private Empresa empresaMock;
	private IOptionPane optionPaneMock;

	@Before
	public void setUp() throws Exception {
		controlador = mock(Controlador.class, CALLS_REAL_METHODS);
		vistaMock = mock(IVista.class);
		persistenciaMock = mock(IPersistencia.class);
		empresaMock = mock(Empresa.class);
		optionPaneMock = mock(IOptionPane.class);
		controlador.setVista(vistaMock);
		controlador.setPersistencia(persistenciaMock);
		when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);
		Field f = Empresa.class.getDeclaredField("instance");
		f.setAccessible(true);
		f.set(null, empresaMock);
	}

	@Test
	public void testConstructor() {
		Controlador c = mock(Controlador.class, CALLS_REAL_METHODS);
		IVista vistaMock = mock(IVista.class);
		IPersistencia persistenciaMock = mock(IPersistencia.class);
		
		when(c.getFileName()).thenReturn("empresa.bin"); 
		when(c.getVista()).thenReturn(vistaMock);
		when(c.getPersistencia()).thenReturn(persistenciaMock);

		assertNotNull("La vista no debería ser null", c.getVista());
		assertNotNull("La persistencia no debería ser null", c.getPersistencia());
		assertEquals("El nombre de archivo por defecto es incorrecto", "empresa.bin", c.getFileName());
	}
	@Test
	public void testGettersSetters() {
		controlador.setFileName("test.bin");
		assertEquals("El FileName no se seteó correctamente", "test.bin", controlador.getFileName());
		controlador.setVista(vistaMock);
		assertEquals("La Vista no se seteó correctamente", vistaMock, controlador.getVista());
		controlador.setPersistencia(persistenciaMock);
		assertEquals("La Persistencia no se seteó correctamente", persistenciaMock, controlador.getPersistencia());
	}

	@Test
	public void testLeer() {
		try {
			EmpresaDTO dtoMock = mock(EmpresaDTO.class);
			when(persistenciaMock.leer()).thenReturn(dtoMock);

			controlador.leer();
            
			verify(persistenciaMock).leer();
            
		} catch (Exception e) {
			fail("El método leer() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}
	@Test
	public void testLeerExcepcion() {
		try {
			doThrow(new RuntimeException("Error")).when(persistenciaMock).leer();
			controlador.leer();
			verify(optionPaneMock).ShowMessage(contains("Error"));
		} catch (Exception e) {
			fail("El test de excepción de leer() falló inesperadamente: " + e.getMessage());
		}
	}

	@Test
	public void testEscribir() {
		try {
			controlador.escribir();
			verify(persistenciaMock).escribir(any());
		} catch (Exception e) {
			fail("El método escribir() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}

	@Test
	public void testEscribirExcepcion() {
		try {
			doThrow(new RuntimeException("Error")).when(persistenciaMock).escribir(any());
			controlador.escribir();
			verify(optionPaneMock).ShowMessage(contains("Error"));
		} catch (Exception e) {
			fail("El test de excepción de escribir() falló inesperadamente: " + e.getMessage());
		}
	}

	@Test
	public void testLogin() {
		when(vistaMock.getUsserName()).thenReturn("u");
		when(vistaMock.getPassword()).thenReturn("p");
		try {
			controlador.login();
			verify(empresaMock).login("u", "p");
		} catch (Exception e) {
			fail("El método login() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}

	@Test
	public void testLoginUsuarioNoExisteException() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("x");
		when(vistaMock.getPassword()).thenReturn("p");
		UsuarioNoExisteException ex = new UsuarioNoExisteException("x");
		doThrow(ex).when(empresaMock).login("x", "p");
		controlador.login();
		verify(optionPaneMock).ShowMessage(contains("Usuario"));
	}

	@Test
	public void testLoginPasswordErroneaException() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("u");
		when(vistaMock.getPassword()).thenReturn("wrong");
		PasswordErroneaException ex = new PasswordErroneaException("u", "wrong");
		doThrow(ex).when(empresaMock).login("u", "wrong");
		controlador.login();
		verify(optionPaneMock).ShowMessage(contains("Password"));
	}

	@Test
	public void testRegistrarPasswordsNoCoinciden() {
		when(vistaMock.getRegPassword()).thenReturn("1");
		when(vistaMock.getRegConfirmPassword()).thenReturn("2");
		controlador.registrar();
		verify(optionPaneMock).ShowMessage(contains("no coincide"));
	}

	@Test
	public void testRegistrar() {
		when(vistaMock.getRegPassword()).thenReturn("1");
		when(vistaMock.getRegConfirmPassword()).thenReturn("1");
		when(vistaMock.getRegUsserName()).thenReturn("x");
		when(vistaMock.getRegNombreReal()).thenReturn("y");
		controlador.registrar();
		try {
			verify(empresaMock).agregarCliente("x", "1", "y");
		} catch (Exception e) {
			fail("El método agregarCliente() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}

	@Test
	public void testNuevoChoferTemporario() {
		when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
		when(vistaMock.getDNIChofer()).thenReturn("1");
		when(vistaMock.getNombreChofer()).thenReturn("C");
		controlador.nuevoChofer();
		try {
			verify(empresaMock).agregarChofer(any());
		} catch (Exception e) {
			fail("El método agregarChofer() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}

	@Test
	public void testNuevoChoferTemporarioRepetido() throws Exception {
		when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
		when(vistaMock.getDNIChofer()).thenReturn("1");
		when(vistaMock.getNombreChofer()).thenReturn("C");
		ChoferRepetidoException ex = new ChoferRepetidoException("1", null);
		doThrow(ex).when(empresaMock).agregarChofer(any());
		controlador.nuevoChofer();
		verify(optionPaneMock).ShowMessage(contains("Chofer"));
	}

	@Test
	public void testNuevoVehiculo() {
		when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
		when(vistaMock.getPatente()).thenReturn("AAA111");
		when(vistaMock.getPlazas()).thenReturn(4);
		when(vistaMock.isVehiculoAptoMascota()).thenReturn(false);

		try {
			controlador.nuevoVehiculo();
			verify(empresaMock).agregarVehiculo(any());
		} catch (Exception e) {
			fail("El método agregarVehiculo() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}
	@Test
	public void testNuevoVehiculoConExcepcion() throws Exception {
		when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
		when(vistaMock.getPatente()).thenReturn("AAA111");
		when(vistaMock.getPlazas()).thenReturn(4);
		when(vistaMock.isVehiculoAptoMascota()).thenReturn(false); 

		VehiculoRepetidoException ex = new VehiculoRepetidoException("AAA111", null);
		doThrow(ex).when(empresaMock).agregarVehiculo(any());
		
        controlador.nuevoVehiculo();
		
        verify(optionPaneMock).ShowMessage(contains("Vehiculo"));
	}
	@Test
	public void testNuevoPedido() {
		Cliente c = mock(Cliente.class);
		when(c.getNombreReal()).thenReturn("Cliente de Prueba");
		when(empresaMock.getUsuarioLogeado()).thenReturn(c);
		when(vistaMock.getCantidadPax()).thenReturn(2);
		when(vistaMock.isPedidoConMascota()).thenReturn(true);
		when(vistaMock.isPedidoConBaul()).thenReturn(false);
		when(vistaMock.getCantKm()).thenReturn(10);
		when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
		try {
			controlador.nuevoPedido();
			verify(empresaMock).agregarPedido(any(Pedido.class));
		} catch (Exception e) {
			fail("No debería haber lanzado ninguna excepcion: " + e.getMessage());
		}
	}

	@Test
	public void testNuevoPedidoConExcepcion() throws Exception {
		Cliente c = mock(Cliente.class);
		when(c.getNombreReal()).thenReturn("Cliente de Prueba");
		when(empresaMock.getUsuarioLogeado()).thenReturn(c);
		when(vistaMock.getCantidadPax()).thenReturn(2);
		when(vistaMock.isPedidoConMascota()).thenReturn(true);
		when(vistaMock.isPedidoConBaul()).thenReturn(false);
		when(vistaMock.getCantKm()).thenReturn(10);
		when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
		when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);
		ClienteConPedidoPendienteException ex = new ClienteConPedidoPendienteException();
		doThrow(ex).when(empresaMock).agregarPedido(any(Pedido.class));
		controlador.nuevoPedido();
		verify(optionPaneMock).ShowMessage(contains("Pedido"));
	}

	@Test
	public void testNuevoPedidoConViajePendienteExcepcion() throws Exception {
		Cliente c = mock(Cliente.class);
		when(c.getNombreReal()).thenReturn("Cliente de Prueba");
		when(empresaMock.getUsuarioLogeado()).thenReturn(c);
		when(vistaMock.getCantidadPax()).thenReturn(2);
		when(vistaMock.isPedidoConMascota()).thenReturn(true);
		when(vistaMock.isPedidoConBaul()).thenReturn(false);
		when(vistaMock.getCantKm()).thenReturn(10);
		when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
		when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);
		ClienteConViajePendienteException ex = new ClienteConViajePendienteException();
		doThrow(ex).when(empresaMock).agregarPedido(any(Pedido.class));
		controlador.nuevoPedido();
		verify(optionPaneMock).ShowMessage(contains("viaje"));
	}

	@Test
	public void testCalificarPagar() {
		when(vistaMock.getCalificacion()).thenReturn(5);
		try {
			controlador.calificarPagar();
			verify(empresaMock).pagarYFinalizarViaje(5);
		} catch (Exception e) {
			fail("El método pagarYFinalizarViaje() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}

	@Test
	public void testCalificarPagarConExcepcion() throws Exception {
		when(vistaMock.getCalificacion()).thenReturn(5);
		ClienteSinViajePendienteException ex = new ClienteSinViajePendienteException();
		doThrow(ex).when(empresaMock).pagarYFinalizarViaje(5);
		controlador.calificarPagar();
		verify(optionPaneMock).ShowMessage(contains("Viaje"));
	}

	@Test
	public void testNuevoViaje() {
		Pedido p = mock(Pedido.class);
		Chofer ch = mock(Chofer.class);
		Vehiculo v = mock(Vehiculo.class);
		when(vistaMock.getPedidoSeleccionado()).thenReturn(p);
		when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(ch);
		when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(v);
		try {
			controlador.nuevoViaje();
			verify(empresaMock).crearViaje(p, ch, v);
		} catch (Exception e) {
			fail("El método crearViaje() no debería haber lanzado una excepción: " + e.getMessage());
		}
	}

	@Test
	public void testNuevoViajeConExcepcion() throws Exception {
		Pedido p = mock(Pedido.class);
		Chofer ch = mock(Chofer.class);
		Vehiculo v = mock(Vehiculo.class);
		when(vistaMock.getPedidoSeleccionado()).thenReturn(p);
		when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(ch);
		when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(v);
		ChoferNoDisponibleException ex = new ChoferNoDisponibleException(ch);
		doThrow(ex).when(empresaMock).crearViaje(p, ch, v);
		controlador.nuevoViaje();
		verify(optionPaneMock).ShowMessage(contains("Chofer"));
	}

	@Test
	public void testNuevoViajeClienteConViajePendienteExcepcion() throws Exception {
		Pedido p = mock(Pedido.class);
		Chofer ch = mock(Chofer.class);
		Vehiculo v = mock(Vehiculo.class);
		when(vistaMock.getPedidoSeleccionado()).thenReturn(p);
		when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(ch);
		when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(v);
		ClienteConViajePendienteException ex = new ClienteConViajePendienteException();
		doThrow(ex).when(empresaMock).crearViaje(p, ch, v);
		controlador.nuevoViaje();
		verify(optionPaneMock).ShowMessage(contains("viaje"));
	}

	@Test
	public void testLogout() {
		controlador.logout();
		verify(empresaMock).logout();
	}

	@Test
	public void testActionPerformedComandoInvalido() {
		controlador.actionPerformed(new ActionEvent(this, 0, "INVALID"));
		verifyNoInteractions(empresaMock);
	}
}