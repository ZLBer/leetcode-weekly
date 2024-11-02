package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
public class wk418 {
    // 暴力
    public int maxGoodNumber(int[] nums) {
        int a = Integer.MIN_VALUE;
        a = Math.max(a, help(nums[0], nums[1], nums[2]));
        a = Math.max(a, help(nums[0], nums[2], nums[1]));
        a = Math.max(a, help(nums[1], nums[2], nums[0]));
        a = Math.max(a, help(nums[1], nums[0], nums[2]));
        a = Math.max(a, help(nums[2], nums[0], nums[1]));
        a = Math.max(a, help(nums[2], nums[1], nums[0]));

        return a;
    }

    int help(int a, int b, int c) {
        String s = Integer.toBinaryString(a) + Integer.toBinaryString(b) + Integer.toBinaryString(c);
        return Integer.parseInt(s, 2);
    }


    // dfs
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        UnionFind uf = new UnionFind(n);
        for (int[] invocation : invocations) {
            if (!map.containsKey(invocation[0])) map.put(invocation[0], new ArrayList<>());
            map.get(invocation[0]).add(invocation[1]);
            uf.union(invocation[0], invocation[1]);
        }
        Set<Integer> set = new HashSet<>();
        dfs(k, map, set);
        int group = uf.find(k);
        for (int i = 0; i < n; i++) {
            //可疑方法直接跳过
            if (set.contains(i)) continue;
            if (uf.find(i) == group) {
                //存在之外的调用
                List<Integer> ans = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    ans.add(j);
                }
                return ans;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (set.contains(i)) continue;
            ans.add(i);
        }
        return ans;
    }

    void dfs(int pre, Map<Integer, List<Integer>> map, Set<Integer> set) {
        set.add(pre);
        for (Integer child : map.getOrDefault(pre, new ArrayList<>())) {
            if (set.contains(child)) continue;
            dfs(child, map, set);
        }
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


    // 构造
    public int[][] constructGridLayout(int n, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) map.put(edge[0], new ArrayList<>());
            if (!map.containsKey(edge[1])) map.put(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        int[] count = new int[5];
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            count[entry.getValue().size()]++;
        }

        for (int i = 1; i <= n / 2; i++) {
            int row = i, col = n / i;
            if (row * col != n) continue;
            int[] need = help(row, col);

            boolean flag = true;
            for (int j = 1; j < need.length; j++) {
                if (count[j] != need[j]) {
                    flag = false;
                    break;
                }
            }
            // 校验成功
            if (flag) {
                return help(row, col, map);
            }
        }

        return new int[][]{};

    }

    int[] help(int row, int col) {
        int[] count;
        if (row == 1) {
            count = new int[]{0, 2, col - 2, 0, 0};
        } else {
            count = new int[]{0, 0, 4, (row - 2 + col - 2) * 2, (row - 2) * (col - 2)};
        }
        return count;
    }

    int[][] help(int row, int col, Map<Integer, List<Integer>> map) {
        int cur = -1;

        if (row == 1) {
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                if (entry.getValue().size() == 1) {
                    cur = entry.getKey();
                    break;
                }
            }
        } else {
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                if (entry.getValue().size() == 2) {
                    cur = entry.getKey();
                    break;
                }
            }
        }
        System.out.println(row + " " + col + " " + cur);
        int[][] ans;
        Set<Integer> set=new HashSet<>();
        if (row == 1) {
            ans = new int[col][row];
            ans[0][0] = cur;
            set.add(cur);
        } else if (row == 2) {
            ans = new int[col][row];
            ans[0][0] = cur;
            set.add(cur);
            for (Integer next : map.get(cur)) {
                if (map.get(next).size() == 2) {
                    ans[0][1] = next;
                    set.add(next);
                    break;
                }
            }
        } else {
            //构造第一行
            List<Integer> list = new ArrayList<>();
            list.add(cur);
            int pre = -1;
            for (int i = 1; i < row*col; i++) {
                for (Integer next : map.get(cur)) {
                    if (next != pre && map.get(next).size() <= 3) {
                        list.add(next);
                        pre = cur;
                        cur = next;
                        break;
                    }
                }
                if (map.get(cur).size() == 2) break;
            }
            ans=new int[(row*col)/list.size()][list.size()];
            for (int i = 0; i < list.size(); i++) {
                ans[0][i]=list.get(i);
                set.add(ans[0][i]);
            }
        }

        for (int i = 0; i < ans.length-1; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                cur=ans[i][j];
                for (Integer next : map.get(cur)) {
                    if(set.contains(next)) continue;
                    ans[i+1][j]=next;
                    set.add(next);
                }
            }
        }


        return ans;
    }
    public static void main(String[] args) {
        wk418 w=new wk418();
        // w.constructGridLayout(4,new int[][]{{0,1},{0,2},{1,3},{2,3}});
        w.constructGridLayout(12,new int[][]{
                {0,1},{0,3},{0,6},{0,8},{1,5},{1,9},{2,3},{2,10},{2,11},{3,5},{3,7},{4,6},{4,8},{5,10},{6,7},{7,11},{8,9}
        });
    }


   /* int[][] help(int row, int col, Map<Integer, Integer> map) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new ArrayList<>());
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            list.get(entry.getValue() - 1).add(entry.getKey());
        }

        int[][] ans = new int[row][col];
        if (row == 1) {
            ans[0][0] = list.get(0).get(0);
            ans[0][col - 1] = list.get(0).get(1);
            int index = 0;
            for (int i = 1; i < col - 1; i++) {
                ans[0][i] = list.get(1).get(index++);
            }
            return ans;
        }

        ans[0][0] = list.get(1).get(0);
        ans[0][col - 1] = list.get(1).get(1);
        ans[row - 1][0] = list.get(1).get(2);
        ans[row - 1][col - 1] = list.get(1).get(3);


        int index = 0;
        for (int i = 1; i < col - 1; i++) {
            ans[0][i] = list.get(2).get(index++);
            ans[row - 1][i] = list.get(2).get(index++);
        }

        for (int i = 1; i < row - 1; i++) {
            ans[i][0] = list.get(2).get(index++);
            ans[i][col - 1] = list.get(2).get(index++);
        }

        index = 0;
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                ans[i][j] = list.get(3).get(index++);
            }
        }
        return ans;
    }*/


}
