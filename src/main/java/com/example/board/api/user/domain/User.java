package com.example.board.api.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "BOARD_USER")
@NoArgsConstructor
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "USER_ID")
        private Integer id;
        @Column(name = "NAME")
        private String name;
        @Column(name = "LOGIN_ID")
        private String loginId;
        @Column(name = "PASSWORD")
        private String password;

    public User(Integer userId, String userName) {
        this.id = userId;
        this.name = userName;
    }
}
