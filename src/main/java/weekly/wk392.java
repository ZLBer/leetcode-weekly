package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk392 {

    // 遍历
    public int longestMonotonicSubarray(int[] nums) {
        int ans = 0;
        int temp = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                temp++;
            } else {
                ans = Math.max(temp, ans);
                temp = 1;
            }
        }
        ans = Math.max(temp, ans);
        temp = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                temp++;
            } else {
                ans = Math.max(temp, ans);
                temp = 1;
            }
        }
        ans = Math.max(temp, ans);
        temp = 0;
        return ans;
    }

    // 贪心
    public String getSmallestString(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            int d = distance('a', s.charAt(i));
            if (k - d >= 0) {
                chars[i] = 'a';
                k -= d;
            } else {
                chars[i] -= k;
                break;
            }
        }
        return new String(chars);
    }

    int distance(char a, char b) {
        if (a > b) {
            char t = a;
            a = b;
            b = t;
        }
        return Math.min(b - a, a + 26 - b);
    }


    // 排序
    public long minOperationsToMakeMedianK(int[] nums, int k) {
        Arrays.sort(nums);
        int mid = nums.length / 2;
        long ans = 0;
        for (int i = mid; i < nums.length; i++) {
            if (nums[i] < k) {
                ans += k - nums[i];
            }
        }
        for (int i = mid; i >= 0; i--) {
            if (nums[i] > k) {
                ans += nums[i] - k;
            }
        }
        return ans;
    }

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
    }



    //并查集  &性质
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        UnionFind uf = new UnionFind(n + 1);
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {

            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            uf.union(a, b);
            if (!map.containsKey(a)) map.put(a, new ArrayList<>());
            if (!map.containsKey(b)) map.put(b, new ArrayList<>());
            map.get(a).add(new int[]{b, w});
            map.get(b).add(new int[]{a, w});
        }
        Map<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int index = uf.find(i);
            int t = count.getOrDefault(index, -1);
            for (int[] ints : map.getOrDefault(i, new ArrayList<>())) {
                if (t == -1) {
                    t = ints[1];
                } else {
                    t &= ints[1];
                }
            }
            count.put(index, t);
        }


        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            if (uf.find(query[i][0]) != uf.find(query[i][1])) {
                ans[i] = -1;
            } else if (query[i][0] == query[i][1]) {
                ans[i] = 0;

            } else {
                ans[i] = count.get(uf.find(query[i][0]));

            }
        }
        return ans;
    }


}
