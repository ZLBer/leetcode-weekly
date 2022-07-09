package weekly;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

public class wk294 {


    //ranking: 979 / 6640

    //简单题，注意用double不能用float精度不够
    public int percentageLetter(String s, char letter) {
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == letter) {
                c++;
            }
        }

        return (int) ((((double) c) / s.length()) * 100);
    }


    //中等题，先求差值，然后先满足差值最小的
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {
        int[] count = new int[capacity.length];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < count.length; i++) {
            priorityQueue.add(Math.max(capacity[i] - rocks[i], 0));
        }

        int ans = 0;
        while (!priorityQueue.isEmpty() && priorityQueue.peek() <= additionalRocks) {
            ans++;
            additionalRocks -= priorityQueue.poll();
        }

        return ans;
    }


    //中等题，现将点从左往右排序，判断两点之间的斜率是不是相同
    //注意计算斜率的时候用乘法判断
    public int minimumLines(int[][] stockPrices) {
        Arrays.sort(stockPrices, (a, b) -> a[0] - b[0]);
        if (stockPrices.length == 1) return 1;

        int ans = 1;
        int x = stockPrices[1][0] - stockPrices[0][0];
        int y = stockPrices[1][1] - stockPrices[0][1];
        for (int i = 2; i < stockPrices.length; i++) {
            int tx = stockPrices[i][0] - stockPrices[i - 1][0];
            int ty = stockPrices[i][1] - stockPrices[i - 1][1];
            if (x * ty != y * tx) {
                ans++;
            }
            x = tx;
            y = ty;
        }
        return ans;
    }



    //困难题，左右最小边界可以想到，但是前缀和的前缀和真的不好想
    public int totalStrength(int[] nums) {
        //先求出以i为最小值的左右边界
        Deque<Integer> deque = new ArrayDeque<>();
        int[] left = new int[nums.length];//左侧严格小于nums[i]的位置
        int[] right = new int[nums.length];//右侧小于或等于nums[i]的位置
        Arrays.fill(right, nums.length);

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] >= nums[i]) right[deque.pollLast()] = i;
            left[i] = deque.isEmpty() ? -1 : deque.peekLast();
            deque.addLast(i);
        }
        int mod = (int) 1e9 + 7;

        //计算前缀和\前缀和的前缀和
        long[] sum = new long[nums.length + 1];
        long[] ssum = new long[nums.length + 2];
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = (sum[i] + nums[i]) % mod;
            ssum[i + 2] = (ssum[i + 1] + sum[i + 1]) % mod;
        }

         //左右边界+前缀和的前缀和  优化成线性计算
        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            int l = left[i] + 1;
            int r = right[i] - 1;
            long all = (i - l + 1) * (ssum[r + 2] - ssum[i + 1]) - (r - i + 1) * (ssum[i + 1] - ssum[l]);
            all %= mod;
            res += (nums[i] * all);
            res %= mod;
        }
        return (int) (res + mod) % mod;
    }


    public static void main(String[] args) {

    }
}
