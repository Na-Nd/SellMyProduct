package ru.nand.SellMyProduct.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nand.SellMyProduct.details.PersonDetails;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.repositories.PersonRepository;

import javax.sql.DataSource;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name:" + username));

        //return User.builder().username(person.getName()).password(person.getPassword()).roles(person.getPersonRole()).build();
        return new PersonDetails(person);
    }
}
