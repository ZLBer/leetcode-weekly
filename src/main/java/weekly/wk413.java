package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class wk413 {



    // 判断差值
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        int sub = Math.abs(coordinate1.charAt(0) - coordinate2.charAt(0)) + Math.abs(
                coordinate1.charAt(1) - coordinate2.charAt(1));

        if (sub % 2 == 0) return true;
        else return false;
    }


    public int[] resultsArray(int[][] queries, int k) {
        int[] ans = new int[queries.length];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < queries.length; i++) {
            int dis = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            priorityQueue.add(dis);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
            if (priorityQueue.size() == k) {
                ans[i] = priorityQueue.peek();
            } else {
                ans[i] = -1;
            }
        }
        return ans;
    }

    public int maxScore(List<List<Integer>> grid) {
        Map<Integer, Integer> pos = new HashMap<>();
        int m = grid.size();
        for (int i = 0; i < m; i++) {
            for (int x : grid.get(i)) {
                // 保存所有包含 x 的行号（压缩成二进制数）
                pos.merge(x, 1 << i, (a, b) -> a | b);
            }
        }

        // 只考虑在 grid 中的元素
        List<Integer> allNums = new ArrayList<>(pos.keySet());
        int n = allNums.size();
        int[][] memo = new int[n][1 << m];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示没有计算过
        }
        return dfs(n - 1, 0, pos, allNums, memo);
    }

    private int dfs(int i, int j, Map<Integer, Integer> pos, List<Integer> allNums, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前计算过
            return memo[i][j];
        }
        // 不选 x
        int res = dfs(i - 1, j, pos, allNums, memo);
        // 枚举选第 k 行的 x
        int x = allNums.get(i);
        for (int t = pos.get(x), lb; t > 0; t ^= lb) {
            lb = t & -t; // 最低位的1
            if ((j & lb) == 0) { // 没选过第 k 行的数
                res = Math.max(res, dfs(i - 1, j | lb, pos, allNums, memo) + x);
            }
        }
        return memo[i][j] = res; // 记忆化
    }


    public static void main(String[] args) {

    }
}
