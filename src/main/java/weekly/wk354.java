package weekly;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk354 {

    //模拟
    public int sumOfSquares(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums.length % (i + 1) == 0) {
                ans += nums[i] * nums[i];
            }
        }
        return ans;
    }


    //排序+滑窗
    public int maximumBeauty(int[] nums, int k) {
        Arrays.sort(nums);

        int ans = 0;
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            while (left < i && nums[left] + k < nums[i] - k) {
                left++;
            }

            ans = Math.max(i-left+1, ans);
        }
        return ans;
    }


    //枚举
    public int minimumIndex(List<Integer> nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            counter.put(nums.get(i), counter.getOrDefault(nums.get(i), 0) + 1);
        }
        int max = 0;
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            if (entry.getValue() > count) {
                max = entry.getKey();
                count = entry.getValue();
            }
        }
        if (count * 2 <= nums.size()) {
            return -1;
        }

        int left = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == max) {
                left++;
            }
            if (left * 2 > (i + 1) && (count - left) * 2 > (nums.size() - i - 1)) {
                return i;
            }
        }
        return -1;
    }


    //暴力 注意数据范围
    public int longestValidSubstring(String word, List<String> forbidden) {
        Map<Integer, Set<String>> map = new HashMap<>();
        for (String s : forbidden) {
            int length = s.length();
            if (!map.containsKey(length)) {
                map.put(length, new HashSet<>());
            }
            map.get(length).add(s);
        }
        int n = word.length();

        int ans = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(word.charAt(i));
            for (int j = sb.length() - 1; j >= 0 && (sb.length() - j <= 10); j--) {
                String sub = sb.substring(j, sb.length());
                if (map.getOrDefault(sub.length(), new HashSet<>()).contains(sub)) {
                    sb = new StringBuilder(sub.substring(1, sub.length()));
                    break;
                }
            }
            ans = Math.max(ans, sb.length());
        }
        return ans;
    }
}
