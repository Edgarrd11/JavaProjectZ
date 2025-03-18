package org.example.controller;

import io.javalin.http.Context;
import org.example.controller.UserController;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService; // Mock the UserService dependency

    @Mock
    private Context ctx; // Mock the Context dependency

    @InjectMocks
    private UserController userController; // Inject mocks into the UserController

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        // Create mock User objects
        User user1 = new User();
        user1.setId(1);
        user1.setName("John Doe");
        user1.setUsername("johndoe");
        user1.setPassword("password123");
        user1.setRole("admin");

        User user2 = new User();
        user2.setId(2);
        user2.setName("Jane Doe");
        user2.setUsername("janedoe");
        user2.setPassword("password456");
        user2.setRole("user");

        List<User> mockUsers = Arrays.asList(user1, user2); // Create a mock list of users

        // Stub the userService.getAllUsers() method to return the mock list
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act
        userController.getAllUsers(ctx); // Call the method under test

        // Assert
        // Verify that userService.getAllUsers() was called exactly once
        verify(userService, times(1)).getAllUsers();

        // Verify that ctx.json() was called exactly once with the mock list of users
        verify(ctx, times(1)).json(mockUsers);
    }
}
