package ychen.quickchat.controller;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import ychen.quickchat.model.Message;
import ychen.quickchat.model.MessageStatus;
import ychen.quickchat.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    @WithMockUser
    public void testSendMessage() throws Exception {
        Message message = new Message(1L, 2L, "Hello!", LocalDateTime.now(), MessageStatus.SENT);
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        mockMvc.perform(post("/messages/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderId\":1,\"receiverId\":2,\"content\":\"Hello!\",\"status\":\"SENT\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.senderId").value(1))
                .andExpect(jsonPath("$.receiverId").value(2))
                .andExpect(jsonPath("$.content").value("Hello!"))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    @WithMockUser
    public void testGetAllMessagesForUser() throws Exception {
        List<Message> messages = Arrays.asList(
                new Message(1L, 2L, "Hello!", LocalDateTime.now(), MessageStatus.SENT),
                new Message(2L, 2L, "How are you?", LocalDateTime.now(), MessageStatus.SENT)
        );
        when(messageRepository.findAllByReceiverId(anyLong())).thenReturn(messages);

        mockMvc.perform(get("/messages/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].senderId").value(1))
                .andExpect(jsonPath("$[0].receiverId").value(2))
                .andExpect(jsonPath("$[0].content").value("Hello!"))
                .andExpect(jsonPath("$[0].status").value("SENT"))
                .andExpect(jsonPath("$[1].senderId").value(2))
                .andExpect(jsonPath("$[1].receiverId").value(2))
                .andExpect(jsonPath("$[1].content").value("How are you?"))
                .andExpect(jsonPath("$[1].status").value("SENT"));
    }

    @Test
    @WithMockUser
    public void testGetMessagesBetweenUsers() throws Exception {
        List<Message> messages = Arrays.asList(
                new Message(1L, 2L, "Hello!", LocalDateTime.now(), MessageStatus.SENT),
                new Message(2L, 1L, "Hi!", LocalDateTime.now(), MessageStatus.SENT)
        );
        when(messageRepository.findMessagesBetweenUsers(anyLong(), anyLong())).thenReturn(messages);

        mockMvc.perform(get("/messages/2/from/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].senderId").value(1))
                .andExpect(jsonPath("$[0].receiverId").value(2))
                .andExpect(jsonPath("$[0].content").value("Hello!"))
                .andExpect(jsonPath("$[0].status").value("SENT"))
                .andExpect(jsonPath("$[1].senderId").value(2))
                .andExpect(jsonPath("$[1].receiverId").value(1))
                .andExpect(jsonPath("$[1].content").value("Hi!"))
                .andExpect(jsonPath("$[1].status").value("SENT"));
    }

    @Test
    @WithMockUser
    public void testDeleteMessage() throws Exception {
        when(messageRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/messages/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        Mockito.verify(messageRepository).deleteById(1L);
    }

    @Test
    @WithMockUser
    public void testDeleteMessage_NotFound() throws Exception {
        when(messageRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/messages/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }
}
