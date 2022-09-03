package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk308 {

     //ranking: 1283 / 6394

    //简单题，关键在于排序，可以前缀和+二分优化
    public int[] answerQueries(int[] nums, int[] queries) {
        int[] ans = new int[queries.length];
        Arrays.sort(nums);
        for (int i = 0; i < queries.length; i++) {
            int count = 0;
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                sum += nums[j];
                count++;
                if (sum > queries[i]) {
                    break;
                }
            }
            if (sum > queries[i]) {
                count -= 1;
            }
            ans[i] = count;
        }
        return ans;
    }

    //中等题，栈模拟就行
    public String removeStars(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                deque.pollLast();
            } else {
                deque.addLast(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append(deque.pollFirst());
        }
        return sb.toString();
    }


    //中等题，非必要不前进
  /*  public int garbageCollection(String[] garbage, int[] travel) {
        int[][] count = new int[garbage.length][3];
        for (int i = 0; i < garbage.length; i++) {
            String g = garbage[i];
            for (char c : g.toCharArray()) {
                if (c == 'M') {
                    count[i][0]++;
                } else if (c == 'P') {
                    count[i][1]++;
                } else {
                    count[i][2]++;
                }
            }
        }
        int ans = 0;
        for (int k = 0; k < 3; k++) {
            int cost = 0;
            int cur = 0;
            for (int i = 0; i < count.length; i++) {
                if (i > 0) {
                    cur += travel[i - 1];
                }
                if (count[i][k] > 0) {
                    cost += count[i][k];
                    cost += cur;
                    cur = 0;
                }
            }
           // System.out.println(cost);
            ans += cost;
        }

        return ans;
    }*/

    //一种简单的做法，只与最右边出现的有关
    public int garbageCollection(String[] garbage, int[] travel) {
        int ans = 0;
        int[] right = new int[3];
        for (int j = 0; j < garbage.length; j++) {
            String s = garbage[j];
            ans += s.length();
            for (int i = 0; i < "MPG".toCharArray().length; i++) {
                char c = "MPG".charAt(i);
                if (s.indexOf(c) >= 0) {
                    right[i] = j;
                }
            }

        }
        for (int i = 0; i < right.length; i++) {
            for (int j = 0; j < Math.min(travel.length, right[i]); j++) {
                ans += travel[j + 1];
            }
        }
        return ans;
    }


    //困难题，周赛做的太麻烦了
//    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
//        int[] row = new int[k + 1];
//        int[] col = new int[k + 1];
//        Map<Integer, Set<Integer>> rowMap = new HashMap<>();
//        Map<Integer, Set<Integer>> colMap = new HashMap<>();
//
//        for (int[] rowCondition : rowConditions) {
//            if (!rowMap.containsKey(rowCondition[0])) {
//                rowMap.put(rowCondition[0], new HashSet<>());
//            }
//            boolean add = rowMap.get(rowCondition[0]).add(rowCondition[1]);
//            if (add) row[rowCondition[1]]++;
//
//        }
//        for (int[] colCondition : colConditions) {
//            if (!colMap.containsKey(colCondition[0])) {
//                colMap.put(colCondition[0], new HashSet<>());
//            }
//            boolean add = colMap.get(colCondition[0]).add(colCondition[1]);
//            if (add) col[colCondition[1]]++;
//
//
//        }
//
//        int[] l = help(colMap, col);
//        if (l == null) return new int[][]{};
//        Queue<Integer> queue = new LinkedList<>();
//        for (int i = 1; i < row.length; i++) {
//            //没有入度
//            if (row[i] == 0) {
//                queue.add(i);
//            }
//        }
//        int[][] res = new int[k][k];
//        int up = 0;
//        int add = 0;
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            List<Integer> list = new ArrayList<>();
//            while (size-- > 0) {
//                Integer poll = queue.poll();
//                list.add(poll);
//                for (Integer next : rowMap.getOrDefault(poll, new HashSet<>())) {
//                    if (row[next] == 0) continue;
//                    row[next]--;
//                    if (row[next] == 0) {
//                        queue.add(next);
//                    }
//                }
//            }
//            for (int i = 0; i < list.size(); i++) {
//                int left = 0;
//                for (int j = 1; j < l.length; j++) {
//                    if (l[j] < l[list.get(i)]) {
//                        left++;
//                    }
//                }
//                res[up++][left] = list.get(i);
//                add++;
//            }
//        }
//        return add == k ? res : new int[][]{};
//    }
//
//    int[] help(Map<Integer, Set<Integer>> map, int[] in) {
//        int add = 0;
//        int[] number = new int[in.length];
//        Queue<Integer> queue = new LinkedList<>();
//        for (int i = 1; i < in.length; i++) {
//            //没有入度
//            if (in[i] == 0) {
//                queue.add(i);
//            }
//        }
//        boolean[] visited = new boolean[in.length];
//        int step = 0;
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            while (size-- > 0) {
//                Integer poll = queue.poll();
//                number[poll] = step;
//                add++;
//                for (Integer next : map.getOrDefault(poll, new HashSet<>())) {
//                    if (in[next] == 0) continue;
//                    in[next]--;
//                    if (in[next] == 0) {
//                        queue.add(next);
//                    }
//                }
//                step++;
//            }
//        }
//
//        return add == in.length - 1 ? number : null;
//    }

    //代码优化，横竖无关，两次拓扑排序就能唯一确定数字的位置
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        Map<Integer, Integer> row = help(k, rowConditions);
        Map<Integer, Integer> col = help(k, colConditions);
        if (row.size() < k || col.size() < k) {
            return new int[][]{};
        }
        int[][] res = new int[k][k];
        for (Map.Entry<Integer, Integer> entry : row.entrySet()) {
            int v = entry.getKey();
            res[entry.getValue()][col.get(v)] = v;
        }
        return res;
    }


    Map<Integer, Integer> help(int k, int[][] condition) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] in = new int[k + 1];
        for (int[] ints : condition) {
            if (!map.containsKey(ints[0])) {
                map.put(ints[0], new ArrayList<>());
            }
            map.get(ints[0]).add(ints[1]);
            in[ints[1]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < in.length; i++) {
            if (in[i] == 0) {
                queue.add(i);
            }
        }
        Map<Integer, Integer> res = new HashMap<>();

        int index = 0;
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            res.put(poll, index++);
            for (Integer next : map.getOrDefault(poll, new ArrayList<>())) {
                in[next]--;
                if (in[next] == 0) {
                    queue.add(next);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        wk308 w = new wk308();
        w.buildMatrix(3, new int[][]{{1, 2}, {3, 2}},
                new int[][]{{2, 1}, {3, 1}});
    }
}
