package ru.bacaneco.voting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().withUser("user")
                .password(passwordEncoder().encode("password")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder().encode("password")).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/restaurants/**").hasAnyRole("ADMIN")
                .antMatchers("/menus/todays").permitAll()
                .antMatchers("/menus/**").hasAnyRole("ADMIN")
                .antMatchers("/dishes/**").hasAnyRole("ADMIN")
                .antMatchers("/menus/**").hasAnyRole("ADMIN")
                .antMatchers("/votes/filter").hasAnyRole("ADMIN")
                .antMatchers("/votes/todays").hasAnyRole("ADMIN")
                .antMatchers("/votes/**").hasAnyRole("USER")
                .antMatchers("/profile/register").anonymous()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/**").permitAll();


    }

}
