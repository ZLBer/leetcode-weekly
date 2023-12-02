package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wkb118 {
    //遍历
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (words[i].contains(x + "")) {
                res.add(i);
            }
        }
        return res;
    }

    //分组
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int h = 1, maxH = 1;

        Arrays.sort(hBars);
        Arrays.sort(vBars);
        for (int i = 1; i < hBars.length; i++) {
            if (hBars[i] == hBars[i - 1] + 1) {
                h++;
            } else {
                h = 1;
            }
            maxH = Math.max(maxH, h);
        }

        int v = 1, maxV = 1;
        for (int i = 1; i < vBars.length; i++) {
            if (vBars[i] == vBars[i - 1] + 1) {
                v++;
            } else {
                v = 1;
            }
            maxV = Math.max(maxV, v);

        }
        System.out.println(maxH + " " + maxV);
        int max = Math.min(maxH, maxV) + 1;

        return max * max;
    }

  /*  static public int minimumCoins(int[] prices) {
        int[][] dp = new int[prices.length + 1][2];
        dp[1][0] = prices[0];
        dp[1][1] = (int) 1e9 + 7;
        for (int i = 1; i < prices.length; i++) {
            dp[i + 1][0] = Math.min(dp[i][0], dp[i][1]) + prices[i];
            dp[i + 1][1] = (int) 1e9 + 7;
            for (int j = 1; j <= (i + 1 - j); j++) {
                dp[i + 1][1] = Math.min(dp[i + 1 - j][0], dp[i + 1][1]);
            }
        }
        return Math.min(dp[prices.length][0], dp[prices.length][1]);
    }
*/
    //dp
    public int minimumCoins(int[] prices) {
        int n = prices.length;
        for (int i = (n + 1) / 2 - 1; i > 0; i--) {
            int mn = Integer.MAX_VALUE;
            for (int j = i; j <= i * 2; j++) {
                mn = Math.min(mn, prices[j]);
            }
            prices[i - 1] += mn;
        }
        return prices[0];
    }




    //dp+单调栈
    public int findMaximumLength(int[] nums) {
        int n = nums.length;
        //前缀和
        long[] s = new long[n + 1];
        // 递增数组
        int[] f = new int[n + 1];
        // 队尾最大数据
        long[] last = new long[n + 1];
        int[] q = new int[n + 1]; // 数组模拟队列
        int front = 0, rear = 0;
        for (int i = 1; i <= n; i++) {
            //计算前缀和
            s[i] = s[i - 1] + nums[i - 1];

            // 1. 去掉队首无用数据（计算转移时，直接取队首）
            while (front < rear && s[q[front + 1]] + last[q[front + 1]] <= s[i]) {
                front++;
            }

            // 2. 计算转移
            f[i] = f[q[front]] + 1;
            last[i] = s[i] - s[q[front]];

            // 3. 去掉队尾无用数据，保持单调递增栈
            while (rear >= front && s[q[rear]] + last[q[rear]] >= s[i] + last[i]) {
                rear--;
            }
            q[++rear] = i;
        }
        return f[n];
    }

}
