package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class wk359 {
    //模拟
    public boolean isAcronym(List<String> words, String s) {
        if (words.size() != s.length()) return false;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).charAt(0) != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    //贪心
    public int minimumSum(int n, int k) {
        Set<Integer> set = new HashSet<>();

        int num = 1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            while (set.contains(k - num)) {
                num++;
            }
            set.add(num);
            sum += num;
            num++;
        }

        return sum;
    }


    // n*logn
    // 堆
/*    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        Collections.sort(offers, (a, b) -> a.get(1) - b.get(1));

        int ans = 0;
        for (List<Integer> offer : offers) {
            int start = offer.get(0);
            int end = offer.get(1);
            int gold = offer.get(2);
            Map.Entry<Integer, Integer> pre = treeMap.floorEntry(start - 1);
            Map.Entry<Integer, Integer> entry = treeMap.floorEntry(end);
            int val = Math.max((pre == null ? 0 : pre.getValue()) + gold, entry == null ? 0 : entry.getValue());
            treeMap.put(end, val);
            ans = Math.max(ans, val);
        }

        return ans;
    }*/

    //分组+dp
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        ArrayList<int[]>[] groups = new ArrayList[n];
        Arrays.setAll(groups, e -> new ArrayList<>());
        for (List<Integer> offer : offers) {
            groups[offer.get(1)].add(new int[]{offer.get(0), offer.get(2)});
        }
        int[] dp = new int[n + 1];

        for (int end = 0; end <= n; end++) {
            dp[end + 1] = dp[end];
            for (int[] ints : groups[end]) {
                dp[end + 1] = Math.max(dp[end + 1], dp[ints[0]] + ints[1]);
            }
        }

        return dp[n];
    }


    // n* log n
    //分组+双指针
    public int longestEqualSubarray(List<Integer> nums, int k) {
        Map<Integer, Deque<Integer>> map = new HashMap<>();

        int ans = 1;
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            if (!map.containsKey(num)) {
                map.put(num, new ArrayDeque<>());
                map.get(num).addLast(i);
            } else {
                Deque<Integer> list = map.get(num);
                list.addLast(i);
                int count = list.size();
                while (i - list.peekFirst() + 1 - count > k) {
                    list.pollFirst();
                    count--;
                }
                ans = Math.max(ans, list.size());
            }
        }
        return ans;
    }


}
