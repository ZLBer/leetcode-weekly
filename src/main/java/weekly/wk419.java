package weekly;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class wk419 {

   /* public int[] findXSum(int[] nums, int k, int x) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < k - 1; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        int[] ans = new int[nums.length - k + 1];

        for (int i = k - 1; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            ans[i - k + 1] = help(map, x);
            map.put(nums[i - k + 1], map.get(nums[i - k + 1]) - 1);
        }
        return ans;
    }

    int help(Map<Integer, Integer> map, int x) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] == b[0] ? b[1] - a[1] : b[0] - a[0]);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            priorityQueue.add(new int[]{entry.getValue(), entry.getKey()});
        }
        int ans = 0;
        while (!priorityQueue.isEmpty() && x > 0) {
            int[] poll = priorityQueue.poll();
            ans += poll[0] * poll[1];
            x--;
        }
        return ans;
    }*/

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // dfs
    public int kthLargestPerfectSubtree(TreeNode root, int k) {
        max = k;
        priorityQueue = new PriorityQueue<>();
        dfs(root);
        if (priorityQueue.size() == k) {
            return priorityQueue.poll();
        } else {
            return -1;
        }
    }

    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    int max;


    int[] dfs(TreeNode cur) {
        if (cur == null) {
            return new int[]{1, 0, 0};
        }
        int[] left = dfs(cur.left);
        int[] right = dfs(cur.right);
        if (left[0] == 1 && right[0] == 1) {
            //树高相同
            if (left[1] == right[1]) {
                priorityQueue.add(left[2] + right[2] + 1);
                if (priorityQueue.size() > max) {
                    priorityQueue.poll();
                }
                return new int[]{1, left[1] + 1, left[2] + right[2] + 1};
            } else {
                return new int[]{0, 0, 0};
            }

        } else {
            return new int[]{0, 0, 0};
        }
    }


    public int countWinningSequences(String s) {
        int max = 2001;
        int[][] dp = new int[3][max];
        char[] arr = new char[]{'F', 'W', 'E'};

        for (int i = 0; i < arr.length; i++) {
            dp[i][1000 + getScore(arr[i], s.charAt(0))] = 1;
        }
        int mod = (int) 1e9 + 7;
        for (int i = 1; i < s.length(); i++) {
            char b = s.charAt(i);
            int[][] ndp = new int[3][max];
            for (int j = 0; j < arr.length; j++) {
                char a = arr[j];
                int score = getScore(a, b);
                for (int pre = 0; pre < dp.length; pre++) {
                    if (j == pre) continue;
                    for (int k = 0; k < max; k++) {
                        if (dp[pre][k] == 0) continue;
                        ndp[j][k + score] += dp[pre][k];
                        ndp[j][k + score]%=mod;
                    }
                }
            }
            dp = ndp;
        }
        long ans = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 1001; j < dp[i].length; j++) {
                ans += dp[i][j];
                ans %= mod;
            }
        }
        return (int) ans;
    }

    int getScore(char a, char b) {
        if ((a == 'F' && b == 'E') || (a == 'W' && b == 'F') || (a == 'E' && b == 'W')) {
            return 1;
        } else if (a == b) {
            return 0;
        } else {
            return -1;
        }
    }

    // 对顶堆
    private final TreeSet<int[]> L = new TreeSet<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    private final TreeSet<int[]> R = new TreeSet<>(L.comparator());
    private final Map<Integer, Integer> cnt = new HashMap<>();
    private long sumL = 0;

    public long[] findXSum(int[] nums, int k, int x) {
        long[] ans = new long[nums.length - k + 1];
        for (int r = 0; r < nums.length; r++) {
            // 添加 in
            int in = nums[r];
            del(in);
            cnt.merge(in, 1, Integer::sum); // cnt[in]++
            add(in);

            int l = r + 1 - k;
            if (l < 0) {
                continue;
            }

            // 维护大小
            while (!R.isEmpty() && L.size() < x) {
                r2l();
            }
            while (L.size() > x) {
                l2r();
            }
            ans[l] = sumL;

            // 移除 out
            int out = nums[l];
            del(out);
            cnt.merge(out, -1, Integer::sum); // cnt[out]--
            add(out);
        }
        return ans;
    }

    // 添加元素
    private void add(int val) {
        int c = cnt.get(val);
        if (c == 0) {
            return;
        }
        int[] p = new int[]{c, val};
        if (!L.isEmpty() && L.comparator().compare(p, L.first()) > 0) { // p 比 L 中最小的还大
            sumL += (long) p[0] * p[1];
            L.add(p);
        } else {
            R.add(p);
        }
    }

    // 删除元素
    private void del(int val) {
        int c = cnt.getOrDefault(val, 0);
        if (c == 0) {
            return;
        }
        int[] p = new int[]{c, val};
        if (L.contains(p)) {
            sumL -= (long) p[0] * p[1];
            L.remove(p);
        } else {
            R.remove(p);
        }
    }

    // 从 L 移动一个元素到 R
    private void l2r() {
        int[] p = L.pollFirst();
        sumL -= (long) p[0] * p[1];
        R.add(p);
    }

    // 从 R 移动一个元素到 L
    private void r2l() {
        int[] p = R.pollLast();
        sumL += (long) p[0] * p[1];
        L.add(p);
    }


    public static void main(String[] args) {
        wk419 w = new wk419();
        w.findXSum(new int[]{1, 1, 2, 2, 3, 4, 2, 3}, 6, 2);
    }

}
