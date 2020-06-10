package eu.pinske.playground;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.camunda.bpm.engine.RuntimeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class PlaygroundApplicationTests {

	@Test
	void contextLoads(@Autowired WebApplicationContext ctx, @Autowired JdbcTemplate jdbc) throws Exception {
		MockMvc mvc = webAppContextSetup(ctx).build();
		mvc.perform(
				post("/playground-api/thing").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"table\"}"))
				.andExpect(status().isOk());
		mvc.perform(
				post("/playground-api/thing").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"chair\"}"))
				.andExpect(status().isOk());

		mvc.perform(get("/playground-api/thing")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(Matchers.greaterThanOrEqualTo(2)))
				.andExpect(jsonPath("$[0].name", equalTo("table")));

		mvc.perform(get("/playground-api/thing?name={name}", "table")).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].name").value("table")).andExpect(jsonPath("$[0].id").value(1));

		mvc.perform(get("/playground-api/thing/1")).andExpect(status().isOk());
		mvc.perform(get("/playground-data/things/1")).andExpect(status().isOk());
	}

	@Test
	@Disabled
	void camunda(@Autowired RuntimeService service) throws Exception {
		service.startProcessInstanceByKey("Prozess");
		Thread.sleep(10000);
	}

}
