package ychen.quickchat.controller;

import ychen.quickchat.model.Message;
import ychen.quickchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Message>> getAllMessagesForUser(@PathVariable Long userId) {
        List<Message> messages = messageRepository.findAllByReceiverId(userId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{userId}/from/{senderId}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable Long userId, @PathVariable Long senderId) {
        List<Message> messages = messageRepository.findMessagesBetweenUsers(userId, senderId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            return ResponseEntity.notFound().build();
        }
        messageRepository.deleteById(messageId);
        return ResponseEntity.ok().build();
    }
}
