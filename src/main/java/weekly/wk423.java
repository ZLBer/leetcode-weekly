package weekly;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class wk423 {

  /*  public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int[] left = new int[nums.size()];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.size(); i++) {
            int cur = nums.get(i);
            while (!deque.isEmpty() && nums.get(deque.peekLast()) >= cur) {
                deque.pollLast();
            }
            left[i] = deque.size() + 1;
            deque.add(i);

        }
        int[] right = new int[nums.size()];
        deque.clear();
        for (int i = nums.size() - 1; i >= 0; i--) {
            int cur = nums.get(i);
            while (!deque.isEmpty() && nums.get(deque.peekLast()) <= cur) {
                deque.pollLast();
            }
            right[i] = deque.size() + 1;
            deque.add(i);

        }

        for (int i = 0; i < left.length-1; i++) {
            System.out.println(left[i]+" "+right[i+1]);
            if(left[i]>=k&&right[i+1]>=k){
                return true;
            }
        }
        return false;

    }*/


    //前缀和  后缀和
//    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
//         int []left=new int[nums.size()];
//         int []right=new int[nums.size()];
//         left[0]=1;
//        for (int i = 1; i < nums.size(); i++) {
//            if(nums.get(i)>nums.get(i-1)){
//                left[i]=left[i-1]+1;
//            }else {
//                left[i]=1;
//            }
//        }
//
//        right[nums.size()-1]=1;
//        for (int i = nums.size() - 2; i >= 0; i--) {
//            if(nums.get(i)<nums.get(i+1)){
//                right[i]=right[i+1]+1;
//            }else {
//                right[i]=1;
//            }
//        }
//        int ans=0;
//        for (int i = 0; i < left.length-1; i++) {
//            System.out.println(left[i]+" "+right[i+1]);
//            ans=Math.max(ans, Math.min(left[i],right[i+1]));
//
//        }
//        return ans;
//    }

    public int maxIncreasingSubarrays(List<Integer> nums) {
        int[] left = new int[nums.size()];
        int[] right = new int[nums.size()];
        left[0] = 1;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        right[nums.size() - 1] = 1;
        for (int i = nums.size() - 2; i >= 0; i--) {
            if (nums.get(i) < nums.get(i + 1)) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < left.length - 1; i++) {
            ans = Math.max(ans, Math.min(left[i], right[i + 1]));
        }
        return ans;
    }


    // 计数dp
    static public int sumOfGoodSubsequences(int[] nums) {
        int max = (int) 1e5 + 7;
        long[] dp = new long[max];
        long[] count = new long[max];
        int mod = (int) 1e9 + 7;
        for (int i = 0; i < nums.length; i++) {
            int index = nums[i];
            // 自己作为子序列
            dp[index] = (dp[index] + nums[i]) % mod;
            count[index]++;
            if (count[index + 1] > 0) {
                dp[index] = (dp[index] + dp[index + 1] + count[index + 1] * nums[i]) % mod;
                count[index]+=count[index+1];
                count[index]%=mod;
            }
            if (index - 1 >= 0 && count[index - 1] > 0) {
                dp[index] = (dp[index] + dp[index - 1] + count[index - 1] * nums[i]) % mod;
                count[index]+=count[index-1];
                count[index]%=mod;
            }
        }

        long ans = 0;
        for (int i = 0; i < dp.length; i++) {
            ans += dp[i];
            ans %= mod;
        }
        return (int)ans;
    }

    private static final int MOD = 1_000_000_007;

    public int countKReducibleNumbers(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        long ans = 0;
        int[] f = new int[n];
        for (int i = 1; i < n; i++) {
            f[i] = f[Integer.bitCount(i)] + 1;
            if (f[i] <= k) {
                // 计算有多少个二进制数恰好有 i 个 1
                ans += dfs(0, i, true, s, memo);
            }
        }
        return (int) (ans % MOD);
    }

    private int dfs(int i, int left1, boolean isLimit, char[] s, int[][] memo) {
        if (i == s.length) {
            return !isLimit && left1 == 0 ? 1 : 0;
        }
        if (!isLimit && memo[i][left1] != -1) {
            return memo[i][left1];
        }
        int up = isLimit ? s[i] - '0' : 1;
        int res = 0;
        for (int d = 0; d <= Math.min(up, left1); d++) {
            res = (res + dfs(i + 1, left1 - d, isLimit && d == up, s, memo)) % MOD;
        }
        if (!isLimit) {
            memo[i][left1] = res;
        }
        return res;
    }


    public static void main(String[] args) {
        sumOfGoodSubsequences(new int[]{0,1});
    }


}
