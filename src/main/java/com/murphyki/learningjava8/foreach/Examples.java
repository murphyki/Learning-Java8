/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8.foreach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author kieran
 */
public class Examples {

    public static void main(String[] args) {
        // forEach example.. here uses Map, also available on ArrayList
        Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);

        items.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));

        items.forEach((k, v) -> {
            System.out.println("Item : " + k + " Count : " + v);
            if ("E".equals(k)) {
                System.out.println("Hello E");
            }
        });
        
        // Streams Examples...
        List<String> lines = Arrays.asList("spring", "node", "mkyong");
        List<String> result1 = new ArrayList<>();
        
        //for (String line : lines) {
        //    if (!"mkyong".equals(line)) { // we dont like mkyong
        //        result1.add(line);
        //    }
        //}
        
        lines.forEach((line) -> {
            if (!"mkyong".equals(line)) { // we dont like mkyong
                result1.add(line);
            }
        });
        
        // Use streams...
        List<String> result2 = lines.stream()
                .filter(line -> !"mkyong".equals(line))
                .collect(Collectors.toList());
        
        //for (String temp : result) {
        //    System.out.println(temp);    //output : spring, node
        //}
        
        result2.forEach((item) -> {
            System.out.println(item);    //output : spring, node
        });
        
        result2.forEach(System.out::println); // method reference
 
    }
}
