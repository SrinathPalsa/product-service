package com.example.product_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private ProductService service;

	@GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	public List<Product> getAll() {
		return service.getAllProducts();
	}

	@PostMapping
	public Product create(@RequestBody Product p) {
		return service.save(p);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get product by Id", description = "Returns a product by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Optional<Product> product = service.findById(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(product.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	 // Find by name
    @GetMapping("/by-name/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return service.findByName(name);
    }

    // Measure query performance
    @GetMapping("/measure/{name}")
    public String measureQueryTime(@PathVariable String name) {
        long duration = service.measureQueryTime(name);
        return "Query executed in " + duration + " ms for product name: " + name;
    }
}
