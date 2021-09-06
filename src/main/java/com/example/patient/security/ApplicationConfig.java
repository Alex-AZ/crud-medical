package com.example.patient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure( AuthenticationManagerBuilder auth  ) throws Exception {
        // System.out.println(passwordEncoder().encode("1234"));

        // auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN");

       /* auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, 1 as enabled "
                    + "FROM user "
                    + "WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email, roles "
                    + "FROM user "
                    + "WHERE email = ?")
                .passwordEncoder(
                        passwordEncoder()
                );*/

        // Céde l'authentification du user au service
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");
        http.authorizeRequests().antMatchers("/login", "/css/**").permitAll();
        http.authorizeRequests().antMatchers("**/add", "**/edit/**", "**/delete/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

