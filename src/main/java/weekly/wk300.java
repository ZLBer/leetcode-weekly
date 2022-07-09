package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk300 {

    //简单题，注意空格和重复字符
    public String decodeMessage(String key, String message) {
        String replace = key.replace(" ", "");
        char[] chars = replace.toCharArray();
        Map<Character, Character> map = new HashMap<>();
        map.put(' ', ' ');
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (map.containsKey(chars[i])) continue;
            map.put(chars[i], (char) ('a' + count));
            count++;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : message.toCharArray()) {

            sb.append(map.get(c));
        }
        return sb.toString();
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //中等题，出过好几次的螺旋矩阵
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] res = new int[m][n];
        for (int[] re : res) {
            Arrays.fill(re, -1);
        }
        spiralOrder(res, head);
        return res;
    }

    public void spiralOrder(int[][] matrix, ListNode node) {
        if (matrix.length == 0 || matrix[0].length == 0) return;

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (true) {
            for (int i = left; i <= right; i++) {
                matrix[top][i] = node.val;
                node = node.next;
                if (node == null) return;

            }
            top++;
            if (left > right || top > bottom) break;

            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = node.val;
                node = node.next;
                if (node == null) return;

            }
            right--;
            if (left > right || top > bottom) break;

            for (int i = right; i >= left; i--) {
                matrix[bottom][i] = node.val;
                node = node.next;
                if (node == null) return;

            }
            bottom--;
            if (left > right || top > bottom) break;

            for (int i = bottom; i >= top; i--) {
                matrix[i][left] = node.val;
                node = node.next;
                if (node == null) return;
            }
            left++;
            if (left > right || top > bottom) break;
        }

    }



    //中等题，O(n),用dp[i][2]来记录，dp[i][0]表示刚知道秘密的，dp[i][1]表示可以传播秘密的
    //dp[i][1]=dp[i-1][1]+dp[i-delay][0]-dp[i-forget][0]
    //dp[i][0]=dp[i][1]
   static public int peopleAwareOfSecret(int n, int delay, int forget) {
        long[][] dp = new long[n + 1][2];
        dp[1][0] = 1;
        int mod = (int) 1e9 + 7;
        for (int i = 2; i < dp.length; i++) {
            dp[i][1] = dp[i - 1][1];
            //新进入可以传播秘密的
            if (i - delay > 0) {
                dp[i][1] += dp[i - delay][0];
            }
            //忘记秘密的
            if (i - forget > 0) {
                dp[i][1] -= dp[i - forget][0];
                dp[i][1] += mod;
            }
            //传播秘密: 知道秘密的-忘记秘密的
            dp[i][0] += dp[i][1];

            dp[i][0] %= mod;
            dp[i][1] %= mod;
        }
        //最后要加上可以传播和不可以传播秘密的
        long res = dp[n][1];
        for (int i = 0; i < delay; i++) {
            res += dp[n - i][0];
            res %= mod;
        }
        return (int) res;
    }



    //困难题目，先排序，然后dp
    //或者可以直接记忆化搜索
    public int countPaths(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][] dp = new long[m][n];
        int mod = (int) 1e9 + 7;
        int[][] moves = new int[][]{
                {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                list.add(new int[]{grid[i][j], i, j});
            }
        }
        Collections.sort(list, (a, b) -> a[0] - b[0]);


        for (int[] ints : list) {
            int i = ints[1], j = ints[2];
            dp[i][j] = 1;

            for (int[] move : moves) {
                int ni = move[0] + i;
                int nj = move[1] + j;
                if (ni >= 0 && ni < m && nj >= 0 && nj < n&&grid[i][j]>grid[ni][nj]) {
                    dp[i][j]+=dp[ni][nj];
                    dp[i][j]%=mod;
                }
            }
        }

        long res = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                res += dp[i][j];
                res %= mod;
            }
        }
        return (int) res;
    }


    public static void main(String[] args) {
        peopleAwareOfSecret(6, 2, 4);
    }
}
