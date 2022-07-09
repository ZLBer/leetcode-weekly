package weekly;

public class wk292 {
    //298 / 6884


    //简单题，判断三个相同  然后求个全局最大
    public String largestGoodInteger(String num) {
        int n = num.length();
        String res = "";
        for (int i = 0; i < n - 2; i++) {
            String sub = num.substring(i, i + 3);

            if (sub.charAt(0) == sub.charAt(1) && sub.charAt(0) == sub.charAt(2)) {
                if (sub.compareTo(res) > 0) {
                    res = sub;
                }
            }
        }
        return res;
    }

    //比较骚的做法
  /*  public String largestGoodInteger(String num) {
        String[] arr = { "999", "888", "777", "666", "555", "444", "333", "222", "111", "000" };
        for (String s : arr) {
            if (num.indexOf(s) >= 0) return s;
        }
        return "";
    }*/

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

    //中等题，后序遍历记录子树sum和count即可
    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return count;
    }

    int count = 0;

    int[] dfs(TreeNode node) {
        if (node == null) {
            return null;
        }
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        int[] all = new int[]{
                node.val, 1
        };
        if (left != null) {
            all[0] += left[0];
            all[1] += left[1];
        }
        if (right != null) {
            all[0] += right[0];
            all[1] += right[1];
        }
        if (all[0] / all[1] == node.val) count++;
        return all;
    }


    //中等题，只需要考虑连续的相同数字的组合数  然后做乘法即可
    //如何计算组合数？dp求和。可以提前算好3和4的dp方案数
    public int countTexts(String pressedKeys) {

        int count = 1;
        int key = pressedKeys.charAt(0) - '0';
        long res = 1;
        for (int i = 1; i < pressedKeys.length(); i++) {
            if (pressedKeys.charAt(i) == pressedKeys.charAt(i - 1)) {
                count++;
            } else {
                res *= helper(count, key);
                res %= (int) 1e9 + 7;
                count = 1;
                key = pressedKeys.charAt(i) - '0';


            }
        }
        res *= helper(count, key);
        res %= (int) 1e9 + 7;
        return (int) res;
    }

    long helper(int count, int key) {
        int len = 3;
        if (key == 7 || key == 9) {
            len++;
        }

        long[] dp = new long[count + 1];

        dp[0] = 1;
        for (int i = 1; i <= count; i++) {
            for (int j = 1; j <= len; j++) {
                if (i - j >= 0) dp[i] += dp[i - j];
            }
            dp[i] %= (int) 1e9 + 7;
        }

        return dp[count];
    }


    //困难题，用了dfs+记录
    //递归返回的时候需记录该点的状态，包括(x,y,左括号数，该点是否能valid)
    //也可以用三维dp来做
    public boolean hasValidPath(char[][] grid) {
        visit = new boolean[grid.length][grid[0].length][grid.length + grid[0].length];
        return dfs(0, 0, grid, 0);
    }


    boolean[][][] visit;

    boolean dfs(int i, int j, char[][] grid, int left) {
        if (i == grid.length - 1 && j == grid[0].length - 1) {
            if (grid[i][j] == '(') return false;
            else {
                if (left == 1) return true;
                return false;
            }
        }
        if (visit[i][j][left]) return false;
        visit[i][j][left] = true;
        if (grid[i][j] == '(') left++;
        else {
            if (left == 0) return false;//剪枝
            left--;
        }
        boolean l = false, r = false;
        if (i + 1 < grid.length) {
            l = dfs(i + 1, j, grid, left);
        }
        if (j + 1 < grid[0].length) {
            r = dfs(i, j + 1, grid, left);
        }
        return l || r;
    }


    //可以用三维dp来做
  /*  public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][][] dp = new boolean[m][n][m + n];
        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') return false;
        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                char c = grid[i][j];
                for (int k = 0; k < m + n; k++) {
                    if (c == '(') {
                        if (i - 1 >= 0 && k - 1 >= 0 && dp[i - 1][j][k - 1]) {
                            dp[i][j][k] = true;
                        }
                        if (j - 1 >= 0 && k - 1 >= 0 && dp[i][j - 1][k - 1]) {
                            dp[i][j][k] = true;
                        }
                    } else {
                        if (i - 1 >= 0 && k + 1 <= m + n - 1 && dp[i - 1][j][k + 1]) {
                            dp[i][j][k] = true;
                        }
                        if (j - 1 >= 0 && k + 1 <= m + n - 1 && dp[i][j - 1][k + 1]) {
                            dp[i][j][k] = true;
                        }
                    }

                }
            }
        }
        return dp[m - 1][n - 1][0];
    }*/

    //可以用dfs+visit数组+剪枝来做


    public static void main(String[] args) {


    }
}
