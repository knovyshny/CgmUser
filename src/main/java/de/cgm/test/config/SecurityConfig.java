package de.cgm.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //remove any restriction for access to the endpoints
        //because Rest API will be used by React or Angular app
        //we need to add at least cors
        //but because we have no athorization, we'll need to disable
        //csrf
        http.cors().and().csrf().disable().authorizeRequests().anyRequest().permitAll();
    }
}

