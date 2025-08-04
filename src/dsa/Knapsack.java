package dsa;

public class Knapsack {

    public int knapsack(int w, int[] weight, int[] val, int index){
        if(index == val.length || w == 0 )  return 0;
        if(weight[index] > w) return knapsack(w, weight, val, index-1);// code is done , now we can memoize using map  and return the  value

        // time complexity will be w*n   and space complexity will be w*n

        // extra recursion stack will be utilised, so it will be little bit slow as compared to the top down approach
        return Math.max(knapsack(w-weight[index], weight, val, index-1)+val[index], knapsack(w,weight,val, index-1));
    }
}
