package com.root.demo.repository;

import com.root.demo.model.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DisplayName("Unit testing of UserRepository")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @Test
    public void shouldFindUserByUserName() {
        User user = createUser();
        Optional<User> u = userRepository.findByUserName(user.getUserName());

        assertThat(user).isEqualTo(u.get());
    }

    @Test
    public void shouldFindUserByEmail() {
        User user = createUser();
        Optional<User> u = userRepository.findByEmail(user.getEmail());

        assertThat(user).isEqualTo(u.get());
    }

    @Test
    public void shouldFindUserNameOrEmail() {
        User user = createUser();
        Optional<User> u = userRepository
                .findByUserNameOrEmail(user.getUserName(), user.getEmail());

        assertThat(user).isEqualTo(u.get());
    }

    private User createUser() {
        if(testUser == null) {
            testUser = (User) (new User())
                    .setUserName("root")
                    .setFirstName("root")
                    .setLastName("Root")
                    .setEmail("root@gmail.com")
                    .setPassword("root@dev")
                    .setCreatedAt(Date.from(Instant.now()))
                    .setUpdatedAt(Date.from(Instant.now()));

            entityManager.persist(testUser);
            entityManager.flush();
        }

        return testUser;
    }
}
