package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class wk339 {

    //模拟
    static public int findTheLongestBalancedSubstring(String s) {
        int i = 0;
        int ans = 0;
        while (i < s.length()) {
            int zero = 0;
            while (i < s.length() && s.charAt(i) == '0') {
                zero++;
                i++;
            }
            int one = 0;
            while (i < s.length() && s.charAt(i) == '1') {
                one++;
                i++;
            }
            ans = Math.max(Math.min(zero, one) * 2, ans);
        }
        return ans;
    }


    //hash
    public List<List<Integer>> findMatrix(int[] nums) {
        Arrays.sort(nums);

        int pre = nums[0];
        int count = 1;
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == pre) {
                count++;
            } else {
                while (res.size() < count) {
                    res.add(new ArrayList<>());
                }
                for (int j = 0; j < count; j++) {
                    res.get(j).add(pre);
                }
                pre = nums[i];
                count = 1;
            }
        }
        while (res.size() < count) {
            res.add(new ArrayList<>());
        }
        for (int j = 0; j < count; j++) {
            res.get(j).add(pre);
        }

        return res;
    }


    //贪心
    static public int miceAndCheese(int[] reward1, int[] reward2, int k) {

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());

        int sum = 0;
        for (int i = 0; i < reward1.length; i++) {
            int dis = reward1[i] - reward2[i];
            priorityQueue.add(dis);
            sum += reward2[i];
        }

        while (k > 0) {
            sum += priorityQueue.poll();
            k--;
        }
        return sum;

    }


    //直接遍历超时
    /*static public int[] minReverseOperations(int n, int p, int[] banned, int k) {
        int[] res = new int[n];
        Set<Integer> banSet = new HashSet<>();
        for (int i : banned) {
            banSet.add(i);
        }
        //存储未查询的点
        Set<Integer> treeSet=new TreeSet<>();
        for (int i = 0; i < n; i++) {
            treeSet.add(i);
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(p);
        treeSet.remove(p);
        for (int i = 0; i < res.length; i++) {
            res[i] = -1;
        }
        res[p] = 0;
        boolean[] visited = new boolean[n];
        int step = 0;
        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                visited[cur] = true;

                int r=cur+k-1;
                int l=cur-k+1;
                for (int i = k; i > 1; i -= 2) {
                    //左移
                    int left = cur - i + 1;
                    int ll = (l++);
                    int lr = ll+k-1;
                    //右移动
                    int right = cur + i - 1;
                    int rr=(r--);
                    int rl=rr-k+1;

                    if (left >= 0 && lr<n&&ll>=0 && !visited[left] && !banSet.contains(left)) {
                        visited[left] = true;
                        queue.add(left);
                        res[left] = step;
                    }

                    if (right < n &&rl>=0&&rr<n && !visited[right] && !banSet.contains(right)) {
                        visited[right] = true;
                        queue.add(right);
                        res[right] = step;
                    }
                }
            }
        }
        return res;
    }*/


    //广度优先+有序集合
    static public int[] minReverseOperations(int n, int p, int[] banned, int k) {
        int[] res = new int[n];
        Set<Integer> banSet = new HashSet<>();
        for (int i : banned) {
            banSet.add(i);
        }
        banSet.add(p);
        //存储未查询的点
        TreeSet<Integer> treeEven = new TreeSet<>();
        TreeSet<Integer> treeOdd = new TreeSet<>();

        TreeSet<Integer> sets[] = new TreeSet[2];
        sets[0] = treeEven;
        sets[1] = treeOdd;
        treeEven.add(n);
        treeOdd.add(n);
        for (int i = 0; i < n; i++) {
            if (banSet.contains(i)) continue;
            if (i % 2 == 0) {
                treeEven.add(i);
            } else {
                treeOdd.add(i);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(p);
        for (int i = 0; i < res.length; i++) {
            res[i] = -1;
        }
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Integer i = queue.poll();
                res[i] = step;
                // 从 mn 到 mx 的所有位置都可以翻转到
                int mn = Math.max(i - k + 1, k - i - 1);
                int mx = Math.min(i + k - 1, n * 2 - k - i - 1);
                TreeSet<Integer> s = sets[mn % 2];
                for (int j = s.ceiling(mn); j <= mx; j = s.ceiling(mn)) {
                    queue.add(j);
                    s.remove(j);
                }
            }
            step++;
        }
        return res;
    }

    public static void main(String[] args) {
        minReverseOperations(4, 0, new int[]{1,2}, 4);
    }
}
