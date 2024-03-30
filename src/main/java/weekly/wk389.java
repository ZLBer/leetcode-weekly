package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wk389 {

    // 模拟
    public boolean isSubstringPresent(String s) {
        Set<String> list = new HashSet<>();
        for (int i = 0; i < s.length() - 1; i++) {
            list.add(s.substring(i, i + 2));
        }
        String sb = new StringBuilder(s).reverse().toString();
        for (int i = 0; i < sb.length() - 1; i++) {
            if (list.contains(sb.substring(i, i + 2))) {
                return true;
            }
        }
        return false;

    }


    //前缀和 数学
    public long countSubstrings(String s, char c) {
        int[] count = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            count[i + 1] = count[i];
            if (s.charAt(i) == c) {
                count[i + 1]++;
            }
        }

        long ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                int left = count[i];
                int right = count[s.length()] - left;
                ans += right;
            }

        }
        return ans;
    }

    //
   static public int minimumDeletions(String word, int k) {
        int[] count = new int[26];
        for (int i = 0; i < word.length(); i++) {
            count[word.charAt(i) - 'a']++;
        }

        Arrays.sort(count);
       int[] pre = new int[count.length + 1];
       for (int i = 0; i < count.length; i++) {
           pre[i + 1] = count[i] + pre[i];
       }
        int i = 0;
        while (count[i] == 0) {
            i++;
        }
        int ans = Integer.MAX_VALUE;
        for (; i < count.length; i++) {
            if (i > 0 && count[i] == count[i - 1]) continue;
            int del = 0;
            for (int j = i + 1; j < count.length; j++) {
                if (count[j] - count[i] > k) {
                    del += count[j] - count[i] - k;
                }
            }
            del += pre[i];
            ans = Math.min(ans, del);
        }
        return ans;
    }

    public static void main(String[] args) {
        minimumDeletions("aabcaba",0);
    }
}
