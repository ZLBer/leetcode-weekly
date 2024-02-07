package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wk382 {
    //遍历
    public int countKeyChanges(String s) {
        int ans = 0;
        for (int i = 1; i < s.length(); i++) {
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(i - 1))) {
                ans++;
            }
        }
        return ans;
    }


    //枚举
    public int maximumLength(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
        }
        Set<Integer> set = new HashSet<>();
        Integer ones = counter.getOrDefault(1, 0);
        int res = ones % 2 == 1 ? ones : ones - 1;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int ans = 0;
            int num = nums[i];
            if (num == 1) continue;
            if (set.contains(num)) continue;
            while (true) {
                Integer count = counter.get(num);
                set.add(num);
                if (count == null) {
                    ans -= 1;
                    break;
                } else if (count >= 2) {
                    ans += 2;
                } else if (count == 1) {
                    ans += 1;
                    break;
                }
                num *= num;
            }
            res = Math.max(ans, res);
        }
        return res;
    }


    //找规律
    public long flowerGame(int n, int m) {
        long na = 0, nb = 0;
        if (n % 2 == 1) {
            na = (n + 1) / 2;
            nb = n / 2;
        } else {
            na = (n) / 2;
            nb = n / 2;
        }

        long ma = 0, mb = 0;
        if (m % 2 == 1) {
            ma = (m + 1) / 2;
            mb = m / 2;
        } else {
            ma = m / 2;
            mb = m / 2;
        }
      long res=0;
       res+= na*mb+nb*ma;
       return res;
    }

    // 试填法
    public int minOrAfterOperations(int[] nums, int k) {
        int ans = 0;
        int mask = 0;
        for (int b = 29; b >= 0; b--) {
            mask |= 1 << b;
            int cnt = 0; // 操作次数
            int and = -1; // -1 的二进制全为 1
            for (int x : nums) {
                and &= x & mask;
                if (and != 0) {
                    cnt++; // 合并 x，操作次数加一
                } else {
                    and = -1; // 准备合并下一段
                }
            }
            if (cnt > k) {
                ans |= 1 << b; // 答案的这个比特位必须是 1
                mask ^= 1 << b; // 后面不考虑这个比特位
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        wk382 w = new wk382();
        w.maximumLength(new int[]{5, 4, 1, 2, 2});
    }
}
