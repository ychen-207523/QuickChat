package ychen.quickchat.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ychen.quickchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // Given
        User alex = new User("JohnDoe", "Jdoe@example.com", "password@123");
        entityManager.persist(alex);
        entityManager.flush();

        // When
        User found = userRepository.findByUsername(alex.getUsername());

        // Then
        assertThat(found.getUsername()).isEqualTo(alex.getUsername());
    }

    @Test
    public void whenInvalidUsername_thenReturnNull() {
        // When
        User fromDb = userRepository.findByUsername("doesNotExist");

        // Then
        assertThat(fromDb).isNull();
    }
}
