package ru.nand.SellMyProduct.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nand.SellMyProduct.models.Product;
import ru.nand.SellMyProduct.repositories.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public String saveImage(MultipartFile imageFile){
        String fileName = UUID.randomUUID().toString() + "." + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadPath + "/" + fileName);
        try {
            Files.copy(imageFile.getInputStream(), path);
        } catch (IOException e){
            e.printStackTrace();
        }
        return fileName;
    }


}
