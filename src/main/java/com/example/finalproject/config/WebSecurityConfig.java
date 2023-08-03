package com.example.finalproject.config;

import com.example.finalproject.security.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailService userDetailsService;

    public WebSecurityConfig(UserDetailService userDetailService) {
        this.userDetailsService = userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home").authenticated()
                .antMatchers("/departments/**").permitAll()
                .antMatchers("/employees/index").permitAll()
//                .antMatchers("/employees/edit").hasAuthority("USER")
//                .antMatchers("/employees/search").permitAll()
//                .antMatchers("/employees/detail").permitAll()
//                .antMatchers("/employees/add").hasAuthority("ADMIN")
//                .antMatchers("/certificates/**").hasAuthority("USER")
//                .antMatchers("/skills/**").hasAuthority("USER")
//                .antMatchers("/project/**").hasAuthority("ADMIN")
//                .antMatchers("/experience/**").hasAuthority("ADMIN")
                .and()
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .exceptionHandling()
                .accessDeniedPage("/403");

    }
}