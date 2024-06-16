package ychen.quickchat.repository;

import ychen.quickchat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.junit.Test;
import ychen.quickchat.model.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void whenFindBySenderId_thenRetrieveMessages() {
        // Given
        Message msg1 = new Message(1L, 2L, "Hello", LocalDateTime.now(), MessageStatus.SENT);
        entityManager.persist(msg1);
        entityManager.flush();

        // When
        List<Message> foundMessages = messageRepository.findBySenderId(1L);

        // Then
        assertThat(foundMessages).hasSize(1);
        assertThat(foundMessages.get(0).getContent()).isEqualTo("Hello");
    }

    @Test
    public void whenFindByReceiverId_thenRetrieveMessages() {
        // Given
        Message msg1 = new Message(1L, 2L, "Hello", LocalDateTime.now(), MessageStatus.SENT);
        entityManager.persist(msg1);
        entityManager.flush();

        // When
        List<Message> foundMessages = messageRepository.findByReceiverId(2L);

        // Then
        assertThat(foundMessages).hasSize(1);
        assertThat(foundMessages.get(0).getContent()).isEqualTo("Hello");
    }

    @Test
    public void whenFindBySenderIdAndReceiverId_thenRetrieveMessages() {
        // Given
        Message msg1 = new Message(1L, 2L, "Hello", LocalDateTime.now(), MessageStatus.SENT);
        entityManager.persist(msg1);
        entityManager.flush();

        // When
        List<Message> foundMessages = messageRepository.findBySenderIdAndReceiverId(1L, 2L);

        // Then
        assertThat(foundMessages).hasSize(1);
        assertThat(foundMessages.get(0).getContent()).isEqualTo("Hello");
    }

    // Additional test to check for no results scenario
    @Test
    public void whenSenderOrReceiverIdNotFound_thenRetrieveNoMessages() {
        // When
        List<Message> foundMessages = messageRepository.findBySenderIdAndReceiverId(99L, 99L);

        // Then
        assertThat(foundMessages).isEmpty();
    }
}
