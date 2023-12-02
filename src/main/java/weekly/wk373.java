package weekly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class wk373 {
    //模拟
    static public boolean areSimilar(int[][] mat, int k) {
        k %= mat[0].length;
        int[][] ans = new int[mat.length][mat[0].length];

        for (int i = 0; i < mat.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < mat[0].length; j++) {
                    int nj = (j + k) % (mat[0].length);
                    ans[i][nj] = mat[i][j];
                }
            } else {
                for (int j = 0; j < mat[0].length; j++) {
                    int nj = (j - k + mat[0].length) % (mat[0].length);
                    ans[i][nj] = mat[i][j];
                }
            }
        }
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != ans[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


   /* static public int beautifulSubstrings(String s, int k) {
        int[] count = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            count[i + 1] += count[i];
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count[i + 1]++;
            }
        }
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                int vowels = count[j + 1] - count[i];
                int consonants = (j - i) + 1 - vowels;
                if (vowels == consonants && (vowels * consonants) % k == 0) {
                    ans++;
                }
            }
        }
        return ans;
    }*/

    //排序分组 +填充原位置
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add(new int[]{nums[i], i});
        }
        Collections.sort(list, (a, b) -> a[0] - b[0]);

        List<Integer> indexList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        indexList.add(list.get(0)[1]);
        numList.add(list.get(0)[0]);
        int pre = list.get(0)[0];
        int[] ans = new int[nums.length];
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i)[0] - pre <= limit) {

            } else {
                Collections.sort(indexList);
                for (int j = 0; j < indexList.size(); j++) {
                    ans[indexList.get(j)] = numList.get(j);
                }
                indexList = new ArrayList<>();
                numList = new ArrayList<>();
            }
            pre = list.get(i)[0];
            indexList.add(list.get(i)[1]);
            numList.add(list.get(i)[0]);
        }
        Collections.sort(indexList);
        for (int j = 0; j < indexList.size(); j++) {
            ans[indexList.get(j)] = numList.get(j);
        }
        return ans;
    }




    //枚举+前缀和
    public long beautifulSubstrings(String s, int k) {

        int[] count = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            count[i + 1] += count[i];
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count[i + 1]++;
            }
        }
        int n = s.length();
        int max = s.length() / 2;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            if ((i * i) % k == 0) {
                list.add(i * 2);
            }
        }

        int ans = 0;
        for (Integer len : list) {
            for (int i = 0; i+len <= s.length(); i++) {
                int vowels = count[i + len] - count[i];
                int consonants = (i+len-i) - vowels;
                if (vowels == consonants && (vowels * consonants) % k == 0) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
