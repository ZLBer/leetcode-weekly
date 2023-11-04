package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk368 {
   /* public int minimumSum(int[] nums) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] < nums[j] && nums[j] > nums[k]) {
                        ans = Math.min(nums[i] + nums[j] + nums[k], ans);
                    }
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }*/


    //前后缀
    public int minimumSum(int[] nums) {
        int[] minLeft = new int[nums.length + 1];
        int[] minRight = new int[nums.length + 1];

        minLeft[0] = (int) 1e9 + 7;
        minRight[nums.length] = (int) 1e9 + 7;
        for (int i = 0; i < nums.length; i++) {
            minLeft[i + 1] = Math.min(nums[i], minLeft[i]);
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            minRight[i] = Math.min(nums[i], minRight[i + 1]);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length - 1; i++) {
            if (minLeft[i] < nums[i] && minRight[i + 1] < nums[i]) {
                ans = Math.min(ans, minLeft[i] + nums[i] + minRight[i + 1]);
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    //找出最小分组
    static public int minGroupsForValidAssignment(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) map.put(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }

        List<Integer> count = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            count.add(entry.getValue().size());
            min = Math.min(min, entry.getValue().size());
        }
        int ans = nums.length;

        for (int i = 1; i <= min; i++) {
            ans = Math.min(ans, check(i, count));
        }

        return ans;
    }

    static int check(int min, List<Integer> count) {
        int ans = 0;
        for (Integer c : count) {
            if (c / min < c % min) {
                return Integer.MAX_VALUE;
            }
            //
            ans += (c + min) / (min + 1);
        }
        return ans;
    }


    //预处理+记忆化搜索好写一点
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] modify = new int[n - 1][n];
        for (int left = 0; left < n - 1; left++) {
            for (int right = left + 1; right < n; right++) {
                modify[left][right] = getModify(s.substring(left, right + 1));
            }
        }

        int[][] memo = new int[k][n];
        for (int i = 0; i < k; i++) {
            Arrays.fill(memo[i], -1); // -1 表示没有计算过
        }
        return dfs(k - 1, n - 1, memo, modify);
    }

    private static final int MX = 201;
    private static final List<Integer>[] divisors = new ArrayList[MX];

    static {
        // 预处理每个数的真因子，时间复杂度 O(MX*logMX)
        Arrays.setAll(divisors, k -> new ArrayList<>());
        for (int i = 1; i < MX; i++) {
            for (int j = i * 2; j < MX; j += i) {
                divisors[j].add(i);
            }
        }
    }

    private int getModify(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int res = n;
        for (int d : divisors[n]) {
            int cnt = 0;
            for (int i0 = 0; i0 < d; i0++) {
                for (int i = i0, j = n - d + i0; i < j; i += d, j -= d) {
                    if (s[i] != s[j]) {
                        cnt++;
                    }
                }
            }
            res = Math.min(res, cnt);
        }
        return res;
    }

    private int dfs(int i, int j, int[][] memo, int[][] modify) {
        if (i == 0) { // 递归边界
            return modify[0][j];
        }
        if (memo[i][j] != -1) { // 之前计算过
            return memo[i][j];
        }
        int res = Integer.MAX_VALUE;
        for (int L = i * 2; L < j; L++) {
            res = Math.min(res, dfs(i - 1, L - 1, memo, modify) + modify[L][j]);
        }
        return memo[i][j] = res; // 记忆化
    }


    public static void main(String[] args) {
        minGroupsForValidAssignment(new int[]{
                1, 3, 2, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 3, 1});
    }
}
