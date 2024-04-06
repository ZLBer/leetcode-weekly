package weekly;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class wk391 {
    // 遍历
    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int num = x;
        int ans = 0;
        while (x > 0) {
            ans += x % 10;
            x /= 10;
        }
        return num % ans == 0 ? ans : -1;
    }

    //模拟
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        int ans = numBottles;
        while (true) {
            if (numBottles >= numExchange) {
                numBottles -= numExchange;
                ans++;
                numBottles += 1;
                numExchange++;
            } else {
                return ans;
            }
        }
    }

    //遍历
    public long countAlternatingSubarrays(int[] nums) {

        long ans = 1;
        int left = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                left = i;
            }
            ans += (i - left + 1);
        }
        return ans;
    }


    //数学 切比雪夫
    public int minimumDistance(int[][] points) {
        TreeMap<Integer, Integer> xs = new TreeMap<>();
        TreeMap<Integer, Integer> ys = new TreeMap<>();
        for (int[] p : points) {
            xs.merge(p[0] + p[1], 1, Integer::sum);
            ys.merge(p[1] - p[0], 1, Integer::sum);
        }
        int ans = Integer.MAX_VALUE;
        for (int[] p : points) {
            int x = p[0] + p[1], y = p[1] - p[0];
            if (xs.get(x) == 1) xs.remove(x);
            else xs.merge(x, -1, Integer::sum);
            if (ys.get(y) == 1) ys.remove(y);
            else ys.merge(y, -1, Integer::sum);
            ans = Math.min(ans, Math.max(xs.lastKey() - xs.firstKey(), ys.lastKey() - ys.firstKey()));
            xs.merge(x, 1, Integer::sum);
            ys.merge(y, 1, Integer::sum);
        }
        return ans;
    }



    public static void main(String[] args) {
        wk391 w = new wk391();
        w.minimumDistance(new int[][]{
                {3, 10}, {5, 15}, {10, 2}, {4, 4}
        });
    }

}
