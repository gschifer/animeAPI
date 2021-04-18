package com.example.animeapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//  It's always a good practice to use the generic type which is the UserDetailsService interface
//  In this case the IDE is not finding any other UserDetailsService which normally it'll cause a conflict
//  Hence, we don't need to use the example below, but I will leave as commented
//  In @Service of UserDetailsServiceImpl, just use as @Service(value = "userDetailsServiceImpl") to be able to identify

    //    @Qualifier("userDetailsServiceImpl")
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
        });
//        http.formLogin(); removes the form authentication, we use now the basic authentication which is the alert.
//        CSRF was disabled below due to impact in our post requests. It's a protection from Spring Security but it's
//        safe to disable due to our API being stateless
        http.httpBasic().and().csrf().disable();
    }

//        Authentication and roles permitted to the users
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
