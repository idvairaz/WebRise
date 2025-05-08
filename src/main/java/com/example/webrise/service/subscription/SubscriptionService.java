package com.example.webrise.service.subscription;

import com.example.webrise.model.Subscription;
import java.util.List;

public interface SubscriptionService {
    Subscription addService(Subscription subscription);
    void delete(Long subscriptionId);

    List<Subscription> getTop3Subscriptions();
}
