package weekly;

import sun.security.krb5.internal.APRep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk341 {
    //模拟
    public int[] rowAndMaximumOnes(int[][] mat) {
        int ans = 0;
        int count = 0;
        for (int i = 0; i < mat.length; i++) {
            int c = 0;
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 1) {
                    c++;
                }
            }
            if (c > count) {
                count = c;
                ans = i;
            }
        }
        return new int[]{ans, count};
    }


    //枚举
    public int maxDivScore(int[] nums, int[] divisors) {
        int[] score = new int[divisors.length];
        for (int i = 0; i < divisors.length; i++) {
            int count = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] % divisors[i] == 0) {
                    count++;
                }
            }
            score[i] = count;
        }

        int max = 0;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < score.length; i++) {
            if (score[i] > max) {
                ans = divisors[i];
                max = score[i];
            } else if (score[i] == max && divisors[i] < ans) {
                ans = divisors[i];
            }
        }
        return ans;
    }


    //贪心，尽量用之前没有用完的字母
    static public int addMinimum(String word) {
        char pre = 'c';
        int ans = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            //前面结束
            if (pre == 'c') {
                ans += c - 'a';
                pre = c;
            } else {
                if (c > pre) {
                    ans += c - pre - 1;
                    pre = c;
                } else {
                    //表示前面的需要结束，重新开始
                    ans += 'c' - pre;
                    pre = 'c';
                    i--;
                }
            }
        }
        ans += 'c' - pre;
        return ans;
    }

    //转换成count数组然后树形dp
    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        int[] count = new int[n];

        for (int[] trip : trips) {
            dfs(map, count, trip[0], trip[1], -1);
        }

        int[] ans = dfs2(map, count, price, 0, -1);
        return Math.min(ans[0], ans[1]);
    }

    boolean dfs(Map<Integer, List<Integer>> map, int[] count, int cur, int target, int parent) {
        if (cur == target) {
            count[cur]++;
            return true;
        }

        for (Integer next : map.getOrDefault(cur, new ArrayList<>())) {
            if (next == parent) continue;
            //是路径上的点
            if (dfs(map, count, next, target, cur)) {
                count[cur]++;
                return true;
            }
        }
        return false;
    }

    int[] dfs2(Map<Integer, List<Integer>> map, int[] count, int[] price, int cur, int parent) {

        int[] ans = new int[]{price[cur] * count[cur], price[cur] * count[cur]/2};

        int childMaxSum = 0;
        int childNoSum = 0;
        for (Integer next : map.getOrDefault(cur, new ArrayList<>())) {
            if (next == parent) continue;
            int[] child = dfs2(map, count, price, next, cur);
            childMaxSum += Math.min(child[0], child[1]);
            childNoSum += child[0];
        }
        ans[0] += childMaxSum;
        ans[1] +=childNoSum;
        return ans;
    }


    public static void main(String[] args) {
        wk341 w = new wk341();
        w.minimumTotalPrice(4, new int[][]{{0, 1}, {1, 2}, {1, 3}}, new int[]{2, 2, 10, 6}, new int[][]{{0, 3}, {2, 1}, {2, 3}});
    }
}
