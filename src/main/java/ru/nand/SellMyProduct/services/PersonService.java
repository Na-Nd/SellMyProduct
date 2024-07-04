package ru.nand.SellMyProduct.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.models.Product;
import ru.nand.SellMyProduct.repositories.PersonRepository;
import ru.nand.SellMyProduct.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    @Autowired
    public PersonService(PersonRepository personRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService) {
        this.personRepository = personRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public List<Product> findProductsByPerson(Person person){
        return productRepository.findByPerson(person);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    public Person register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setPersonRole("USER");
        person.setAuthenticated(false);

        String authCode = mailSenderService.generateAuthCode();
        person.setAuthCode(authCode);

        Person savedPerson = personRepository.save(person);

        String subject = "Your authentication code, bro";
        String body = "Your authentication code is: " + authCode;

        mailSenderService.sendEmail(person.getEmail(), subject, body);

        return savedPerson;
    }

    public boolean authenticateUser(String email, String authCode){
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if(optionalPerson.isPresent()){ // Да
            Person person = optionalPerson.get();
            if (authCode.equals(person.getAuthCode())){
                person.setAuthenticated(true);
                person.setAuthCode(null);
                personRepository.save(person);
                return true;
            }
        }
        return false;
    }

    public Optional<Person> findByEmail(String email){
        return personRepository.findByEmail(email);
    }
    public Optional<Person> findByName(String name){return personRepository.findByName(name);}


}
