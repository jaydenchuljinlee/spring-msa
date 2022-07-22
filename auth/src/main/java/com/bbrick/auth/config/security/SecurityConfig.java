package com.bbrick.auth.config.security;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.config.security.filter.jwt.JwtAuthenticationFilter;
import com.bbrick.auth.config.security.filter.jwt.JwtLogoutFilter;
import com.bbrick.auth.config.security.handler.jwt.JwtAuthenticationFailHandler;
import com.bbrick.auth.config.security.handler.jwt.JwtAuthenticationSuccessHandler;
import com.bbrick.auth.config.security.handler.jwt.JwtLogoutHandler;
import com.bbrick.auth.config.security.provider.EmailPasswordAuthenticationProvider;
import com.bbrick.auth.core.auth.application.AuthenticationService;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.user.application.UserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailService userDetailService;
    private final TokenService tokenService;
    private final Environment environment;

    @Bean
    public WebSecurityCustomizer configure() throws Exception{
        String[] activeProfiles =  environment.getActiveProfiles();

        return (web) -> {
            for (String activeProfile: activeProfiles) {
                web.debug(true);
            }
        };
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        WebConstants.URL.LOGIN_REQUEST_PATH,
                        WebConstants.URL.USER_JOIN_PATH_WITH_TRAILING_SLASH
                ).permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().println(
                            new ObjectMapper().writeValueAsString(BaseResponse.fail(authException.getMessage()))
                    );
                }))

                .and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(
                this.userDetailService,
                this.tokenService,
                new JwtAuthenticationSuccessHandler(),
                new JwtAuthenticationFailHandler(),
                new JwtTokenUtil()
        );
    }
}
