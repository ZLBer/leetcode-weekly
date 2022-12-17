package weekly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class wkb93 {


    //ranking: 280 / 2929

    //不会正则直接判断
    public int maximumValue(String[] strs) {
        int max = 0;
        for (int i = 0; i < strs.length; i++) {
            boolean flag = true;
            for (int j = 0; j < strs[i].length(); j++) {
                if (!Character.isDigit(strs[i].charAt(j))) {
                    flag = false;
                }
            }
            if (flag) {
                max = Math.max(max, Integer.parseInt(strs[i]));
            } else {
                max = Math.max(strs[i].length(), max);
            }
        }
        return max;
    }


    //找邻居+排序
    public int maxStarSum(int[] vals, int[][] edges, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(vals[edge[1]]);
            map.get(edge[1]).add(vals[edge[0]]);
        }
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            Collections.sort(entry.getValue(), Collections.reverseOrder());
        }
        int ans = Integer.MIN_VALUE;
        for (int val : vals) {
            ans = Math.max(ans, val);
        }
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int sum = vals[entry.getKey()];
            int count = 1;
            for (Integer val : entry.getValue()) {
                if (sum + val < sum || count >= k) {
                    break;
                }
                sum += val;
                k++;
            }
            ans = Math.max(sum, ans);
        }
        return ans;
    }


    //贪心间隔跳
    public int maxJump(int[] stones) {

        int ans = stones[1] - stones[0];
        for (int i = 2; i < stones.length; i++) {
            ans = Math.max(ans, stones[i] - stones[i - 2]);
        }
        return ans;

    }

  /*  public long minimumTotalCost(int[] nums1, int[] nums2) {
        long ans = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == nums2[i]) {
                list.add(i);
            }
        }
        boolean[] visited = new boolean[nums1.length];
        for (int i = list.size() - 1; i >= 0; i--) {
            if (visited[i]) continue;
            for (int j = i - 1; j >= 0; j--) {
                if (visited[j]) continue;
                if (nums1[list.get(i)] != nums1[list.get(j)]) {
                    visited[i] = true;
                    visited[j] = true;
                    ans += (i + j);
                }
            }
        }
    }*/

    public long minimumTotalCost(int[] nums1, int[] nums2) {
        int swapCount = 0;
        int ans = 0;
        int modCount = 0;
        int mod = 0;
        int[] cnt = new int[nums1.length + 1];

        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == nums2[i]) {
                swapCount++;
                ans += i;
                cnt[nums1[i]]++;
                if (cnt[nums1[i]] > modCount) {
                    modCount = cnt[nums1[i]];
                    mod = nums1[i];
                }
            }
        }
        System.out.println(ans);
        for (int i = 0; i < nums1.length && mod * 2 > swapCount; i++) {
            if (nums1[i] != nums2[i] && nums1[i] != mod && nums2[i] != mod) {
                ans += i;
                swapCount++;
            }
        }
        return modCount * 2 > swapCount ? -1 : ans;
    }

}
