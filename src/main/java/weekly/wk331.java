package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class wk331 {
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int gift : gifts) {
            priorityQueue.add(gift);
        }
        while (k-- > 0 && !priorityQueue.isEmpty()) {
            Integer poll = priorityQueue.poll();
            priorityQueue.add((int) Math.sqrt(poll));
        }
        long res = 0;
        while (!priorityQueue.isEmpty()) {
            res += priorityQueue.poll();
        }
        return res;
    }

    public int[] vowelStrings(String[] words, int[][] queries) {
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        int[] count = new int[words.length + 1];
        for (int i = 0; i < words.length; i++) {
            if (set.contains(words[i].charAt(0)) && set.contains(words[i].charAt(words[i].length() - 1))) {
                count[i + 1]++;
            }
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = count[queries[i][1] + 1] - count[queries[i][0]];
        }
        return res;
    }


    /*public int minCapability(int[] nums, int k) {
        int[] dp = new int[nums.length];


        dp[0]=nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i]=Math.min(dp[i-1],nums[i]);
        }

        for (int i = 2; i <= k; i++) {
            int []ndp=new int[nums.length];
            for (int j = 0; j < nums.length; j++) {
                //偷这个房间
                ndp[j] =Math.max(nums[j] , (j - 2 >= 0 ? dp[j - 2] : Integer.MAX_VALUE));
                //不偷这个房间
                ndp[j] = Math.min(j - 1 >= 0 ? ndp[j - 1] : Integer.MAX_VALUE, ndp[j]);
            }
            dp=ndp;
        }
        return dp[nums.length - 1];
    }*/

    /*public int minCapability(int[] nums, int k) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < nums.length; i++) {
            priorityQueue.add(new int[]{nums[i], i});
        }
        Set<Integer> set = new HashSet<>();
        int max = 0;
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            if (!set.contains(poll[1] - 1) && !set.contains(poll[1] + 1)) {
                max = poll[0];
                set.add(poll[1]);
                k--;
                if (k == 0) return max;
            }
        }
        return max;
    }*/

    public int minCapability(int[] nums, int k) {
        int left = nums[0], right = nums[0];
        for (int num : nums) {
            left = Math.min(left, num);
            right = Math.max(right, num);
        }
        while (left < right) {
            int mid = (left + right) / 2;
            if (check(mid, nums, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    boolean check(int max, int[] nums, int k) {
        int no = -1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= max && i != no) {
                count++;
                no = i + 1;
            }
        }
        return count >= k;
    }

    public static void main(String[] args) {
        wk331 w = new wk331();
        w.minCost(new int[]{4, 2, 2, 2}, new int[]{1, 4, 1, 2});
    }

    int[] mapToInt(TreeMap<Integer, Integer> map1) {
        int[] res = new int[map1.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            res[i++] = entry.getKey();
        }
        return res;
    }

    public long minCost(int[] basket1, int[] basket2) {
        TreeMap<Integer, Integer> map1 = new TreeMap<>();
        TreeMap<Integer, Integer> map2 = new TreeMap<>();
        int min = Integer.MAX_VALUE;

        for (int i : basket1) {
            min = Math.min(min, i);
            map1.put(i, map1.getOrDefault(i, 0) + 1);
        }
        for (int i : basket2) {
            min = Math.min(min, i);
            map2.put(i, map2.getOrDefault(i, 0) + 1);
        }
        //消去相同的
        for (int key : mapToInt(map1)) {
            int count1 = map1.get(key);
            int count2 = map2.getOrDefault(key, 0);
            if (count1 > count2) {
                map2.remove(key);
                map1.put(key, count1 - count2);
            } else if (count2 > count1) {
                map1.remove(key);
                map2.put(key, count2 - count1);
            } else {
                map1.remove(key);
                map2.remove(key);
            }
        }

        long res = 0;
        //再展开，只需要存一半值即可
        List<Integer> list1 = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            if(entry.getValue()%2!=0) return -1;
            for (int i = 0; i < entry.getValue()/2; i++) {
                list1.add(entry.getKey());
            }
        }
        List<Integer> list2 = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map2.entrySet()) {
            if(entry.getValue()%2!=0) return -1;
            for (int i = 0; i < entry.getValue()/2; i++) {
                list2.add(entry.getKey());
            }
        }
        //遍历求最小
        for (int i = 0; i < list1.size(); i++) {
            res+=Math.min(2*min,Math.min(list1.get(i),list2.get(list2.size()-i-1)));
        }

        return res;
    }
}


