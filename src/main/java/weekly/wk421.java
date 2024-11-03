package weekly;

import java.util.Arrays;

public class wk421 {

   /* public long maxScore(int[] nums) {
        if (nums.length == 1) {
            return (long) nums[0] * nums[0];
        }
        long ans = help(nums, -1);
        for (int i = 0; i < nums.length; i++) {
            ans = Math.max(help(nums, i), ans);
        }
        return ans;

    }*/

    long help(int[] nums, int index) {
        long a = -1, b = -1;
        for (int i = 0; i < nums.length; i++) {
            if (i == index) {
                continue;
            }
            if (a == -1) {
                a = nums[i];
                b = nums[i];
            } else {
                a = gcd(a, nums[i]);
                b = lcm(b, nums[i]);
            }
        }
        return a * b;
    }

    //前后缀
    public long maxScore(int[] nums) {
        int n = nums.length;
        int[] sufGcd = new int[n + 1];
        long[] sufLcm = new long[n + 1];
        sufLcm[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            sufGcd[i] = (int) gcd(sufGcd[i + 1], nums[i]);
            sufLcm[i] = lcm(sufLcm[i + 1], nums[i]);
        }

        long ans = sufGcd[0] * sufLcm[0]; // 不移除元素
        int preGcd = 0;
        long preLcm = 1;
        for (int i = 0; i < n; i++) { // 枚举移除 nums[i]
            ans = Math.max(ans, gcd(preGcd, sufGcd[i + 1]) * lcm(preLcm, sufLcm[i + 1]));
            preGcd = (int) gcd(preGcd, nums[i]);
            preLcm = lcm(preLcm, nums[i]);
        }
        return ans;
    }


    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }


//    public int lengthAfterTransformations(String s, int t) {
//        int mod = (int) 1e9 + 7;
//        long ans = 0;
//        for (int i = 0; i < s.length(); i++) {
//            //到z要多少步骤
//            int d = 'z' - s.charAt(i) + 1;
//            if (d > t) {
//                ans++;
//            } else {
//                // 从z到..
//                ans += help(t - d);
//            }
//            ans%=mod;
//        }
//        return ans;
//    }


    //递推
    public int lengthAfterTransformations(String s, int t) {
        int mod = (int) 1e9 + 7;
        long[] arr = new long[26];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t; i++) {
            long a = arr[25];
            long b = arr[25];
            for (int j = arr.length - 1; j >= 1; j--) {
                arr[j] = arr[j - 1];
            }
            arr[0] = a;
            arr[1] += b;
            arr[1]%=mod;
        }
        long ans=0;
        for (long l : arr) {
            ans+=l;
            ans%=mod;
        }
       return (int) ans;
    }


    private static final int MOD = 1_000_000_007;


    // dfs/dp
    public int subsequencePairCount(int[] nums) {
        int n = nums.length;
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }
        int[][][] memo = new int[n][m + 1][m + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示没有计算过
            }
        }
        return (dfs(n - 1, 0, 0, nums, memo) - 1 + MOD) % MOD; // +MOD 防止减一后变成负数
    }

    int dfs(int i, int j, int k, int[] nums, int[][][] memo) {
        if (i < 0) {
            return j == k ? 1 : 0;
        }
        if (memo[i][j][k] < 0) {
            long res = (long) dfs(i - 1, j, k, nums, memo) +   // 不选i
                    dfs(i - 1, gcd(j, nums[i]), k, nums, memo) + // 把i放进第一组
                    dfs(i - 1, j, gcd(k, nums[i]), nums, memo);  // 把i放进第二组
            memo[i][j][k] = (int) (res % MOD);
        }
        return memo[i][j][k];
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


}
