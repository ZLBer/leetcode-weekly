package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wkb107 {

    //暴力
    //字符串长度是2，可以不用这么麻烦
    public int maximumNumberOfStringPairs(String[] words) {
        int ans = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < words.length; i++) {
            if (set.contains(i)) continue;
            for (int j = i + 1; j < words.length; j++) {
                if (set.contains(j)) continue;
                String word = words[j];
                String rs = new StringBuilder(word).reverse().toString();
                if (words[i].equals(rs)) {
                    ans++;
                    set.add(i);
                    set.add(j);
                }
            }
        }
        return ans;
    }


    //找规律
    public int longestString(int x, int y, int z) {
        int ans = 0;
        int min = Math.min(x, y);


        //按照 min*(AA+BB)+z*AB 拼接

        ans += 4 * min + z * 2;

        //剩下的 AA或BB
        int left = Math.max(x, y) - min;
        boolean flag = true;

        //若之前有AA或BB 且 还有剩下的，那么就可以把AA放在最后或者把BB放在最前面
        if (x == 0 || y == 0) {
            flag = false;
        }
        if (left > 0 && flag) {
            ans += 2;
        }
        return ans;
    }


    //记忆化搜索
    public int minimizeConcatenatedLength(String[] words) {
        memo = new int[words.length][26 * 26];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }
        return help(words[0].charAt(0), words[0].charAt(words[0].length() - 1), 1, words) + words[0].length();
    }

    int[][] memo;

    int help(char left, char right, int index, String[] words) {
        if (index >= words.length) {
            return 0;
        }
        int key = (left - 'a') * 26 + (right - 'a');

        if (memo[index][key] != -1) {
            return memo[index][key];
        }

        String word = words[index];
        int len = Integer.MAX_VALUE;
        //放在左边
        int l = 0;
        if (word.charAt(word.length() - 1) == left) {
            l = 1;
        }
        len = Math.min(help(word.charAt(0), right, index + 1, words) + word.length() - l, len);

        //放在右边
        int r = 0;
        if (word.charAt(0) == right) {
            r = 1;
        }
        len = Math.min(len, help(left, word.charAt(word.length() - 1), index + 1, words) + word.length() - r);

        memo[index][key] = len;
        return len;
    }




    //滑动窗口
  static   public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int[][] qq = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            qq[i] = new int[]{queries[i] - x, queries[i], i};
        }
        Arrays.sort(qq, (a, b) -> a[0] - b[0]);
        Arrays.sort(logs, (a, b) -> a[1] - b[1]);

        int right=0;
        int left=0;
        Map<Integer,Integer> counter=new HashMap<>();
        int []res=new int[queries.length];
        for (int i = 0; i < qq.length; i++) {
            int l=qq[i][0];
            int r=qq[i][1];

            //向右滑到查询的截止时间
            while (right<logs.length&&logs[right][1]<=r){
                counter.put(logs[right][0],counter.getOrDefault(logs[right][0],0)+1);
                right++;
            }

            //左边开始滑动到查询的开始时间
            while (left<logs.length&&logs[left][1]<l){
                Integer count = counter.get(logs[left][0]);
                if(count==1){
                    counter.remove(logs[left][0]);
                }else {
                    counter.put(logs[left][0],count-1);

                }
                left++;
            }

            res[qq[i][2]]=n-counter.size();
        }
        return res;

    }

    public static void main(String[] args) {
        countServers(3,new int[][]{
                {1,3},{2,6},{1,5}
        },5,new int[]{10,11});
    }
}
