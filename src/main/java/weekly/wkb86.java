package weekly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class wkb86 {
    //简单题, set记录数组和
    public boolean findSubarrays(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            if (set.contains(sum)) return true;
            set.add(sum);
            sum -= nums[i - 1];
        }
        return false;
    }

    //中等题，进制转换+回文检测
    //可以直接返回false,n>=5时,n的(n-2)进制是12，不是回文
    public boolean isStrictlyPalindromic(int n) {
        for (int i = 2; i < n; i++) {
            if (!help(n, i)) return false;
        }
        return true;
    }

    boolean help(int n, int k) {
        StringBuilder sb = new StringBuilder();
        while (n >= k) {
            sb.append(n % k);
            n /= k;
        }
        if (n > 0) sb.append(n);
        for (int i = 0; i < sb.length() / 2; i++) {
            if (sb.charAt(i) != sb.charAt(sb.length() - i - 1)) return false;
        }
        return true;
    }


    //中等题，枚举子序列
    static public int maximumRows(int[][] mat, int cols) {

        //记录每一行的二进制表示
        int[] dp = new int[mat.length];
        for (int i = 0; i < mat.length; i++) {
            int k = 0;
            for (int j = 0; j < mat[i].length; j++) {
                k = (k << 1) + mat[i][j];
            }
            dp[i] = k;
            //System.out.println(Integer.toBinaryString(k));
        }

        //子序列枚举，没必要子序列，可以直接遍历
        int state = (1 << mat[0].length) - 1;
        int max = 0;
        for (int i = state; i > 0; i = (i - 1) & state) {
            if (Integer.bitCount(i) != cols) continue;
            int count = 0;
            //求是不是被覆盖的
            for (int j = 0; j < dp.length; j++) {
                if ((dp[j] | i) == i) count++;
            }
            // System.out.println(Integer.toBinaryString(i) + " " + count);
            max = Math.max(max, count);
        }
        return max;
    }


    //困难题，注意题目是连续，所以可以滑动窗口+treemap记录最大的chargeTime
  /*  public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int left = 0;
        long sumRunning = 0;
        int k = 0;
        TreeMap<Integer, Integer> counter = new TreeMap<>();
        int ans = 0;
        for (int i = 0; i < chargeTimes.length; i++) {
            sumRunning += runningCosts[i];
            k++;
            counter.put(chargeTimes[i], counter.getOrDefault(chargeTimes[i], 0) + 1);

            long c = counter.lastKey() + k * sumRunning;
            while (left <= i && c > budget) {
                Integer le = counter.get(chargeTimes[left]) - 1;
                if (le == 0) {
                    counter.remove(chargeTimes[left]);
                } else {
                    counter.put(chargeTimes[left], le);
                }
                sumRunning-=runningCosts[left];
                left++;
                k--;
                if (counter.size() == 0) {
                    break;
                }
                c = counter.lastKey() + k * sumRunning;
            }
            ans = Math.max(ans, k);
        }
        return ans;
    }*/

    //单调队列写法
   static public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int left = 0;
        long sumRunning = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < chargeTimes.length; i++) {
            sumRunning += runningCosts[i];
            while (!deque.isEmpty() && chargeTimes[deque.peekLast()] <= chargeTimes[i]) {
                deque.pollLast();
            }
            deque.addLast(i);

            long c = chargeTimes[deque.peekFirst()] + (i-left+1) * sumRunning;
            while (left <= i && c > budget) {

                sumRunning -= runningCosts[left];
                left++;
                while (!deque.isEmpty() && deque.peekFirst() < left) {
                    deque.pollFirst();
                }
                if (deque.isEmpty()) {
                    break;
                }

                c = chargeTimes[deque.peekFirst()] + (i-left+1) * sumRunning;
            }
            ans = Math.max(ans, (i-left+1));
        }
        return ans;
    }

    public static void main(String[] args) {
        maximumRobots(new int[]{11,12,19},new int[]{10,8,7},19);
    }
}
