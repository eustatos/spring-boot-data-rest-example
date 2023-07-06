package com.example.dataRest;

import com.example.dataRest.domain.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryRestConrollerIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.data.rest.basePath}")
    private String basePath;

    @Test
    public void shouldCreateCategory() throws Exception {
        Category category = new Category("first", "description");
        this.mockMvc.perform(
                        post(basePath + "/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(category))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("title").value("first"))
                .andExpect(status().isCreated());

        this.mockMvc.perform(
                        get(basePath + "/categories")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$._embedded.categories[0].title").value("first"))
                .andExpect(status().isOk());

    }
}
