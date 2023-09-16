package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class wk362 {
    //暴力
  /*  public int numberOfPoints(List<List<Integer>> nums) {
        Set<Integer> set = new HashSet<>();
        for (List<Integer> num : nums) {
            int start = num.get(0), end = num.get(1);
            for (int i = start; i <= end; i++) {
                set.add(i);
            }
        }
        int ans = 0;
        for (int i = 1; i <= 100; i++) {
            if (set.contains(i)) {
                ans++;
            }
        }
        return ans;
    }
*/
    //差分数组
    public int numberOfPoints(List<List<Integer>> nums) {
        int[] res = new int[100 + 2];
        for (List<Integer> num : nums) {
            res[num.get(0)]++;
            res[num.get(1) + 1]--;
        }
        int ans = 0;
        for (int i = 1; i < res.length; i++) {
            res[i] += res[i - 1];
            if (res[i] > 0) ans++;
        }
        return ans;
    }

    //贪心 先斜着走
    public boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
        if (sx == fx && sy == fy) {
            if (t == 1) return false;
            return true;
        }
        //斜着走
        int min = Math.min(Math.abs(fx - sx), Math.abs(fy - sy));
        int sub = Math.abs(fx - sx) - min + Math.abs(fy - sy) - min;
        if (sub + min > t) {
            return false;
        }
        return true;
    }


    //全排列
    public int minimumMoves(int[][] grid) {
        List<int[]> zero = new ArrayList<>();
        List<int[]> more = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    zero.add(new int[]{i, j, 0});
                } else if (grid[i][j] > 1) {
                    for (int k = 1; k < grid[i][j]; k++) {
                        more.add(new int[]{i, j, 0});
                    }
                }
            }
        }

        return help(0, 0, zero, more);
    }


    int help(int len, int dp, List<int[]> zero, List<int[]> more) {
        if (len >= zero.size()) {
            return 0;
        }
        int[] z = zero.get(len);
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < more.size(); j++) {
            int[] m = more.get(j);
            if (m[2] == 1) {
                continue;
            }
            m[2] = 1;
            int ndp = dp | (1 << j);
            int c = 0;

            c = help(len + 1, ndp, zero, more) + Math.abs(z[0] - m[0]) + Math.abs(z[1] - m[1]);

            min = Math.min(min, c);
            m[2] = 0;
        }

        return min;
    }


    public int numberOfWays(String s, String t, long k) {
        int MOD = 1000000007;
        int n = s.length();
        long count = 0;

        for (int l = 1; l < n; l++) {
            String rotated = s.substring(n - l) + s.substring(0, n - l);
            if (rotated.equals(t)) {
                int gcd = gcd(l, n);
                int lcm = l * n / gcd;
                count = (count + (k * gcd) / lcm) % MOD;
            }
        }

        if (s.equals(t)) {
            count = (count + k) % MOD;
        }

        return (int) count;
    }

    public int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        wk362 w = new wk362();
        w.minimumMoves(new int[][]{
                {1, 2, 2}, {1, 1, 0}, {0, 1, 1}
        });
    }

}
