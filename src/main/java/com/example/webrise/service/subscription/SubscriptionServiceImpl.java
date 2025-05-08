package com.example.webrise.service.subscription;

import com.example.webrise.model.Subscription;
import com.example.webrise.repo.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription addService(Subscription subscription) {
        subscriptionRepository.findByServiceName(subscription.getServiceName())
                .ifPresent(existingSubcription -> {
                    throw new IllegalArgumentException(String.format("Сервис с таким названием %s уже существует", subscription.getServiceName()));
                });
        log.info("Сервис с именем  {} был добавлен", subscription.getServiceName());
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void delete(Long subscriptionId) {
        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Сервис с таким id  %d не найден", subscriptionId)));
        subscriptionRepository.deleteById(subscriptionId);
        log.info("Сервис с id  {}  был удален", subscriptionId);
    }

    @Override
    public List<Subscription> getTop3Subscriptions() {
        log.info("GET ТОП-3 популярных подписок services log");
        return subscriptionRepository.findTopSubscriptions();
    }
}
