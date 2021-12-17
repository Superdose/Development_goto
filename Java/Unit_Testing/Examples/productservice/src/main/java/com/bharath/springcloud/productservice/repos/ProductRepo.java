package com.bharath.springcloud.productservice.repos;

import com.bharath.springcloud.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
