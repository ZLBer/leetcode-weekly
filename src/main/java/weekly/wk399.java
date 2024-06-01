package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk399 {
//    public int numberOfPairs(int[] nums1, int[] nums2, int k) {
//
//    }


    // 遍历
    public String compressedString(String word) {
        char pre = word.charAt(0);
        int count = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c != pre || count == 9) {
                sb.append(count);
                sb.append(pre);
                pre = c;
                count = 1;
            } else {
                count++;
            }
        }
        sb.append(count);
        sb.append(pre);
        return sb.toString();
    }


    //
    public long numberOfPairs(int[] nums1, int[] nums2, int k) {
        int max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            max = Math.max(i, max);
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        Map<Integer, Integer> map1 = new HashMap<>();
        for (int i : nums2) {
            map1.put(i * k, map1.getOrDefault(i * k, 0) + 1);
        }

        long ans = 0;
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            for (int i = 1; i * num <= max; i++) {
                if (map.containsKey(i * num)) {
                    ans += ((long) map.get(i * num)) * count;
                }
            }
        }
        return ans;
    }


    static final int MOD = 1000000007;

    //线段树
    public int maximumSumSubsequence(int[] nums, int[][] queries) {
        int n = nums.length;
        SegTree tree = new SegTree(nums);
        long sum = 0;
        for (int[] q : queries) {
            tree.update(q[0], q[1]);
            sum = (sum + tree.query()) % MOD;
        }
        return (int) sum;
    }

    public class SegTree {
        long[] max;// 最优子序列和
        long[] pre;// 不包含右端点最优子序列和
        long[] suf;// 不包含左端点最优子序列和
        long[] not;// 既不包含左端点又不包含右端点最优子序列和
        int N;

        public SegTree(int[] nums) {
            N = nums.length;
            max = new long[N << 2];
            pre = new long[N << 2];
            suf = new long[N << 2];
            not = new long[N << 2];
            build(nums, 1, N, 1);
        }

        // 初始化
        private void build(int[] nums, int l, int r, int i) {
            if (l == r) {
                max[i] = Math.max(nums[l - 1], 0);
                return;
            }
            int m = (l + r) >> 1;
            build(nums, l, m, i << 1);
            build(nums, m + 1, r, i << 1 | 1);
            up(i);
        }

        // 区间合并
        private void up(int i) {
            int l = i << 1, r = i << 1 | 1;
            max[i] = Math.max(pre[l] + max[r], max[l] + suf[r]);
            pre[i] = Math.max(max[l] + not[r], pre[l] + pre[r]);
            suf[i] = Math.max(not[l] + max[r], suf[l] + suf[r]);
            not[i] = Math.max(suf[l] + not[r], not[l] + pre[r]);
        }

        // 单点更新
        public void update(int index, int value) {
            update(index + 1, value, 1, N, 1);
        }

        private void update(int idx, int v, int l, int r, int i) {
            if (l == r) {
                max[i] = Math.max(v, 0);
                return;
            }
            int m = (l + r) >> 1;
            if (idx <= m) {
                update(idx, v, l, m, i << 1);
            } else {
                update(idx, v, m + 1, r, i << 1 | 1);
            }
            up(i);
        }

        // 1 代表区间 [0, n - 1]
        public long query() {
            return max[1];
        }
    }


    public static void main(String[] args) {
    }

}
