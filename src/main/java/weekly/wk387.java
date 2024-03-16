package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk387 {

    //模拟
   /* public int[] resultArray(int[] nums) {
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        arr1.add(nums[0]);
        arr2.add(nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int num = nums[i];
            if (arr1.get(arr1.size() - 1) > arr2.get(arr2.size() - 1)) {
                arr1.add(num);
            } else {
                arr2.add(num);
            }

        }
        int[] res = new int[nums.length];
        int i = 0;
        for (Integer integer : arr1) {
            res[i++] = integer;
        }
        for (Integer integer : arr2) {
            res[i++] = integer;
        }
        return res;

    }*/

    //二维前缀和
    public int countSubmatrices(int[][] grid, int k) {
        int[][] dp = new int[grid.length + 1][grid[0].length + 1];
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            int pre = 0;
            for (int j = 0; j < grid[0].length; j++) {
                pre += grid[i][j];
                dp[i + 1][j + 1] = dp[i][j + 1] + pre;
                if (dp[i + 1][j + 1] <= k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    //模拟
    public int minimumOperationsToWriteY(int[][] grid) {
        int[] all = new int[3];
        for (int[] ints : grid) {
            for (int anInt : ints) {
                all[anInt]++;
            }
        }
        int[] Y = check(grid);
        int sumY = 0;
        for (int i : Y) {
            sumY += i;
        }
        int sumLeft = grid.length * grid[0].length - sumY;
        int ans = Integer.MAX_VALUE;
        int[] left = new int[3];
        for (int i = 0; i < left.length; i++) {
            left[i] = all[i] - Y[i];
        }
        for (int i = 0; i < Y.length; i++) {
            for (int j = 0; j < left.length; j++) {
                if (i == j) continue;
                //把Y都变成i
                int t = sumY - Y[i];
                //把非Y都变成j
                t += sumLeft - left[j];
                ans = Math.min(t, ans);
            }
        }
        return ans;
    }

    int[] check(int[][] grid) {
        int[] ans = new int[3];
        int n = grid.length;
        for (int i = 0; i <= n / 2; i++) {
            ans[grid[i][i]]++;
        }

        for (int i = 0; i < n / 2; i++) {
            ans[grid[i][n - i - 1]]++;
        }

        for (int i = n / 2 + 1; i < n; i++) {
            ans[grid[i][n / 2]]++;
        }
        return ans;
    }

    public class FenwickTree {

        /**
         * 预处理数组
         */
        private int[] tree;
        private int len;
        int all=0;

        public FenwickTree(int n) {
            this.len = n;
            tree = new int[n + 1];
        }

        /**
         * 单点更新
         *
         * @param i     原始数组索引 i
         * @param delta 变化值 = 更新以后的值 - 原始值
         */
        public void update(int i, int delta) {
            // 从下到上更新，注意，预处理数组，比原始数组的 len 大 1，故 预处理索引的最大值为 len
            while (i <= len) {
                tree[i] += delta;
                i += lowbit(i);
            }
            all++;
        }

        //区间更新
        void update(int x, int y, int k) {
            update(x, k);
            update(y + 1, -k);
        }

        /**
         * 查询前缀和
         *
         * @param i 前缀的最大索引，即查询区间 [0, i] 的所有元素之和
         */
        public int query(int i) {
            // 从右到左查询
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= lowbit(i);
            }
            return all-sum;
        }

        public int lowbit(int x) {
            return x & (-x);
        }
    }

 /*   public int[] resultArray(int[] nums) {
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        arr1.add(nums[0]);
        arr2.add(nums[1]);

        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        Map<Integer, Integer> Index = new HashMap<>();
        for (int i = 0; i < copy.length; i++) {
            Index.put(copy[i], i+1);
        }

        FenwickTree f1 = new FenwickTree(nums.length+1);
        FenwickTree f2 = new FenwickTree(nums.length+1);
        f1.update(Index.get(nums[0]), 1);
        f2.update(Index.get(nums[1]), 1);

        for (int i = 2; i < nums.length; i++) {
            int num = nums[i];
            Integer index = Index.get(num);
            int q1 = f1.query(index);
            int q2 = f2.query(index);
            if(q1>q2){
                arr1.add(num);
                f1.update(index,1);
            }else if(q2>q1){
                arr2.add(num);
                f2.update(index,1);
            }else {
                if(arr1.size()>arr2.size()){
                    arr2.add(num);
                    f2.update(index,1);
                }else {
                    arr1.add(num);
                    f1.update(index,1);
                }
            }

        }
        int[] res = new int[nums.length];
        int i = 0;
        for (Integer integer : arr1) {
            res[i++] = integer;
        }
        for (Integer integer : arr2) {
            res[i++] = integer;
        }
        return res;

    }*/

    // 离散化树状数组
    public int[] resultArray(int[] nums) {
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        arr1.add(nums[0]);
        arr2.add(nums[1]);

        int max=0;
        for (int num : nums) {
            max=Math.max(num,max);
        }


        FenwickTree f1 = new FenwickTree(max+1);
        FenwickTree f2 = new FenwickTree(max+1);
        f1.update(nums[0]+1, 1);
        f2.update(nums[1]+1, 1);

        for (int i = 2; i < nums.length; i++) {
            int num = nums[i];
            Integer index =num+1;
            int q1 = f1.query(index);
            int q2 = f2.query(index);
            if(q1>q2){
                arr1.add(num);
                f1.update(index,1);
            }else if(q2>q1){
                arr2.add(num);
                f2.update(index,1);
            }else {
                if(arr1.size()>arr2.size()){
                    arr2.add(num);
                    f2.update(index,1);
                }else {
                    arr1.add(num);
                    f1.update(index,1);
                }
            }

        }
        int[] res = new int[nums.length];
        int i = 0;
        for (Integer integer : arr1) {
            res[i++] = integer;
        }
        for (Integer integer : arr2) {
            res[i++] = integer;
        }
        return res;

    }

    public static void main(String[] args) {
        wk387 w = new wk387();
        w.resultArray(new int[]{5,14,3,1,2});
    }
}