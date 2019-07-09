package com.bcc.grp.generic;

import com.bcc.grp.generic.database.repositories.GenericRepository;
import com.bcc.grp.generic.helpers.dataclass.GenericSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ITGenericControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private GenericRepository genericRepository;

	@Test
	public void shouldReturnAvailableGenerics() throws Exception {
		mockMvc
				.perform(get("/generics"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is("3269c6ad-0b41-4b4c-94d7-26facf399729")))
				.andExpect(jsonPath("$[0].firstname", is("Ishmum")))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void shouldReturnGenericWithId() throws Exception {
		mockMvc
				.perform(get("/generics/3269c6ad-0b41-4b4c-94d7-26facf399729"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("3269c6ad-0b41-4b4c-94d7-26facf399729")))
				.andExpect(jsonPath("$.firstname", is("Ishmum")));
	}

	@Test
	public void shouldReturnErrorResponseIfNonExistentIdProvided() throws Exception {
		mockMvc
				.perform(get("/generics/3269c6ad-0b41-4b4c-94d7-26facf399720"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is("STR2000")))
				.andExpect(jsonPath("$.message", containsString("No Generic found")));
	}

	@Test
	public void shouldReturnErrorResponseIfInvalidIdProvided() throws Exception {
		mockMvc
				.perform(get("/generics/invalid-id"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code", is("STR1000")))
				.andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors", contains("Invalid Request Parameters")))
				.andExpect(jsonPath("$.message", is("Invalid Request Parameters")));
	}

	@Test
	public void shouldReturnGenericWithFirstname() throws Exception {
		mockMvc
				.perform(get("/generics/find").param("firstname", "Ishmum"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is("3269c6ad-0b41-4b4c-94d7-26facf399729")))
				.andExpect(jsonPath("$[0].firstname", is("Ishmum")))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void shouldReturnEmptyArrayIfNonExistentNameProvided() throws Exception {
		mockMvc
				.perform(get("/generics/find").param("firstname", "non-existent"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void shouldReturnAvailableGenericDetails() throws Exception {
		mockMvc
				.perform(get("/generics/detail"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].type.name", contains("admin")))
				.andExpect(jsonPath("$[0].state.name", is("active")))
				.andExpect(jsonPath("$[0].generic.address", containsString("Dhaka - 1205")))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void shouldReturnAvailableGenericTypes() throws Exception {
		mockMvc
				.perform(get("/generics/type"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].name", hasItems("admin", "employee", "hr")))
				.andExpect(jsonPath("$", hasSize(5)));
	}

    @Test
    @Transactional
    public void shouldAddNewGeneric() throws Exception {
        GenericSummary genericSummary = new GenericSummary();
        /*genericSummary.setFirstname("randomfirstname");
        genericSummary.setLastname("randomlastname");
        genericSummary.setEmail("random@email.com");
        genericSummary.setPassword("randompassword");
        genericSummary.setType(1);
        genericSummary.setState(1);
        genericSummary.setDesignation(1);*/

        mockMvc
                .perform(
                        post("/generics")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(genericSummary))
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(2, genericRepository.count());
    }

    @Test
    public void shouldReturnErrorResponseForInvalidGenericData() throws Exception {
        GenericSummary genericSummary = new GenericSummary();
        /*genericSummary.setFirstname("randomfirstname");
        genericSummary.setEmail("");
        genericSummary.setPassword("randompassword");
        genericSummary.setState(50);
        genericSummary.setDesignation(1);*/

        mockMvc
                .perform(
                        post("/generics")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(genericSummary))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("STR1000")))
                .andExpect(jsonPath("$.message", containsString("Validation failed")))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
						"lastname must not be blank",
						"state can not be validated",
						"type can not be validated",
						"generic data can not be validated",
						"email must not be blank"
                )));

        assertEquals(1, genericRepository.count());
    }
}
