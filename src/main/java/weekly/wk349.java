package weekly;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class wk349 {
    //模拟
    public int findNonMinOrMax(int[] nums) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        for (int num : nums) {
            if (num != min && num != max) {
                return num;
            }
        }
        return -1;
    }

    public String smallestString(String s) {
        char[] chars = s.toCharArray();
        int index = s.length();
        //找到第一个不等于a的
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != 'a') {
                index = i;
                break;
            }
        }
        //若都是a，则也要替换一个
        if (index == s.length()) {
            chars[s.length() - 1] = 'z';
            return new String(chars);
        }
        //碰到a就返回
        for (int i = index; i < s.length(); i++) {
            if (chars[i] == 'a') {
                break;
            } else {
                chars[i] = (char) (chars[i] - 1);
            }
        }
        return new String(chars);
    }


    //动态规划，考虑旋转n次
    public long minCost(int[] nums, int x) {
        //一次不转
        long ans = 0;
        for (int num : nums) {
            ans += num;
        }

        int[] dp = new int[nums.length];
        //一次不转就是原来的价格
        for (int i = 0; i < nums.length; i++) {
            dp[i] = nums[i];
        }
        //转d次
        for (int d = 1; d < nums.length; d++) {
            long temp = (long) d * x;
            for (int i = 0; i < nums.length; i++) {
                //比较最小的一次替换
                dp[i] = Math.min(nums[(i + d) % nums.length], dp[i]);
                temp += dp[i];
            }
            ans = Math.min(temp, ans);
        }
        return ans;
    }


    static public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int[][] comp = new int[nums1.length][4];
        for (int i = 0; i < nums1.length; i++) {
            comp[i] = new int[]{nums1[i], nums2[i]};
        }
        Arrays.sort(comp, (a, b) -> a[0] - b[0]);


        int[][] qq = new int[queries.length][3];
        for (int i = 0; i < queries.length; i++) {
            qq[i] = new int[]{queries[i][0], queries[i][1], i};
        }
        Arrays.sort(qq, (a, b) -> b[0] - a[0]);
        int[] ans = new int[queries.length];
        int q = 0;
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i = comp.length - 1; i >= 0; i--) {
            //查询的num1比这个要大，说明当前已不满足条件
            while (q < queries.length && qq[q][0] > comp[i][0]) {
                Map.Entry<Integer, Integer> entry = treeMap.ceilingEntry(qq[q][1]);
                if (entry == null) {
                    ans[qq[q][2]] = -1;
                } else {
                    ans[qq[q][2]] = entry.getValue();
                }
                q++;
            }


            int num2 = comp[i][1];
            int max = num2 + comp[i][0];

            //找一个比num2大的，继承其最大值
            Map.Entry<Integer, Integer> up = treeMap.ceilingEntry(num2);
            if (up != null) {
                max = Math.max(up.getValue(), max);
            }

            //将比num2小的且和没有num2的大的都删除掉，即表示num2可以代表这些数了
            while (treeMap.floorEntry(num2) != null && treeMap.floorEntry(num2).getValue() <= max) {
                treeMap.remove(treeMap.floorKey(num2));
            }
            treeMap.put(num2, max);
        }

        while (q < queries.length) {
            Map.Entry<Integer, Integer> entry = treeMap.ceilingEntry(qq[q][1]);
            if (entry == null) {
                ans[qq[q][2]] = -1;
            } else {
                ans[qq[q][2]] = entry.getValue();
            }
            q++;
        }
        return ans;
    }

    public static void main(String[] args) {
        maximumSumQueries(new int[]{4, 3, 1, 2}, new int[]{2, 4, 9, 5}, new int[][]{
                {4, 1}, {1, 3}, {2, 5}
        });
    }
}
