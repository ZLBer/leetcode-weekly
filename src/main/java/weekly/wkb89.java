package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class wkb89 {
    //ranking: 484 / 3984


    //if判断吧
    public int countTime(String time) {
        String[] split = time.split(":");
        int ans = 1;
        if (split[0].charAt(0) == '?' && split[0].charAt(1) == '?') {
            ans *= 24;
        } else if (split[0].charAt(0) == '?') {
            if (split[0].charAt(1) >= '4') {
                ans *= 2;
            } else {
                ans *= 3;
            }
        } else if (split[0].charAt(1) == '?') {
            if (split[0].charAt(0) == '2') {
                ans *= 4;
            } else {
                ans *= 10;
            }
        }
        if (split[1].charAt(0) == '?' && split[1].charAt(1) == '?') {
            ans *= 60;
        } else if (split[1].charAt(0) == '?') {
            ans *= 6;
        } else if (split[1].charAt(1) == '?') {
            ans *= 10;

        }
        return ans;
    }


    //可以前前缀和
    public int[] productQueries(int n, int[][] queries) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) > 0) {
                list.add(i);
            }
        }
        list.add(0, 0);
        for (int i = 1; i < list.size(); i++) {
            list.set(i, list.get(i) + list.get(i - 1));
        }

        int mod = (int) 1e9 + 7;
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            //System.out.println(((long) 1) << (list.get(queries[i][1] + 1) - list.get(queries[i][0])));

            double a = Math.pow(2, list.get(queries[i][1] + 1) - list.get(queries[i][0])) % mod;

            res[i] = (int) a;
        }
        return res;
    }


    //贪心,也可以二分
    public int minimizeArrayValue(int[] nums) {
        long sum = nums[0];
        long ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            //平摊一下
            if (nums[i] > ans) {
                long a = sum / (i + 1);
                if (a * (i + 1) != sum) {
                    a += 1;
                }
                ans = Math.max(a, ans);
            }
        }
        return (int) ans;
    }

   /* public int componentValue(int[] nums, int[][] edges) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        int left = 1, right = sum;
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] count = new int[nums.length];
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
            count[edge[0]]++;
            count[edge[1]]++;
        }
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 1) {
                queue.add(new int[]{i, nums[i]});
                count[i]--;
            }
        }
        while (left < right) {
            int mid = (left + right) / 2;
            boolean b = check(nums, map, mid, Arrays.copyOf(count, count.length), new LinkedList<>(queue));
            System.out.println(mid + " " + b);
            if (b) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }*/

   /* boolean check(int[] nums, Map<Integer, List<Integer>> map, int mid, int[] count, Queue<int[]> queue) {

        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int cur = poll[0];
            int sum = poll[1];
            if (sum > mid) {
                return false;
            }
            if (sum == mid) {
                sum = 0;
            }
            //
            for (Integer to : map.getOrDefault(cur, new ArrayList<>())) {
                count[to]--;
                if (count[to] == 1) {
                    queue.add(new int[]{to, sum + nums[to]});
                } else if (count[to] == 0) {
                    return sum + nums[to] == mid;
                } else {
                    nums[to] += sum;
                }
            }
        }
        return true;
    }*/


    //枚举+DFS,居然没想到该怎么dfs,反而开始用拓扑排序?
    public int componentValue(int[] nums, int[][] edges) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        //枚举拆分多少个
        for (int i = nums.length; i > 0; i--) {
            if (sum % i == 0) {
                if (dfs(-1, 0, nums, map, sum / i) == 0) return i-1;
            }
        }
        return 1;
    }

    int dfs(int from, int index, int[] nums, Map<Integer, List<Integer>> map, int sum) {
        int result = nums[index];
        for (Integer to : map.getOrDefault(index, new ArrayList<>())) {
            if (to == from) continue;
            int d=dfs(index, to, nums, map, sum);
            if(d<0){
                return d;
            }
            result += d;
        }
        return result > sum ? -1 : result % sum;
    }


}
