package com.onesquad.user.adapter.client;

import com.onesquad.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserServiceClient {
    Optional<User> getUserById(UUID userId);
}