package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wk404 {

    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(help(red, blue), help(blue, red));
    }

    int help(int a, int b) {
        int i = 1;
        while (true) {
            if (i % 2 == 1) {
                a -= i;
            } else {
                b -= i;
            }
            if (a < 0 || b < 0) return i - 1;
            i++;
        }
    }


    static public int maximumLength(int[] nums) {

        int pre0 = -1, pre1 = -1;
        int[][] dp = new int[nums.length][2];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int d = nums[i] % 2;
            if (d == 0) {
                dp[i][0] = pre0 == -1 ? 1 : dp[pre0][0] + 1;
                dp[i][1] = pre1 == -1 ? 1 : dp[pre1][1] + 1;
                pre0 = i;
            } else if (d == 1) {
                dp[i][0] = pre1 == -1 ? 1 : dp[pre1][0] + 1;
                dp[i][1] = pre0 == -1 ? 1 : dp[pre0][1] + 1;
                pre1 = i;
            }
            res = Math.max(res, Math.max(dp[i][0], dp[i][1]));
        }
        return res;
    }


    // dp
  /*  public int maximumLength(int[] nums, int k) {

        int[] pre = new int[k];
        Arrays.fill(pre, -1);
        int[][] dp = new int[nums.length][k];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int d = nums[i] % k;
            for (int j = 0; j < k; j++) {
                int p = (k - d - j+k) % k;
                dp[i][j] = pre[p] == -1 ? 1 : dp[pre[p]][j] + 1;
                res = Math.max(res, dp[i][j]);
            }
            pre[d] = i;
        }
        return res;
    }*/


    // 利用同余性质dp
    public int maximumLength(int[] nums, int k) {
        int ans = 0;
        int[][] f = new int[k][k];
        for (int x : nums) {
            x %= k;
            for (int y = 0; y < k; y++) {
                f[y][x] = f[x][y] + 1;
                ans = Math.max(ans, f[y][x]);
            }
        }
        return ans;
    }


    // 树直径的性质
    public int minimumDiameterAfterMerge(int[][] edges1, int[][] edges2) {
        int d1 = diameter(edges1);
        int d2 = diameter(edges2);
        return Math.max(Math.max(d1, d2), (d1 + 1) / 2 + (d2 + 1) / 2 + 1);
    }

    private int res;

    private int diameter(int[][] edges) {
        List<Integer>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        res = 0;
        dfs(0, -1, g);
        return res;
    }

    private int dfs(int x, int fa, List<Integer>[] g) {
        int maxLen = 0;
        for (int y : g[x]) {
            if (y != fa) {
                int subLen = dfs(y, x, g) + 1;
                res = Math.max(res, maxLen + subLen);
                maxLen = Math.max(maxLen, subLen);
            }
        }
        return maxLen;
    }


    public static void main(String[] args) {
        maximumLength(new int[]{1, 3});
    }
}
