package com.root.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root.demo.controller.UserController;
import com.root.demo.model.User;
import com.root.demo.repository.UserRepository;
import com.root.demo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class
})
@DisplayName("Unit testing of UserController")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void shouldGetAllUser() throws Exception {

        List<User> users = this.createUserList();

        given(userService.getAllUser()).willReturn(users);

        mockMvc.perform(get("/api/user/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())))
                .andExpect(jsonPath("$[0].userName", is("user0")));
    }

    @Test
    public void shouldFindUserByEmail() throws Exception{
        List<User> users = this.createUserList();

        User first = users.get(1);

        given(userRepository.findByEmail("user0@gmail.com"))
                .willReturn(java.util.Optional.ofNullable(first));

        mockMvc.perform(post("/api/user/getByEmail")
                .content("{\"email\" : \"user0@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(first.getEmail())));
    }

    @Test
    public void shouldRefuseSignUpIfDataIsInvalid() throws Exception {
        User user = new User()
                .setFirstName("user")
                .setLastName("user")
                .setPassword("000") // Violated password constraint
                .setUserName("u") // Violated username constraint
                .setEmail("user@gmail.com");

        mockMvc.perform(post("/api/user/signup")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is("size must be between 6 and 2147483647")))
                .andExpect(jsonPath("$.userName", is("size must be between 3 and 10")));
    }

    @Test
    public void shouldSignUpIfDataIsValid() throws Exception {
        User user = createUserList().get(1);

        mockMvc.perform(post("/api/user/signup")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldDeleteUser() throws Exception {

        User user = createUserList().get(1);

        when(this.userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));

        mockMvc.perform(delete("/api/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User deleted successfully!")));
    }

    private List<User> createUserList() {
        List<User> users = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            users.add((new User())
                    .setId(i)
                    .setFirstName("user" + i)
                    .setLastName("user" + i)
                    .setPassword("00000000")
                    .setUserName("user" + i)
                    .setEmail("user" + i + "@gmail.com")
            );
        }

        return users;
    }

    private String asJsonString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
