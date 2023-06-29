package com.shopping.productservice.service;

import com.shopping.productservice.dto.ProductRequest;
import com.shopping.productservice.dto.ProductResponse;
import com.shopping.productservice.model.Product;
import com.shopping.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    //use requiredArgsConstructor instead of product repository
    /*public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }*/

    public void createProduct(ProductRequest productRequest){
        Product product=Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        //log4jfeature{}
        log.info("Product {} is saved",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products=productRepository.findAll();
        List<ProductResponse> productResponse=products.stream().map(product -> mapToProductResponse(product)).collect(Collectors.toList());
        return productResponse;
    }
    private ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse=ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        return productResponse;
    }
}
