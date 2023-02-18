package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk332 {

    //ranking: 1061 / 4547


    //模拟
    public long findTheArrayConcVal(int[] nums) {
        long res = 0;
        for (int i = 0; i < nums.length / 2; i++) {
            int end = nums.length - i - 1;
            if (i == end) {
                res += nums[i];
            } else {
                String s = nums[i] + "" + nums[end];
                res += Long.parseLong(s);
            }
        }
        return res;
    }

    //不要被i < j 这个条件干扰，排序重新找就行
    public long countFairPairs(int[] nums, int lower, int upper) {
        long ans = 0;
        Arrays.sort(nums);
        for (int j = 0; j < nums.length; ++j) {
            int r = lowerBound(nums, j, upper - nums[j] + 1);
            int l = lowerBound(nums, j, lower - nums[j]);
            ans += r - l;
        }
        return ans;
    }

    private int lowerBound(int[] nums, int right, int target) {
        int left = -1; // 开区间 (left, right)
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // nums[left] < target
            // nums[right] >= target
            int mid = (left + right) >>> 1;
            if (nums[mid] < target)
                left = mid; // 范围缩小到 (mid, right)
            else
                right = mid; // 范围缩小到 (left, mid)
        }
        return right;
    }


    //最长是32
    public int[][] substringXorQueries(String s, int[][] queries) {

        Map<String, Integer> map = new HashMap<>();
        int[][] res = new int[queries.length][2];

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < 32 && i + j <= s.length(); j++) {
                String substring = s.substring(i, i + j);
                if (!map.containsKey(substring)) {
                    map.put(substring, i);
                }
            }
        }


        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            int val = query[0] ^ query[1];
            String s1 = Integer.toBinaryString(val);
            int left = map.getOrDefault(s1, -1);
            if (left == -1) {
                res[i] = new int[]{-1, -1};
            } else {
                res[i][0] = left;
                res[i][1] = left + s1.length() - 1;
            }
        }
        return res;
    }


    //前后缀
   static public int minimumScore(String s, String t) {
        int[] right = new int[s.length() + 1];
        int r = t.length() - 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (r >= 0 && c == t.charAt(r)) {
                right[i] = t.length() - r;
                r--;
            }
        }

        int l = 0;
        int lCount = 0;
        int res=t.length()-right[0];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (l < t.length() && c == t.charAt(l)) {
                lCount++;
                l++;
            }
            res = Math.min(res, Math.max(0,t.length() - right[i + 1] - lCount));
        }
        return res;
    }

    public static void main(String[] args) {
        minimumScore("cbedceeeccd","ed");
    }
}
