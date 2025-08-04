package dsa.tree;

public class DistributeCoinsBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(0);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(1);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(5);
        System.out.println(new DistributeCoinsBinaryTree().distributeCoins(root));
    }

    /**
     * 💡 Explanation:
     * node.val - 1: surplus (or deficit) coins at this node.
     * left + right: coins coming from left and right children.
     * moves += abs(left) + abs(right): total coins moved from child to this node.
     * We return the net balance of coins to the parent, which will then absorb/redistribute them.
     */
    int moves = 0;
    public int distributeCoins(TreeNode root) {
        dfs(root);
        return moves;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int left = dfs(node.left);    // coins surplus/deficit from left
        int right = dfs(node.right);  // coins surplus/deficit from right

        moves += Math.abs(left) + Math.abs(right);

        return node.val - 1 + left + right;
    }
}

