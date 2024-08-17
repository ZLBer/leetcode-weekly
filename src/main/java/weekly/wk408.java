package weekly;

import java.util.ArrayList;
import java.util.List;

public class wk408 {

    public boolean canAliceWin(int[] nums) {
        int sum = 0;
        int a = 0;
        for (int num : nums) {
            if (num < 10) {
                a += num;
            }
            sum += num;
        }
        if (sum - a == a) {
            return false;
        }
        return true;
    }


    static List<Long> list = new ArrayList<>();

    static {
        for (int i = 2; i <= (int) Math.sqrt(1e9); i++) {
            if (isPrime(i)) {
                list.add((long) i * i);
            }
        }
    }

    public int nonSpecialCount(int l, int r) {
        int ans = 0;
        for (Long num : list) {
            if (num >= l && num <= r) {
                ans++;
            }
        }
        return r - l + 1 - ans;
    }

    static boolean isPrime(int a) {
        for (int i = 2; i <= Math.sqrt(a); i++) {
            if (a % i == 0) return false;
        }
        return true;
    }



}
