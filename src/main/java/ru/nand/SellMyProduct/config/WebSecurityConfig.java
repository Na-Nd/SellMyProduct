package ru.nand.SellMyProduct.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.services.PersonDetailsService;
import ru.nand.SellMyProduct.services.PersonService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(PersonService personService, PersonDetailsService personDetailsService, PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/person/register").permitAll()
                        .requestMatchers("/person/login").permitAll()
                        .requestMatchers("/person/codeAuth").permitAll()
                        .requestMatchers("/codeAuth").permitAll()
                        .requestMatchers("/hello").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/person/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Autowired
    public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {
        System.out.println("initialize");
        builder.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return personDetailsService;
    }


}
