package com.root.demo.service;

import com.root.demo.model.User;
import com.root.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.root.demo.repository.specification.UserSpecifications.hasEmail;

@Service
public class UserService {
    private final UserRepository userRepository;

    private EntityManager em;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EntityManager em, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.em = em;
    }

    public List<User> getAllUser() {

        return new ArrayList<>(this.userRepository.findAll());
    }

    public void addUser(User user) {
        this.userRepository.save(user);
    }

    public User findUserByEmail(String email) {
       Optional<User> user = userRepository.findOne(Specification.where(hasEmail(email)));

        return user.orElse(null);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        User u = findUserByEmail(user.getEmail());

//        if(u != null && passwordEncoder.matches(oldPassword, u.getPassword())) {
//            u.setPassword(passwordEncoder.encode(newPassword));
//
//            return true;
//        }

        return false;
    }
}
