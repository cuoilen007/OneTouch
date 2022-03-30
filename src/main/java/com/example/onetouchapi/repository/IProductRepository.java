package com.example.onetouchapi.repository;

import com.example.onetouchapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface IProductRepository extends JpaRepository<Product, String> {
    Page<Product> findProductsByNameLike(String keyword, Pageable pageable);
}
