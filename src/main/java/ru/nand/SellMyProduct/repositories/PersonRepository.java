package ru.nand.SellMyProduct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.models.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    Optional<Person> findByName(String name);

}
