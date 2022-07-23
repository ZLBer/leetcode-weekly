package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class wk302 {

    //ranking: 445 / 7092

    //Array
    //简单题，统计每个数字出现多少次就行
    public int[] numberOfPairs(int[] nums) {
        int[] res = new int[2];
        int count[] = new int[102];
        for (int c : nums) {
            count[c]++;
        }
        for (int i : count) {
            res[0] += i / 2;
            res[1] += i % 2;
        }
        return res;
    }

    //Heap
    //中等题，将数位和相同的放在一个优先队列中，弹出最大的两个
    public int maximumSum(int[] nums) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int num : nums) {
            int c = num;
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            if (!map.containsKey(sum)) {
                map.put(sum, new PriorityQueue<>(Comparator.reverseOrder()));
            }
            map.get(sum).add(c);
        }
        int ans = -1;
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() < 2) continue;
            int max = entry.getValue().poll() + entry.getValue().poll();
            ans = Math.max(ans, max);
        }
        return ans;
    }

    class help {
        String value;
        int index;

        help(String v, int i) {
            value = v;
            index = i;
        }
    }
    //Heap
    //中等题，先把所有的剪裁结果都保存下来，然后取第k个即可
    public int[] smallestTrimmedNumbers(String[] nums, int[][] queries) {
        int m = nums[0].length();
        Map<Integer, List<help>> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            List<help> list = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {
                String substring = nums[j].substring(i);
                list.add(new help(substring, j));
            }
            map.put(nums[0].length() - i, list);
        }

        for (List<help> value : map.values()) {
            Collections.sort(value, (a, b) -> a.value.compareTo(b.value));
        }


        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            List<help> list = map.get(query[1]);

            res[i] = list.get(query[0] - 1).index;
        }
        return res;
    }

    private static long gcd2(long a, long b) {
        return (a == 0 ? b : gcd2(b % a, a));
    }

    //MATH
    //困难提不困难，求numsDivide的最大公因数，nums从小往大遍历，看哪个能整除即可
    public int minOperations(int[] nums, int[] numsDivide) {
        Arrays.sort(nums);

        int pre = numsDivide[0];
        for (int i = 0; i < numsDivide.length; i++) {
            pre = (int) gcd2(pre, numsDivide[i]);
        }
        for (int i = 0; i < nums.length; i++) {
          if(pre%nums[i]==0) return i;
        }
        return -1;
    }

    public static void main(String[] args) {

    }

}
