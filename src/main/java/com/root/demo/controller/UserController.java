package com.root.demo.controller;

import com.root.demo.exception.ResourceNotFoundException;
import com.root.demo.model.User;
import com.root.demo.repository.UserRepository;
import com.root.demo.request.user.FindByEmailRequest;
import com.root.demo.request.user.SignUpRequest;
import com.root.demo.response.ApiResponse;
import com.root.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@ResponseBody
@Api(value = "SpringBoot Demo", description = "Operation to manage user")
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        Logger l = LoggerFactory.getLogger(UserController.class);
        l.info("UserController called !");
    }

    @GetMapping("/all")
    @ApiOperation(value = "",
            httpMethod = "GET",
            notes = "Get all user",
            authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = this.userService.getAllUser();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/getByEmail")
    @ApiOperation(value = "",
            httpMethod = "POST",
            notes = "Get user by email",
            authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<User> getUserByEmail(@RequestBody @Valid FindByEmailRequest findByEmailRequest) {
        User user = this
                .userRepository
                .findByEmail(findByEmailRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", findByEmailRequest.getEmail()));

                return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "",
            httpMethod = "POST",
            notes = "Create a new user",
            authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<?> createUser(@RequestBody @Valid SignUpRequest signUpRequest) {

        if(userRepository.existsByUserName(signUpRequest.getUserName())) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "username already taken !"),
                    HttpStatus.BAD_REQUEST
            );
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "email already taken !"),
                    HttpStatus.BAD_REQUEST
            );
        }

        User user = (new User())
                .setFirstName(signUpRequest.getFirstName())
               .setLastName(signUpRequest.getLastName())
               .setPassword(passwordEncoder.encode(signUpRequest.getPassword()))
               .setEmail(signUpRequest.getEmail())
               .setUserName(signUpRequest.getUserName());

       this.userRepository.save(user);

        return new ResponseEntity<>(
                new ApiResponse(true, "User registered successfully !"),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Integer id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        this.userRepository.delete(user);

        return new ResponseEntity<>(
                new ApiResponse(true, "User deleted successfully!"),
                HttpStatus.OK
        );
    }

}
