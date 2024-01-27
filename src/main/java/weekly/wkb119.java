package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wkb119 {

    //遍历
    public int[] findIntersectionValues(int[] nums1, int[] nums2) {
        int[] ans = new int[2];
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    ans[0]++;
                    break;
                }
            }
        }
        for (int i = 0; i < nums2.length; i++) {
            for (int j = 0; j < nums1.length; j++) {
                if (nums2[i] == nums1[j]) {
                    ans[1]++;
                    break;
                }
            }
        }
        return ans;
    }


    //贪心
    public int removeAlmostEqualCharacters(String word) {
        int ans = 0;
        for (int i = 1; i < word.length(); i++) {
            char c = word.charAt(i);
            char p = word.charAt(i - 1);
            if (c == p || Math.abs(c - p) == 1) {
                ans++;
                i++;
            }
        }
        return ans;
    }


    //滑动窗口
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int ans = 1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            map.put(num, map.getOrDefault(num, 0) + 1);
            int count = map.get(num);

            if (count > k) {
                while (nums[left] != num) {
                    map.put(nums[left], map.get(nums[left]) - 1);
                    left++;
                }
                map.put(nums[left], map.get(nums[left]) - 1);
                left++;
            }
            ans = Math.max(i - left + 1, ans);

        }
        return ans;
    }


    public int numberOfSets(int n, int maxDistance, int[][] roads) {
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], Integer.MAX_VALUE / 2); // 防止加法溢出
            g[i][i] = 0;
        }
        for (int[] e : roads) {
            int x = e[0], y = e[1], wt = e[2];
            g[x][y] = Math.min(g[x][y], wt);
            g[y][x] = Math.min(g[y][x], wt);
        }

        int ans = 0;
        int[][] f = new int[n][n];
        for (int s = 0; s < (1 << n); s++) {
            //拷贝一次路径图
            for (int i = 0; i < n; i++) {
                if ((s >> i & 1) == 1) {
                    System.arraycopy(g[i], 0, f[i], 0, n);
                }
            }

                    // Floyd计算剩下节点的最短路径
                    for (int k = 0; k < n; k++) {
                        if ((s >> k & 1) == 0) continue;
                        for (int i = 0; i < n; i++) {
                            if ((s >> i & 1) == 0) continue;
                            for (int j = 0; j < n; j++) {
                                f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
                            }
                        }
                    }

            //判断路径是不是符合条件
            int flag=0;
            for (int i = 0; i < n; i++) {
                if ((s >> i & 1) == 0) continue;
                for (int j = 0; j < n; j++) {
                    if ((s >> j & 1) == 1 && f[i][j] > maxDistance) {
                       flag=1;
                       break;
                    }
                }
                if(flag==1) break;
            }
            if (flag==1) continue;
            ans++;
        }
        return ans;
    }


//
//    public int numberOfSets(int n, int maxDistance, int[][] roads) {
//        if (n == 1) return 2;
//        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
//        int del = 0;
//        for (int[] road : roads) {
//            if (!map.containsKey(road[0])) map.put(road[0], new HashMap<>());
//            if (!map.containsKey(road[1])) map.put(road[1], new HashMap<>());
//            int min = road[2];
//            min = Math.min(road[2], map.get(road[0]).getOrDefault(road[1], Integer.MAX_VALUE));
//            map.get(road[0]).put(road[1], min);
//            map.get(road[1]).put(road[0], min);
//        }
//
//        for (Map.Entry<Integer, Map<Integer, Integer>> entry : map.entrySet()) {
//            for (Map.Entry<Integer, Integer> integerEntry : entry.getValue().entrySet()) {
//                if(integerEntry.getValue()>maxDistance){
//                    del++;
//                }
//            }
//        }
//
//        dfs(0, n, maxDistance, map, del/2, new boolean[n]);
//        return ans;
//    }
//
//    int ans = 0;
//
//    void dfs(int cur, int n, int maxDistance, Map<Integer, Map<Integer, Integer>> map, int del, boolean[] set) {
//        if (cur == n) {
//            if (del == 0) {
//                ans++;
//            }
//            return;
//        }
//
//        int s = 0;
//
//        for (Map.Entry<Integer, Integer> entry : map.get(cur).entrySet()) {
//            if (set[entry.getKey()]) continue;
//            if (entry.getValue() > maxDistance) {
//                s++;
//            }
//        }
//
//
//        dfs(cur + 1, n, maxDistance, map, del, set);
//
//        set[cur] = true;
//        dfs(cur + 1, n, maxDistance, map, del - s, set);
//        set[cur] = false;
//
//    }
//
//    public static void main(String[] args) {
//        wkb119 w=new wkb119();
//        w.numberOfSets(3,5,new int[][]{
//                {0,1,20},{0,1,10},{1,2,2},{0,2,2}        });
//    }

}
