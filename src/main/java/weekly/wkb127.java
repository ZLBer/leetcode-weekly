package weekly;

import java.util.Arrays;
import java.util.HashMap;

public class wkb127 {


    //前缀和
    public int minimumLevels(int[] possible) {
        int sum = 0;
        for (int i = 0; i < possible.length; i++) {
            if (possible[i] == 1) {
                sum++;
            } else {
                sum--;
            }
        }
        int left = 0;
        for (int i = 0; i < possible.length - 1; i++) {
            if (possible[i] == 1) {
                left++;
            } else {
                left--;
            }
            int right = sum - left;
            if (left > right) {
                return i + 1;
            }
        }
        return -1;
    }


    //滑动窗口
    static public int minimumSubarrayLength(int[] nums, int k) {
        int temp = 0;
        int ans = Integer.MAX_VALUE;

        int[] count = new int[32];
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            for (int j = 0; j <= 30; j++) {
                if ((num & (1 << j)) > 0) {
                    count[j]++;
                }
            }
            temp |= nums[i];
            while (temp >= k) {
                ans = Math.min(ans, i - left + 1);
                if (left >= i) break;
                num = nums[left];
                int delete = 0;
                for (int j = 0; j <= 30; j++) {
                    if ((num & (1 << j)) > 0) {
                        count[j]--;
                        if (count[j] == 0) {
                            delete += (1 << j);
                        }
                    }
                }

                if (temp - delete >= k) {
                    temp -= delete;
                    left++;
                } else {
                    for (int j = 0; j <= 30; j++) {
                        if ((num & (1 << j)) > 0) {
                            count[j]++;
                        }
                    }
                    break;
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    int mod = (int) 1e9 + 7;

    int inf = -mod;

    //记忆化搜索
    public int sumOfPowers(int[] nums, int k) {

        Arrays.sort(nums);
        memo = new HashMap[nums.length][k];
        return dfs(nums, k, inf, 0, 0, Integer.MAX_VALUE);
    }

    HashMap<String, Integer>[][] memo;

    int dfs(int[] nums, int k, int pre, int i, int j, int diff) {
        if (j >= k) return diff;
        if (i >= nums.length) {
            return 0;
        }
        String key = pre + "_" + diff;
        if (memo[i][j] != null && memo[i][j].containsKey(key)) return memo[i][j].get(key);
        int res1 = dfs(nums, k, pre, i + 1, j, diff);
        int res2 = dfs(nums, k, nums[i], i + 1, j + 1, Math.min(nums[i] - pre, diff));
        if (memo[i][j] == null) memo[i][j] = new HashMap<>();
        int res = (res1 + res2) % mod;
        memo[i][j].put(key, res);
        return res;
    }


    public static void main(String[] args) {
        wkb127 w = new wkb127();
        w.sumOfPowers(new int[]{1, 2, 3, 4}, 3);
    }
}
