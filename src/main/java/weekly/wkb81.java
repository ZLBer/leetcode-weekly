package weekly;

import java.util.HashMap;
import java.util.Map;

public class wkb81 {

    
    
    //简单题，按照|分割字符串就行，注意转义符
    public int countAsterisks(String s) {
        String[] split = s.split("\\|");
        int star = 0;
        for (int i = 0; i < split.length; i += 2) {

            for (int j = 0; j < split[i].length(); j++) {
                if (split[i].charAt(j) == '*') {
                    star++;
                }
            }
        }
        return star;
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


    //中等题，并查集统计分成多少个集合以及集合的大小，
    public long countPairs(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int k = uf.find(i);
            map.put(k, map.getOrDefault(k, 0) + 1);
        }
        if (map.size() == 1) return 0;
        long res = 0;

        for (Integer value : map.values()) {
            res += ((long) value) * (n - value);
        }
        return res / 2;
    }


    //中等题，实在想不出来可以仔细观察用例
    public int maximumXOR(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res |= num;
        }
        return res;
    }



    //检查要一次i，再要一次j能不能符合条件
    boolean check(int i, int j) {
        if (i == j) return false;
        if (i % 2 == 0 && j % 2 == 0) return false;
        if (i % 3 == 0 && j % 3 == 0) return false;
        return true;
    }

    public int distinctSequences(int n) {
        int mod=(int)1e9+7;
        if (n == 1) return 6;
        if (n == 2) return 22;


        long[][] dp = new long[6][6];
        //一开始符合条件的只能是1种
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                //不能相同，不能有公约数>1
                if (check(i + 1, j + 1)) dp[i][j] = 1;
            }
        }

        for (int k = 2; k < n; k++) {
            long[][] ndp = new long[6][6];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    //不能相同，不能有公约数>1
                    if (check(i + 1, j + 1)) {
                        for (int m = 0; m < 6; m++) {
                            if(m==j) continue;
                            //新的i到j 可以使 0-6到i的
                            //即  0-6 m -> i -> j
                            ndp[i][j]+=dp[m][i];
                            ndp[i][j]%=mod;
                        }
                    }
                }
            }
            dp = ndp;
        }

        long res=0;
        for (long[] longs : dp) {
            for (long aLong : longs) {
                res+=aLong;
                res%=mod;
            }
        }
        return (int)res;
    }

    public static void main(String[] args) {

    }
}
