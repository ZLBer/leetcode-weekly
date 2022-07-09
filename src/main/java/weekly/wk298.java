package weekly;

public class wk298 {


    //ranking: 1590 / 6228  行,在菜的路上一去不复返


    //简单题，统计频率即可
    public String greatestLetter(String s) {
        int[] count = new int[26];
        int[] count1 = new int[26];
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                count[c - 'A']++;
            } else {
                count1[c - 'a']++;
            }
        }
        String res = "";
        for (int i = count.length - 1; i >= 0; i--) {
            if (count[i] > 0 && count1[i] > 0) {
                res = ((char) (i + 'A')) + "";
                break;
            }
        }
        return res;
    }


    //中等题，想太多了，只考虑个位数出现几次就行啊，还是种贪心策略
    public int minimumNumbers(int num, int k) {
        if (num == 0) return 0;
        if (k == 0) return num % 10 == 0 ? 1 : -1;
        int count = 0;
        while (num >= 0) {
            if ((num - k) % 10 != 0) {
                num -= k;
                count++;
            } else {
                if (num != 0) count++;
                break;
            }
        }
        return num > 0 ? count : -1;
    }

   /* public int minimumNumbers(int num, int k) {

        if (num == 0) return 0;
        List<Integer> set = new ArrayList<>();
        int i = k;
        while (i <= num) {
            if (i != 0) set.add(i);
            i += 10;
        }
        for (Integer integer : set) {
            System.out.print(integer+" ");
        }
        helper(set, 0, 0, 0, num);
        return max == Integer.MAX_VALUE ? -1 : max;
    }

    int max = Integer.MAX_VALUE;

    void helper(List<Integer> nums, int index, int len, int sum, int need) {


        if (sum == need) {
            max = Math.min(max, len);
            return;
        }
        if (sum > need || index >= nums.size()) {
            return;
        }

        for (int i = 0; ; i++) {
            if (sum + i * nums.get(index) > need) break;
            helper(nums, index + 1, len + i, sum + i * nums.get(index), need);
        }

    }*/


    //中等题，这个题也是做的不好
    //我的想法是：依次检查k长度的字符串，k前面的1删掉，k里面的要删掉之后比k小，k后面的全部删掉
    public int longestSubsequence(String s, int k) {
        String binaryString = Integer.toBinaryString(k);
        if (binaryString.length() > s.length()) return s.length();

        int n = s.length();

        int pre = 0;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i + binaryString.length() <= n; i++) {

            int count = 0;
            boolean flag = false;
            for (int j = 0; j < binaryString.length(); j++) {
                if (binaryString.charAt(j) > s.charAt(i + j)) {
                    break;
                } else if (binaryString.charAt(j) < s.charAt(i + j)) {
                    count++;
                    if (flag) break; //必定有前缀1相等，此时删除需要后移，必定比k小了，所以直接返回
                } else {
                    flag = true;
                    continue;
                }
            }

            ans = Math.min(ans, pre + count + (s.length() - i - binaryString.length()));
            //             System.out.println(pre+" "+count);

            if (s.charAt(i) == '1') pre++;
        }
        return s.length() - ans;
    }


    //超级贪心，只看m长度的后缀和m-1长度的后缀
 /*   public int longestSubsequence(String s, int k) {
        int n = s.length(), m = 32 - Integer.numberOfLeadingZeros(k);
        if (n < m) return n;
        int ans = Integer.parseInt(s.substring(n - m), 2) <= k ? m : m - 1;
        return ans + (int) s.substring(0, n - m).chars().filter(c -> c == '0').count();
    }*/



    //困难题目，要注意是一刀切
    public long sellingWood(int m, int n, int[][] prices) {
        long[][] dp = new long[m + 1][n + 1];

        for (int[] price : prices) {
            dp[price[0]][price[1]] = price[2];
        }
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 1; k <= i; k++) {
                    dp[i][j]=Math.max(dp[i][j],dp[k][j]+dp[i-k][j]);
                }

                for (int k = 1; k <= j; k++) {
                    dp[i][j]=Math.max(dp[i][j],dp[i][k]+dp[i][j-k]);
                }
            }
        }
        return dp[m][n];
    }

}
