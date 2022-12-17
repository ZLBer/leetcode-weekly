package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

public class wk323 {
    //ranking: 1203 / 4671 菜死


    //排序
    public int deleteGreatestValue(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            Arrays.sort(grid[i]);
        }
        for (int i = 0; i < grid[0].length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < grid.length; j++) {
                max = Math.max(max, grid[j][i]);
            }
            ans += max;
        }
        return ans;
    }

    //哈希
    public int longestSquareStreak(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int ans = -1;
        boolean[] visited = new boolean[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i] * nums[i];
            int count = 1;
            while (set.contains(num)) {
                count++;
                num = num * num;
            }
            if (count >= 2) {
                ans = Math.max(count, ans);
            }
        }
        return ans;
    }


    //模拟分配
    class Allocator {
        int[] memo;

        public Allocator(int n) {
            memo = new int[n];
        }

        public int allocate(int size, int mID) {
            int count = 0;
            for (int i = 0; i < memo.length; i++) {
                if (memo[i] != 0) {
                    count = 0;
                } else {
                    count++;
                    if (count == size) {
                        for (int j = i; j > i - size; j--) {
                            memo[j] = mID;
                        }
                        return i - size + 1;
                    }
                }
            }
            return -1;
        }

        public int free(int mID) {
            int count = 0;
            for (int i = 0; i < memo.length; i++) {
                if (memo[i] == mID) {
                    memo[i] = 0;
                    count++;
                }
            }
            return count;
        }
       /* public int allocate(int size, int mID) {
            int index = -1;
            for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
                //全分配
                if (entry.getValue() == size) {
                    treeMap.remove(entry.getKey());
                    index = entry.getKey();
                    //半分配
                } else if (entry.getValue() > size) {
                    treeMap.remove(entry.getKey());
                    treeMap.put(entry.getKey() + size, entry.getValue() - size);
                    index = entry.getKey();
                }
            }
            if (index > -1) {
                if (!map.containsKey(mID)) map.put(mID, new ArrayList<>());
                map.get(mID).add(index);
            }
            return index;
        }*/
    }


    //优先队列可降低复杂度
    //离线查询，排序查询，从小的开始处理
   /* public int[] maxPoints(int[][] grid, int[] queries) {
        int[][] qq = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            qq[i] = new int[]{queries[i], i};
        }
        Arrays.sort(qq, (a, b) -> a[0] - b[0]);
        int m = grid.length, n = grid[0].length;

        int[][] move = new int[][]{
                {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };
        int[] ans = new int[queries.length];
        Set<Integer> set = new HashSet<>();
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        priorityQueue.add(new int[]{grid[0][0], 0});
        for (int i = 0; i < qq.length; i++) {
            int[] a = qq[i];

            while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] < a[0]) {
                int[] poll = priorityQueue.poll();
                int index = poll[1];
                int x = index / n;
                int y = index % n;
                set.add(index);
                for (int[] next : move) {
                    int nx = next[0] + x;
                    int ny = next[1] + y;
                    int s = nx * n + ny % n;
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !set.contains(s)) {
                        priorityQueue.add(new int[]{grid[nx][ny], s});//这次可以检查
                        if (grid[nx][ny] < a[0]) {
                            set.add(s);
                        }
                    }
                }
            }
            ans[a[1]] = set.size();
        }
        return ans;
    }*/


    //转化成Dijkstra找最短路径问题
    public int[] maxPoints(int[][] grid, int[] queries) {
        int m = grid.length, n = grid[0].length;
        int[][] d = new int[m][n];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        priorityQueue.add(new int[]{grid[0][0], 0, 0});
        d[0][0]=grid[0][0];
        int[][] move = new int[][]{
                {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            int val = poll[0];
            int x = poll[1];
            int y = poll[2];
            for (int[] ints : move) {
                int nx = ints[0] + x;
                int ny = ints[1] + y;
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && d[nx][ny] == 0) {
                    d[nx][ny] = Math.max(val, grid[nx][ny]);
                    priorityQueue.add(new int[]{d[nx][ny],nx,ny});
                }
            }
        }
        int []sum=new int[(int)1e6];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                System.out.print(d[i][j]+" ");
                sum[d[i][j]]++;
            }
            System.out.println();
        }
        for (int i = 1; i < sum.length; i++) {
            sum[i]+=sum[i-1];
        }
        for (int i = 0; i < queries.length; i++) {
            queries[i]=sum[queries[i]-1];
        }
        return queries;
    }
}
