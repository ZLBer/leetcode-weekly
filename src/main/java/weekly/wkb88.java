package weekly;

import java.util.HashMap;
import java.util.Map;

public class wkb88 {

    //ranking: 354 / 3905
    
    //简单题，暴力解决
    public boolean equalFrequency(String word) {
        for (int i = 0; i < word.length(); i++) {
            int[] count = new int[26];
            for (int j = 0; j < word.toCharArray().length; j++) {
                char c = word.charAt(j);
                if (j == i) continue;
                count[c - 'a']++;
            }

            Map<Integer, Integer> map = new HashMap<>();
            for (int j = 0; j < count.length; j++) {
                if (count[j] != 0) {
                    map.put(count[j], map.getOrDefault(count[j], 0) + 1);
                }
            }
            if (map.size() == 1) return true;
        }
        return false;
    }

    //用一个指针标记最长前缀
    class LUPrefix {
        int[] dp;
        int index = 1;

        public LUPrefix(int n) {
            dp = new int[n + 1];
            dp[0] = 1;
        }

        public void upload(int video) {
            dp[video] = 1;
            while (index < dp.length && dp[index] == 1) {
                index++;
            }
        }

        public int longest() {
            return index - 1;
        }
    }

    //找规律即可
    public int xorAllNums(int[] nums1, int[] nums2) {
        int ans = 0;
        if (nums2.length % 2 == 1) {
            for (int i : nums1) {
                ans ^= i;
            }
        }
        if (nums1.length % 2 == 1) {
            for (int i : nums2) {
                ans ^= i;
            }
        }
        return ans;
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

    //把两个数组拼成一个数组，然后线段树查找指定区间值的个数
    public long numberOfPairs(int[] nums1, int[] nums2, int diff) {
        int MAX = 100000;
        int D = 5 * 10000;
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = nums1[i] - nums2[i];
        }
        FenwickTree f = new FenwickTree(MAX);
        long ans = 0;
        for (int i = 0; i < nums1.length; i++) {
            ans += f.query(nums1[i] + diff + D);
            f.update(nums1[i] + D, 1);
        }
        return ans;
    }

    //补周赛313第四题
    public int deleteString(String s) {
        int n = s.length();
        int[][] lcp = new int[n + 1][n + 1];
        //求s[i]s[j]的最长公共前缀长度
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    lcp[i][j] = lcp[i + 1][j + 1] + 1;
                }
            }
        }
        int[] dp = new int[n];
        for (int i = n-1; i >= 0; i--) {
            dp[i]=1;
            for (int j = 1; i + j * 2 <= n; j++) {
                if (lcp[i][i + j] >= j) {
                    dp[i]=Math.max(dp[i],dp[i+j]+1);
                }
            }
        }
        return dp[0];
    }
}
