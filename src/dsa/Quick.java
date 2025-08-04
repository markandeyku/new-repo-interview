package dsa;

import java.util.Scanner;

public class Quick {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        quicksort(arr, 0, n-1);
        for (var x : arr) System.out.print(x + " ");
    }

    private static void quicksort(int[] arr, int i, int r) {
        if(i >= r) return;
        int partition = partition(arr, i, r);
        quicksort(arr, i, partition-1);
        quicksort(arr, partition+1, r);
    }

    private static int partition(int[] arr, int i, int r) {
        int pivot = arr[r];
        int j = i;
        for (int k = i; k < r; k++) {
            if (arr[k] < pivot) {
                swap(arr, j, k);
                j++;
            }
        }
        swap(arr, j, r);
        return j;
    }

    private static void swap(int[] arr, int j, int r) {
        int temp = arr[j];
        arr[j] = arr[r];
        arr[r] = temp;
    }
}
