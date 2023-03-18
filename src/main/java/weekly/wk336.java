package weekly;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wk336 {
    //模拟
    public int vowelStrings(String[] words, int left, int right) {
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        int ans = 0;
        for (int i = left; i <= right; i++) {
            String word = words[i];
            if (set.contains(word.charAt(0)) && set.contains(word.charAt(word.length() - 1))) {
                ans++;
            }
        }
        return ans;
    }


    //排序
    public int maxScore(int[] nums) {
        Arrays.sort(nums);
        long sum = 0;
        int ans = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            sum += nums[i];
            if (sum > 0) {
                ans++;
            } else {
                return ans;
            }
        }
        return ans;
    }

    //异或前缀和
    public long beautifulSubarrays(int[] nums) {
        long ans = 0;
        int pre = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre ^= nums[i];
            if (map.containsKey(pre)) {
                Integer val = map.get(pre);
                ans += val;
                map.put(pre, val + 1);
            } else {
                map.put(pre, 1);
            }
        }
        return ans;
    }

    //贪心 任务尽量在后面完成
    //也可以线段树
   static public int findMinimumTime(int[][] tasks) {
        Arrays.sort(tasks, (a, b) -> a[1] != b[1] ? a[1] - b[1] : b[0] - a[0]);
        int ans = 0;
        boolean[] dp = new boolean[2001];
        for (int i = 0; i < tasks.length; i++) {
            int[] task = tasks[i];
            int start = task[0];
            int end = task[1];
            int duration = task[2];
            for (int j = start; j <= end; j++) {
                if (dp[j]) {
                    duration--;
                }
            }
            if (duration > 0) {
                for (int j = end; j >= start; j--) {
                    if (!dp[j]) {
                        duration--;
                        dp[j] = true;
                        ans++;
                    }
                    if (duration == 0) break;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        findMinimumTime(new int[][]{
                {2,3,1},{4,5,1},{1,5,2}
        });
    }
}


