package ir.sinafood.orderservice.service;

import ir.sinafood.orderservice.dto.CreateProductRequest;
import ir.sinafood.orderservice.dto.ProductResponse;
import ir.sinafood.orderservice.entity.Product;
import ir.sinafood.orderservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {


    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public ProductResponse create(CreateProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCreatedAt(
                Instant.now().toEpochMilli()
        );


        Product savedProduct = productRepository.save(product);


        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription()
        );
    }



    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(product ->
                        new ProductResponse(
                                product.getId(),
                                product.getName(),
                                product.getDescription()
                        )
                )
                .toList();
    }



    public ProductResponse findById(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );


        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription()
        );
    }

}