package com.ssp.apps.spring.security;

import com.ssp.apps.spring.security.stateless.CustomStatelessAuthorizationRequestRepository;
import com.ssp.apps.spring.security.stateless.OAuthController;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomStatelessAuthorizationRequestRepository customStatelessAuthorizationRequestRepository;
    private final OAuthController oauthController;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().sessionManagement(config -> {
                    config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .oauth2Login(config -> {
                    config.authorizationEndpoint(subconfig -> {
                        subconfig.authorizationRequestRepository(this.customStatelessAuthorizationRequestRepository);
                    });
                    config.successHandler(this.oauthController::oauthSuccessResponse);
                    config.failureHandler(this.oauthController::oauthFailureResponse);
                });
        //@formatter:on
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //@formatter:off
        web.ignoring()
                .antMatchers("/home");
        //@formatter:on
    }

}
