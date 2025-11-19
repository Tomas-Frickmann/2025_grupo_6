package test_GUI;
import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferRepetidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

public class TestAdminAltasChoferes {
	
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	ChoferTemporario chofer;
	Empresa empresa = Empresa.getInstance();

	@Before
	public void setUp() throws Exception {
		controlador = new Controlador();
		controlador.getVista().setOptionPane(op);
		this.empresa.getClientes().clear();
		this.empresa.getVehiculos().clear();
		this.empresa.getChoferes().clear();
		this.empresa.getChoferesDesocupados().clear();
		try {
			robot = new Robot(); 
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}

	@After
	public void tearDown() throws Exception {
		this.empresa.getClientes().clear();
		this.empresa.getVehiculos().clear();
		this.empresa.getChoferes().clear();
		this.empresa.getChoferesDesocupados().clear();
	}
	
	public void buildEscenarioChoferes() {
		this.chofer = new ChoferTemporario("2","ChoferTemporario2");
		try {
			this.empresa.agregarChofer(this.chofer);
		}
		catch(ChoferRepetidoException e) {
			
		}
	}
	
	public void logeaVentana(String user,String password) {
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto(user, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto(password, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		
		robot.delay(this.delay);
		
	}

	public void logeaVentana() {
		JTextField nombre = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton aceptarLog = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		
		
		
		TestUtil.clickComponent(nombre, robot);
		TestUtil.tipeaTexto("admin", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(pass, robot);
		TestUtil.tipeaTexto("admin", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(aceptarLog, robot);
		
		robot.delay(this.delay);
		
	}
	
	public boolean verificarLista(ArrayList listaEmpresa, JList<Object> historArea) {


	    ListModel<Object> model = historArea.getModel();


	    if (model.getSize() != listaEmpresa.size()) {
	        System.out.println("Error de verificación: El número de viajes no coincide.");
	        System.out.println("Modelo tiene: " + model.getSize() + ", Lista correcta tiene: " + listaEmpresa.size());
	        return false; // No son iguales
	    }


	    for (int i = 0; i < model.getSize(); i++) {

	        Object objetoEnLista = model.getElementAt(i);
	        Object objetoCorrecto = listaEmpresa.get(i);

	        if (!objetoEnLista.equals(objetoCorrecto)) {
	            System.out.println("Error de verificación: El viaje en la posición " + i + " no coincide.");
	            System.out.println("Lista dice: " + objetoEnLista);
	            System.out.println("Debería decir: " + objetoCorrecto);
	            return false; 
	        }
	    }
	    return true;
	}

	public void creaChofer(String DNI, String nombre) {
		JTextField dnicText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nameText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		
		TestUtil.clickComponent(dnicText, robot);
		TestUtil.tipeaTexto(DNI, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nameText, robot);
		TestUtil.tipeaTexto(nombre, robot);
		robot.delay(this.delay);
		
		JRadioButton tempRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.TEMPORARIO);
		
		TestUtil.clickComponent(tempRadio, robot);
		//Nuevo chofer
		robot.delay(this.delay);
		
		
	}
	
	public void creaChofer(String DNI, String nombre,int anio, int hijos) {
		JTextField dnicText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nameText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		
		TestUtil.clickComponent(dnicText, robot);
		TestUtil.tipeaTexto(DNI, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(nameText, robot);
		TestUtil.tipeaTexto(nombre, robot);
		robot.delay(this.delay);

		JRadioButton permRadio = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		TestUtil.clickComponent(permRadio, robot);
		robot.delay(this.delay);
		
		JTextField anioText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JTextField hijosText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		
		if(anio >= 0) {
			TestUtil.clickComponent(anioText, robot);
			TestUtil.tipeaTexto(String.valueOf(anio), robot);
			robot.delay(this.delay);			
		}
		
		if(hijos >= -1) {
			TestUtil.clickComponent(hijosText, robot);
			TestUtil.tipeaTexto(String.valueOf(hijos), robot);
			robot.delay(this.delay);			
		}

		
		
	}
	
	
	@Test
	public void testAltaChoferTemporal_buttonHab() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("3333333", "ChoferTemporal2");
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar habilitado", ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferTemporal_buttonDesHab_nombre() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("333333","");
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferTemporal_buttonDesHab_dni() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("", "ChoferTemporal2");
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonHab() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("3333333", "ChoferTemporal2", 2020, 2);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar habilitado", ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonDesHab_nombre() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("3333333", "", 2020, 2);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonDesHab_dni() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("", "ChoferPerm2", 2020, 2);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonHab_anioMin() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("", "ChoferPerm2", 1900, 2);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonHab_anioMax() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("", "ChoferPerm2", 3000, 2);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonDesHab_hijosNeg() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("", "ChoferPerm2", 2020, -1);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		

		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	@Test
	public void testAltaChoferPermanente_buttonDesHab_hijosNull() {
		//Da de alta un chofer, se fija que el boton este habilitado
		this.logeaVentana();
		this.creaChofer("", "ChoferPerm2", 2020, -2);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		assertTrue("El JButton nuevo chofer deberia estar deshabilitado", !ncButton.isEnabled());	
		
	}
	
	
	@Test
	public void testButtonNuevoChofer_dniEmpty() {
		//Se crea un chofer nuevo, verificar que se vacien los JTextFields 1, 2, 5 y 6 deben vaciarse
		this.logeaVentana();
		this.creaChofer("00000", "ChoferPermanente", 2020, 0);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		TestUtil.clickComponent(ncButton, robot);
		
		JTextField dniText= (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		assertTrue("El campo dni deberia estar vacio", dniText.getText().isEmpty());
		
	}
	
	@Test
	public void testButtonNuevoChofer_nameEmpty() {
		//Se crea un chofer nuevo, verificar que se vacien los JTextFields 1, 2, 5 y 6 deben vaciarse
		this.logeaVentana();
		this.creaChofer("00000", "ChoferPermanente", 2020, 0);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		TestUtil.clickComponent(ncButton, robot);
		
		JTextField nameText= (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		assertTrue("El campo nombre deberia estar vacio", nameText.getText().isEmpty());
		
	}
	
	@Test
	public void testButtonNuevoChofer_anioEmpty() {
		//Se crea un chofer nuevo, verificar que se vacien los JTextFields 1, 2, 5 y 6 deben vaciarse
		this.logeaVentana();
		this.creaChofer("00000", "ChoferPermanente", 2020, 0);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		TestUtil.clickComponent(ncButton, robot);
		
		JTextField anioText= (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		assertTrue("El campo anio deberia estar vacio", anioText.getText().isEmpty());
		
	}
	
	@Test
	public void testButtonNuevoChofer_hijosEmpty() {
		//Se crea un chofer nuevo, verificar que se vacien los JTextFields 1, 2, 5 y 6 deben vaciarse
		this.logeaVentana();
		this.creaChofer("00000", "ChoferPermanente", 2020, 0);
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		TestUtil.clickComponent(ncButton, robot);
		
		JTextField hijosText= (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		assertTrue("El campo hijos deberia estar vacio", hijosText.getText().isEmpty());
		
	}
	
	@Test
	public void testButtonNuevoChofer_DNIexistente() {	
		
		//Se intenta crear un chofer con un DNI ya existente, verificar que salte el mensaje de error. Los JText 1, 2, 5 y 6 deben vaciarse
		this.logeaVentana();
		this.buildEscenarioChoferes();
		this.creaChofer(this.chofer.getDni(), "Otro nombre");
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		TestUtil.clickComponent(ncButton, robot);
		robot.delay(this.delay);
		assertTrue("Mensaje de error equivocado", op.getMensaje() != null && op.getMensaje().equals(Mensajes.CHOFER_YA_REGISTRADO.getValor()));
		
	}
	
	@Test
	public void testListaChoferesTotales_correcta() {

		this.buildEscenarioChoferes();
		this.logeaVentana();
		
		//choferes totales List
		
		JList ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		ArrayList <Chofer> ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		
		robot.delay(this.delay);
		
		assertTrue("La lista no es correcta", this.verificarLista(ctEmpresa, ctList));
		
		//Alta de un chofer

		this.creaChofer("1", "chofer1");
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		
		TestUtil.clickComponent(ncButton, robot);
		
		ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		
		robot.delay(this.delay);
		
		assertTrue("La lista no es correcta", this.verificarLista(ctEmpresa, ctList));
		
	}
	
	@Test
	public void testListaChoferesDesocupados_correcta() {

		this.buildEscenarioChoferes();
		this.logeaVentana();
		
		//choferes totales List
		
		JList ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		ArrayList <Chofer> ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		ArrayList <Chofer> cdEmpresa = this.empresa.getChoferesDesocupados();
		
		robot.delay(this.delay);
		
		this.creaChofer("1", "chofer1");
		
		JButton ncButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER); 
		
		TestUtil.clickComponent(ncButton, robot);
		
		ctList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		JList cdList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		
		ctEmpresa = new ArrayList<Chofer> (this.empresa.getChoferes().values());
		
		robot.delay(this.delay*20);
		
		assertTrue("La lista no es correcta", this.verificarLista(ctEmpresa, ctList) && cdEmpresa.equals(ctEmpresa));
		
	}

}
