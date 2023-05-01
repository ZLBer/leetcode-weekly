package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk340 {

    public static boolean isPrime(int n) {
        if (n == 0 || n == 1) {
            return false;
        }
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    //求质数
    public int diagonalPrime(int[][] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (isPrime(nums[i][i])) {
                ans = Math.max(nums[i][i], ans);
            }
            if (isPrime(nums[i][nums.length - i - 1])) {
                ans = Math.max(nums[i][nums.length - i - 1], ans);
            }
        }
        return ans;
    }


    // 哈希分组+计算增量或前缀和
    public long[] distance(int[] nums) {
        long[] arr = new long[nums.length];
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) map.put(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }
        for (List<Integer> list : map.values()) {
            long sum = 0;
            for (int i = 1; i < list.size(); i++) {
                sum += list.get(i) - list.get(0);
            }

            //最左侧直接求差值和
            arr[list.get(0)] = sum;

            for (int i = 1; i < list.size(); i++) {
                long dis = list.get(i) - list.get(i - 1);
                //增加了i个dis，减少了(list.size-i)个ids
                sum += (i - (list.size() - i)) * dis;
                arr[list.get(i)] = sum;
            }

        }
        return arr;
    }


 /*  static public int minimizeMax(int[] nums, int p) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            priorityQueue.add(new int[]{nums[i] - nums[i - 1], i - 1, i});
        }
        Set<Integer> set = new HashSet<>();
        int ans=0;
        while (p > 0) {
            int[] cur = priorityQueue.poll();
            if (!set.contains(cur[1]) && !set.contains(cur[2])) {
                set.add(cur[1]);
                set.add(cur[2]);
                ans=Math.max(cur[0],ans);
                int left=cur[1]-1;
                int right=cur[2]+1;
                if(left>=0&&right<nums.length){
                    priorityQueue.add(new int[]{nums[right]-nums[left],left,right});
                }
                p--;
            }
        }
        return ans;
    }*/


    //最大化最小  考虑二分
    static public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int left = 0, right = nums[nums.length - 1] - nums[0];

        while (left < right) {
            int mid = (left + right) / 2;
            if (check(nums, mid, p)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    static boolean check(int[] nums, int mid, int p) {
        Deque<Integer> deque = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!deque.isEmpty()) {
                if (nums[i] - deque.peekLast() <= mid) {
                    ans++;
                    deque.pollLast();
                } else {
                    deque.addLast(nums[i]);
                }
            } else {
                deque.addLast(nums[i]);
            }
        }
        return ans >= p;
    }


    //记录每次每行每列到达的最大位置
    public int minimumVisitedCells(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        int m = grid.length;
        int n = grid[0].length;
        int[] maxBelow = new int[n];
        int[] maxRight = new int[m];

        Set<Integer> set = new HashSet<>();
        int step = 1;
        set.add(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                if (cur[0] == m - 1 && cur[1] == n - 1) {
                    return step;
                }
                int k = grid[cur[0]][cur[1]];

                int belowM = Math.min(m - 1, cur[0] + k);
                int rightM = Math.min(n - 1, cur[1] + k);


                //取最大值，因为可能到不了cur[0]这个位置
                int begin=Math.max(maxBelow[cur[1]],cur[0]);
                for (int i = begin; i <= belowM; i++) {
                    int nx = i, ny = cur[1];
                    if(set.contains(nx * n + ny)) continue;
                    set.add(nx * n + ny);
                    queue.add(new int[]{nx, ny});
                }
                //取最大值，因为可能到不了cur[1]这个位置
                begin=Math.max(maxRight[cur[0]],cur[1]);
                for (int i =  begin; i <= rightM; i++) {
                    int nx = cur[0], ny = i;
                    if(set.contains(nx * n + ny)) continue;
                    set.add(nx * n + ny);
                    queue.add(new int[]{nx, ny});
                }
                maxBelow[cur[1]]=Math.max(belowM,maxBelow[cur[1]]);
                maxRight[cur[0]]=Math.max(rightM,maxRight[cur[0]]);
            }
            step++;
        }
        return -1;
    }

    public static void main(String[] args) {
        minimizeMax(new int[]{2, 4, 1, 2}, 1);
    }
}
