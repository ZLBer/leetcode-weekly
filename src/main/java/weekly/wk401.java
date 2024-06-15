package weekly;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class wk401 {


    //暴力
    static public int numberOfChild(int n, int k) {
        int d = (k % (2 * n - 2));
        if (d < n) {
            return d;
        } else {
            return n - (d - n) - 2;
        }
    }


    // 前缀和
    static public int valueAfterKSeconds(int n, int k) {
        int mod = (int) 1e9 + 7;

        long[] pre = new long[n + 1];
        Arrays.fill(pre, 1);
        pre[0] = 0;
        for (int j = 0; j < k; j++) {
            for (int i = 1; i < pre.length; i++) {
                pre[i] += pre[i - 1];
                pre[i] %= mod;
            }
        }


        return (int) (pre[n] % mod);
    }


    // 0-1背包
   /* public int maxTotalReward(int[] rewardValues) {
        Arrays.sort(rewardValues);
        memo = new int[rewardValues.length][2000 * 2000 + 1];

        int ans = dfs(0, 0, rewardValues);
        return ans;
    }


    int[][] memo;

    int dfs(int x, int i, int[] rewardValues) {
        if (i >= rewardValues.length) {
            return x;
        }
        if (memo[i][x] != 0) return memo[i][x];
        int ans = 0;
        if (x < rewardValues[i]) {
            ans = Math.max(ans, dfs(x + rewardValues[i], i + 1, rewardValues));
        }
        int jump = Math.max(i + 1, lowerBound(rewardValues, x + 1));
        ans = Math.max(dfs(x, jump, rewardValues), ans);
        memo[i][x] = ans;
        return ans;
    }

    // lowerBound 返回最小的满足 nums[i] >= target 的 i
    // 如果数组为空，或者所有数都 < target，则返回 nums.length
    // 要求 nums 是非递减的，即 nums[i] <= nums[i + 1]

    // 闭区间写法
    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // 闭区间 [left, right]
        while (left <= right) { // 区间不为空
            // 循环不变量：
            // nums[left-1] < target
            // nums[right+1] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid + 1; // 范围缩小到 [mid+1, right]
            else
                right = mid - 1; // 范围缩小到 [left, mid-1]
        }
        return left; // 或者 right+1
    }*/

    public static void main(String[] args) {
        wk401 w = new wk401();
        w.maxTotalReward(new int[]{1, 1, 3, 3});
    }



    // bitset +剪枝
    public int maxTotalReward(int[] rewardValues) {
        int m = 0;
        for (int v : rewardValues) {
            m = Math.max(m, v);
        }
        for (int v : rewardValues) {
            if (v == m - 1) {
                return m * 2 - 1;
            }
        }

        BigInteger f = BigInteger.ONE;
        for (int v : Arrays.stream(rewardValues).distinct().sorted().toArray()) {
            BigInteger mask = BigInteger.ONE.shiftLeft(v).subtract(BigInteger.ONE);
            f = f.or(f.and(mask).shiftLeft(v));
        }
        return f.bitLength() - 1;
    }




}
