package testingontrolador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import util.Constantes;
import vista.IVista;
import persistencia.IPersistencia;

public class TestActionPerformed {

	private Controlador c; 
	private IVista vistaMock;
	private IPersistencia persistenciaMock;
	
	@Before
	public void setUp() throws Exception {
		vistaMock = mock(IVista.class);
		persistenciaMock = mock(IPersistencia.class);
		c = mock(Controlador.class, CALLS_REAL_METHODS);
		c.setVista(vistaMock);
		assertNotNull("La vista inyectada no debe ser null", c.getVista());
		c.setPersistencia(persistenciaMock);
		assertNotNull("La persistencia inyectada no debe ser null", c.getPersistencia());

		doNothing().when(c).login();
		doNothing().when(c).logout();
		doNothing().when(c).registrar();
		doNothing().when(c).nuevoPedido();
		doNothing().when(c).calificarPagar();
		doNothing().when(c).nuevoChofer();
		doNothing().when(c).nuevoVehiculo();
		doNothing().when(c).nuevoViaje();
        
        when(c.getVista()).thenReturn(vistaMock);
	}
	@Test
	public void testActionPerformed_Login() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.LOGIN);
		c.actionPerformed(e);
		verify(c).login();
	}

	@Test
	public void testActionPerformed_Registrar() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.REG_BUTTON_REGISTRAR);
		c.actionPerformed(e);
		verify(c).registrar();
	}

	@Test
	public void testActionPerformed_LogoutCliente() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.CERRAR_SESION_CLIENTE);
		c.actionPerformed(e);
		verify(c).logout();
	}

	@Test
	public void testActionPerformed_LogoutAdmin() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.CERRAR_SESION_ADMIN);
		c.actionPerformed(e);
		verify(c).logout();
	}

	@Test
	public void testActionPerformed_NuevoPedido() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.NUEVO_PEDIDO);
		c.actionPerformed(e);
		verify(c).nuevoPedido();
	}

	@Test
	public void testActionPerformed_CalificarPagar() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.CALIFICAR_PAGAR);
		c.actionPerformed(e);
		verify(c).calificarPagar();
	}

	@Test
	public void testActionPerformed_NuevoChofer() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.NUEVO_CHOFER);
		c.actionPerformed(e);
		verify(c).nuevoChofer();
	}

	@Test
	public void testActionPerformed_NuevoVehiculo() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.NUEVO_VEHICULO);
		c.actionPerformed(e);
		verify(c).nuevoVehiculo();
	}

	@Test
	public void testActionPerformed_NuevoViaje() {
		ActionEvent e = new ActionEvent(this, 0, Constantes.NUEVO_VIAJE);
		c.actionPerformed(e);
		verify(c).nuevoViaje();
	}

	@Test
	public void testActionPerformed_ComandoIgnorado() {
		ActionEvent e = new ActionEvent(this, 0, "COMANDO_INEXISTENTE"); 
		c.actionPerformed(e);

		verify(c, never()).login();
		verify(c, never()).logout();
		verify(c, never()).registrar();
		verify(c, never()).nuevoPedido();
		verify(c, never()).calificarPagar();
		verify(c, never()).nuevoChofer();
		verify(c, never()).nuevoVehiculo();
		verify(c, never()).nuevoViaje();
        verify(c).actionPerformed(e);
	}
	
	
}