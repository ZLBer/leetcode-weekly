package weekly;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class wkb77 {


    // 688 / 4211

    //简单题，java直接调startWith
    public int countPrefixes(String[] words, String s) {
        int ans = 0;
        for (String word : words) {
            if (s.startsWith(word)) ans++;
        }
        return ans;
    }


    //中等题，前缀和求前后的平均值
    public int minimumAverageDifference(int[] nums) {
        long[] presum = new long[nums.length + 1];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            presum[i + 1] = presum[i] + nums[i];
        }
        long deep = Long.MAX_VALUE;

        for (int i = 0; i < nums.length - 1; i++) {
            long abs = Math.abs(presum[i + 1] / (i + 1) - (presum[nums.length] - presum[i + 1]) / (nums.length - 1 - i));
            if (abs < deep) {
                deep = abs;
                index = i;
            }
        }
        if (Math.abs(presum[nums.length] / nums.length) < deep) {
            index = nums.length - 1;
        }
        return index;
    }


    //中等题，遍历每一个守卫，遇到墙和守卫均停止，达到剪枝效果
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        int[][] count = new int[m][n];
        for (int i = 0; i < walls.length; i++) {
            int[] wall = walls[i];
            count[wall[0]][wall[1]] = -1;
        }
        for (int i = 0; i < guards.length; i++) {
            int[] guard = guards[i];
            count[guard[0]][guard[1]] = 2;
        }
        for (int i = 0; i < guards.length; i++) {
            int[] guard = guards[i];
            for (int[] move : moves) {
                int[] cur = new int[]{guard[0], guard[1]};
                cur[0] += move[0];
                cur[1] += move[1];
                while (cur[0] >= 0 && cur[0] < m && cur[1] >= 0 && cur[1] < n && count[cur[0]][cur[1]] != -1 && count[cur[0]][cur[1]] != 2) {
                    count[cur[0]][cur[1]] = 1;
                    cur[0] += move[0];
                    cur[1] += move[1];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[0].length; j++) {
                if (count[i][j] == 0) ans++;
            }
        }
        return ans;
    }

    static int[][] moves = new int[][]{
            {0, -1}, {-1, 0}, {0, 1}, {1, 0}
    };


    //困难题，周赛时间没够没调出来
    //说一下我的做法，steps[][]记录达到此处的最小步数，防止重复计算
    //为了统一计算，我把grid==2有墙的点改成了 Integer.MAX_VALUE
    //先让火势蔓延，计算每个点着火的时间，这样gird上的点就分成了三部分：
    //==0的表示永远不会着火，== Integer.MAX_VALUE表示墙，其余>0表示着火的时间
    //然后从原点再一次bfs，队列里保存当前的状态{x,y,step,能停留的时间}，并实时更新steps[][]的值，让其只增不减。
    //当且仅当 grid[x][y] != Integer.MAX_VALUE && steps[x][y] > step+1时，我们才将该点入队。
    //注意当到达重点的时候与其他点的计算有区别，不需要再-1
    //二分可以简化算法，对时间进行二分，然后判断这个t是否满足条件
    static public int maximumMinutes(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) queue.add(new int[]{i, j});
                else if (grid[i][j] == 2) {
                    grid[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        int m = grid.length, n = grid[0].length;
        //计算着火的时间
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            for (int[] move : moves) {
                int x = poll[0] + move[0];
                int y = poll[1] + move[1];
                if (x >= 0 && y >= 0 && x < m && y < n && grid[x][y] == 0) {
                    grid[x][y] = grid[poll[0]][poll[1]] + 1;
                    queue.add(new int[]{x, y});
                }
            }
        }
        //记录到该点的步数，来剪枝
        int[][] steps = new int[m][n];
        //分别表示： x y 步数 能停留的最大时间
        queue.add(new int[]{0, 0, 1, grid[0][0] == 0 ? (int) 1e9 : grid[0][0] - 1});
        for (int i = 0; i < steps.length; i++) {
            Arrays.fill(steps[i], Integer.MAX_VALUE);
        }
        steps[0][0] = 1;
        int anx = -1;
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            //达到终点
            if (poll[0] == m - 1 & poll[1] == n - 1) {
                anx = Math.max(anx, poll[3]);
                continue;
            }
            for (int[] move : moves) {
                int x = poll[0] + move[0];
                int y = poll[1] + move[1];

                // steps[x][y] > poll[2] + 1 保证达到改点的步数永远是最小的
                if (x >= 0 && y >= 0 && x < m && y < n && grid[x][y] != Integer.MAX_VALUE && steps[x][y] > poll[2] + 1) {
                    //表示火永远到不了，继承前面的最大停留时间
                    if (grid[x][y] == 0) {
                        queue.add(new int[]{x, y, poll[2] + 1, poll[3]});
                        steps[x][y] = (poll[2] + 1);
                     //表示在此处可以停留0+
                    } else if (grid[x][y] >= poll[2] + 1) { //表示此处还可以停留下

                        //到达最后了 不同再-1了
                        if (x == m - 1 & y == n - 1) {
                            queue.add(new int[]{x, y, poll[2] + 1, Math.min(poll[3], grid[x][y] - poll[2] - 1)});
                            continue;
                        }
                        //其余情况要-1
                        queue.add(new int[]{x, y, poll[2] + 1, Math.min(poll[3], grid[x][y] - poll[2] - 1 - 1)});
                        steps[x][y] = (poll[2] + 1);
                    }
                    //其余情况都会被火烧掉
                }
            }
        }

        return anx;
    }

    public static void main(String[] args) {
        maximumMinutes(new int[][]{
                {0, 0, 0, 0, 0}, {0, 2, 0, 2, 0}, {0, 2, 0, 2, 0}, {0, 2, 1, 2, 0}, {0, 2, 2, 2, 0}, {0, 0, 0, 0, 0}
        });
    }
}
