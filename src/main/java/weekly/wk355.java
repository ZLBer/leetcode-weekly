package weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk355 {


    //模拟 注意转义
    static public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> res = new ArrayList<>();
        String sp = String.valueOf('\\') + String.valueOf(separator);
        for (String word : words) {
            String[] split = word.split(sp);
            for (String s : split) {
                if ("".equals(s)) continue;
                res.add(s);
            }
        }
        return res;
    }


    //贪心
    public long maxArrayValue(int[] nums) {
        long ans = nums[nums.length - 1];
        long pre = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= pre) {
                pre += nums[i];
            } else {
                pre = nums[i];
            }
            ans = Math.max(pre, ans);
        }
        return ans;
    }

    //二分
    public int maxIncreasingGroups(List<Integer> usageLimits) {
        Collections.sort(usageLimits, Collections.reverseOrder());
        int left = 1, right = usageLimits.size();
        while (left < right) {
            int mid = (left + right + 1) / 2;
            int count = mid;
            int gap = 0;
            for (Integer usageLimit : usageLimits) {
                //gap是能借，不能留给下次使用
                gap = Math.min(gap + usageLimit - count, 0);
                if (count > 0) {
                    count--;
                }
            }
            if (gap >= 0) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }


    // 状态压缩+遍历 时间复杂度很高
   /* public long countPalindromePaths(List<Integer> parent, String s) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 1; i < parent.size(); i++) {
            int p = parent.get(i);
            if (!map.containsKey(p)) {
                map.put(p, new ArrayList<>());
            }
            map.get(p).add(i);
        }

        dfs(0, s, map);
        return ans;
    }

    long ans = 0;

    Map<Integer, Integer> dfs(int cur, String s, Map<Integer, List<Integer>> map) {
        if (!map.containsKey(cur)) {
            return new HashMap<>();
        }

        Map<Integer, Integer> parenMap = new HashMap<>();
        for (Integer child : map.get(cur)) {
            int c = 1 << (s.charAt(child) - 'a');

            Map<Integer, Integer> childMap = dfs(child, s, map);

            Map<Integer, Integer> newChildMap = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : childMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                int nkey = key ^ c;
                if (Integer.bitCount(nkey) == 1 || Integer.bitCount(nkey) == 0) {
                    ans += value;
                }

                newChildMap.put(nkey, newChildMap.getOrDefault(nkey, 0) + value);
            }
            //加单独这一条
            newChildMap.put(c, newChildMap.getOrDefault(c, 0) + 1);
            ans++;

            for (Map.Entry<Integer, Integer> entry : newChildMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                //完全递归子串
                ans += ((long) value) * parenMap.getOrDefault(key, 0);
                //非完全递归子串
                for (int i = 0; i < 26; i++) {
                    int k = key ^ (1 << i);
                    ans += ((long) value) * parenMap.getOrDefault(k, 0);
                }
            }
            for (Map.Entry<Integer, Integer> entry : newChildMap.entrySet()) {
                parenMap.put(entry.getKey(), parenMap.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }
        return parenMap;
    }*/


    //位运算+推断性质
    public long countPalindromePaths(List<Integer> parent, String s) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 1; i < parent.size(); i++) {
            int p = parent.get(i);
            if (!map.containsKey(p)) {
                map.put(p, new ArrayList<>());
            }
            map.get(p).add(i);
        }

        Map<Integer, Integer> counter = new HashMap<>();
        dfs(0, -1, s, 0, map, counter);
        long ans = 0;
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            ans += (long) (value - 1) * value;

            for (int i = 0; i < 26; i++) {
                int k = key ^ (1 << i);
                ans += ((long) value) * counter.getOrDefault(k, 0);
            }
        }
        return ans;
    }

    void dfs(int cur, int parent, String s, int status, Map<Integer, List<Integer>> map, Map<Integer, Integer> counter) {

        if (cur == parent) {
            return;
        }

        counter.put(status, counter.getOrDefault(status, 0) + 1);

        for (Integer child : map.getOrDefault(cur, new ArrayList<>())) {
            int c = 1 << (s.charAt(child) - 'a');
            dfs(child, cur, s, status ^ c, map, counter);
        }
    }

    public static void main(String[] args) {
        wk355 w = new wk355();
        List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(-1, 5, 0, 5, 5, 2));
        w.countPalindromePaths(list, "xsbcqq");
    }


}
