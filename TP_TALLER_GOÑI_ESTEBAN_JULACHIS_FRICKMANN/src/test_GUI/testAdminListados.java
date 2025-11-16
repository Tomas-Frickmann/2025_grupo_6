package test_GUI;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testAdminListados {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListaClientesTotalesCorrecto() {
		//Se fija que la JList 24 contenga los clientes correctamente
	}
	
	@Test
	public void testListaVehiculosTotalesCorrecto() {
		//Se fija que la JList 25 contenga los vehiculos correctamente, por eso hay de los 3 tipos
	}
	
	@Test
	public void testListaViajesHistoricosCorrecta() {
		//Se fija que la JList 26 contenga todos los viajes historicos de la empresa
	}
	
	@Test 
	public void testSueldosTotales() {
		//El JTextField 15 debe mostrar la suma de todos los sueldos de los choferes
	}

}
