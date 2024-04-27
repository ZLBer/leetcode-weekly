package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class wk394 {
    //遍历
    /*public int numberOfSpecialChars(String word) {
        int[] a = new int[26];
        int[] b = new int[26];
        for (char c : word.toCharArray()) {
            if (Character.isLowerCase(c)) {
                 a[c-'a']++;
            }else {
                  b[c-'A']++;
            }
        }
        int ans=0;
        for (int i = 0; i < a.length; i++) {
            if(a[i]>0&&b[i]>0){
                ans++;
            }
        }
        return ans;
    }*/

    //遍历
    public int numberOfSpecialChars(String word) {
        int[] a = new int[26];
        int[] b = new int[26];
        Arrays.fill(a, -1);
        Arrays.fill(b, -1);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLowerCase(c)) {
                a[c - 'a'] = i;
            } else {
                if (b[c - 'A'] == -1) {
                    b[c - 'A'] = i;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1 && b[i] != -1 && a[i] < b[i]) {
                ans++;
            }
        }
        return ans;
    }

    //记忆化搜索
    public int minimumOperations(int[][] grid) {
        int[][] counter = new int[grid[0].length][10];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                counter[j][grid[i][j]]++;
            }
        }
        int m = grid.length;
        int n = grid[0].length;
        memo = new int[n][1000];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }
        return dfs(0, -1, counter, m, n);
    }

    int[][] memo;

    int dfs(int j, int pre, int[][] counter, int m, int n) {
        if (j >= n) {
            return 0;
        }
        if (pre != -1 && memo[j][pre] != -1) return memo[j][pre];
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= 9; i++) {
            if (i == pre) continue;
            ans = Math.min(dfs(j + 1, i, counter, m, n) + m - counter[j][i], ans);
        }
        if (pre != -1) memo[j][pre] = ans;
        return ans;
    }


    // dijkstra
    public boolean[] findAnswer(int n, int[][] edges) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(new int[]{edge[1], edge[2], i});
            map.get(edge[1]).add(new int[]{edge[0], edge[2], i});
        }

        Map<Integer, Integer> left = help(map, 0);
        Map<Integer, Integer> right = help(map, n - 1);
        boolean[] ans = new boolean[edges.length];

        if (!left.containsKey(n - 1)) return ans;

        int min = left.get(n - 1);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            if (left.containsKey(edge[0]) && right.containsKey(edge[1]) && left.get(edge[0]) + right.get(edge[1]) + edge[2] == min) {
                ans[i] = true;
            }

            if (left.containsKey(edge[1]) && right.containsKey(edge[0]) && left.get(edge[1]) + right.get(edge[0]) + edge[2] == min) {
                ans[i] = true;
            }
        }
        return ans;
    }

    Map<Integer, Integer> help(Map<Integer, List<int[]>> map, int from) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a,b)->a[0]-b[0]);
        priorityQueue.add(new int[]{0, from});
        Map<Integer, Integer> ans = new HashMap<>();
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            if (ans.containsKey(poll[1])) continue;
            ans.put(poll[1], poll[0]);
            for (int[] next : map.getOrDefault(poll[1], new ArrayList<>())) {
                if (ans.containsKey(next[0])) continue;
                priorityQueue.add(new int[]{poll[0] + next[1],next[0]});
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        wk394 w = new wk394();
       w.findAnswer(4,new int[][]{
               {2,0,1},{0,1,1},{0,3,4},{3,2,2}
       });
    }
}
