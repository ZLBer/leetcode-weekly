package weekly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk436 {
    public int[][] sortMatrix(int[][] grid) {
        int n = grid.length;
        if (n == 1) return grid;
        for (int i = 1; i < n; i++) {
            int x = 0, y = i;
            List<Integer> nums = new ArrayList<>();
            List<int[]> indexs = new ArrayList<>();
            while (x < n && y < n) {
                indexs.add(new int[]{x, y});
                nums.add(grid[x][y]);
                x++;
                y++;
            }
            Collections.sort(nums);
            for (int j = 0; j < indexs.size(); j++) {
                grid[indexs.get(j)[0]][indexs.get(j)[1]] = nums.get(j);
            }
        }
        for (int i = 0; i < n; i++) {
            int x = i, y = 0;
            List<Integer> nums = new ArrayList<>();
            List<int[]> indexs = new ArrayList<>();
            while (x < n && y < n) {
                indexs.add(new int[]{x, y});
                nums.add(grid[x][y]);
                x++;
                y++;
            }
            Collections.sort(nums, Comparator.reverseOrder());
            for (int j = 0; j < indexs.size(); j++) {
                grid[indexs.get(j)[0]][indexs.get(j)[1]] = nums.get(j);
            }
        }
        return grid;
    }


    public int[] assignElements(int[] groups, int[] elements) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = elements.length - 1; i >= 0; i--) {
            map.put(elements[i], i);
        }


        int[] ans = new int[groups.length];
        for (int i = 0; i < groups.length; i++) {
            int num = groups[i];
            int index=Integer.MAX_VALUE;
            for(int j=1;j<=Math.sqrt(num);j++){
                if(num%j==0){
                    index=Math.min(index,map.getOrDefault(j,Integer.MAX_VALUE));
                    index=Math.min(index,map.getOrDefault(num/j,Integer.MAX_VALUE));
                }
            }
            ans[i]=(index==Integer.MAX_VALUE?-1:index);
        }
        return ans;
    }


    public long countSubstrings(String s) {
        long []sum=new long[][s.length()];
        for (int i = s.length()-1; i >= 0; i--) {
            
        }
    }

    public static void main(String[] args) {

    }
}
