package com.example.webrise.controller;

import com.example.webrise.dto.UserDto;
import com.example.webrise.model.Subscription;
import com.example.webrise.model.User;
import com.example.webrise.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable Long id) {
        log.info("GET пользователя c id {}", id);
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserDto userDto) {
        log.info("Создание пользователя {}", userDto);
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.info("Удаление пользователя с id {}", id);
        userService.delete(id);
    }

    @PostMapping("/{id}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public User addSubscriptionToUser(@PathVariable Long id, @RequestBody Subscription subscription) {
        log.info("Добавление подписки {} пользователю с id {}", subscription, id);
        return userService.addSubscriptionToUser(id, subscription);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.info("Обновление пользователя с id {}", id);
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscriptionFromUser(@PathVariable Long id, @PathVariable Long sub_id) {
        log.info("Удаление подписки {} у пользователя с id {}", id, sub_id);
        userService.deleteSubscriptionFromUser(id, sub_id);
    }

    @GetMapping("/{id}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public Set<Subscription> getUserSubscriptions(@PathVariable Long id) {
        log.info("GET подписок пользователя c id  {}", id);
        return userService.getUserSubscriptions(id);
    }
}
