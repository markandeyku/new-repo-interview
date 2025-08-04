package test;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Predicate;

public class HashMapConcurrencyIssue {

    public static void main(String[] args) {
        Predicate<String> predicate = s -> {
            return true;
        };
        HashMap<String, Integer> map = new HashMap<>();
        // moral of the story it should be the dto as object but in event it will be string

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                map.put("key" + i, i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                map.put("key" + i, i);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Map size: " + map.size());
    }
}