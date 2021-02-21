package com.example.icommerce.services;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.icommerce.utilities.SkuGenerator;

@Component
public class DataInitialization implements ApplicationRunner {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private final List<String> COLORS = Arrays.asList(
        "Red", "White", "Black", "Orange", "Yellow", "Green", "Blue", "Purple", "Brown"
    );

    @Override
    public void run (ApplicationArguments args) throws Exception {

        userService.createUser("admin", "adminpassword");
        userService.createUser("user", "userpassword");

        for (int i = 1; i <= 20; i++) {
            String sku = SkuGenerator.generate("AIP");
            String name = "Iphone " + getRandomNumber(1, 100);
            String description = "This is description of " + name;
            String color = COLORS.get(getRandomNumber(1, 10) - 1);
            double price = getRandomNumber(1000, 10000);
            productService.createProduct(sku, name, description, "Apple", color, price);
            Thread.sleep(100);
        }

        for (int i = 1; i <= 20; i++) {
            String sku = SkuGenerator.generate("SAM");
            String name = "Samsung " + getRandomNumber(1, 100);
            String description = "This is description of " + name;
            String color = COLORS.get(getRandomNumber(1, 10) - 1);
            double price = getRandomNumber(1000, 10000);
            productService.createProduct(sku, name, description, "Samsung", color, price);
            Thread.sleep(100);
        }

        for (int i = 1; i <= 20; i++) {
            String sku = SkuGenerator.generate("XIA");
            String name = "Xiaomi mi " + getRandomNumber(1, 100);
            String description = "This is description of " + name;
            String color = COLORS.get(getRandomNumber(1, 10) - 1);
            double price = getRandomNumber(1000, 10000);
            productService.createProduct(sku, name, description, "Xiaomi", color, price);
            Thread.sleep(100);
        }

        for (int i = 1; i <= 20; i++) {
            String sku = SkuGenerator.generate("XIA");
            String name = "Oppo Note " + getRandomNumber(1, 100);
            String description = "This is description of " + name;
            String color = COLORS.get(getRandomNumber(1, 10) - 1);
            double price = getRandomNumber(1000, 10000);
            productService.createProduct(sku, name, description, "Oppo", color, price);
            Thread.sleep(100);
        }

        for (int i = 1; i <= 20; i++) {
            String sku = SkuGenerator.generate();
            String name = "Product " + getRandomNumber(1, 100);
            String description = "This is description of " + name;
            double price = getRandomNumber(1000, 10000);
            productService.createProduct(sku, name, description, "Brand", "Color", price);
            Thread.sleep(100);
        }
    }

    private int getRandomNumber (int min, int max) {
        return new Random().ints(min, max).limit(1).findFirst().getAsInt();
    }
}
