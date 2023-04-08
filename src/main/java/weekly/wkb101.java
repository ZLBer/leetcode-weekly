package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wkb101 {
    //哈希
    public int minNumber(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        Set<Integer> set = new HashSet<>();

        for (int i : nums2) {
            set.add(i);
        }

        for (int i : nums1) {
            if (set.contains(i)) return i;
        }

        return Math.min(nums1[0] * 10 + nums2[0], nums2[0] * 10 + nums1[0]);
    }


    //滑动窗口
    public int maximumCostSubstring(String s, String chars, int[] vals) {
        int[] scores = new int[26];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = i + 1;
        }
        for (int i = 0; i < chars.length(); i++) {
            char c = chars.charAt(i);
            scores[c - 'a'] = vals[i];
        }
        int max = 0;
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sum += scores[c - 'a'];
            max = Math.max(sum, max);
            if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }

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


    //并查集分组计算
    public long makeSubKSumEqual(int[] arr, int k) {
        UnionFind uf = new UnionFind(arr.length);
        for (int i = 0; i < arr.length; i++) {
            uf.union(i, (i + k) % arr.length);
        }
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int key = uf.find(i);
            if (!map.containsKey(key)) map.put(key, new ArrayList<>());
            map.get(key).add(arr[i]);
        }

        long ans = 0;
        for (List<Integer> list : map.values()) {
            Collections.sort(list);
            int mid = list.get(list.size() / 2);
            for (Integer num : list) {
                ans += Math.abs(num - mid);
            }
        }
        return ans;
    }


    //bfs、迪杰斯特拉
    public int findShortestCycle(int n, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            help(map, i,n);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    int ans = Integer.MAX_VALUE;

    void help(Map<Integer, List<Integer>> map, int cur, int n) {
        int[] dis = new int[n];
        Arrays.fill(dis, -1);
        dis[cur] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{cur, -1});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int x = poll[0];
            int pre = poll[1];
            for (Integer y : map.getOrDefault(x, new ArrayList<>())) {
                //未访问过该点
                if (dis[y] < 0) {
                    dis[y] = dis[x] + 1;
                    queue.add(new int[]{y, x});
                    //非第一次访问
                } else if(y!=pre) {
                    ans = Math.min(ans, dis[x] + dis[y] + 1);
                }
            }
        }
    }
}
