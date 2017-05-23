/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8.streams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author kieran
 */
public class Examples5 {

    public static void main(String[] args) {
        Stream<int[]> pythagoreanTriples
                = IntStream.rangeClosed(1, 100)
                        .boxed()
                        .flatMap(a-> IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                        );
        
        pythagoreanTriples.forEach((triple) -> {
            System.out.println("(" + triple[0] + ", " + triple[1] + ", " + triple[2] + ")");
        });
        
        
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);
    }
}
