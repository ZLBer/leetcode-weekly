package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class wk337 {


    //模拟
    public int[] evenOddBit(int n) {
        int even = 0, odd = 0;
        String s = Integer.toBinaryString(n);

        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '1') {
                if ((s.length() - i - 1) % 2 == 0) {
                    even++;
                } else {
                    odd++;
                }
            }
        }
        return new int[]{even, odd};
    }

    //模拟
    //可以打散成一维
    public boolean checkValidGrid(int[][] grid) {
        int[][] moves = new int[][]{
                {1, 2}, {2, 1}, {-2, 1}, {1, -2},
                {-1, 2}, {2, -1}, {-2, -1}, {-1, -2},
        };

        int x = 0, y = 0;
        int cur = 0;
        for (int i = 0; i < grid.length * grid[0].length - 1; i++) {
            boolean flag = false;
            for (int[] move : moves) {
                int nx = move[0] + x;
                int ny = move[1] + y;
                if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length) {
                    if (grid[nx][ny] == cur + 1) {
                        x = nx;
                        y = ny;
                        cur++;
                        flag = true;
                        break;
                    }
                }

            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    //回溯
    public int beautifulSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        return help(new HashSet<>(),0,nums,k);
    }

    int help(Set<Integer> set, int index, int[] nums, int k) {

        if (index >= nums.length) {
            return 1;
        }
        int count = help(set,index+1,nums,k);
        if (!set.contains(nums[index] - k)) {
            set.add(nums[index]);
            count += help(set, index + 1, nums, k);
            set.remove(nums[index]);
        }
        return count;
    }


    //同余分组
    public int findSmallestInteger(int[] nums, int value) {
        int[] counter = new int[value];
        for (int num : nums) {
            int index = num % value;
            if (index < 0) index += value;
            counter[index]++;
        }
        int step = 0;
        while (true) {
            for (int i = 0; i < counter.length; i++) {
                if (counter[i] == 0) {
                    return value * step + i;
                }
                counter[i]--;
            }
            step++;
        }
    }

    public static void main(String[] args) {

    }
}
