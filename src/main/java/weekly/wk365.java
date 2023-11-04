package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk365 {
 /*   public long maximumTripletValue(int[] nums) {
        long max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    max = Math.max(max, ((long) nums[i] - nums[j]) * nums[k]);
                }
            }
        }
        return max;
    }*/


    //枚举
    public long maximumTripletValue(int[] nums) {
        long max = 0;
        long sub = 0;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            ans = Math.max(ans, sub * nums[i]);
            sub = Math.max(sub, max - nums[i]);
            max = Math.max(max, nums[i]);
        }
        return ans;
    }


    //滑动窗口+分类
    public int minSizeSubarray(int[] nums, int target) {
        long total = 0;
        for (int x : nums) total += x;
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        int left = 0;
        long sum = 0;
        for (int right = 0; right < n * 2; right++) {
            sum += nums[right % n];
            while (sum > target % total) {
                sum -= nums[left++ % n];
            }
            if (sum == target % total) {
                ans = Math.min(ans, right - left + 1);
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans + (int) (target / total) * n;
    }


    //内向基环树
   static public int[] countVisitedNodes(List<Integer> edges) {


        int[] ans = new int[edges.size()];
        int[] in = new int[edges.size()];
        for (Integer edge : edges) {
            in[edge]++;
        }

        for (int i = 0; i < edges.size(); i++) {

            int cur = i;
            if (ans[cur] != 0) continue;
            int time = 0;
            Map<Integer, Integer> set = new HashMap<>();
            List<Integer> list = new ArrayList<>();
            set.put(cur, time);
            list.add(cur);

            while (true) {
                time++;
                cur = edges.get(cur);
                //之前计算过了
                if (ans[cur] != 0) {
                    for (int j = 0; j < list.size(); j++) {
                        ans[list.get(j)] = ans[cur] + list.size() - j;
                    }
                    break;
                }
                //存在圈
                if (set.containsKey(cur)) {
                    int circleIn = set.get(cur);
                    int circle = time - circleIn;
                    for (int j = 0; j < list.size(); j++) {
                        //在圈之前
                        if (j < circleIn) {
                            ans[list.get(j)] = circle + circleIn - j;
                            //在圈里
                        } else {
                            ans[list.get(j)] = circle;
                        }
                    }
                    break;
                }
                set.put(cur, time);
                list.add(cur);

            }


        }

        return ans;
    }


    public static void main(String[] args) {
       countVisitedNodes(Arrays.asList(1,2,0,0));
    }
}
