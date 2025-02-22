package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class wk432 {

    public List<Integer> zigzagTraversal(int[][] grid) {
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < grid[0].length; j += 2) {
                    ans.add(grid[i][j]);
                }
            } else {
                for (int j = (grid[0].length % 2 == 0) ? grid[0].length - 1 : grid[0].length - 2; j >= 0; j -= 2) {
                    ans.add(grid[i][j]);
                }
            }

        }
        return ans;
    }

    static public int maximumAmount(int[][] coins) {
        int[][][] dp = new int[3][coins.length][coins[0].length];
        for (int[] ints : dp[0]) {
            Arrays.fill(ints, Integer.MIN_VALUE);
        }
        dp[0][0][0] = coins[0][0];
        for (int k = 0; k <= 2; k++) {
            for (int i = 0; i < coins.length; i++) {
                for (int j = 0; j < coins[0].length; j++) {
                    int max=Integer.MIN_VALUE;
                    // 最初始位子不讨论
                    if (i == 0 && j == 0) {
                        max=coins[0][0];
                    }
                    //k>0时初始位置可以不选
                    if (k > 0 && i == 0 && j == 0) {
                        max = Math.max(0, max);
                    }
                    //非初始位置的讨论
                    if (i - 1 >= 0) {
                        max = Math.max(dp[k][i - 1][j] + coins[i][j], max);
                        if (k - 1 >= 0) {
                            max = Math.max(dp[k - 1][i - 1][j] + Math.max(coins[i][j], 0), max);
                        }
                    }
                    if (j - 1 >= 0) {
                        max = Math.max(dp[k][i][j - 1] + coins[i][j], max);
                        if (k - 1 >= 0) {
                            max = Math.max(dp[k - 1][i][j - 1] + Math.max(coins[i][j], 0), max);
                        }
                    }
                    dp[k][i][j] = max;
                }
            }
        }
        return dp[2][coins.length - 1][coins[0].length - 1];
    }


    public int minMaxWeight(int n, int[][] edges, int threshold) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a,b)->a[1]-b[1]);
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }

        Set<Integer> set = new HashSet<>();
        set.add(0);
        for (int[] next : map.getOrDefault(0, new ArrayList<>())) {
            priorityQueue.add(next);
        }

        int ans = 0;
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            if (set.contains(poll[0])) continue;
            set.add(poll[0]);
            ans = Math.max(ans, poll[1]);
            for (int[] next : map.getOrDefault(poll[0], new ArrayList<>())) {
                if (!set.contains(next[0])) {
                    priorityQueue.add(next);
                }
            }
            if (set.size() ==n) return ans;
        }
        return -1;
    }

    public static void main(String[] args) {
        maximumAmount(new int[][]{
                {-4}
        });
    }
}
