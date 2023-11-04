package weekly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class wkb115 {

    //模拟
    public List<Integer> lastVisitedIntegers(List<String> words) {
        List<Integer> nums = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        int k = 0;
        for (String word : words) {
            if (word.equals("prev")) {
                k++;
                if (nums.size() - k >= 0) {
                    res.add(nums.get(nums.size() - k));
                } else {
                    res.add(-1);
                }
            } else {
                k = 0;
                nums.add(Integer.parseInt(word));
            }
        }
        return res;
    }




    //遍历即可
    /* public List<String> getWordsInLongestSubsequence(int n, String[] words, int[] groups) {
         int pre = groups[0];
         List<String> res = new ArrayList<>();
         res.add(words[0]);
         for (int i = 1; i < groups.length; i++) {
             if (groups[i] != pre) {
                 res.add(words[i]);
                 pre = groups[i];
             }
         }
         return res;
     }
 */

//    class Pack {
//        String s;
//        int index;
//
//        public Pack(String s, int index) {
//            this.s = s;
//            this.index = index;
//        }
//    }
//
//    public List<String> getWordsInLongestSubsequence(int n, String[] words, int[] groups) {
//
//        List<List<Pack>> lists = new ArrayList<>();
//        for (int i = 0; i < 11; i++) {
//            lists.add(new ArrayList<>());
//        }
//        for (int i = 0; i < words.length; i++) {
//            lists.get(words[i].length()).add(new Pack(words[i], i));
//        }
//        List<String> res=new ArrayList<>();
//        for (List<Pack> list : lists) {
//            List<String> help = help(list, groups);
//            if (help.size()>res.size()){
//                res=help;
//            }
//        }
//        return res;
//
//    }
//
//    List<String> help(List<Pack> list, int[] groups) {
//
//        if (list.size()==0){
//            return new ArrayList<>();
//        }
//        int[] ans = new int[list.size()];
//        int[] pre = new int[list.size()];
//        int max = -1, maxIndex = -1;
//        for (int i = 0; i < list.size(); i++) {
//            Pack pack = list.get(i);
//            ans[i] = 1;
//            pre[i] = i;
//            for (int j = 0; j < i; j++) {
//                if (groups[pack.index] != groups[list.get(j).index] && check(pack.s, list.get(j).s)) {
//                    if (ans[j] + 1 > ans[i]) {
//                        ans[i] = ans[j] + 1;
//                        pre[i] = j;
//                    }
//                }
//            }
//            if (ans[i] > max) {
//                max = ans[i];
//                maxIndex = i;
//            }
//        }
//
//        List<String> res = new ArrayList<>();
//
//        for (int i = maxIndex; ; ) {
//            res.add(0,list.get(i).s);
//            if (pre[i] == i) {
//                break;
//            }
//            i = pre[i];
//
//        }
//        return res;
//    }


    //不用分类 直接动态规划
    public List<String> getWordsInLongestSubsequence(int n, String[] words, int[] groups) {
        int[] f = new int[n];
        int[] from = new int[n];
        int mx = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (f[j] > f[i] && groups[j] != groups[i] && ok(words[i], words[j])) {
                    f[i] = f[j];
                    from[i] = j;
                }
            }
            f[i]++; // 加一写在这里
            if (f[i] > f[mx]) {
                mx = i;
            }
        }

        int m = f[mx];
        List<String> ans = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            ans.add(words[mx]);
            mx = from[mx];
        }
        return ans;
    }

    private static boolean ok(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        boolean diff = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (diff) return false;
                diff = true;
            }
        }
        return diff;
    }


    boolean check(String a, String b) {
        int ans = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                ans++;
            }
        }
        return ans == 1;
    }

    public static void main(String[] args) {
        wkb115 w=new wkb115();
        w.getWordsInLongestSubsequence(3,new String[]{"bab","dab","cab"},new int[]{1,2,2});
    }



}
