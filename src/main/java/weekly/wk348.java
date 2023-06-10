package weekly;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class wk348 {


    //哈希
    public int minimizedStringLength(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }
        return set.size();
    }


    //分类讨论吧
    public int semiOrderedPermutation(int[] nums) {
        int min = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                min = i;
            } else if (nums[i] == nums.length) {
                max = i;
            }
        }
        int ans = min - 1 + nums.length - max;
        if (max < min) {
            ans--;
        }
        return ans;
    }


    //逆着遍历，然后乘法快速计算
    static public long matrixSumQueries(int n, int[][] queries) {
        long ans = 0;

        Set<Integer> row = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        for (int i = queries.length - 1; i >= 0; i--) {
            int[] query = queries[i];
            if (query[0] == 0) {
                if (row.contains(query[1])) continue;
                ans += ((long) n - col.size()) * (long) query[2];
                row.add(query[1]);
            } else {
                if (col.contains(query[1])) continue;
                ans += ((long) n - row.size()) * (long) query[2];
                col.add(query[1]);
            }
        }

        return ans;
    }


    //数位dp
    int mod=(int)1e9+7;
    public int count(String num1, String num2, int minSum, int maxSum) {
        int ans = count(num2,maxSum,minSum) - count(num1,maxSum,minSum) + mod; // 避免负数
        int sum = 0;
        for (char c : num1.toCharArray()) sum += c - '0';
        if (minSum <= sum && sum <= maxSum) ans++; // x=num1 是合法的，补回来
        return ans % mod;
    }

    private int count(String S, int maxSum,int minSum) {
        char[] s = S.toCharArray();
        int n = s.length;
        int [][] memo = new int[n][Math.min(9 * n, maxSum) + 1];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示没有计算过
        return f(s, memo, 0, 0, true,maxSum,minSum);
    }

    private int f(char[] s, int[][] memo, int i, int sum, boolean isLimit, int maxSum,int minSum) {
        if (sum > maxSum) return 0;
        if (i >= s.length) return sum>=minSum?1:0;
        if (isLimit && memo[i][sum] != -1) return memo[i][sum];

        int up = isLimit ? s[i] - '0' : 9;
        int res=0;
        for (int u = 0; u <= up; u++) {
           res+=f(s,memo,i+1,sum+u,isLimit&&u==up,maxSum,minSum);
           res%=mod;
        }
       if(!isLimit) memo[i][sum]=res;
       return res;
    }

    public static void main(String[] args) {

    }


}
