package com.example.webrise.service.user;

import com.example.webrise.dto.UserDto;
import com.example.webrise.model.Subscription;
import com.example.webrise.model.User;

import java.util.Set;

public interface UserService {
    User addUser(UserDto userDto);

    void delete(Long userId);

    UserDto getUserById(Long userId);

    User addSubscriptionToUser(Long userId, Subscription subscription);

    User updateUser(Long id, UserDto userDto);

    User deleteSubscriptionFromUser (Long user_id, Long subscr_id);

    Set<Subscription> getUserSubscriptions(Long userId);
}
