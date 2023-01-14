package weekly;

import java.util.Comparator;
import java.util.PriorityQueue;

public class wk327 {
    //ranking:

    //过
    public int maximumCount(int[] nums) {
        int neg = 0, pos = 0;
        for (int num : nums) {
            if (num > 0) {
                pos++;
            } else if (num < 0) {
                neg++;
            }
        }
        return Math.max(neg, pos);
    }

    //模拟
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int num : nums) {
            priorityQueue.add(num);
        }
        long res = 0;
        while (k-- > 0) {
            Integer poll = priorityQueue.poll();
            if (poll == 0) {
                break;
            }
            res += poll;
            priorityQueue.add((poll + 2) / 3);
        }
        return res;
    }


    //统计次数 枚举讨论
    static public boolean isItPossible(String word1, String word2) {
        int[] c1 = new int[26];
        int[] c2 = new int[26];
        int diff1 = 0, diff2 = 0;
        for (char c : word1.toCharArray()) {
            c1[c - 'a']++;
            if (c1[c - 'a'] == 1) {
                diff1++;
            }
        }
        for (char c : word2.toCharArray()) {
            c2[c - 'a']++;
            if (c2[c - 'a'] == 1) {
                diff2++;
            }
        }
        //测试交换
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c2.length; j++) {
                //有一个不存在此字母
                if (c1[i] == 0 || c2[j] == 0) {
                    continue;
                }
                //相同的字母
                if (i == j) {
                    if (diff1 == diff2) {
                        return true;
                    } else {
                        continue;
                    }
                }
                //不同的字母
                int d1 = diff1, d2 = diff2;
               //只有一个此字母
                if (c1[i] == 1) {
                    d1--;
                }
                //没有此字母
                if (c1[j] == 0) {
                    d1++;
                }
                //只有一个此字母
                if (c2[j] == 1) {
                    d2--;
                }
                //没有此字母
                if (c2[i] == 0) {
                    d2++;
                }
                if (d1 == d2) {
                    return true;
                }
            }
        }
        return false;
    }



    //记住一次一次模拟  不要用while
    static public int findCrossingTime(int n, int k, int[][] time) {
        PriorityQueue<int[]> left = new PriorityQueue<>((a, b) -> a[0] + a[2] == b[0] + b[2] ? b[4] - a[4] : b[0] + b[2] - (a[0] + a[2]));
        PriorityQueue<int[]> right = new PriorityQueue<>((a, b) -> a[0] + a[2] == b[0] + b[2] ? b[4] - a[4] : b[0] + b[2] - (a[0] + a[2]));
        PriorityQueue<int[]> leftWarehouse = new PriorityQueue<>((a, b) -> a[5] - b[5]);
        PriorityQueue<int[]> rightWarehouse = new PriorityQueue<>((a, b) -> a[5] - b[5]);
        for (int i = 0; i < time.length; i++) {
            int[] t = time[i];
            left.add(new int[]{t[0], t[1], t[2], t[3], i, 0});
        }

        int cur = 0;
        while (n > 0) {

            while (!rightWarehouse.isEmpty() && rightWarehouse.peek()[5] <= cur) {
                int[] poll = rightWarehouse.poll();
                right.add(poll);
            }
            while (!leftWarehouse.isEmpty() && leftWarehouse.peek()[5] <= cur) {
                int[] poll = leftWarehouse.poll();
                left.add(poll);
            }
         // 右边的先过河
            if (!right.isEmpty()) {
                int[] poll = right.poll();
                cur += poll[2];
                poll[5] = cur + poll[3];
                leftWarehouse.add(poll);
                //右边空左边可以过
            } else if (!left.isEmpty()) {
                int[] poll = left.poll();
                cur += poll[0];
                poll[5] = cur + poll[1];
                rightWarehouse.add(poll);
                n--;
              //  右边房子有人
            } else if (leftWarehouse.isEmpty() && !rightWarehouse.isEmpty()) {
                cur = rightWarehouse.peek()[5];
             //左边房子有人
            } else if (!leftWarehouse.isEmpty() && rightWarehouse.isEmpty()) {
                cur = leftWarehouse.peek()[5];
             //都有人取最小
            } else {
                cur = Math.min(leftWarehouse.peek()[5], rightWarehouse.peek()[5]);
            }
        }

        //将右边的全部过河
        while (!rightWarehouse.isEmpty()) {
            int[] poll = rightWarehouse.poll();
            cur = Math.max(poll[5],cur)+poll[2];
        }

        return cur;

    }


    public static void main(String[] args) {
        findCrossingTime(3, 2, new int[][]{
                {1, 9, 1, 8}, {10, 10, 10, 10}
        });
    }
}
