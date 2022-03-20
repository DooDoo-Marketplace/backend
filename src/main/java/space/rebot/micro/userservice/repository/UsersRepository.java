package com.rebot.micro.userservice.repository;

import com.rebot.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User getUserByPhone(String phone);
    User getUserById(Long id);

}
