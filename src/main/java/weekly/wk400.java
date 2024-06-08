package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wk400 {

    //遍历
    public int minimumChairs(String s) {
        int count = 0;
        int ans = 0;
        for (char c : s.toCharArray()) {
            if (c == 'E') {
                count++;
                ans = Math.max(count, ans);
            } else {
                count--;
            }
        }
        return ans;
    }

//    public int countDays(int days, int[][] meetings) {
//        int[] count = new int[days + 1];
//        for (int[] meeting : meetings) {
//            count[meeting[0] - 1]++;
//            count[meeting[1]]--;
//        }
//        int ans = 0;
//        for (int i = 0; i < count.length - 1; i++) {
//            if (i > 0) {
//                count[i] += count[i - 1];
//            }
//            if (count[i] == 0) {
//                ans++;
//            }
//
//        }
//        return ans;
//    }


    //排序
    static public int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int start = 0, end = 0;
        int ans = 0;
        for (int[] meeting : meetings) {
            if (meeting[0] <= end) {
                end = Math.max(meeting[1], end);
            } else {
                ans += meeting[0] - end - 1;
                start = meeting[0];
                end = Math.max(meeting[1], end);
            }
        }
        ans += days - end;
        return ans;
    }

    //模拟
    public String clearStars(String s) {
        ArrayList<Integer>[] count = new ArrayList[26];
        for (int i = 0; i < count.length; i++) {
            count[i] = new ArrayList<>();
        }
        Set<Integer> delete = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                for (int j = 0; j < count.length; j++) {
                    if (count[j].size() > 0) {
                        delete.add(count[j].get(count[j].size() - 1));
                        count[j].remove(count[j].size() - 1);
                        break;
                    }
                }
            } else {
                count[c - 'a'].add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '*' && !delete.contains(i)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

/*
 //超时
   static public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= k) {
                ans = Math.min(ans, k - nums[i]);
            } else {
                int temp = nums[i];
                ans = Math.min(ans, Math.abs(temp-k));
                for (int j = i + 1; j < nums.length; j++) {
                    temp&=nums[j];
                    ans = Math.min(ans, Math.abs(temp-k));
                    if(temp<=k) break;
                }
            }
        }
        return ans;
    }*/


    // and
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            ans = Math.min(ans, Math.abs(x - k));
            for (int j = i - 1; j >= 0 && (nums[j] & x) != nums[j]; j--) {
                nums[j] &= x;
                ans = Math.min(ans, Math.abs(nums[j] - k));
            }
        }
        return ans;
    }


    public static void main(String[] args) {
    }


}
