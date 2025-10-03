package thread;

import java.util.concurrent.*;



public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {

        // Custom executor (better than ForkJoin common pool in production)
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 1. supplyAsync: Run a supplier async
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            System.out.println("Task f1 by " + Thread.currentThread().getName());
            return 10;
        }, executor);

        // 2. runAsync: Run a Runnable async
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> {
            sleep(300);
            System.out.println("Task f2 (Runnable) by " + Thread.currentThread().getName());
        }, executor);

        // 3. thenApply: Transform result
        CompletableFuture<Integer> f3 = f1.thenApply(n -> {
            System.out.println("Transforming f1 result...");
            return n * 2;
        });

        // 4. thenAccept: Consume result
        CompletableFuture<Void> f4 = f3.thenAccept(res -> {
            System.out.println("Consumed f3 result: " + res);
        });

        // 5. thenRun: Run task after completion, doesn’t use result
        CompletableFuture<Void> f5 = f4.thenRun(() -> {
            System.out.println("f4 finished, running f5");
        });

        // 6. thenCombine: Combine two futures
        CompletableFuture<Integer> f6 = f1.thenCombine(f3, (a, b) -> {
            System.out.println("Combining f1 + f3");
            return a + b;
        });

        // 7. thenCompose: Flatten nested futures (async pipeline)
        CompletableFuture<Integer> f7 = f1.thenCompose(val -> {
            return CompletableFuture.supplyAsync(() -> val * 5, executor);
        });

        // 8. allOf: Run multiple tasks in parallel and wait for all
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f3, f6, f7, f2, f5);
        all.join();  // wait for all

        System.out.println("f6 result (combine): " + f6.get());
        System.out.println("f7 result (compose): " + f7.get());

        // 9. anyOf: First one to finish
        CompletableFuture<Object> any = CompletableFuture.anyOf(f1, f3, f6, f7);
        System.out.println("anyOf result: " + any.get());

        // 10. exceptionally: Handle error
        CompletableFuture<Integer> f8 = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("Boom!");
            return 1;
        }, executor).exceptionally(ex -> {
            System.out.println("Handled exception: " + ex.getMessage());
            return -1;
        });
        System.out.println("f8 (exception handled): " + f8.get());

        // 11. handle: Always executed (success or failure)
        CompletableFuture<String> f9 = f8.handle((res, ex) -> {
            if (ex != null) return "Recovered: " + ex.getMessage();
            return "Got: " + res;
        });
        System.out.println("f9 (handle): " + f9.get());

        // 12. whenComplete: Side-effect after completion
        CompletableFuture<Integer> f10 = CompletableFuture.supplyAsync(() -> 99, executor)
                .whenComplete((res, ex) -> {
                    if (ex == null) System.out.println("f10 completed with: " + res);
                });
        System.out.println("f10 result: " + f10.get());

        // 13. timeout / orTimeout / completeOnTimeout (Java 9+)
        CompletableFuture<String> f11 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Slow task";
        }, executor).completeOnTimeout("Default due to timeout", 1, TimeUnit.SECONDS);

        System.out.println("f11 result: " + f11.get());

        executor.shutdown();
    }

    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}

