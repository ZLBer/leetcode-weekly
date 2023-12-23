package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk375 {

    //遍历
    public int countTestedDevices(int[] batteryPercentages) {

        int ans = 0;
        int del = 0;
        for (int i = 0; i < batteryPercentages.length; i++) {
            int batteryPercentage = batteryPercentages[i];
            batteryPercentage -= del;
            if (batteryPercentage > 0) {
                ans++;
                del++;
            }
        }
        return ans;
    }


    //快速幂
    public List<Integer> getGoodIndices(int[][] variables, int target) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < variables.length; i++) {
            int[] variable = variables[i];
            if (fastPower(fastPower(variable[0], variable[1], 10), variable[2], variable[3]) == (long) target) {
                ans.add(i);
            }
        }
        return ans;
    }

    public static long fastPower(long a, long b, int mod) {
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans *= a;
                ans %= mod;
            }
            a *= a;
            a %= mod;
            b >>= 1;
        }
        return ans;
    }



    //滑动窗口 维持k次，计算子数组个数
    static public long countSubarrays(int[] nums, int k) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(num, max);
        }

        int left = 0;
        int count = 0;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == max) {
                count++;
            }

            while (count > k || nums[left] != max) {
                if (nums[left] == max) {
                    count--;
                }
                left++;
            }

            if (count == k) {
                ans += (left + 1);
            }

        }
        return ans;
    }



    // 区间合并
    public int numberOfGoodPartitions(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], i);
            }
        }


        int cur = 0;
        int ans = 0;
        while (true) {
            int right = map.get(nums[cur]);
            for (int i = cur; i < nums.length && i < right; i++) {
                right = Math.max(right, map.get(nums[i]));
            }
            ans++;
            cur = right + 1;
            if (cur >= nums.length) break;
        }
        return (int)fastPower(2, ans);
    }

    public static long fastPower(long a, long b) {
        long ans = 1;
        int mod = (int) 1e9 + 7;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans *= a;
                ans %= mod;
            }
            a *= a;
            a %= mod;
            b >>= 1;
        }
        return ans;
    }

}
