package weekly;

import java.util.Arrays;
import java.util.Map;

public class wk369 {

    public int findKOr(int[] nums, int k) {


        int res = 0;

        for (int i = 0; i < 31; i++) {
            int c = 1 << i;
            int count = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((nums[j] & c) == c) {
                    count++;
                }
            }
            if (count >= k) {
                res += c;
            }
        }
        return res;
    }

    public long minSum(int[] nums1, int[] nums2) {
        long sum1 = 0, sum2 = 0;
        long zero1 = 0, zero2 = 0;
        for (int i : nums1) {
            sum1 += i;
            if (i == 0) {
                zero1++;
            }
        }
        for (int i : nums2) {
            sum2 += i;
            if (i == 0) {
                zero2++;
            }
        }
        if (sum2 > sum1) {
            long a = sum1;
            sum1 = sum2;
            sum2 = a;
            a = zero1;
            zero1 = zero2;
            zero2 = a;
        }

        //System.out.println(sum1+" "+sum2+" "+zero1+" "+zero2);
        long sub = sum1 - sum2;
        if (sub == 0) {
            if (zero1 == 0 && zero2 == 0) {
                return 0;
            } else if (zero1 == 0 || zero2 == 0) {
                return -1;
            } else {
                return sum1 + Math.max(zero1, zero2);
            }
        } else {

            if (zero2 == 0) return -1;
            if (zero1 == 0) {
                if (sub < zero2) {
                    return -1;
                } else {
                    return sum1;
                }
            }
            return Math.max(sum1 + zero1, sum2 + zero2);
        }
    }


    public long minIncrementOperations(int[] nums, int k) {
        long[] dp = new long[3];
        for (int i = 0; i < 3; i++) {
           dp[i]=Math.max(0,k-nums[i]);
        }

        for (int i = 3; i < nums.length; i++) {
            long up=Math.min(Math.min(dp[0],dp[1]),dp[2])+Math.max(0,k-nums[i]);
            dp[0]=dp[1];
            dp[1]=dp[2];
            dp[2]=up;
        }
        return Math.min(Math.min(dp[0],dp[1]),dp[2]);
    }

    static long min(long[] dp) {
        long ans = Integer.MAX_VALUE;
        for (int i = 0; i < dp.length; i++) {
            ans = Math.min(dp[i], ans);
        }
        return ans;
    }

    public static void main(String[] args) {

    }

}
