package weekly;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class wkb100 {

    //思维题？
    public int distMoney(int money, int children) {
        if (money < children) {
            return -1;
        }
        int ans = 0;
        for (int i = 0; i < children; i++) {
            if (money >= 8) {
                money -= 8;
                ans++;
            } else {
                if (money == 4 && children - i == 1) {
                    ans--;
                } else if (money >= (children - i)) {

                } else {
                    int left = children - i - money;
                    if (left != 0) {
                        if (left % 7 == 0) {
                            ans -= (left / 7);
                        } else {
                            ans -= (left / 7 + 1);
                        }
                    }
                }
                money = 0;
                break;
            }
        }
        if (money > 0) ans--;
        return ans;
    }


    //贪心+排序
    public int maximizeGreatness(int[] nums) {
        Arrays.sort(nums);
        int right = nums.length - 1;
        int ans = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[right] > nums[i]) {
                right--;
                ans++;
            }
        }
        return ans;
    }


    //优先队列
    public long findScore(int[] nums) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        for (int i = 0; i < nums.length; i++) {
            priorityQueue.add(new int[]{nums[i], i});
        }

        long ans = 0;
        Set<Integer> set = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            if (set.contains(poll[1])) {
                continue;
            }
            set.add(poll[1] - 1);
            set.add(poll[1] + 1);
            ans += poll[0];
        }

        return ans;
    }


    // 优先队列
    /*public long repairCars(int[] ranks, int cars) {
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        for (int rank : ranks) {
            priorityQueue.add(new long[]{rank, 1, rank});
        }
        long ans = 0;
        for (int i = 0; i < cars - 1; i++) {
            long[] poll = priorityQueue.poll();
            poll[1]++;
            poll[0] = poll[2] * poll[1] * poll[1];
            priorityQueue.add(poll);
        }
        return priorityQueue.poll()[0];
    }*/

    // 二分
    public long repairCars(int[] ranks, int cars) {

        long max = ranks[0];
        for (int i : ranks) {
            max = Math.min(max, i);
        }
        long left = 0;
        long right = (long)cars * cars * max;
        System.out.println(left + " " + right);
        while (left < right) {
            long mid = (left + right) / 2;
            System.out.println(mid);
            int s = 0;
            for (int rank : ranks) {
                s += Math.sqrt(mid / rank);
            }
            if (s >= cars) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
