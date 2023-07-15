package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class wk352 {

    //o(n)也可以，因为具有单一性
    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 != 0 || nums[i] > threshold) continue;
            int max = 1;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] % 2 == nums[j - 1] % 2) {
                    break;
                }
                if (nums[j] > threshold) {
                    break;
                }
                max++;
            }
            ans = Math.max(ans, max);
        }
        return ans;
    }

    public static List<Integer> getPrimes(int n) {
        boolean[] isComposite = new boolean[n + 1];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * 2; j >= 0 && j <= n; j += i) { // 标记该数的倍数为合数
                    isComposite[j] = true;
                }
            }
        }
        return primes;
    }

    static Set<Integer> set = new HashSet<>();

    static {

        for (Integer prime : getPrimes(1000001)) {
            set.add(prime);
        }
    }

    //先static求出所有质数
    public List<List<Integer>> findPrimePairs(int n) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 2; i <= n / 2; i++) {
            if (set.contains(i) && set.contains(n - i)) {
                res.add(Arrays.asList(i, n - i));
            }
        }
        return res;
    }


    //滑动窗口,treemap存值方便找最大最小值
    static public long continuousSubarrays(int[] nums) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        int left = 0;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            treeMap.put(nums[i], treeMap.getOrDefault(nums[i], 0) + 1);
            while (Math.abs(treeMap.firstKey() - treeMap.lastKey()) > 2) {
                Integer count = treeMap.get(nums[left]);
                if (count == 1) {
                    treeMap.remove(nums[left]);
                } else {
                    treeMap.put(nums[left], count - 1);
                }
                left++;
            }
            long c = (i - left + 1);
            ans += c;
        }
        return ans;
    }

    public static void main(String[] args) {
        continuousSubarrays(new int[]{5, 4, 2, 4});
    }



    //  枚举
    public int sumImbalanceNumbers(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            TreeSet<Integer> treeSet = new TreeSet<>();
            treeSet.add(nums[i]);
            for (int j = i + 1; j < nums.length; j++) {
                if (treeSet.contains(nums[j])) {
                    ans += count;
                    continue;
                }
                Integer left = treeSet.lower(nums[j]);
                Integer right = treeSet.higher(nums[j]);
                if (left != null && right != null) {
                    if (nums[j] - left > 1 && right - nums[j] > 1) {
                        count++;
                    } else if (nums[j] - left <= 1 && right - nums[j] <= 1) {
                        count--;
                    }
                } else if (left == null) {
                    if (right - nums[j] > 1) {
                        count++;
                    }
                } else if (right == null) {
                    if (nums[j] - left > 1) {
                        count++;
                    }
                }
                treeSet.add(nums[j]);
                ans += count;

            }
        }
        return ans;
    }
}
