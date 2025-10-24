package Test_todo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testingDatos.*;
import testingEmpresa.*;
import testingPersistencia.*;

@RunWith(Suite.class)
@SuiteClasses({
    
    TestAdministrador.class,
    TestAuto.class,
    TestChoferPermanente.class,
    TestChoferTemporario.class,
    TestCliente.class,
    TestCombi.class,
    TestMoto.class,
    TestPedido.class,
    TestViaje.class,
    
    
    Escenario1.class, 
    Escenario2.class,
    Escenario3.class,
    Escenario4.class,

    
   
    TestPersistenciaBIN.class,
    TestPersistenciaEmpersaDTO.class,
    TestUtilPersistencia.class
})
public class AllTests {

}
