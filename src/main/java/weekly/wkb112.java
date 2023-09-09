package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class wkb112 {

    //区分奇偶统计次数
    public boolean canBeEqual(String s1, String s2) {
        int[][] count = new int[2][26];
        for (int i = 0; i < s1.length(); i++) {
            count[i % 2][s1.charAt(i) - 'a']++;
            count[i % 2][s2.charAt(i) - 'a']--;
        }
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i].length; j++) {
                if (count[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //和上一题一样
    public boolean checkStrings(String s1, String s2) {
        int[][] count = new int[2][26];
        for (int i = 0; i < s1.length(); i++) {
            count[i % 2][s1.charAt(i) - 'a']++;
            count[i % 2][s2.charAt(i) - 'a']--;
        }
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i].length; j++) {
                if (count[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }


    //hash+滑窗
    public long maxSum(List<Integer> nums, int m, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        long ans = 0;
        long sum = 0;
        for (int i = 0; i < nums.size(); i++) {
            map.put(nums.get(i), map.getOrDefault(nums.get(i), 0) + 1);
            sum += nums.get(i);
            if (i >= k) {
                sum -= nums.get(i - k);
                Integer count = map.get(nums.get(i - k));
                count--;
                if (count == 0) {
                    map.remove(nums.get(i - k));
                } else {
                    map.put(nums.get(i - k), count);
                }

            }
            if (i >= k - 1) {
                if (map.size() >= m) {
                    ans = Math.max(ans, sum);
                }
            }
        }
        return ans;
    }


    /*public int countKSubsequencesWithMaxBeauty(String s, int k) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']--;
        }

        if (k > 26) return 0;

        //先求出最大值是多少
        Arrays.sort(count);
        int c = count[k - 1];
        int left = k - 1, right = k - 1;
        for (; count[left] == c; ) {
            left--;
            if (left < 0) break;
        }
        for (; count[right] == c; ) {
            right++;
            if (right == count.length) break;
        }
        long ans = 1;
        int mod = (int) 1e9 + 7;
        for (int i = 0; i < k; i++) {
            ans *= (-count[i]);
            ans %= mod;
        }

        System.out.println((right - left - 1) + " " + (k - 1 - left));
        ans *= comnination(right - left - 1, k - 1 - left);
        ans %= mod;
        return (int) ans;
    }*/

    //统计次数+组合数
    public int countKSubsequencesWithMaxBeauty(String s, int k) {

        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        for (int c : count) {
            treeMap.put(c, treeMap.getOrDefault(c, 0) + 1);
        }

        long ans = 1;
        for (Map.Entry<Integer, Integer> entry : treeMap.descendingMap().entrySet()) {
            int c = entry.getKey(), num = entry.getValue();

            if (num >= k) {
                return (int) (ans * pow(c, k) % MOD * comnination(num, k) % MOD);
            }

            ans = ans * pow(c, num) % MOD;
            k -= num;
        }
        return 0;
    }

    private static final long MOD = (long) 1e9 + 7;


    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0)
                res = res * x % MOD;
            x = x * x % MOD;
        }
        return res;
    }
    //组合数 Cnm   m>n

    // m>=n
    public int comnination(int m, int n) {
        if (n == 0) return 1;
        long ans = 1;

        //都从小的开始 防止过早溢出
        for (int x = m - n + 1, y = 1; y <= n; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }

    public static void main(String[] args) {
        Set<Character> set = new HashSet<>();
        String s = "vdohsooaxshrhavgpqjgduhyvdt";
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        System.out.println(set.size());
    }
}
