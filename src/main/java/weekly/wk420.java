package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk420 {

    //模拟
    public List<String> stringSequence(String target) {
        List<String> ans = new ArrayList<>();
        String s = "";
        for (char c : target.toCharArray()) {
            int max = c - 'a';
            for (int i = 0; i <= max; i++) {
                ans.add(s + (char) ('a' + i));
            }
            s += c;
        }
        return ans;
    }

    // 前缀和
//    public int numberOfSubstrings(String s, int k) {
//        int[][] pre = new int[s.length() + 1][26];
//        for (int i = 0; i < s.length(); i++) {
//            for (int j = 0; j < pre[i].length; j++) {
//                pre[i + 1][j] = pre[i][j];
//            }
//            pre[i + 1][s.charAt(i) - 'a']++;
//        }
//
//        int ans = 0;
//        for (int i = 0; i < s.length(); i++) {
//            for (int j = 0; j < s.length(); j++) {
//
//                for (int l = 0; l < 26; l++) {
//                    if (pre[j + 1][l] - pre[i][l] >= k) {
//                        ans++;
//                        break;
//                    }
//                }
//            }
//        }
//        return ans;
//    }

    // 滑动窗口
    int numberOfSubstrings(String S, int k) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[26];
        for (char c : s) {
            cnt[c - 'a']++;
            while (cnt[c - 'a'] >= k) {
                cnt[s[left] - 'a']--;
                left++;
            }
            ans += left;
        }
        return ans;
    }




    static List<Integer> primeList = new ArrayList<>();

    static {
        int max = 1000000;
        isPrime(max);
    }

    public static boolean[] isPrime(int n) {
        boolean[] arr = new boolean[n + 1];
        // 1:质数   0:非质数
        Arrays.fill(arr, true);
        arr[1] = false;
        for (int i = 2; i <= n; i++) {
            if (arr[i]) {
                primeList.add(i);
                // 将i的倍数去除掉
                for (int j = i + i; j <= n; j += i) {
                    arr[j] = false;
                }
            }
        }
        return arr;
    }

    public static int getMaxTrueDivisor(int number) {
        for (Integer p : primeList) {
            if (number % p == 0) {
                return p;
            }
        }
        return number;
    }

    public int minOperations(int[] nums) {
        int last = nums[nums.length - 1];
        int ans = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (last >= nums[i]) {
                last = nums[i];
                continue;
            }
            int pre = getMaxTrueDivisor(nums[i]);
            if (pre > last) {
                return -1;
            }
            ans++;
            last = pre;
        }
        return ans;
    }

    boolean []ans;

    public boolean[] findAnswer(int[] parent, String s) {
        ans=new boolean[parent.length];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 1; i < parent.length; i++) {
            if (!map.containsKey(parent[i])) map.put(parent[i], new ArrayList<>());
            map.get(parent[i]).add(i);
        }
        dfs(0,map,s);
        return ans;
    }

    String dfs( int cur,Map<Integer, List<Integer>> map ,String s) {
        String sub="";
        for (Integer child : map.getOrDefault(cur,new ArrayList<>())) {
             sub+=dfs(child,map,s);
        }
        sub+=s.charAt(cur);
        if (help(sub)){
            ans[cur]=true;
        }
        return sub;
    }
    boolean help(String s){
        for (int i = 0; i < s.length()/2; i++) {
            if(s.charAt(i)!=s.charAt(s.length()-1-i)){
                return false;
            }
        }
        return true;
    }
}
