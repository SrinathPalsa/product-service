package com.example.product_service.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductService {

	@Autowired
    private ProductRepository repo;

	private int callCount = 0;

    @CircuitBreaker(name = "getAllProductsCB", fallbackMethod = "getAllFallback")
    public List<Product> getAllProducts() {
  //  	callCount++;
        // Simulate a potential failure
//        if(callCount <= 3) {  // 50% chance of failure
//        	throw new RuntimeException("Simulated service failure #" + callCount);
//        }
        return repo.findAll();
    }

    // Fallback method
    public List<Product> getAllFallback(Throwable t) {
        List<Product> fallbackList = new ArrayList<>();
        fallbackList.add(new Product(0L, "Fallback Product 1",0.0));
        fallbackList.add(new Product(0L, "Fallback Product 1",0.0));
        return fallbackList;
    }
    
    public List<Product> findByName(String name) {
        return repo.findByName(name);
    }

    public long measureQueryTime(String productName) {
        // Warm up to avoid Hibernate overhead
        repo.findByName(productName);

        long start = System.nanoTime();
        List<Product> result = repo.findByName(productName);
        long end = System.nanoTime();

        long durationMs = (end - start) / 1_000_000;
        System.out.println("Query result size: " + result.size());
        System.out.println("Query executed in: " + durationMs + " ms");

        return durationMs;
    }
    @CachePut(value = "products", key = "#result.id")
	public Product save(Product p) {
		return repo.save(p);
	}
    @Cacheable(value = "products", key = "#id")
	public Optional<Product> findById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

}
