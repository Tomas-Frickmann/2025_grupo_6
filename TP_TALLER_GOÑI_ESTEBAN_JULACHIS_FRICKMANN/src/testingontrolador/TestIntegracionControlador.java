package testingontrolador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
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

public class TestIntegracionControlador {

	private Controlador controlador;
	private IVista vistaMock;
	private IPersistencia persistenciaMock;
	private Empresa empresa;
	private IOptionPane optionPaneMock;

	@Before
	public void setUp() throws Exception {
		vistaMock = mock(IVista.class);
		persistenciaMock = mock(IPersistencia.class);
		optionPaneMock = mock(IOptionPane.class);
		when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);

		controlador = mock(Controlador.class, CALLS_REAL_METHODS);
		controlador.setVista(vistaMock);
		controlador.setPersistencia(persistenciaMock);

		try {
			Field f = Empresa.class.getDeclaredField("instance");
			f.setAccessible(true);
			f.set(null, null);
		} catch (Exception e) {
			fail("Falló la reflexión al resetear el Singleton de Empresa: " + e.getMessage());
		}

		empresa = Empresa.getInstance();
	}

	@Test
	public void testIntegracionRegistrarYLoginExitoso() throws Exception {
		when(vistaMock.getRegPassword()).thenReturn("pass123");
		when(vistaMock.getRegConfirmPassword()).thenReturn("pass123");
		when(vistaMock.getRegUsserName()).thenReturn("nuevoCliente");
		when(vistaMock.getRegNombreReal()).thenReturn("Nombre Real");

		controlador.registrar();

		assertNotNull("El cliente no se agregó a la empresa", empresa.getClientes().get("nuevoCliente"));
		verify(optionPaneMock, never()).ShowMessage(anyString());

		when(vistaMock.getUsserName()).thenReturn("nuevoCliente");
		when(vistaMock.getPassword()).thenReturn("pass123");

		controlador.login();

		assertNotNull("La empresa real debería tener un usuario logueado", empresa.getUsuarioLogeado());
		assertEquals("El usuario logueado no es el correcto", "nuevoCliente", empresa.getUsuarioLogeado().getNombreUsuario());
	}

	@Test
	public void testIntegracionLoginUsuarioNoExiste() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("userFalso");
		when(vistaMock.getPassword()).thenReturn("123");

		controlador.login();

		assertNull("La empresa no debería tener ningún usuario logueado", empresa.getUsuarioLogeado());
		verify(optionPaneMock).ShowMessage(contains("Usuario"));
	}

	@Test
	public void testIntegracionLoginPasswordErronea() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("admin");
		when(vistaMock.getPassword()).thenReturn("passIncorrecta");

		controlador.login();

		assertNull("La empresa no debería tener ningún usuario logueado", empresa.getUsuarioLogeado());
		verify(optionPaneMock).ShowMessage(contains("Contrasena"));
	}

	@Test
	public void testIntegracionLogout() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("admin");
		when(vistaMock.getPassword()).thenReturn("admin");
		controlador.login();
		assertNotNull("El admin debería estar logueado", empresa.getUsuarioLogeado());

		controlador.logout();

		assertNull("El usuario debería ser null después del logout", empresa.getUsuarioLogeado());
		verify(persistenciaMock).escribir(any(EmpresaDTO.class));
	}

	@Test
	public void testIntegracionCrearPedidoExitoso() throws Exception {
		empresa.agregarCliente("clientePrueba", "123", "Cliente de Prueba");
		when(vistaMock.getUsserName()).thenReturn("clientePrueba");
		when(vistaMock.getPassword()).thenReturn("123");
		controlador.login();

		empresa.agregarVehiculo(new Auto("AAA111", 4, false));

		when(vistaMock.getCantidadPax()).thenReturn(2);
		when(vistaMock.isPedidoConMascota()).thenReturn(false);
		when(vistaMock.isPedidoConBaul()).thenReturn(false);
		when(vistaMock.getCantKm()).thenReturn(10);
		when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);

		controlador.nuevoPedido();

		Cliente clienteLogueado = (Cliente) empresa.getUsuarioLogeado();
		assertNotNull("Debería haber un pedido activo en la empresa", empresa.getPedidoDeCliente(clienteLogueado));
		verify(optionPaneMock, never()).ShowMessage(anyString());
	}

	@Test
	public void testIntegracionCrearPedidoFallaConPedidoPendiente() throws Exception {
		empresa.agregarCliente("clientePrueba", "123", "Cliente de Prueba");
		empresa.agregarVehiculo(new Auto("AAA111", 4, false));
		when(vistaMock.getUsserName()).thenReturn("clientePrueba");
		when(vistaMock.getPassword()).thenReturn("123");
		controlador.login();

		when(vistaMock.getCantidadPax()).thenReturn(2);
		when(vistaMock.getCantKm()).thenReturn(10);
		when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
		controlador.nuevoPedido();

		controlador.nuevoPedido();

		verify(optionPaneMock).ShowMessage(contains("Pedido"));
	}

	@Test
	public void testIntegracionCalificarSinViaje() throws Exception {
		empresa.agregarCliente("clienteCalifica", "123", "Cliente Califica");
		when(vistaMock.getUsserName()).thenReturn("clienteCalifica");
		when(vistaMock.getPassword()).thenReturn("123");
		controlador.login();

		when(vistaMock.getCalificacion()).thenReturn(5);
		controlador.calificarPagar();

		verify(optionPaneMock).ShowMessage(contains("Viaje"));
	}

	@Test
	public void testIntegracionCrearVehiculoYChofer() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("admin");
		when(vistaMock.getPassword()).thenReturn("admin");
		controlador.login();

		when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
		when(vistaMock.getPatente()).thenReturn("AAA111");
		when(vistaMock.getPlazas()).thenReturn(4);
		when(vistaMock.isVehiculoAptoMascota()).thenReturn(true);
		controlador.nuevoVehiculo();
		assertNotNull("El vehículo no se agregó a la empresa", empresa.getVehiculos().get("AAA111"));

		when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
		when(vistaMock.getDNIChofer()).thenReturn("123456");
		when(vistaMock.getNombreChofer()).thenReturn("Chofer Temp");
		controlador.nuevoChofer();
		assertNotNull("El chofer no se agregó a la empresa", empresa.getChoferes().get("123456"));
	}

	@Test
	public void testIntegracionNuevoChoferRepetido() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("admin");
		when(vistaMock.getPassword()).thenReturn("admin");
		controlador.login();
		when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
		when(vistaMock.getDNIChofer()).thenReturn("12345");
		when(vistaMock.getNombreChofer()).thenReturn("Chofer Uno");
		controlador.nuevoChofer();

		when(vistaMock.getNombreChofer()).thenReturn("Chofer Dos");
		controlador.nuevoChofer();

		verify(optionPaneMock).ShowMessage(contains("Chofer"));
		assertEquals("Solo debería haber un chofer en la empresa", 1, empresa.getChoferes().size());
	}

	@Test
	public void testIntegracionNuevoVehiculoRepetido() throws Exception {
		when(vistaMock.getUsserName()).thenReturn("admin");
		when(vistaMock.getPassword()).thenReturn("admin");
		controlador.login();
		when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
		when(vistaMock.getPatente()).thenReturn("ABC111");
		when(vistaMock.getPlazas()).thenReturn(4);
		when(vistaMock.isVehiculoAptoMascota()).thenReturn(false);
		controlador.nuevoVehiculo();

		controlador.nuevoVehiculo();

		verify(optionPaneMock).ShowMessage(contains("Vehiculo"));
		assertEquals("Solo debería haber un vehículo en la empresa", 1, empresa.getVehiculos().size());
	}

	@Test
	public void testIntegracionNuevoViajeYCalificar() throws Exception {
		empresa.agregarCliente("clienteFlow", "123", "Cliente Flujo");
		when(vistaMock.getUsserName()).thenReturn("clienteFlow");
		when(vistaMock.getPassword()).thenReturn("123");
		controlador.login();

		empresa.agregarVehiculo(new Auto("FLUJO1", 4, false));

		when(vistaMock.getCantidadPax()).thenReturn(2);
		when(vistaMock.getCantKm()).thenReturn(10);
		when(vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
		when(vistaMock.isPedidoConBaul()).thenReturn(false);
		when(vistaMock.isPedidoConMascota()).thenReturn(false);
		controlador.nuevoPedido();

		Cliente clienteReal = (Cliente) empresa.getUsuarioLogeado();
		Pedido pedidoCreado = empresa.getPedidoDeCliente(clienteReal);
		assertNotNull("El pedido no se creó en la empresa", pedidoCreado);

		controlador.logout();

		when(vistaMock.getUsserName()).thenReturn("admin");
		when(vistaMock.getPassword()).thenReturn("admin");
		controlador.login();

		Vehiculo vehiculoCreado = empresa.getVehiculos().get("FLUJO1");

		when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
		when(vistaMock.getDNIChofer()).thenReturn("789012");
		when(vistaMock.getNombreChofer()).thenReturn("Chofer Flow");
		controlador.nuevoChofer();
		Chofer choferCreado = empresa.getChoferes().get("789012");
		assertNotNull("El chofer no se creó en la empresa", choferCreado);

		when(vistaMock.getPedidoSeleccionado()).thenReturn(pedidoCreado);
		when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(choferCreado);
		when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculoCreado);

		controlador.nuevoViaje();

		assertNull("El pedido debe desaparecer de la lista de pedidos", empresa.getPedidoDeCliente(clienteReal));
		assertNotNull("Debería existir un viaje iniciado para el cliente", empresa.getViajeDeCliente(clienteReal));

		controlador.logout();

		when(vistaMock.getUsserName()).thenReturn("clienteFlow");
		when(vistaMock.getPassword()).thenReturn("123");
		controlador.login();

		when(vistaMock.getCalificacion()).thenReturn(5);

		controlador.calificarPagar();

		assertNull("El viaje ya no debe estar iniciado", empresa.getViajeDeCliente(clienteReal));
		assertEquals("El viaje debe estar en la lista de terminados", 1, empresa.getViajesTerminados().size());
		verify(optionPaneMock, never()).ShowMessage(anyString());
	}
}