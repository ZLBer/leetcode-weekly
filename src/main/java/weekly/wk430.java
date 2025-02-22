package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk430 {


    public int minimumOperations(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 1; j < grid.length; j++) {
                if (grid[j][i] <= grid[j - 1][i]) {
                    ans += grid[j - 1][i] - grid[j][i] + 1;
                    grid[j][i] = grid[j - 1][i] + 1;
                }
            }
        }
        return ans;
    }

   /* public String answerString(String word, int numFriends) {
        int len = word.length() - numFriends + 1;
        String ans = "a";
        for (int i = 0; i <= word.length() - len; i++) {
            for (int j = 0; j < len; j++) {
                String sub = word.substring(i, i + len);
                if (ans.compareTo(sub) < 0) {
                    ans = sub;
                }
            }
        }
        if (numFriends != 1) {
            for (int i = word.length() - len + 1; i < word.length(); i++) {
                String sub = word.substring(i, word.length());
                if (ans.compareTo(sub) < 0) {
                    ans = sub;
                }
            }
        }

        return ans;
    }*/


    public String answerString(String word, int numFriends) {
        int max = word.length() - numFriends + 1;
        List<Integer> list = new ArrayList<>(word.length());
        char a = 'a';
        for (int i = 0; i < word.length(); i++) {
            list.add(i);
            if (word.charAt(i) > a) {
                a = word.charAt(i);
            }
        }
        if (numFriends == 1) {
            return word;
        }
        if (max == 1) {
            return String.valueOf(a);
        }
        for (int i = 0; i < max; i++) {
            list = help(list, word);
            if (list.size() == 1) {
                int start = list.get(0) - i - 1;
                return word.substring(start, Math.min(word.length(), start + max));
            }
        }
        int end = list.get(0);
        return word.substring(end - max, end);
    }

    List<Integer> help(List<Integer> list, String word) {
        char max = 'a';
        for (Integer index : list) {
            if (index >= word.length()) continue;
            if (word.charAt(index) > max) {
                max = word.charAt(index);
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (Integer index : list) {
            if (index >= word.length()) continue;
            if (word.charAt(index) == max) {
                ans.add(index + 1);
            }
        }
        return ans;
    }

    public long numberOfSubsequences(int[] nums) {
        int n = nums.length;
        long ans = 0;
        Map<Float, Integer> cnt = new HashMap<>();
        // 枚举 b 和 c
        for (int i = 4; i < n - 2; i++) {
            // 增量式更新，本轮循环只需枚举 b=nums[i-2] 这一个数
            // 至于更前面的 b，已经在前面的循环中添加到 cnt 中了，不能重复添加
            float b = nums[i - 2];
            // 枚举 a
            for (int j = 0; j < i - 3; j++) {
                cnt.merge(nums[j] / b, 1, Integer::sum);
            }

            float c = nums[i];
            // 枚举 d
            for (int j = i + 2; j < n; j++) {
                ans += cnt.getOrDefault(nums[j] / c, 0);
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        wk430 wk = new wk430();
        wk.answerString("bqbprgrg", 7);
    }
}
