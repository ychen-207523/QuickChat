package ychen.quickchat.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    @Test
    public void testMessageSetAndGet() {
        Long senderId = 1L;
        Long receiverId = 2L;
        String content = "Hello, this is a test message";
        LocalDateTime timestamp = LocalDateTime.now();
        MessageStatus status = MessageStatus.SENT;

        Message message = new Message(senderId, receiverId, content, timestamp, status);

        assertEquals(senderId, message.getSenderId(), "Sender ID should match the set value.");
        assertEquals(receiverId, message.getReceiverId(), "Receiver ID should match the set value.");
        assertEquals(content, message.getContent(), "Content should match the set value.");
        assertEquals(timestamp, message.getTimestamp(), "Timestamp should match the set value.");
        assertEquals(status, message.getStatus(), "Status should match the set value.");
    }
}
