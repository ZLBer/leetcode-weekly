package weekly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class wk314 {
    //ranking: 610 / 4838

    //简单题，遍历比较
    public int hardestWorker(int n, int[][] logs) {
        int pre = 0;
        int ans = Integer.MAX_VALUE;
        int time = 0;
        for (int i = 0; i < logs.length; i++) {
            if (logs[i][1] - pre > time || (logs[i][1] - pre == time && logs[i][0] < ans)) {
                ans = logs[i][0];
                time = logs[i][1] - pre;
            }
            pre = logs[i][1];
        }
        return ans;
    }

    //理解异或运算，a^a=0
    public int[] findArray(int[] pref) {
        int[] res = new int[pref.length];
        for (int i = 0; i < pref.length; i++) {
            res[i] = i==0?pref[i]:pref[i-1] ^ pref[i];
        }
        return res;
    }

    // 单调栈
  /*  static public String robotWithString(String s) {
        char[] chars = s.toCharArray();
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            while (!deque.isEmpty() && chars[deque.peekLast()] > chars[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        Deque<Integer> d = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (!deque.isEmpty() && deque.peekFirst() == i) {
                while (!d.isEmpty() && chars[d.peekLast()] <= chars[i]) {
                    sb.append(chars[d.pollLast()]);
                }
                sb.append(chars[deque.pollFirst()]);
                while (!deque.isEmpty() && !d.isEmpty() && chars[d.peekLast()] <= chars[deque.peekFirst()]) {
                    sb.append(chars[d.pollLast()]);
                }
            } else if (!deque.isEmpty() && deque.peekFirst() > i) {
                d.addLast(i);
            }
        }
        while (!d.isEmpty()) {
            sb.append(chars[d.pollLast()]);
        }
        return sb.toString();
    }*/

    //贪心写法
   static public String robotWithString(String s) {
        char[] chars = s.toCharArray();
        char []mins=new char[s.length()+1];
        mins[mins.length-1]='z';
        for (int i = chars.length - 1; i >= 0; i--) {
            if(mins[i+1]<chars[i]){
                mins[i]=mins[i+1];
            }else {
                mins[i]=chars[i];
            }
        }
        StringBuilder sb=new StringBuilder();
        Deque<Character> deque=new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            deque.addLast(chars[i]);
            while (!deque.isEmpty()&&deque.peekLast()<=mins[i+1]){
               sb.append(deque.pollLast());
           }
        }
        while (!deque.isEmpty()){
            sb.append(deque.pollLast());
        }

        return sb.toString();
    }




    //dp记录mod k的所有数目
    public int numberOfPaths(int[][] grid, int k) {
        int mod=(int)1e9+7;
        int[][][] dp = new int[grid.length][grid[0].length][k];

        dp[0][0][grid[0][0]%k]=1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j <grid[0].length; j++) {
                for (int kk = 0; kk < k; kk++) {
                    if(i-1>=0)  dp[i][j][(kk+grid[i][j])%k]+=dp[i-1][j][kk];
                    if(j-1>=0)  dp[i][j][(kk+grid[i][j])%k]+=dp[i][j-1][kk];
                    dp[i][j][(kk+grid[i][j])%k]%=mod;
                }
            }
        }
        return dp[grid.length-1][grid[0].length-1][0];
    }

    public static void main(String[] args) {
        robotWithString("bac");
    }
}
