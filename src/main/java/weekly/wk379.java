package weekly;

import java.util.HashSet;
import java.util.Set;

public class wk379 {
    //遍历
    public int areaOfMaxDiagonal(int[][] dimensions) {
        int ans = 0;
        int max = 0;
        for (int[] dimension : dimensions) {
            int val = dimension[0] * dimension[0] + dimension[1] * dimension[1];

            if (val > max) {
                max = val;
                ans = dimension[0] * dimension[1];
            } else if (val == max) {
                ans = Math.max(dimension[0] * dimension[1], ans);

            }
        }
        return ans;
    }



    boolean checkIfPointsAreOnDiagonalLine(int c, int d, int e, int f) {
        int xDiff = Math.abs(c - e);
        int yDiff = Math.abs(d - f);
        return xDiff == yDiff;
    }

    // b是否在c中间
    boolean checkInner(int a, int b, int c) {
        if (a > c) {
            if (b > c && b < a) return true;
        } else {
            if (b < c && b > a) return true;
        }
        return false;
    }


    int checkLine(int a, int b, int c, int d, int e, int f) {
        int ans = Integer.MAX_VALUE;
        if (a == e) {
            if (c == e && checkInner(b, d, f)) {
                ans = Math.min(ans, 2);
            } else {
                ans = Math.min(ans, 1);
            }
        } else if (b == f) {
            if (d == f && checkInner(a, c, e)) {
                ans = Math.min(ans, 2);
            } else {
                ans = Math.min(ans, 1);
            }
        } else {
            ans = Math.min(ans, 2);
        }
        return ans;
    }


    //分类讨论  做麻了

    public int minMovesToCaptureTheQueen(int a, int b, int c, int d, int e, int f) {


        int ans = checkLine(a, b, c, d, e, f);

        boolean b1 = checkIfPointsAreOnDiagonalLine(a, b, c, d);
        boolean b2 = checkIfPointsAreOnDiagonalLine(c, d, e, f);

        //象和后在一条对角线上
        if (b2) {
            if (b1) {
                //在同一侧的对角线上
                if ((a - e) * (c - e) > 0 && (b - f) * (d - f) > 0) {
                    int diffA = Math.abs(a - e);
                    int diffB = Math.abs(c - e);
                    if (diffA < diffB) {

                    } else {
                        ans = 1;
                    }
                } else {
                    ans = 1;
                }


            } else {
                ans = 1;
            }
        }
        return ans;
    }



    //贪心
    public int maximumSetSize(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i : nums1) {
            set1.add(i);
        }
        for (int i : nums2) {
            set2.add(i);
        }
        //找出set1和set2都存在的
        Set<Integer> all = new HashSet<>();
        for (Integer i : set1) {
            if (set2.contains(i)) {
                all.add(i);
            }
        }

        //还需要删除这些
        int need1 = Math.max(0,   (set1.size()-nums1.length/2));
        int need2 = Math.max(0,  (set2.size() - nums2.length/2));

        //能用公共的覆盖
        if (all.size() >= need1 + need2) {
            return set1.size() + set2.size() - all.size();
        }else {
            return set1.size()+set2.size()-need1-need2;
        }

    }

    public static void main(String[] args) {
        wk379 w = new wk379();
        w.maximumSetSize(new int[]{1,2,3,4,5,6},new int[]{2,3,2,3,2,3});
    }
}
