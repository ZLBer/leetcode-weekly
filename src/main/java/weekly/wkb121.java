package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wkb121 {
    //遍历
    public int missingInteger(int[] nums) {
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] + 1 == nums[i]) {
                sum += nums[i];
            } else {
                break;
            }
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        while (!set.contains(sum)) {
            sum++;
        }
        return sum;
    }


   /* public int minOperations(int[] nums, int k) {
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum ^= nums[i];
        }
        int ans = 0;
        while (k != 0 || sum != 0) {
            if ((k & 1) != (sum & 1)) {
                ans++;
            }

            k >>= 1;
            sum >>= 1;
        }
        return ans;
    }*/

    //更简洁的写法
    public int minOperations(int[] nums, int k) {
        for (int x : nums) {
            k ^= x;
        }
        return Integer.bitCount(k);
    }


  /*  //贪心
    public int minimumOperationsToMakeEqual(int x, int y) {
        return dfs(x, y);
    }


    Map<Integer,Integer> memo=new HashMap<>();
    int dfs(int x, int y) {
        if (x <= y) {
            return y - x;
        }

        if (memo.containsKey(x)) return memo.get(x);
        int min = Math.abs(x - y);

        int c1 = x % 11;
        int c2 = Math.abs(11 - x % 11);

        if (c1 < c2) {
            min = Math.min(min, c1 + 1 + dfs((x - c1) / 11, y));
        } else {
            min = Math.min(min, c2 + 1 + dfs((x + c2) / 11, y));
        }

        int c3 = x % 5;
        int c4 = Math.abs(5 - x % 5);

        if (c3 < c4) {
            min = Math.min(min, c3 + 1 + dfs((x - c3) / 5, y));
        } else {
            min = Math.min(min, c4 + 1 + dfs((x + c4) / 5, y));
        }

        return min;
    }*/


    //数位dp
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        return countDigitOne(finish, limit, s) - countDigitOne(start - 1, limit, s);
    }

    char s[];
    long dp[];

    public long countDigitOne(long n, int limit, String suffix) {
        String ss = Long.toString(n);
        while (ss.length() < suffix.length()) {
            ss = "0" + ss;
        }
        s = ss.toCharArray();
        dp = new long[ss.length()];
        Arrays.fill(dp, -1);
        return f(0, true, limit, suffix);
    }

    long f(int i, boolean isLimit, int limit, String suffix) {
        if (i == s.length) return 1;
        if (!isLimit && dp[i] >= 0) return dp[i];
        long res = 0;


        int up = isLimit ? s[i] - '0' : 9;
        if (i < s.length - suffix.length()) {
            for (int d = 0; d <= Math.min(up, limit); ++d) // 枚举要填入的数字 d
                res += f(i + 1, isLimit && d == up, limit, suffix);
        } else {
            //受到suffix约束
            int d = suffix.charAt(i - (s.length - suffix.length())) - '0';
            // 判断是不是可行
            if (d <= up) {
                res += f(i + 1, isLimit && d == up, limit, suffix);
            }
        }

        if (!isLimit) dp[i] = res;
        return res;
    }


    public static void main(String[] args) {
        wkb121 w = new wkb121();
        long l = w.countDigitOne(6000, 4, "124");
        w.numberOfPowerfulInt(1, 6000, 4, "124");
    }


}
