/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8.streams;

import com.murphyki.learningjava8.pojos.Trader;
import com.murphyki.learningjava8.pojos.Transaction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author kieran
 */
public class Examples3 {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        
        // Find all transactions in 2011 and sort by value (small to high)
        transactions.stream()
                .filter((t) -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        
        // What are all the unique cities where the traders work?
        transactions.stream()
                .map((t) -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);
        
        transactions.stream()
                .map((t) -> t.getTrader().getCity())
                .collect(Collectors.toSet())
                .forEach(System.out::println);
        
        // Find all traders from Cambridge and sort them by name
        transactions.stream()
                .map(Transaction::getTrader)
                .filter((t) -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        
        // Return a string of all traders’ names sorted alphabetically
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList())
                .forEach(System.out::println);
        
        String strTrader = transactions.stream()
                .map((t) -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + " " + n2);
        System.out.println(strTrader);
        
        strTrader = transactions.stream()
                .map((t) -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        System.out.println(strTrader);
        
        // Are any traders based in Milan?
        boolean milanExists = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("milan"));
        System.out.println("Milan exists " + milanExists);
        
        // Print all transactions’ values from the traders living in Cambridge
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
        
        // What’s the highest value of all the transactions?
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println("Max value = " + max.get());
        
        // Find the transaction with the smallest value
        Optional<Integer> min = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        System.out.println("Min value = " + min.get());
        
        Optional<Transaction> smallestTransaction = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        if (smallestTransaction.isPresent()) {
            System.out.println("Min value = " + smallestTransaction.get().getValue());
        }
        
        smallestTransaction = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        if (smallestTransaction.isPresent()) {
            System.out.println("Min value = " + smallestTransaction.get().getValue());
        }
        
    }
}
