package dsa.graph;


class DisjointSet{

    int[] rank;
    int[] parent;
    int[] size;


    public DisjointSet(int n){
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    /**
     * time complexity to find this is almost constant because of path compression
     * @param node
     * @return
     */
    int find(int node){
        if(node == parent[node]) return node;
        return parent[node] = find(parent[node]);
    }

    void unionByRank(int u, int v){

        int pu = find(u);
        int pv = find(v);

        if(pu == pv) return;

        if(rank[pu] < rank[pv]){
            parent[pu] = pv;
            rank[pv]++;
        }
        else if(rank[pu] > rank[pv]){
            parent[pv] = pu;
            rank[pu]++;
        }
        else{
            parent[pv] = pu;
            rank[pu]++;
        }
    }

    void unionBySize(int u, int v){

        int pu = find(u);
        int pv = find(v);

        if(pu == pv) return;

        if(size[pu] < size[pv]){
            parent[pu] = pv;
            size[pv] += size[pu];
        }
        else{
            parent[pv] = pu;
            size[pu] += size[pv];
        }
    }
}
public class DSU {


}
