package weekly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class wk335 {

    //模拟
    public int passThePillow(int n, int time) {
        int i = 0;
        while (time >= n - 1) {
            i++;
            time -= (n - 1);
        }
        int ans = 0;
        if (i % 2 == 0) {
            ans = time + 1;
        } else {
            ans = n - time;
        }
        return ans;
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

    //层序遍历
    public long kthLargestLevelSum(TreeNode root, int k) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Long> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            long sum = 0;
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                sum += cur.val;
                TreeNode left = cur.left;
                TreeNode right = cur.right;
                if (left != null) {
                    queue.add(left);
                }
                if (right != null) {
                    queue.add(right);
                }
            }
            list.add(sum);
        }
        Collections.sort(list, Comparator.reverseOrder());
        return list.size() < k ? -1 : list.get(k - 1);
    }


    //质因数分解+统计次数
  /*  static public int findValidSplit(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            List<Integer> l = new ArrayList<>();
            for (int j = 2; j <= Math.sqrt(nums[i]); j++) {
                while (num % j == 0) {
                    counter.put(j, counter.getOrDefault(j, 0) + 1);
                    l.add(j);
                    num /= j;
                }
            }
            if (num > 1) {
                int j = num;
                counter.put(j, counter.getOrDefault(j, 0) + 1);
                l.add(j);
            }
            list.add(l);
        }
        int ans = -1;
        Map<Integer, Integer> pre = new HashMap<>();
        for (int i = 0; i < nums.length - 1; i++) {
            List<Integer> l = list.get(i);
            for (Integer mul : l) {
                Integer count = counter.get(mul);
                if (count == 1) {
                    counter.remove(mul);
                } else {
                    counter.put(mul, count - 1);
                }
                pre.put(mul, pre.getOrDefault(mul, 0) + 1);
            }
            boolean flag = true;
            for (Integer key : pre.keySet()) {
                if (counter.containsKey(key)) {
                    flag = false;
                }
            }
            if (flag) {
                ans = i;
                break;
            }
        }
        return ans;
    }*/
    static public int findValidSplit(int[] nums) {
        Map<Integer,Integer> left=new HashMap<>();
        int []right=new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            for (int j = 2; j <= Math.sqrt(nums[i]); j++) {
                if (num % j == 0) {
                   if(left.containsKey(j)){
                       right[left.get(j)]=i;//更新质数j出现的最左位置的最右位置
                   }else {
                       left.put(j,i);
                   }
                    num /= j;
                }
                while (num%j==0){
                    num/=j;
                }
            }
            if (num > 1) {
               if(left.containsKey(num)){
                   right[left.get(num)]=i;//更新质数j出现的最左位置的最右位置
               }else {
                   left.put(num,i);
               }
            }
        }
        for (int i = 0,maxR=0; i < right.length; i++) {
             if(i>maxR){
                 return maxR;
             }
             maxR=Math.max(maxR,right[i]);
        }
        return -1;
    }

    //多重背包 可压缩成以为
    static public int waysToReachTarget(int target, int[][] types) {
        int[] dp = new int[target + 1];
        int mod = (int) 1e9 + 7;
        dp[0] = 1;
        for (int[] type : types) {
            int count = type[0];
            int mark = type[1];
                for (int j = dp.length - 1; j >= 0; j--) {
                    for (int i = 0; i < count; i++) {
                        if (j - mark >= 0) {
                        dp[j] += dp[j - mark];
                        dp[j] %= mod;
                    }
                }
                mark += type[1];
            }

        }
        return dp[target];
    }

    public static void main(String[] args) {
        findValidSplit(new int[]{4, 7, 8, 15, 3, 5});
    }
}
