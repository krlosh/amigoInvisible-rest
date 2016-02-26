package org.chenche.amigoInvisible.restControllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EventoControllerTest.class, GrupoControllerTest.class, SesionControllerTest.class,
		UsuarioControllerTest.class })
public class AllTests {

}
