package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk344 {
    // 前缀和
    public int[] distinctDifferenceArray(int[] nums) {
        int[] ans = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            Set<Integer> left = new HashSet<>();
            for (int j = 0; j <= i; j++) {
                left.add(nums[j]);
            }
            Set<Integer> right = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                right.add(nums[j]);
            }
            ans[i] = left.size() - right.size();
        }
        return ans;
    }


    //记录 数字->频率  频率->个数
    class FrequencyTracker {

        int[] count = new int[(int) 1e5 + 7];
        Map<Integer, Integer> map = new HashMap<>();

        public FrequencyTracker() {

        }

        public void add(int number) {
            Integer c = map.getOrDefault(number, 0);
            count[c]--;
            count[c + 1]++;
            map.put(number, c + 1);
        }

        public void deleteOne(int number) {
            Integer c = map.getOrDefault(number, 0);
            if (c == 0) return;
            count[c]--;
            count[c - 1]++;
            map.put(number, c - 1);
        }

        public boolean hasFrequency(int frequency) {
            return count[frequency] > 0;
        }
    }


    //模拟
    static public int[] colorTheArray(int n, int[][] queries) {
        int[] ans = new int[queries.length];
        int[] color = new int[n];
        int same = 0;
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            int index = query[0];

            //若颜色未改变
            if (color[index] == query[1]) {
                ans[i] = same;
                continue;
            }
            //考虑前面一个
            if (index > 0) {
                //现在相同了
                if (color[index - 1] == query[1]) {
                    same++;
                //之前有颜色且是相同的色块，则表示这次需要减少
                } else if (color[index] != 0 && color[index - 1] == color[index]) {
                    same--;
                }
            }
            //考虑后面一个
            if (index < color.length - 1) {
                if (color[index + 1] == query[1]) {
                    same++;
                } else if (color[index] != 0 && color[index + 1] == color[index]) {
                    same--;
                }
            }
            color[index] = query[1];
            ans[i] = same;
        }
        return ans;
    }

    public static void main(String[] args) {
        minIncrements(7, new int[]{1, 5, 2, 2, 3, 3, 1});
    }

  /*  static public int minIncrements(int n, int[] cost) {
        int ans = 0;
        int step = 1;
        int[] sum = new int[n];
        sum[0] = cost[0];
        int i = 1;
        while (i < n) {
            int count = 1 << step;
            for (int j = i; j < i + count; j++) {
                sum[j] = cost[j] + sum[(j - 1) / 2];
            }
            i += count;
            step++;
        }
        List<Integer> sumS = new ArrayList<>();
        int max = 0;
        for (int j = n / 2; j < n; j++) {
            max = Math.max(sum[j], max);
            sumS.add(sum[j]);
        }

        List<Integer> nSum = new ArrayList<>();
        for (int j = 0; j < sumS.size(); j += 2) {
            int min = Math.min(max - sumS.get(j), max - sumS.get(j + 1));
            ans += max - sumS.get(j + 1) - min;
            ans += max - sumS.get(j) - min;
            nSum.add(min);
        }
        sumS = nSum;

        while (sumS.size() > 1) {
            nSum = new ArrayList<>();
            for (int j = 0; j < sumS.size(); j += 2) {
                int min = Math.min(sumS.get(j), sumS.get(j + 1));
                ans += sumS.get(j) - min;
                ans += sumS.get(j + 1) - min;
                nSum.add(min);
            }
            sumS = nSum;
        }
        return ans;
    }*/

    //树形DP
    static public int minIncrements(int n, int[] cost) {
        return dfs(cost, 0)[0];
    }

    static int[] dfs(int[] cost, int index) {
        if (index >= cost.length) {
            return new int[2];
        }
        int[] left = dfs(cost, index * 2 + 1), right = dfs(cost, index * 2 + 2);

        return new int[]{
                 //arr[0]表示
                left[0] + right[0] + Math.abs(left[1] - right[1]),
                cost[index] + Math.max(left[1], right[1])
        };
    }
}