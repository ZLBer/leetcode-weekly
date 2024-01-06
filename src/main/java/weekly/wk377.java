package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class wk377 {

    // 模拟
    public int[] numberGame(int[] nums) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int num : nums) {
            priorityQueue.add(num);
        }
        int[] res = new int[nums.length];
        int i = 0;
        while (!priorityQueue.isEmpty()) {
            Integer alice = priorityQueue.poll();
            Integer bob = priorityQueue.poll();

            res[i++] = bob;
            res[i++] = alice;
        }
        return res;

    }

    //哈希
    public int maximizeSquareArea(int m, int n, int[] hBars, int[] vBars) {
        Arrays.sort(hBars);
        Arrays.sort(vBars);
        Set<Integer> hList = help(hBars, m);
        Set<Integer> vList = help(vBars, n);
        long ans = -1;
        int mod = (int) 1e9 + 7;
        for (Integer high : hList) {
            if (vList.contains(high)) {
                ans = Math.max(((long) high * high), ans);
            }
        }
        return (int)( ans%mod);
    }
    Set<Integer> help(int[] hBars, int max) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < hBars.length; i++) {
            set.add(hBars[i] - 1);
            for (int j = 0; j < i; j++) {
                set.add(hBars[i] - hBars[j]);
            }
        }
        set.add(max - 1);
        for (int i = 0; i < hBars.length; i++) {
            set.add(max - hBars[i]);
        }
        return set;
    }


    // floyd
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        long[][] f = new long[26][26];

        for (long[] ints : f) {
            Arrays.fill(ints, Long.MAX_VALUE / 2);
        }
        for (int i = 0; i < original.length; i++) {
            f[original[i] - 'a'][changed[i] - 'a'] = Math.min(cost[i], f[original[i] - 'a'][changed[i] - 'a']);
        }

        for (int i = 0; i < f.length; i++) {
            f[i][i] = 0;
        }

        int n = 26;
        // Floyd计算剩下节点的最短路径
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            long v = f[source.charAt(i) - 'a'][target.charAt(i) - 'a'];
            if (v == Long.MAX_VALUE / 2) return -1;
            ans += v;
        }
        return ans;
    }

    public static void main(String[] args) {
        wk377 w = new wk377();
        // w.maximizeSquareArea(4, 3, new int[]{2, 3}, new int[]{2});

        w.minimumCost("abcd", "acbe", new char[]{
                'a', 'b', 'c', 'c', 'e', 'd'
        }, new char[]{
                'b', 'c', 'b', 'e', 'b', 'e'
        }, new int[]{2, 5, 5, 1, 2, 20});
    }

}
