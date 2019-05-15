package com.qinhan.videoblog.dal;

import com.qinhan.videoblog.dal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
