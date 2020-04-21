package com.root.demo.repository.specification;

import com.root.demo.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasEmail(String email) {
        return (u, cq, cb) -> cb.equal(u.get("email"), email);
    }
}
