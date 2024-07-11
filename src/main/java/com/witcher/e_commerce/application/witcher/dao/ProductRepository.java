package com.witcher.e_commerce.application.witcher.dao;

import com.witcher.e_commerce.application.witcher.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

     List<Product> findAllByCategoryId(long id);


}
