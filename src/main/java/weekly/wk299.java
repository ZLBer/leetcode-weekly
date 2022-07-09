package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk299 {


    //ranking: 466 / 6792
    //简单题，知道怎么判断对角线就行
    public boolean checkXMatrix(int[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == j || i + j + 1 == grid[0].length) {
                    if (grid[i][j] == 0) return false;
                } else {
                    if (grid[i][j] != 0) return false;
                }
            }
        }
        return true;
    }


    //中等题，像极了打家劫舍？
    public int countHousePlacements(int n) {
        int mod = (int) 1e9 + 7;

        long[] dp = new long[2];
        dp[0] = 1;//0也是一种情况
        dp[1] = 0;
        for (int i = 0; i < n; i++) {
            long[] ndp = new long[2];
            ndp[0] = dp[0] + dp[1];
            ndp[1] = dp[0];
            ndp[0] %= mod;
            ndp[1] %= mod;
            dp = ndp;
        }
        //最后的结果做乘法 是组合
        long res = (dp[0] + dp[1]) * (dp[0] + dp[1]);
        res %= mod;

        return (int) res;

    }


    //
    static public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        return Math.max(help(nums1, nums2), help(nums2, nums1));
    }

    static public int help(int[] nums1, int[] nums2) {


        int res = 0;
        int[] num = new int[nums1.length];
        //求差值
        for (int i = 0; i < nums1.length; i++) {
            res += nums1[i];
            num[i] = nums2[i] - nums1[i];
        }

        int sum = 0;
        int left = 0;
        int max = 0;
        //最大子数组和  滑动窗口，不用滑动窗口也行
     /*   for (int i = 0; i < num.length; i++) {
            sum += num[i];
            while (sum < 0) {
                sum -= num[left++];
            }
            max = Math.max(sum, max);

        }*/
        for (int i = 0; i < num.length; i++) {
            sum += num[i];
            if (sum < 0) {
                sum = 0;
            }
            max = Math.max(sum, max);

        }
        return res + max;
    }

    //困难题，太暴力了过不了的..看了大佬的提示自己做出来了
    //枚举+分类讨论，即枚举的时候判断是不是子树，
    public int minimumScore(int[] nums, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        int[] xors = new int[nums.length];
        dfs(xors, 0, map, new boolean[nums.length], nums);


        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (i == 0 || j == 0) continue;//跳过根节点
                if (i == j) continue;
                int max = 0;
                int min=Integer.MAX_VALUE;
                //是子树的情况
                if (set.get(i).contains(j)) {
                    max = Math.max(xors[i] ^ xors[j], Math.max(xors[j], xors[0] ^ xors[i]));
                    min=Math.min(xors[i] ^ xors[j], Math.min(xors[j], xors[0] ^ xors[i]));
                } else if (set.get(j).contains(i)) {
                    max = Math.max(xors[j] ^ xors[i], Math.max(xors[i], xors[0] ^ xors[j]));
                    min = Math.min(xors[j] ^ xors[i], Math.min(xors[i], xors[0] ^ xors[j]));
               //不是子树的情况
                } else {
                    max = Math.max(xors[i], Math.max(xors[j], xors[0] ^ xors[j] ^ xors[i]));
                    min = Math.min(xors[i], Math.min(xors[j], xors[0] ^ xors[j] ^ xors[i]));
                }
                ans = Math.min(ans, max-min);
            }
        }
        return ans;

    }
   //记录子树  应该有更好的记录办法
    Map<Integer, Set<Integer>> set = new HashMap<>();

    //dfs求每个点及其子树的异或
    int dfs(int[] xors, int index, Map<Integer, List<Integer>> map, boolean[] visited, int[] nums) {
        int xor = nums[index];
        Set<Integer> s = new HashSet<>();
        s.add(index);
        visited[index]=true;
        for (Integer next : map.getOrDefault(index, new LinkedList<>())) {
            if (visited[next]) continue;
            visited[next] = true;
            xor ^= dfs(xors, next, map, visited, nums);
            s.addAll(set.get(next));

        }
        xors[index] = xor;
        set.put(index, s);
        return xor;
    }

    public static void main(String[] args) {
       wk299 w=new wk299();
       w.minimumScore(new int[]{1,5,5,4,11},new int[][]{
               {0,1},{1,2},{1,3},{3,4}
       });
    }

  /*  public int minimumScore(int[] nums, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < edges.length; i++) {
            for (int j = i; j < edges.length; j++) {
                if (i == j) continue;
                res = Math.min(res, help(nums, edges, i, j, map));
            }
        }
        return res;
    }

    int help(int[] nums, int[][] edges, int a, int b, Map<Integer, List<Integer>> map) {
        int[] edgeA = edges[a];
        int[] edgeB = edges[b];
        List<Integer> list = new ArrayList<>();
        list.add(edgeA[0]);
        list.add(edgeA[1]);
        list.add(edgeB[0]);
        list.add(edgeB[1]);
        int max = 0, min = Integer.MAX_VALUE;
        for (Integer integer : list) {
            int bfs = bfs(nums, a, b, edges,integer, map);
            max = Math.max(max, bfs);
            min = Math.min(min, bfs);
        }
        System.out.println(a+" "+b);
        System.out.println(max +"" + min);
        return max - min;
    }

    int bfs(int[] nums, int a, int b,int[][] edges, int from, Map<Integer, List<Integer>> map) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(from);
        int sum = 0;
        boolean[] visited = new boolean[nums.length];
        visited[from] = true;
        while (!queue.isEmpty()) {
            Integer index = queue.poll();
            sum ^= nums[index];
            for (Integer next : map.getOrDefault(index, new ArrayList<>())) {
                if (index == edges[a][0] && next == edges[a][1]) continue;
                if (index == edges[a][1] && next == edges[a][0]) continue;
                if (index == edges[b][0] && next == edges[b][1]) continue;
                if (index == edges[b][1] && next == edges[b][0]) continue;

                if (visited[next]) continue;
                queue.add(next);
                visited[next] = true;
            }
        }
        return sum;
    }*/


}
