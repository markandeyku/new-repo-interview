package dsa;

import java.util.List;
import java.util.PriorityQueue;

public class Tree {
}

class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;

    public TreeNode(int val) {
        this.val = val;
    }
}

class Test{
    int ans = 0;
    int maxPathSum(TreeNode root) {
        if(root == null) return 0;

        int leftSum = maxPathSum(root.left);
        int rightSum = maxPathSum(root.right);

        int curr = leftSum + rightSum + root.val;
        int maxi = Math.max(curr, Math.max(leftSum, rightSum)+root.val);
        ans = Math.max(ans, Math.max(maxi, root.val));
        return Math.max(maxi, root.val);
    }


}
class Pair{
    int v;
    int weight;

    public Pair(int v, int weight) {
        this.v = v;
        this.weight = weight;
    }
}

class Testdasa{


    void minimumDistance(int v, List<Pair> [] adj){
        // create a visited array
        boolean[] vis = new boolean[v];
        int[] parent = new int[v];
        int[] dis = new int[v];
        PriorityQueue<Pair> pq = new PriorityQueue<>((p1,p2)-> p1.weight - p2.weight);
        for (int i = 0; i<v;i++){
            parent[i] = -1;
            dis[i] = (int)1e9;
        }
        pq.add(new Pair(0,0));
        dis[0] = 0;

        while(!pq.isEmpty()){

            var node = pq.poll();

            int u = node.v;

            if(vis[u]) continue;
            vis[u] = true;
            for (Pair curr : adj[u]){

                int newWeight = curr.weight;
                int newV = curr.v;

                if(!vis[newV]){
                    if (newWeight < dis[newV]){
                        dis[newV] = newWeight;
                        parent[newV] = u;
                        pq.add(new Pair(newV, newWeight));
                    }
                }

            }
        }

        int  ans = 0;

        for(int i= 0 ; i<v;i++){
            if(dis[i] !=  (int)1e9){
                ans += dis[i];
            }
        }
        System.out.println(ans);

        // print path from parent



    }



}
