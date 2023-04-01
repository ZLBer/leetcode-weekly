package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class wk338 {
    //贪心
    public int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        if (numOnes >= k) {
            return k;
        } else if (numOnes + numZeros >= k) {
            return numOnes;
        } else {
            return numOnes - (k - numOnes - numZeros);
        }
    }

    //贪心  减去能减的最大质数
    public boolean primeSubOperation(int[] nums) {
        int n = 1000;
        TreeSet<Integer> treeSet = new TreeSet<>();
        boolean[] Prime = new boolean[n];
        Prime[0] = true;
        for (int i = 2; i <= n; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                Prime[i] = true;
                treeSet.add(i);
            }
        }
        for (int i = nums[0] - 1; i >= 0; i--) {
            if (Prime[i]) {
                nums[0] -= i;
                break;
            }
        }
        for (int i = 1; i < nums.length; i++) {
            boolean flag = false;
            for (int j = nums[i] - 1; j >= 0; j--) {
                if (Prime[j] && nums[i] - j > nums[i - 1]) {
                    nums[i] -= j;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }


    //前缀和+二分
    public List<Long> minOperations(int[] nums, int[] queries) {
        int[][] q = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            q[i] = new int[]{queries[i], i};
        }
        Arrays.sort(q, (a, b) -> a[0] - b[0]);
        Arrays.sort(nums);

        long[] res = new long[queries.length];


        int left = 0;
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= q[0][0]) {
                left++;
                sum += q[0][0] - nums[i];
            } else {
                sum += nums[i] - q[0][0];
            }
        }
        res[q[0][1]] = sum;

        for (int i = 1; i < q.length; i++) {
            int dis = q[i][0] - q[i - 1][0];
            int count = 0;
            while (left + count < nums.length && nums[left + count] < q[i][0]) {
                sum += (q[i][0] - nums[left + count]) - (nums[left + count] - q[i - 1][0]);
                count++;

            }

            sum += ((long) left - (nums.length - left - count)) * dis;

            res[q[i][1]] = sum;
            left += count;
        }
        List<Long> ans = new ArrayList<>();
        for (long re : res) {
            ans.add(re);
        }
        return ans;
    }


    //两次拓扑排序:一次从0一直找，一次从1找两次
    public int collectTheCoins(int[] coins, int[][] edges) {
        // 构建无向图
        int n = coins.length;
        int[] in = new int[coins.length];
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
            in[u]++;
            in[v]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < in.length; i++) {
            if (in[i] == 1) {
                queue.add(i);
            }
        }
        Set<Integer> remove = new HashSet<>();

        help(remove, graph, in, coins);

        help2(remove, graph, in, coins);

        return 2 * (Math.max(0, coins.length - remove.size() - 1));
    }

    //0遍历到底
    void help(Set<Integer> remove, Map<Integer, List<Integer>> graph, int[] in, int[] coins) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < in.length; i++) {
            if (in[i] == 1 && coins[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            remove.add(cur);
            in[cur] = 0;
            for (Integer next : graph.getOrDefault(cur, new ArrayList<>())) {
                in[next]--;
                if (coins[next] == 0 && in[next] == 1) {
                    queue.add(next);
                }
            }
        }
    }

    void help2(Set<Integer> remove, Map<Integer, List<Integer>> graph, int[] in, int[] coins) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < in.length; i++) {
            if (in[i] == 1 && coins[i] == 1) {
                queue.add(i);
            }
        }
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Integer cur = queue.poll();
                remove.add(cur);
                in[cur] = 0;
                for (Integer next : graph.getOrDefault(cur, new ArrayList<>())) {
                    in[next]--;
                    if (in[next] == 1) {
                        queue.add(next);
                    }
                }
            }
            step++;
            if (step >= 2) return;
        }
    }

    public static void main(String[] args) {
        wk338 w = new wk338();
        w.collectTheCoins(new int[]{1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0},
                new int[][]{{0, 1}, {1, 2}, {1, 3}, {2, 4}, {4, 5}, {5, 6}, {5, 7}, {4, 8}, {7, 9}, {7, 10}, {10, 11}});
    }


}
