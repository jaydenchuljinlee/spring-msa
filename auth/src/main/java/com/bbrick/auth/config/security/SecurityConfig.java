package com.bbrick.auth.config.security;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.config.security.filter.AuthenticationFailHandler;
import com.bbrick.auth.config.security.filter.AuthenticationFilter;
import com.bbrick.auth.config.security.filter.AuthenticationSuccessHandler;
import com.bbrick.auth.config.security.provider.EmailPasswordAuthenticationProvider;
import com.bbrick.auth.core.auth.application.AuthenticationService;
import com.bbrick.auth.core.auth.domain.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationService authenticationService;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(this.emailPasswordAuthenticationProvider());
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().changeSessionId()
                .and()

                .addFilterAfter(this.authenticationFilter(authenticationManager), CsrfFilter.class)
                .authorizeRequests()

                .requestMatchers(new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.USER_JOIN_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.USER_JOIN_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                )).permitAll()
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

                .logout()
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.LOGOUT_REQUEST_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.LOGOUT_REQUEST_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                ))
                .logoutSuccessHandler(((request, response, authentication) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().println(
                            new ObjectMapper().writeValueAsString(BaseResponse.success())
                    );
                }))
                .deleteCookies(WebConstants.Session.COOKIE_NAME);

        return http.build();
    }

    private AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        return new AuthenticationFilter(
                new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                ),
                authenticationManager,
                new AuthenticationSuccessHandler(),
                new AuthenticationFailHandler(),
                new SessionFixationProtectionStrategy()
        );
    }

    @Bean
    public EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider(this.authenticationService);
    }
}
