package weekly;

import java.util.Arrays;

public class wk433 {

    public int subarraySum(int[] nums) {
        int[] sum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int start = Math.max(0, i - nums[i]);
            int end = i;
            ans += sum[end + 1] - sum[start];
        }
        return ans;
    }


    public int minMaxSums(int[] nums, int k) {
        long ans = 0;
        int mod = (int) 1e9 + 7;
        Arrays.sort(nums);
        long[] dp = new long[k];
        dp[0] = 1;
        int res = 0;
        for (int i = 0; i < nums.length; ++i) {
            long sum = 1;
            // 前i - 1个数中挑选0 到 k - 1个数字
            for (int j = Math.min(i, k - 1); j >= 1; --j) {
                dp[j] = (dp[j] + dp[j - 1]) % mod;
                sum += dp[j];
                sum %= mod;
            }
            res += sum * nums[i] % mod;
            res %= mod;
        }

        for (int i = 0; i < nums.length; i++) {
            int left = Math.min(i, k - 1);
            int right = Math.min(nums.length - i - 1, k - 1);

            for (int j = 0; j <= left; j++) {
                ans += comnination(i, j);
            }


        }
        return (int) ans;
    }

    // m>=n
    public int comnination(int m, int n) {
        if (n == 0) return 1;
        long ans = 1;

        //都从小的开始 防止过早溢出
        for (int x = m - n + 1, y = 1; y <= n; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }


    public long minCost(int n, int[][] cost) {

        long[][][] dp = new long[n / 2][3][3];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                Arrays.fill(dp[i][j], Long.MAX_VALUE / 2);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) continue;
                dp[0][i][j] = cost[0][i] + cost[cost.length - 1][j];
            }
        }

        for (int i = 1; i < n / 2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (j == k) continue;

                    for (int a = 0; a < 3; a++) {
                        for (int b = 0; b < 3; b++) {
                            if (a == j) continue;
                            if (b == k) continue;
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][a][b] + cost[i][j] + cost[cost.length - 1 - i][k]);
                        }
                    }
                }
            }
        }

        long ans = Long.MAX_VALUE;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (j != k) {
                    ans = Math.min(ans, dp[n / 2 - 1][j][k]);
                }
            }
        }
        return ans;
    }
}
