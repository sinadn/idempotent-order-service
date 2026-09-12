package ir.sinafood.orderservice.controller;

import ir.sinafood.orderservice.dto.CreateProductRequest;
import ir.sinafood.orderservice.dto.ProductResponse;
import ir.sinafood.orderservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ProductResponse create(
            @RequestBody CreateProductRequest request){

        return productService.create(request);
    }


    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.findAll();
    }



    @GetMapping("/{id}")
    public ProductResponse findById(
            @PathVariable UUID id
    ){

        return productService.findById(id);
    }

}