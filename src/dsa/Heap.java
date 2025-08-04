package dsa;

import java.util.Scanner;

public class Heap {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        minHeap(arr, n);
        for (var x : arr) System.out.print(x + " ");

        // built the max heap
        for (int i = n/2-1; i >=0 ; i--) {
            heapify(arr, i, n);
        }

        // implementing heap sort

        for (int i = n-1; i >= 0; i--) {
            swapData(arr, 0, i);
            heapify(arr, 0, i);
        }
       for (var x : arr) System.out.println(x);
    }

    // creating min heap logic

    private static void  minHeap(int[] arr, int n) {
        for (int i = n/2-1; i >=0 ; i--) {
            minHeapify(arr, i, n);
        }

        // implementing heap sort

        System.out.println("min heap data  -- >  ");

        for (int i = n-1; i >= 0; i--) {
            swapData(arr, 0, i);
minHeapify(arr, 0, i);
            System.out.println(arr[i]);
        }

    }

    private static void minHeapify(int[] arr, int i, int n) {
        int left = 2*i+1;
        int right = 2*i+2;
       int smallest = i;

       if(left < n && arr[left] < arr[smallest]){
           smallest = left;
       }

       if(right < n && arr[right] < arr[smallest]){
           smallest = right;
       }

       if(smallest != i){
           swapData(arr, i, smallest);
           minHeapify(arr, smallest, n);
       }
    }

    private static void heapify(int[] arr, int i, int n) {

        int left = 2*i+1;
        int right = 2*i+2;
       int largest = i;

       if(left < n && arr[left] > arr[largest]){
           largest = left;
       }

       if(right < n && arr[right] > arr[largest]){
           largest = right;
       }

       if(largest != i){
           swapData(arr, i, largest);
           heapify(arr, largest, n);
       }

    }

    private static void swapData(int[] arr, int i, int largest) {
        int temp = arr[i];
        arr[i] = arr[largest];
        arr[largest] = temp;
    }

}
