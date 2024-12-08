package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk425 {
    //滑动窗口
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            int sum = 0;
            for (int j = i; j < nums.size(); j++) {
                sum += nums.get(j);
                if (sum > 0 && (j - i + 1) >= l && (j - i + 1) <= r) {
                    ans = Math.min(ans, sum);
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    //哈希表
    public boolean isPossibleToRearrange(String s, String t, int k) {
        k = s.length() / k;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); ) {
            String sub = s.substring(i, i + k);
            map.put(sub, map.getOrDefault(sub, 0) + 1);
            i += k;
        }

        for (int i = 0; i < t.length(); ) {
            String sub = t.substring(i, i + k);
            int c = map.getOrDefault(sub, 0);
            if (c <= 0) {
                return false;
            }
            map.put(sub, c - 1);
            i += k;
        }
        return true;
    }


    // 记忆化搜索
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        memo = new int[nums.length][op1 + 1][op2 + 1];
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0,nums,k,op1,op2);
    }

    int[][][] memo;

    int dfs(int cur, int[] nums, int k, int op1, int op2) {
        if (cur >= nums.length) {
            return 0;
        }
        if (memo[cur][op1][op2] != -1) {
            return memo[cur][op1][op2];
        }
        int num = nums[cur];
        //什么操作都不进行
        int ans = num + dfs(cur + 1, nums, k, op1, op2);
        //进行操作1
        if (op1 > 0) {
            int sum = 0;
            int newNum = (num + 1) / 2;
            sum += newNum;
            sum += dfs(cur + 1, nums, k, op1 - 1, op2);
            ans = Math.min(sum, ans);
            // 再进行操作2
            if (op2 > 0 && newNum >= k) {
                sum = 0;
                sum += (newNum - k);
                sum += dfs(cur + 1, nums, k, op1 - 1, op2 - 1);
                ans = Math.min(sum, ans);
            }
        }
        //进行操作2
        if (op2 > 0 && num >= k) {
            int sum = 0;
            sum += (num - k);
            sum += dfs(cur + 1, nums, k, op1, op2 - 1);
            ans = Math.min(sum, ans);
            // 再进行操作1
            if (op1 > 0) {
                sum = 0;
                sum += (num - k + 1) / 2;
                sum += dfs(cur + 1, nums, k, op1 - 1, op2 - 1);
                ans = Math.min(sum, ans);
            }
        }

        memo[cur][op1][op2] = ans;
        return ans;

    }

    public static void main(String[] args) {
        wk425 w=new wk425();
        w.minArraySum(new int[]{275,208,626},553,3,2);
    }
}
