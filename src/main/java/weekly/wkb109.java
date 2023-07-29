package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wkb109 {


    //模拟
    public boolean isGood(int[] nums) {
        Arrays.sort(nums);
        int pre = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != pre + 1) return false;
            pre = nums[i];
        }
        return nums[nums.length - 1] == nums.length - 1;
    }


    //模拟 记录诱因字母的位置
    public String sortVowels(String s) {
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('A');
        set.add('e');
        set.add('E');
        set.add('i');
        set.add('I');
        set.add('o');
        set.add('O');
        set.add('u');
        set.add('U');
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                list.add(new int[]{c, i});
            }
        }
        Collections.sort(list, (a, b) -> a[0] - b[0]);
        char[] chars = s.toCharArray();

        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            if (set.contains(chars[i])) {
                chars[i] = (char) (list.get(index++)[0]);
            }
        }
        return new String(chars);
    }


 /*   public long maxScore(int[] nums, int x) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                if (nums[i] % 2 == nums[j] % 2) {
                    dp[i] = Math.max(dp[i], dp[j] + nums[i]);
                } else {
                    dp[i] = Math.max(dp[i], dp[j] + nums[i] - x);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }*/

    // dp, 优化成奇偶两个参数
    public long maxScore(int[] nums, int x) {
        //one表示奇数结尾的得分和
        //two表示偶数纪委的得分和
        long one = 0, two = 0;
        if (nums[0] % 2 == 0) {
            two = nums[0];
            one = -(int) 1e9 + 7;
        } else {
            one = nums[0];
            two = -(int) 1e9 + 7;
        }
        long max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] % 2 == 0) {
                //更新偶数结尾
                two = Math.max(two, Math.max(two + nums[i], one + nums[i] - x));
            } else {
                //更新奇数结尾
                one = Math.max(one, Math.max(one + nums[i], two + nums[i] - x));
            }
            max = Math.max(max, Math.max(one, two));
        }
        return max;
    }

    //
//    public int numberOfWays(int n, int x) {
//        //先求出幂
//        List<Integer> list = new ArrayList<>();
//        for (int i = 1; i <= n; i++) {
//            int h = help(i, x);
//            if (h > n) {
//                break;
//            }
//            list.add(h);
//        }
//        long[][] memo = new long[n + 1][list.size()];
//        for (long[] longs : memo) {
//            Arrays.fill(longs, -1);
//        }
//        //记忆化搜索
//        return (int) dfs(n, 0, list, memo);
//    }

    int mod = (int) 1e9 + 7;

    long dfs(int n, int index, List<Integer> list, long[][] memo) {
        if (n == 0) {
            return 1;
        }
        //剪枝
        if (index >= list.size()) {
            return 0;
        }
        //剪枝
        if (list.get(index) > n) {
            return 0;
        }
        if (memo[n][index] != -1) {
            return memo[n][index];
        }
        long ans = 0;
        for (int i = index; i < list.size(); i++) {
            if (list.get(i) <= n) {
                ans += dfs(n - list.get(i), i + 1, list, memo);
            }
        }
        memo[n][index] = ans % mod;
        return memo[n][index];
    }

    int help(int i, int x) {
        int ans = 1;
        for (int j = 0; j < x; j++) {
            ans *= i;
        }
        return ans;
    }


    //用01背包做，优化成一维
    public int numberOfWays(int n, int x) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int h = help(i, x);
            if (h > n) {
                break;
            }
            list.add(h);
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (Integer num : list) {
            for (int j = n; j >= 0; j--) {
                if (j - num >= 0) {
                    dp[j] += dp[j - num];
                    dp[j] %= mod;
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        wkb109 w = new wkb109();
        w.numberOfWays(6, 1);
    }
}
