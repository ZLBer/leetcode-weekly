package weekly;

import java.util.Arrays;

public class wk403 {

    //排序
    public double minimumAverage(int[] nums) {
        double res = Double.MAX_VALUE;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length / 2; i++) {
            res = Math.min(res, ((double) nums[i] + nums[nums.length - i - 1]) / 2);
        }
        return res;

    }


    // 模拟
    public int minimumArea(int[][] grid) {
        int left = grid[0].length, right = 0;
        int up = grid.length, down = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    left = Math.min(left, j);
                    right = Math.max(right, j);
                    up = Math.min(up, i);
                    down = Math.max(down, i);
                }
            }
        }
        return (right - left + 1) * (down - up + 1);
    }


    // dp
    // dp[i][0]表示+
    // dp[i][1]表示-
   static public long maximumTotalCost(int[] nums) {
        long[][] dp = new long[nums.length][2];
        dp[0][0] = nums[0];
        dp[0][1] = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i-1][1]) + nums[i];

            dp[i][1] = dp[i-1][0] - nums[i];
        }

        return Math.max(dp[nums.length-1][0],dp[nums.length-1][1]);
    }

    public static void main(String[] args) {
        maximumTotalCost(new int[]{1,-2,3,4});
    }



}
