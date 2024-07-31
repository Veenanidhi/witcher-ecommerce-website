package com.witcher.e_commerce.application.witcher.service.product;

import com.witcher.e_commerce.application.witcher.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

public List<Product> getAllProduct();

public void addProduct(Product product);

public void removeProductById(Long id);

public Optional <Product> getProductById(Long id);

public List<Product> getAllProductsByCategoryId(Long id);


}
