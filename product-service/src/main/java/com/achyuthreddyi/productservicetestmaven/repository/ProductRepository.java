package com.achyuthreddyi.productservicetestmaven.repository;

import com.achyuthreddyi.productservicetestmaven.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
