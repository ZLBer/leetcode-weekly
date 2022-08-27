package weekly;

import java.util.TreeMap;

public class wkb85 {

    //ranking: 228 / 4193

    //简单题，暴力
   /* public int minimumRecolors(String blocks, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i + k <= blocks.length(); i++) {
            int count = 0;
            for (int j = i; j < i + k; j++) {
                char c = blocks.charAt(j);
                if (c == 'W') {
                    count++;
                }
            }
            ans = Math.min(count, ans);
        }
        return ans;
    }*/

    //简单题，滑动窗口
    public int minimumRecolors(String blocks, int k) {
        int ans = Integer.MAX_VALUE;
        int count = 0;
        for (int i = 0; i < k-1; i++) {
            if(blocks.charAt(i)=='W'){
                count++;
            }
        }
        for(int i=k-1;i<blocks.length();i++){
            if(blocks.charAt(i)=='W'){
                count++;
            }
            ans=Math.min(ans,count);
            if(blocks.charAt(i-k+1)=='W'){
                count--;
            }
        }
        return ans;
    }

    //中等题，暴力
   /* public int secondsToRemoveOccurrences(String s) {
        char[] chars = s.toCharArray();
        int ans = 0;
        while (true) {
            boolean change = false;
            for (int i = 1; i < s.length(); i++) {
                if (chars[i - 1] == '0' && chars[i] == '1') {
                    chars[i - 1] = '1';
                    chars[i] = '0';
                    change = true;
                    i++;
                }
            }
            if (change) ans++;
            else return ans;
        }
    }*/

    public int secondsToRemoveOccurrences(String s) {
        int res=0;
        int count=0;
        for (char c : s.toCharArray()) {
            if(c=='0'){
                count++;
            }else if(count>0){
                res=Math.max(res+1,count);
            }
        }
        return res;
    }

        public String shiftingLetters(String s, int[][] shifts) {
        char[] chars = s.toCharArray();
        //差分数组统计移动的位数
        int[] count = new int[s.length() + 1];
        for (int i = 0; i < shifts.length; i++) {
            int left = shifts[i][0];
            int right = shifts[i][1];
            int u = shifts[i][2];
            if (u == 0) {
                count[left] -= 1;
                count[right + 1] += 1;
            } else {
                count[left] += 1;
                count[right + 1] -= 1;
            }
        }
        int cur = 0;
        for (int i = 0; i < chars.length; i++) {
            cur += count[i];
            //System.out.println(chars[i] + "   " + cur);
            //去除多余的循环
            chars[i] += (cur % 26);
            //+26或-26
            if (chars[i] < 'a') {
                chars[i] += 26;
            } else if (chars[i] > 'z') {
                chars[i] -= 26;
            }
        }
        return new String(chars);
    }


    //困难题，倒着思考，然后并查集合并区间
    public long[] maximumSegmentSum(int[] nums, int[] removeQueries) {
        long[] res = new long[nums.length];
        long max = 0;
        long[] sum = new long[nums.length];//用来存以i为uf key的子数组和
        UnionFind uf = new UnionFind(nums.length);
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] = max;
            int index = removeQueries[i];
            int num = nums[index];
            sum[index] = num;
            max = Math.max(max, sum[index]);
            //左边可以合并
            if (index - 1 >= 0 && sum[index - 1] > 0) {
                long s = sum[uf.find(index - 1)] + sum[uf.find(index)];
                uf.union(index - 1, index);
                sum[uf.find(index)] = s;
                max = Math.max(s, max);
            }
            //右边可以合并
            if (index + 1 < nums.length && sum[index + 1] > 0) {
                long s = sum[uf.find(index + 1)] + sum[uf.find(index)];
                uf.union(index + 1, index);
                sum[uf.find(index)] = s;
                max = Math.max(s, max);
            }
        }
        return res;
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

    public static void main(String[] args) {

    }
}
