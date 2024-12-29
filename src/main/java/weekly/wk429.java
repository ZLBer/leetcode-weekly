package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wk429 {
    //set
    public int minimumOperations(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int index = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (set.contains(nums[i])) {
                index = i;
                break;
            }
            set.add(nums[i]);
        }
        if (index == -1) {
            return 0;
        }
        int count = ((index + 3) / 3);

        return count;
    }

    //贪心
//    public int maxDistinctElements(int[] nums, int k) {
//        Arrays.sort(nums);
//        int left = nums[0] - k;
//        int ans = 1;
//        for (int i = 1; i < nums.length; i++) {
//            int need = left + 1;
//            if (nums[i] - k <= need && nums[i] + k >= need) {
//                left = need;
//                ans++;
//            } else if (nums[i] - k > need) {
//                left = nums[i] - k;
//                ans++;
//            }
//        }
//        return ans;
//    }

    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 0;
        int pre = Integer.MIN_VALUE; // 记录每个人左边的人的位置
        for (int x : nums) {
            x = Math.min(Math.max(x - k, pre + 1), x + k);
            if (x > pre) {
                ans++;
                pre = x;
            }
        }
        return ans;
    }

    public int minLength(String S, int numOps) {
        char[] s = S.toCharArray();
        int left = 0;
        int right = s.length;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid, s, numOps)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int m, char[] s, int numOps) {
        int n = s.length;
        int cnt = 0;
        if (m == 1) {
            // 改成 0101...
            for (int i = 0; i < n; i++) {
                if (i % 2 == 0) {
                    if (s[i] != '0') cnt++;
                } else {
                    if (s[i] != '1') cnt++;
                }
            }
            // n-cnt 表示改成 1010...
            cnt = Math.min(cnt, n - cnt);
        } else {
            int k = 0;
            for (int i = 0; i < n; i++) {
                k++;
                // 到达连续相同子串的末尾
                if (i == n - 1 || s[i] != s[i + 1]) {
                    cnt += k / (m + 1);
                    k = 0;
                }
            }
        }
        return cnt <= numOps;
    }


    public static void main(String[] args) {
        wk429 w = new wk429();
        w.minLength("000001", 1);
    }
}
