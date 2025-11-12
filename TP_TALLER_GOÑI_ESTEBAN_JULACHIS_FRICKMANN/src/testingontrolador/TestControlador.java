package testingontrolador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import bin.excepciones.ChoferNoDisponibleException;
import bin.excepciones.ClienteConViajePendienteException;
import bin.excepciones.PasswordErroneaException;
import bin.excepciones.UsuarioNoExisteException;
import controlador.Controlador;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
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
        vistaMock = mock(IVista.class);
        persistenciaMock = mock(IPersistencia.class);
        empresaMock = mock(Empresa.class);
        optionPaneMock = mock(IOptionPane.class);

        when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);

        // ⚙️ Reemplazar Empresa singleton con mock
        Field instancia = Empresa.class.getDeclaredField("instance");
        instancia.setAccessible(true);
        instancia.set(null, empresaMock);

        controlador = new Controlador();
        controlador.setVista(vistaMock);
        controlador.setPersistencia(persistenciaMock);
    }

    @Test
    public void testConstructor() {
        Controlador c = new Controlador();
        assertNotNull("Vista debe inicializarse", c.getVista());
        assertNotNull("Persistencia debe inicializarse", c.getPersistencia());
        assertEquals("empresa.bin", c.getFileName());
    }

    @Test
    public void testGettersSetters() {
        controlador.setFileName("test.bin");
        assertEquals("test.bin", controlador.getFileName());
        controlador.setVista(vistaMock);
        assertEquals(vistaMock, controlador.getVista());
        controlador.setPersistencia(persistenciaMock);
        assertEquals(persistenciaMock, controlador.getPersistencia());
    }

    @Test
    public void testLeer() {
        try {
            controlador.leer();
            verify(persistenciaMock).leer();
            assertTrue(true);
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void testLeerExcepcion() {
        try {
            doThrow(new RuntimeException("Error de lectura")).when(persistenciaMock).leer();
            controlador.leer();
            verify(optionPaneMock).ShowMessage(contains("Error"));
        } catch (Exception e) {
            fail("No debería propagarse excepción");
        }
    }

    @Test
    public void testEscribir() {
        try {
            controlador.escribir();
            verify(persistenciaMock).escribir(any());
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
    }

    @Test
    public void testEscribirExcepcion() {
        try {
            doThrow(new RuntimeException("Error escritura")).when(persistenciaMock).escribir(any());
            controlador.escribir();
            verify(optionPaneMock).ShowMessage(contains("Error"));
        } catch (Exception e) {
            fail("No debería propagarse excepción");
        }
    }


    @Test
    public void testLogin() {
        when(vistaMock.getUsserName()).thenReturn("usuario");
        when(vistaMock.getPassword()).thenReturn("clave");

        try {
            controlador.login();
            verify(empresaMock).login("usuario", "clave");
        } catch (Exception e) {
            fail("No debería fallar login: " + e.getMessage());
        }
    }

    @Test
    public void testLoginUsuarioNoExisteException() throws Exception {
        when(vistaMock.getUsserName()).thenReturn("usuarioInexistente");
        when(vistaMock.getPassword()).thenReturn("clave");
        doThrow(new UsuarioNoExisteException("usuarioInexistente", "clave")).when(empresaMock).login(anyString(), anyString());
        controlador.login();
        verify(optionPaneMock).ShowMessage(contains("Usuario"));
    }

    @Test
    public void testLoginPasswordErroneaException() throws Exception {
        when(vistaMock.getUsserName()).thenReturn("usuario");
        when(vistaMock.getPassword()).thenReturn("claveIncorrecta");
        doThrow(new PasswordErroneaException("usuario", "claveIncorrecta", "claveReal")).when(empresaMock).login(anyString(), anyString());
        controlador.login();
        verify(optionPaneMock).ShowMessage(contains("Contraseña"));
    }
    @Test
    public void testRegistrarPasswordsNoCoinciden() {
        when(vistaMock.getRegPassword()).thenReturn("123");
        when(vistaMock.getRegConfirmPassword()).thenReturn("456");

        controlador.registrar();
        verify(optionPaneMock).ShowMessage(contains("no coincide"));
    }

    @Test
    public void testRegistrar() {
        when(vistaMock.getRegPassword()).thenReturn("123");
        when(vistaMock.getRegConfirmPassword()).thenReturn("123");
        when(vistaMock.getRegUsserName()).thenReturn("juan");
        when(vistaMock.getRegNombreReal()).thenReturn("Juan Perez");
        controlador.registrar();
        try {
            verify(empresaMock).agregarCliente("juan", "123", "Juan Perez");
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void testNuevoChoferTemporario() {
        when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
        when(vistaMock.getDNIChofer()).thenReturn("123");
        when(vistaMock.getNombreChofer()).thenReturn("Carlos");
        controlador.nuevoChofer();
        try {
			verify(empresaMock).agregarChofer(any());
		} catch (ChoferRepetidoException e) {
			fail("no se deberia lanzar una exepcion");
		}
    }
    @Test
    public void testNuevoChoferTemporarioRepetido() throws ChoferRepetidoException {
        when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
        when(vistaMock.getDNIChofer()).thenReturn("123");
        when(vistaMock.getNombreChofer()).thenReturn("Carlos");
        Chofer choferDuplicado = mock(Chofer.class);
        doThrow(new ChoferRepetidoException("12345678", choferDuplicado)).when(empresaMock).agregarChofer(any());
        controlador.nuevoChofer();
        verify(optionPaneMock).ShowMessage(contains("Chofer"));
    }
    @Test
    public void testNuevoChoferPermanente() {
        when(vistaMock.getTipoChofer()).thenReturn(Constantes.PERMANENTE);
        when(vistaMock.getDNIChofer()).thenReturn("123");
        when(vistaMock.getNombreChofer()).thenReturn("Carlos");
        when(vistaMock.getAnioChofer()).thenReturn(2010);
        when(vistaMock.getHijosChofer()).thenReturn(2);

        controlador.nuevoChofer();

        try {
			verify(empresaMock).agregarChofer(any());
		} catch (ChoferRepetidoException e) {
			fail("no se deberia lanzar una exepcion");
		}
    }

    @Test
    public void testNuevoChoferPermanenteRepetido() throws ChoferRepetidoException {
        when(vistaMock.getTipoChofer()).thenReturn(Constantes.PERMANENTE);
        when(vistaMock.getDNIChofer()).thenReturn("123");
        when(vistaMock.getNombreChofer()).thenReturn("Carlos");
        when(vistaMock.getAnioChofer()).thenReturn(2010);
        when(vistaMock.getHijosChofer()).thenReturn(2);
        Chofer choferDuplicado = mock(Chofer.class);
        doThrow(new ChoferRepetidoException("12345678", choferDuplicado)).when(empresaMock).agregarChofer(any());
        controlador.nuevoChofer();
        verify(optionPaneMock).ShowMessage(contains("Chofer"));
    }

    @Test
    public void testNuevoVehiculo() {
        when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
        when(vistaMock.getPatente()).thenReturn("AAA111");
        try {
            controlador.nuevoVehiculo();
            verify(empresaMock).agregarVehiculo(any());
        } catch (VehiculoRepetidoException e) {
            fail("No se debería lanzar excepción");
        }
    }

    @Test
    public void testNuevoVehiculoConExcepcion() throws Exception {
        when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
        when(vistaMock.getPatente()).thenReturn("AAA111");
        Vehiculo vehiculoDuplicado = mock(Vehiculo.class);
        doThrow(new VehiculoRepetidoException("AAA111", vehiculoDuplicado)).when(empresaMock).agregarVehiculo(any());
        controlador.nuevoVehiculo();
        verify(optionPaneMock).ShowMessage(contains("Vehículo"));
    }

    @Test
    public void testNuevoPedido() {
        Cliente clienteMock = mock(Cliente.class);
        when(empresaMock.getUsuarioLogeado()).thenReturn(clienteMock);
        when(vistaMock.getCantidadPax()).thenReturn(2);
        when(vistaMock.isPedidoConMascota()).thenReturn(true);
        when(vistaMock.isPedidoConBaul()).thenReturn(false);
        when(vistaMock.getCantKm()).thenReturn(10);
        try {
            controlador.nuevoPedido();
            verify(empresaMock).agregarPedido(any());
        } catch (Exception e) {
            fail("No se debería lanzar excepción");
        }
    }

    @Test
    public void testNuevoPedidoConExcepcion() throws Exception {
        Cliente clienteMock = mock(Cliente.class);
        when(empresaMock.getUsuarioLogeado()).thenReturn(clienteMock);
        when(vistaMock.getCantidadPax()).thenReturn(2);
        when(vistaMock.isPedidoConMascota()).thenReturn(true);
        when(vistaMock.isPedidoConBaul()).thenReturn(false);
        when(vistaMock.getCantKm()).thenReturn(10);
        doThrow(new ClienteConPedidoPendienteException()).when(empresaMock).agregarPedido(any());
        controlador.nuevoPedido();
        verify(optionPaneMock).ShowMessage(contains("Pedido"));
    }
    @Test
    public void testNuevoPedidoConViajePendienteExcepcion() throws Exception {
        Cliente clienteMock = mock(Cliente.class);
        when(empresaMock.getUsuarioLogeado()).thenReturn(clienteMock);
        when(vistaMock.getCantidadPax()).thenReturn(2);
        when(vistaMock.isPedidoConMascota()).thenReturn(true);
        when(vistaMock.isPedidoConBaul()).thenReturn(false);
        when(vistaMock.getCantKm()).thenReturn(10);
        
        doThrow(new ClienteConViajePendienteException("cliente")).when(empresaMock).agregarPedido(any());
        controlador.nuevoPedido();
        verify(optionPaneMock).ShowMessage(contains("Viaje"));
    }


    @Test
    public void testCalificarPagar() {
        when(vistaMock.getCalificacion()).thenReturn(5);
        try {
            controlador.calificarPagar();
            verify(empresaMock).pagarYFinalizarViaje(5);
        } catch (ClienteSinViajePendienteException e) {
            fail("No se debería lanzar excepción");
        }
    }

    @Test
    public void testCalificarPagarConExcepcion() throws Exception {
        when(vistaMock.getCalificacion()).thenReturn(5);
        doThrow(new ClienteSinViajePendienteException()).when(empresaMock).pagarYFinalizarViaje(5);
        controlador.calificarPagar();
        verify(optionPaneMock).ShowMessage(contains("Viaje"));
    }

    @Test
    public void testNuevoViaje() {
        Pedido pedido = mock(Pedido.class);
        Chofer chofer = mock(Chofer.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        when(vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        try {
            controlador.nuevoViaje();
            verify(empresaMock).crearViaje(pedido, chofer, vehiculo);
        } catch (Exception e) {
            fail("No se debería lanzar excepción");
        }
    }

    @Test
    public void testNuevoViajeConExcepcion() throws Exception {
        Pedido pedido = mock(Pedido.class);
        Chofer chofer = mock(Chofer.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        when(vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        doThrow(new ChoferNoDisponibleException("12345678", chofer)).when(empresaMock).crearViaje(pedido, chofer, vehiculo);
        controlador.nuevoViaje();
        verify(optionPaneMock).ShowMessage(contains("Chofer"));
    }
    @Test
    public void testNuevoViajeClienteConViajePendienteExcepcion() throws Exception {
        Pedido pedido = mock(Pedido.class);
        Chofer chofer = mock(Chofer.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        when(vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);

        doThrow(new ClienteConViajePendienteException("cliente")).when(empresaMock).crearViaje(pedido, chofer, vehiculo);
        controlador.nuevoViaje();
        verify(optionPaneMock).ShowMessage(contains("Viaje"));
    }

    @Test
    public void testLogout() {
        controlador.logout();
        verify(empresaMock).logout();
    }
    @Test
    public void testActionPerformedComandoInvalido() {
        controlador.actionPerformed(new ActionEvent(this, 0, "COMANDO_INVALIDO"));
        assertTrue("El controlador no debería lanzar excepción ante comandos desconocidos", true);
    }

}
