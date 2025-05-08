package com.example.webrise.controller;

import com.example.webrise.model.Subscription;
import com.example.webrise.service.subscription.SubscriptionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription addSubscription(@RequestBody Subscription subscription) {
        log.info("Добавление сервиса");
        return subscriptionService.addService(subscription);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable Long id) {
        log.info("Удаление сервиса с id = {}", id);
        subscriptionService.delete(id);
    }
    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public List<Subscription> getTopSubscriptions() {
        log.info("GET ТОП-3 популярных подписок controllers log");
        return subscriptionService.getTop3Subscriptions();
    }
}
