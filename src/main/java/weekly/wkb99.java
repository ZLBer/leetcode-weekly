package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wkb99 {

    //贪心
    public int splitNum(int num) {

        List<Integer> list = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        while (num > 0) {
            list.add(num % 10);
            num /= 10;
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                l1.add(list.get(i));
            } else {
                l2.add(list.get(i));
            }
        }
        return sum(l1) + sum(l2);
    }

    //找规律 递推
    public int sum(List<Integer> list) {
        int s = 0;
        for (Integer i : list) {
            s = s * 10 + i;
        }
        return s;
    }

    static Map<Integer, Long> map = new HashMap<>();

    static {
        map.put(1, (long) 1);
        long sum = 1;

        for (int i = 1; i <= (int) 1e5; i++) {
            sum += (long) i * 4;
            map.put(i + 1, sum);
        }
    }

    public long coloredCells(int n) {
        return map.get(n);
    }



    //排序，合并区间
    public int countWays(int[][] ranges) {

        Arrays.sort(ranges, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        int pre = ranges[0][1];

        int count = 1;
        for (int i = 1; i < ranges.length; i++) {
            if (ranges[i][0] <= pre) {
                pre = Math.max(ranges[i][1], pre);
            } else {
                count++;
                pre = ranges[i][1];
            }
        }


        long res = 1;

        for (int i = 0; i < count; i++) {
            res *= 2;
            res %= (int) 1e9 + 7;
        }
        return (int) res;

    }

    //两次dfs,也换根DP
    public int rootCount(int[][] edges, int[][] guesses, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        Map<Integer, Set<Integer>> guessMap = new HashMap<>();
        for (int[] guess : guesses) {
            if (!guessMap.containsKey(guess[0])) guessMap.put(guess[0], new HashSet<>());
            guessMap.get(guess[0]).add(guess[1]);
        }

        int kk = dfs(-1, 0, map, guessMap);
        return dfs2(-1, 0, map, guessMap, k, kk);
    }

    int dfs(int parent, int index, Map<Integer, List<Integer>> map, Map<Integer, Set<Integer>> guessMap) {
        int val = 0;
        for (Integer child : map.getOrDefault(index, new ArrayList<>())) {
            if (child == parent) continue;
            if (guessMap.containsKey(index) && guessMap.get(index).contains(child)) {
                val++;
            }
            val += dfs(index, child, map, guessMap);
        }
        return val;
    }


    int dfs2(int parent, int index, Map<Integer, List<Integer>> map, Map<Integer, Set<Integer>> guessMap, int k, int kk) {
        int res = 0;
        //可满足
        if (guessMap.containsKey(index) && guessMap.get(index).contains(parent)) {
            kk++;
        }
        if (guessMap.containsKey(parent) && guessMap.get(parent).contains(index)) {
            kk--;
        }
        if (kk >= k) {
            res++;
        }

        for (Integer child : map.getOrDefault(index, new ArrayList<>())) {
            if (child == parent) continue;
            res += dfs2(index, child, map, guessMap, k, kk);
        }
        return res;
    }


}
