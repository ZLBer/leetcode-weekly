package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wk361 {

    //枚举
    public int countSymmetricIntegers(int low, int high) {

        int ans = 0;
        for (int i = low; i <= high; i++) {
            int num = i;
            List<Integer> list = new ArrayList<>();
            while (num > 0) {
                list.add(num % 10);
                num /= 10;
            }
            if (list.size() % 2 == 1) continue;
            int sum = 0;
            for (int j = 0; j < list.size() / 2; j++) {
                sum += list.get(j);
            }
            for (int j = list.size() / 2; j < list.size(); j++) {
                sum -= list.get(j);
            }
            if (sum == 0) ans++;
        }
        return ans;
    }


    //枚举后缀
    public int minimumOperations(String num) {
        int ans = num.length();
        //变成0
        if (num.contains("0")) {
            ans = num.length() - 1;
        }

        ans = Math.min(ans, help(num, new char[]{'5', '2'}));
        ans = Math.min(ans, help(num, new char[]{'0', '5'}));
        ans = Math.min(ans, help(num, new char[]{'5', '7'}));
        ans = Math.min(ans, help(num, new char[]{'0', '0'}));
        return ans;
    }

    int help(String num, char[] arr) {
        int index = 0;
        int ans = 0;
        for (int i = num.length() - 1; i >= 0; i--) {
            if (num.charAt(i) == arr[index]) {
                index++;
                if (index >= arr.length) break;
            } else {
                ans++;
            }
        }
        if (index < arr.length) return Integer.MAX_VALUE;
        return ans;
    }


    //前缀和
    static public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        int[] pre = new int[nums.size() + 1];
        for (int i = 0; i < nums.size(); i++) {
            pre[i + 1] = pre[i] + (nums.get(i) % modulo == k ? 1 : 0);
        }

        Map<Integer, Integer> map = new HashMap<>();

        long ans = 0;
        for (int i = 0; i < pre.length; i++) {
            int num = pre[i];
            ans += map.getOrDefault((num %modulo  - k + modulo) % modulo,0);
            map.put(num % modulo, map.getOrDefault(num % modulo, 0) + 1);
        }
        return ans;
    }


   /* static public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        int[][] check = new int[nums.size()][2];
        int pre = 0;
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            if (num % modulo == k) {
                check[i][0] = 1;
                check[i][1] = pre;
                pre = 0;
            } else {
                pre++;
            }
        }

        List<int[]> list = new ArrayList<>();
        long ans = 0;
        pre = 0;
        for (int i = 0; i < nums.size(); i++) {
            int sum = 0;
            if (check[i][0] > 0) {
                int left = list.size() - modulo;
                list.add(new int[]{i, (left >= 0 && left < list.size() ? list.get(left)[1] : 0) + pre + 1});
                pre = 0;
            } else {
                pre++;
                if (k == 0) sum = pre;
            }
            int j = k == 0 ? list.size() - modulo : list.size() - k;
            if (j >= 0) sum += list.get(j)[1];
            ans += sum;
        }
        return ans;
    }*/



    //双上倍增+lca
    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int [] e : edges) {
            int x = e[0], y = e[1], w = e[2] - 1;
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        int m = 32 - Integer.numberOfLeadingZeros(n); // n 的二进制长度
        int [][] pa = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(pa[i], -1);
        }
        int [][][] cnt = new int[n][m][26];
        int [] depth = new int[n];
        //求出 父节点、cnx[x][0]，深度数组
        dfs(0, -1, g, pa, cnt, depth);

        //倍增求每个点的2的i次方的次数以及位置
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                if (p != -1) {
                    int pp = pa[p][i];
                    pa[x][i + 1] = pp;
                    for (int j = 0; j < 26; j++) {
                        cnt[x][i + 1][j] = cnt[x][i][j] + cnt[p][i][j];
                    }
                }
            }
        }

        //计算结果
        int [] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int x = queries[qi][0], y = queries[qi][1];
            int pathLen = depth[x] + depth[y];
            //倍增求lca，顺便计算次数
            int [] cw = new int[26];
            //置换深度大小，让y始终是深的
            if (depth[x] > depth[y]) {
                int temp = x;
                x = y;
                y = temp;
            }

            // 让 y 和 x 在同一深度，避免在同一个路径上
            for (int k = depth[y] - depth[x]; k > 0; k &= k - 1) {
                int i = Integer.numberOfTrailingZeros(k);
                int p = pa[y][i];
                for (int j = 0; j < 26; ++j) {
                    cw[j] += cnt[y][i][j];
                }
                y = p;
            }
            if (y != x) {
                for (int i = m - 1; i >= 0; i--) {
                    int px = pa[x][i];
                    int py = pa[y][i];
                    //相等表示跳过了
                    if (px != py) {
                        for (int j = 0; j < 26; j++) {
                            cw[j] += cnt[x][i][j] + cnt[y][i][j];
                        }
                        x = px;
                        y = py; // x 和 y 同时上跳 2^i 步
                    }
                }
                for (int j = 0; j < 26; j++) {
                    cw[j] += cnt[x][0][j] + cnt[y][0][j];
                }
                x = pa[x][0];
            }

            int lca = x;
            pathLen -= depth[lca] * 2;
            int maxCw = 0;
            for (int i = 0; i < 26; i++) {
                maxCw = Math.max(maxCw, cw[i]);
            }
            ans[qi] = pathLen - maxCw;
        }
        return ans;
    }

    private void dfs(int x, int fa, List<int[]>[] g, int[][] pa, int[][][] cnt, int[] depth) {
        pa[x][0] = fa;
        for (int [] e : g[x]) {
            int y = e[0], w = e[1];
            if (y != fa) {
                cnt[y][0][w] = 1;
                depth[y] = depth[x] + 1;
                dfs(y, x, g, pa, cnt, depth);
            }
        }
    }


}
