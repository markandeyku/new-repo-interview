package dsa.graph;


import java.util.Arrays;
 import java.util.List;

class NewNode{
    int source;
    int destination;
    int w;

     public NewNode(int source, int destination, int w) {
         this.source = source;
         this.destination = destination;
         this.w = w;
     }
 }

public class BellmanFords {

    //Its time complexity will be On 3  --->  because  for each node I will go with v-1 nodes travesal and find miniu
    //O(v*E) -->   E --> v*v   ---> v^3  maximum time complexity
    // maximum no of edges can relax for a node is v-1. if more than v - 1  It means there is cycle

    // int[][] edges = new int[][] {
    //            {1, 3, 2},
    //            {4, 3, -1},
    //            {2, 4, 1},
    //            {1, 2, 1},
    //            {0, 1, 5}
    //        };


    // if  undirected graph given then convert it into directed similar like above structure to solve the problem


    int findShortestPath(List<NewNode> [] list, int source, int destination, int v){


        int[] dis = new int[v];
        Arrays.fill(dis, (int)1e8);
        dis[source]  = 0;

        for(int i = 0; i<v;i++){

            for (NewNode curr : list[i]){

                if(dis[curr.source] != (int)1e8 && dis[curr.destination] > dis[curr.source] + curr.w){
                    dis[curr.destination] = dis[curr.source] + curr.w;

                    if(i == v-1)  return -1; // found negative cycle

                }
            }
        }

        return 0;

    }

}

//what is the principle? and why are we using bellman fords algorithm - for negative edges


