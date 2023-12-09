package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wk374 {
    //遍历
    public List<Integer> findPeaks(int[] mountain) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i < mountain.length - 1; i++) {
            if (mountain[i] > mountain[i - 1] && mountain[i] > mountain[i + 1]) {
                res.add(i);
            }
        }
        return res;
    }

    //贪心
    public int minimumAddedCoins(int[] coins, int target) {
        Arrays.sort(coins);
        int preSum = 0;
        int ans = 0;
        int left = 0;
        for (int i = 1; i <= target; i++) {
            if (preSum >= i) {
                continue;
            } else if (left < coins.length && coins[left] <= i) {
                preSum += coins[left];
                left++;
            } else {
                ans++;
                preSum += i;
            }
        }
        return ans;
    }

    //滑动窗口
    public int countCompleteSubstrings(String word, int k) {
        int[][] count = new int[word.length() + 1][26];
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            for (int j = 0; j < count[i].length; j++) {
                count[i + 1][j] += count[i][j];
            }
            count[i + 1][c - 'a']++;
        }


        int left = 0;
        int ans = 0;
        for (int i = 1; i < word.length(); i++) {
            char pre = word.charAt(i - 1);
            char cur = word.charAt(i);
            if (Math.abs(cur - pre) > 2) {
                ans += help(word, left, i - 1, k, count);
                left = i;
            }
        }
        ans += help(word, left, word.length() - 1, k, count);
        return ans;
    }

    //剪枝优化
    int help(String word, int left, int right, int k, int[][] count) {
        int ans = 0;
        for (int i = left; i <= right; i++) {
            for (int j = 1; i + j * k - 1 <= right && k*j<word.length(); j++) {
                int c = 0;
                for (int m = 0; m < 26; m++) {
                    if (count[i + j * k][m]!=0) {
                        if (count[i + j * k][m] - count[i][m] == k){
                            c++;
                        }else if(count[i + j * k][m] - count[i][m] != 0){
                            c=-1;
                            break;
                        }
                    }
                }
                if (c == j) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        wk374 w = new wk374();
        w.countCompleteSubstrings("igigee", 2);
    }


    private static final int MOD = 1_000_000_007;
    private static final int MX = 100_000;

    // 组合数模板
    private static final long[] FAC = new long[MX];
    private static final long[] INV_FAC = new long[MX];

    static {
        FAC[0] = 1;
        for (int i = 1; i < MX; i++) {
            FAC[i] = FAC[i - 1] * i % MOD;
        }
        INV_FAC[MX - 1] = pow(FAC[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_FAC[i - 1] = INV_FAC[i] * i % MOD;
        }
    }

    private static long comb(int n, int k) {
        return FAC[n] * INV_FAC[k] % MOD * INV_FAC[n - k] % MOD;
    }

    public int numberOfSequence(int n, int[] a) {
        int m = a.length;
        int total = n - m;
        //求前后边缘
        long ans = comb(total, a[0]) * comb(total - a[0], n - a[m - 1] - 1) % MOD;
        total -= a[0] + n - a[m - 1] - 1;
        //未被感染的小朋友数量
        int e = 0;
        for (int i = 1; i < m; i++) {
            //间隔大小
            int k = a[i] - a[i - 1] - 1;
            if (k > 0) {
                e += k - 1;
                //组合数
                ans = ans * comb(total, k) % MOD;
                total -= k;
            }
        }
        return (int) (ans * pow(2, e) % MOD);
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }

}
