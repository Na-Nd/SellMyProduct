package ru.nand.SellMyProduct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPerson(Person person);
}
