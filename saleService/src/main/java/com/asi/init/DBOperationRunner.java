package com.asi.init;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.asi.model.Sale;
import com.asi.repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBOperationRunner implements CommandLineRunner {
    @Autowired
    SaleRepository saleRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("---------------------- Begin saving ----------------------");

        Sale sale = new Sale(0, 6, 50.0);

        saleRepository.saveAll(Arrays.asList(sale));
        
        System.out.println("---------------------- All Data saved into Database ----------------------");
    }

}