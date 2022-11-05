package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class wkb90 {

    //ranking: 835 / 3624

    //改成map也行啊
    public String oddString(String[] words) {

        String[] diff = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            String s = "";
            for (int j = 1; j < words[i].length(); j++) {
                s += words[i].charAt(j) - words[i].charAt(j - 1) + ",";
                diff[i] = s;
            }
        }

        for (int i = 0; i < diff.length; i++) {
            int c = 0;
            for (int j = 0; j < diff.length; j++) {
                if (!diff[i].equals(diff[j])) {
                    c++;
                }
            }
            if (c == diff.length - 1) return words[i];
        }
        return words[0];
    }


    //遍历左就好了
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            boolean f = false;
            for (int j = 0; j < dictionary.length; j++) {
                boolean flat = true;
                int count = 0;
                for (int k = 0; k < query.length(); k++) {
                    if (query.charAt(k) != dictionary[j].charAt(k)) {
                        count++;
                    }
                    if (count > 2) {
                        flat = false;
                        break;
                    }
                }
                if (flat) {
                    f = true;
                    break;
                }
            }
            if (f) {
                res.add(queries[i]);
            }
        }
        return res;
    }

    public int destroyTargets(int[] nums, int space) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> minMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int sub = nums[i] % space;
            map.put(sub, map.getOrDefault(sub, 0) + 1);
            minMap.put(sub, Math.min(nums[i], minMap.getOrDefault(sub, Integer.MAX_VALUE)));
        }

        int ans = 0;
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > ans) {
                ans = entry.getValue();
                index = entry.getKey();
            } else if (entry.getValue() == ans && minMap.get(entry.getKey()) < (minMap.get(index))) {
                ans = entry.getValue();
                index = entry.getKey();
            }
        }
        return minMap.get(index);

    }


  /*  public int[] secondGreaterElement(int[] nums) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] ans = new int[nums.length];
        Arrays.fill(ans, -1);
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = new ArrayList<>();
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                int pre = deque.pollLast();
                list.add(pre);
                for (Integer prepre : map.get(pre)) {
                    ans[prepre] = nums[i];
                }
            }
            map.put(i, list);
            deque.addLast(i);
        }
        if (deque.size()>=2) {
            Integer head = deque.pollFirst();
            Integer next=deque.pollFirst();
            for (Integer pre : map.get(head)) {
                if(nums[next]>nums[pre]){
                    ans[pre]=nums[next];
                }
            }
        }
        return ans;
    }*/


    //单调栈+优先队列
    //单调递减栈出栈的时候放入优先队列，表示已经有一个数比此数大了
    //后续遍历的时候，查看有限队列最小的是不是比这个数要小，如果是这个数就是第二大的数
    public int[] secondGreaterElement(int[] nums) {
        Deque<Integer> deque = new ArrayDeque<>();
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>((a,b)->nums[a]-nums[b]);
        int[] ans = new int[nums.length];
        Arrays.fill(ans, -1);
        for (int i = 0; i < nums.length; i++) {
            while (!priorityQueue.isEmpty()&&nums[i]>priorityQueue.peek()){
                ans[priorityQueue.poll()]=nums[i];
            }
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                 priorityQueue.add(deque.pollLast());
            }
            deque.addLast(i);
        }

        return ans;
    }
}
