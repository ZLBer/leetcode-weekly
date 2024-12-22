package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk428 {
    public int buttonWithLongestTime(int[][] events) {
        int max = events[0][1];
        int ans = events[0][0];
        for (int i = 1; i < events.length; i++) {
            if (events[i][1] - events[i - 1][1] > max) {
                max = events[i][1] - events[i - 1][1];
                ans = events[i][0];
            } else if (events[i][1] - events[i - 1][1] == max) {
                ans = Math.min(events[i][0], ans);
            }
        }
        return ans;
    }


    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {

        double ans = 1.0;
        dfs(initialCurrency, 1.0, pairs1, rates1, new boolean[pairs1.size()]);
        if (target.containsKey(initialCurrency)) {
            ans = Math.max(ans, target.get(initialCurrency));
        }

        Map<String, Double> next = target;
        target = new HashMap<>();


        for (Map.Entry<String, Double> entry : next.entrySet()) {
            dfs(entry.getKey(), entry.getValue(), pairs2, rates2, new boolean[pairs2.size()]);
        }
        if (target.containsKey(initialCurrency)) {
            ans = Math.max(ans, target.get(initialCurrency));
        }

        return ans;
    }

    Map<String, Double> target = new HashMap<>();

    void dfs(String cur, Double amount, List<List<String>> pairs, double[] rates, boolean[] visited) {

        target.put(cur, Math.max(target.getOrDefault(cur, 0.0), amount));
        for (int i = 0; i < pairs.size(); i++) {
            if (visited[i]) continue;
            visited[i] = true;
            if (pairs.get(i).get(0).equals(cur)) {
                dfs(pairs.get(i).get(1), amount * rates[i], pairs, rates, visited);
            }
            if (pairs.get(i).get(1).equals(cur)) {
                dfs(pairs.get(i).get(0), amount * (1 / rates[i]), pairs, rates, visited);
            }
            visited[i] = false;
        }
    }

    public int beautifulSplits(int[] nums) {
        int n = nums.length;
        // lcp[i][j] 表示 s[i:] 和 s[j:] 的最长公共前缀
        int[][] lcp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= i; j--) {
                if (nums[i] == nums[j]) {
                    lcp[i][j] = lcp[i + 1][j + 1] + 1;
                }
            }
        }

        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((i <= j - i && lcp[0][i] >= i )|| lcp[i][j] >= j - i) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
    }

}
