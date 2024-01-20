package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class wk380 {

    // 遍历
    public int maxFrequencyElements(int[] nums) {
        int max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            max = Math.max(max, map.get(num));
        }
        int ans = 0;
        for (Integer value : map.values()) {
            if (value == max) {
                ans += value;
            }
        }
        return ans;
    }

    // kmp+二分
    public static List<Integer> findAll(String target, String pattern) {
        int[] next = getNext(pattern);
        int i = 0, j = 0;
        List<Integer> result = new ArrayList<>();
        while (i < target.length()) {
            if (target.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    result.add(i - j);
                    j = next[j];
                }
            } else {
                j = next[j];
                if (j == -1) {
                    i++;
                    j++;
                }
            }
        }
        return result;
    }

    private static int[] getNext(String pattern) {
        int[] next = new int[pattern.length() + 1];
        next[0] = -1;
        int i = 0, j = -1;
        while (i < pattern.length()) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    TreeSet<Integer> help(String s, String a) {
        TreeSet<Integer> l = new TreeSet<>();
        for (Integer i : findAll(s, a)) {
            l.add(i);
        }
        return l;
    }


    public List<Integer> beautifulIndices(String s, String a, String b, int k) {

        TreeSet<Integer> al = help(s, a);

        TreeSet<Integer> bl = help(s, b);

        List<Integer> ans = new ArrayList<>();
        for (Integer i : al) {
            Integer ceiling = bl.ceiling(i - k);
            if (ceiling != null && ceiling <= i + k) {
                ans.add(i);
            }
        }
        return ans;
    }


    // 二分+数位dp、找规律
    public long findMaximumNumber(long k, int x) {
        this.x = x;
        long left = 0;
        long right = (k + 1) << x;
        //开区间二分
        while (left + 1 < right) {
            long mid = (left + right) >>> 1;
            if (countDigitOne(mid) <= k) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int x;
    private long num;
    private long memo[][];

    private long countDigitOne(long num) {
        this.num = num;
        int m = 64 - Long.numberOfLeadingZeros(num);
        memo = new long[m][m + 1];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(m - 1, 0, true);
    }

    private long dfs(int i, int cnt1, boolean isLimit) {
        if (i < 0) return cnt1;
        if (!isLimit && memo[i][cnt1] != -1) return memo[i][cnt1];
        int up = isLimit ? (int) (num >> i & 1) : 1;
        long res = 0;
        for (int d = 0; d <= up; d++) { // 枚举要填入的数字 d
            res += dfs(i - 1, cnt1 + (d == 1 && (i + 1) % x == 0 ? 1 : 0), isLimit && d == up);
        }
        if (!isLimit) memo[i][cnt1] = res;
        return res;
    }


    public static void main(String[] args) {
        wk380 w = new wk380();
        w.x = 1;
        w.countDigitOne(10);

    }
}
