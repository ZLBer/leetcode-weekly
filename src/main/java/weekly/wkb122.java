package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class wkb122 {

    public int minimumCost(int[] nums) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 1; i < nums.length; i++) {
            priorityQueue.add(nums[i]);
        }
        int ans = nums[0];
        for (int i = 0; i < 2; i++) {
            ans += priorityQueue.poll();
        }
        return ans;
    }

    // public boolean canSortArray(int[] nums) {
//        List<List<Integer>> counter = new ArrayList<>();
//        List<List<Integer>> indexes = new ArrayList<>();
//        for (int i = 0; i <= 8; i++) {
//            counter.add(new ArrayList<>());
//            indexes.add(new ArrayList<>());
//        }
//        for (int i = 0; i < nums.length; i++) {
//            int c = Integer.bitCount(nums[i]);
//            counter.get(c).add(i);
//            indexes.get(c).add(i);
//        }
//        for (List<Integer> list : counter) {
//            Collections.sort(list, (a, b) -> nums[a] - nums[b]);
//        }
//        int[] temp = new int[nums.length];
//        for (int i = 0; i <= 8; i++) {
//            for (int j = 0; j < counter.get(i).size(); j++) {
//                temp[indexes.get(i).get(j)]=nums[counter.get(i).get(j)];
//            }
//        }
//        for (int i = 1; i < temp.length; i++) {
//            if(temp[i]<temp[i-1])return false;
//        }
//        return true;
//    }


    //分组循环
    public boolean canSortArray(int[] nums) {
        int preMax = 0;

        int i = 0;
        while (i < nums.length) {
            int max=nums[i];
            int ones=Integer.bitCount(nums[i]);
            while (i<nums.length&&ones==Integer.bitCount(nums[i])){
                if(nums[i]<preMax){
                    return false;
                }
                max=Math.max(max,nums[i]);
            }
            preMax=max;
        }

        return true;
    }

//    public int minimumArrayLength(int[] nums) {
//        Map<Integer, Integer> counter = new HashMap<>();
//        int min = nums[0];
//        for (int num : nums) {
//            counter.put(num, counter.getOrDefault(num, 0) + 1);
//            min = Math.min(min, num);
//        }
//        int c = counter.get(min);
//        return (c+1)/2;
//    }

    //贪心
    public int minimumArrayLength(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
        }
        Arrays.sort(nums);
        int min = nums[0];
        int ans = -1;

        //能不能构造出小于min的整数
        boolean canFind = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % min != 0) {
                canFind = true;
            }
        }

        if (canFind) {
            return 1;
        } else {
            return (counter.get(min) + 1) / 2;
        }
    }


    // 滑动窗口中的前 k 小值
    // 滑动窗口+双堆+懒删除
    // 或 treeMap
    public long minimumCost(int[] nums, int k, int dist) {
        long ans = Long.MAX_VALUE;
        long sum = 0;

        //记录堆里有哪些还没被删除
        Set<Integer> leftSet = new HashSet<>();
        Set<Integer> rightSet = new HashSet<>();
        //大跟堆
        PriorityQueue<Integer> leftPq = new PriorityQueue<>((a, b) -> nums[b] - nums[a]);
        //小跟堆
        PriorityQueue<Integer> rightPq = new PriorityQueue<>((a, b) -> nums[a] - nums[b]);
        for (int i = 1; i < Math.min(1 + dist, nums.length); i++) {
            //左堆添加
            leftSet.add(i);
            leftPq.add(i);
            sum += nums[i];
            if (leftPq.size() > k - 1) {

                //左堆删除
                Integer poll = leftPq.poll();
                leftSet.remove(poll);
                sum -= nums[poll];

                //右堆添加
                rightPq.add(poll);
                rightSet.add(poll);
            }
        }

        for (int begin = 1; begin + dist < nums.length; begin++) {
            int end = begin + dist;
            //左堆添加
            leftPq.add(end);
            leftSet.add(end);
            sum += nums[end];
            while (leftSet.size() > k - 1) {
                Integer poll = leftPq.poll();
                //是不是之前已经被删除了
                if (!leftSet.contains(poll)) continue;
                //左堆删除
                leftSet.remove(poll);
                sum -= nums[poll];

                //右堆添加
                rightPq.add(poll);
                rightSet.add(poll);
            }

            // 结果判断
            ans = Math.min(sum, ans);

            // 开始滑动窗口
            //左堆是不是有这个
            if (leftSet.contains(begin)) {
                //左堆删除，pq先不变
                sum -= nums[begin];
                leftSet.remove(begin);
            }
            // 右堆也可能删除
            rightSet.remove(begin);

            // 左堆不足k-1个，用右堆来补齐
            while (!rightSet.isEmpty() && leftSet.size() < k - 1) {
                //右堆删除
                Integer poll = rightPq.poll();
                if (!rightSet.contains(poll)) continue;
                //左堆添加
                leftPq.add(poll);
                leftSet.add(poll);
                sum += nums[poll];
            }
        }
        return ans + nums[0];
    }


    public static void main(String[] args) {
        wkb122 w = new wkb122();
        w.minimumCost(new int[]{1, 3, 2, 6, 4, 2}, 3, 3);
    }
}
