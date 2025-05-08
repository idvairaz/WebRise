package com.example.webrise.dto;

import com.example.webrise.model.Subscription;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String name;
    private String email;
    private List<Subscription> subscriptions;
}
