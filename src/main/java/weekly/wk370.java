package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class wk370 {

    //求入度为0的点
  /*  public int findChampion(int[][] grid) {
       int []in=new int[grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                in[j]+=grid[i][j];
            }
        }
        for (int i = 0; i < in.length; i++) {
            if(in[i]==0) return i;
        }
        return -1;
    }*/

    //求入度为0的点
    public int findChampion(int n, int[][] edges) {
        int[] in = new int[n];
        for (int i = 0; i < edges.length; i++) {
            in[edges[i][1]]++;
        }
        int ans = 0;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == 0) {
                ans++;
            }
        }
        if (ans == 0 || ans > 1) return -1;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == 0) {
                return i;
            }
        }
        return -1;
    }


    //dfs  逆向思维  要么选根节点 要么继续往下走
    public long maximumScoreAfterOperations(int[][] edges, int[] values) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum - dfs(0, -1, map, values);
    }

    long dfs(int cur, int parent, Map<Integer, List<Integer>> map, int[] values) {

        long val = values[cur];
        long childMinVal = 0;

        for (Integer child : map.get(cur)) {
            if (child == parent) continue;
            childMinVal += dfs(child, cur, map, values);
        }
        //没有孩子
        if (childMinVal == 0) {
            childMinVal = Integer.MAX_VALUE;
        }

        return Math.min(val, childMinVal);
    }

    // 化简+线段树or树状数组
    public long maxBalancedSubsequenceSum(int[] nums) {
        int n = nums.length;
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = nums[i] - i;
        }
        Arrays.sort(b);

        BIT t = new BIT(b.length + 1);
        for (int i = 0; i < n; i++) {
            // j 为 nums[i]-i 离散化后的值（从 1 开始）
            int j = Arrays.binarySearch(b, nums[i] - i) + 1;
            long f = Math.max(t.preMax(j), 0) + nums[i];
            t.update(j, f);
        }
        return t.preMax(b.length);
    }

    // 树状数组模板（维护前缀最大值）
    class BIT {
        private long[] tree;

        public BIT(int n) {
            tree = new long[n];
            Arrays.fill(tree, Long.MIN_VALUE);
        }

        public void update(int i, long val) {
            while (i < tree.length) {
                tree[i] = Math.max(tree[i], val);
                i += i & -i;
            }
        }

        public long preMax(int i) {
            long res = Long.MIN_VALUE;
            while (i > 0) {
                res = Math.max(res, tree[i]);
                i &= i - 1;
            }
            return res;
        }

    }
}
