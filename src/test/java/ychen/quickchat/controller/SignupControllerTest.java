package ychen.quickchat.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignupController.class)
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void signupPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk());
    }
}
