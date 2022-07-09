package weekly;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class wk295 {

   //679 / 6447
    //简单题，统计次数求最大值就行
    public int rearrangeCharacters(String s, String target) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        int[] need = new int[26];
        for (char c : target.toCharArray()) {
            need[c - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < count.length; i++) {
            if (need[i] == 0) continue;
            min = Math.min(min, count[i] / need[i]);
        }
        return min;
    }


    //中等题，写吐了，最后改成BigDecimal才解决了浮点问题
    //改成long，不管后面多余的小数位
    public String discountPrices(String sentence, int discount) {
        String[] s = sentence.split(" ");
        for (int i = 0; i < s.length; i++) {
            String t = s[i];
            if (!t.startsWith("$") || t.length() == 1) continue;
            boolean flag = true;
            for (int j = 1; j < t.toCharArray().length; j++) {
                if (!Character.isDigit(t.charAt(j))) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Long num = Long.parseLong(t.substring(1)) * (100 - discount); //原始价格
                s[i] = "$" + num / 100 +'0';
                num %= 10;
                s[i] += ((num < 10) ?  "0"+num : num);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length - 1; i++) {
            sb.append(s[i] + " ");
        }
        sb.append(s[s.length - 1]);
        return sb.toString();
    }

    //中等题，一轮一轮模拟
    //单调栈
    public int totalSteps(int[] nums) {
        Deque<int[]> deque = new LinkedList<>();
        int ans=0;
        for (int num : nums) {
            int maxT=0;
            while (!deque.isEmpty()&&deque.peekLast()[0]<=num){
                maxT=Math.max(maxT,deque.pollLast()[1]);
            }
            maxT=deque.isEmpty()?0:maxT+1;
            ans=Math.max(maxT,ans);
            deque.addLast(new int[]{num,maxT});
        }
        return ans;
    }

   /* static public int totalSteps(int[] nums) {
        int ans = 0;

        TreeSet<Integer> a = new TreeSet<>();
        TreeMap<Integer, Integer> b = new TreeMap<>();
        TreeMap<Integer, Integer> c = new TreeMap<>();

        for (int i = 0; i < nums.length; i++) {
            a.add(i);
        }
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) b.put(i, i + 1);
        }
        while (!b.isEmpty()) {
            ans++;
            for (Map.Entry<Integer, Integer> entry : b.entrySet()) {
                a.remove(entry.getValue());
            }
            for (Map.Entry<Integer, Integer> entry : b.entrySet()) {
                Integer last = a.higher(entry.getKey());
                if (last == null) continue;
                if (nums[last] < nums[entry.getKey()]) {
                    c.put(entry.getKey(), last);
                }
            }
            b = new TreeMap<>(c);
            c.clear();

        }
        return ans;
    }*/


    //困难题，不困难，优先级队列弹出最小花费即可，不然会TLE
    //或者可以01bfs，遇到0放在前面，遇到1放在后面，每次从前面取，这样每次取到的都是花费最小的，省去优先队列logn的开销
    int[][] moves = new int[][]{
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}
    };

    public int minimumObstacles(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m + 1][n + 1];


        //用pq优化可以过
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        for (int[] ints : dp) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        dp[0][0] = grid[0][0];
        queue.add(new int[]{0, 0, grid[0][0]});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            if (poll[0] == m - 1 && poll[1] == n - 1) {
                return poll[2];
            }
            //System.out.println(poll[0] + " " + poll[1]);
            for (int[] move : moves) {
                int nx = move[0] + poll[0];
                int ny = move[1] + poll[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    if (dp[nx][ny] <= poll[2] + grid[nx][ny]) continue;
                    dp[nx][ny] = poll[2] + grid[nx][ny];
                    queue.add(new int[]{nx, ny, dp[nx][ny]});
                }
            }
        }
        return dp[m - 1][n - 1];
    }


}
