package test_GUI;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

public class TestAdminAltasVehiculos {
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	int delay = TestUtil.getDelay();
	
	Moto moto;
	Combi combi;
	Auto auto;
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
	
	public void buildExcenarioVehiculos() {
		this.auto = new Auto("AAA111",4,true);
		this.moto = new Moto("MMM222");
		this.combi = new Combi("CCC333",8,true);
		
		try {
			this.empresa.agregarVehiculo(this.auto);
			this.empresa.agregarVehiculo(this.moto);
			this.empresa.agregarVehiculo(this.combi);
		
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public void agregaVehiculo(String patente, int tipo, int plazas, boolean mascota) {
		
		JTextField patentes = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		
		TestUtil.clickComponent(patentes, robot);
		TestUtil.tipeaTexto(patente, robot);
		robot.delay(this.delay);
		
		JRadioButton rad1 = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JRadioButton rad2 = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.MOTO);
		JRadioButton rad3 = (JRadioButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		
		if (tipo == 1)
			TestUtil.clickComponent(rad1, robot);
		else
			if(tipo ==2)
				TestUtil.clickComponent(rad2, robot);
			else
				TestUtil.clickComponent(rad3, robot);
		
		robot.delay(this.delay);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		if(tipo != 2) {
			if(mascota) {
				JCheckBox mascBox = (JCheckBox) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_VEHICULO_ACEPTA_MASCOTA);
				TestUtil.clickComponent(mascBox, robot);
				robot.delay(this.delay);
			}
			
			
			TestUtil.clickComponent(paxText, robot);
			TestUtil.tipeaTexto(String.valueOf(plazas), robot);
			robot.delay(this.delay);
		}
		
		
		
		/*
		TestUtil.clickComponent(patentes, robot);
		TestUtil.borraJTextField(patentes, robot);
		robot.delay(this.delay);
		
		TestUtil.clickComponent(paxText, robot);
		TestUtil.borraJTextField(paxText, robot);
		robot.delay(this.delay);
		Comentado por que imposibilita el testing
		*/
		///##############agregar el JButton dentro o fuera cuando sea necesario.
	}
	
	public void agregaCliente() {
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
		robot.delay(this.delay);
	}

	@Test
	public void testAltaMoto_AceptarVehiculoHab() {
		//El JTextField 8 debe tener al menos un caracter para habilitar el JButton 14
		
		this.logeaVentana();
		this.agregaVehiculo("MMM222", 2, 1, false);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar habilitado", vehButton.isEnabled());
	}
	
	@Test
	public void testAltaMoto_AceptarVehiculoDes() {
		//El JTextField 8 debe tener al menos un caracter para habilitar el JButton 14
		
		this.logeaVentana();
		this.agregaVehiculo("", 2, 1, false);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar habilitado", !vehButton.isEnabled());
	}
	
	@Test
	public void testAltaAuto_AceptarVehiculoHab() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 0 y 4
	
		this.logeaVentana();
		this.agregaVehiculo("AAA555", 1, 3, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar habilitado", vehButton.isEnabled());
	}
	
	@Test
	public void testAltaAuto_AceptarVehiculoDes_pax() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 0 y 4
	
		this.logeaVentana();
		this.agregaVehiculo("AAA222", 1, 5, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar deshabilitado", !vehButton.isEnabled());
	}
	
	@Test
	public void testAltaAuto_AceptarVehiculoDes_pat() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 0 y 4
	
		this.logeaVentana();
		this.agregaVehiculo("", 1, 3, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar deshabilitado", !vehButton.isEnabled());
	}
		
	@Test
	public void testAltaCombi_AceptarVehiculoHab() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 5 y 10
		this.logeaVentana();
		this.agregaVehiculo("CCC333", 3, 8, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar habilitado", vehButton.isEnabled());
	}
	
	@Test
	public void testAltaCombi_AceptarVehiculoDes_pax() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 5 y 10
		this.logeaVentana();
		this.agregaVehiculo("CCC333", 3, 11, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar deshabilitado", !vehButton.isEnabled());
	}
	
	@Test
	public void testAltaCombi_AceptarVehiculoDes_pat() {
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 5 y 10
		this.logeaVentana();
		this.agregaVehiculo("", 3, 8, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		
		assertTrue("Aceptar vehiculo deberia estar deshabilitado", !vehButton.isEnabled());
	}
	
	@Test
	public void testAltaMoto_pulsaBoton() {
		//Dar de alta una moto con el robot, verificar que se haya agregado correctamente
		this.buildExcenarioVehiculos();
		this.logeaVentana();
		this.agregaVehiculo("MMM333", 2, 8, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		
		assertTrue("Las listas no son iguales", this.verificarLista(vtEmpresa, vehJList));
	}
	
	@Test
	public void testAltaAuto_pulsaBoton() {
		//Dar de alta un auto con el robot, verificar que se haya agregado correctamente
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 1 y 4
		this.buildExcenarioVehiculos();
		this.logeaVentana();
		this.agregaVehiculo("AAA333", 1, 4, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		
		assertTrue("Las listas no son iguales", this.verificarLista(vtEmpresa, vehJList));
	}
	
	@Test
	public void testAltaCombi_pulsaBoton() {
		//Dar de alta una combi con el robot, verificar que se haya agregado correctamente
		//El JTextField 8 debe tener al menos un caracter para habilitar, el JTextField 13 entre 5 y 10
		
		this.buildExcenarioVehiculos();
		this.logeaVentana();
		this.agregaVehiculo("CCC33", 3, 5, true);
		
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		
		assertTrue("Las listas no son iguales", this.verificarLista(vtEmpresa, vehJList));
	}
	
	@Test
	public void testAltaVehculo_borraJTextPatente() {
		this.logeaVentana();
		this.agregaVehiculo("OOO111", 1, 3, false);
		

		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JTextField patentes = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		
		assertTrue("El JTextField deberia estar vacio", patentes.getText().isEmpty());
		
	}
	
	@Test
	public void testAltaVehculo_borraJTextPax() {
		this.logeaVentana();
		this.agregaVehiculo("OOO111", 1, 3, false);
		

		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JTextField paxText = (JTextField) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		
		assertTrue("El JTextField deberia estar vacio", paxText.getText().isEmpty());
		
	}

	@Test
	public void testAltaAuto_repetido() {
		
		this.buildExcenarioVehiculos();
		this.logeaVentana();
		this.agregaVehiculo("AAA111", 1, 4, true);
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		
		assertTrue("Las listas no son iguales", op.getMensaje().equals(Mensajes.VEHICULO_YA_REGISTRADO.getValor()));
	}
	
	@Test
	public void testAltaMoto_repetido() {
		
		this.buildExcenarioVehiculos();
		this.logeaVentana();
		this.agregaVehiculo("MMM222", 2, 1, true);
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		
		assertTrue("Las listas no son iguales", op.getMensaje().equals(Mensajes.VEHICULO_YA_REGISTRADO.getValor()));
	}
	
	@Test
	public void testAltaCombi_repetido() {
		
		this.buildExcenarioVehiculos();
		this.logeaVentana();
		this.agregaVehiculo("CCC333", 3, 8, true);
		JButton vehButton= (JButton) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		TestUtil.clickComponent(vehButton, robot);
		robot.delay(this.delay);
		
		JList vehJList = (JList) TestUtil.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		ArrayList <Vehiculo> vtEmpresa = new ArrayList<Vehiculo> (this.empresa.getVehiculos().values());
		
		
		assertTrue("Las listas no son iguales", op.getMensaje().equals(Mensajes.VEHICULO_YA_REGISTRADO.getValor()));
	}
}
