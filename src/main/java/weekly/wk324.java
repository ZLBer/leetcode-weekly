package weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk324 {

    //ranking: 1206 / 4167

    //暴力
   /* public int similarPairs(String[] words) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            int[] count = new int[26];
            for (char c : words[i].toCharArray()) {
                count[c - 'a']++;
            }
            list.add(count);
        }

        int ans = 0;
        for (int i = 0; i < words.length; i++) {
            int[] count = list.get(i);
            for (int j = i + 1; j < words.length; j++) {
                boolean flag = true;
                int[] count2 = list.get(j);
                for (int k = 0; k < count.length; k++) {
                    if ((count[k] == 0 && count2[k] == 0) || (count[k] > 0 && count2[k] > 0)) {
                        continue;
                    } else {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    System.out.println(i + " " + j);
                    ans++;
                }
            }

        }
        return ans;
    }*/

    //位运算
    public int similarPairs(String[] words) {
        int ans=0;
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            int mask=0;
            for (char c : words[i].toCharArray()) {
                mask|=(1<<(c-'a'));
            }
            ans+=map.getOrDefault(mask,0);
            map.put(mask,map.getOrDefault(mask,0)+1);
        }
        return ans;
    }

    public int smallestValue(int n) {
        int sum = 0;
        for (int i = 2, m = n; m > 1; i++) {
            while (m % i == 0) {
                m /= i;
                sum += i;
            }
        }
        return sum == n ? n : smallestValue(sum);
    }

    public boolean isPossible(int n, List<List<Integer>> edges) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (List<Integer> edge : edges) {
            int a = edge.get(0);
            int b = edge.get(1);
            if (!map.containsKey(a)) map.put(a, new HashSet<>());
            if (!map.containsKey(b)) map.put(b, new HashSet<>());
            map.get(a).add(b);
            map.get(b).add(a);
        }
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() % 2 != 0) {
                //奇数的点
                list.add(entry.getKey());
            }
        }
        if (list.size() > 4 || list.size() % 2 != 0) {
            return false;
        }
        if (list.size() == 0) return true;
        if (list.size() == 2) {
            int a = list.get(0);
            int b = list.get(1);
            for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
                if (!entry.getValue().contains(a) && !entry.getValue().contains(b)) {
                    return true;
                }
            }
            return false;
        } else {
            int a = list.get(0);
            int b = list.get(1);
            int c = list.get(2);
            int d = list.get(3);
            return (check(a, b, map) && check(c, d, map)) || (check(a, c, map) && check(b, d, map)) || (check(a, d, map) && check(b, c, map));
        }
    }

    boolean check(int a, int b, Map<Integer, Set<Integer>> map) {
        if (!map.get(a).contains(b)) {
            return true;
        }
        return false;
    }


    public int[] cycleLengthQueries(int n, int[][] queries) {
        int[] res = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0];
            int b = queries[i][1];
            res[i] = findFather(a, b);
        }
        return res;
    }

    public double log2(double N) {
        return Math.log(N) / Math.log(2);
    }

    int findFather(int i, int j) {
        int res=0;
        while (i != j) {
            res++;
            if (i > j) i /= 2;
            else j /= 2;
        }
        return res;
    }

}
