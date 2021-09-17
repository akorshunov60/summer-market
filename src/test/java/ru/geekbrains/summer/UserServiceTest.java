package ru.geekbrains.summer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.User;
import ru.geekbrains.summer.repositories.UserRepository;
import ru.geekbrains.summer.services.UserService;

import java.util.Optional;

@SpringBootTest // (classes = UserService.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {

        User userFromDB = new User();
        userFromDB.setUsername("Alex");
        userFromDB.setEmail("alex@gmail.ru");
        Mockito.doReturn(Optional.of(userFromDB))
                .when(userRepository)
                .findByUsername("Alex");

        User testUser = userService
                .findByUsername("Alex")
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Assertions.assertNotNull(testUser);
        Assertions.assertEquals("alex@gmail.ru", testUser.getEmail());
        Mockito.verify(userRepository, Mockito
                .times(1))
                .findByUsername(ArgumentMatchers.eq("Alex"));
    }
}
