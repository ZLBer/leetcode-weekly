package weekly;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wk356 {

    //遍历
    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int ans = 0;
        for (int hour : hours) {
            if (hour >= target) {
                ans++;
            }
        }
        return ans;
    }


    //滑动窗口
    public int countCompleteSubarrays(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        int left = 0;
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);

            while (map.size() == set.size() && map.get(nums[left]) != 1) {
                map.put(nums[left], map.get(nums[left]) - 1);
                left++;
            }

            if (map.size() == set.size()) {
                ans += (left + 1);
            }

        }
        return ans;
    }

    //额 做法太烂了
   /* public String minimumString(String a, String b, String c) {
        String res = "";
        res = help(a, b, c);
        String ans = help(a, c, b);
        res = cal(res, ans);
        ans = help(b, a, c);
        res = cal(res, ans);

        ans = help(b, c, a);
        res = cal(res, ans);

        ans = help(c, a, b);
        res = cal(res, ans);

        ans = help(c, b, a);
        res = cal(res, ans);

        return res;


    }

    String cal(String res, String ans) {
        if (ans.length() < res.length()) {
            return ans;
        } else if (ans.length() == res.length()) {
            if (res.compareTo(ans) > 0) {
                return ans;
            } else {
                return res;
            }
        } else {
            return res;
        }
    }

    String help(String a, String b, String c) {
        String res = a;
        int index = index(a, b);
        if (!res.contains(b)) {
            res += b.substring(index + 1);
        }


        if (res.contains(c)) {
            return res;
        }

        index = index(res, c);
        res += c.substring(index + 1);

        return res;
    }

    int index(String a, String b) {
        for (int i = b.length() - 1; i >= 0; i--) {
            if (a.endsWith(b.substring(0, i + 1))) {
                return i;
            }
        }
        return -1;
    }*/


    //数位dp通用模板

    char s[];
    int memo[][];
    int MOD = (int) 1e9 + 7;


    //经典数位dp
    public int countSteppingNumbers(String low, String high) {
        int l = calc(low);
        int h = calc(high);
        return (h - l + MOD + (valid(low) ? 1 : 0)) % MOD;
    }

    private int calc(String s) {
        this.s = s.toCharArray();
        int m = s.length();
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++)
            Arrays.fill(memo[i], -1); // -1 表示没有计算过
        return f(0, 0, true, false);
    }


    // pre表示前面的数字
    // isLimit表示是否达到边界
    //isNum表示前面是否有数字
   /* int f(int i, int pre, boolean isLimit, boolean isNum) {
        if (i == s.length)
            return isNum ? 1 : 0; // isNum 为 true 表示得到了一个合法数字
        if (!isLimit && isNum && memo[i][pre] != -1)
            return memo[i][pre];
        int res = 0;
        if (!isNum) // 可以跳过当前数位
            res = f(i + 1, pre, false, false);
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的数字都和 n 的一样，那么这一位至多填数字 s[i]（否则就超过 n 啦）
        for (int d = isNum ? 0 : 1; d <= up; ++d) // 枚举要填入的数字 d
            if (!isNum || Math.abs(d - pre) == 1) {
                res += f(i + 1, d, isLimit && d == up, true);
                res %= MOD;
            }
        if (!isLimit && isNum)
            memo[i][pre] = res;
        return res;
    }*/
    int f(int i, int pre, boolean isLimit, boolean isNum) {

        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i][pre] != -1) {
            return memo[i][pre];
        }

        int res = 0;
        //如果前面没有数字，那么可以继续没有数字
        if (!isNum) {
            res = f(i + 1, pre, false, false);
        }
        //找到此时的上界
        int up = isLimit ? s[i] - '0' : 9;

        //遍历上界，前面有数字时可以从0开始
        for (int d = isNum ? 0 : 1; d <= up; d++) {
            if (!isNum || Math.abs(d - pre) == 1) {
                res += f(i + 1, d, isLimit && d == up, true);
                res %= MOD;
            }
        }
        //只存储不是上界且是数字的位置
        if (!isLimit && isNum) {
            memo[i][pre] = res;
        }
        return res;

    }

    private boolean valid(String s) {
        for (int i = 1; i < s.length(); i++)
            if (Math.abs((int) s.charAt(i) - (int) s.charAt(i - 1)) != 1)
                return false;
        return true;
    }

    public static void main(String[] args) {
        wk356 wk = new wk356();
        wk.countSteppingNumbers("10", "101");
    }
}
