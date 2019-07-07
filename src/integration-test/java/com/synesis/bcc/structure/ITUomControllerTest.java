package com.synesis.bcc.structure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synesis.bcc.structure.database.repositories.UomRepository;
import com.synesis.bcc.structure.helpers.dataclass.UomSummary;
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
public class ITUomControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private UomRepository uomRepository;

	@Test
	public void shouldReturnAvailableUsers() throws Exception {
		mockMvc
				.perform(get("/users"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is("3269c6ad-0b41-4b4c-94d7-26facf399729")))
				.andExpect(jsonPath("$[0].firstname", is("Ishmum")))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void shouldReturnUserWithId() throws Exception {
		mockMvc
				.perform(get("/users/3269c6ad-0b41-4b4c-94d7-26facf399729"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("3269c6ad-0b41-4b4c-94d7-26facf399729")))
				.andExpect(jsonPath("$.firstname", is("Ishmum")));
	}

	@Test
	public void shouldReturnErrorResponseIfNonExistentIdProvided() throws Exception {
		mockMvc
				.perform(get("/users/3269c6ad-0b41-4b4c-94d7-26facf399720"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is("STR2000")))
				.andExpect(jsonPath("$.message", containsString("No Uom found")));
	}

	@Test
	public void shouldReturnErrorResponseIfInvalidIdProvided() throws Exception {
		mockMvc
				.perform(get("/users/invalid-id"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code", is("STR1000")))
				.andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors", contains("Invalid Request Parameters")))
				.andExpect(jsonPath("$.message", is("Invalid Request Parameters")));
	}

	@Test
	public void shouldReturnUserWithFirstname() throws Exception {
		mockMvc
				.perform(get("/users/find").param("firstname", "Ishmum"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is("3269c6ad-0b41-4b4c-94d7-26facf399729")))
				.andExpect(jsonPath("$[0].firstname", is("Ishmum")))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void shouldReturnEmptyArrayIfNonExistentNameProvided() throws Exception {
		mockMvc
				.perform(get("/users/find").param("firstname", "non-existent"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void shouldReturnAvailableUserDetails() throws Exception {
		mockMvc
				.perform(get("/users/detail"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].type.name", contains("admin")))
				.andExpect(jsonPath("$[0].state.name", is("active")))
				.andExpect(jsonPath("$[0].uom.address", containsString("Dhaka - 1205")))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void shouldReturnAvailableUserTypes() throws Exception {
		mockMvc
				.perform(get("/users/type"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].name", hasItems("admin", "employee", "hr")))
				.andExpect(jsonPath("$", hasSize(5)));
	}

    @Test
    @Transactional
    public void shouldAddNewUser() throws Exception {
        UomSummary uomSummary = new UomSummary();
        /*uomSummary.setFirstname("randomfirstname");
        uomSummary.setLastname("randomlastname");
        uomSummary.setEmail("random@email.com");
        uomSummary.setPassword("randompassword");
        uomSummary.setType(1);
        uomSummary.setState(1);
        uomSummary.setDesignation(1);*/

        mockMvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(uomSummary))
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(2, uomRepository.count());
    }

    @Test
    public void shouldReturnErrorResponseForInvalidUserData() throws Exception {
        UomSummary uomSummary = new UomSummary();
        /*uomSummary.setFirstname("randomfirstname");
        uomSummary.setEmail("");
        uomSummary.setPassword("randompassword");
        uomSummary.setState(50);
        uomSummary.setDesignation(1);*/

        mockMvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(uomSummary))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("STR1000")))
                .andExpect(jsonPath("$.message", containsString("Validation failed")))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
						"lastname must not be blank",
						"state can not be validated",
						"type can not be validated",
						"uom data can not be validated",
						"email must not be blank"
                )));

        assertEquals(1, uomRepository.count());
    }
}
