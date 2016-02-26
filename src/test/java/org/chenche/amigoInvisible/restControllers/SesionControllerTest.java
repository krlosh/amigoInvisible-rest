package org.chenche.amigoInvisible.restControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.chenche.amigoInvisible.domain.UserData;
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
public class SesionControllerTest extends RestControllerTest{


	@Before
	public void setup() {
		super.setup();
	}

	@Test
	public void testLogin() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		UserData value= new UserData();
		value.setName("nombre");
		value.setPassword("nombre");
		String json = mapper.writeValueAsString(value);//"{\"name\":\"nombre\",\"password\":\"nombre\"}";
		this.mockMvc.perform(
			post("/sesion")
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
			)
			.andExpect(status().is(200));
	}
	
	@Test
	public void testLogout() throws Exception {
		this.mockMvc.perform(delete("/sesion?token=1")).andExpect(status().is(403));
	}
}
