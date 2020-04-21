package com.root.demo.Controller;

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
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        List<User> users = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            users.add((new User())
                    .setFirstName("user" + i)
                    .setLastName("user" + i)
                    .setUserName("user" + i)
                    .setUserName("user" + i)
            );
        }

        given(userService.getAllUser()).willReturn(users);

        mockMvc.perform(get("/api/user/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())))
                .andExpect(jsonPath("$[0].userName", is("user0")));
    }
}
