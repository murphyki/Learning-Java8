/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kieran
 */
public class ExecutorServiceExamples {

    public static void runExamples(String[] args) {
        ExecutorServiceExamples ex = new ExecutorServiceExamples();
        ex.oldRunnableWay();
        ex.newRunnableWay();
        ex.executorSubmitExample();
        ex.executorCallableExample();
        ex.executorInvokeAllExample();
    }

    private void oldRunnableWay() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    Logger.getLogger(ExecutorServiceExamples.class.getName()).log(Level.INFO, "oldRunnableWay just woke up!!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecutorServiceExamples.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    private void newRunnableWay() {
        // Runnable is a functional interface
        Runnable r = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                Logger.getLogger(ExecutorServiceExamples.class.getName()).log(Level.INFO, "newRunnableWay just woke up!!");
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecutorServiceExamples.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        Thread t = new Thread(r);
        t.start();
    }

    private void executorSubmitExample() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("executorSubmitExample -> hello " + threadName);
        });
        Utils.stopExecutor(executor);
    }

    private void executorCallableExample() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> c = () -> {
            TimeUnit.SECONDS.sleep(3);
            String threadName = Thread.currentThread().getName();
            System.out.println("executorCallableExample -> hello " + threadName);

            return "Hello From executorCallableExample";
        };

        Future<String> result = executor.submit(c);

        System.out.println("future done? " + result.isDone());

        try {
            System.out.println("result: " + result.get());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ExecutorServiceExamples.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("future done? " + result.isDone());
            Utils.stopExecutor(executor);
        }
    }

    private void executorInvokeAllExample() {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "callable 1",
                () -> "callable 2",
                () -> "callable 3"
        );

        try {
            executor.invokeAll(callables).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(System.out::println);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecutorServiceExamples.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Utils.stopExecutor(executor);
        }

    }
}
