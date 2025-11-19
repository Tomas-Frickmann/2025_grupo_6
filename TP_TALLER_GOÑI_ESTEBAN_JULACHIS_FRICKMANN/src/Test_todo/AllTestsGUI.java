package Test_todo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test_GUI.*;

@RunWith(Suite.class)
@SuiteClasses({ 
	
	TestAdminGestionPedidos.class,
    TestAdminListados.class,
    TestAdminVisualizacion.class,
    TestAdminAltasChoferes.class,
    TestAdminAltasVehiculos.class,
    TestCliente.class,
    TestLogin.class,
    TestRegister.class,
    TestAdmin.class
    })
public class AllTestsGUI {

}
