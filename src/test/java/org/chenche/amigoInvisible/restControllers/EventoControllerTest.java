package org.chenche.amigoInvisible.restControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.chenche.amigoInvisible.domain.Evento;
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
public class EventoControllerTest extends RestControllerTest{

	@Before
	public void setup(){
		super.setup();
	}

	@Test
	public void testEliminarEvento() throws Exception {
		this.mockMvc.perform(delete("/grupos/eventos/1")
				.param("grupoId","2"))
			.andExpect(status().is(200));
	}

	@Test
	public void testAnadirEvento()throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Evento e = new Evento();
		e.setFecha(new Date());
		e.setNombre("nombre");
		String json = mapper.writeValueAsString(e);
		this.mockMvc.perform(post("/grupos/eventos/1")
				//.session(session)
				.param("grupoId","2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andExpect(status().is(200));
	}

}
