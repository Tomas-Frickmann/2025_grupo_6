package test_GUI;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;
import util.Constantes;

public class testAdminListados {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	String usuario = "Usuario1";
	String pass = "123456";
	String nombreReal = "NombreReal";
	
	ChoferPermanente choferPermanente;
	ChoferTemporario choferTemporal;
	Moto moto;
	Combi combi;
	Auto auto;
	Empresa empresa = Empresa.getInstance();
	Pedido pedido;

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
	public void buildEscenarioClientes() {
		try {
			
			this.empresa.agregarCliente(this.usuario, this.pass, this.nombreReal);
			
			this.empresa.agregarCliente("Usuario2", "654321", "OtroNombre");
			this.empresa.agregarCliente("Usuario3", "111111", "Nombre3");
			
			
			}
			
		catch(Exception e) {
			System.out.println("Problemas en el escenario " + e.getMessage());
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

	
	@Test
	public void testListaClientesTotalesCorrecto() {
			//añande un usuario 
		
	
		buildEscenarioClientes();
		JButton regButtoninicial = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		
		TestUtil.clickComponent(regButtoninicial, robot);
		robot.delay(this.delay);
		
		JTextField nomText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField passText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField passTextAgain = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JTextField realText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton regButton = (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		
		
		TestUtil.clickComponent(nomText, robot);
		TestUtil.tipeaTexto("UsuarioNuevo", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passText, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(passTextAgain, robot);
		TestUtil.tipeaTexto("123456", robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(realText, robot);
		TestUtil.tipeaTexto("NombreNuevo", robot);
		robot.delay(this.delay);
		
		
		TestUtil.clickComponent(regButton, robot);	
		/*
		robot.delay(this.delay);
		logeaVentana("admin", "admin");
		JList jlistclientes = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTADO_DE_CLIENTES);
		ArrayList <Cliente> clEmpresa = new ArrayList<Cliente> (this.empresa.getClientes().values());
		
		robot.delay(this.delay);
		
				
		assertTrue("La lista clientes no es correcta", this.verificarLista(clEmpresa, jlistclientes));
		
		
		
*/		//Se fija que la JList 24 contenga los clientes correctamente
	}
	
	@Test
	public void testListaVehiculosTotalesCorrecto() {
		//Se fija que la JList 25 contenga los vehiculos correctamente, por eso hay de los 3 tipos
	}
	
	@Test
	public void testListaViajesHistoricosCorrecta() {
		//Se fija que la JList 26 contenga todos los viajes historicos de la empresa
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

}
