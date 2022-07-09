package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wk293 {

    //ranking: 1746 / 7357
    //简单题，统计字母个数比较就可以了
    public List<String> removeAnagrams(String[] words) {
        List<String> res = new ArrayList<>();
        String pre = "";
        for (int i = 0; i < words.length; i++) {
            int[] count = new int[26];
            for (int j = 0; j < words[i].length(); j++) {
                int c = words[i].charAt(j) - 'a';
                count[c]++;
            }
            String s = "";
            for (int j : count) {
                s += j;
            }
            if (!pre.equals(s)) res.add(words[i]);
            pre = s;
        }
        return res;
    }


    //中等题，对special排序，两个sp之间的距离就是连续楼层，求其最大值，注意上下边界
    static public int maxConsecutive(int bottom, int top, int[] special) {
        int pre = bottom;
        int max = 0;
        Arrays.sort(special);
        for (int i = 0; i < special.length; i++) {
            max = Math.max(max, special[i] - pre);
            pre = special[i] + 1;
        }
        max = Math.max(max, top - pre + 1);
        return max;
    }

    //中等题，灵活转换，&操作下，只有都是1的位置才会结果为1，统计二进制下每个位置1的个数即可
    public int largestCombination(int[] candidates) {
        int[] count = new int[32];
        for (int i = 0; i < candidates.length; i++) {
            int num = candidates[i];
            for (int j = 0; j < 32; j++) {
                if (((num >> j) & 1) > 0) {
                    count[j]++;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < count.length; i++) {
            max = Math.max(count[i], max);
        }
        return max;
    }

/*  static   class CountIntervals {


        //start end；
        TreeMap<Integer, Integer> tree = new TreeMap<>();
        int count = 0;

        public CountIntervals() {
        }

        int sum=0;
        public void add(int left, int right) {

            System.out.println("s"+ left+ " "+right);
            //已经被覆盖了
            Map.Entry<Integer, Integer> over = tree.floorEntry(left);
            if (over != null && over.getValue() >= right) return;

            count += (right - left + 1);

            //先查看是否覆盖了区间
            while (tree.ceilingEntry(left) != null) {
                Map.Entry<Integer, Integer> high = tree.ceilingEntry(left);
                if (high.getValue() <= right) {
                    count -= (high.getValue() - high.getKey() + 1);
                    tree.remove(high.getKey());
                } else break;
            }


            boolean flag=false;

            //左侧是否重叠
            Map.Entry<Integer, Integer> leftNode = tree.floorEntry(left);
            if (leftNode != null && leftNode.getValue() <= right && leftNode.getValue() >= left) {
                System.out.println("left");
                tree.put(leftNode.getKey(), right);
                count -= (leftNode.getValue() - left + 1);
                flag=true;
                System.out.println(count);
            }
            //右侧是否重叠

            Map.Entry<Integer, Integer> rightNode = tree.floorEntry(right);
            if (rightNode != null && rightNode.getKey() >= left && rightNode.getKey() <= right) {
                System.out.println("right");

                tree.remove(rightNode.getKey());
                tree.put(left, rightNode.getValue());
                count -= (right - rightNode.getKey() + 1);
                flag=true;
                System.out.println(count);
            }

            //未覆盖
            if(!flag) tree.put(left,right);

            System.out.println(sum+++" "+count);
        }

        public int count() {
            return count;
        }
    }*/


    //困难题，合并区间，之前做过类似的题目，这次又没做出来，XXXX
    //treeMap保存区间的头尾，当有闲的区间进来的时候，不断的找到<=right的位置，并且要>=left，删除此区间，更新心的区间大小
    //线段树：动态开点线段树，防止范围太大 out of memory
 /*   class CountIntervals {

        TreeMap<Integer, Integer> map = new TreeMap<>();

        int ans = 0;

        public CountIntervals() {

        }

        public void add(int left, int right) {
            Integer L = map.floorKey(right);
            int l = left, r = right;
            while (L != null && map.get(L) >= l) { //判断有交集
                l = Math.min(l, L);   //求最小左侧
                r = Math.max(r, map.get(L)); //求最大右侧
                ans -= (map.get(L) - L + 1);//count也要删掉
                map.remove(L); //删掉此段

                L = map.floorKey(right);
            }
            ans += (r - l + 1);
            map.put(l, r);
        }

        public int count() {
            return ans;
        }
    }*/


    //线段树：动态开点线段树，防止范围太大 out of memory
    class CountIntervals {
        int l, r, cnt;
        CountIntervals L, R;

        public CountIntervals() {
            this.l = 1;
            this.r = (int) 1e9;
        }

        public CountIntervals(int l, int r) {
            this.l = l;
            this.r = r;

        }

        public void add(int left, int right) {
            //表示这一段之前有过，再加还是这样， 直接返回
            if (cnt == r - l + 1) return;
            //表示完全覆盖了这段 直接返回
            if (left <= l && right >= r) {
                cnt = r - l + 1;
                return;
            }
            //均分这一段
            int mid = (l + r) / 2;
            if (L == null) L = new CountIntervals(l, mid);
            if (R == null) R = new CountIntervals(mid + 1, r);
            //和左边有没有交集
            if (left <= mid) L.add(left, right);
            //和右边有没有交集
            if (mid < right) R.add(left, right);

            cnt = L.cnt + R.cnt;
        }

        public int count() {
            return cnt;
        }
    }

    public static void main(String[] args) {
   /*   CountIntervals c=new CountIntervals();
      c.add(2,3);
      c.add(7,10);
      c.count();
      c.add(5,8);
      c.count();*/
    }
}
