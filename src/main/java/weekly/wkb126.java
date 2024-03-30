package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class wkb126 {
    //模拟
    public int sumOfEncryptedInt(int[] nums) {

        int ans = 0;
        for (int num : nums) {
            String s = num + "";
            int max = 0;
            for (int i = 0; i < s.length(); i++) {
                int c = s.charAt(i) - '0';
                max = Math.max(max, c);
            }


            int n = 0;
            for (int i = 0; i < s.length(); i++) {
                n = n * 10 + max;
            }
            ans += n;
        }
        return ans;
    }


    //排序or小根堆
    public long[] unmarkedSumArray(int[] nums, int[][] queries) {

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            priorityQueue.add(new int[]{nums[i], i});
        }

        Set<Integer> set = new HashSet<>();
        long[] ans = new long[queries.length];

        for (int j = 0; j < queries.length; j++) {
            int[] query = queries[j];
            int index = query[0];
            int k = query[1];
            if (!set.contains(index)) {
                sum -= nums[index];
                set.add(index);
            }
            for (int i = 0; i < k; i++) {
                if (priorityQueue.isEmpty()) break;
                int[] poll = priorityQueue.poll();
                if (!set.contains(poll[1])) {
                    sum -= poll[0];
                    set.add(poll[1]);
                } else {
                    i--;
                }
            }
            ans[j] = sum;
        }
        return ans;
    }



    // 小根堆/贪心
    public String minimizeStringValue(String s) {

        int need = 0;
        int[] count = new int[26];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        Arrays.fill(count, 0);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '?') {
                need++;
            } else {
                count[s.charAt(i) - 'a']++;
            }
        }
        for (int i = 0; i < count.length; i++) {
            priorityQueue.add(new int[]{count[i], i});
        }

        List<Character> list = new ArrayList<>();
        for (int i = 0; i < need; i++) {
            int[] poll = priorityQueue.poll();
            list.add((char) ('a' + poll[1]));
            poll[0]++;
            priorityQueue.add(poll);
        }

        Collections.sort(list);

        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '?') {
                sb.append(list.get(index++));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }


    //dp
    public int sumOfPower(int[] nums, int k) {
        int mod = (int) 1e9 + 7;
        int[] mem = new int[k + 1];
        mem[0] = 1;
        for (int num : nums) {
            int[] nextMem = new int[k + 1];
            for (int j = k; j >= 0; --j) {
                // 子序列累计，加入or不加入两种
                if (j - num >= 0) {
                    nextMem[j] = ((2 * mem[j]) % mod + mem[j - num]) % mod;
                } else {
                    nextMem[j] = (2 * mem[j]) % mod;
                }
            }
            mem = nextMem;
        }
        return mem[k];
    }

    public static void main(String[] args) {
    }
}
