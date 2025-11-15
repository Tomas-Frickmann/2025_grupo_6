package testingontrolador;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import mockit.Mocked;

import org.junit.Before;
import org.junit.BeforeClass;
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
import vista.Ventana;

public class TestControlador {

    @Mocked
    private Ventana ventanaMock;

    private Controlador controlador;
    private IVista vistaMock;
    private IPersistencia persistenciaMock;
    private Empresa empresaMock;
    private IOptionPane optionPaneMock;

    @BeforeClass
    public static void setUpHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @Before
    public void setUp() throws Exception {
        vistaMock = mock(IVista.class);
        persistenciaMock = mock(IPersistencia.class);
        empresaMock = mock(Empresa.class);
        optionPaneMock = mock(IOptionPane.class);

        when(vistaMock.getOptionPane()).thenReturn(optionPaneMock);

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
        assertNotNull(c.getVista());
        assertNotNull(c.getPersistencia());
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
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLeerExcepcion() {
        try {
            doThrow(new RuntimeException("Error")).when(persistenciaMock).leer();
            controlador.leer();
            verify(optionPaneMock).ShowMessage(contains("Error"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEscribir() {
        try {
            controlador.escribir();
            verify(persistenciaMock).escribir(any());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEscribirExcepcion() {
        try {
            doThrow(new RuntimeException("Error")).when(persistenciaMock).escribir(any());
            controlador.escribir();
            verify(optionPaneMock).ShowMessage(contains("Error"));
        } catch (Exception e) {
            fail();
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
            fail();
        }
    }

    @Test
    public void testLoginUsuarioNoExisteException() throws Exception {
        when(vistaMock.getUsserName()).thenReturn("x");
        when(vistaMock.getPassword()).thenReturn("p");

        UsuarioNoExisteException ex = mock(UsuarioNoExisteException.class);

        doThrow(ex).when(empresaMock).login(anyString(), anyString());

        controlador.login();
        verify(optionPaneMock).ShowMessage(contains("Usuario"));
    }

    @Test
    public void testLoginPasswordErroneaException() throws Exception {
        when(vistaMock.getUsserName()).thenReturn("u");
        when(vistaMock.getPassword()).thenReturn("wrong");

        PasswordErroneaException ex = mock(PasswordErroneaException.class);

        doThrow(ex).when(empresaMock).login(anyString(), anyString());

        controlador.login();
        verify(optionPaneMock).ShowMessage(contains("Contraseña"));
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
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testNuevoChoferTemporario() {
        when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
        when(vistaMock.getDNIChofer()).thenReturn("1");
        when(vistaMock.getNombreChofer()).thenReturn("C");

        controlador.nuevoChofer();

        try { verify(empresaMock).agregarChofer(any()); }
        catch (Exception e) { fail(); }
    }

    @Test
    public void testNuevoChoferTemporarioRepetido() throws Exception {
        when(vistaMock.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
        when(vistaMock.getDNIChofer()).thenReturn("1");
        when(vistaMock.getNombreChofer()).thenReturn("C");

        ChoferRepetidoException ex = mock(ChoferRepetidoException.class);

        doThrow(ex).when(empresaMock).agregarChofer(any());

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
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testNuevoVehiculoConExcepcion() throws Exception {
        when(vistaMock.getTipoVehiculo()).thenReturn(Constantes.AUTO);
        when(vistaMock.getPatente()).thenReturn("AAA111");

        VehiculoRepetidoException ex = mock(VehiculoRepetidoException.class);

        doThrow(ex).when(empresaMock).agregarVehiculo(any());

        controlador.nuevoVehiculo();
        verify(optionPaneMock).ShowMessage(contains("Vehículo"));
    }

    @Test
    public void testNuevoPedido() {
        Cliente c = mock(Cliente.class);
        when(empresaMock.getUsuarioLogeado()).thenReturn(c);
        when(vistaMock.getCantidadPax()).thenReturn(2);
        when(vistaMock.isPedidoConMascota()).thenReturn(true);
        when(vistaMock.isPedidoConBaul()).thenReturn(false);
        when(vistaMock.getCantKm()).thenReturn(10);

        try {
            controlador.nuevoPedido();
            verify(empresaMock).agregarPedido(any());
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testNuevoPedidoConExcepcion() throws Exception {
        Cliente c = mock(Cliente.class);
        when(empresaMock.getUsuarioLogeado()).thenReturn(c);
        when(vistaMock.getCantidadPax()).thenReturn(2);
        when(vistaMock.isPedidoConMascota()).thenReturn(true);
        when(vistaMock.isPedidoConBaul()).thenReturn(false);
        when(vistaMock.getCantKm()).thenReturn(10);

        ClienteConPedidoPendienteException ex = mock(ClienteConPedidoPendienteException.class);

        doThrow(ex).when(empresaMock).agregarPedido(any());

        controlador.nuevoPedido();
        verify(optionPaneMock).ShowMessage(contains("Pedido"));
    }

    @Test
    public void testNuevoPedidoConViajePendienteExcepcion() throws Exception {
        Cliente c = mock(Cliente.class);
        when(empresaMock.getUsuarioLogeado()).thenReturn(c);
        when(vistaMock.getCantidadPax()).thenReturn(2);
        when(vistaMock.isPedidoConMascota()).thenReturn(true);
        when(vistaMock.isPedidoConBaul()).thenReturn(false);
        when(vistaMock.getCantKm()).thenReturn(10);

        ClienteConViajePendienteException ex = mock(ClienteConViajePendienteException.class);

        doThrow(ex).when(empresaMock).agregarPedido(any());

        controlador.nuevoPedido();
        verify(optionPaneMock).ShowMessage(contains("Viaje"));
    }

    @Test
    public void testCalificarPagar() {
        when(vistaMock.getCalificacion()).thenReturn(5);

        try {
            controlador.calificarPagar();
            verify(empresaMock).pagarYFinalizarViaje(5);
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testCalificarPagarConExcepcion() throws Exception {
        when(vistaMock.getCalificacion()).thenReturn(5);

        ClienteSinViajePendienteException ex = mock(ClienteSinViajePendienteException.class);

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
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testNuevoViajeConExcepcion() throws Exception {
        Pedido p = mock(Pedido.class);
        Chofer ch = mock(Chofer.class);
        Vehiculo v = mock(Vehiculo.class);

        when(vistaMock.getPedidoSeleccionado()).thenReturn(p);
        when(vistaMock.getChoferDisponibleSeleccionado()).thenReturn(ch);
        when(vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(v);

        ChoferNoDisponibleException ex = mock(ChoferNoDisponibleException.class);

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

        ClienteConViajePendienteException ex = mock(ClienteConViajePendienteException.class);

        doThrow(ex).when(empresaMock).crearViaje(p, ch, v);

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
        controlador.actionPerformed(new ActionEvent(this, 0, "INVALID"));
        assertTrue(true);
    }
}
