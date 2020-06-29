package com.example.iBlog;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected WebSecurityConfig() {
        super();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/img/**","/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //放行
        http.authorizeRequests().antMatchers("/user/addUser","/h2-console/**","/","/nologin","/article/noLoginFindPage").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/main",true).permitAll()
                .and()
                .logout().permitAll();

    }

}
