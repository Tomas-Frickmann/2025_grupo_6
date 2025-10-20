package testingEmpresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;


import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Constantes;



public class Escenario5 {


	@Before
	public void setUp() throws Exception {
		Empresa empresa;
		empresa = Empresa.getInstance();
		empresa.setChoferes(new HashMap <String,Chofer>());
		empresa.setChoferesDesocupados(new ArrayList <Chofer>());
		empresa.setClientes(new HashMap <String,Cliente>());
		empresa.setPedidos(new HashMap <Cliente,Pedido>());
		empresa.setVehiculos(new HashMap <String,Vehiculo>());
		empresa.setVehiculosDesocupados(new ArrayList <Vehiculo>());
		empresa.setViajesIniciados(new HashMap <Cliente,Viaje>());
		empresa.setViajesTerminados(new ArrayList <Viaje>());	
		
		try {
			empresa.agregarCliente("Usuario1", "12345678", "NombreReal1");
			
			empresa.agregarChofer(new ChoferPermanente("22222222","nombreRealChofer1",2020,4));
			
			empresa.agregarVehiculo(new Combi("BBB111",8,true));
			empresa.agregarVehiculo(new Moto("BBB222"));
			
			Cliente cliente1 = empresa.getClientes().get("Usuario1");
			empresa.agregarPedido(new Pedido(cliente1, 4, false, false, 1, Constantes.ZONA_STANDARD));
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
}

