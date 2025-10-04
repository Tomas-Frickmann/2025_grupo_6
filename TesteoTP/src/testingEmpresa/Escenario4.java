package testingEmpresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;


public class Escenario4 {
	Empresa empresa;

	@Before
	public void setUp() throws Exception {
		this.escenario4();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void escenario4(){
		this.empresa = Empresa.getInstance();
		this.empresa.setChoferes(new HashMap <String,Chofer>());
		this.empresa.setChoferesDesocupados(new ArrayList <Chofer>());
		this.empresa.setClientes(new HashMap <String,Cliente>());
		this.empresa.setPedidos(new HashMap <Cliente,Pedido>());
		this.empresa.setVehiculos(new HashMap <String,Vehiculo>());
		this.empresa.setVehiculosDesocupados(new ArrayList <Vehiculo>());
		this.empresa.setViajesIniciados(new HashMap <Cliente,Viaje>());
		this.empresa.setViajesTerminados(new ArrayList <Viaje>());	
		
		try {
			this.empresa.agregarCliente("Usuario1", "12345678", "NombreReal1");
			this.empresa.agregarCliente("Usuario2", "12345677", "nombreRealCliente2");
			
			this.empresa.agregarChofer(new ChoferPermanente("22222222","nombreRealChofer1",2020,4));
			
			this.empresa.agregarVehiculo(new Auto("BBB111",4,false));
			this.empresa.agregarVehiculo(new Moto("BBB222"));
			
			Cliente cliente1 = this.empresa.getClientes().get("Usuario1");
			this.empresa.agregarPedido(new Pedido(cliente1, 4, false, false, 1, Constantes.ZONA_STANDARD));
			
			Cliente cliente2 = this.empresa.getClientes().get("Usuario2");
			Pedido p = this.empresa.getPedidoDeCliente(cliente2);
			Chofer c = this.empresa.getChoferesDesocupados().get(0);
			Vehiculo a = this.empresa.getVehiculosDesocupados().get(0);
			
			this.empresa.crearViaje(p, c, a);
			
		}
		catch(Exception e) {
			System.out.println("Datos mal ingresados en el escenario");
		}
	}
	
}

