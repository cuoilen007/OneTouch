package com.example.onetouchapi.seed;


import com.example.onetouchapi.model.Category;
import com.example.onetouchapi.model.Product;
import com.example.onetouchapi.repository.ICategoryRepository;
import com.example.onetouchapi.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class SeedProductsTable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeedProductsTable.class);

    private static IProductRepository productRepository;

    private static ICategoryRepository categoryRepository;

    public SeedProductsTable(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public static void insertData() {
        long count = productRepository.count();

        if (count == 0) {
            // Insert Card
            // Insert Card1
            Category category01 = categoryRepository.findById(1L).get();

            Product product01 = new Product("C0882021035821","the den",188000L,"mau den ca tinh","imagef","imagef","imagef","imagef", category01);


            //Insert Sticker
            //Insert Sticker01
            Category category02 = categoryRepository.findById(2L).get();
            Product sticker01 = new Product("C0882021035821","sticker den",99000L,"mau den ca tinh","image1","image2","image1","image2", category02);



            // Insert Data
            productRepository.saveAll(Arrays.asList(product01, sticker01));
            LOGGER.info("Products Table Seeded.");
        } else {
            LOGGER.trace("Products Seeding Not Required.");
        }
    }
}
