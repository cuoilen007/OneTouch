package com.example.onetouchapi.seed;

import com.example.onetouchapi.model.Category;
import com.example.onetouchapi.repository.ICategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SeedCategoriesTable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeedCategoriesTable.class);

    private static ICategoryRepository categoryRepository;

    public SeedCategoriesTable(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static void insertData() {
        long count = categoryRepository.count();
        if (count == 0) {
            // Insert Categories
            Category category01 = new Category(1,"Card");
            Category category02 = new Category(2,"Sticker");


            // Insert Data
            categoryRepository.saveAll(Arrays.asList(category01, category02));
            LOGGER.info("Categories Table Seeded.");
        } else {
            LOGGER.trace("Categories Seeding Not Required.");
        }
    }
}
