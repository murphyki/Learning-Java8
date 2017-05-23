/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8.streams;

import com.murphyki.learningjava8.pojos.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kieran
 */
public class Examples1 {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 800, Dish.Type.MEAT),
                new Dish("chicken", false, 800, Dish.Type.MEAT),
                new Dish("french fries", false, 800, Dish.Type.OTHER),
                new Dish("rice", false, 800, Dish.Type.OTHER),
                new Dish("season fruit", false, 800, Dish.Type.OTHER),
                new Dish("pizza", false, 800, Dish.Type.OTHER),
                new Dish("prawns", false, 800, Dish.Type.FISH),
                new Dish("salmon", false, 800, Dish.Type.FISH)
        );
        
        menu.stream()
            .filter((d) -> d.getType() == Dish.Type.MEAT)
            .map(Dish::getName)
            .collect(Collectors.toList())
            .forEach(System.out::println);
    }
}
