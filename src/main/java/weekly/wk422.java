package weekly;

import java.util.Arrays;
import java.util.PriorityQueue;

public class wk422 {
    //模拟
    public boolean isBalanced(String num) {
        int a = 0, b = 0;
        for (int i = 0; i < num.length(); i++) {
            if (i % 2 == 0) {
                a += num.charAt(i) - '0';
            } else {
                b += num.charAt(i) - '0';
            }
        }
        return a == b;
    }

   /* public int minTimeToReach(int[][] moveTime) {

        int[][] dp = new int[moveTime.length][moveTime[0].length];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        priorityQueue.add(new int[]{0, 0, 0});
        int[][] moves = new int[][]{
                {0, 1}, {0, -1}, {1, 0}, {-1, 0},
        };
        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll();

            for (int[] move : moves) {
                int nx = cur[1] + move[0];
                int ny = cur[2] + move[1];
                if (nx >= 0 && ny >= 0 && nx < moveTime.length && ny < moveTime[0].length) {
                    if (dp[nx][ny] == -1) {
                        dp[nx][ny] = Math.max(cur[0] + 1, moveTime[nx][ny]+1);
                        priorityQueue.add(new int[]{dp[nx][ny], nx, ny});
                    }
                }
            }
        }
        return dp[moveTime.length-1][moveTime[0].length-1];
    }*/

    // Dijkstra
    public int minTimeToReach(int[][] moveTime) {

        int[][] dp = new int[moveTime.length][moveTime[0].length];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> (a[0]+a[3]) - (b[0]+b[3]));
        priorityQueue.add(new int[]{0, 0, 0, 1});
        int[][] moves = new int[][]{
                {0, 1}, {0, -1}, {1, 0}, {-1, 0},
        };
        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll();
            for (int[] move : moves) {
                int nx = cur[1] + move[0];
                int ny = cur[2] + move[1];
                if (nx >= 0 && ny >= 0 && nx < moveTime.length && ny < moveTime[0].length) {
                    if (dp[nx][ny] == -1) {
                        dp[nx][ny] = Math.max(cur[0] + cur[3], moveTime[nx][ny] + cur[3]);
                        priorityQueue.add(new int[]{dp[nx][ny], nx, ny, cur[3]==1?2:1});
                    }
                }
            }
        }
        return dp[moveTime.length - 1][moveTime[0].length - 1];
    }
}
