package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TwoThreadCombine {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(9);
        list.add(3);
        list.add(10);

        List<Integer> list1 = new ArrayList<>();
        list.add(120);
        list.add(9);
        list.add(33);
        list.add(10);

        CompletableFuture<List<Integer>> listCompletableFuture = CompletableFuture.supplyAsync((() -> list.stream().sorted().collect(Collectors.toList())), executorService);
        CompletableFuture<List<Integer>> listCompletableFuture1 = CompletableFuture.supplyAsync((() -> list1.stream().sorted().collect(Collectors.toList())), executorService);


        CompletableFuture<List<Integer>> listCompletableFuturere = listCompletableFuture.thenCombine(listCompletableFuture1, (result1, result2)-> {
            List<Integer> mergedList = new ArrayList<>();
            mergedList.addAll(result1);
            mergedList.addAll(result2);
            mergedList.sort(Integer::compareTo);
            return mergedList;
        });

        List<Integer> res = listCompletableFuturere.join();


        CompletableFuture<List<Integer>> mergedFuture = listCompletableFuture.thenCombine(
                listCompletableFuture1, (sortedList1, sortedList2) -> {
                    List<Integer> mergedList = new ArrayList<>();
                    mergedList.addAll(sortedList1);
                    mergedList.addAll(sortedList2);
                    return mergedList.stream().sorted().collect(Collectors.toList()); // Sorting merged list
                }
        );


    }

    static {
        String s = "bbsjndbfjbskjf hbfsdbfj";
        String str = s.chars().filter(c-> Character.isAlphabetic(c)).mapToObj(i-> String.valueOf((char)i)).collect(Collectors.joining());
    }
}
