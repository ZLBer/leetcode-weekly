package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk289 {

    // 520 / 7293

    //简单题，k个一组求和就好
    static public String digitSum(String s, int k) {

        while (s.length() > k) {
            StringBuilder ns = new StringBuilder();
            for (int i = 0; i < s.length(); i += k) {
                int sum = 0;
                for (int j = 0; j < k && i + j < s.length(); j++) {
                    sum += s.charAt(i + j) - '0';
                }
                ns.append(sum);
            }
            s = ns.toString();
        }
        return s;
    }

    //中等题，先计算相等数字的个数，可以排序也可以hash表(可以少判断很多边界情况)，
    // 对于每个个数 count,
    // 如果count==1直接返回，
    // 如果除三能除尽，就累加count/3,
    // 如果不能除尽，余数要么是1，要么是2, 1的情况要占用一个3,变成2+2，2的情况就是要占用2，所以要累加count/3+1
    public int minimumRounds(int[] tasks) {

        if(tasks.length==1) return -1;
        Arrays.sort(tasks);
        int count = 1;
        int res = 0;
        for (int i = 1; i < tasks.length; i++) {
            if (tasks[i] == tasks[i - 1]) {
                count++;
            }
            //最后一个和前面不相等
            if(i == tasks.length - 1&&tasks[i]!=tasks[i-1]){
                return -1;
            }

            if (tasks[i] != tasks[i - 1] || i == tasks.length - 1) {
                if (count == 1) return -1;

                if (count % 3 == 0) res += count / 3;
                else{
                    res += (count / 3) + 1;
                }
                count = 1;
            }
        }

        return res;
    }


    //中等题，周赛的时候没想明白0的个数和什么有关，其实与因数2和5的个数有关，
    //要用二维前缀和记录此位置之前/之上因数2和5的个数 ,二维前缀和细节比较多
    //然后枚举点， 枚举上下左右 两两组合的min(2,5),
    // 虽然题目说可以不拐弯，但拐弯一定比不拐弯更好，因为拐弯因数的个数只会增加
    static public int maxTrailingZeros(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int[][] rowTwo = new int[m + 1][n + 1];
        int[][] rowFive = new int[m + 1][n + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int[] twoAndFive = getTwoAndFive(grid[i][j]);
                rowTwo[i + 1][j + 1] = twoAndFive[0] + rowTwo[i + 1][j];
                rowFive[i + 1][j + 1] = twoAndFive[1] + rowFive[i + 1][j];

            }
        }
        int[][] colTwo = new int[m + 1][n + 1];
        int[][] colFive = new int[m + 1][n + 1];
        for (int i = 0; i < grid[0].length; i++) {

            for (int j = 0; j < grid.length; j++) {
                int[] twoAndFive = getTwoAndFive(grid[j][i]);
                colTwo[j + 1][i + 1] = twoAndFive[0] + colTwo[j][i + 1];
                colFive[j + 1][i + 1] = twoAndFive[1] + colFive[j][i + 1];

            }

        }
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //左边
                int[] left = new int[]{rowTwo[i + 1][j + 1], rowFive[i + 1][j + 1]};
                //右边
                int[] right = new int[]{rowTwo[i + 1][n] - rowTwo[i + 1][j], rowFive[i + 1][n] - rowFive[i + 1][j]};
                //上边 不包含i j
                int[] up = new int[]{colTwo[i][j + 1], colFive[i][j + 1]};

                //下边 不包含i j
                int[] down = new int[]{colTwo[m][j + 1] - colTwo[i + 1][j + 1], colFive[m][j + 1] - colFive[i + 1][j + 1]};

                List<int[]> row = new ArrayList<>();
                List<int[]> col = new ArrayList<>();
                row.add(left);
                row.add(right);
                col.add(up);
                col.add(down);
                for (int[] r : row) {
                    for (int[] c : col) {
                        int t = r[0] + c[0];
                        int f = r[1] + c[1];
                        res = Math.max(res, Math.min(t, f));
                    }
                }

            }
        }
        return res;
    }



    static public int[] getTwoAndFive(int num) {
        int two = 0, five = 0;
        while (num > 0 && num % 2 == 0) {
            num /= 2;
            two++;
        }
        while (num > 0 && num % 5 == 0) {
            num /= 5;
            five++;
        }
        return new int[]{two, five};
    }



    //困难题，这个题比前面那个题就简单点了
    //前序遍历，每次遍历返回以此节点开始的最长路径， 递归返回时就得到了孩子节点的最长路径，然后求出最长的两条，与此节点组成一条完整路径，更新全局最大值
    //注意这不是二叉树！！
    public int longestPath(int[] parent, String s) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            if (!map.containsKey(parent[i])) map.put(parent[i], new ArrayList<>());
            map.get(parent[i]).add(i);
        }
        dfs(0, map, s);
        return res;
    }

    int res = 1;

    int dfs(int indedx, Map<Integer, List<Integer>> map, String s) {

        List<Integer> childMax = new ArrayList<>();

        int max1 = 0, max2 = 0;
        for (Integer child : map.getOrDefault(indedx, new ArrayList<>())) {
            int next = dfs(child, map, s);
            if (s.charAt(child) != s.charAt(indedx)) {
                if (next > max1) {
                    if (max1 > max2) {
                        max2 = max1;
                    }
                    max1 = next;
                } else if (next > max2) {
                    max2 = next;
                }
            }
        }

        res = Math.max(res, max1 + max2 + 1);
        return max1 + 1;
    }
}
