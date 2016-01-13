package org.chenche.amigoInvisible.restControllers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/webcontext/DispatchServlet-context.xml")
@WebAppConfiguration
public class UsuarioControllerTest extends RestControllerTest{

	@Before
	public void setup(){
		super.setup();
	}
	
	@Test
	public void testCargarPerfil() throws Exception {
		MvcResult result =this.mockMvc.perform(get("/usuario/yo")).andExpect(status().is(200))
		.andExpect(jsonPath("$.login").value("yo"))
		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

}
