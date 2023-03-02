package weekly;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wk333 {

    //ranking: 1623 / 4969

    //双指针，可直接treeMap
    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {

        int i = 0;
        int j = 0;
        List<int[]> list = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i][0] == nums2[j][0]) {
                list.add(new int[]{nums1[i][0], nums1[i++][1] + nums2[j++][1]});
            } else if (nums1[i][0] > nums2[j][0]) {
                list.add(new int[]{nums2[j][0], nums2[j++][1]});
            } else {
                list.add(new int[]{nums1[i][0], nums1[i++][1]});
            }
        }
        while (i < nums1.length) {
            list.add(new int[]{nums1[i][0], nums1[i++][1]});
        }
        while (j < nums2.length) {
            list.add(new int[]{nums2[j][0], nums2[j++][1]});
        }
        int[][] res = new int[list.size()][2];
        for (int i1 = 0; i1 < list.size(); i1++) {
            res[i1] = list.get(i1);
        }
        return res;
    }


    //贪心
    public int minOperations(int n) {
        int ans = 1;
        while ((n & (n - 1)) > 0) { // n 不是 2 的幂次
            int lb = n & -n;
            if ((n & (lb << 1)) > 0) n += lb; // 多个连续 1
            else n -= lb; // 单个 1
            ++ans;
        }
        return ans;
    }



    /* static public int squareFreeSubsets(int[] nums) {
         int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
         int[] cnt = new int[1 << primes.length];
         for (int num : nums) {
             int mask = 0;
             for (int i = 0; i < primes.length; i++) {
                 if (num % primes[i] == 0) {
                     num /= primes[i];
                     mask ^= (1 << i);
                 }
             }
             if (num == 1) {
                 cnt[mask]++;
             }
         }
         long[] dp = new long[1 << primes.length];
         dp[0] = 1;
         for (int i = 0; i < primes.length; i++) {
             long[] ndp = new long[1 << primes.length];
             for (int j = 0; j < (1 << primes.length); j++) {
                 ndp[j] = (dp[j] + (long) cnt[j] * dp[j ^ (1 << i)]) % MOD;
             }
             dp = ndp;
         }
         long res = 0;
         for (long l : dp) {
             res += l;
         }
         return (int) (res % MOD);
     }*/


    //状态压缩
    static private final int MOD = (int) 1e9 + 7;
    static public int squareFreeSubsets(int[] nums) {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        int[] maskMap = new int[30 + 1];

        for (int i = 2; i <= 30; i++) {
            int num = i;
            int mask = 0;
            for (int j = 0; j < primes.length; j++) {
                if (num % primes[j] == 0) {
                    num /= primes[j];
                    mask |= (1 << j);
                }
            }
            if (num == 1) {
                maskMap[i] = mask;
            } else {
                maskMap[i] = -1;
            }
        }
        long[] dp = new long[1 << primes.length];
        dp[0] = 1;
        for (int num : nums) {
            int mask = maskMap[num];
            if (mask < 0) continue; //存在映射
            for (int i = (1 << primes.length) - 1; i >= 0; i--) {
                if ((i | mask) == i) //mask是i的子集
                    dp[i] = (dp[i] + dp[i ^ mask]) % MOD;
            }
        }
        long ans = 0;
        for (long l : dp) {
            ans += l;
        }
        return (int) ((ans - 1) % MOD);  //去掉空集
    }

    public static void main(String[] args) {
        squareFreeSubsets(new int[]{1, 1, 1});
    }

}
