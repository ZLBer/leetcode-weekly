package weekly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class wk393 {

    // 枚举
    public String findLatestTime(String s) {
        char[] chars = s.toCharArray();
        if (chars[0] == '?' && chars[1] == '?') {
            chars[0] = '1';
            chars[1] = '1';
        } else if (chars[0] == '?') {
            if (chars[1] > '1') {
                chars[0] = '0';
            } else {
                chars[0] = '1';
            }
        } else if (chars[1] == '?') {
            if (chars[0] == '1') {
                chars[1] = '1';
            } else {
                chars[1] = '9';
            }
        }

        if (chars[3] == '?') {
            chars[3] = '5';
        }
        if (chars[4] == '?') {
            chars[4] = '9';
        }
        return new String(chars);
    }

    // 遍历
    public int maximumPrimeDifference(int[] nums) {
        int left = -1;
        int right = -1;
        for (int i = 0; i < nums.length; i++) {
            if (isPrime(nums[i])) {
                if (left == -1) {
                    left = i;
                }
                right = i;
            }
        }
        return right - left;
    }

    public boolean isPrime(int number) {
        if (number == 1) return false;
        int count = 0;
        for (int i = 2; i < number; i++) {
            if ((number % i) == 0) {
                count++;
            }
        }
        return count == 0;
    }


    // 超时
   /* public long findKthSmallest(int[] coins, int k) {
        Set<Long> set = new HashSet<>();
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        for (int i = 0; i < coins.length; i++) {
            priorityQueue.add(new long[]{coins[i], i});
        }


        long ans = -1;
        for (int i = 0; i < k; i++) {

            while (true) {
                long[] poll = priorityQueue.poll();
                if (set.contains(poll[0])) {
                    int index = (int) poll[1];
                    priorityQueue.add(new long[]{poll[0] + coins[index], index});
                    continue;
                }
                int index = (int) poll[1];
                priorityQueue.add(new long[]{poll[0] + coins[index], index});
                ans = poll[0];
                set.add(poll[0]);
                break;
            }
        }
        return ans;
    }

*/


    // 二分  容斥+枚举子集
    public long findKthSmallest(int[] coins, int k) {
        int mn = Integer.MAX_VALUE;
        for (int x : coins) {
            mn = Math.min(mn, x);
        }
        long left = k - 1, right = (long) mn * k;
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (check(mid, coins, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(long m, int[] coins, int k) {
        long cnt = 0;
        next:
        for (int i = 1; i < (1 << coins.length); i++) { // 枚举所有非空子集
            long lcmRes = 1; // 计算子集 LCM
            for (int j = 0; j < coins.length; j++) {
                if ((i >> j & 1) == 1) {
                    lcmRes = lcm(lcmRes, coins[j]);
                    if (lcmRes > m) { // 太大了
                        continue next;
                    }
                }
            }
            cnt += Integer.bitCount(i) % 2 == 1 ? m / lcmRes : -m / lcmRes;
        }
        return cnt >= k;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }



    // 动态规划
    public int minimumValueSum(int[] nums, int[] andValues) {
        Map<Long, Integer> memo = new HashMap<>();
        int ans = dfs(0, 0, -1, nums, andValues, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int j, int and, int[] nums, int[] andValues, Map<Long, Integer> memo) {
        int n = nums.length;
        int m = andValues.length;
        if (m - j > n - i) { // 剩余元素不足
            return Integer.MAX_VALUE / 2;
        }
        if (j == m) { // 分了 m 段
            return i == n ? 0 : Integer.MAX_VALUE / 2;
        }
        and &= nums[i];
        if (and < andValues[j]) { // 剪枝：无法等于 andValues[j]
            return Integer.MAX_VALUE / 2;
        }
        long mask = (long) i << 36 | (long) j << 32 | and; // 三个状态压缩成一个 long
        if (memo.containsKey(mask)) {
            return memo.get(mask);
        }
        int res = dfs(i + 1, j, and, nums, andValues, memo); // 不划分
        if (and == andValues[j]) { // 划分，nums[i] 是这一段的最后一个数
            res = Math.min(res, dfs(i + 1, j + 1, -1, nums, andValues, memo) + nums[i]);
        }
        memo.put(mask, res);
        return res;
    }


}
