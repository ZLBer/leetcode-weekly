package weekly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.*;

public class wkb102 {


    //模拟
    public int[] findColumnWidth(int[][] grid) {
        int[] ans = new int[grid[0].length];
        for (int i = 0; i < grid[0].length; i++) {
            int max = 0;
            for (int j = 0; j < grid.length; j++) {
                max = Math.max(max, Integer.toString(grid[j][i]).length());

            }
            ans[i] = max;
        }
        return ans;
    }

    //模拟
    public long[] findPrefixScore(int[] nums) {
        long[] ans = new long[nums.length];
        int max=nums[0];
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            max=Math.max(nums[i],max );
            sum += nums[i] + max;
            ans[i] = sum;
        }
        return ans;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //bfs+dfs
    public TreeNode replaceValueInTree(TreeNode root) {
        Map<Integer, Integer> sums = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int step = 0;
        //求每一层的和
        while (!queue.isEmpty()) {
            int size = queue.size();
            int sum = 0;
            while (size-- > 0) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
                sum += poll.val;
            }
            sums.put(step++, sum);
        }
        //每一层的和减去同父的孩子就是堂兄弟的和
        dfs(root, sums, 0);
        root.val = 0;
        return root;
    }

    void dfs(TreeNode node, Map<Integer, Integer> sums, int step) {
        if (node == null) return;
        int sum = sums.getOrDefault(step + 1, 0);

        dfs(node.left, sums, step + 1);
        dfs(node.right, sums, step + 1);
        int childSum = 0;
        if (node.left != null) {
            childSum += node.left.val;
        }
        if (node.right != null) {
            childSum += node.right.val;
        }
        if (node.left != null) {
            node.left.val = sum - childSum;
        }
        if (node.right != null) {
            node.right.val = sum - childSum;
        }
    }

   /* class Graph {

        public Graph(int n, int[][] edges) {

        }

        public void addEdge(int[] edge) {

        }

        public int shortestPath(int node1, int node2) {

        }
    }*/



    //直接Dijkstra
    class Graph {
        int n;
        Map<Integer, List<int[]>> edges;

        public Graph(int n, int[][] edges) {
            this.n = n;
            this.edges = new HashMap<>();
            for (int[] edge : edges) {
                int from = edge[0], to = edge[1], cost = edge[2];
                if (!this.edges.containsKey(from)) {
                    this.edges.put(from, new ArrayList<>());
                }
                this.edges.get(from).add(new int[]{to, cost});
            }
        }

        public void addEdge(int[] edge) {
            int from = edge[0], to = edge[1], cost = edge[2];
            if (!this.edges.containsKey(from)) {
                this.edges.put(from, new ArrayList<>());
            }
            this.edges.get(from).add(new int[]{to, cost});
        }

        public int shortestPath(int node1, int node2) {
            int[] dist = new int[n];
            Arrays.fill(dist, (int)1e9+7);
            dist[node1] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(node1);
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int[] edge : edges.getOrDefault(cur, new ArrayList<>())) {
                    int next = edge[0], cost = edge[1];
                    if (dist[cur] + cost < dist[next]) {
                        dist[next] = dist[cur] + cost;
                        queue.offer(next);
                    }
                }
            }
            int ans=dist[node2]==(int)1e9+7?-1:dist[node2];
            return ans;
        }
    }
}
