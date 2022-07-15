package com.bbrick.auth.core.user.domain.repository;

import com.bbrick.auth.core.user.domain.entity.UserDetail;

import java.util.Optional;

public interface UserDetailRepository {
    UserDetail save(UserDetail userDetail);

    Optional<UserDetail> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserDetail> findById(long userId);
}
