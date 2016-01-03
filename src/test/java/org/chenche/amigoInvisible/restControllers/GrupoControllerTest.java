package org.chenche.amigoInvisible.restControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/webcontext/DispatchServlet-context.xml")
@WebAppConfiguration
public class GrupoControllerTest extends RestControllerTest{

	@Before
	public void setup(){
		super.setup();
	}
	
	@Test
	public void testGetGrupos() throws Exception {
		this.mockMvc.perform(get("/grupos")).andExpect(status().is(200));
	}

}
