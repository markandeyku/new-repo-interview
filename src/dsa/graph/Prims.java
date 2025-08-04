package dsa.graph;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class Node{

    int u;
    int weight;

    public Node(int u, int weight) {
        this.u = u;
        this.weight = weight;
    }
}

public class Prims {

    int prims(List<Node>[] list, int v, int source){

        int edges = list.length;

        // 0 --> [{1,2}{2,2}{3,7}

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));

        // I am considering the o weight from the source

        pq.add(new Node(source, 0));
        boolean[] vis = new boolean[v];
        int ans = 0;
        int[] parent = new int[v];

        while(!pq.isEmpty()){

            Node curr = pq.poll();
            int u = curr.u;

            if(vis[u]) continue;
            vis[u] = true;

            ans += curr.weight;

            for (Node node : list[u]){

                if(!vis[node.u] && curr.weight  > node.weight){ //If I will not compare then also it will be right because I am using the priority queue

                    parent[node.u] = u;
                    pq.add(node);
                }
            }
        }


        for (int i = 0; i<v;i++){
            System.out.println(i + " --> " + parent[i]);
        }


        // I have to find minimum cost to connect all edges with minimum cost

        return ans;
    }
}



