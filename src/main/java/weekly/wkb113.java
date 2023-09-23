package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class wkb113 {

    //找到截断点
    public int minimumRightShifts(List<Integer> nums) {
        if (nums.size() == 1) return 0;
        List<Integer> list = new ArrayList<>();
        if (nums.get(0) < nums.get(nums.size() - 1)) {
            list.add(0);
        }
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(i - 1)) {
                list.add(i);
            }
        }
        if (list.size() > 1) return -1;

        return (nums.size() - list.get(0)) % nums.size();

    }

    //贪心
    static public int minLengthAfterRemovals(List<Integer> nums) {

        List<Integer> list = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i).equals(nums.get(i - 1))) {
                count++;
            } else {
                list.add(count);
                count = 1;
            }
        }
        list.add(count);
        if (list.size() == 1) return nums.size();
        Collections.sort(list, Comparator.reverseOrder());

        int max = list.get(0);
        int left = 0;
        for (Integer integer : list) {
            left += integer;
        }
        left -= max;
        return max >= left ? max - left : nums.size() % 2;
    }


    // 哈希
    static public int countPairs(List<List<Integer>> coordinates, int k) {

        int ans = 0;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < coordinates.size(); i++) {
            int x = coordinates.get(i).get(0);
            int y = coordinates.get(i).get(1);
            for (int j = 0; j <= k; j++) {
                int cx = x ^ j;
                int cy = y ^ (k - j);

                ans += map.getOrDefault(cx + "-" + cy, 0);
            }

            map.put(x + "-" + y, map.getOrDefault(x + "-" + y, 0) + 1);
        }
        return ans;
    }


    //换根dp
    public int[] minEdgeReversals(int n, int[][] edges) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) {
                map.put(edge[0], new ArrayList<>());
            }
            if (!map.containsKey(edge[1])) {
                map.put(edge[1], new ArrayList<>());
            }
            map.get(edge[0]).add(new int[]{edge[1], 0});
            map.get(edge[1]).add(new int[]{edge[0], 1});
        }

        //计算正反路径次数
        int[] count = dfs(0, -1, map);

        int[] ans = new int[n];
        //
        help(0, -1, map, count, new int[2], ans);
        return ans;
    }


    int[] dfs(int cur, int pre, Map<Integer, List<int[]>> map) {
        int[] ans = new int[2];
        for (int[] next : map.get(cur)) {
            if (next[0] == pre) continue;
            ans[next[1]]++;
            int[] child = dfs(next[0], cur, map);
            ans[0] += child[0];
            ans[1] += child[1];
        }
        return ans;
    }

    //

    void help(int cur, int pre, Map<Integer, List<int[]>> map, int[] count, int[] parent, int[] ans) {


        //根节点的反路径-根节点到此的反路径+根节点到此的正路径
        ans[cur] = count[1] - parent[1]+parent[0];

        for (int[] next : map.get(cur)) {
            if (next[0] == pre) continue;
            parent[next[1]]++;
            help(next[0], cur, map, count, parent,ans);
            //回溯
            parent[next[1]]--;
        }
    }


    public static void main(String[] args) {
        minLengthAfterRemovals(Arrays.asList(1000000000, 1000000000));
    }
}
