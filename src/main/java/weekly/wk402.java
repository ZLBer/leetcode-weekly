package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk402 {


    // 哈希
    public long countCompleteDayPairs(int[] hours) {
        int[] count = new int[24];
        for (int hour : hours) {
            count[hour % 24]++;
        }

        long ans = 0;
        for (int i = 0; i <= count.length / 2; i++) {
            int sub = 24 - i;
            if (sub == 24 || sub == 12) {
                ans += (long) count[i] * (count[i] - 1) / 2;
            } else {
                ans += (long) count[i] * (count[sub]);
            }
        }
        return ans;
    }



    // 排序+dp
    static public long maximumTotalDamage(int[] power) {

        Arrays.sort(power);
        Map<Integer, Long> map = new HashMap<>();
        for (int i : power) {
            map.put(i, map.getOrDefault(i, 0L) + i);
        }

        List<Integer> list = new ArrayList<>();
        list.add(-100);
        for (int i = 0; i < power.length; i++) {
            if (i == 0 || power[i] != power[i - 1]) {
                list.add(power[i]);
            }
        }

        long[][] dp = new long[list.size()][2];
        for (int i = 1; i < list.size(); i++) {
            int left = i;
            while (left >= 0 && list.get(left) >= list.get(i) - 2) {
                left--;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = Math.max(dp[left][1], dp[left][0]) + map.get(list.get(i));
        }
        return Math.max(dp[list.size() - 1][0], dp[list.size() - 1][1]);
    }


    public static void main(String[] args) {
        wk402 w = new wk402();
        w.countOfPeaks(new int[]{4, 9, 4, 10, 7}, new int[][]{
                {2, 3, 2}, {2, 1, 3}, {1, 2, 3}}
        );
    }

    //树状数组
    public List<Integer> countOfPeaks(int[] nums, int[][] queries) {


        FenwickTree f = new FenwickTree(nums.length + 1);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            help(i, nums, f);
        }
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            if (query[0] == 1) {
                if (query[2] - query[1] < 2) {
                    ans.add(0);
                    continue;
                }
                ans.add(f.query(query[2]) - f.query(query[1] + 1));
            } else {
                int index = query[1];
                nums[index] = query[2];
                help(index, nums, f);
                help(index + 1, nums, f);
                help(index - 1, nums, f);
            }
        }
        return ans;
    }

    void help(int index, int[] nums, FenwickTree f) {
        if (index <= 0 || index >= nums.length - 1) return;
        // 之前是峰值元素
        if (f.query(index + 1) - f.query(index) == 1) {
            //依旧是峰值
            if (nums[index] > nums[index - 1] && nums[index] > nums[index + 1]) {

            } else {
                //不是峰值
                f.update(index + 1, -1);
            }

        } else {
            //之前不是
            // 现在是了
            if (nums[index] > nums[index - 1] && nums[index] > nums[index + 1]) {
                f.update(index + 1, 1);
            } else {
                //现在也不是
            }
        }
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
