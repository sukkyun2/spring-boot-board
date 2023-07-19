package com.example.board.api.user.domain;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Integer> {
    User findByLoginIdAndPassword(String loginId, String password);
    User findById(Integer id);
}
