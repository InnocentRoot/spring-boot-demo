package com.root.demo.repository;

import com.root.demo.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<User, Integer>,
        JpaSpecificationExecutor<User> {}
