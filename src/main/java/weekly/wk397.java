package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk397 {
    // 哈希
    public int findPermutationDifference(String s, String t) {
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }
        for (int i = 0; i < t.length(); i++) {
            ans += Math.abs(i - map.get(t.charAt(i)));
        }
        return ans;
    }

    //遍历
    public int maximumEnergy(int[] energy, int k) {
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < k; i++) {
            int ans = 0;
            for (int j = i; j < energy.length; j += k) {
                if (ans + energy[j] < energy[j]) {
                    ans = energy[j];
                } else {
                    ans += energy[j];
                }
            }
            res = Math.max(res, ans);
        }
        return res;
    }

    // dp
    public int maxScore(List<List<Integer>> grid) {
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] min = new int[m + 1][n + 1];
        int ans = Integer.MIN_VALUE;

        for (int i = 0; i <= m; i++) {
            min[i][0] = Integer.MAX_VALUE;
        }
        for (int i = 0; i <= n; i++) {
            min[0][i] = Integer.MAX_VALUE;
        }


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                min[i + 1][j + 1] = Math.min(min[i][j + 1], min[i + 1][j]);
                ans = Math.max(ans, grid.get(i).get(j) - min[i + 1][j + 1]);
                min[i + 1][j + 1] = Math.min(grid.get(i).get(j), min[i + 1][j + 1]);
                System.out.println(min[i + 1][j + 1]);
            }
        }


        return ans;
    }


    // 记忆化
    public int[] findPermutation(int[] nums) {
        memo = new int[1 << nums.length][nums.length];
        ans = new int[nums.length];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }
        makeAns(1,0,nums,0);
        return ans;
    }

    int[][] memo;
    int[] ans;

    int dfs(int s, int cur, int[] nums) {
        if (s == (1 << nums.length) - 1) {
            return Math.abs(cur - nums[0]);
        }

        if (memo[s][cur] != -1) return memo[s][cur];

        int res = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if ((s & (1 << i)) != 0) continue;
            res = Math.min(Math.abs(cur - nums[i]) + dfs(s | (1 << i), i, nums), res);
        }

        memo[s][cur] = res;
        return res;
    }

    void makeAns(int s, int cur, int[] nums, int j) {
        ans[j] = cur;
        if (s == (1 << nums.length) - 1) {
            return;
        }

        int res = dfs(s, cur, nums);
        for (int i = 1; i < nums.length; i++) {
            if ((s & (1 << i)) != 0) continue;
            if (dfs(s | (1 << i), i, nums)+Math.abs(cur - nums[i])  == res) {
                makeAns(s | (1 << i), i, nums, j + 1);
                break;
            }
        }
    }


    public static void main(String[] args) {
        wk397 w=new wk397();
        w.findPermutation(new int[]{0,1,2});
    }

}
