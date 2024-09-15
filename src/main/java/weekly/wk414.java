package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wk414 {

    //模拟
    public String convertDateToBinary(String date) {
        String res = "";
        String[] split = date.split("-");
        res += Integer.toBinaryString(Integer.parseInt(split[0])) + "-";
        res += Integer.toBinaryString(Integer.parseInt(split[1])) + "-";
        res += Integer.toBinaryString(Integer.parseInt(split[2]));
        return res;
    }


    //二分
    static public int maxPossibleScore(int[] start, int d) {
        Arrays.sort(start);
        long left = 0, right = start[start.length - 1] + d - start[0];
        while (left < right) {
            long mid = (left + right + 1) / 2;
            boolean ok = check(start, d, mid);
            if (ok) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return (int) left;
    }


    static boolean check(int[] start, int d, long sub) {
        long pre = start[0];
        for (int i = 1; i < start.length; i++) {
            int s = start[i], e = start[i] + d;
            if (pre + sub > e) {
                return false;
            }
            pre = Math.max(pre + sub, s);
        }
        return true;
    }


    public long findMaximumScore(List<Integer> nums) {
        int pre = nums.get(0);
        long ans = 0;
        for (int i = 1; i < nums.size(); i++) {
            ans += pre;
            if (nums.get(i) > pre) {
                pre = nums.get(i);
            }
        }
        return ans;
    }


    private static final int[][] DIRS = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public int maxMoves(int kx, int ky, int[][] positions) {
        int n = positions.length;
        // 计算马到兵的步数，等价于计算兵到其余格子的步数
        int[][][] dis = new int[n][50][50];
        for (int i = 0; i < n; i++) {
            int[][] d = dis[i];
            for (int j = 0; j < 50; j++) {
                Arrays.fill(d[j], -1);
            }
            int px = positions[i][0];
            int py = positions[i][1];
            d[px][py] = 0;
            List<int[]> q = new ArrayList<>();
            q.add(new int[]{px,py});
            for (int step = 1; !q.isEmpty(); step++) {
                List<int[]> tmp = q;
                q = new ArrayList<>();
                for (int[] p : tmp) {
                    for (int[] dir : DIRS) {
                        int x = p[0] + dir[0];
                        int y = p[1] + dir[1];
                        if (0 <= x && x < 50 && 0 <= y && y < 50 && d[x][y] < 0) {
                            d[x][y] = step;
                            q.add(new int[]{x, y});
                        }
                    }
                }
            }
        }

        int[][] memo = new int[n + 1][1 << n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示没有计算过
        }
        return dfs(n, (1 << n) - 1, kx, ky, positions, dis, memo);
    }

    private int dfs(int i, int mask, int kx, int ky, int[][] positions, int[][][] dis, int[][] memo) {
        if (mask == 0) {
            return 0;
        }
        if (memo[i][mask] != -1) { // 之前计算过
            return memo[i][mask];
        }
        int n = positions.length;
        int x = i < n ? positions[i][0] : kx;
        int y = i < n ? positions[i][1] : ky;

        int res = 0;
        int u = (1 << n) - 1;
        if (Integer.bitCount(u ^ mask) % 2 == 0) { // Alice 操作
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) > 0) {
                    res = Math.max(res, dfs(j, mask ^ (1 << j), kx, ky, positions, dis, memo) + dis[j][x][y]);
                }
            }
        } else { // Bob 操作
            res = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) > 0) {
                    res = Math.min(res, dfs(j, mask ^ (1 << j), kx, ky, positions, dis, memo) + dis[j][x][y]);
                }
            }
        }
        return memo[i][mask] = res; // 记忆化
    }


    public static void main(String[] args) {
        maxPossibleScore(new int[]{1000000000, 0}, 1000000000);
    }
}
