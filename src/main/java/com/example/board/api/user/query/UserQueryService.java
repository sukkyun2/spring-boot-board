package com.example.board.api.user.query;

import com.example.board.api.user.domain.User;
import com.example.board.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public User getUserByUserId(Integer userId){
        return userRepository.findById(userId);
    }

    public List<User> getUsersByUserIds(List<Integer> userIds){
        if(CollectionUtils.isEmpty(userIds)) return Collections.emptyList();

        Set<Integer> userIdSet = new HashSet<>(userIds);
        return userIdSet.stream().map(this::getUserByUserId).toList();
    }

    public Map<Integer, User> getUserMapByUserIds(List<Integer> userIds){
        return getUsersByUserIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
