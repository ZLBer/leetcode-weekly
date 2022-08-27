package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class wk307 {

    //ranking: 659 / 7064


    //简单题，注意比赛顺序是从后往前的..
    public int minNumberOfHours(int initialEnergy, int initialExperience, int[] energy, int[] experience) {

        int ans = 0;
        for (int i = 0; i < energy.length; i++) {
            if (energy[i] >= initialEnergy) {
                //严格大于
                ans += energy[i] - initialEnergy + 1;
                initialEnergy += energy[i] - initialEnergy + 1;
            }
            //精力扣除
            initialEnergy -= energy[i];

            if (experience[i] >= initialExperience) {
                //严格大于
                ans += experience[i] - initialExperience + 1;
                initialExperience += experience[i] - initialExperience + 1;
            }
            //经验上涨
            initialExperience += experience[i];
        }
        return ans;
    }


    //中等题，注意边界条件，只需计算前半部分即可
    public String largestPalindromic(String num) {
        int[] count = new int[10];
        for (char c : num.toCharArray()) {
            count[c - '0']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = count.length - 1; i >= 0; i--) {
            while (count[i] >= 2) {
                sb.append(i);
                count[i] -= 2;
            }
        }
        boolean add = false;
        int c = -1;
        for (int i = count.length - 1; i >= 0; i--) {
            if (count[i] > 0) {
                add = true;
                c = i;
                break;
            }
        }
        //
        while (sb.length() > 0 && sb.charAt(0) == '0') {
            sb.delete(0, 1);
        }
        //没有单独的>0的数字
        if (c <= 0 && sb.length() == 0) return "0";
        StringBuilder reverse = new StringBuilder(sb).reverse();
        if (!add) {
            return sb.append(reverse).toString();
        } else {
            return sb.append(c).append(reverse).toString();
        }
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

    //中等题，先计算图，然后bfs
    public int amountOfTime(TreeNode root, int start) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        help(root, map, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        Set<Integer> set = new HashSet<>();
        int time = 0;
        set.add(start);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Integer poll = queue.poll();
                if (!map.containsKey(poll)) continue;
                for (Integer next : map.get(poll)) {
                    if (set.contains(next)) continue;
                    set.add(next);
                    queue.add(next);
                }
            }
            time++;
        }
        return time - 1;
    }

    void help(TreeNode root, Map<Integer, List<Integer>> map, int up) {

        if (!map.containsKey(root)) {
            map.put(root.val, new ArrayList<>());
        }
        if (up != -1) {
            map.get(root.val).add(up);
        }
        if (root.left != null) {
            map.get(root.val).add(root.left.val);
            help(root.left, map, root.val);
        }
        if (root.right != null) {
            map.get(root.val).add(root.right.val);
            help(root.right, map, root.val);
        }
    }

    class Pair {
        long sum = 0;
        int index = 0;

        Pair(long sum, int index) {
            this.sum = sum;
            this.index = index;
        }
    }


    //困难题，当时没想出来，看了题解恍然大悟
    public long kSum(int[] nums, int k) {

        //最大的数一定是所有正数的和
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                sum += nums[i];
            } else {
                //变成正数，一律按减法处理
                nums[i] =-nums[i];
            }
        }
        if (k == 1) return sum;
        Arrays.sort(nums);

        PriorityQueue<Pair> priorityQueue = new PriorityQueue<>((a, b) -> (int) (b.sum - a.sum));
        priorityQueue.add(new Pair(sum - nums[0], 0));
        //依次对每个位置取或者不取
        for(int i=2;i<k;i++){
            Pair poll = priorityQueue.poll();
            //System.out.println(poll.sum);
            if (poll.index < nums.length-1) {
                priorityQueue.add(new Pair(poll.sum - nums[poll.index + 1], poll.index + 1));
                priorityQueue.add(new Pair(poll.sum + nums[poll.index]- nums[poll.index + 1], poll.index + 1));
            }
        }
        return priorityQueue.poll().sum;
    }

    public static void main(String[] args) {

    }
}
