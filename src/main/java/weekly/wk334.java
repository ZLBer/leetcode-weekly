package weekly;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class wk334 {
    //ranking: 635 / 5501

    //前缀和
    public int[] leftRigthDifference(int[] nums) {
        int[] sum = new int[nums.length + 1];
        for (int i = nums.length - 1; i >= 0; i--) {
            sum[i] = sum[i + 1] + nums[i];
        }

        int[] ans = new int[nums.length];
        int pre = 0;
        for (int i = 0; i < nums.length; i++) {
            ans[i] = Math.abs(pre - sum[i + 1]);
            pre += nums[i];
        }
        return ans;
    }


    //取模
    public int[] divisibilityArray(String word, int m) {

        int[] ans = new int[word.length()];
        long sum = 0;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - '0';
            sum = sum * 10 + c;
            sum %= m;
            if (sum == 0) {
                ans[i] = 1;
            }
        }
        return ans;
    }

    //贪心
    //排序后 前一半和后一半比较即可，最多n/2对
    static public int maxNumOfMarkedIndices(int[] nums) {
        Arrays.sort(nums);
        int right = nums.length - 1;
        int ans = 0;
        for (int i = (nums.length) / 2 - 1; i >= 0; i--) {
            if (right >= 0 && nums[i] * 2 <= nums[right]) {
                ans += 2;
                right--;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        minimumTime(new int[][]{
                {0,1,99},{3,99,99},{4,5,6}
        });
    }


    // Dijkstra变形
    static int[][] moves = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    static public int minimumTime(int[][] grid) {
        if (grid[0][1] > 1 && grid[1][0] > 1) // 如果迂回
            return -1;
        int[][] dp = new int[grid.length][grid[0].length];
        for (int[] ints : dp) {
            Arrays.fill(ints, (int) 1e9 + 7);
        }
        dp[0][0] = 0;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        priorityQueue.add(new int[]{0, 0, 0});

        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll();
            for (int[] move : moves) {
                int nx = cur[1] + move[0];
                int ny = cur[2] + move[1];
                if (nx < grid.length && ny < grid[0].length && nx >= 0 && ny >= 0) {

                    int step = cur[0];
                    //无法按时访问
                    if (grid[nx][ny] > cur[0] + 1) {
                        //在起点无法迂回
                        if (cur[1] == 0 && cur[2] == 0) {
                            continue;
                        }
                        step += grid[nx][ny] - cur[0];
                        //此时必须要多一步
                        if ((grid[nx][ny] - cur[0]) % 2 == 0) step++;

                    } else {
                        step++;
                    }
                    if (dp[nx][ny] > step) {
                        priorityQueue.add(new int[]{step, nx, ny});
                        dp[nx][ny] = step;
                    }
                }
            }
        }
        return dp[grid.length - 1][grid[0].length - 1] == (int) 1e9 + 7 ? -1 : dp[grid.length - 1][grid[0].length - 1];
    }

}
