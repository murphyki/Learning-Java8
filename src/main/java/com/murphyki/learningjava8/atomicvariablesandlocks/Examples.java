/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.murphyki.learningjava8.atomicvariablesandlocks;

import com.murphyki.learningjava8.utils.Utils;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author kieran
 */
public class Examples {

    public static void main(String[] args) {
        Examples ex = new Examples();

        ex.atomicIntegerExample();
        ex.lockExample();
        ex.readWriteLockExample();
        ex.semaphoreExample();
    }

    private void lockExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        ReentrantLock lock = new ReentrantLock();

        executor.submit(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Examples.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        Utils.stopExecutor(executor);
    }

    private void readWriteLockExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Map<String, String> map = new HashMap<>();

        ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                TimeUnit.SECONDS.sleep(3);
                map.put("foo", "bar");
            } catch (InterruptedException ex) {
                Logger.getLogger(Examples.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                lock.writeLock().unlock();
            }
        });

        Runnable readTask = () -> {
            System.out.println("Trying to get readLock...");
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Examples.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        Utils.stopExecutor(executor);
    }

    private void atomicIntegerExample() {
        AtomicInteger ai = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        IntStream.range(0, 1000).forEach(i -> executor.submit(ai::incrementAndGet));

        Utils.stopExecutor(executor);

        System.out.println("atomicIntegerExamples result -> " + ai.get());
    }

    private void semaphoreExample() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(longRunningTask));

        Utils.stopExecutor(executor);
    }
}
