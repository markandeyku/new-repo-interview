package dsa.graph;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkshtra {


    int findShortestPath(List<Node>[] list, int v, int source, int destination){
        int edges = list.length;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node-> node.weight));

        int[] dis = new int[v];
        for(int i = 0; i<v;i++){
            dis[i] = Integer.MAX_VALUE;
        }

        dis[source] = 0;

        pq.add(new Node(source, 0));

        while(!pq.isEmpty()){

           Node curr = pq.poll();

            if(curr.u == destination) return curr.weight;

            for (Node node : list[curr.u]){

                if(dis[node.u] > node.weight + curr.weight){
                    dis[node.u] = node.weight + curr.weight;
                    pq.add(new Node(node.u,  dis[node.u]));
                }
            }
        }

        return -1;
    }
}




// this is the algorithm that is used to find the shortest path from source to destination



