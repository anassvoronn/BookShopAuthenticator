package org.nastya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.nastya.dto.UserDTO;
import org.nastya.repository.UserRepository;
import org.nastya.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @RepeatedTest(20)
    public void userUniqueness_parallel() {
        final String userName = "user1";
        final String password = "123";

        final UserDTO user1 = createUserDTO(userName, password);
        final UserDTO user2 = createUserDTO(userName, password);

        final Thread thread1 = new Thread(() -> saveUser(user1));
        final Thread thread2 = new Thread(() -> saveUser(user2));
        thread1.start();
        thread2.start();

        join(thread1);
        join(thread2);

        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    public void userUniqueness_sequential() throws UserAlreadyExistsException {
        final String userName = "user1";
        final String password = "123";

        final UserDTO user1 = createUserDTO(userName, password);
        final UserDTO user2 = createUserDTO(userName, password);

        userService.saveUser(user1);
        try {
            userService.saveUser(user2);
            fail();
        } catch (UserAlreadyExistsException ignored) {
        }

        assertEquals(1, userRepository.findAll().size());
    }

    private UserDTO saveUser(UserDTO user) {
        try {
            return userService.saveUser(user);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    private UserDTO createUserDTO(String userName, String password) {
        final UserDTO user1 = new UserDTO();
        user1.setUsername(userName);
        user1.setPassword(password);
        return user1;
    }

    private void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}