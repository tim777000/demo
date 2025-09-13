package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnAllUsers() throws Exception {
		mockMvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0].name", is("John")));
	}

	@Test
	void shouldReturnAllUsersAsync() throws Exception {
		mockMvc.perform(get("/api/users/async"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0].name", is("John")));
	}

	@Test
	void shouldReturnUserById() throws Exception {
		mockMvc.perform(get("/api/users/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("John")));
	}

	@Test
	void shouldReturnNotFoundForInvalidUser() throws Exception {
		mockMvc.perform(get("/api/users/99"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DirtiesContext // 告訴 Spring Test 在這個測試後，要重置 Application Context (例如，清除資料庫)，確保測試間的獨立性。
	void shouldDeleteUser() throws Exception {
		mockMvc.perform(delete("/api/users/1"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/users"))
				.andExpect(jsonPath("$", hasSize(4)));
	}
}