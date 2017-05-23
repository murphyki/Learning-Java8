/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8.streams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author kieran
 */
public class Examples2 {
    public static void main(String[] args) {
        Stream.of(1,2,3,4,5)
                .map((number) -> number * number)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
