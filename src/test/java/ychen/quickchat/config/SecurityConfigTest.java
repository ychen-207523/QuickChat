package ychen.quickchat.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAllowAccessToPublicEndpoint() throws Exception {
        mockMvc.perform(get("/api/public/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRedirectUnauthenticatedUserToLoginPage() throws Exception {
        mockMvc.perform(get("/api/user/test"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void shouldAllowAccessToUserEndpointWithUserRole() throws Exception {
        mockMvc.perform(get("/api/user/test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void shouldAllowAccessToAdminEndpointWithAdminRole() throws Exception {
        mockMvc.perform(get("/api/admin/test"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void shouldPerformUserLogin() throws Exception {
//        mockMvc.perform(formLogin().user("user").password("password"))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/home")); // Check if it redirects to the expected URL
//    }

    @Test
    public void shouldPerformUserLogout() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andExpect(status().isFound()) // Check for redirect after logout
                .andExpect(redirectedUrl("/login"));
    }
}
