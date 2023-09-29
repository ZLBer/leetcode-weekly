package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class wk364 {

    //贪心
    public String maximumOddBinaryNumber(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                count++;
            }
        }
        char[] ans = new char[s.length()];

        count--;
        for (int i = 0; i < ans.length; i++) {
            if (count > 0) {
                count--;
                ans[i] = '1';
            } else {
                ans[i] = '0';
            }
        }
        ans[ans.length - 1] = '1';
        return new String(ans);
    }


  /*  public long maximumSumOfHeights(List<Integer> maxHeights) {
        long res = 0;
        for (int i = 0; i < maxHeights.size(); i++) {
            long ans = maxHeights.get(i);
            int pre = maxHeights.get(i);
            for (int j = i - 1; j >= 0; j--) {
                int cur = Math.min(pre, maxHeights.get(j));
                ans += cur;
                pre = cur;
            }

            pre = maxHeights.get(i);
            for (int j = i + 1; j < maxHeights.size(); j++) {
                int cur = Math.min(pre, maxHeights.get(j));
                ans += cur;
                pre = cur;
            }
            res = Math.max(res, ans);
        }
        return res;
    }*/


    //单调栈
    static public long maximumSumOfHeights(List<Integer> maxHeights) {
        long[] left = help(maxHeights);

        List<Integer> reverse = new ArrayList<>();
        for (int i = maxHeights.size() - 1; i >= 0; i--) {
            reverse.add(maxHeights.get(i));
        }
        long[] right = help(reverse);

        long ans = 0;
        for (int i = 0; i < maxHeights.size(); i++) {
            ans = Math.max(ans, left[i] + right[maxHeights.size() - i]);
        }
        return ans;
    }

    static long[] help(List<Integer> maxHeights) {
        long[] left = new long[maxHeights.size() + 1];
        ArrayDeque<int[]> deque = new ArrayDeque<>();
        deque.add(new int[]{0, -1});
        for (int i = 0; i < maxHeights.size(); i++) {
            while (!deque.isEmpty() && deque.peekLast()[0] > maxHeights.get(i)) {
                deque.pollLast();
            }
            int minIndex = deque.peekLast()[1];
            left[i + 1] = left[minIndex + 1] + (i - minIndex) * (long) maxHeights.get(i);
            deque.add(new int[]{maxHeights.get(i), i});
        }
        return left;
    }


    public static List<Integer> getPrimes(int n) {
        boolean[] isComposite = new boolean[n + 1];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * 2; j >= 0 && j <= n; j += i) { // 标记该数的倍数为合数
                    isComposite[j] = true;
                }
            }
        }
        return primes;
    }

    static Set<Integer> set = new HashSet<>();

    static {

        for (Integer prime : getPrimes(1000001)) {
            set.add(prime);
        }
    }

    //负责dfs
    public long countPaths(int n, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) {
                map.put(edge[0], new ArrayList<>());
            }
            if (!map.containsKey(edge[1])) {
                map.put(edge[1], new ArrayList<>());
            }
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        dfs(1, -1, map);
        return ans;
    }

    long ans = 0;

    long[] dfs(int cur, int parent, Map<Integer, List<Integer>> map) {

        boolean isPrime = set.contains(cur);
        long[] counts = new long[2];

        if (!isPrime) {
            counts[0] = 1;
        } else {
            counts[1] = 1;
        }

        for (Integer child : map.getOrDefault(cur, new ArrayList<>())) {
            if (child == parent) continue;
            long[] ints = dfs(child, cur, map);
           ans+=ints[0]*counts[1]+ints[1]*counts[0];
            if (!isPrime) {
                counts[0]+=ints[0];
                counts[1]+=ints[1];
            } else {
                counts[1]+=ints[0];
            }
        }
        return counts;
    }

    public static void main(String[] args) {
        wk364 w = new wk364();
        w.countPaths(5, new int[][]{
                {1, 2}, {1, 3}, {2, 4}, {2, 5}
        });
    }
}
