package com.rebot.micro.repository;

import com.rebot.micro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByPhone(String phone);
    User getUserById(Integer id);

}
