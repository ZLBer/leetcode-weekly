package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk322 {
    //ranking: 349 / 5085
    //直接判断
    public boolean isCircularSentence(String sentence) {
        String[] s = sentence.split(" ");
        for (int i = 0; i < s.length - 1; i++) {
            if (s[i].charAt(s[i].length() - 1) != s[i + 1].charAt(0)) {
                return false;
            }
        }
        //判断最后一个
        if (s[0].charAt(0) != s[s.length - 1].charAt(s[s.length - 1].length() - 1)) {
            return false;
        }
        return true;
    }

    //排序+双指针
    public long dividePlayers(int[] skill) {
        Arrays.sort(skill);
        long cur = -1;
        long ans = 0;
        for (int i = 0; i < skill.length / 2; i++) {
            long sum = skill[i] + skill[skill.length - i - 1];
            if (cur != -1 && sum != cur) {
                return -1;
            }
            cur = sum;
            ans += (long) skill[i] * skill[skill.length - i - 1];
        }
        return ans;
    }


    //求联通块的最小路径
    public int minScore(int n, int[][] roads) {
        boolean[] visit = new boolean[n + 1];
        int min = Integer.MAX_VALUE;
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] road : roads) {
            if (!map.containsKey(road[0])) {
                map.put(road[0], new ArrayList<>());
            }
            if (!map.containsKey(road[1])) {
                map.put(road[1], new ArrayList<>());
            }
            map.get(road[0]).add(new int[]{road[1], road[2]});
            map.get(road[1]).add(new int[]{road[0], road[2]});
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            visit[cur] = true;
            for (int[] next : map.getOrDefault(cur, new ArrayList<>())) {
                if (visit[next[0]]) {
                    continue;
                }
                min = Math.min(min, next[1]);
                queue.add(next[0]);
            }
        }
        return min;
    }

    //有些直觉的想法是错的，再做下去也不会对啦
    //只考虑到三个边的换不可以，其实所有奇数边都不行，所以要判断是不是二分图
    /*public int magnificentSets(int n, int[][] roads) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        UnionFind uf = new UnionFind(n + 1);

        for (int[] road : roads) {
            uf.union(road[0], road[1]);
            if (!map.containsKey(road[0])) {
                map.put(road[0], new HashSet<>());
            }
            if (!map.containsKey(road[1])) {
                map.put(road[1], new HashSet<>());
            }
            map.get(road[0]).add(road[1]);
            map.get(road[1]).add(road[0]);
        }
        if (!check(map)) {
            return -1;
        }

        Map<Integer, Integer> res = new HashMap<>();
        for (int i = 1; i < n; i++) {
            boolean[] visit = new boolean[n + 1];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(i);
            int m = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                while (size-- > 0) {
                    Integer cur = queue.poll();
                    visit[cur] = true;
                    for (Integer next : map.getOrDefault(cur, new HashSet<>())) {
                        if (visit[next]) {
                            continue;
                        }
                        queue.add(next);
                    }
                }
                m++;
            }
            res.put(uf.find(i), Math.max(res.getOrDefault(uf.find(i), 0), m));
        }
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : res.entrySet()) {
            ans += entry.getValue();
        }
        return ans;
    }

    boolean check(Map<Integer, Set<Integer>> map) {
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            for (Integer two : entry.getValue()) {
                Set<Integer> three = map.getOrDefault(two, new HashSet<>());
                for (Integer integer : three) {
                    if (entry.getValue().contains(integer)) {
                        return false;
                    }
                }
            }
        }
        return true;
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


    }*/

    //判断二分图
    //+ bfs求最长路径
    public int magnificentSets(int n, int[][] edges) {
        int[] visited = new int[n];
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int[] road : edges) {
            if (!map.containsKey(road[0] - 1)) {
                map.put(road[0] - 1, new ArrayList<>());
            }
            if (!map.containsKey(road[1] - 1)) {
                map.put(road[1] - 1, new ArrayList<>());
            }
            map.get(road[0] - 1).add(road[1] - 1);
            map.get(road[1] - 1).add(road[0] - 1);
        }
        timer = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] != 0) continue;
            //该联通图的点数
            List<Integer> nodes = new ArrayList<>();
            if (!help(i, -1, nodes, map, 1, visited)) {
                return -1;
            }
            int max = 0;
            for (Integer node : nodes) {
                max = Math.max(max, bfs(node, map, n));
            }
            ans += max;
        }
        return ans;
    }

    int[] timer;
    int clock;

    int bfs(int index, Map<Integer, List<Integer>> map, int n) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(index);
        clock++;
        timer[index]=clock;
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Integer poll = queue.poll();
                for (Integer next : map.getOrDefault(poll, new ArrayList<>())) {
                    if (timer[next] == clock) continue;
                    timer[next] = clock;
                    queue.add(next);
                }
            }
            step++;
        }
        return step;

    }

    //染色标记法判断二分图
    boolean help(int node, int pre, List<Integer> nodes, Map<Integer, List<Integer>> map, int color, int[] visited) {
        if (visited[node] != 0) {
            return visited[node] == color;
        }
        visited[node] = color;
        nodes.add(node);
        for (Integer next : map.getOrDefault(node, new ArrayList<>())) {
            if (next == pre) continue;
            if (!help(next, node, nodes, map, -color, visited)) {
                return false;
            }
        }
        return true;
    }
}
