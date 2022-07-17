package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class wk301 {

    //ranking: 2372 / 7133  丢人 - -

    //简单题，贪心，麻烦的做法，每次取两个最大的比较
   /* public int fillCups(int[] amount) {
        int res = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i : amount) {
            if (i != 0)
                priorityQueue.add(i);
        }

        while (priorityQueue.size() > 2) {
            Integer poll = priorityQueue.poll() - 1;
            Integer poll1 = priorityQueue.poll() - 1;
            res++;
            if (poll != 0)
                priorityQueue.add(poll);
            if (poll1 != 0)
                priorityQueue.add(poll1);
        }

        if (!priorityQueue.isEmpty()) {
            res += priorityQueue.poll();
        }


        return res;
    }*/

    //贪心
    public int fillCups(int[] amount) {
        Arrays.sort(amount);
        int x = amount[0], y = amount[1], z = amount[2];

        if (x + y <= z) {
            return z;
        } else {
            int t = (x + y - z);
            if (t % 2 == 0) {
                return t / 2 + z;
            } else {
                return t / 2 + z + 1;
            }
        }
    }


    //中等题，哈希表，注意去重
    class SmallestInfiniteSet {

        int min = 1;

        TreeSet<Integer> priorityQueue = new TreeSet<>();

        public SmallestInfiniteSet() {

        }

        public int popSmallest() {
            if (!priorityQueue.isEmpty()) {
                Integer first = priorityQueue.first();
                priorityQueue.remove(first);
                return first;
            }
            return min++;
        }

        public void addBack(int num) {
            if (num < min) {
                priorityQueue.add(num);
            }
        }
    }

    //中等题，思维题，双指针
    static public boolean canChange(String start, String target) {
        for (int i = 0, j = 0; ; i++, j++) {
            //跳过空格
            for (; i < start.length() && start.charAt(i) == '_'; i++) {
            }
            for (; j < target.length() && target.charAt(j) == '_'; j++) {
            }
            //全部检查完毕
            if (i == start.length() && j == target.length()) {
                return true;
            }
            //以下情况都不正确：
            //1.有一个遍历完成
            //2 . L匹配到R
            //3. target的L比start的L滞后 target的R比start的R提前
            if (i == start.length() || j == target.length() || start.charAt(i) != target.charAt(j)
                    || (start.charAt(i) == 'L' ? i < j : i > j)) {
                return false;
            }
        }
    }


    //直接dp超时
   /* static public int idealArrays(int n, int maxValue) {
        int mod = (int) 1e9 + 7;
        long[] dp = new long[maxValue + 1];
        for (int i = 1; i <= maxValue; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < n; i++) {
            long[] ndp = new long[maxValue + 1];
            for (int j = 1; j <= maxValue; j++) {
                for (int k = 1; k * j <= maxValue; k++) {
                    ndp[k * j] += dp[j];
                    ndp[k * j] %= mod;
                }
            }
            dp = ndp;
        }
        long res = 0;
        for (long l : dp) {
            res += l;
            res %= mod;
        }
        return (int) res;
    }*/

    static final int MOD = (int) 1e9 + 7, MX = (int) 1e4 + 1, MX_K = 14;
    static int[][] c = new int[MX + MX_K][MX_K + 1]; // 组合数


    //预处理组合数
    static {
        c[0][0] = 1;
        for (int i = 1; i < MX + MX_K; ++i) {
            c[i][0] = 1;
            for (int j = 1; j <= Math.min(i, MX_K); ++j)
                c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
        }
    }

    //困难题，dp+排列组合，质因数分解，最多有14个质因数(n最大是10000)，
    public int idealArrays(int n, int maxValue) {

        //f[i][j]表示以i结尾的不同的质因数有j的个数
        int[][] f = new int[maxValue + 1][MX_K + 1];
        for (int i = 1; i <= maxValue; i++) {
            f[i][1] = 1;
        }
        for (int j = 0; j <= MX_K; j++) {
            for (int i = 1; i <= maxValue; i++) {
                for (int k = 2; k * i <= maxValue; k++) {
                    f[k * i][j] = (f[k * i][j] + f[i][j - 1]) % MOD;
                }
            }
        }
        //排列组合，在n-1个位置选择k-1个位置插入质因数(0位置必须放一个质因数)
        long res = 0;
        for (int i = 1; i < f.length; i++) {
            for (int j = 1; j < f[0].length; j++) {
                res = (res + (long) f[i][j] * c[n - 1][j - 1]) % MOD;
            }
        }

        return (int) res;
    }

    public static void main(String[] args) {
    }


}
