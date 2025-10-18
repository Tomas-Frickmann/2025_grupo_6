package testingDatos;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistencia.EmpresaDTO;

public class TestPersistenciaEmpersaDTO implements Serializable {


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorDTO() {
		EmpresaDTO edto = new EmpresaDTO();
		assertNotNull("Los choferes deberían estar inicializados", edto.getChoferes());
        assertNotNull("Los choferes desocupados deberían estar inicializados", edto.getChoferesDesocupados());
        assertNotNull("Los clientes deberían estar inicializados", edto.getClientes());
        assertNotNull("Los pedidos deberían estar inicializados", edto.getPedidos());
        assertNotNull("Los vehículos deberían estar inicializados", edto.getVehiculos());
        assertNotNull("Los vehículos desocupados deberían estar inicializados", edto.getVehiculosDesocupados());
        assertNotNull("Los viajes iniciados deberían estar inicializados", edto.getViajesIniciados());
        assertNotNull("Los viajes terminados deberían estar inicializados", edto.getViajesTerminados());
        
        assertTrue("Los choferes deberían comenzar vacíos", edto.getChoferes().isEmpty());
        assertTrue("Los choferes desocupados deberían comenzar vacíos", edto.getChoferesDesocupados().isEmpty());
        assertTrue("Los clientes deberían comenzar vacíos", edto.getClientes().isEmpty());
        assertTrue("Los pedidos deberían comenzar vacíos", edto.getPedidos().isEmpty());
        assertTrue("Los vehículos deberían comenzar vacíos", edto.getVehiculos().isEmpty());
        assertTrue("Los vehículos desocupados deberían comenzar vacíos", edto.getVehiculosDesocupados().isEmpty());
        assertTrue("Los viajes iniciados deberían comenzar vacíos", edto.getViajesIniciados().isEmpty());
        assertTrue("Los viajes terminados deberían comenzar vacíos", edto.getViajesTerminados().isEmpty());
        //no se en que empieza usuario por defecto, asumo que en null
        assertNull("El usuario logueado debería ser null al inicio", edto.getUsuarioLogeado());
	}

}
