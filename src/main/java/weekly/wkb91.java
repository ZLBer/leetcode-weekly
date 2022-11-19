package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wkb91 {
    //ranking: 400 / 3535
    //排序+hash
    public int distinctAverages(int[] nums) {
        Arrays.sort(nums);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length / 2; i++) {
            set.add(nums[i] + nums[nums.length - 1 - i]);
        }
        return set.size();
    }

    // dp
    public int countGoodStrings(int low, int high, int zero, int one) {
        long[] dp = new long[high + 1];
        int mod = (int) 1e9 + 7;
        dp[0] = 1;
        for (int i = 0; i < dp.length; i++) {
            if (i - zero > 0) dp[i] += dp[i - zero];
            if (i - one > 0) dp[i] += dp[i - one];
            dp[i] %= mod;
        }
        long res = 0;
        for (int i = low; i <= high; i++) {
            res += dp[i];
            res %= mod;
        }
        return (int) res;
    }


    //两次dfs
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        arrivedTime = new HashMap<>();
        helpBob(0, -1, bob, 0, map);

        return helpAlice(0, -1, 0, map, amount);
    }

    int bobStep;
    Map<Integer, Integer> arrivedTime;

     //bob的路线是固定的，计算bob打到每个节点的步数
    boolean helpBob(int cur, int pre, int bob, int step, Map<Integer, List<Integer>> map) {
        if (cur == bob) {
            arrivedTime.put(cur, step);
            bobStep = step;
            return true;
        }

        boolean flag = false;
        for (Integer next : map.getOrDefault(cur, new ArrayList<>())) {
            if (next == pre) continue;
            flag |= helpBob(next, cur, bob, step + 1, map);
        }
        if (flag) {
            arrivedTime.put(cur, step);
        }
        return flag;
    }

    //求alice达到每个叶子结点的分数，求最大
    int helpAlice(int cur, int pre, int step, Map<Integer, List<Integer>> map, int[] amount) {
        int score = 0;
        if (arrivedTime.containsKey(cur)) {
            int time = bobStep - arrivedTime.get(cur);
            //比bob先打到
            if (time > step) {
                score += amount[cur];
                //一起到达
            } else if (time == step) {
                score += amount[cur] / 2;
            }
            //bob没有经过
        } else {
            score += amount[cur];
        }

        int max = Integer.MIN_VALUE;
        for (Integer next : map.getOrDefault(cur, new ArrayList<>())) {
            if (next == pre) continue;
            max = Math.max(max, helpAlice(next, cur, step + 1, map, amount));
        }
        //这里表示没有路了
        if (max == Integer.MIN_VALUE) {
            max = 0;
        }
        return score + max;
    }


    //写的好麻烦，本质是找规律
    public String[] splitMessage(String message, int limit) {
        int length = message.length();
        int number = check(length, limit);
        if (number == -1) {
            return new String[]{};
        }
        int l = (number + "").length();
        String[] res = new String[number];
        int begin = 0;
        for (int i = 1; i <= number; i++) {
            int r = (i + "").length();
            int end = begin + (limit - 3 - r - l);
            res[i - 1] = message.substring(begin, Math.min(message.length(), end)) + "<" + i + "/" + number + ">";
            begin = end;
        }
        return res;
    }

    //计算要分成多少组
    int check(int len, int limit) {
        for (int i = 1; i < arr.length + 1; i++) {
            long sum = 0;
            for (int j = 0; j < i; j++) {
                sum += (long) arr[j] * (limit - i - j - 1 - 3);
            }
            if (sum >= len) {
                int count=0;
                for (int j = 0; j < i - 1; j++) {
                    count+=arr[j];
                    len -= arr[j] * (limit - i - j - 1 - 3);
                }
                int l = len / (limit - i  - i - 3);
                if (l * (limit - i - i - 3) != len) {
                    l++;
                }
                return count + l;
            }
        }
        return -1;
    }

    int[] arr = new int[]{9, 90, 900, 9000, 90000, 900000};

//    public static void main(String[] args) {
//        wk319 w = new wk319();
//        w.check(10000, 12);
//        w.splitMessage("this is really a very awesome message", 9);
//    }
}
