package weekly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class wkb87 {
    //ranking: 712 / 4005

    //这是做的啥啊?
  /*  static public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int res = 0;
        try {
            res = getRepeatDays(formatter.parse("2022-" + arriveAlice), formatter.parse("2022-" + leaveAlice), formatter.parse("2022-" + arriveBob), formatter.parse("2022-" + leaveBob));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getDayDifference(Date d1, Date d2) {
        StringBuffer ds = new StringBuffer();
        long days = ((d1.getTime() - d2.getTime()) / 1000) / (3600 * 24);
        if (days >= 0) {
            return (days + 1) + "";
        }
        return ds.toString();
    }

    public static int getRepeatDays(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        long star1 = startDate1.getTime();
        long end1 = endDate1.getTime();
        long star2 = startDate2.getTime();
        long end2 = endDate2.getTime();
        String res;

        if (star1 <= star2 && end1 >= end2) {
            res = getDayDifference(endDate2, startDate2);
        } else if (star1 >= star2 && end1 <= end2) {

            res = getDayDifference(endDate1, startDate1);
        } else if (star1 >= star2 && star1 <= end2 && end2 <= end1) {

            res = getDayDifference(endDate2, startDate1);
        } else if (star1 <= star2 && end1 <= end2 && end1 >= star2) {

            res = getDayDifference(endDate1, startDate2);
        } else if (end1 <= star2 && star1 >= end2) {
            res = "0";
        } else {
            res = "0";
        }
        int numberDays = Integer.parseInt(res);

        return numberDays;
    }*/

    //直接调api做吧
    private static final String YEAR = "2022-";

    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        LocalDate alice = LocalDate.parse(YEAR + arriveAlice), bob = LocalDate.parse(YEAR + arriveBob), arrive = alice.isAfter(bob) ? alice : bob;
        alice = LocalDate.parse(YEAR + leaveAlice);
        bob = LocalDate.parse(YEAR + leaveBob);
        return (int) Math.max(0L, (alice.isAfter(bob) ? bob : alice).toEpochDay() - arrive.toEpochDay() + 1L);
    }


    //排序+双指针
    public int matchPlayersAndTrainers(int[] players, int[] trainers) {
        Arrays.sort(players);
        Arrays.sort(trainers);
        int j = 0;
        int ans = 0;
        for (int i = 0; i < players.length; i++) {
            while (j < trainers.length && players[i] > trainers[j]) {
                j++;
            }
            if (j < trainers.length) {
                ans++;
            }
            j++;
        }
        return ans;
    }


    //位运算
   /* static public int[] smallestSubarrays(int[] nums) {
        List<Integer>[] dp = new List[32];
        //考虑每个数字对每一位的贡献，
        // 比如 3的二进制是11,对第0和第1位有贡献  然后在每个位置讲坐标依次入队
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new ArrayList<>();
            int a = 1 << i;
            for (int j = 0; j < nums.length; j++) {
                if ((a & nums[j]) != 0) {
                    dp[i].add(j);
                }
            }
        }
        int[] cur = new int[32];
        for (int i = 0; i < nums.length; i++) {
            int max = i;
            for (int j = 0; j < cur.length; j++) {
                //判断队首的位置是不是超出i
                if (cur[j] < dp[j].size() && dp[j].get(cur[j]) < i) {
                    cur[j]++;
                }
                // 求最大位置
                if (cur[j] < dp[j].size()) {
                    max = Math.max(max, dp[j].get(cur[j]));
                }
            }
            //只需要到max即可,max后面的对整体没有贡献
            nums[i] = max - i + 1;
        }
        return nums;
    }*/

    //从后往前遍历，减少遍历次数
    static public int[] smallestSubarrays(int[] nums) {
        int[] ans = new int[nums.length];
        int[] dp = new int[32];
        for (int i = nums.length - 1; i >= 0; i--) {
            ans[i]=1;
            int num = nums[i];
            for (int j = 0; j < 32; j++) {
                if ((num & (1 << j)) != 0) {
                    dp[j] = i;
                }
            }
            int max=0;
            for (int j = 0; j < dp.length; j++) {
                max=Math.max(dp[j],max);
            }

            if(max==0){
              continue;
            }
            ans[i]=max-i+1;
        }
        return ans;
    }


    //贪心
    public long minimumMoney(int[][] transactions) {
        long money = 0;
        //记录所有 cost<cashback的赔钱情况,totalLose
        for (int i = 0; i < transactions.length; i++) {
            if (transactions[i][1] <= transactions[i][0]) {
                money += (transactions[i][0] - transactions[i][1]);
            }
        }
        long max = 0;

        //考虑所有的情况都可以完成交易
        //对于cost<=cashback的交易，只要初始资金为 totalLose+max(cost)，一定能保证这种交易完成。因为money一直在变多
        //对于cost>cashback的交易, 只要初始资金为 totalLose+max(cashback)，一定能完成交易。因为保证了一定大于任何一个cost
        //所以取两者最大值即可
        for (int i = 0; i < transactions.length; i++) {
            if (transactions[i][1] <= transactions[i][0]) money -= (transactions[i][0] - transactions[i][1]);
            max = Math.max(max, money + transactions[i][0]);
            if (transactions[i][1] <= transactions[i][0]) money += (transactions[i][0] - transactions[i][1]);

        }
        return max;
    }

    public static void main(String[] args) {

    }

}
