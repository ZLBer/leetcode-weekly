package weekly;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wkb114 {


    // hash
    public int minOperations(List<Integer> nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= k; i++) {
            set.add(i);
        }
        int ans = 0;
        for (int size = nums.size() - 1; size >= 0; size--) {
            set.remove(nums.get(size));
            ans++;
            if (set.size() == 0) return ans;
        }
        return ans;
    }

    //贪心
    public int minOperations(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        int ans = 0;
        for (Integer value : map.values()) {
            if (value == 1) {
                return -1;
            }
            if (value % 3 == 0) {
                ans += value / 3;
            } else if (value % 3 == 1) {
                ans += (value - 1) / 3 + 1;
            } else {
                ans += value / 3 + 1;
            }
        }
        return ans;
    }


    // &运算性质
    static public int maxSubarrays(int[] nums) {
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum &= nums[i];
        }
        int ans = 0;

        //&和为0的时候特殊计算
        if (sum == 0) {
            int pre = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (pre == sum) {
                    ans++;
                    pre = nums[i];
                } else {
                    pre &= nums[i];
                }
            }
            if (pre == sum) {
                ans++;
            }
        } else {
            ans = 1;
        }


        return ans;
    }


    //贪心
    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
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
        return (int) dfs(-1, 0, map, values, k)[0];
    }

    long[] dfs(int parent, int cur, Map<Integer, List<Integer>> map, int[] values, int k) {

        long val = values[cur];
        long ans = 0;
        for (Integer child : map.getOrDefault(cur, new ArrayList<>())) {
            if (child == parent) continue;
            long[] ints = dfs(cur, child, map, values, k);
            ans += ints[0];
            val += ints[1];
        }
        if (val % k == 0) {
            ans += 1;
        }

        return new long[]{ans, val};
    }
}
