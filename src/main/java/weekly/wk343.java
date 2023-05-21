package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class wk343 {

    //模拟
    public int isWinner(int[] player1, int[] player2) {
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < player1.length; i++) {
            boolean f1 = false, f2 = false;
            int j = i - 1;
            while (j >= 0 && j >= i - 2) {
                if (player1[j] == 10) {
                    f1 = true;
                }
                if (player2[j] == 10) {
                    f2 = true;
                }
                j--;
            }
            if (f1) {
                sum1 += player1[i] * 2;
            } else {
                sum1 += player1[i];
            }
            if (f2) {
                sum2 += player2[i] * 2;
            } else {
                sum2 += player2[i];
            }
        }
        int ans = 0;
        if (sum1 > sum2) {
            ans = 1;
        } else if (sum2 > sum1) {
            ans = 2;
        }
        return ans;
    }


    //模拟
    static public int firstCompleteIndex(int[] arr, int[][] mat) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = mat[0].length;
        int m = mat.length;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                map.put(mat[i][j], i * n + j);
            }
        }

        Map<Integer, Integer> col = new HashMap<>();
        Map<Integer, Integer> row = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            Integer integer = map.get(arr[i]);
            int x = integer / n;
            int y = integer % n;
            row.put(x, row.getOrDefault(x, 0) + 1);
            col.put(y, col.getOrDefault(y, 0) + 1);
            if (row.get(x) == n || col.get(y) == m) {
                return i;
            }
        }
        return arr.length;
    }




//    static int[][] moves = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
//
//    static public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
//
//        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
//        priorityQueue.add(new int[]{0, start[0], start[1]});
//
//        Map<String, List<Integer>> map = new HashMap<>();
//        for (int i = 0; i < specialRoads.length; i++) {
//            int[] specialRoad = specialRoads[i];
//            if (Math.abs(specialRoad[0] - specialRoad[2]) + Math.abs(specialRoad[1] - specialRoad[3]) >= specialRoad[4]) {
//                String k = specialRoad[0] + "-" + specialRoad[1];
//                if (!map.containsKey(k)) {
//                    map.put(k, new ArrayList<>());
//                }
//                map.get(k).add(i);
//            }
//        }
//       // System.out.println(map.size());
//        Set<String> set = new HashSet<>();
//        while (!priorityQueue.isEmpty()) {
//            int[] cur = priorityQueue.poll();
//            int cost = cur[0];
//            int x = cur[1];
//            int y = cur[2];
//            if (set.contains(x + "-" + y)) continue;
//            if (x == target[0] && y == target[1]) {
//                return cost;
//            }
//            set.add(x + "-" + y);
//
//            for (int[] move : moves) {
//                int nx = move[0] + x;
//                int ny = move[1] + y;
//                if(nx<=target[0]&&ny<=target[1]&&!set.contains(nx+"-"+ny)){
//                    priorityQueue.add(new int[]{cost + 1, nx, ny});
//                }
//            }
//            for (Integer index : map.getOrDefault(x + "-" + y, new ArrayList<>())) {
//                if(set.contains(specialRoads[index][2]+"-"+specialRoads[index][3])) continue;
//                priorityQueue.add(new int[]{cost + specialRoads[index][4], specialRoads[index][2], specialRoads[index][3]});
//            }
//        }
//        return -1;
//    }


    //朴素Dijkstra
  /*  public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        long t = (long) target[0] << 32 | target[1];
        Map<Long, Integer> dis = new HashMap();
        dis.put(t, Math.abs(start[0] - target[0]) + Math.abs(start[1] - target[1]));
        dis.put((long) start[0] << 32 | start[1], 0);
        Set<Long> vis = new HashSet<>();
        for (; ; ) {
            long v = -1;
            int dv = -1;
            for (Map.Entry<Long, Integer> e : dis.entrySet()) {
                if (!vis.contains(e.getKey()) && (dv < 0 || e.getValue() < dv)) {
                    v = e.getKey();
                    dv = e.getValue();
                }
            }
            if (v == t) return dv; // 到终点的最短路已确定
            vis.add(v);
            int vx = (int) (v >> 32), vy = (int) (v & Integer.MAX_VALUE);
            // 更新到终点的最短路
            dis.merge(t, dv + (target[0] - vx + target[1] - vy), Math::min);
            //计算改点到其他special的距离
            for (int[] r : specialRoads) {
                int d = dv + Math.abs(r[0] - vx) + Math.abs(r[1] - vy) + r[4];
                long w = (long) r[2] << 32 | r[3];
                if (d < dis.getOrDefault(w, Integer.MAX_VALUE))
                    dis.put(w, d);
            }
        }
    }*/

    int dis(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    //优先队列 Dijkstra
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        priorityQueue.add(new int[]{0, start[0], start[1]});
        priorityQueue.add(new int[]{Integer.MAX_VALUE, target[0], target[1]});
        Set<Long> visited = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            long flag = ((long) poll[1] << 32) + poll[2];
            System.out.println(poll[0]+" "+poll[1]+" "+poll[2]);
            if (visited.contains(flag)) continue;
            if (poll[1] == target[0] && poll[2] == target[1]) {
                return poll[0];
            }
            visited.add(flag);
            priorityQueue.add(new int[]{dis(target[0], target[1], poll[1], poll[2])+poll[0], target[0], target[1]});
            for (int[] specialRoad : specialRoads) {
                long f = ((long) specialRoad[2] << 32) + specialRoad[3];
                if (visited.contains(f)) continue;
                int d = Math.min(specialRoad[4] + dis(poll[1], poll[2], specialRoad[0], specialRoad[1]),dis(specialRoad[2],specialRoad[3],poll[1],poll[2]))+poll[0];
                priorityQueue.add(new int[]{d, specialRoad[2], specialRoad[3]});
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        wk343 w=new wk343();
        w.minimumCost(new int[]{1,1},new int[]{10,9},new int[][]{
                {5,2,3,6,3},{5,6,9,5,3},{5,9,1,2,5},{8,6,9,8,1}
        });
    }


}
