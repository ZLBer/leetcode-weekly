package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk305 {
    //ranking: 903 / 7465

    //简单题，遍历即可
    public int arithmeticTriplets(int[] nums, int diff) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[j] - nums[i] == diff && nums[k] - nums[j] == diff) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }


    //中等题，bfs+限制
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        Set<Integer> set = new HashSet<>();
        for (int i : restricted) {
            set.add(i);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        int ans = 1;
        boolean[] visited = new boolean[n];
        visited[0] = true;

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (Integer next : map.getOrDefault(poll, new ArrayList<>())) {
                if (visited[next] || set.contains(next)) continue;
                visited[next] = true;
                queue.add(next);
                ans++;
            }
        }
        return ans;

    }

    /* public boolean validPartition(int[] nums) {
         memo = new int[nums.length];
         return help(nums, 0);
     }

     int[] memo;

     boolean help(int[] nums, int index) {
         if (index >= nums.length) return true;

         if (memo[index] != 0) {
             return memo[index] == 1;
         }
         int end = index + 1;
         boolean a = false, b = false, c = false;
         if (end < nums.length && nums[index] == nums[end]) {
             a = help(nums, end + 1);
         }
         end++;
         if (end < nums.length && nums[index] == nums[index + 1] && nums[index + 1] == nums[end]) {
             b = help(nums, end + 1);
         }
         if (end < nums.length && nums[index] + 1 == nums[index + 1] && nums[index + 1] + 1 == nums[end]) {
             c = help(nums, end + 1);
         }
         boolean res = a | b | c;
         if (res) {
             memo[index] = 1;
         } else {
             memo[index] = -1;
         }
      return res;
     }*/

    //中等题，暴力dp，不要想什么贪心做发 - -！
    public boolean validPartition(int[] nums) {
        boolean[] dp = new boolean[nums.length + 1];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++) {
            int start = i - 1;
            if (start < 0) continue;
            if (nums[start] == nums[i]) {//两个相同
                dp[i + 1] |= dp[start];
            }
            start--;
            if (start < 0) continue;
            if (nums[start] == nums[i - 1] && nums[start] == nums[i]) {//三个相同
                dp[i + 1] |= dp[start];
            }
            if (nums[start] + 1 == nums[i - 1] && nums[i - 1] + 1 == nums[i]) {//三个相差1
                dp[i + 1] |= dp[start];
            }
        }
        return dp[nums.length];
    }

    //中等题，直接一维dp
    public int longestIdealString(String s, int k) {
        char[] chars = s.toCharArray();
        int[] dp = new int[s.length()];
        int[] index = new int[26];//记录字母的位置
        Arrays.fill(index, -1);//初试为-1都不存在
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            char num = chars[i];
            dp[i] = 1;
            //j表示相差k的字母
            for (int j = Math.max(0, num - 'a' - k); j <= Math.min(25, num - 'a' + k); j++) {
                if (index[j] < 0) continue;
                //更新dp[i], 可到达的字母+1
                dp[i] = Math.max(dp[index[j]] + 1, dp[i]);//
            }
            index[num - 'a'] = i;
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    //或者直接dp[26]
  /*  public int longestIdealString(String s, int k) {
        char[] chars = s.toCharArray();
        int[] dp = new int[26];
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            char num = chars[i];
            //j表示相差k的字母
            for (int j = Math.max(0, num - 'a' - k); j <= Math.min(25, num - 'a' + k); j++) {
                dp[num-'a'] = Math.max(dp[j], dp[num-'a']);
            }
            dp[num-'a'] ++;
            max = Math.max(max, dp[i]);
        }
        return max;

    }*/
}