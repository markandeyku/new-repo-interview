package dsa.graph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Cycle {

    public static void main(String[] args) {

        try{
            int V = 5;
        }
        catch (Exception e){

        }
        finally {
            System.out.println("vmlmfvlkd");
        }

    }


    // cycle found in the undirected graph

    public static  boolean isCycle(int start, List<Integer>[] adj, boolean [] vis, int parent){
        vis[start] = true;
        for(int curr : adj[start]){
            if(!vis[curr]){
                if(isCycle(curr, adj, vis, start)) return true;
            } else if (parent != curr) {
                return  true;
            }
        }
        return false;
    }

    public static boolean isCycleInDirectedGraph(int start, List<Integer>[] adj, boolean [] vis, boolean[] recStack){
        if(recStack[start]) return true;
        recStack[start] = true;
        vis[start] = true;
        for(int curr : adj[start]){
            if(!vis[curr]){
                if(isCycleInDirectedGraph(curr, adj, vis, recStack)) return true;
            }
        }
        return recStack[start] = false;
    }

    public void queueTest(){

        Deque<Integer> q = new LinkedList<>();
    }
}
