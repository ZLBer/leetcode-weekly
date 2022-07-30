package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.IntFunction;

public class wk303 {


    //ranking:800 / 7032

    //简单题，计数即可
    public char repeatedCharacter(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
            if (count[c - 'a'] >= 2) {
                return c;
            }
        }
        return '1';
    }


    //中等题，模拟
    public int equalPairs(int[][] grid) {
        List<List<Integer>> row = new ArrayList<>();
        List<List<Integer>> col = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            List<Integer> r = new ArrayList<>();
            List<Integer> c = new ArrayList<>();
            for (int j = 0; j < grid[0].length; j++) {
                r.add(grid[i][j]);
                c.add(grid[j][i]);
            }
            row.add(r);
            col.add(c);
        }
        int res = 0;
        for (List<Integer> r : row) {
            for (List<Integer> c : col) {
                boolean flag = true;
                for (int i = 0; i < r.size(); i++) {
                    if (!r.get(i).equals(c.get(i))) {
                        flag = false;
                        break;
                    }
                }
                if (flag) res++;

            }
        }
        return res;

    }



    //中等题，hashmap+PriorityQueue，懒修改，一时没想明白怎么映射
    class FoodRatings {

        class pac {
            int rate;
            String name;

            public pac(int rate, String name) {
                this.name = name;
                this.rate = rate;
            }
        }

        Map<String, PriorityQueue<pac>> map = new HashMap<>();
        Map<String, Integer> rate = new HashMap<>();
        Map<String, String> type = new HashMap<>();


        public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {

            for (int i = 0; i < foods.length; i++) {
                String food = foods[i];
                String cuisine = cuisines[i];
                int rating = ratings[i];
                type.put(food, cuisine);
                if (!map.containsKey(cuisine)) {
                    map.put(cuisine, new PriorityQueue<>((a, b) -> a.rate == b.rate ? a.name.compareTo(b.name) : b.rate - a.rate));
                }
                rate.put(food, rating);
                map.get(cuisine).add(new pac(rating, food));
            }
        }

        public void changeRating(String food, int newRating) {
            map.get(type.get(food)).add(new pac(newRating, food));
            rate.put(food, newRating);
        }

        public String highestRated(String cuisine) {
            PriorityQueue<pac> pacs = map.get(cuisine);
            while (!pacs.isEmpty()) {
                pac poll = pacs.poll();
                if (!rate.get(poll.name).equals(poll.rate)) {
                    continue;
                } else {
                    pacs.add(poll);
                    return poll.name;
                }
            }
            return "";
        }
    }



    //困难提，找规律+二分查找
  /*  public long countExcellentPairs(int[] nums, int k) {

        Map<Integer, Integer> set = new HashMap<>();

        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            set.put(nums[i], set.getOrDefault(nums[i], 0) + 1);
        }


        nums = new int[set.size()];
        int j = 0;
        for (Map.Entry<Integer, Integer> entry : set.entrySet()) {
            Integer num = entry.getKey();
            if (Integer.bitCount(num ) + Integer.bitCount(num) >= k) {
                res++;
            }
            nums[j++] = Integer.bitCount(num);
        }

        Arrays.sort(nums);
     
        for (int i = 0; i < nums.length; i++) {
            res += ((long) help(nums, k - nums[i], i+1)) * 2;
        }
        return res;

    }

    int help(int[] nums, int k, int left) {

        int right = nums.length - 1;
        if(left>right) return 0;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (nums[left] < k) return 0;
        return nums.length - left;
    }*/


    //统计次数+前缀和
    public long countExcellentPairs(int[] nums, int k) {
        int[] count=new int[33];
        Set<Integer> set=new HashSet<>();
        for (int num : nums) {
            if(!set.contains(num)){
                count[Integer.bitCount(num)]++;
                set.add(num);
            }
        }
        long ans=0;
        int sum=0;
        for(int i=k;i<count.length;i++){
            sum+=count[i];
        }

        for(int i=0;i<count.length;i++){
            ans+=sum*(long)count[i];
            int j=k-1-i;
            if(j>=0&&j<count.length) sum+=count[j];
        }
        return ans;
    }
}
