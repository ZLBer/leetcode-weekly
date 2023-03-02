package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wkb98 {
    //ranking: 475 / 3250

    //最大值  将第一个不为9的替换成9
    //最小值  将第一个替换为0
    static public int minMaxDifference(int num) {
        String s = num + "";
        char big = ' ';
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '9') {
                big = s.charAt(i);
                break;
            }
        }
        char little = s.charAt(0);
        StringBuilder bigNum = new StringBuilder();
        StringBuilder littleNum = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == big) {
                bigNum.append(9);
            } else {
                bigNum.append(s.charAt(i));
                littleNum.append(s.charAt(i));
            }
            if (s.charAt(i) == little) {
                littleNum.append(0);
            } else {
                littleNum.append(s.charAt(i));
            }
        }

        System.out.println(bigNum.toString() + " " + littleNum);
        return Integer.parseInt(bigNum.toString()) - Integer.parseInt(littleNum.toString());

    }


    //贪心 修改最小的两个数、最大的两个数、最大和最小的各一个数
    //修改的话，一定是改成和已存在的相等的数
    //所以min=0
    static public int minimizeSum(int[] nums) {
        Arrays.sort(nums);
        int a = help(Arrays.copyOfRange(nums, 2, nums.length));
        int b = help(Arrays.copyOfRange(nums, 0, nums.length - 2));
        int c = help(Arrays.copyOfRange(nums, 1, nums.length - 1));
        return Math.min(a, Math.min(b, c));
    }

    static public int help(int[] nums) {
        return nums[nums.length - 1] - nums[0];
    }


    //只判断2的幂即可
    static public int minImpossibleOR(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        for (int i = 0; i <= 31; i++) {
            if (!(set.contains(1 << i))) {
                return 1 << i;
            }
        }
        return -1;
    }


    //懒加载线段树
    public long[] handleQuery(int[] nums1, int[] nums2, int[][] queries) {
        long sum = 0;
        for (int num : nums2) {
            sum += num;
        }
        LazySegTree tree = new LazySegTree(nums1);
        ArrayList<Long> list = new ArrayList<>();
        for (int[] query : queries) {
            if (query[0] == 1) {
                tree.updateLazySegTree(0, 0, nums1.length - 1, query[1], query[2]);
            } else if (query[0] == 2) {
                sum += query[1] * tree.queryLazySegTree(0, 0, nums1.length - 1, 0, nums1.length - 1);
            } else {
                list.add(sum);
            }
        }
        return list.stream().mapToLong(v -> v).toArray();
    }

    class LazySegTree {
        private int[] tree, lazy;

        private LazySegTree(int[] arr) {
            tree = new int[4 * arr.length];
            lazy = new int[4 * arr.length];
            buildSegTree(arr, 0, 0, arr.length - 1);
        }

        private void buildSegTree(int[] arr, int treeIndex, int lo, int hi) {
            if (lo == hi) {
                tree[treeIndex] = arr[lo];
                return;
            }
            int mid = lo + (hi - lo) / 2;
            buildSegTree(arr, 2 * treeIndex + 1, lo, mid);
            buildSegTree(arr, 2 * treeIndex + 2, mid + 1, hi);
            tree[treeIndex] = tree[2 * treeIndex + 1] + tree[2 * treeIndex + 2];
        }

        private void updateLazySegTree(int treeIndex, int lo, int hi, int i, int j) {
            if (lazy[treeIndex] != 0) {
                tree[treeIndex] = (hi - lo + 1) - tree[treeIndex];
                if (lo != hi) {
                    lazy[2 * treeIndex + 1] ^= 1;
                    lazy[2 * treeIndex + 2] ^= 1;
                }
                lazy[treeIndex] = 0;
            }
            if (lo > hi || lo > j || hi < i) {
                return;
            }
            if (i <= lo && hi <= j) {
                tree[treeIndex] = (hi - lo + 1) - tree[treeIndex];
                if (lo != hi) {
                    lazy[2 * treeIndex + 1] ^= 1;
                    lazy[2 * treeIndex + 2] ^= 1;
                }
                return;
            }
            int mid = lo + (hi - lo) / 2;
            updateLazySegTree(2 * treeIndex + 1, lo, mid, i, j);
            updateLazySegTree(2 * treeIndex + 2, mid + 1, hi, i, j);
            tree[treeIndex] = tree[2 * treeIndex + 1] + tree[2 * treeIndex + 2];
        }

        private long queryLazySegTree(int treeIndex, int lo, int hi, int i, int j) {
            if (lo > j || hi < i) {
                return 0;
            }
            if (lazy[treeIndex] != 0) {
                tree[treeIndex] = (hi - lo + 1) - tree[treeIndex];
                if (lo != hi) {
                    lazy[2 * treeIndex + 1] ^= 1;
                    lazy[2 * treeIndex + 2] ^= 1;
                }
                lazy[treeIndex] = 0;
            }
            if (i <= lo && j >= hi) {
                return tree[treeIndex];
            }
            int mid = lo + (hi - lo) / 2;
            if (i > mid) {
                return queryLazySegTree(2 * treeIndex + 2, mid + 1, hi, i, j);
            } else if (j <= mid) {
                return queryLazySegTree(2 * treeIndex + 1, lo, mid, i, j);
            }
            long leftQuery = queryLazySegTree(2 * treeIndex + 1, lo, mid, i, mid);
            long rightQuery = queryLazySegTree(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);
            return leftQuery + rightQuery;
        }
    }

    public static void main(String[] args) {

    }
}
