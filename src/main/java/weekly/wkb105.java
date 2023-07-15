package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wkb105 {

    //模拟
    public int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        if (prices[0] + prices[1] > money) {
            return money;
        }
        return money - prices[0] - prices[1];
    }

    //记忆化搜索
    public int minExtraChar(String s, String[] dictionary) {
        Arrays.sort(dictionary, (a, b) -> b.length() - a.length());
        dp = new int[s.length()];
        Arrays.fill(dp, -1);
        help(s, dictionary, 0);
        return dp[0];
    }


    int[] dp;

    int help(String s, String[] dictionary, int index) {

        if (index >= s.length()) {
            return 0;
        }
        if (dp[index] != -1) return dp[index];
        int ans = help(s, dictionary, index + 1) + 1;
        for (int j = 0; j < dictionary.length; j++) {
            if (index + dictionary[j].length() > s.length()) {
                continue;
            }
            if (s.substring(index, index + dictionary[j].length()).equals(dictionary[j])) {
                ans = Math.min(ans, help(s, dictionary, index + dictionary[j].length()));
            }
        }
        dp[index] = ans;
        return ans;
    }


    //分类讨论
    public long maxStrength(int[] nums) {
        long ans = 1;

        List<Integer> list = new ArrayList<>();
        List<Integer> pos = new ArrayList<>();
        int zero = 0;
        for (int num : nums) {
            if (num > 0) {
                pos.add(num);
                ans *= num;
            } else if (num < 0) {
                list.add(num);
            } else {
                zero++;
            }
        }

        if (pos.size() == 0 && list.size() == 1) {
            if (zero > 0) {
                return 0;
            } else {
                return list.get(0);
            }
        }
        Collections.sort(list);
        int n = 0;
        if (list.size() % 2 == 0) {
            n = list.size();
        } else {
            n = list.size() - 1;
        }
        for (int i = 0; i < n; i++) {
            ans *= list.get(i);
        }
        return ans;

    }

    //并查集
    class UnionFind {
        public final int[] parents;
        public int count;

        public UnionFind(int n) {
            this.parents = new int[n];
            reset();
        }

        public void reset() {
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
            }
            count = parents.length - 1;
        }

        public int find(int i) {
            int parent = parents[i];
            if (parent == i) {
                return i;
            } else {
                int root = find(parent);
                parents[i] = root;
                return root;
            }
        }

        public boolean union(int i, int j) {
            int r1 = find(i);
            int r2 = find(j);
            if (r1 != r2) {
                count--;
                parents[r1] = r2;
                return true;
            } else {
                return false;
            }
        }

  /*      void isolate(int x) {
            if (x != parents[x]) {
                parents[x] = x;
                count++;
            }
        }*/

    }

    public static List<Integer> getPrimes(int n) {

        boolean[] isComposite = new boolean[n + 1];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * 2; j >= 0 && j <= n; j += i) { // 标记该数的倍数为合数
                    isComposite[j] = true;
                }
            }
        }

        return primes;
    }

    static List<Integer>[] zhiyinshu = new List[100001];

    static {

        zhiyinshu[1]=new ArrayList<>();
        for (int i = 2; i < zhiyinshu.length; i++) {
            if (zhiyinshu[i] == null) {
                for (int j = i; j < zhiyinshu.length; j += i) {
                    if (zhiyinshu[j] == null) {
                        zhiyinshu[j] = new ArrayList<>();
                    }
                    zhiyinshu[j].add(i);
                }
            }
        }
    }

    public boolean canTraverseAllPairs(int[] nums) {
        int n = nums.length;
        int mx = 0;
        for (int num : nums) {
            mx = Math.max(num, mx);
        }
        UnionFind uf = new UnionFind(nums.length + mx+1);

        for (int i = 0; i < nums.length; i++) {
            for (Integer prime : zhiyinshu[nums[i]]) {
                uf.union(i, prime + n);
            }
        }
        int group = -1;
        for (int i = 0; i < n; i++) {
            int g = uf.find(i);
            if (group == -1) group = g;
            if (group != g) return false;
        }
        return true;

    }
   /* public boolean canTraverseAllPairs(int[] nums) {

        if(nums.length==1&&nums[0]==1) return true;

        Set<Integer> set=new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        nums=new int[set.size()];
        int index=0;
        for (Integer integer : set) {
            nums[index++]=integer;
        }

        UnionFind uf = new UnionFind(primes.size());
        int[] count = new int[primes.size()];
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) return false;
            for (int j = 0;j < primes.size()&&nums[i]>=primes.get(j); j++) {
                if (nums[i] % primes.get(j) == 0) {
                    count[j]++;
                    if(!map.containsKey(i)){
                        map.put(i,j);
                    }else {
                        uf.union(map.get(i),j);
                    }
                }
            }
        }
        int group=-1;
        for (int i = 0; i < primes.size(); i++) {
            if(count[i]==0) continue;
            int g = uf.find(i);
            if(group==-1) group=g;
            if(group!=g) return false;
        }
        return true;
    }*/


}
