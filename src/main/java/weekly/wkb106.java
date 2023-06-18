package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wkb106 {
    //模拟
    public boolean isFascinating(int n) {
        int[] count = new int[10];
        for (int i = 1; i <= 3; i++) {
            String num = new String((n * i) + "");
            for (char c : num.toCharArray()) {
                count[c - '0']++;
            }
        }

        if (count[0] > 0) return false;
        for (int i = 1; i < count.length; i++) {
            if (count[i] != 1) return false;
        }
        return true;
    }
/*
    public int longestSemiRepetitiveSubstring(String s) {
        char[] chars = s.toCharArray();
        List<Integer> list = new ArrayList<>();
        list.add(-1);
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                list.add(i);
            }
        }
        list.add(s.length());
        for (Integer integer : list) {
            System.out.println(integer);
        }
        int ans = 0;
        if (list.size() == 2) {
            ans = s.length();
        }
        for (int i = 1; i < list.size() - 1; i++) {
            ans = Math.max(ans, list.get(i + 1) - list.get(i - 1) + 1);
        }
        return ans;
    }*/

    //暴力
   /* public int longestSemiRepetitiveSubstring(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                int count = 0;
                for (int k = i + 1; k <= j; k++) {
                    if (s.charAt(k) == s.charAt(k - 1)) {
                        count++;
                    }
                }
                if (count <= 1) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }*/
    //滑动窗口
    static public int longestSemiRepetitiveSubstring(String s) {
        char[] chars = s.toCharArray();
        int left = 0;
        int same = 0;
        int ans = 0;
        for (int right = 1; right < chars.length; right++) {
            if (chars[right] == chars[right - 1]) {
                same++;
                if (same > 1) {
                    for (left++; chars[left] != chars[left - 1]; left++) {
                        same = 1;
                    }
                }
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


    //不管有没有碰撞，都是走d距离
    public int sumDistance(int[] nums, String s, int d) {
        int mod = (int) 1e9 + 7;
        long[] distance = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (s.charAt(i) == 'R') {
                distance[i] = (long) nums[i] + d;
            } else {
                distance[i] = (long) nums[i] - d;
            }
        }
//        for (int i = 0; i < nums.length; i++) {
//            System.out.println(nums[i]);
//        }
        Arrays.sort(distance);

        long sum = 0;
        for (int i = 1; i < nums.length; i++) {
            sum += distance[i] - distance[0];
        }
        long ans = 0;
        ans += sum;
        ans %= mod;
        for (int i = 1; i < nums.length; i++) {
            long dis = distance[i] - distance[i - 1];
            sum -= (dis * (nums.length - i));
            ans += sum;
            ans %= mod;
        }
        return (int) ans;
    }


    //脑筋急转弯
    public List<Integer> goodSubsetofBinaryMatrix(int[][] grid) {
        Map<Integer, Integer> idx = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            int mask = 0;
            for (int j = 0; j < grid[i].length; j++)
                mask |= grid[i][j] << j;
            idx.put(mask, i);
        }
        if (idx.containsKey(0)) {
            return Arrays.asList(idx.get(0));
        }
        for (Map.Entry<Integer, Integer> e1 : idx.entrySet()) {
            for (Map.Entry<Integer, Integer> e2 : idx.entrySet()) {
                if ((e1.getKey() & e2.getKey()) == 0) {
                    int i = e1.getValue(), j = e2.getValue();
                    return i < j ? Arrays.asList(i, j) : Arrays.asList(j, i);
                }
            }
        }
        return new ArrayList<>();
    }
}
