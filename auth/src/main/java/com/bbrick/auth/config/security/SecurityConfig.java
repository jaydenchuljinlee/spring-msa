package com.bbrick.auth.config.security;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.config.security.filter.jwt.JwtAuthenticationFilter;
import com.bbrick.auth.config.security.handler.jwt.JwtAuthenticationFailHandler;
import com.bbrick.auth.config.security.handler.jwt.JwtAuthenticationSuccessHandler;
import com.bbrick.auth.config.security.handler.session.AuthenticationFailHandler;
import com.bbrick.auth.config.security.filter.session.SessionAuthenticationFilter;
import com.bbrick.auth.config.security.handler.session.AuthenticationSuccessHandler;
import com.bbrick.auth.config.security.provider.EmailPasswordAuthenticationProvider;
import com.bbrick.auth.core.auth.application.AuthenticationService;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.user.application.UserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationService authenticationService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailService userDetailService;
    private final Environment environment;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer configure() throws Exception{
        String[] activeProfiles =  environment.getActiveProfiles();

        return (web) -> {
            for (String activeProfile: activeProfiles) {
                web.debug(true);
            }
        };
    }


    public SecurityFilterChain providerFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.authenticationProvider(this.emailPasswordAuthenticationProvider());

        return http.build();
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//        auth.authenticationProvider(this.emailPasswordAuthenticationProvider());

        http
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                .requestMatchers(new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH, HttpMethod.POST.name()),
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

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(
                this.userDetailService,
                new JwtAuthenticationSuccessHandler(),
                new JwtAuthenticationFailHandler(),
                new JwtTokenUtil()
        );
    }


    public SecurityFilterChain sessionFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().changeSessionId()
                .and()

                .addFilterAfter(this.sessionAuthenticationFilter(), CsrfFilter.class)
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

    private SessionAuthenticationFilter sessionAuthenticationFilter() throws Exception {
        return new SessionAuthenticationFilter(
                new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                ),
                this.authenticationManager(),
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
