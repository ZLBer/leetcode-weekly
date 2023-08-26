package weekly;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class wkb111 {
    //遍历
    public int countPairs(List<Integer> nums, int target) {
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = i + 1; j < nums.size(); j++) {
                if (nums.get(i) + nums.get(j) < target) {
                    ans++;
                }
            }
        }
        return ans;
    }

    //贪心+双指针
    public boolean canMakeSubsequence(String str1, String str2) {
        int index = 0;
        for (int i = 0; i < str1.length() && index < str2.length(); i++) {
            char c = str1.charAt(i);
            if (c == str2.charAt(index)) {
                index++;
            } else if (c == 'z' && str2.charAt(index) == 'a') {
                index++;
            } else if ((char) (c + 1) == str2.charAt(index)) {
                index++;
            }
        }
        return index >= str2.length();
    }

    //前缀和或dp
    public int minimumOperations(List<Integer> nums) {
        int[][] count = new int[nums.size() + 1][3];
        for (int i = 0; i < nums.size(); i++) {
            count[i + 1] = new int[]{
                    count[i][0], count[i][1], count[i][2]
            };
            if (nums.get(i) == 1) {
                count[i + 1][0]++;
            } else if (nums.get(i) == 2) {
                count[i + 1][1]++;
            } else if (nums.get(i) == 3) {
                count[i + 1][2]++;
            }
        }

        int ans = Math.min(nums.size() - count[nums.size()][0], Math.min(nums.size() - count[nums.size()][1], nums.size() - count[nums.size()][2]));
        for (int i = 0; i <= nums.size(); i++) {
            for (int j = i; j <= nums.size(); j++) {
                int one = i - count[i][0];
                int two = (j - i) - (count[j][1] - count[i][1]);
                int three = (nums.size() - j) - (count[nums.size()][2] - count[j][2]);
                ans = Math.min(ans, one + two + three);
            }
        }

        return ans;
    }


    //数位dp
    public int numberOfBeautifulIntegers(int low, int high, int k) {
        this.k = k;
        return countSpecialNumbers(high) - countSpecialNumbers(low-1) ;
    }

    //数位dp通用模板

    char s[];
    int dp[][][];
    int k;

    public int countSpecialNumbers(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        dp = new int[m][100][21];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < dp[0].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        return f(0, 0, 0, true, false);
    }

    //i表示当前位置，mask 奇数和偶数的数目，islimit表示是否限制最大到9，isNum表示之前是否有数字
    int f(int i, int mask, int mod, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            boolean b = isNum && mod == 0 && (mask / 10) == (mask % 10);
            if (b) {
                return 1;
            } else {
                return 0;
            }
        }
        if (!isLimit && isNum && dp[i][mask][mod] >= 0) return dp[i][mask][mod];
        int res = 0;
        if (!isNum) res = f(i + 1, mask,mod, false, false); // 可以跳过当前数位
        for (int d = isNum ? 0 : 1, up = isLimit ? s[i] - '0' : 9; d <= up; ++d) { // 枚举要填入的数字 d
            int add = 0;
            if (d % 2 == 0) {
                add = 10;
            } else {
                add = 1;
            }

            res += f(i + 1, mask + add, (mod * 10 + d)%k,isLimit && d == up, true);
        }
        if (!isLimit && isNum) dp[i][mask][mod] = res;
        return res;
    }


    public static void main(String[] args) {
        wkb111 w = new wkb111();
        System.out.println(w.numberOfBeautifulIntegers(0, 72, 16));
    }

}
