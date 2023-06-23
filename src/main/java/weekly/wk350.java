package weekly;

import java.util.Arrays;

public class wk350 {
    //模拟
    public int distanceTraveled(int mainTank, int additionalTank) {
        int ans = 0;
        while (mainTank > 0) {
            if (mainTank >= 5) {
                if (additionalTank > 0) {
                    ans += 10 * 5;
                    mainTank -= 5;
                    mainTank += 1;
                    additionalTank--;
                } else {
                    ans += 10 * 5;
                    mainTank -= 5;
                }
            } else {
                ans += mainTank * 10;
                mainTank = 0;
            }
        }
        return ans;
    }

    //排序
    public int findValueOfPartition(int[] nums) {
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            min = Math.min(min, nums[i] - nums[i - 1]);
        }
        return min;
    }


    private int[][] memo;
    int mod = (int) 1e9 + 7;

    int dfs(int[] nums, int index, int pre, int state) {
        if (index >= nums.length) {
            return 1;
        }
        if (memo[pre + 1][state] != -1) {
            return memo[pre + 1][state];
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (((state >> i) & 1) == 0 && (pre == -1 || (nums[i] % nums[pre] == 0) || (nums[pre] % nums[i] == 0))) {
                count = (count + dfs(nums, index + 1, i, state | (1 << i))) % mod;
            }
        }

        memo[pre + 1][state] = count;
        return count;
    }

    //记忆化搜索
    public int specialPerm(int[] nums) {
        memo = new int[nums.length * (nums.length + 1)][1 << nums.length];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }

        return dfs(nums, 0, -1, 0);
    }


 /*   public int paintWalls(int[] cost, int[] time) {
        int csum = 0;
        for (int c : time) {
            csum += c;
        }
        int[][] dp = new int[csum + 1][cost.length + 1];
        int[][] count = new int[csum + 1][cost.length + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, (int) 1e9 + 7);
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= time.length; i++) {
            for (int j = 1; j <= cost.length; j++) {
                int t = time[j - 1];
                int c = cost[j - 1];
                if (i - t >= 0) {
                    dp[i][j] = dp[i - t][j - 1] + c;
                    count[i][j] = count[i - t][j - 1] + 1;
                }
                if (dp[i][j - 1] < dp[i][j]) {
                    dp[i][j] = dp[i][j - 1];
                    count[i][j] = count[i][j - 1];
                }else if(dp[i][j - 1] == dp[i][j]) {
                    count[i][j] = Math.max(count[i][j], count[i][j - 1]);
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1]);
                if ((i + count[i][j]) >= (time.length)) {
                    ans = Math.min(dp[i][j], ans);
                }
            }
        }
        return ans;
    }*/


    //要先化简题目+记忆化
  /*  public int paintWalls(int[] cost, int[] time) {
        int ts = 0;
        for (int i : time) {
            ts += i;
        }
        memos = new int[cost.length][cost.length * 2 + 1];
        for (int[] ints : memos) {
            Arrays.fill(ints, -1);
        }
        return dfs(0, 0, cost, time);
    }

    int inf = (int) 1e9 + 7;
    int[][] memos;

    int dfs(int i, int j, int[] cost, int[] time) {
        if (j >= cost.length - i) {
            return 0;
        }
        if (i >= cost.length) return inf;

        if (memos[i][cost.length + j] != -1) {
            return memos[i][cost.length + j];
        }
        int res = Math.min(dfs(i + 1, j + time[i], cost, time) + cost[i], dfs(i + 1, j - 1, cost, time));
        memos[i][cost.length + j] = res;
        return res;
    }*/

    int inf = (int) 1e9 + 7;

    //递推
    public int paintWalls(int[] cost, int[] time) {
        int[] dp = new int[cost.length + 1];
        Arrays.fill(dp, inf);
        dp[0] = 0;
        for (int i = 0; i < cost.length; i++) {
            for (int j = cost.length; j >= 0; j--) {
                dp[j] = Math.min(dp[j], dp[Math.max(j - time[i] - 1, 0)]+cost[i]);
            }
        }
        return dp[cost.length];
    }

}
