package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class wk386 {
    public boolean isPossibleToSplit(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
            Integer c = counter.get(num);
            if (c > 2) return false;
        }
        return true;
    }


    // 贪心
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        long ans = 0;
        for (int i = 0; i < bottomLeft.length; i++) {
            for (int j = i + 1; j < bottomLeft.length; j++) {
                ans = Math.max(ans, help(bottomLeft[i], topRight[i], bottomLeft[j], topRight[j]));
            }
        }
        return ans * ans;
    }

    long help(int[] bl1, int[] tr1, int[] bl2, int[] tr2) {
        long ans = 0;

        long l = Math.max(bl1[0], bl2[0]);
        long r = Math.min(tr1[0], tr2[0]);
        long t = Math.min(tr1[1], tr2[1]);
        long b = Math.max(bl1[1], bl2[1]);
        if (l >= r || b >= t) return 0;
        return Math.min(r - l, t - b);
    }


   //二分+贪心
   /* public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {

        int[] last = new int[changeIndices.length];
        Map<Integer, Integer> lastMap = new HashMap<>();
        for (int i = 0; i < changeIndices.length; i++) {
            last[i] = changeIndices[i];
            if (lastMap.containsKey(last[i])) {
                last[lastMap.get(last[i])] = -1;
            }
            lastMap.put(changeIndices[i], i);

            if (lastMap.size() != nums.length) {
                continue;
            }

            int count = 0;
            boolean success=true;
            for (int j = 0; j <= i; j++) {
                if (last[j] == -1) {
                    count++;
                } else {
                    count -= nums[last[j]-1];
                }
                if (count < 0){
                    success=false;
                    break;
                }
            }
            if(success){
                return i+1;
            }

        }
        return -1;
    }*/



    //二分+贪心
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        int n = nums.length;
        int m = changeIndices.length;
        if (n > m) {
            return -1;
        }

        long slow = n; // 慢速复习+考试所需天数
        for (int v : nums) {
            slow += v;
        }

        int[] firstT = new int[n];
        Arrays.fill(firstT, -1);
        for (int t = m - 1; t >= 0; t--) {
            firstT[changeIndices[t] - 1] = t;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> a - b);
        int left = n - 1, right = m + 1;
        while (left + 1 < right) {
            pq.clear();
            int mid = (left + right) / 2;
            if (check(nums, changeIndices, firstT, pq, slow, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right > m ? -1 : right;
    }

    private boolean check(int[] nums, int[] changeIndices, int[] firstT, PriorityQueue<Integer> pq, long slow, int mx) {
        int cnt = 0;
        for (int t = mx - 1; t >= 0; t--) {
            int i = changeIndices[t] - 1;
            int v = nums[i];
            //需要的时间<=1 或者 不是首次出现 可以放过保存count
            if (v <= 1 || t != firstT[i]) {
                cnt++; // 留给左边，用来快速复习/考试
                continue;
            }

            //如果后面不能标记，那么就要从最小堆里反悔一个
            if (cnt == 0) {
                // 不需反悔
                if (pq.isEmpty() || v <= pq.peek()) {
                    cnt++; // 留给左边，用来快速复习/考试
                    continue;
                }
                //开始反悔 上下文回退
                slow += pq.poll() + 1; //慢速复习回退
                cnt += 2; // 反悔：一天快速复习，一天考试
            }
            //执行快速复习
            slow -= v + 1; //慢速复习相应的天数减去
            cnt--; // 快速复习，然后消耗一天来考试
            pq.offer(v); //入栈，可以参与回退
        }
        return cnt >= slow; // 剩余天数不能慢速复习+考试
    }

}
