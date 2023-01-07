package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wk325 {

    //ranking: 917 / 3873

    //直接做就好了
    public int countDigits(int num) {
        int c = num;
        int ans = 0;
        while (c > 0) {

            if (num % (c % 10) == 0) {
                ans++;
            }
            c /= 10;
        }
        return ans;
    }

     //计算质因数
    public int distinctPrimeFactors(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            for (int i = 2; i*i<=num; i++) {
                while (num % i == 0) {
                    num /= i;
                    set.add(i);
                }

            }
            if (num>1){
                set.add(num);
            }
        }
        return set.size();
    }


    //用dp做，也可以贪心
    public int minimumPartition(String s, int k) {

        int[] dp = new int[s.length() + 1];

        Arrays.fill(dp, (int) 1e9 + 7);
        dp[0] = 0;

        for (int left = 0; left < s.length(); left++) {
            long sum = 0;
            for (int right = left; right < s.length(); right++) {
                sum = sum * 10 + s.charAt(right) - '0';
                if (sum <= k) {
                    dp[right + 1] = Math.min(dp[right + 1], dp[left] + 1);
                } else {
                    break;
                }
            }
        }
        return dp[s.length()] == (int) 1e9 + 7 ? -1 : dp[0];
    }


    //预处理所有质数(埃氏筛)+计算质数间隔
    public int[] closestPrimes(int left, int right) {

        boolean[] prime = isPrime(right);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < prime.length; i++) {
            if (prime[i] && i >= left && i <= right) {
                list.add(i);
            }
        }
        if (list.size() < 2) return new int[]{-1, -1};
        int min = Integer.MAX_VALUE;
        int[] ans = new int[2];

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) - list.get(i - 1) < min) {
                min = list.get(i) - list.get(i - 1);
                ans = new int[]{list.get(i-1), list.get(i )};
            }
        }
        return ans;
    }

    public static boolean[] isPrime(int n) {
        boolean[] arr = new boolean[n + 1];
        // 1:质数   0:非质数
        Arrays.fill(arr, true);
        arr[1]=false;
        for (int i = 2; i <= n; i++) {
            if (arr[i]) {
                // 将i的倍数去除掉
                for (int j = i + i; j <= n; j += i) {
                    arr[j] = false;
                }
            }
        }
        return arr;
    }

}
