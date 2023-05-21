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

public class wk345 {
    //模拟
    public int[] circularGameLosers(int n, int k) {
        int[] count = new int[n];

        int cur = 0;
        int i = 1;
        count[cur] = 1;
        while (true) {
            int next = (cur + i * k) % (n);
            count[next]++;
            if (count[next] == 2) break;
            cur = next;
            i++;
        }
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < count.length; j++) {
            if (count[j] == 0) {
                list.add(j + 1);
            }
        }
        int[] res = new int[list.size()];
        for (int j = 0; j < list.size(); j++) {
            res[j] = list.get(j);
        }
        return res;
    }


    // 异或逆运算
    public boolean doesValidArrayExist(int[] derived) {
        return check(0, derived) | check(1, derived);
    }

    boolean check(int cur, int[] derived) {
        int begin = cur;
        for (int i = 0; i < derived.length; i++) {
            int next = derived[i] ^ cur;
            cur = next;
        }
        return begin == cur;
    }

    int[][] moves = new int[][]{
            {-1, -1}, {0, -1}, {1, -1}
    };

    //简单dp
    public int maxMoves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        int max = 0;
        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                for (int[] move : moves) {
                    int px = i + move[0];
                    int py = j + move[1];
                    if (px >= 0 && py >= 0 && px < m && py < n && grid[i][j] > grid[px][py] && dp[px][py] == py) {
                        dp[i][j] = Math.max(dp[i][j], dp[px][py] + 1);
                        max = Math.max(max, dp[i][j]);
                    }
                }
            }
        }
        return max;
    }


    //先找出连通分量 然后判断每个点的入度
    public int countCompleteComponents(int n, int[][] edges) {

        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] count = new int[n];
        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) {
                map.put(edge[0], new ArrayList<>());
            }
            if (!map.containsKey(edge[1])) {
                map.put(edge[1], new ArrayList<>());
            }
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
            count[edge[0]]++;
            count[edge[1]]++;
            uf.union(edge[0], edge[1]);
        }
        Map<Integer, List<Integer>> distinct = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int index = uf.find(i);
            if (!distinct.containsKey(index)) distinct.put(index, new ArrayList<>());
            distinct.get(index).add(i);
        }
        int ans=0;
        for (Map.Entry<Integer, List<Integer>> entry : distinct.entrySet()) {
            boolean flag = true;
            int c=entry.getValue().size();
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (count[entry.getValue().get(i)] != c) {
                    flag=false;
                    break;
                }
            }
            if(flag) ans++;
        }
     return ans;
    }

    //并查集
    class UnionFind {
        public final int[] parents;
        public int count;

        public UnionFind(int n) {
            this.parents = new int[n];
            reset();
        }

        public void reset() {
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
            }
            count = parents.length - 1;
        }

        public int find(int i) {
            int parent = parents[i];
            if (parent == i) {
                return i;
            } else {
                int root = find(parent);
                parents[i] = root;
                return root;
            }
        }

        public boolean union(int i, int j) {
            int r1 = find(i);
            int r2 = find(j);
            if (r1 != r2) {
                count--;
                parents[r1] = r2;
                return true;
            } else {
                return false;
            }
        }

  /*      void isolate(int x) {
            if (x != parents[x]) {
                parents[x] = x;
                count++;
            }
        }*/

    }


  /*  boolean help(int cur, boolean[] visited, Map<Integer, List<Integer>> map) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(cur);
        visited[cur] = true;
        Set<Integer> set=new HashSet<>();
        while (!queue.isEmpty()) {
            for (Integer next : map.getOrDefault(cur, new ArrayList<>())) {
                if (!visited[next]) {

                }
            }
        }
    }*/
}
