package com.bbrick.auth.core.user.application;

import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.user.domain.exceptions.UserNotFoundException;
import com.bbrick.auth.core.user.domain.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailQueryService {
    private final UserDetailRepository userDetailRepository;

    public UserDetail getUser(long userId) {
        return this.userDetailRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
