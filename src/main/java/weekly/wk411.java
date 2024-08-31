package weekly;

import java.math.BigInteger;
import java.util.Arrays;

public class wk411 {

    // 滑动窗口
    public int countKConstraintSubstrings(String s, int k) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] count = new int[2];
            for (int j = i; j < s.length(); j++) {
                count[s.charAt(j) - '0']++;
                if (count[0] <= k || count[1] <= k) {
                    ans++;
                }
            }
        }
        return ans;
    }

 /*   public long maxEnergyBoost(int[] energyDrinkA, int[] energyDrinkB) {
        long dp[][] = new long[energyDrinkA.length + 1][3];

        for (int i = 0; i < energyDrinkA.length; i++) {
            dp[i + 1][0] = Math.max(energyDrinkA[i] + dp[i][0], energyDrinkA[i] + dp[i][2]);
            dp[i + 1][1] = Math.max(energyDrinkB[i] + dp[i][1], energyDrinkB[i] + dp[i][2]);
            dp[i + 1][2] = Math.max(dp[i][0], dp[i][1]);
        }
        return Math.max(dp[energyDrinkA.length][0], dp[energyDrinkA.length][1]);
    }

    public String largestPalindrome(int n, int k) {
        BigInteger big = BigInteger.ZERO;
        BigInteger[] mul = new BigInteger[n + 1];
        mul[0] = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            mul[i + 1] = mul[i].multiply(BigInteger.TEN);
            big.multiply(BigInteger.TEN).add(BigInteger.valueOf(9));
        }

        BigInteger mod = big.mod(BigInteger.valueOf(k));
        // 模k为0,直接返回
        if (mod.equals(BigInteger.ZERO)) {
            return big.toString();
        }
        String[] mods = new String[k];
        for (int i = 0; i < 10; i++) {
            int[] add = new int[k];
            Arrays.fill(add, -1);
            if (add[i % k] == -1) {
                add[i % k] = i;
            }
        }
        int mid = n / 2;
        for (int i = 0; i < n / 2; i++) {
            int left = mid - i;
            int right = mid + i;
            for (int j = 0; j < 10; j++) {

            }
        }

    }*/

    public long[] countKConstraintSubstrings(String S, int k, int[][] queries) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] left = new int[n];
        long[] sum = new long[n + 1];
        int[] cnt = new int[2];
        int l = 0;
        // 计算 i的合法左侧位置以及前缀和
        for (int i = 0; i < n; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[l++] & 1]--;
            }
            left[i] = l;
            // 计算 i-left[i]+1 的前缀和
            sum[i + 1] = sum[i] + i - l + 1;
        }

        //二分查找分界点
        long[] ans = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int ql = queries[i][0];
            int qr = queries[i][1];
            int j = lowerBound(left, ql - 1, qr + 1, ql);
            ans[i] = sum[qr + 1] - sum[j] + (long) (j - ql + 1) * (j - ql) / 2;
        }
        return ans;
    }

    private int lowerBound(int[] nums, int left, int right, int target) {
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid; // 范围缩小到 (mid, right)
            } else {
                right = mid; // 范围缩小到 (left, mid)
            }
        }
        return right;
    }

    //树状数组板子
    public class FenwickTree {

        /**
         * 预处理数组
         */
        private int[] tree;
        private int len;

        public FenwickTree(int n) {
            this.len = n;
            tree = new int[n + 1];
        }

        /**
         * 单点更新
         *
         * @param i     原始数组索引 i
         * @param delta 变化值 = 更新以后的值 - 原始值
         */
        public void update(int i, int delta) {
            // 从下到上更新，注意，预处理数组，比原始数组的 len 大 1，故 预处理索引的最大值为 len
            while (i <= len) {
                tree[i] += delta;
                i += lowbit(i);
            }
        }

        //区间更新
        void update(int x, int y, int k) {
            update(x, k);
            update(y + 1, -k);
        }

        /**
         * 查询前缀和
         *
         * @param i 前缀的最大索引，即查询区间 [0, i] 的所有元素之和
         */
        public int query(int i) {
            // 从右到左查询
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= lowbit(i);
            }
            return sum;
        }

        public int lowbit(int x) {
            return x & (-x);
        }
    }
}
