package com.jvm.Week6.config;

import com.jvm.Week6.model.Product;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product item) throws Exception {
            System.out.println(item.getDescription());
        return item;
}
}