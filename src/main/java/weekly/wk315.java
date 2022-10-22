package weekly;

import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wk315 {
    //ranking: 1111 / 6490 拉胯啊

    //hash
    public int findMaxK(int[] nums) {
        int ans = -1;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(-num)) {
                ans = Math.max(Math.abs(num), ans);
            }
            set.add(num);
        }
        return ans;
    }

    // hash
    public int countDistinctIntegers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        for (int num : nums) {
            int nnum = 0;
            while (num > 0) {
                nnum = nnum * 10 + num % 10;
                num /= 10;
            }
            set.add(nnum);
        }
        return set.size();
    }

    //暴力,其他解法不会
    public boolean sumOfNumberAndReverse(int num) {
        for (int i = 0; i <= num; i++) {
            int n = i;
            int nnum = 0;
            while (n > 0) {
                nnum = nnum * 10 + n % 10;
                n /= 10;
            }
            if (i + nnum == num) return true;
        }
        return false;
    }

   /* public long check(int[] nums, int leftSub, int rightSub, int minK, int maxK) {
        List<Integer> minList = new ArrayList<>();
        List<Integer> maxList = new ArrayList<>();
        for (int i = leftSub; i <= rightSub; i++) {
            if (nums[i] == minK) {
                minList.add(i);
            }
            if (nums[i] == maxK) {
                maxList.add(i);
            }
        }
        int[] Left = new int[nums.length];
        int[] Right = new int[nums.length];
        int pre = -1;
        // if(nums[0]<minK||nums[0]>maxK)pre=0;
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < minK) {
                pre = i;
            }
            if (nums[i] > maxK) {
                pre = i;
            }
            Left[i] = pre;

        }
        pre = nums.length;
        // if(nums[nums.length-1]<minK||nums[nums.length-1]>maxK)pre=nums.length-1;
        for (int i = nums.length - 1; i >= 0; i--) {

            if (nums[i] < minK) {
                pre = i;
            }
            if (nums[i] > maxK) {
                pre = i;
            }
            Right[i] = pre;

        }


        long ans = 0;
        for (int i = 0; i < minList.size(); i++) {
            for (int j = 0; j < maxList.size(); j++) {
                int left = Math.min(minList.get(i), maxList.get(j));
                int right = Math.max(minList.get(i), maxList.get(j));
                if (Right[left] < right || Left[right] > left) continue;
                int l = Left[left];
                int r = Right[right];
                ans += (long) (left - l) * (r - right);
            }
        }
        return ans;
    }

    public long countSubarrays(int[] nums, int minK, int maxK) {
        long left = 0;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < minK || nums[i] > maxK) {

            }
        }
        return ans;
    }*/

    //双指针,要学会拆分子问题
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long min = -1, max = -1, index = -1, count = 0;
        //考虑以i结尾符合条件的定界子数组的个数
        for (int i = 0; i < nums.length; i++) {
            //更新下界的位置
            min = (nums[i] == minK ? i : min);
            //更新上界的位置
            max = (nums[i] == maxK ? i : max);
            //更新不存在上下界的位置
            index = (nums[i] < minK || nums[i] > maxK ? i : index);
            //以i结尾的符合条件的子数组的个数
            count += Math.max(0, Math.min(min,max) - index);
        }
        return count;
    }
}
