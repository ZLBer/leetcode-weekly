package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class wkb110 {

    //遍历
    public int accountBalanceAfterPurchase(int purchaseAmount) {
        int min = Integer.MAX_VALUE;
        int ans = 0;
        for (int i = 100; i >= 0; i -= 10) {
            if (Math.abs(purchaseAmount - i) < min) {
                min = Math.abs(purchaseAmount - i);
                ans = i;
            }
        }
        return 100 - ans;
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
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode cur = head.next;
        ListNode pre = head;
        while (cur != null) {
            int gcd = getGCD(pre.val, cur.val);
            ListNode node = new ListNode(gcd);
            pre.next = node;
            node.next = cur;
            pre = cur;
            cur = cur.next;
        }
        return head;
    }

    public static int getGCD(int a, int b) {
        if (a < 0 || b < 0) {
            return -1; // 数学上不考虑负数的约数
        }
        if (b == 0) {
            return a;
        }
        return a % b == 0 ? b : getGCD(b, a % b);
    }


    //哈希
    static public int minimumSeconds(List<Integer> nums) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> dis = new HashMap<>();
        Map<Integer, Integer> first = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            Integer num = nums.get(i);
            if (map.containsKey(num)) {
                int d = i - map.get(num) - 1;
                dis.put(num, Math.max(dis.get(num), (d + 1) / 2));
            } else {
                first.put(num, i);
                dis.put(num, 0);
            }
            map.put(num, i);
        }
        int ans = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : dis.entrySet()) {
            int d = (first.get(entry.getKey()) + (nums.size() - map.get(entry.getKey()) - 1) + 1) / 2;
            Integer value = entry.getValue();
            ans = Math.min(ans, Math.max(value, d));
        }

        return ans;
    }


    //排序+dp
   static public int minimumTime(List<Integer> nums1, List<Integer> nums2, int x) {
        int s1 = 0, s2 = 0;
        Integer[] idx = new Integer[nums1.size()];
        for (int i = 0; i < nums1.size(); i++) {
            idx[i] = i;
            s1 += nums1.get(i);
            s2 += nums2.get(i);
        }

        int[][] dp = new int[nums1.size() + 1][nums1.size() + 1];
        //关键的排序
        Arrays.sort(idx, (i, j) -> (nums2.get(i) - nums2.get(j)));
        for (int i = 0; i < nums1.size(); i++) {
            for (int j = 0; j < nums1.size(); j++) {
                //前i个选j个
                dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + nums1.get(idx[i]) + nums2.get(idx[i]) * (j + 1));
            }
        }

        for (int i = 0; i <dp[0].length; i++) {
            System.out.println( dp[nums1.size()][i]);
            if (s1 + s2*(i) - dp[nums1.size()][i] <= x) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        minimumTime(Arrays.asList(9,2,8,3,1,9,7,6),
                Arrays.asList(0,3,4,1,3,4,2,1),40);
    }
}
