package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wkb129 {
    //遍历
    public boolean canMakeSquare(char[][] grid) {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                int a = 0, b = 0;
                if (grid[i][j] == 'W') {
                    a++;
                } else {
                    b++;
                }

                if (grid[i + 1][j] == 'W') {
                    a++;
                } else {
                    b++;
                }
                if (grid[i][j + 1] == 'W') {
                    a++;
                } else {
                    b++;
                }
                if (grid[i + 1][j + 1] == 'W') {
                    a++;
                } else {
                    b++;
                }
                if (a >= 3 || b >= 3) return true;
            }
        }
        return false;
    }

    //枚举
    public long numberOfRightTriangles(int[][] grid) {
        int[][] row = new int[grid.length + 1][grid[0].length + 1];
        int[][] col = new int[grid.length + 1][grid[0].length + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                row[i][j + 1] = row[i][j] + grid[i][j];
                col[i + 1][j] = col[i][j] + grid[i][j];
            }
        }
        long ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) continue;
                int left = row[i][j];
                int right = row[i][grid[0].length] - left - 1;
                int up = col[i][j];
                int down = col[grid.length][j] - up - 1;
                ans += ((long) right + left) * (up + down);
            }
        }
        return ans;
    }

    // 记忆化搜索
    public int numberOfStableArrays(int zero, int one, int limit) {

        memo=new int[zero+1][one+1][2];
        for (int[][] ints : memo) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt,-1);
            }
        }
        return (int)((dfs(zero, one, 0, limit) +dfs(zero,one,1,limit))%mod);
    }


    int mod = (int) 1e9 + 7;
    int [][][]memo;

    long dfs(int zero, int one, int cur, int limit) {
        if (zero == 0) {
            return cur==1&&one <= limit ? 1 : 0;
        }
        if (one == 0) {
            return cur==0&&zero <= limit ? 1 : 0;
        }
        Long key = ((long) zero << 10) + ((long) one << (10 * 2)) + ((long) cur << (10 * 3));
        if (memo[zero][one][cur]!=-1) return memo[zero][one][cur];

        long ans = 0;
        if (cur == 0) {
            ans += dfs(zero - 1, one, 0, limit) + dfs(zero-1,one, 1, limit)
                    - (zero > limit ? dfs(zero - limit - 1, one, 1, limit) : 0)+mod;
        } else if (cur == 1) {
            ans += dfs(zero , one-1, 0, limit) + dfs(zero, one - 1, 1, limit)
                    - (one > limit ? dfs(zero, one - limit - 1, 0, limit) : 0)+mod;
        }
        ans%=mod;
        memo[zero][one][cur]=(int)ans;
        return  (int)ans;
    }


    //TLE
   /* int dfs(int zero, int one,int preZero,int preOne, int len, int limit) {
        int cur = len - zero - one;
        if (cur >= len) {
            return 1;
        }


        Long key = ((long) zero << 10) + ((long) one << (10 * 2)) + ((long) pre << (10 * 3));

        if (memo.containsKey(key)) return memo.get(key);
        int ans = 0;
        //当前位置放0
        if (cur - preOne <= limit && zero > 0) {
            ans += dfs(zero - 1, one, cur, preOne, len, limit);
            ans %= mod;
        }
        //当前位置放1
        if (cur - preZero <= limit && one > 0) {
            ans += dfs(zero, one - 1, preZero, cur, len, limit);
            ans %= mod;
        }
        memo.put(key, ans);
        return ans;
    }*/

    public static void main(String[] args) {
        wkb129 w = new wkb129();
        w.numberOfStableArrays(1, 2, 1);
    }
}
