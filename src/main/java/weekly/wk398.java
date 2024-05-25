package weekly;

import java.util.Arrays;
import java.util.HashMap;

public class wk398 {


    //遍历
    public boolean isArraySpecial(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] % 2 == nums[i - 1] % 2) {
                return false;
            }
        }
        return true;
    }


    //前缀和
    static public boolean[] isArraySpecial(int[] nums, int[][] queries) {

        int[] dp = new int[nums.length + 1];
        int index = 1;
        dp[1] = index;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] % 2 == nums[i - 1] % 2) {
                index++;
            }
            dp[i + 1] = index;
        }

        int[] pre = new int[nums.length + 1];
        for (int i = 1; i < dp.length; i++) {
            pre[i] = pre[i - 1] + dp[i];
        }


        boolean[] res = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            int c = pre[query[1] + 1] - pre[query[0]];
            if (c == dp[query[0] + 1] * (query[1] - query[0] + 1)) {
                res[i] = true;
            }
        }
        return res;
    }


    //遍历
    public long sumDigitDifferences(int[] nums) {
        int d = 1;
        long res = 0;
        while (nums[0] / d > 0) {
            int[] count = new int[10];
            long tmp = 1;
            for (int num : nums) {
                int c = (num / d) % 10;
                count[c]++;
            }
            for (int i = 0; i < count.length; i++) {
                for (int j = i + 1; j < count.length; j++) {
                    res += (long) count[i] * count[j];
                }
            }

            d *= 10;
        }
        return res;
    }


    // 记忆化
  /*  public int waysToReachStair(int k) {
        return dfs(1, k, 0, 0);

    }

    HashMap<Long,Integer> memo=new HashMap<>();


    int dfs(int cur, int k, int jump, int back) {
        if (cur > k + 1) return 0;
        long key=((long)cur<<32)|((long) jump<<1)|back;
        if (memo.containsKey(key)) return memo.get(key);
        int res = 0;
        if (cur == k) res++;
        if (back == 0 && cur > 0) {
            res += dfs(cur - 1, k, jump, 1);
        }
        res += dfs(cur + (int) Math.pow(2, jump), k, jump + 1, 0);
        memo.put(key,res);
        return res;
    }
*/

    static int[][] dp = new int[32][2];

    static {
        int a = 1;
        dp[0][1] = 1;
        for (int i = 0; i < 31; i++) {
            dp[i + 1][0] = dp[i][0] + a;
            dp[i + 1][1] = i + 2;
            a *= 2;
        }
//        for (int i = 0; i < dp.length; i++) {
//            System.out.println(dp[i][0] + " " + dp[i][1]);
//        }
    }

    // 组合数
    public int waysToReachStair(int k) {
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            if (k <= 1+dp[i][0] &&k>=1+dp[i][0]-dp[i][1]) {
                int dis= dp[i][1]-(1+dp[i][0]-k);
               res+=comnination(dp[i][1],dis);
            }
        }
        return res;
    }

    // m>=n
    public int comnination(int m, int n) {
        if (n == 0) return 1;
        long ans = 1;

        //都从小的开始 防止过早溢出
        for (int x = m - n + 1, y = 1; y <= n; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }

    public static void main(String[] args) {
        wk398 w = new wk398();
        w.waysToReachStair(1);
    }
}
