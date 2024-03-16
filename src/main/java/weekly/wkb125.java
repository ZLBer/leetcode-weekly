package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class wkb125 {
    //遍历
    public int minOperations(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < k) {
                ans++;
            }
        }
        return ans;
    }


    //小根堆
   /* public int minOperations(int[] nums, int k) {
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        for (int num : nums) {
            priorityQueue.add((long)num);
        }
        int ans = 0;
        while (priorityQueue.size() >= 2) {
            Long x = priorityQueue.poll();
            Long y = priorityQueue.poll();
            if (x >= k && y >= k) {
                return ans;
            }

            priorityQueue.add(x * 2 + y);
            ans++;

        }
        return ans;
    }*/


    //乘法原理
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(new int[]{edge[1], edge[2]});
            map.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        int n = edges.length + 1;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int cur = 0;
            for (int[] next : map.get(i)) {
                int d = dfs(i, next[0], next[1], signalSpeed, map);
                res[i] += d * cur;
                cur += d;
            }
        }
        return res;
    }


    int dfs(int pre, int cur, int weight, int signal, Map<Integer, List<int[]>> map) {
        int ans = 0;
        if (weight % signal == 0) ans++;
        for (int[] ints : map.get(cur)) {
            if (ints[0] == pre) continue;
            ans += dfs(cur, ints[0], weight + ints[1], signal, map);
        }
        return ans;
    }


    //贪心
 /*   static public long maximumValueSum(int[] nums, int k, int[][] edges) {

        long sum = 0;
        int[] up = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            up[i] = nums[i] - (nums[i] ^ k);
        }
        Arrays.sort(up);

        for (int i = 0; i < up.length; i += 2) {
            if (i + 1 < up.length) {
                if (up[i] + up[i + 1] <= 0) {
                    sum += -(up[i] + up[i + 1]);
                }
            }
        }
        return sum;
    }*/


    // dp
    public long maximumValueSum(int[] nums, int k, int[][] edges) {
        long f0 = 0, f1 = Long.MIN_VALUE;
        for (int x : nums) {
            long t = Math.max(f1 + x, f0 + (x ^ k));
            f0 = Math.max(f0 + x, f1 + (x ^ k));
            f1 = t;
        }
        return f0;
    }


    public static void main(String[] args) {
//        countPairsOfConnectableServers(new int[][]{
//                {0, 1, 1}, {1, 2, 5}, {2, 3, 13}, {3, 4, 9}, {4, 5, 2}
//        }, 1);

    }
}
