package com.onlyphones.onlyphones.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new UserController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllUsers_ReturnsOk_WhenGetALlUsers() throws Exception {
        User returned = new User();
        List<User> users = Collections.singletonList(returned);

        when(service.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/user/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0]").exists());

        verify(service).getAllUsers();
    }

    @Test
    void getAllUsers_ReturnNoContent_WhenListIsEmpty() throws Exception{
        when(service.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/user/user"))
                .andExpect(status().isNoContent());

        verify(service).getAllUsers();
    }

    @Test
    void GetUserById_ReturnsOk_WhenGetAUser() throws Exception{
        String id = "123";
        User returned = new User();
        returned.setIdUser(id);

        when(service.getUserById(eq(id))).thenReturn(returned);

        mockMvc.perform(get("/api/user/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUser").value(returned.getIdUser()));

        verify(service, times(1)).getUserById(id);
    }

    @Test
    void GetUserById_ReturnsNoContent_WhenUserDoesNotExist() throws Exception{
        String id = "1234";
        when(service.getUserById(id)).thenReturn(null);

        mockMvc.perform(get("/api/user/user/{id}", id))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getUserById(id);
    }

    @Test
    void UpdateUser_ReturnsOk_WhenUpdate() throws Exception{
        String idUser = "abc";
        User user = new User();
        when(service.updateUser(eq(idUser), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/user/edituser/{id}", idUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new User())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(service).updateUser(eq(idUser), any(User.class));

    }

    @Test
    void UpdateUSer_ReturnBad_WhenUserCanNotUpdate() throws Exception{
        String id = "1234";
        User user = new User();

        when(service.updateUser(eq(id), any(User.class))).thenReturn(null);

        mockMvc.perform(put("/api/user/edituser/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());

        verify(service).updateUser(eq(id), any(User.class));
    }

    @Test
    void DeleteUSer_ReturnOk_WhenUserDelete() throws Exception{
        String id = "1234";
        doNothing().when(service).deleteUser(id);

        mockMvc.perform(delete("/api/user/deleteuser/{id}", id))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteUser(id);
    }

    @Test
    void DeleteUser_ReturnNotFound_WhenUserDoesNotExist() throws Exception{
        String id = "1234";

        doThrow(new RuntimeException("User not found")).when(service).deleteUser(id);

        mockMvc.perform(delete("/api/user/deleteuser/{id}", id))
                .andExpect(status().isNotFound());

        verify(service).deleteUser(id);

    }

}
