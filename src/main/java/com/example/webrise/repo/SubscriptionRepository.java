package com.example.webrise.repo;

import com.example.webrise.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByServiceName(String serviceName);

    @Query(value =
            "SELECT s.* FROM subscriptions s " +
                    "JOIN user_subscriptions us ON s.id = us.subscription_id " +
                    "GROUP BY s.id, s.service_name " +
                    "ORDER BY COUNT(us.user_id) DESC, s.service_name ASC " +
                    "LIMIT 3",
            nativeQuery = true)
    List<Subscription> findTopSubscriptions();
}
