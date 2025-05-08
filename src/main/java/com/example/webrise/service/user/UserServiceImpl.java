package com.example.webrise.service.user;

import com.example.webrise.dto.UserDto;
import com.example.webrise.model.Subscription;
import com.example.webrise.model.User;
import com.example.webrise.repo.SubscriptionRepository;
import com.example.webrise.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public User addUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException(String.format("Пользователь с таким email - %s уже существует", user.getEmail()));
                });
        Set<Subscription> subscriptions = user.getSubscription();
        for (Subscription subscription : userDto.getSubscriptions()) {
            Subscription existingSubscription = subscriptionRepository.findByServiceName(subscription.getServiceName())
                    .orElseThrow(() -> new IllegalArgumentException("Подписка с названием: " + subscription.getServiceName() + " не найдена"));

            subscriptions.add(existingSubscription);
        }
        log.info("Создание пользователя {} с подписками на {}", user, user.getSubscription());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователь с  id = %d не найден", userId)));
        userRepository.deleteById(userId);
        log.info("Пользователь с id {} был удален ", userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Пользователь с  id = %d не найден", userId));
                });
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        Set<Subscription> subscriptions = user.getSubscription();
        if (subscriptions != null) {
            List<Subscription> list = new ArrayList(subscriptions.size());
            for (Subscription subscription : subscriptions) {
                list.add(subscription);
            }
            userDto.setSubscriptions(list);
        }

        return userDto;
    }

    @Transactional
    @Override
    public User addSubscriptionToUser(Long userId, Subscription subscription) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователь с id " + userId + " не найден")));
        Subscription existingSubscription = subscriptionRepository.findByServiceName(subscription.getServiceName())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Подписка с названием " + subscription.getServiceName() + " не найдена")));
        user.getSubscription().add(existingSubscription);
        log.info("Пользователю  {} добавлена подписка {}", user, subscription.getServiceName());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователь с id " + userId + " не найден")));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setSubscription(new HashSet<>());
        Set<Subscription> subscriptions = user.getSubscription();
        for (Subscription subscription : userDto.getSubscriptions()) {
            Subscription existingSubscription = subscriptionRepository.findByServiceName(subscription.getServiceName())
                    .orElseThrow(() -> new IllegalArgumentException("Подписка с названием: " + subscription.getServiceName() + " не найдена"));

            subscriptions.add(existingSubscription);

        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User deleteSubscriptionFromUser(Long userId, Long subscrId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователь с id " + userId + " не найден")));
        Set<Subscription> subscription = user.getSubscription();
        Subscription existingSubscription = subscriptionRepository.findById(subscrId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Подписка с id " + subscrId + " у пользовател " + user + "не найдена")));
        subscription.remove(existingSubscription);
        log.info("У пользователя  {} удалена  подписка {}", user, subscriptionRepository.findById(subscrId));
        return userRepository.save(user);
    }

    @Override
    public Set<Subscription> getUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователь с id " + userId + " не найден")));
        Set<Subscription> subscription = user.getSubscription();
        return subscription;
    }
}
