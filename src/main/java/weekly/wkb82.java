package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class wkb82 {


    //ranking: 592 / 4144 拉胯

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

    //简单题，dfs，直接做就好了
    public boolean evaluateTree(TreeNode root) {
        //叶子结点
        if (root.left == null && root.right == null) {
            return root.val == 0 ? false : true;
        }
        boolean left = evaluateTree(root.left);
        boolean right = evaluateTree(root.right);
        if (root.val == 2) {
            return left | right;
        } else {
            return left & right;
        }
    }


    //中等题，贪心，先把能上车的乘车都安排上，然后从能上车最后一个位置开始往前遍历
    static public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {
        int j = 0, c = 0;
        Arrays.sort(buses);
        Arrays.sort(passengers);
        for (int bus : buses) {
            for (c = capacity; c > 0 && j < passengers.length && passengers[j] <= bus; j++) {
                c--;
            }
        }

        j--; //j最后一个上车的乘客  c表示最后一辆车的剩余容量
        //容量>0表示最后一辆车有剩余，可以从最后一辆车的发车时刻开始找
        //容量==0表示最后一个已经满了，此时可能有一些在发车之前达到，但也没能坐上车，可以从最后一个乘客开始找
        int last = c > 0 ? buses[buses.length - 1] : passengers[j];
        while (j >= 0 && passengers[j--] == last) last--;
        return last;
    }


    //中等题，贪心，先求差值的绝对值，然后从大往小开始截断
    static public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        //求差值+排序
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            list.add(Math.abs(nums1[i] - nums2[i]));
        }
        list.add(0);
        Collections.sort(list);
        long k = k1 + k2;//可以减去的最大值
        int i;
        long res = 0;
        for (i = list.size() - 1; i > 0; i--) {
            //此时的最大值
            long max = list.get(i);
            //需要将所有的max减到min
            long min = list.get(i - 1);
            //需要减的数量
            long count = (list.size() - i);
            //k可以给这些,将数组的最大值砍到min
            if (k > (max - min) * count) {
                k -= (max - min) * count;
                //k不足，只能由部分max减到min
            } else {
                //还能有几个max减到min
                long step = k / (count);
                k -= step * (count);//剩余的k
                //计算
                res += k * (max - step - 1) * (max - step - 1) + (count - k) * (max - step) * (max - step);

                break;
            }

        }
        //将剩下的没有减去的累加
        for (int j = 0; j < i; j++) {
            res += (long) list.get(j) * list.get(j);
        }
        return res;

    }


    //困难题, 单调栈，要看清题目的本质
    //要求子数组每个元素>threshold/k,即子数组的下界>threshold/k,求以每个数字为下界的子数组长度
    // 两次单调栈求左右边界
    static public int validSubarraySize(int[] nums, int threshold) {
        //单调栈求左右边界
        Deque<Integer> deque = new ArrayDeque<>();
        int[] right = new int[nums.length];
        int[] left = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] > nums[i]) {
                Integer index = deque.pollLast();
                right[index] = i;
            }
            deque.addLast(i);
        }
        while (!deque.isEmpty()) {
            right[deque.pollLast()] = nums.length;
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            while (!deque.isEmpty() && nums[deque.peekLast()] > nums[i]) {
                Integer index = deque.pollLast();
                left[index] = i;
            }
            deque.addLast(i);
        }
        while (!deque.isEmpty()) {
            left[deque.pollLast()] = -1;
        }
        //判断是不是存在子数组长度
        for (int i = 0; i < nums.length; i++) {
            int len = right[i] - left[i] - 1;
            if (nums[i] > threshold / len) {
                return len;
            }
        }
        return -1;
    }


    //并查集
    class UnionFind {
        public final int[] parents;
        public int count;

        public UnionFind(int n) {
            this.parents = new int[n];
            reset();
        }

        public void reset() {
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
            }
            count = parents.length - 1;
        }

        public int find(int i) {
            int parent = parents[i];
            if (parent == i) {
                return i;
            } else {
                int root = find(parent);
                parents[i] = root;
                return root;
            }
        }

        public boolean union(int i, int j) {
            int r1 = find(i);
            int r2 = find(j);
            if (r1 != r2) {
                count--;
                parents[r1] = r2;
                return true;
            } else {
                return false;
            }
        }

  /*      void isolate(int x) {
            if (x != parents[x]) {
                parents[x] = x;
                count++;
            }
        }*/

    }


    //并查集也可以做，需要先排序，从大往小一次连接i和i+1，最终能组成一个
   /*
   public int validSubarraySize(int[] nums, int threshold) {
        UnionFind uf = new UnionFind(nums.length + 1);
        int[][] copy = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            copy[i][0] = nums[i];
            copy[i][1] = i;
        }
        Arrays.sort(copy, (a, b) -> b[0] - a[0]);
        int[] count = new int[nums.length];
        Arrays.fill(count, 1);
        for (int i = 0; i < copy.length; i++) {
            //i在并查集中的序号
            int i1 = uf.find(copy[i][1]);
            //i+1在并查集中的序号
            int i2 = uf.find(copy[i][1] + 1);
           //合并
            uf.union(i1, i2);
            //新的序号
            int index = uf.find(copy[i][1]);
            //更新序号下面有多少个元素
            count[index] = count[i1] + count[i2];

           if(copy[i][0]>threshold/(count[index]-1)) return count[index];
        }
        return -1;
    }
    */

    public static void main(String[] args) {
        validSubarraySize(new int[]{1, 1, 1, 1, 1}, 4);
    }
}
