//package com.example.finalproject.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
//////    @Override
//////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests()
////                .antMatchers("/employees/homePage").authenticated()
////                .antMatchers("/departments/**").hasRole("ADMIN")
////                .antMatchers("/employees/index").permitAll()
////                .and()
////                .formLogin((form) -> form
////                                .loginPage("/login")
////                                .usernameParameter("email")
////                                .passwordParameter("password")
////                                .defaultSuccessUrl("/employees/index")
////                                .permitAll()
////                )
////                .logout((logout) -> logout.permitAll())
////                .exceptionHandling()
////                .accessDeniedPage("/403");
////    }
//}
//
