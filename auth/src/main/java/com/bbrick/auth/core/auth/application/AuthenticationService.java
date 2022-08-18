package com.bbrick.auth.core.auth.application;

import com.bbrick.auth.core.auth.domain.exceptions.AuthenticationFailException;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.auth.domain.PasswordEncoder;
import com.bbrick.auth.core.user.domain.exceptions.UserNotFoundException;
import com.bbrick.auth.core.user.domain.repository.UserDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDetail authenticate(String email, String password) {
        Optional<UserDetail> userOptional = this.userDetailRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw AuthenticationFailException.userNotRegistered(email);
        }

        UserDetail user = userOptional.get();
        boolean passwordMatched = this.passwordEncoder.matches(password, user.getPassword());

        if (!passwordMatched) {
            throw AuthenticationFailException.passwordNotMatched(email);
        }

        return user;
    }
}
