package weekly;

import java.util.Arrays;

public class wk381 {

    // 贪心
    public int minimumPushes(String word) {
        int ans = 0;
        int[] counter = new int[26];
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            counter[c - 'a']++;
        }
        Arrays.sort(counter);

        int[] c = new int[4];
        Arrays.fill(c, 8);
        for (int i = counter.length - 1; i >= 0; i--) {
            if (counter[i] == 0) continue;
            for (int j = 0; j < c.length; j++) {
                if (c[j] == 0) continue;
                else {
                    c[j]--;
                    ans += (j + 1) * counter[i];
                    break;
                }

            }
        }

        return ans;
    }

    //floyd算法
//    public int[] countOfPairs(int n, int x, int y) {
//        int[] res = new int[n];
//        if (y < x) {
//            int t = y;
//            y = x;
//            x = t;
//        }
//
//        int f[][] = new int[n][n];
//        for (int[] ints : f) {
//            Arrays.fill(ints, Integer.MAX_VALUE / 2);
//        }
//        for (int i = 0; i < f.length; i++) {
//            f[i][i] = 0;
//        }
//
//        for (int i = 1; i < f.length; i++) {
//            f[i][i - 1] = 1;
//            f[i - 1][i] = 1;
//        }
//        f[x-1][y-1] = 1;
//        f[y-1][x-1] = 1;
//        // Floyd计算剩下节点的最短路径
//        for (int k = 0; k < n; k++) {
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < n; j++) {
//                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
//                }
//            }
//        }
//
//
//        for (int i = 1; i <= n; i++) {
//            for (int j = i + 1; j <= n; j++) {
//                int k = f[i-1][j-1];
//               res[k-1]++;
//            }
//        }
//        for (int i = 0; i < res.length; i++) {
//            res[i] *= 2;
//        }
//        return res;
//    }


    //超时过不了了
    public long[] countOfPairs(int n, int x, int y) {
        long[] res = new long[n];
        if (y < x) {
            int t = y;
            y = x;
            x = t;
        }

        int f[][] = new int[n][n];
        for (int[] ints : f) {
            Arrays.fill(ints, Integer.MAX_VALUE / 2);
        }
        for (int i = 0; i < f.length; i++) {
            f[i][i] = 0;
        }

        for (int i = 1; i < f.length; i++) {
            f[i][i - 1] = 1;
            f[i - 1][i] = 1;
        }
        f[x-1][y-1] = 1;
        f[y-1][x-1] = 1;
        // Floyd计算剩下节点的最短路径
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
                }
            }
        }


        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                int k = f[i-1][j-1];
                res[k-1]++;
            }
        }
        for (int i = 0; i < res.length; i++) {
            res[i] *= 2;
        }
        return res;
    }

    public static void main(String[] args) {
        wk381 w = new wk381();
        w.countOfPairs(5, 1, 5);
    }
}
