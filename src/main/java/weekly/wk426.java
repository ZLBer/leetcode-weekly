package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk426 {

    //遍历
    public int smallestNumber(int n) {

        int sub = 1;
        for (int i = 0; i < 31; i++) {
            sub *= 2;
            if (sub - 1 >= n) {
                return sub - 1;
            }
        }
        return -1;
    }

    // 贪心
    static public int getLargestOutlier(int[] nums) {

        Map<Integer, Integer> set = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            set.put(nums[i], set.getOrDefault(nums[i], 0) + 1);
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int last = sum - nums[i];
            if (last % 2 == 0 && set.getOrDefault(last / 2, 0) > 0) {
                if (nums[i] == last / 2 && set.get(last / 2) == 1) continue;
                ans = Math.max(ans, nums[i]);
            }
        }
        return ans;
    }
/*
     // dfs
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        Map<Integer, List<Integer>> map1 = help(edges1);
        Map<Integer, List<Integer>> map2 = help(edges2);

        int []count1=new int[edges1.length+1];
        int []count2=new int[edges2.length+1];
        for (int i = 0; i < count1.length; i++) {
            count1[i]=find(i,-1,map1,k)+1;
        }
        int max=0;
        for (int i = 0; i < count2.length; i++) {
            count2[i]=find(i,-1,map2,k-1)+1;
            max=Math.max(max,count2[i]);
        }
        int []ans=new int[edges1.length+1];
        for (int i = 0; i < count1.length; i++) {
            ans[i]=count1[i]+(k>0?max:0);
        }
        return ans;
    }

    int find(int cur, int parent, Map<Integer, List<Integer>> map1, int k) {
        if (k <= 0) {
            return 0;
        }
        int ans = 0;
        for (Integer next : map1.get(cur)) {
            if (next == parent) {
                continue;
            }
            ans += 1 + find(next, cur, map1, k - 1);
        }
        return ans;
    }

    Map<Integer, List<Integer>> help(int[][] edges) {
        Map<Integer, List<Integer>> map1 = new HashMap<>();
        for (int[] edge : edges) {
            if (!map1.containsKey(edge[0])) map1.put(edge[0], new ArrayList<>());
            if (!map1.containsKey(edge[1])) map1.put(edge[1], new ArrayList<>());
            map1.get(edge[0]).add(edge[1]);
            map1.get(edge[1]).add(edge[0]);
        }
        return map1;
    }*/


    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        List<Integer>[] g2 = buildTree(edges2);
        int[] cnt2 = new int[2];
        dfs(0, -1, 0, g2, cnt2);
        int max2 = Math.max(cnt2[0], cnt2[1]);

        List<Integer>[] g1 = buildTree(edges1);
        int[] cnt1 = new int[2];
        dfs(0, -1, 0, g1, cnt1);

        int[] ans = new int[g1.length];
        Arrays.fill(ans, max2);
        dfs1(0, -1, 0, g1, cnt1, ans);
        return ans;
    }

    private List<Integer>[] buildTree(int[][] edges) {
        List<Integer>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        return g;
    }

    private void dfs(int x, int fa, int d, List<Integer>[] g, int[] cnt) {
        cnt[d]++;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, d ^ 1, g, cnt);
            }
        }
    }

    private void dfs1(int x, int fa, int d, List<Integer>[] g, int[] cnt1, int[] ans) {
        ans[x] += cnt1[d];
        for (int y : g[x]) {
            if (y != fa) {
                dfs1(y, x, d ^ 1, g, cnt1, ans);
            }
        }
    }


    public static void main(String[] args) {
        getLargestOutlier(new int[]{6, -31, 50, -35, 41, 37, -42, 13});
    }

}
