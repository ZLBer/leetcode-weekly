package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class wk309 {


    //ranking: 357 / 7972

    //简单题，数组记录字母的位置
    public boolean checkDistances(String s, int[] distance) {
        int[] count = new int[26];
        Arrays.fill(count, -1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count[c - 'a'] == -1) {
                count[c - 'a'] = i;
            } else {
                count[c - 'a'] = i - count[c - 'a'] - 1;
            }
        }
        for (int i = 0; i < distance.length; i++) {
            if (count[i] == -1) continue;
            if (count[i] != distance[i]) return false;
        }
        return true;
    }


    //中等题，dp,模拟k次移动，时间复杂度 k*3000,也可以记忆化搜索
    public int numberOfWays(int startPos, int endPos, int k) {
        int mod = (int) 1e9 + 7;
        int MAX = 3002;
        long[] dp = new long[MAX];
        dp[startPos + 1000] = 1;
        for (int i = 0; i < k; i++) {
            long[] ndp = new long[MAX];
            for (int j = 0; j < MAX; j++) {
                int left = j - 1;
                int right = j + 1;
                if (left >= 0) ndp[j] += dp[left];
                ndp[j] %= mod;
                if (right < MAX) ndp[j] += dp[right];
                ndp[j] %= mod;
            }
            dp = ndp;
        }

        return (int) (dp[endPos + 1000] % mod);

    }


    //中等题，滑动窗口
   /* static public int longestNiceSubarray(int[] nums) {

        int left = 0;
        int ans = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i - 1; j >= left; j--) {
                if ((nums[j] & nums[i]) != 0) {
                    left = j + 1;
                    break;
                }
            }
            ans = Math.max(i - left + 1, ans);
            // System.out.println(ans);
        }
        return ans;
    }*/

    //优雅的滑动窗口，没有bit位重复
    static public int longestNiceSubarray(int[] nums) {
        int left = 0;
        int or = 0;
        int ans = 1;
        for (int i = 0; i < nums.length; i++) {
            while ((or & nums[i]) > 0) {
                or ^= nums[left++];//去掉nums[left]的bit位
            }
            ans = Math.max(i - left + 1, ans);
            or |= nums[i];//记录nums[i]的bit位
        }
        return ans;
    }


    //困难题，两个堆：一个记录没被占用的会议室，一个记录在开会的会议室
    static public int mostBooked(int n, int[][] meetings) {
        //先排序
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
        //记录每个会议室的使用次数
        int[] count = new int[n];
        //记录{开始时间，房间号}，每次弹出开始时间最小，房间号最小的会议室
        PriorityQueue<int[]> inuse = new PriorityQueue<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        PriorityQueue<Integer> notUse = new PriorityQueue<>();
        //一开始全部没有使用
        for (int i = 0; i < n; i++) {
            notUse.add(i);
        }
        for (int[] meeting : meetings) {
            //腾出会议室
            while (!inuse.isEmpty() && inuse.peek()[0] <= meeting[0]) {
                int[] poll = inuse.poll();
                notUse.add(poll[1]);
            }
            //有空闲会议室，找出最小的
            if (!notUse.isEmpty()) {
                Integer room = notUse.poll();
                count[room]++;
                inuse.add(new int[]{meeting[1], room});
                //无空闲会议室，找出最先结束的
            } else {
                int[] room = inuse.poll();
                inuse.add(new int[]{room[0] + meeting[1] - meeting[0], room[1]});
                count[room[1]]++;
            }
        }
        int max = 0;
        int ans = 0;
        for (int i = 0; i < count.length; i++) {
            //System.out.println(count[i]);
            if (count[i] > max) {
                max = count[i];
                ans = i;
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        mostBooked(3, new int[][]{
                {0, 10}, {1, 9}, {2, 8}, {3, 7}, {4, 6}
        });
    }


}
