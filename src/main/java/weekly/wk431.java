package weekly;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class wk431 {

    public int maxLength(int[] nums) {

        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (help(i, j, nums)) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

    boolean help(int from, int to, int[] nums) {
        long sub = nums[from];
        long l = nums[from];
        long g = nums[from];
        for (int i = from + 1; i <= to; i++) {
            sub *= nums[i];
            l = lcm(l, nums[i]);
            g = gcd(g, nums[i]);
        }

        return sub == l * g;
    }


    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    public long calculateScore(String s) {
        Map<Character, PriorityQueue<Integer>> map = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            map.put((char) ('a' + i), new PriorityQueue<>(Comparator.reverseOrder()));
        }
        long ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            char j = (char) ('z' - (c - 'a'));
            Integer poll = map.get(j).poll();
            if (poll != null) {
                ans += i - poll;
            } else {
                map.get(c).add(i);
            }
        }
        return ans;
    }


    /*static public long maximumCoins(int[][] coins, int k) {
        Arrays.sort(coins, (a, b) -> a[0] - b[0]);
        int leftIndex = 0;
        long sum = 0;
        long max = 0;
        for (int i = 0; i < coins.length; i++) {
            sum += coins[i][2] * ((long) coins[i][1] - coins[i][0] + 1);
            while (leftIndex <= i && coins[i][1] - coins[leftIndex][0] + 1 > k) {
                sum -= ((long) coins[leftIndex][2] * (coins[leftIndex][1] - coins[leftIndex][0] + 1));
                leftIndex++;
            }
            int cost = leftIndex >= i ? 0 : (coins[i][1] - coins[leftIndex][0] + 1);
            //考虑半个区间
            max = Math.max(max, sum);
            //前半个
            if (leftIndex - 1 >= 0) {
                long need = Math.max(0, k - (cost + Math.max(0, leftIndex >= i ? 0 : (coins[leftIndex][0] - coins[leftIndex - 1][1] - 1))));
                need = Math.min(coins[leftIndex - 1][1] - coins[leftIndex - 1][0] + 1, need);

                max = Math.max(max, sum + need * coins[leftIndex - 1][2]);
            }
            //后半个
            if (i < coins.length - 1) {
                long need = Math.max(0, k - (cost + Math.max(0, coins[i + 1][0] - coins[i][1] - 1)));
                need = Math.min(coins[i + 1][1] - coins[i + 1][0] + 1, need);
                max = Math.max(max, sum + need * coins[i + 1][2]);
            }
//            if (leftIndex - 1 >= 0 && i < coins.length - 1) {
//                int need =Math.max(0, k - (cost + (coins[i + 1][0] - coins[i][1]) + (coins[i + 1][0] - coins[i][1])));
//
//            }
        }
        return max;
    }*/

    public long maximumCoins(int[][] coins, int k) {
        Arrays.sort(coins, (a, b) -> a[0] - b[0]);
        long ans = maximumWhiteTiles(coins, k);

        for (int i = 0, j = coins.length - 1; i < j; i++, j--) {
            int[] tmp = coins[i];
            coins[i] = coins[j];
            coins[j] = tmp;
        }
        for (int[] t : coins) {
            int temp = t[0];
            t[0] = -t[1];
            t[1] = -temp;
        }
        return Math.max(ans, maximumWhiteTiles(coins, k));
    }

    // 2271. 毯子覆盖的最多白色砖块数
    private long maximumWhiteTiles(int[][] tiles, int carpetLen) {
        long ans = 0;
        long cover = 0;
        int left = 0;
        for (int[] tile : tiles) {
            int tl = tile[0], tr = tile[1], c = tile[2];
            cover += (long) (tr - tl + 1) * c;
            while (tiles[left][1] + carpetLen - 1 < tr) {
                cover -= (long) (tiles[left][1] - tiles[left][0] + 1) * tiles[left][2];
                left++;
            }
            long uncover = Math.max((long) (tr - carpetLen + 1 - tiles[left][0]) * tiles[left][2], 0);
            ans = Math.max(ans, cover - uncover);
        }
        return ans;
    }


    public static void main(String[] args) {
       /* maximumCoins(new int[][]{
                {1, 10, 3}
        }, 2);*/
    }
}
