package testingPersistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistencia.PersistenciaBIN;

public class TestPersistenciaBIN {

	private PersistenciaBIN pbin;
	private File temp ;

	@Before
	public void setUp() throws Exception {
		pbin = new PersistenciaBIN();
	    temp = File.createTempFile("temp", ".bin");
	}

	@After
	public void tearDown() throws Exception {
		if (temp.exists()) {
			temp.delete();
		}
	}

	@Test
	public void testLecturaYEscritura() {
		try {
			pbin.abrirOutput(temp.getAbsolutePath());
			String objeto= "prueba de lectura y escritura";
			pbin.escribir(objeto);
			pbin.cerrarOutput();
			
			pbin.abrirInput(temp.getAbsolutePath());
			String leido= (String)pbin.leer();
			pbin.cerrarInput();
			assertEquals("lo escrito y lo leido son dos cosas distintas",objeto,leido);
		}
		catch(Exception e) {
			fail("no deberia lanzar exepcion");
		}
	}

}
