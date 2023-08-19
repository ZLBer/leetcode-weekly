package weekly;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class wk358 {

    //模拟
    public int maxSum(int[] nums) {
        int[] help = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int max = 0;
            while (num > 0) {
                max = Math.max(max, num % 10);
                num /= 10;
            }
            help[i] = max;
        }

        int ans = -1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (help[i] == help[j]) {
                    ans = Math.max(ans, nums[i] + nums[j]);
                }
            }
        }
        return ans;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //链表
    public ListNode doubleIt(ListNode head) {
        ListNode temp = head;
        ListNode pre = new ListNode(0);
        pre.next = head;
        head = pre;
        while (temp != null) {

            int val = temp.val;
            val *= 2;

            pre.val += val / 10;
            temp.val = val % 10;
            pre = temp;
            temp = temp.next;
        }

        if (head.val == 0) {
            return head.next;
        } else {
            return head;
        }
    }


    //滑动窗口
    public int minAbsoluteDifference(List<Integer> nums, int x) {
        TreeMap<Integer, Integer> map = new TreeMap<>();

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.size(); i++) {

            if (i >= x) {
                map.put(nums.get(i - x), map.getOrDefault(nums.get(i - x), 0) + 1);
            }

            int cur = nums.get(i);
            Integer floorKey = map.floorKey(cur);
            if (floorKey != null) {
                ans = Math.min(ans, Math.abs(floorKey - cur));
            }
            Integer ceilKey = map.ceilingKey(cur);
            if (ceilKey != null) {
                ans = Math.min(ans, Math.abs(ceilKey - cur));
            }
        }
        return ans;
    }

    public static int countDistinctFactors(int n) {
        int count = 0;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                count++;
                while (n % i == 0) {
                    n /= i;
                }
            }
        }
        if (n > 1) {
            count++;
        }
        return count;
    }

    static Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(1, 0);
        for(int i=2;i<=(int)1e5+7;i++){
            map.put(i, countDistinctFactors(i));
        }

    }

    //质数分解+单调栈+快速幂
    public int maximumScore(List<Integer> nums, int k) {

        ArrayDeque<int[]> deque = new ArrayDeque<>();
        Integer[][] dp = new Integer[nums.size()][2];
        //单调栈求边界
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            int count = map.get(num);
            while (!deque.isEmpty() && deque.peekLast()[0] < count) {
                int[] ints = deque.pollLast();
                int l = ints[1] - ints[2]; //左边有多少个
                int r = i - ints[1]; //右边有多少个
                dp[ints[1]] = new Integer[]{l * r, nums.get(ints[1])};
            }
            int left = deque.isEmpty() ? -1 : deque.peekLast()[1];
            deque.add(new int[]{count, i, left});
        }
        while (!deque.isEmpty()) {
            int[] ints = deque.pollLast();
            int l = ints[1] - ints[2];
            int r = nums.size() - ints[1];
            dp[ints[1]] = new Integer[]{l * r, nums.get(ints[1])};
        }
        long ans = 1;
        int mod = (int) 1e9 + 7;
        Arrays.sort(dp, (a, b) -> b[1] - a[1]);


        //快速幂求幂
        for (int i = 0; i < dp.length&&k>0; i++) {
            int count = dp[i][0]>k?k:dp[i][0];
            long l = fastPower(dp[i][1], count) % mod;
            ans *= l;
            ans %= mod;
            k -= count;
        }
        return (int) ans;
    }

    public static long fastPower(long a, long b) {
        long ans = 1;
        int mod = (int) 1e9 + 7;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans *= a;
                ans %= mod;
            }
            a *= a;
            a %= mod;
            b >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(fastPower(7, 5));


    }

}
