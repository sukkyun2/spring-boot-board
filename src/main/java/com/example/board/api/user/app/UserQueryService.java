package com.example.board.api.user.app;

import com.example.board.api.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserQueryService {

    public User getUserByUserId(Integer userId){
        return new User(userId,"홍석균");
    }

    public List<User> getUserByUserIds(List<Integer> userIds){
        return userIds.stream().map(this::getUserByUserId).toList();
    }

    public Map<Integer, User> getUserMapByUserIds(List<Integer> userIds){
        return getUserByUserIds(userIds).stream()
                .collect(Collectors.toMap(User::id, Function.identity()));
    }
}
