package com.bbrick.auth.core.user.application;

import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.user.domain.exceptions.UserNotFoundException;
import com.bbrick.auth.core.user.domain.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserDetailRepository userDetailRepository;

    public UserDetail getUser(long userId) {
        return this.userDetailRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetail> userDetailOptional = userDetailRepository.findByEmail(username);

        if (userDetailOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return userDetailOptional.get();
    }
}
