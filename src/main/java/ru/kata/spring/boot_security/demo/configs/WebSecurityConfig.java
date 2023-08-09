package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

   @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override //Только авторизованные пользователи увидят страницу приветствия
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/user/**").authenticated() //доступ на страницу требует аутентификации
                .antMatchers("/admin/**").hasRole("ADMIN")

                .and()
//                .formLogin().successHandler(successUserHandler)//предоставляем стандартную форму для логина//
//                .permitAll()//
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/")
                .permitAll();
    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("1")
//                        .roles("USER")
//                        .build();
//
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("2")
//                        .roles("ADMIN", "USER")
//                        .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//    @Bean
//    public JdbcUserDetailsManager users (DataSource dataSource) {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("1")
//                        .roles("USER")
//                        .build();
//
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("2")
//                        .roles("ADMIN", "USER")
//                        .build();
//        JdbcUserDetailsManager userDetailsManager= new JdbcUserDetailsManager(dataSource);
//        if (userDetailsManager.userExists(user.getUsername())) {
//            userDetailsManager.deleteUser(user.getUsername());
//        }
//        if(userDetailsManager.userExists(admin.getUsername())) {
//            userDetailsManager.deleteUser(admin.getUsername());
//        }
//        userDetailsManager.createUser(user);
//        userDetailsManager.createUser(admin);
//        return userDetailsManager;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

}