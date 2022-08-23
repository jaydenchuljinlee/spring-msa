package com.bbrick.auth.unit.core.user.application.user;

import com.bbrick.auth.core.user.application.UserDetailService;
import com.bbrick.auth.core.user.domain.entity.Gender;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.user.domain.repository.UserDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserDetailService 클래스")
public class UserDetailServiceTest {
    @Mock
    private UserDetailRepository userDetailRepository;
    @InjectMocks
    private UserDetailService userDetailService;

    @DisplayName("check 메서드는")
    @Nested
    class check {

        @DisplayName("이메일에 해당하는 회원 정보를 반환한다.")
        @Test
        void loadUserByUserName() {
            // given
            given(userDetailRepository.findByEmail("tester@gmail.com")).willReturn(
                    Optional.of(
                            UserDetail.builder()
                                    .email("tester@gmail.com")
                                    .encodedPassword("Passw@rd123")
                                    .userName("cheoljin")
                                    .phoneNumber("01012341234")
                                    .gender(Gender.MALE)
                                    .build()
                    )
            );

            // when & then
            assertThat(userDetailService.loadUserByUsername("tester@gmail.com")).isInstanceOf(UserDetail.class);
        }

        @DisplayName("이메일 정보가 조회되지 않으면 예외를 반환한다.")
        @Test
        void validateUserNameIsNotFounded() {
            // given
            given(userDetailRepository.findByEmail("tester@gmail.com")).willReturn(
                    Optional.empty()
            );

            // when & then
            assertThatThrownBy(
                    () -> userDetailService.loadUserByUsername("tester@gmail.com")
            ).isInstanceOf(UsernameNotFoundException.class);
        }
    }


}
