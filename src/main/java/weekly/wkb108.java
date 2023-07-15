package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class wkb108 {


    //模拟
    public int alternatingSubarray(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int flag = 1;
            int j = i + 1;
            for (; j < nums.length; j++) {
                if (nums[j] - nums[j - 1] != flag) {
                    break;
                }
                flag *= -1;
            }
            ans = Math.max(ans, j - i);
        }
        return ans == 1 ? -1 : ans;
    }


    //哈希
    public List<Integer> relocateMarbles(int[] nums, int[] moveFrom, int[] moveTo) {
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }

        for (int i = 0; i < moveFrom.length; i++) {
            set.remove(moveFrom[i]);
            set.add(moveTo[i]);
        }

        List<Integer> res = new ArrayList<>(set);
        return res;
    }


    //dp
    public int minimumBeautifulSubstrings(String s) {
        int[] dp = new int[s.length() + 1];
        Arrays.fill(dp, (int) 1e9 + 7);
        dp[0] = 0;
        Set<String> set = new HashSet<>();
        int ans = 1;
        //先求出5的幂的2进制字符串
        for (int i = 0; i < 12; i++) {
            set.add(Integer.toBinaryString(ans));
            ans *= 5;
        }

        //dp枚举
        for (int i = 0; i < s.length(); i++) {
            for (int start = 0; start <= i; start++) {
                String sub = s.substring(start, i + 1);
                if (sub.startsWith("0")) continue;
                if (!set.contains(sub)) continue;
                dp[i + 1] = Math.min(dp[start] + 1, dp[i + 1]);
            }
        }
        return dp[s.length()] == (int) 1e7 ? -1 : dp[s.length()];
    }


    //枚举黑格子,每个格子只对四个有贡献，一次求对他们的贡献
    public long[] countBlackBlocks(int m, int n, int[][] coordinates) {
        Map<Long, Integer> map = new HashMap<>();
        for (int[] coordinate : coordinates) {
            //左上角
            int x = coordinate[0] - 1, y = coordinate[1] - 1;
            if (x >= 0 && y >= 0 && x < m && y < n) {
                long ans = (long) x * n + y;
                map.put(ans, map.getOrDefault(ans, 0) + 1);
            }
            //右上角
            x = coordinate[0] - 1;
            y = coordinate[1] + 1;
            if (x >= 0 && y >= 0 && x < m && y < n) {
                long ans = (long) x * n + y - 1;
                map.put(ans, map.getOrDefault(ans, 0) + 1);
            }
            //左下角
            x = coordinate[0] + 1;
            y = coordinate[1] - 1;
            if (x >= 0 && y >= 0 && x < m && y < n) {
                long ans = (long) (x - 1) * n + y;
                map.put(ans, map.getOrDefault(ans, 0) + 1);

            }
            //右下角
            x = coordinate[0] + 1;
            y = coordinate[1] + 1;
            if (x >= 0 && y >= 0 && x < m && y < n) {
                long ans = (long) (x - 1) * n + y - 1;
                map.put(ans, map.getOrDefault(ans, 0) + 1);
            }
        }
        long[] res = new long[5];
        long c = 0;
        for (int value : map.values()) {
            res[value]++;
            c++;
        }
        res[0] = (long) (m - 1) * (n - 1) - c;
        return res;
    }


    public static void main(String[] args) {

    }
}
