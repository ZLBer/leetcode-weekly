package weekly;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class wk318 {

    //ranking: 411 / 5670

    //直接做
    public int[] applyOperations(int[] nums) {

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
        }
        int[] ans = new int[nums.length];
        int i = 0;
        for (int num : nums) {
            if (num > 0) {
                ans[i++] = num;
            }
        }
        return ans;
    }


    //滑动窗口
    public long maximumSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        long sum = 0;
        for (int i = 0; i < k - 1; i++) {
            sum += nums[i];
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            if (map.get(nums[i]) > 1) {
                set.add(nums[i]);
            }
        }
        long ans = 0;
        for (int i = k - 1; i < nums.length; i++) {
            sum += nums[i];
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            if (map.get(nums[i]) > 1) {
                set.add(nums[i]);
            }
            if (set.size() == 0) {
                ans = Math.max(sum, ans);
            }

            int val = map.get(nums[i - k + 1]) - 1;
            if (val == 1) {
                set.remove(nums[i - k + 1]);
            }
            map.put(nums[i - k + 1], val);
            sum -= nums[i - k + 1];
        }
        return ans;
    }


    //双指针模拟
    public long totalCost(int[] costs, int k, int candidates) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> costs[a[0]] == costs[b[0]] ? a[0] - b[0] : costs[a[0]] - costs[b[0]]);
        int left = 0, right = costs.length - 1;
        for (; left < candidates && left <= right; left++) {
            priorityQueue.add(new int[]{left, 0});
        }
        for (; right >= costs.length - candidates && right >= left; right--) {
            priorityQueue.add(new int[]{right, 1});
        }
        long ans = 0;
        while (k-- > 0 && !priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            ans += costs[poll[0]];
            if (left <= right) {
                if (poll[1] == 0) {
                    priorityQueue.add(new int[]{left++, 0});
                } else {
                    priorityQueue.add(new int[]{right--, 1});
                }
            }
        }
        return ans;
    }
    //直接dfs就别想了
  /*  public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        help(robot, 0, factory, 0);
        return min;
    }

    long min = Long.MAX_VALUE;
    Map<Integer, Integer> memo = new HashMap<>();

    void help(List<Integer> robot, int index, int[][] factory, long ans) {
        if (index >= robot.size()) {
            min = Math.min(min, ans);
            return;
        }

        for (int j = 0; j < factory.length; j++) {
            factory[j][1]--;
            if (factory[j][1] >= 0) {
                long val = Math.abs(factory[j][0] - robot.get(index));
                help(robot, index + 1, factory, ans + val);
            }
            factory[j][1]++;
        }
    }*/


    //dp，不好想
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        //都进行排序才能连续分配
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);
        Collections.sort(robot);

        //dp[i][j]表示前i个工厂和前j个机器人的最少消耗
        long[][] dp = new long[factory.length + 1][robot.size() + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = (long) 1e18;
            }
        }


        for (int i = 0; i < factory.length; i++) {
            //考虑第i个工厂
            int[] f = factory[i];
            //考虑第j个机器人
            for (int j = 1; j <= robot.size(); j++) {
                long cost = 0;
                //同步之前的状态，即不用这个工厂的最少消耗
                dp[i + 1][j] = dp[i][j];
                //k表示在这个工厂里分配k个机器人，所有k<(j,limit(factory(i)))
                for (int k = 1; k <= Math.min(f[1], j); k++) {
                    //cost累加计算
                    cost += Math.abs((long) f[0] - robot.get(j - k));
                    //取最小消耗
                    dp[i + 1][j] = Math.min(dp[i + 1][j], cost + dp[i][j - k]);
                }
            }
        }
        return dp[factory.length][robot.size()];
    }
}
