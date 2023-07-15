package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class wk353 {

    //贪心
    public int theMaximumAchievableX(int num, int t) {
        return num + t * 2;
    }

    //dp
    public int maximumJumps(int[] nums, int target) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] - nums[j] >= -target && nums[i] - nums[j] <= target && dp[j] != -1) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[nums.length - 1];
    }


    //dp
    public int maxNonDecreasingLength(int[] nums1, int[] nums2) {
        int ans = 1;
        int[][] dp = new int[nums1.length][2];
        dp[0][0] = 1;
        dp[0][1] = 1;
        for (int i = 1; i < nums1.length; i++) {
            dp[i][0] = 1;
            dp[i][1] = 1;
            if (nums1[i] >= nums1[i - 1]) {
                dp[i][0] = Math.max(dp[i - 1][0] + 1, dp[i][0]);
            }
            if (nums1[i] >= nums2[i - 1]) {
                dp[i][0] = Math.max(dp[i - 1][1] + 1, dp[i][0]);
            }
            if (nums2[i] >= nums1[i - 1]) {
                dp[i][1] = Math.max(dp[i - 1][0] + 1, dp[i][1]);
            }
            if (nums2[i] >= nums2[i - 1]) {
                dp[i][1] = Math.max(dp[i - 1][1] + 1, dp[i][1]);
            }
            ans = Math.max(ans, dp[i][0]);
            ans = Math.max(ans, dp[i][1]);
        }
        return ans;

    }


    //滑动窗口  本质是差分数组
   static public boolean checkArray(int[] nums, int k) {

        if(k==1) return true;
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int left = i - k ;
            //减去左边的贡献
            if (left >= 0) {
                sum -= nums[left];
            }
            //需要减去更多，此处已经不满足
            if (sum > nums[i]) {
                return false;
            }
            //i处需要添加的贡献
            nums[i] = (nums[i] - sum);
            sum+=nums[i];
        }
        return nums[nums.length-1]==0;
    }

    public static void main(String[] args) {
        checkArray(new int[]{2,2,3,2,1},3);
    }
}
