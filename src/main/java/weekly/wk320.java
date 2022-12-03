package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class wk320 {

    //ranking: 71 / 5678

    // a*b*c
    public int unequalTriplets(int[] nums) {
        Arrays.sort(nums);
        int start = 0;
        int ans = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                ans += start * (i - start + 1) * (nums.length - i - 1);
                start = i + 1;
            }
        }
        return ans;
    }

   /* public int unequalTriplets(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] != nums[j] && nums[j] != nums[k] && nums[i] != nums[k]) {
                        res++;
                    }
                }
            }
        }
        return res;
    }*/


    // 用treemap做快一点
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    TreeSet<Integer> set = new TreeSet<>();

    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        dfs(root);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < queries.size(); i++) {
            List<Integer> l = new ArrayList<>();
            Integer floor = set.floor(queries.get(i));
            if (floor == null) {
                l.add(-1);
            } else {
                l.add(floor);
            }
            Integer ceiling = set.ceiling(queries.get(i));
            if (ceiling == null) {
                l.add(-1);
            } else {
                l.add(ceiling);
            }
            res.add(l);
        }
        return res;
    }

    void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        set.add(root.val);
        dfs(root.left);
        dfs(root.right);
    }

    //贪心 每次把所有乘客都放到离0点更近的节点，一起运送
    public long minimumFuelCost(int[][] roads, int seats) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] road : roads) {
            if (!map.containsKey(road[0])) map.put(road[0], new ArrayList<>());
            if (!map.containsKey(road[1])) map.put(road[1], new ArrayList<>());
            map.get(road[0]).add(road[1]);
            map.get(road[1]).add(road[0]);
        }
        dfs(0, -1, seats, map);
        return cost;
    }

    long cost = 0;

    int dfs(int cur, int pre, int seats, Map<Integer, List<Integer>> map) {

        int count = 0;
        for (Integer next : map.getOrDefault(cur, new ArrayList<>())) {
            if (next == pre) continue;
            int c = dfs(next, cur, seats, map);
            cost += c / seats + (c % seats == 0 ? 0 : 1);
            count += c;
        }
        return count + 1;
    }


    //dp[i][k] 记录截止到i，能分成k段的方法次数
   /* static public int beautifulPartitions(String s, int K, int minLength) {
        Set<Character> set = new HashSet<>(Arrays.asList('2', '3', '5', '7'));
        long[][] dp = new long[s.length() + 1][K + 1];
        dp[0][0] = 1;
        int mod = (int) 1e9 + 7;
        //截止到i
        for (int i = 0; i < s.length(); i++) {
            //从j开始
            for (int j = i - minLength + 1; j >= 0; j--) {
                //首尾判断
                if (set.contains(s.charAt(j)) && !set.contains(s.charAt(i))) {
                    for (int k = 1; k <= K; k++) {
                        dp[i + 1][k] += dp[j][k - 1];
                        dp[i + 1][k] %= mod;
                    }
                }
            }
        }
        return (int) dp[s.length()][K];
    }*/

    //优化成二次循环
    //dp[k][i] 记录截止到i，能分成k段的方法次数
    public int beautifulPartitions(String s, int K, int minLength) {
        long[][] dp = new long[s.length() + 1][K + 1];
        dp[0][0] = 1;
        int mod = (int) 1e9 + 7;
        if (K * minLength > s.length() || !isPrime(s.charAt(0)) || isPrime(s.charAt(s.length() - 1))) // 剪枝
            return 0;
        //分成k段
        for (int i = 1; i <= K; i++) {
            long sum = 0;
            //从j开始
            for (int j = i * minLength; j + (K - i) * minLength <= s.length(); j++) {
                //求前缀和
                //累加前面的分割点，从j-minLength才能累加
                if (canPartition(s, j - minLength)) sum = (sum + dp[i - 1][j - minLength]) % mod;
                //判断该点能不能分割
                if (canPartition(s, j)) dp[i][j] = sum;
            }
        }
        return (int) dp[K][s.length()];
    }

    private boolean isPrime(char c) {
        return c == '2' || c == '3' || c == '5' || c == '7';
    }

    private boolean canPartition(String s, int j) {
        return j == 0 || j == s.length() || !isPrime(s.charAt(j - 1)) && isPrime(s.charAt(j));
    }


    public static void main(String[] args) {
    }
}
