package com.asb.jenkiniazation;

import com.asb.jenkiniazation.model.Task;
import com.asb.jenkiniazation.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldShowEmptyList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(model().attributeExists("newTask"));
    }

    @Test
    void shouldAddTask() throws Exception {
        mockMvc.perform(post("/add")
                .param("name", "Test Task"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Verify task was saved
        Task task = taskRepository.findAll().get(0);
        assert task.getName().equals("Test Task");
    }
}
