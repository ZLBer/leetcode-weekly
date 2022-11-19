package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk319 {

    //ranking: 628 / 6175

    //模拟
    public double[] convertTemperature(double celsius) {
        return new double[]{celsius + 273.15, celsius * 1.80 + 32};
    }


    //暴力做的
    public int subarrayLCM(int[] nums, int k) {

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int s = nums[i];
            for (int j = i; j < nums.length; j++) {
                //两数乘积＝两数的最大公因数×两数的最小公倍数
                s = (int) (s / gcd(nums[j], s) * nums[j]);
                if (s == k) {
                    res++;
                }
                if (s > k) break;
            }
            System.out.println(res);
        }
        return res;
    }

    long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

//    public static int getCM(int m, int n) {
//        for (int i = (m > n ? m : n); i <= m * n; i++) {
//            if (i % n == 0 && i % m == 0) {
//                return i;
//            }
//        }
//        return -1;
//    }

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


    //层序遍历，然后求每层的最小交换此次数，问题转换了-置换环
    public int minimumOperations(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int res = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>(size);
            while (size-- > 0) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            int ans = minSwaps(list);
            res += ans;
        }
        return res;
    }

    public static int minSwaps(List<Integer> list) {
        int n = list.size();

        ArrayList<int[]> arrpos = new ArrayList<>();
        for (int i = 0; i < n; i++)
            arrpos.add(new int[]{list.get(i), i});

        arrpos.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0])
                    return -1;
                else if (o1[0] == o2[0])
                    return 0;
                else
                    return 1;
            }
        });
        boolean[] vis = new boolean[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (vis[i] || arrpos.get(i)[1] == i)
                continue;
            int cycle_size = 0;
            int j = i;
            while (!vis[j]) {
                vis[j] = true;
                j = arrpos.get(j)[1];
                cycle_size++;
            }
            if (cycle_size > 0) {
                ans += (cycle_size - 1);
            }
        }
        return ans;
    }

    //dp
    static public int maxPalindromes(String s, int k) {
        int[] dp = new int[s.length() + 1];
        boolean[][] can = new boolean[s.length()][s.length()];
        for (int i = 0; i < can.length; i++) {
            can[i][i] = true;
        }
        //求所有的回文串，这里也可以中心扩展法
        for (int len = 2; len <= s.length(); len++) {
            for (int i = 0; i + len <= s.length(); i++) {
                if (s.charAt(i) == s.charAt(i + len - 1)) {
                    if (len == 2) {
                        can[i][i + len - 1] = true;
                    } else {
                        can[i][i + len - 1] |= can[i + 1][i + len - 2];
                    }
                }
            }
        }

        if (can[0][k - 1]) {
            dp[k] = 1;
        }

        //dp[i]表示i结尾的满足长度为k的回文序列的个数
        //从k长度开始找
        for (int i = k; i < s.length(); i++) {
            dp[i + 1] = dp[i];
            for (int begin = i - k + 1; begin >= 0; begin--) {
                if (can[begin][i]) {
                    dp[i + 1] = Math.max(dp[i + 1], dp[begin] + 1);
                }
            }
        }
        return dp[dp.length - 1];
    }

    //检查是不是回文串
    static boolean check(String s, int begin, int end) {
        while (begin < end) {
            if (s.charAt(begin) != s.charAt(end)) {
                return false;
            }
            begin++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {
        maxPalindromes("fttfjofpnpfydwdwdnns", 2);
    }

}
