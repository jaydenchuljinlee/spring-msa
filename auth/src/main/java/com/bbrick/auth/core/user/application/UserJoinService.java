package com.bbrick.auth.core.user.application;

import com.bbrick.auth.core.auth.domain.PasswordEncoder;
import com.bbrick.auth.core.user.domain.entity.Gender;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.user.domain.exceptions.UserDuplicationException;
import com.bbrick.auth.core.user.domain.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserJoinService {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetail join(String email, String password, String name, String phoneNumber, Gender gender) {
        boolean userWithEmailExists = this.userDetailRepository.existsByEmail(email);

        if(userWithEmailExists) {
            throw UserDuplicationException.duplicatedEmail(email);
        }

        String encodedPassowrd = passwordEncoder.encode(password);
        UserDetail userDetail = UserDetail.builder()
                .email(email)
                .encodedPassword(encodedPassowrd)
                .userName(name)
                .phoneNumber(phoneNumber)
                .gender(gender).build();

        this.userDetailRepository.save(userDetail);

        return userDetail;


    }
}
