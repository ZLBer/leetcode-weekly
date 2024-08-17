package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk410 {

    // 模拟
    public int finalPositionOfSnake(int n, List<String> commands) {
        int x = 0, y = 0;
        for (String command : commands) {
            if (command.equals("UP")) {
                x--;
            } else if (command.equals("DOWN")) {
                x++;
            } else if (command.equals("RIGHT")) {
                y++;
            } else {
                y--;
            }
        }
        return x * n + y;
    }

    // DFS
    public int countGoodNodes(int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        dfs(0, -1, map);
        return ans;
    }

    int ans = 0;

    int dfs(int cur, int parent, Map<Integer, List<Integer>> map) {

        int cc = 0;
        int pre = -1;
        boolean flag = true;
        for (Integer child : map.getOrDefault(cur, new ArrayList<>())) {
            if (child == parent) continue;
            int i = dfs(child, cur, map);
            if (pre != -1 && i != pre) {
                flag = false;
            }
            cc += i;
            pre = i;
        }
        if (flag) {
            ans++;
        }

        return cc + 1;
    }


    //前缀和优化dp
    static public int countOfPairs(int[] nums) {
        int MAX = 51;
        long[][] dp = new long[nums.length][MAX + 1];

        for (int i = 0; i <= nums[0]; i++) {
            dp[0][i + 1] = dp[0][i] + 1;
        }

        for (int i = nums[0]+1; i <MAX; i++) {
            dp[0][i + 1] = dp[0][i] + 1;
        }
        long mod = (long) 1e9 + 7;

        for (int i = 1; i < nums.length; i++) {

            for (int j = 0; j <= nums[i]; j++) {
                long a ;
                int pre = Math.max(0, nums[i - 1] - (nums[i] - j) + 1);
                if(j+1<pre){
                    a = dp[i - 1][j + 1];
                }else {
                    a= dp[i - 1][pre];
                }
                dp[i][j + 1] =( dp[i][j] +a  )%mod;
            }
            for (int j = nums[i] + 1; j < MAX; j++) {
                dp[i][j + 1] = dp[i][j];
            }
        }
        long ans = dp[nums.length - 1][nums[nums.length - 1] + 1]%mod ;
        return (int) ans;
    }

    public static void main(String[] args) {
        countOfPairs(new int[]{3, 21});
    }
}
