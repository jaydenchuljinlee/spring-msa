package com.bbrick.auth.unit.core.user.application.user;

import com.bbrick.auth.core.auth.domain.PasswordEncoder;
import com.bbrick.auth.core.user.application.UserJoinService;
import com.bbrick.auth.core.user.domain.entity.Gender;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.core.user.domain.exceptions.UserDuplicationException;
import com.bbrick.auth.core.user.infrastructure.jpa.JpaUserDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserJoinService 클래스")
public class UserJoinServiceTest {
    @Mock
    private JpaUserDetailRepository userDetailRepository;
    @Spy
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserJoinService userJoinService;

    @DisplayName("check 메서드는")
    @Nested
    class check {

        @DisplayName("회원 인증을 하고 인증 된 회원 객체를 리턴한다.")
        @Test
        void joinUser() {
            // when
            UserDetail joinUser = userJoinService.join("tester@gmail.com", "Passw@rd123", "cheoljin", "01089669169", Gender.MALE);

            // then
            assertThat(joinUser.getEmail()).isEqualTo("tester@gmail.com");
            assertThat(passwordEncoder.matches("Passw@rd123", joinUser.getEncodedPassword())).isTrue();
            assertThat(joinUser.getName()).isEqualTo("cheoljin");
            assertThat(joinUser.getPhoneNumber()).isEqualTo("01089669169");
            assertThat(joinUser.getGender()).isEqualTo(Gender.MALE);

        }

        @DisplayName("이메일이 중복일 경우 false를 반환한다.")
        @Test
        void validateEmailIsExisted() {
            // given
            given(userDetailRepository.existsByEmail("tester@gmail.com")).willReturn(true);

            // when & then
            assertThatThrownBy(() -> {
                userJoinService.join("tester@gmail.com", "Passw@rd123", "cheoljin", "01089669169", Gender.MALE);
            }).isInstanceOf(UserDuplicationException.class);
        }
    }
}
