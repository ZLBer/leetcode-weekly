package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class wk312 {


    //ranking: 425 / 6638

    //混合排序即可
    public String[] sortPeople(String[] names, int[] heights) {
        int[][] he = new int[heights.length][2];
        for (int i = 0; i < heights.length; i++) {
            he[i][0] = heights[i];
            he[i][1] = i;
        }
        Arrays.sort(he, (a, b) -> b[0] - a[0]);
        String[] res = new String[names.length];
        for (int i = 0; i < he.length; i++) {
            res[i] = names[he[i][1]];
        }

        return res;
    }


    //找最大值最长连续子数组即可
    public int longestSubarray(int[] nums) {
        int count = 0;
        int ans = 1;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(nums[i], max);
        }

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] && nums[i] == max) {
                count++;
            } else {
                count = 1;
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }

    //正序遍历计算i位置的最长递减长度
    //逆序遍历计算i位置的最长递增长度
    public List<Integer> goodIndices(int[] nums, int k) {
        int[] up = new int[nums.length];
        up[0] = 1;
        int[] down = new int[nums.length];
        down[nums.length - 1] = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                up[i] = up[i - 1] + 1;
            } else {
                up[i] = 1;
            }
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= nums[i + 1]) {
                down[i] = down[i + 1] + 1;
            } else {
                down[i] = 1;
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i - 1 >= 0 && i + 1 < nums.length) {
                if (up[i - 1] >= k && down[i + 1] >= k) {
                    res.add(i);
                }
            }
        }
        return res;
    }

    //直接做会超时
   /* public int numberOfGoodPaths(int[] vals, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < vals.length; i++) {
            if (!map.containsKey(vals[i])) map.put(vals[i], new ArrayList<>());
            map.get(vals[i]).add(i);
        }
        List<int[]> list = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> integerListEntry : map.entrySet()) {
            for (int i = 0; i < integerListEntry.getValue().size(); i++) {
                for (int j = i + 1; j < integerListEntry.getValue().size(); j++) {
                    list.add(new int[]{integerListEntry.getValue().get(i), integerListEntry.getValue().get(j), integerListEntry.getKey(), 1, 0});
                }
            }
        }
        Map<Integer, List<Integer>> from = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            if (!from.containsKey(edges[i][0])) from.put(edges[i][0], new ArrayList<>());
            from.get(edges[i][0]).add(edges[i][1]);
            if (!from.containsKey(edges[i][1])) from.put(edges[i][1], new ArrayList<>());
            from.get(edges[i][1]).add(edges[i][0]);
        }

        Clist = list;

        help(from, 0, vals, new boolean[vals.length]);

        int ans = vals.length;
        for (int[] ints : list) {
            if (ints[3] == 1) {
                ans++;
            }
        }
        return ans;

    }

    List<int[]> Clist;

    Set<Integer> help(Map<Integer, List<Integer>> from, int cur, int[] vals, boolean[] visited) {

        Set<Integer> children = new HashSet<>();
        for (Integer next : from.getOrDefault(cur, new ArrayList<>())) {
            if (visited[next]) continue;
            visited[next] = true;
            Set<Integer> c = help(from, next, vals, visited);
            children.addAll(c);
        }
        children.add(cur);

        List<int[]> nList = new ArrayList<>();
        for (int[] ints : Clist) {
            //不在这里
            if ((!children.contains(ints[0]) && !children.contains(ints[1]))) {
                nList.add(ints);
                continue;
            } else if ((children.contains(ints[0]) && children.contains(ints[1]))) {
                if (ints[4] == 1) {
                    continue;
                }
                ints[4] = 1;
            }

            if (vals[cur] > ints[2]) {
                ints[3] = 0;
            } else {
                nList.add(ints);
            }
        }
        Clist = nList;
        return children;
    }*/
    //并查集
    class UnionFind {
        public final int[] parents;
        public int count;

        public UnionFind(int n) {
            this.parents = new int[n];
            reset();
        }

        public void reset() {
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
            }
            count = parents.length - 1;
        }

        public int find(int i) {
            int parent = parents[i];
            if (parent == i) {
                return i;
            } else {
                int root = find(parent);
                parents[i] = root;
                return root;
            }
        }

        public boolean union(int i, int j) {
            int r1 = find(i);
            int r2 = find(j);
            if (r1 != r2) {
                count--;
                parents[r1] = r2;
                return true;
            } else {
                return false;
            }
        }

  /*      void isolate(int x) {
            if (x != parents[x]) {
                parents[x] = x;
                count++;
            }
        }*/

    }


    //并查集从下到大一次连接，然后判断相同的点能不能联通即可，这样就保证了两个点之间的val都小于
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        for (int i = 0; i < vals.length; i++) {
            if (!map.containsKey(vals[i])) map.put(vals[i], new ArrayList<>());
            map.get(vals[i]).add(i);
        }
        UnionFind uf = new UnionFind(vals.length);
        int ans = 0;
        Arrays.sort(edges, (a, b) -> Math.max(vals[a[0]], vals[a[1]]) - Math.max(vals[b[0]], vals[b[1]]));
        int i = 0;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            for (; i < edges.length && Math.max(vals[edges[i][0]], vals[edges[i][1]]) <= entry.getKey(); i++) {
                uf.union(edges[i][0], edges[i][1]);
            }
            Map<Integer, Integer> count = new HashMap<>();
            for (Integer e : entry.getValue()) {
                count.put(uf.find(e), count.getOrDefault(uf.find(e), 0) + 1);
            }

            for (Map.Entry<Integer, Integer> c : count.entrySet()) {
                ans+=(c.getValue()+1)*c.getValue()/2;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        wk312 w = new wk312();
        w.numberOfGoodPaths(new int[]{2, 4, 1, 2, 2, 5, 3, 4, 4}, new int[][]{
                {0, 1}, {2, 1}, {0, 3}, {4, 1}, {4, 5}, {3, 6}, {7, 5}, {2, 8}
        });
    }
}
