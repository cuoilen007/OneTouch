package com.example.onetouchapi;

import com.example.onetouchapi.seed.SeedCategoriesTable;
import com.example.onetouchapi.seed.SeedProductsTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.text.ParseException;

@SpringBootApplication
public class OneTouchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneTouchApiApplication.class, args);
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {
        SeedCategoriesTable.insertData();

        SeedProductsTable.insertData();

    }

}
