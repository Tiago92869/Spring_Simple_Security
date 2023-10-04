package com.demo.security.controller;

import com.demo.security.domain.Product;
import com.demo.security.dto.ProductDto;
import com.demo.security.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.Optional;
import java.util.UUID;

@Tag(name = "Product", description = "Product Management")
@RequestMapping("/product")
@RestController
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get Product Shops")
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> getAllProducts(Pageable pageable){

        return this.productRepository.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Product by id")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable UUID id){

        Optional<Product> optionalProduct = this.productRepository.findById(String.valueOf(id));

        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }else {
            throw new NotFoundException("Could not find");
        }
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Shop")
    @ResponseStatus(HttpStatus.OK)
    public Product createProduct(@RequestBody ProductDto productDto){

        Product product = new Product(String.valueOf(UUID.randomUUID()), productDto.getName(), productDto.getQuantity());

        return this.productRepository.save(product);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Product by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID id){

        Optional<Product> optionalProduct = this.productRepository.findById(String.valueOf(id));

        if(optionalProduct.isPresent()){
           this.productRepository.delete(optionalProduct.get());
        }else {
            throw new NotFoundException("Could not find");
        }
    }
}
