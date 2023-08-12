package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class wk357 {

    //模拟
    public String finalString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'i') {
                sb = sb.reverse();
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //可贪心
    public boolean canSplitArray(List<Integer> nums, int m) {
        int n = nums.size();
        if (n <= 2) return true;
        for (int i = 1; i < n; i++)
            if (nums.get(i - 1) + nums.get(i) >= m)
                return true;
        return false;
    }


    /*public boolean canSplitArray(List<Integer> nums, int m) {
        int[] sum = new int[nums.size() + 1];
        for (int i = 0; i < nums.size(); i++) {
            sum[i + 1] += sum[i] + nums.get(i);
        }

        memo = new int[nums.size()][nums.size()];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }
        return dfs(0, nums.size() - 1, sum, m);
    }


    int[][] memo;

    boolean dfs(int begin, int end, int[] presum, int m) {
        if (begin == end) return true;
        if (begin + 1 == end) return true;
        if (memo[begin][end] != -1) {
            return memo[begin][end] == 1;
        }
        boolean ans = false;
        for (int i = begin + 1; i <= end; i++) {
            boolean left = (i - begin == 1) || (presum[i] - presum[begin] >= m);
            boolean right = (end - i + 1 == 1) || (presum[end + 1] - presum[i] >= m);
            if (left && right) {
                ans = dfs(begin, i - 1, presum, m) && dfs(i, end, presum, m);
                if (ans) break;
            }
        }
        memo[begin][end] = ans ? 1 : 0;
        return ans;
    }*/

/*    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int m = grid.size(), n = grid.get(0).size();

        List<int[]> g = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    g.add(new int[]{i, j});
                }
            }
        }

        int[][] dis = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int ans = Integer.MAX_VALUE;
                for (int[] ints : g) {
                    ans = Math.min(ans, Math.abs(i - ints[0]) + Math.abs(j - ints[1]));
                }
                dis[i][j] = ans;
            }
        }

        int left = 0, right = Math.max(m, n);
        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (check(dis, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    int[][] moves = new int[][]{
            {0, -1}, {-1, 0}, {1, 0}, {0, 1}
    };

    boolean check(int[][] distance, int dis) {
        boolean[][] visited = new boolean[distance.length][distance[0].length];
        return dfs(0, 0, dis, distance, visited);
    }

    boolean dfs(int x, int y, int dis, int[][] distance, boolean[][] visited) {
        if (distance[x][y] < dis) {
            return false;
        }
        if (x == distance.length - 1 && y == distance.length[] - 1) {
            return true;
        }
        if (visited[x][y]) {
            return false;
        }
        visited[x][y] = true;

        for (int[] move : moves) {
            int nx = move[0] + x;
            int ny = move[1] + y;
            if (nx >= 0 && ny >= 0 && nx < distance.length && ny < distance[0].length) {
                if (dfs(nx, ny, dis, distance, visited)) {
                    return true;
                }
            }
        }
        return false;
    }*/

    int[][] moves = new int[][]{
            {0, -1}, {-1, 0}, {1, 0}, {0, 1}
    };

    //并查集
  /*  public int maximumSafenessFactor(List<List<Integer>> grid) {
        int m = grid.size(), n = grid.get(0).size();

        Queue<int[]> queue = new LinkedList<>();
        int[][] dis = new int[m][n];
        for (int[] di : dis) {
            Arrays.fill(di, -1);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    queue.add(new int[]{i, j});
                   dis[i][j]=0;
                }
            }
        }

        TreeMap<Integer, List<int[]>> treeMap = new TreeMap<>((a, b) -> b - a);


        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] poll = queue.poll();

                if (!treeMap.containsKey(step)) {
                    treeMap.put(step, new ArrayList<>());
                }
                treeMap.get(step).add(poll);

                for (int[] move : moves) {
                    int nx = move[0] + poll[0];
                    int ny = move[1] + poll[1];
                    if (nx >= 0 && ny >= 0 && nx < m && ny < n && dis[nx][ny] == -1) {
                        queue.add(new int[]{nx, ny});
                        dis[nx][ny]=step+1;
                    }
                }
            }
            step++;
        }

        UnionFind uf = new UnionFind(m * n);

        for (Map.Entry<Integer, List<int[]>> entry : treeMap.entrySet()) {
            for (int[] ints : entry.getValue()) {
                int x = ints[0], y = ints[1];
                for (int[] move : moves) {
                    int nx = move[0] + x;
                    int ny = move[1] + y;
                    if (nx >= 0 && ny >= 0 && nx < m && ny < n && dis[x][y] <= dis[nx][ny]) {
                        uf.union(x * n + y, nx * n + ny);
                    }
                }
            }
            if (uf.find(0) == uf.find((m - 1) * n + n - 1)) return entry.getKey();
        }

        return 0;
    }*/

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


    //迪杰斯塔拉
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int m = grid.size(), n = grid.get(0).size();

        Queue<int[]> queue = new LinkedList<>();
        int[][] dis = new int[m][n];
        for (int[] di : dis) {
            Arrays.fill(di, -1);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    queue.add(new int[]{i, j});
                    dis[i][j] = 0;
                }
            }
        }

        TreeMap<Integer, List<int[]>> treeMap = new TreeMap<>((a, b) -> b - a);


        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] poll = queue.poll();

                if (!treeMap.containsKey(step)) {
                    treeMap.put(step, new ArrayList<>());
                }
                treeMap.get(step).add(poll);

                for (int[] move : moves) {
                    int nx = move[0] + poll[0];
                    int ny = move[1] + poll[1];
                    if (nx >= 0 && ny >= 0 && nx < m && ny < n && dis[nx][ny] == -1) {
                        queue.add(new int[]{nx, ny});
                        dis[nx][ny] = step + 1;
                    }
                }
            }
            step++;
        }

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        priorityQueue.add(new int[]{dis[0][0], 0, 0});
        boolean[][] vis = new boolean[m][n];
        vis[0][0] = true;
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();

            if (poll[1] == m - 1 && poll[2] == n - 1) {
                return poll[0];
            }
            for (int[] move : moves) {
                int nx = move[0] + poll[1];
                int ny = move[1] + poll[2];
                if (nx >= 0 && ny >= 0 && nx < m && ny < n && !vis[nx][ny]) {
                    priorityQueue.add(new int[]{Math.min(dis[nx][ny],poll[0]), nx, ny});
                    vis[nx][ny] = true;
                }
            }
        }
        return 0;
    }

//    int dfs(int x, int y, int[][] distance, int[][] dp, boolean[][] visited) {
//        if (x == distance.length - 1 && y == distance[0].length - 1) {
//            return distance[x][y];
//        }
//        if (dp[x][y] != -1) {
//            return dp[x][y];
//        }
//        if (visited[x][y]) {
//            return 0;
//        }
//        visited[x][y] = true;
//        int max = 0;
//        for (int[] move : moves) {
//            int nx = move[0] + x;
//            int ny = move[1] + y;
//            if (nx >= 0 && ny >= 0 && nx < distance.length && ny < distance[0].length) {
//                max = Math.max(max, dfs(nx, ny, distance, dp, visited));
//            }
//        }
//        max = Math.min(distance[x][y], max);
//        dp[x][y] = max;
//        visited[x][y] = false;
//        return max;
//    }


    //贪心
    public long findMaximumElegance(int[][] items, int k) {
        // 把利润从大到小排序
        Arrays.sort(items, (a, b) -> b[0] - a[0]);
        long ans = 0, totalProfit = 0;
        Set<Integer> vis = new HashSet<>();
        ArrayDeque<Integer> duplicate = new ArrayDeque<Integer>(); // 重复类别的利润
        for (int i = 0; i < items.length; i++) {
            int profit = items[i][0], category = items[i][1];
            if (i < k) {
                totalProfit += profit;
                if (!vis.add(category)) // 重复类别
                    duplicate.push(profit);
            } else if (!duplicate.isEmpty() && vis.add(category)) {
                totalProfit += profit - duplicate.pop(); // 选一个重复类别中的最小利润替换
            } // else：比前面的利润小，而且类别还重复了，选它只会让 totalProfit 变小，vis.size() 不变，优雅度不会变大
            ans = Math.max(ans, totalProfit + (long) vis.size() * vis.size()); // 注意 1e5*1e5 会溢出
        }
        return ans;
    }

    public static void main(String[] args) {
        wk357 w = new wk357();
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(0, 1, 1));
        list.add(Arrays.asList(0, 0, 1));
        list.add(Arrays.asList(1, 0, 0));

        w.maximumSafenessFactor(list);
    }

}
