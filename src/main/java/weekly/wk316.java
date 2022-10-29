package weekly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class wk316 {

    //ranking: 942 / 6387

    //又是这种时间题
    public boolean haveConflict(String[] event1, String[] event2) {

        return event1[0].compareTo(event2[1]) <= 0 && event1[1].compareTo(event2[0]) >= 0;

    }

//    boolean check(LocalTime a, LocalTime b, LocalTime c, LocalTime d) {
//        if (b.compareTo(c) >= 0 && b.compareTo(d) <= 0) return true;
//        if (d.compareTo(a) >= 0 && d.compareTo(b) <= 0) return true;
//        return false;
//    }


    //暴力写的
    public int subarrayGCD(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int g = 0;
            for (int j = i; j < nums.length; j++) {
                g = gcd(g, nums[j]);
                if (g == k) ans += 1;
                if (g % k > 0) break;
            }
        }
        return ans;
    }

    int gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return (int) a;
    }


    //前缀和容易考虑一点, 但也可以直接考虑sum
  /*  public long minCost(int[] nums, int[] cost) {
        long[][] pack = new long[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            pack[i] = new long[]{nums[i], cost[i]};
        }
        Arrays.sort(pack, (a, b) -> Long.compare(a[0], b[0]));

        long res = 0;
        for (int i = 1; i < pack.length; i++) {
            res += (pack[i][0] - pack[0][0]) * pack[i][1];
        }
        for (int i = 1; i < pack.length; i++) {
            pack[i][1] += pack[i - 1][1];
        }
        long ans = Long.MAX_VALUE;
        for (int i = 1; i < pack.length; i++) {
            res += (pack[i][0] - pack[i - 1][0]) * (pack[i - 1][1] - (pack[nums.length - 1][1] - pack[i - 1][1]));
            ans = Math.min(ans, res);
        }
        return ans;
    }*/
    //不用前缀和
    public long minCost(int[] nums, int[] cost) {
        long[][] pack = new long[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            pack[i] = new long[]{nums[i], cost[i]};
        }
        Arrays.sort(pack, (a, b) -> Long.compare(a[0], b[0]));

        long res = 0;
        long sumCost=pack[0][1];
        for (int i = 1; i < pack.length; i++) {
            res += (pack[i][0] - pack[0][0]) * pack[i][1];
            sumCost+=pack[i][1];
        }
        long ans = res;
        for (int i = 1; i < pack.length; i++) {
             sumCost-=2*pack[i-1][1];
            res -= (pack[i][0] - pack[i - 1][0]) * sumCost;
            ans = Math.min(ans, res);
        }
        return ans;
    }


    //分奇数偶数考虑,要分很多情况
   /* public long makeSimilar(int[] nums, int[] target) {
        List<Integer> nums1 = new ArrayList<>();
        List<Integer> nums2 = new ArrayList<>();
        List<Integer> target1 = new ArrayList<>();
        List<Integer> target2 = new ArrayList<>();
        Arrays.sort(nums);
        Arrays.sort(target);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 1) {
                nums1.add(nums[i]);
            } else {
                nums2.add(nums[i]);
            }
            if (target[i] % 2 == 1) {
                target1.add(target[i]);
            } else {
                target2.add(target[i]);
            }
        }
        long[] a = help(nums1, target1, 0, 0);
        long[] b = help(nums2, target2, a[1], a[2]);
        System.out.println(a + " " + b);
        return a[0] + b[0];
    }

    long[] help(List<Integer> nums, List<Integer> target, long up, long down) {
        long count = 0;
        for (int i = 0; i < nums.size(); i++) {
            int a = nums.get(i), b = target.get(i);
            if (a > b) {
                int need = (a - b) / 2;
                if (down >= need) {
                    down -= need;
                } else {
                    count += (need - down);
                    up += (need - down);
                    down = 0;
                }

            } else if (a < b) {
                int need = (b - a) / 2;
                if (up >= need) {
                    up -= need;
                } else {
                    count += (need - up);
                    down += (need - up);
                    up = 0;
                }
            }
        }
        return new long[]{count, up, down};
    }*/


    //大佬的做法,把奇数变成负数，区分奇偶，然后计算绝对值差，最后/4即可，因为题目说一定有答案
    public long makeSimilar(int[] nums, int[] target) {
        f(nums);
        f(target);
        long ans = 0L;
        for (int i = 0; i < nums.length; ++i)
            ans += Math.abs(nums[i] - target[i]);
        return ans / 4;
    }

    private void f(int[] a) {
        // 由于元素都是正数，把奇数变成相反数，这样排序后奇偶就自动分开了
        for (int i = 0; i < a.length; ++i)
            if (a[i] % 2 != 0) a[i] = -a[i];
        Arrays.sort(a);
    }

}
