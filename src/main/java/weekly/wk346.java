package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class wk346 {

    //栈
    public int minLength(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (deque.size() > 0 && check(c, deque.peekLast())) {
                deque.pollLast();
            } else {
                deque.addLast(c);
            }
        }
        return deque.size();
    }

    boolean check(char cur, char pre) {
        if (cur == 'B' && pre == 'A') {
            return true;
        }
        if (cur == 'D' && pre == 'C') {
            return true;
        }
        return false;
    }

    //遍历
    public String makeSmallestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int n = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (chars[i] == chars[n - i - 1]) continue;
            char need = ' ';
            if (chars[i] - chars[n - i - 1] > 0) {
                need = chars[n - i - 1];
            } else {
                need = chars[i];
            }
            chars[i] = need;
            chars[n - i - 1] = need;
        }
        return new String(chars);
    }

    //预处理+回溯
    public int punishmentNumber(int n) {
        int ans=0;
        for (int i = 0; i < list.size(); i++) {
            Integer cur = list.get(i);
            if(cur <=n){
                ans+=cur*cur;
            }else {
                break;
            }
        }
        return ans;
    }

    static List<Integer> list = new ArrayList<>();

    static  {
        for (int i = 1; i <= 1000; i++) {
            int mul = i * i;
            if (check(0, mul + "", 0, i)) {
                list.add(i);
            }
        }
    }

    static boolean check(int cur, String s, int sum, int target) {
        if (cur >= s.length()) {
            if (sum == target) return true;
            return false;
        }
        for (int i = cur; i < s.length(); i++) {
            String num = s.substring(cur, i + 1);
            if (check(i + 1, s, sum + Integer.parseInt(num), target)) {
                return true;
            }
        }
        return false;
    }

    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        List<int[]> g[] = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int x = edges[i][0], y = edges[i][1];
            g[x].add(new int[]{y, i});
            g[y].add(new int[]{x, i}); // 建图，额外记录边的编号
        }

        int[][] dis = new int[n][2];
        for (int i = 0; i < n; i++)
            if (i != source)
                dis[i][0] = dis[i][1] = Integer.MAX_VALUE;
        //第一次遍历 考虑把-1变成1
        dijkstra(g, edges, destination, dis, 0, 0);
        //1是最小的了，若还比target大只能返回false
        int delta = target - dis[destination][0];
        if (delta < 0) // -1 全改为 1 时，最短路比 target 还大
            return new int[][]{};

        //第二次遍历
        dijkstra(g, edges, destination, dis, delta, 1);
        if (dis[destination][1] < target) // 最短路无法再变大，无法达到 target
            return new int[][]{};

        for (int[] e : edges)
            if (e[2] == -1) // 剩余没修改的边全部改成 1
                e[2] = 1;
        return edges;
    }

    // 朴素 Dijkstra 算法
    // 这里 k 表示第一次/第二次
    private void dijkstra(List<int[]> g[], int[][] edges, int destination, int[][] dis, int delta, int k) {
        int n = g.length;
        boolean[] vis = new boolean[n];
        for (; ; ) {
            // 找到当前最短路，去更新它的邻居的最短路
            // 根据数学归纳法，dis[x][k] 一定是最短路长度
            int x = -1;
            //找最短路径x且没被访问过x
            for (int i = 0; i < n; ++i)
                if (!vis[i] && (x < 0 || dis[i][k] < dis[x][k]))
                    x = i;
            if (x == destination) // 起点 source 到终点 destination 的最短路已确定
                return;
            vis[x] = true; // 标记，在后续的循环中无需反复更新 x 到其余点的最短路长度
            for (int[] e : g[x]) {
                int y = e[0], eid = e[1];
                int wt = edges[eid][2];
                if (wt == -1)
                    wt = 1; // -1 改成 1
                if (k == 1 && edges[eid][2] == -1) {
                    // 第二次 Dijkstra，改成 w

                    // x是当前点  y是next
                    //delta是剩下的距离， dis[y][0]是第一次遍历 start到y的距离
                    //dis[x][1]是第二次遍历  start到x的距离
                   // 第二次到x的距离 + x到y的距离 + 第一次到des的距离-第一次到y的距离=target
                    //求x到y的距离
                    int w = delta + dis[y][0] - dis[x][1];
                    if (w > wt)
                        edges[eid][2] = wt = w; // 直接在 edges 上修改
                }
                // 更新最短路
                dis[y][k] = Math.min(dis[y][k], dis[x][k] + wt);
            }
        }
    }


    public static void main(String[] args) {

    }
}
