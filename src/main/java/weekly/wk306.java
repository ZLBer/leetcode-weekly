package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class wk306 {
    //简单题，暴力卷积即可
    public int[][] largestLocal(int[][] grid) {
        int n = grid.length;
        int[][] res = new int[n - 2][n - 2];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                res[i][j] = findMax(i, j, grid);
            }
        }
        return res;
    }

    int findMax(int a, int b, int[][] grid) {
        int max = 0;
        for (int i = a; i < a + 3; i++) {
            for (int j = b; j < b + 3; j++) {
                max = Math.max(grid[i][j], max);
            }
        }
        return max;
    }

    //中等题，统计入度
    public int edgeScore(int[] edges) {
        long[] score = new long[edges.length];
        for (int i = 0; i < edges.length; i++) {
            score[edges[i]] += i;
        }
        int p = -1;
        long s = -1;
        for (int i = 0; i < score.length; i++) {
            if (score[i] > s) {
                p = i;
                s = score[i];
            }
        }
        return p;
    }

    //中等题，dfs回溯
  /*  public String smallestNumber(String pattern) {
        String s = "";
        for (int i = 1; i <= 9; i++) {
            boolean[] booleans = new boolean[10];
            booleans[i] = true;
            String ss = help(0, pattern, booleans, i + "");
            if(ss.equals(""))continue;
            if ("".equals(s) || s.compareTo(ss) > 0) {
                s = ss;
            }
        }
        return s;
    }

  //  Map<String, String> memo = new HashMap<>();

    String help(int index, String pattern, boolean[] visited, String s) {
        if (index >= pattern.length()) {
            return s;
        }
       *//* if (memo.containsKey(s)) {
            return memo.get(s);
        }*//*
        int cur = s.charAt(s.length() - 1) - '0';
        boolean up = pattern.charAt(index) == 'I';
        String need = "";
        for (int i = 1; i <= 9; i++) {
            if (visited[i]) continue;
            if (i > cur == up) {
                visited[i] = true;
                String ss = help(index + 1, pattern, visited, s + i);
                if (!"".equals(ss)) {
                    if ("".equals(need)) {
                        need = ss;
                    } else {
                        if (ss.compareTo(need) < 0) {
                            need = ss;
                        }
                    }
                }
                visited[i] = false;
            }
        }
     //   memo.put(s, need);
        return need;

    }*/

    //用栈来做
    public String smallestNumber(String pattern) {
        Deque<Integer> deque=new ArrayDeque<>();

        int num=1;
        StringBuilder sb=new StringBuilder();
        for (char c : pattern.toCharArray()) {
            if (c == 'D') {
                deque.addLast(num++);
            }else {
                deque.addLast(num++);
                while (!deque.isEmpty()){
                     sb.append(deque.pollLast());
                }
            }
        }
        deque.addLast(num);
        while (!deque.isEmpty()){
            sb.append(deque.pollLast());
        }
        return sb.toString();
    }


    // f[l][r] 代表 i * (i + 1) * ... * (j - 1) * j
 /*   static int[][] f = new int[10][10];
    static {
        for (int i = 1; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                int cur = 1;
                for (int k = i; k <= j; k++) cur *= k;
                f[i][j] = cur;
            }
        }
    }
    int dp(int x) {
        int t = x;
        List<Integer> nums = new ArrayList<>();
        while (t != 0) {
            nums.add(t % 10);
            t /= 10;
        }
        int n = nums.size();
        if (n <= 1) return x + 1;
        int ans = 0;
        for (int i = n - 1, p = 1, s = 0; i >= 0; i--, p++) {
            int cur = nums.get(i), cnt = 0;
            for (int j = cur - 1; j >= 0; j--) {
                if (i == n - 1 && j == 0) continue;
                if (((s >> j) & 1) == 0) cnt++;
            }
            int a = 10 - p, b = a - (n - p) + 1;
            ans += b <= a ? cnt * f[b][a] : cnt;
            if (((s >> cur) & 1) == 1) break;
            s |= (1 << cur);
            if (i == 0) ans++;
        }
        ans += 10;
        for (int i = 2, last = 9; i < n; i++) {
            int cur = last * (10 - i + 1);
            ans += cur; last = cur;
        }
        return ans;
    }
    public int countSpecialNumbers(int n) {
        return dp(n)-1;
    }*/



    //困难题，数位dp模板
    char s[];
    int dp[][];

    public int countSpecialNumbers(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        dp = new int[m][1 << 10];
        for (int i = 0; i < m; i++) Arrays.fill(dp[i], -1);
        return f(0, 0, true, false);
    }

    //i表示当前位置，mask表示哪些数字使用了，islimit表示是否限制最大到9，isNum表示之前是否有数字
    int f(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) return isNum ? 1 : 0;
        if (!isLimit && isNum && dp[i][mask] >= 0) return dp[i][mask];
        int res = 0;
        //之前没有数字的时候，可以直接去下面查找没有数字的，且没有限制，没有数字
        if (!isNum) res = f(i + 1, mask, false, false); // 可以跳过当前数位
        //前面没有数字时，不可以从0开始。 没有限制时，可以到9
        for (int d = isNum ? 0 : 1, up = isLimit ? s[i] - '0' : 9; d <= up; ++d) // 枚举要填入的数字 d
            if ((mask >> d & 1) == 0) // d 不在 mask 中
                 //isLimit && d == up表示之前有限制并且当前位是最大值，则继续限制
                res += f(i + 1, mask | (1 << d), isLimit && d == up, true);
        //只缓存没有限制，且前面有数字的情况，只有这种情况才会重复计算  例如 xx23
        if (!isLimit && isNum) dp[i][mask] = res;
        return res;
    }



}
