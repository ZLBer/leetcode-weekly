package weekly;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class wk360 {


    //贪心
    public int furthestDistanceFromOrigin(String moves) {
        int l = 0, r = 0, black = 0;
        for (int i = 0; i < moves.length(); i++) {
            char c = moves.charAt(i);
            if (c == 'L') {
                l++;
            } else if (c == 'R') {
                r++;
            } else {
                black++;
            }
        }

        return Math.max(l, r) - Math.min(l, r) + black;
    }


    //哈希
    public long minimumPossibleSum(int n, int target) {
        Set<Integer> set = new HashSet<>();
        int num = 1;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            while (set.contains(target - num)) {
                num++;
            }
            set.add(num);
            ans += num;
            num++;
        }
        return ans;
    }


    //贪心+二进制
    public int minOperations(List<Integer> nums, int target) {
        int[] count = new int[32];
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            for (int j = 0; j <= 30; j++) {
                count[j] += (num >> j) & 1;
            }
        }

        int ans = 0;
        for (int i = 0; i <= 30; i++) {
            if (((target >> i) & 1) > 0) {
                count[i]--;
                for (int j = i; count[j] < 0; j++) {
                    if (j == 31) {
                        return -1;
                    }
                    count[j] += 2;
                    count[j + 1]--;
                    ans++;
                }
            }
            //进位
            count[i + 1] += count[i] / 2;
        }
        return ans;
    }


    //树上倍增
    public long getMaxFunctionValue(List<Integer> receiver, long k) {
        //dp[i][j]表示j位置2的i次方可以走到哪里
        int[][] dp = new int[35][receiver.size()];
        long sum[][] = new long[35][receiver.size()], max = 0;
        //初始化 走1步能到的位置，以及和
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = receiver.get(i);
            sum[0][i] = receiver.get(i);
        }

        //倍增求2的n次方的位置和sum
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                //从j走2的i次方为 从j走2的i-1次方到达x，然后自从x走2的i-1次方
                dp[i][j] = dp[i - 1][dp[i - 1][j]];
                sum[i][j] = sum[i - 1][j] + sum[i - 1][dp[i - 1][j]];
            }
        }

        //遍历每个点求k步的最大值
        for (int i = 0; i < dp[0].length; i++) {
            long curr = i;
            for (int j = 0, l = i; j < dp.length; j++) {
                if ((k >> j & 1) > 0) {
                    max = Math.max(max, curr += sum[j][l]);
                    l = dp[j][l];
                }
            }
        }
        return max;
    }


}
