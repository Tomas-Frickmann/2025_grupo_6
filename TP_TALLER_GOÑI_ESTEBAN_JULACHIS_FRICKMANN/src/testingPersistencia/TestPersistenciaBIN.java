package testingPersistencia;

import static org.junit.Assert.*;

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
		this.pbin = new PersistenciaBIN();
	    this.temp = File.createTempFile("temp", ".bin");
	}

	@After
	public void tearDown() throws Exception {
		if (this.temp.exists()) {
			this.temp.delete();
		}
	}

	@Test
	public void testLecturaYEscritura() {
		try {
			this.pbin.abrirOutput(temp.getAbsolutePath());
			String objeto= "prueba de lectura y escritura";
			this.pbin.escribir(objeto);
			this.pbin.cerrarOutput();
			
			this.pbin.abrirInput(temp.getAbsolutePath());
			String leido= (String)pbin.leer();
			this.pbin.cerrarInput();
			assertEquals("lo escrito y lo leido son dos cosas distintas",objeto,leido);
		}
		catch(Exception e) {
			fail("no deberia lanzar exepcion");
		}
	}
	
	@Test
	public void testLectura_SinArchivo() {
		try {
			this.pbin.abrirInput("archivo_que_no_existe.bin");
			fail("deberia lanzar exepcion por archivo inexistente");
		}
		catch(Exception e) {
			assertTrue("El archivo no existe",true);
			
		}
	}
	
	@Test
	public void testLectura_ArchivoNull() {
		try {
			this.pbin.abrirInput(null);
			fail("deberia lanzar exepcion por archivo inexistente");
		}
		catch(Exception e) {
			assertTrue("El archivo no existe",true);
			
		}
	}
	
	@Test
	public void testCerrarSinAbrir_Input() {
		try {
			this.pbin.cerrarInput();
			fail("Deberia lanzar excepcion al cerrar sin abrir");
		}
		catch(Exception e) {
			assertTrue("IOException al cerrar sin abrir",true);
		}
	}
	
	@Test
	public void testCerrarSinAbrir_Output() {
		try {
			this.pbin.cerrarOutput();
			fail("Deberia lanzar excepcion al cerrar sin abrir");
		}
		catch(Exception e) {
			assertTrue("IOException al cerrar sin abrir",true);
		}
	}

}
