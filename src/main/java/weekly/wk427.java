package weekly;

import java.util.Arrays;

public class wk427 {
    //模拟
    public int[] constructTransformedArray(int[] nums) {
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int index = nums[i] % nums.length;
            ans[i] = nums[(i + index + nums.length) % nums.length];
        }
        return ans;
    }

    //暴力
   /* public long maxRectangleArea(int[][] points) {
        Arrays.sort(points, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
       int ans=-1;
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int m = j+1; m < points.length; m++) {
                    for (int n = m+1; n < points.length; n++) {
                     ans=Math.max(help(i,j,m,n,points),ans);
                    }
                }
            }
        }
        return ans;
    }

    int help(int a,int b,int c,int d, int[][] points) {
        //构成矩形
       if(points[a][0]==points[b][0]&&points[a][1]==points[c][1]&&
               points[b][1]==points[d][1]&&points[c][0]==points[d][0]){
           for (int i = 0; i < points.length; i++) {
               if(a==i||b==i||c==i||d==i){
                   continue;
               }
               if(points[i][0]>=points[a][0]&&points[i][0]<=points[d][0]&&
                   points[i][1]>=points[a][a]&&points[i][1]<=points[b][1]){
                   return -1;
               }
           }
           return (points[d][0]-points[a][0])*(points[b][1]-points[a][1]);
        }
        return -1;
    }*/


    //前缀和
    static public long maxSubarraySum(int[] nums, int k) {
        long sum = 0;
        int left = 0;
        long[] pre = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                sum += nums[i];
                continue;
            }
            sum += nums[i];
            pre[i] = sum;
            sum -= nums[left++];
        }

        long ans = pre[k - 1];

        for (int i = k; i < nums.length; i++) {
            if (pre[i - k] > 0) {
                pre[i] += pre[i - k];
            }
            ans = Math.max(pre[i], ans);
        }

        return ans;
    }

    public long maxRectangleArea(int[][] points) {
        Arrays.sort(points, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int ans=-1;
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int m = j+1; m < points.length; m++) {
                    for (int n = m+1; n < points.length; n++) {
                        ans=Math.max(help(i,j,m,n,points),ans);
                    }
                }
            }
        }
        return ans;
    }

    int help(int a,int b,int c,int d, int[][] points) {
        //构成矩形
        if(points[a][0]==points[b][0]&&points[a][1]==points[c][1]&&
                points[b][1]==points[d][1]&&points[c][0]==points[d][0]){
            for (int i = 0; i < points.length; i++) {
                if(a==i||b==i||c==i||d==i){
                    continue;
                }
                if(points[i][0]>=points[a][0]&&points[i][0]<=points[d][0]&&
                        points[i][1]>=points[a][a]&&points[i][1]<=points[b][1]){
                    return -1;
                }
            }
            return (points[d][0]-points[a][0])*(points[b][1]-points[a][1]);
        }
        return -1;
    }

    public static void main(String[] args) {
        wk427 w=new wk427();
        w.maxRectangleArea(new int[][]{
                {1,1},{1,3},{3,1},{3,3}
        });
    }

}
