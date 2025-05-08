package com.example.webrise.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
            (name = "user_subscriptions",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Subscription> subscription = new HashSet<>();

}
