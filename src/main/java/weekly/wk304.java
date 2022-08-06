package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class wk304 {

    //简单题，排序+模拟
    //其实可以直接求不同数字个数
    public int minimumOperations(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        int max = nums[nums.length - 1];
        int sum = 0;
        int ans = 0;
        while (sum < max) {
            nums[i] -= sum;
            if (nums[i] <= 0) {
                i++;
            } else {
                sum += nums[i++];
                ans++;
            }
        }

        return ans;

    }


    //中等题，排序后开始拿1 2 3 ...个 要注意追后的时候要判断能不能构成一个新的组
    public int maximumGroups(int[] grades) {
        Arrays.sort(grades);
        int pre = 0;
        int count = 0;
        int i = 0;
        int ans = 0;
        for (i = 0; i < grades.length; ) {
            int pcount = 0;
            int psum = 0;
            while (i < grades.length && pcount <= count) {
                psum += grades[i++];
                pcount++;
            }
            if (psum <= pre || pcount <= count) {
                return ans;
            }
            ans++;
            pre = psum;
            count = pcount;
        }
        return ans;
    }


    //中等题，图遍历就行了，找出每个点到所有点的距离
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int[] dis1 = help(edges, node1);
        int[] dis2 = help(edges, node2);
        int ans = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < dis1.length; i++) {
            if (dis1[i] == 0 || dis2[i] == 0) continue;
            int max = Math.max(dis1[i], dis2[i]);
            if (max < min) {
                ans = i;
                min = max;
            }
        }
        return ans;

    }

    int[] help(int[] edges, int node) {
        int[] dis = new int[edges.length];
        int step = 0;
        //没路可走 或者 已经访问过
        while (node != -1 && dis[node] == 0) {
            dis[node] = step;
            node = edges[node];
            step++;
        }
        return dis;
    }
      /*  int[] help(int[] edges, int node) {
        int[] dis = new int[edges.length];
        Arrays.fill(dis, -1);
        boolean[] visited = new boolean[edges.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        int step = 0;
        visited[node] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Integer cur = queue.poll();
                dis[cur] = step;
                int next = edges[cur];
                if (next == -1 || visited[next]) continue;
                queue.add(next);
                visited[next] = true;
            }
            step++;
        }
        return dis;
    }*/


    //困难题，周赛写的解法
    /*public int longestCycle(int[] edges) {
        int[] in = new int[edges.length];
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] == -1) continue;
            in[edges[i]]++;
        }

        int max = -1;
        boolean[] visited = new boolean[edges.length];
        for (int i = 0; i < in.length; i++) {
            max = Math.max(max, help2(edges, visited, i));
        }
        return max;
    }

    int help2(int[] edges, boolean[] visited, int node) {
        List<Integer> path = new ArrayList<>();
        while (!visited[node]) {
            path.add(node);
            visited[node] = true;
            node = edges[node];
            if (node == -1) break;
        }
        if (node == -1) return -1;
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i) == node) {
                return path.size() - i;
            }
        }
        return -1;
    }*/


    //时间计时法，还可以拓扑排序删除不是坏的变，然后bfs求环长度
    public int longestCycle(int[] edges) {
        int[] dis = new int[edges.length];

        int ans = -1;
        int step = 1;
        for (int i = 0; i < edges.length; i++) {
            if (dis[i] == 0) {
                int start = step;
                int node = i;
                while (node != -1 && dis[node] == 0) {
                    dis[node] = step++;
                    node = edges[node];
                }
                if (node != -1 && dis[node] >= start) {
                    ans = Math.max(step - dis[node], ans);
                }
            }
        }
        return ans;

    }


    public static void main(String[] args) {
        wk304 w = new wk304();
        w.longestCycle(new int[]{3, 3, 4, 2, 3});
    }
}
