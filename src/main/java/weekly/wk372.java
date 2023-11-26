package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class wk372 {

    //模拟
    public int findMinimumOperations(String s1, String s2, String s3) {
        int minLen = Math.min(s1.length(), Math.min(s2.length(), s3.length()));
        int ans = 0;
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) == s2.charAt(i) && s1.charAt(i) == s3.charAt(i)) {
                ans++;
            } else {
                break;
            }
        }
        if (ans == 0) return -1;
        return s1.length() - ans + s2.length() - ans + s3.length() - ans;
    }


    //数学
    public long minimumSteps(String s) {
        int left = 0;
        long ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '0') {
                ans += i - left;
                left++;
            }
        }
        return ans;
    }

    //贪心
    public int maximumXorProduct(long a, long b, int n) {
        long x = 0;
        int mod = (int) 1e9 + 7;
        for (int i = n - 1; i >= 0; i--) {
            long aa = a & (1L << i);
            long bb = b & (1L << i);
            //都是0直接加0
            if ((aa ^ bb) == 0) {
                if (aa == 0) {
                    x |= aa;
                }
            } else {
                //贪心
                if ((a ^ x) > (b ^ x)) {
                    x |= aa;
                } else {
                    x |= bb;
                }
            }
        }
        return (int) (((((a ^ x) % mod) * ((b ^ x) % mod))) % mod);
    }


   static public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int[] ans = new int[queries.length];
        List<int[]>[] left = new ArrayList[heights.length];
       for (int i = 0; i < left.length; i++) {
           left[i]=new ArrayList<>();
       }
        Arrays.fill(ans,-1);
        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0], b = queries[i][1];
            if (a > b) {
                int t = a;
                a = b;
                b = t;
            }
            if (a==b||heights[a] < heights[b]) {
                ans[i] = b;
            } else {
                left[b].add(new int[]{heights[a], i});
            }
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        for (int i = 0; i < heights.length; i++) {
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] < heights[i]) {
                ans[priorityQueue.poll()[1]] = i;
            }

            priorityQueue.addAll(left[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        leftmostBuildingQueries(new int[]{6, 4, 8, 5, 2, 7},new int[][]{
                {0,1},{0,3},{2,4},{3,4},{2,2}

        });
    }
}
