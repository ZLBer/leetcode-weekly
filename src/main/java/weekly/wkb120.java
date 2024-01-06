package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class wkb120 {

    /*public int incremovableSubarrayCount(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {

                boolean flag = true;
                for (int k = 1; k < i; k++) {
                    if (nums[k] <= nums[k - 1]) {
                        flag = false;
                        break;
                    }
                }

                if (i - 1 >= 0 && j + 1 < nums.length && nums[i - 1] >= nums[j + 1]) flag = false;

                for (int k = j + 2; k < nums.length; k++) {
                    if (nums[k] <= nums[k - 1]) {
                        flag = false;
                        break;
                    }
                }
                System.out.println(flag + " " + i + " " + j);
                if (flag) ans++;
            }
        }
        return ans;
    }*/


    //贪心  找最小的ak
    public long largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        long sum = 0;
        long ans = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            sum += nums[i];

            int ak = nums[i + 1];
            if (sum > ak) {
                ans = Math.max(sum + ak, ans);
            }
        }
        return ans == 0 ? -1 : ans;
    }


   /* public long incremovableSubarrayCount(int[] nums) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(nums[nums.length - 1], nums.length - 1);
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] >= nums[i + 1]) {
                break;
            }
            treeMap.put(nums[i], i);
        }
        long ans = 0;
        //不保留前缀
        if (treeMap.size() == nums.length) {
            ans = nums.length;
        } else {
            ans += treeMap.size() + 1;
        }

        //保留 0-i
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > 0 && nums[i] <= nums[i - 1]) break;
            Map.Entry<Integer, Integer> entry = treeMap.higherEntry(nums[i]);
            if (entry == null) {
                ans += 1;
            } else {
                //相邻
                if (entry.getValue() != i + 1) {
                    ans += nums.length - entry.getValue() + 1;
                    //不相邻
                } else {
                    ans += nums.length - entry.getValue();
                }
            }
        }

        return ans;
    }*/



    // 双指针 前后缀单调性
    public long incremovableSubarrayCount(int[] a) {
        int n = a.length;
        int i = 0;
        while (i < n - 1 && a[i] < a[i + 1]) {
            i++;
        }
        if (i == n - 1) { // 每个非空子数组都可以移除
            return (long) n * (n + 1) / 2;
        }

        long ans = i + 2;
        for (int j = n - 1; j == n - 1 || a[j] < a[j + 1]; j--) {
            while (i >= 0 && a[i] >= a[j]) {
                i--;
            }
            ans += i + 2;
        }
        return ans;
    }

//    public long[] placedCoins(int[][] edges, int[] cost) {
//        Map<Integer, List<Integer>> map = new HashMap<>();
//        for (int i = 0; i < edges.length; i++) {
//            if (!map.containsKey(edges[i][0])) map.put(edges[i][0], new ArrayList<>());
//            if (!map.containsKey(edges[i][1])) map.put(edges[i][1], new ArrayList<>());
//            map.get(edges[i][0]).add(edges[i][1]);
//            map.get(edges[i][1]).add(edges[i][0]);
//        }
//        res = new long[cost.length];
//        dfs(0, -1, map, cost);
//        return res;
//    }
//
//    long[] res;
//
//    long[] dfs(int cur, int parent, Map<Integer, List<Integer>> map, int[] cost) {
//
//        long[] c = new long[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
//        c = help(c, new long[]{cost[cur]});
//
//        for (Integer child : map.get(cur)) {
//            if (child == parent) continue;
//            c = help(dfs(child, cur, map, cost), c);
//        }
//        long val = 1;
//        if (map.get(cur).size() >= 3 || (cur == 0 && map.get(cur).size() >= 2)) {
//            val = compute(c);
//        }
//        res[cur] = val;
//        return c;
//    }
//
//    long compute(long[] max) {
//        // 最小的和第二小的
//        long min1 = max[0], min2 = max[1];
//        // 最大的、第二大的和第三大的
//        long max1 = max[2], max2 = max[3], max3 = max[4];
//        return Math.max(0, Math.max(min1 * min2 * max1, max1 * max2 * max3));
//    }
//
//
//    long[] help(long[] max, long[] add) {
//        // 最小的和第二小的
//        long min1 = max[0], min2 = max[1];
//        // 最大的、第二大的和第三大的
//        long max1 = max[2], max2 = max[3], max3 = max[4];
//        for (long x : add) {
//            if (x==Integer.MAX_VALUE||x==Integer.MIN_VALUE) continue;
//            if (x < min1) {
//                min2 = min1;
//                min1 = x;
//            } else if (x < min2) {
//                min2 = x;
//            }
//
//            if (x > max1) {
//                max3 = max2;
//                max2 = max1;
//                max1 = x;
//            } else if (x > max2) {
//                max3 = max2;
//                max2 = x;
//            } else if (x > max3) {
//                max3 = x;
//            }
//        }
//        return new long[]{min1, min2, max1, max2, max3};
//    }


/*    public long[] placedCoins(int[][] edges, int[] cost) {
        int n = cost.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        long[] ans = new long[n];
        dfs(0, -1, cost, g, ans);
        return ans;
    }

    private List<Integer> dfs(int x, int fa, int[] cost, List<Integer>[] g, long[] ans) {
        List<Integer> a = new ArrayList<>();
        a.add(cost[x]);
        for (int y : g[x]) {
            if (y != fa) {
                // 把子树的都添加进来
                a.addAll(dfs(y, x, cost, g, ans));
            }
        }
        Collections.sort(a);
        int m = a.size();
        if (m < 3) {
            ans[x] = 1;
        } else {
            ans[x] = Math.max((long) a.get(m - 3) * a.get(m - 2) * a.get(m - 1),
                    Math.max((long) a.get(0) * a.get(1) * a.get(m - 1), 0));
        }
        if (m > 5) {
            a = List.of(a.get(0), a.get(1), a.get(m - 3), a.get(m - 2), a.get(m - 1));
        }
        return a;
    }*/


    public static void main(String[] args) {

    }
}
