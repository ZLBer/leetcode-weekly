package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk328 {
    //ranking: 400 / 4776

    //遍历
    public int differenceOfSum(int[] nums) {
        int asum = 0, bsum = 0;

        for (int num : nums) {
            asum += num;

            while (num > 0) {
                bsum += (num % 10);
                num /= 10;
            }
        }
        return Math.abs(asum - bsum);
    }

    //二维差分数组
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] res = new int[n][n];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            for (int j = q[0]; j <= q[2]; j++) {
                res[j][q[1]] += 1;
                if (q[3] + 1 < n) {
                    res[j][q[3] + 1] -= 1;
                }
            }
        }

        for (int i = 0; i < res.length; i++) {
            for (int j = 1; j < res[i].length; j++) {
                res[i][j] += res[i][j - 1];
            }
        }
        return res;
    }


    //滑动窗口
    public long countGood(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int right = 0;
        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            while (count < k && right < nums.length) {
                Integer c = map.getOrDefault(nums[right], 0);

                count += c;
                map.put(nums[right], c + 1);
                right++;
            }
            if (count >= k) {
                res += (nums.length - right + 1);
            }
            Integer c = map.getOrDefault(nums[i], 0);
            if (c > 1) {
                count -= (c - 1);
            }
            map.put(nums[i], c - 1);
        }
        return res;
    }

    long sum(long n) {
        return n * n / 2;
    }


    //树形dp
    public long maxOutput(int n, int[][] edges, int[] price) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            if (!map.containsKey(edge[0])) {
                map.put(edge[0], new ArrayList<>());
            }
            if (!map.containsKey(edge[1])) {
                map.put(edge[1], new ArrayList<>());
            }
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        dfs(map, 0, -1, price);
        return res;
    }

    long res = 0;

    long[] dfs(Map<Integer, List<Integer>> map, int index, long up, int[] price) {
        //yes表示带叶节点  no表示不带叶节点
        //首次初始化相当于没有子树，只有当前节点
        long yes = price[index], no = 0;
        for (int next : map.getOrDefault(index, new ArrayList<>())) {
            if (next == up) {
                continue;
            }
            long[] s = dfs(map, next, index, price);
            //max(之前带叶节点的最大值+此次不带叶节点的最大值,
            //   之前不带叶节点的最大值+此次带叶节点的最大值)
            res = Math.max(res, Math.max(yes + s[1], no + s[0]));
            //更新带叶节点最大值
            yes = Math.max(yes, s[0] + price[index]);
            //更新不带叶节点的最大值
            no = Math.max(no, s[1] + price[index]);
        }
        return new long[]{yes, no};
    }

}
