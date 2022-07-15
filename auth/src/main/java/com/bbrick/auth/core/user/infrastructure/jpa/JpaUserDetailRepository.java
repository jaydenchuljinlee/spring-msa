package com.bbrick.auth.core.user.infrastructure.jpa;

import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.user.domain.exceptions.UserRepositoryIntegrationException;
import com.bbrick.auth.core.user.domain.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

interface InnerUserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByEmail(String email);

    boolean existsByEmail(String mail);
}

@Repository
@RequiredArgsConstructor
public class JpaUserDetailRepository implements UserDetailRepository {
    private final InnerUserDetailRepository repository;


    @Override
    public UserDetail save(UserDetail userDetail) {
        return this.wrapIntegrationException(
                () -> this.repository.save(userDetail)
        );
    }

    @Override
    public Optional<UserDetail> findByEmail(String email) {
        return this.wrapIntegrationException(
                () -> this.repository.findByEmail(email)
        );
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.wrapIntegrationException(
                () -> this.repository.existsByEmail(email)
        );
    }

    @Override
    public Optional<UserDetail> findById(long userId) {
        return this.repository.findById(userId);
    }

    private <T> T wrapIntegrationException(Supplier<T> proccess) {
        try {
            return proccess.get();
        } catch (Exception e) {
            throw new UserRepositoryIntegrationException(e);
        }
    }
}
