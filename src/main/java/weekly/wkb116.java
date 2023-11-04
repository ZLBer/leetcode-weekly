package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wkb116 {
    public int sumCounts(List<Integer> nums) {
        long ans = 0;
        int mod = (int) 1e9 + 7;

        for (int i = 0; i < nums.size(); i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = i; j < nums.size(); j++) {
                set.add(nums.get(j));
                ans += (set.size() * (long) set.size());
                ans %= mod;
            }
        }
        return (int) ans;
    }

    public int minChanges(String s) {
        int ans = 0;
        for (int i = 1; i < s.length(); i += 2) {
            char c = s.charAt(i);
            char p = s.charAt(i);
            if (c != p) {
                ans += 1;
            }
        }
        return ans;
    }

    static public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            for (int j = dp.length - 1; j >= 0; j--) {
                if (dp[j] != -1 && j + num <= target) {
                    dp[j + num] = Math.max(dp[j + num], dp[j] + 1);
                }
            }
        }
        return dp[target];
    }


   /* public int sumCounts(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        long ans = 0;
        int mod = (int) 1e9 + 7;
        int[] count = new int[nums.length];
        long[] count2 = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new ArrayList<>());
            }
            map.get(nums[i]).add(i);
            count[i] = map.size();
            count2[i] = (long) count[i] * count[i];
            ans += (long) count[i] * count[i];
            ans %= mod;
        }

        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = map.get(i);
            list.remove(0);
            Integer next = list.get(0);
            //后续该数字了
            int start = i;
            int end = -1;
            if (next == null) {
                end = nums.length;
            } else {
                end = list.get(0) - 1;
            }
            //start到end都-1

            //start到end的平方和 -  2*sum(start到end) -1
        }
    }*/

    private long[] sum;
    private int[] todo;

    // o=1  [l,r] 1<=l<=r<=n
    // 把 [L,R] 加一，同时返回加一之前的区间和
    private long queryAndAdd1(int o, int l, int r, int L, int R) {
        if (L <= l && r <= R) {
            long res = sum[o];
            do_(o, l, r, 1);
            return res;
        }

        int m = (l + r) / 2;
        int add = todo[o];
        if (add != 0) {
            do_(o * 2, l, m, add);
            do_(o * 2 + 1, m + 1, r, add);
            todo[o] = 0;
        }

        long res = 0;
        if (L <= m) res += queryAndAdd1(o * 2, l, m, L, R);
        if (m < R) res += queryAndAdd1(o * 2 + 1, m + 1, r, L, R);
        sum[o] = sum[o * 2] + sum[o * 2 + 1];
        return res;
    }

    private void do_(int o, int l, int r, int add) {
        sum[o] += (long) add * (r - l + 1);
        todo[o] += add;
    }


    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[n * 4];
        todo = new int[n * 4];

        long ans = 0, s = 0;
        HashMap<Integer, Integer> last = new HashMap<>();
        for (int i = 1; i <= nums.length; i++) {
            int num = nums[i - 1];
            int from = last.getOrDefault(num, 0);
            //记录当前的平方和
            s += queryAndAdd1(1, 1, n, from+1, i) * 2 + (i - from);

            //求和
            ans = (ans + s) % ((int) 1e9 + 7);
            last.put(num, i);
        }
        return (int)ans;
    }

    public static void main(String[] args) {
        lengthOfLongestSubsequence(Arrays.asList(1, 2, 3, 4, 5), 9);
    }
}
