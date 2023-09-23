package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class wk363 {

    //暴力
    public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (Integer.bitCount(i) == k) {
                ans += nums.get(i);
            }
        }
        return ans;
    }


    //遍历
    public int countWays(List<Integer> nums) {
        Collections.sort(nums);

        int ans = 0;
        //判断起始情况
        if (nums.get(0) > 0) {
            ans++;
        }
        //依次遍历判断
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) < i+1) {
                //最后的位置不用判断
                if (i == nums.size() - 1) {
                    ans++;
                } else {
                    if (nums.get(i + 1) > i+1) {
                        ans++;
                    }
                }
            }
        }
        return ans;

    }


    //二分
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int ans = 0;
        //遍历制造哪一个
        for (List<Integer> list : composition) {
            ans = Math.max(help(budget, list, stock, cost), ans);
        }
        return ans;
    }

    int help(int budget, List<Integer> composition, List<Integer> stock, List<Integer> cost) {
        int left = 0, right = (int) 1e9 + 7;

        int b = budget;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            boolean flag = true;
            budget = b;
            for (int i = 0; i < composition.size(); i++) {
                long all = composition.get(i) * (long) mid;
                long need = all > stock.get(i) ? all - stock.get(i) : 0;
                long c = need * cost.get(i);

                if (budget < c) {
                    flag = false;
                    break;
                }
                budget -= c;
            }
            if (flag) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }



    public long maximumSum(List<Integer> nums) {
        long ans = 0;
        int n = nums.size();
        long[] sum = new long[n + 1];
        for (int i = 0; i < nums.size(); i++) {
            int c = core(i + 1);
            sum[c] += nums.get(i);
            ans = Math.max(ans, sum[c]);
        }
        return ans;
    }

    //求去除了平方数的值
    private int core(int n) {
        int res = 1;
        for (int i = 2; i * i <= n; i++) {
            int e = 0;
            while (n % i == 0) {
                e ^= 1;
                n /= i;
            }
            if (e == 1) {
                res *= i;
            }
        }
        if (n > 1) {
            res *= n;
        }
        return res;
    }

    public static void main(String[] args) {
        wk363 w = new wk363();
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(10, 10, 1, 5));
        list.add(Arrays.asList(10, 10, 1, 5));

        w.maxNumberOfAlloys(4, 4, 17, list, Arrays.asList(9, 8, 2, 7), Arrays.asList(9, 2, 6, 10));
    }


}
