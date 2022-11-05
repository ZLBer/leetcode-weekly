package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class wk317 {
    //ranking: 912 / 5660

    //直接做
    public int averageValue(int[] nums) {
        int sum = 0;
        int count = 0;
        for (int num : nums) {
            if (num % 3 == 0 && num % 2 == 0) {
                sum += num;
                count++;
            }
        }
        if (count == 0) return 0;
        return sum / count;
    }

    //思路简单，做起来麻烦点
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        Map<String, Integer> counter = new HashMap<>();
        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < creators.length; i++) {
            counter.put(creators[i], counter.getOrDefault(creators[i], 0) + views[i]);
            if (!map.containsKey(creators[i])) {
                map.put(creators[i], new ArrayList<>());
            }
            map.get(creators[i]).add(i);
        }
        int max = 0;
        for (Integer value : counter.values()) {
            max = Math.max(value, max);
        }
        List<List<String>> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            if (entry.getValue() == max) {
                List<String> ans = new ArrayList<>();
                List<Integer> list = map.get(entry.getKey());
                list.sort((a, b) -> views[a] != views[b] ? views[b] - views[a] : ids[a].compareTo(ids[b]));
                ans.add(entry.getKey());
                ans.add(ids[list.get(0)]);
                res.add(ans);
            }
        }
        return res;
    }


    //模拟进位
    public long makeIntegerBeautiful(long n, int target) {
        if (check(n, target)) return 0;
        int pre = 0;//后面的进位
        long ans = 0; //加上的数字
        long i = 1; //当前位置，个位十位百位..
        while (true) {
            long cur = (n / i) % 10;//当前位置的数字
            long need = 10 - cur - pre; //当前位置需要加的数字
            pre = 1;
            ans = need * i + ans; //需要加的数字
            if (check(n + ans, target)) return ans;
            i *= 10;
        }
    }


    boolean check(long n, int target) {

        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum <= target;
    }

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


    //树形dp
    //先计算每个节点的深度
    //然后dfs计算每个节点删除后的树高度，max(右节点的深度+遍历深度，删除父节点的高度)
    public int[] treeQueries(TreeNode root, int[] queries) {
        dfs(root);
        checkMust(root, 0, 0);
        for (int i = 0; i < queries.length; i++) {
            queries[i] = ans.get(queries[i]);
        }
        return queries;
    }

    Map<Integer, int[]> map = new HashMap<>();

    int dfs(TreeNode parent) {
        if (parent == null) return 0;
        int l = dfs(parent.left) + 1;
        int r = dfs(parent.right) + 1;
        map.put(parent.val, new int[]{Math.max(l, r)});
        return Math.max(l, r);
    }

    Map<Integer, Integer> ans = new HashMap<>();

    void checkMust(TreeNode parent, int rest, int up) {
        if (parent == null) return;
        ans.put(parent.val, rest);
        int[] ints = map.getOrDefault(parent.right != null ? parent.right.val : -1, new int[]{0});
        checkMust(parent.left, Math.max(rest, ints[0] + up), up + 1);
        ints = map.getOrDefault(parent.left != null ? parent.left.val : -1, new int[]{0});
        checkMust(parent.right, Math.max(rest, ints[0] + up), up + 1);
    }

}
