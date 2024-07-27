package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk405 {


    // 遍历
    public String getEncryptedString(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int next = (i + k) % s.length();
            sb.append(s.charAt(next));
        }
        return sb.toString();
    }

    //
    public List<String> validStrings(int n) {
        List<String> ans = new ArrayList<>();
        ans.add("0");
        ans.add("1");
        for (int i = 1; i < n; i++) {
            List<String> t = new ArrayList<>();
            for (String s : ans) {
                if (s.charAt(s.length() - 1) == '0') {
                    t.add(s + "1");
                } else {
                    t.add(s + "0");
                    t.add(s + "1");
                }
            }
            ans = t;
        }
        return ans;
    }


    // 二进制
    /*public List<String> validStrings(int n) {
        List<String> ans = new ArrayList<>();
        int mask = (1 << n) - 1;
        for (int i = 0; i < (1 << n); i++) {
            int x = mask ^ i;
            if (((x >> 1) & x) == 0) {
                ans.add(Integer.toBinaryString((1 << n) | i).substring(1));
            }
        }
        return ans;
    }*/


    //二维前缀和 做麻烦了
    static public int numberOfSubmatrices(char[][] grid) {

        int[][][] row = new int[grid.length + 1][grid[0].length + 1][2];
        int[][][] col = new int[grid.length + 1][grid[0].length + 1][2];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                row[i + 1][j + 1][0] = row[i + 1][j][0];
                row[i + 1][j + 1][1] = row[i + 1][j][1];
                col[i + 1][j + 1][0] = col[i][j + 1][0];
                col[i + 1][j + 1][1] = col[i][j + 1][1];

                if (grid[i][j] == 'X') {
                    row[i + 1][j + 1][0]++;
                    col[i + 1][j + 1][0]++;
                } else if (grid[i][j] == 'Y') {
                    row[i + 1][j + 1][1]++;
                    col[i + 1][j + 1][1]++;
                }
            }
        }

        int[][][] dp = new int[grid.length + 1][grid[0].length + 1][2];

        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                dp[i + 1][j + 1][0] = dp[i][j][0] + col[i][j + 1][0] + row[i + 1][j][0];
                dp[i + 1][j + 1][1] = dp[i][j][1] + col[i][j + 1][1] + row[i + 1][j][1];

                if (grid[i][j] == 'X') {
                    dp[i + 1][j + 1][0]++;
                } else if (grid[i][j] == 'Y') {
                    dp[i + 1][j + 1][1]++;
                }

                if (dp[i + 1][j + 1][0] != 0 && dp[i + 1][j + 1][0] == dp[i + 1][j + 1][1]) {
                    ans++;
                }
            }
        }
        return ans;
    }


    public int minimumCost(String target, String[] words, int[] costs) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], Math.min(costs[i], map.getOrDefault(words[i], Integer.MAX_VALUE)));
        }
        memo = new int[target.length()];
        Arrays.fill(memo, -1);
        int ans = dfs(0, target, map);
        return ans == MAX ? -1 : ans;
    }

    int MAX = (int) 1e9 + 7;
    int[] memo;

    int dfs(int cur, String target, Map<String, Integer> map) {
        if (cur >= target.length()) {
            return 0;
        }
        if (memo[cur] != -1) return memo[cur];

        int ans = MAX;
        for (int i = cur; i < target.length(); i++) {
            String sub = target.substring(cur, i + 1);
            if (!map.containsKey(sub)) {
                continue;
            }

            ans = Math.min(ans, map.get(sub) + dfs(i + 1, target, map));
        }
        memo[cur] = ans;
        return ans;
    }


    public static void main(String[] args) {
       wk405 w=new wk405();
       w.minimumCost("abcdef",
               new String[]{"abdef","abc","d","def","ef"},new int[]{100,1,1,10,5});
    }
}
