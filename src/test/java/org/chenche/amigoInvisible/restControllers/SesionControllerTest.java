package org.chenche.amigoInvisible.restControllers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.chenche.amigoInvisible.domain.UserData;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/webcontext/DispatchServlet-context.xml")
@WebAppConfiguration
public class SesionControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	MockHttpSession session;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testLogin() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		UserData value= new UserData();
		value.setName("nombre");
		value.setName("nombre");
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
		this.mockMvc.perform(delete("/sesion?token=1")).andExpect(status().is(200));
	}
}
