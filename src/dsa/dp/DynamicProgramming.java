package dsa.dp;

import java.util.Arrays;
import java.util.TreeMap;

public class DynamicProgramming {
    public static void main(String[] args) {
        DynamicProgramming d = new DynamicProgramming();
        int[] arr = {3, 34, 4, 12, 5, 2};
        int sum = 9;
        String s = "surfdgdfgdfg".substring(0,"surfdgdfgdfg".length());
        System.out.println(s);

        d.isSubsetSum(arr, sum);
        d.treeSetApplication();
    }
    int[][] dp;
    boolean isSubsetSumRec(int[] A, int n, int target){
        if(target == 0) return true;
        if(n == 0 || target < 0) return false;
        if(dp[n-1][target] != -1) return dp[n-1][target] == 1;
        boolean take = false;
        boolean notake = false;

        if(target >= A[n-1]){
            take = isSubsetSumRec(A,n-1, target-A[n-1]);
        }
        notake = isSubsetSumRec(A, n-1, target);
        dp[n-1][target] = (take | notake) ? 1: 0;
        return take | notake;
    }
     void  isSubsetSum(int[] arr, int target) {
         dp = new int[arr.length][target+1];
         for(int i = 0; i<arr.length; i++) Arrays.fill(dp[i], -1);
         System.out.println(isSubsetSumRec(arr, arr.length, target));
    }


    //buttom up appraoch

    boolean find(int[] A, int target){

        int n = A.length;


        boolean[][] dp = new boolean[n+1][target+1];
        //for target == 0--> we have to return true;

        for(int i = 0; i<n;i++) dp[i][0] = true;
        for (int i = 1;i<=n;i++){
            for(int j = 1; j<=target; j++){

                if(j >= A[n-1]){
                    dp[i][j] = dp[i-1][j-A[i-1]] || dp[i-1][j];
                }
                else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][target];
    }

    public void treeSetApplication(){

        TreeMap<Integer, Integer> treeMap = new TreeMap<>((a,b)-> b-a); // element will be in descending order
        treeMap.put(123,90);
        treeMap.put(5,100);
        for (var v : treeMap.values()){
            System.out.println(v);
        }
    }
}
