package org.chenche.amigoInvisible.restControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.chenche.amigoInvisible.domain.Grupo;
import org.chenche.amigoInvisible.domain.Integrante;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		this.mockMvc.perform(get("/grupos"))
					.andExpect(status().is(200));
	}
	
	@Test
	public void testGetGrupo() throws Exception {
		this.mockMvc.perform(get("/grupos/1"))
					.andExpect(status().is(200))
					.andExpect(jsonPath("$.nombre").value("G1"));
	}
	
	@Test
	public void testCrearGrupo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Grupo g = new Grupo();
		g.setId(2);
		g.setNombre("G2");
		String json = mapper.writeValueAsString(g);
		this.mockMvc.perform(post("/grupos")
				//.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andExpect(status().is(200));
	}
	
	@Test
	public void testGuardarGrupo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Grupo g = new Grupo();
		g.setId(2);
		g.setNombre("G2");
		String json = mapper.writeValueAsString(g);
		this.mockMvc.perform(post("/grupos/2")
				//.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andExpect(status().is(200));
	}
	
	@Test
	public void testEnlazarPerfilGrupo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Integrante integrante = new Integrante();
		integrante.setNombre("p - 2");
		integrante.setEmail("bla@b.es");
		String json = mapper.writeValueAsString(integrante);
		this.mockMvc.perform(post("/grupos/2/usuario")
				//.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andExpect(status().is(200));
	}

	
	@Test
	public void testDesenlazarPerfilGrupo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Integrante integrante = new Integrante();
		integrante.setNombre("p - 2");
		integrante.setEmail("bla@b.es");
		String json = mapper.writeValueAsString(integrante);
		this.mockMvc.perform(delete("/grupos/2/usuario")
				//.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andExpect(status().is(200));
	}
	
}
