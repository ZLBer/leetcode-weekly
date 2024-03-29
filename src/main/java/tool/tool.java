package tool;

import java.util.Arrays;

public class tool {
    //组合数 Cnm   m>n

    // m>=n
    public int comnination(int m, int n) {
        if (n == 0) return 1;
        long ans = 1;

        //都从小的开始 防止过早溢出
        for (int x = m - n + 1, y = 1; y <= n; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }


    //预处理组合数
    static final int MOD = (int) 1e9 + 7, MX = (int) 1e4 + 1, MX_K = 14;
    static int[][] c = new int[MX + MX_K][MX_K + 1]; // 组合数


    //预处理组合数
    static {
        c[0][0] = 1;
        for (int i = 1; i < MX + MX_K; ++i) {
            c[i][0] = 1;
            for (int j = 1; j <= Math.min(i, MX_K); ++j)
                c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
        }
    }

    long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

//    方法二：欧几里得算法 递推法(效率最高)

    private static long gcd2(long a, long b) {
        return (a == 0 ? b : gcd2(b % a, a));
    }


    //返回第一个不小于（即大于或等于）value元素的index
    public static int lowerBound(int[] nums, int l, int r, int target) {
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] >= target) r = m;
            else l = m + 1;
        }
        return l;
    }

    //返回第一个大于value元素的index
    public static int upperBound(int[] nums, int l, int r, int target) {
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] <= target) l = m + 1;
            else r = m;
        }
        return l;
    }


    public static void main(String[] args) {
        //求二进制子集  自己枚举
        int state = 13;
        for (int i = state; i > 0; i = (i - 1) & state) {

        }
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


    // lowerBound 返回最小的满足 nums[i] >= target 的 i
    // 如果数组为空，或者所有数都 < target，则返回 nums.length
    // 要求 nums 是非递减的，即 nums[i] <= nums[i + 1]

    // 闭区间写法
    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // 闭区间 [left, right]
        while (left <= right) { // 区间不为空
            // 循环不变量：
            // nums[left-1] < target
            // nums[right+1] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid + 1; // 范围缩小到 [mid+1, right]
            else
                right = mid - 1; // 范围缩小到 [left, mid-1]
        }
        return left; // 或者 right+1
    }

    // 左闭右开区间写法
    private int lowerBound2(int[] nums, int target) {
        int left = 0, right = nums.length; // 左闭右开区间 [left, right)
        while (left < right) { // 区间不为空
            // 循环不变量：
            // nums[left-1] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid + 1; // 范围缩小到 [mid+1, right)
            else
                right = mid; // 范围缩小到 [left, mid)
        }
        return left; // 或者 right
    }

    // 开区间写法
    private int lowerBound3(int[] nums, int target) {
        int left = -1, right = nums.length; // 开区间 (left, right)
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid; // 范围缩小到 (mid, right)
            else
                right = mid; // 范围缩小到 (left, mid)
        }
        return right; // 或者 left+1
    }



    //数位dp通用模板

    char s[];
    int memo[][];

    public int numDupDigitsAtMostN(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++)
            Arrays.fill(memo[i], -1); // -1 表示没有计算过
        return n - f(0, 0, true, false);
    }

    int f(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length)
            return isNum ? 1 : 0; // isNum 为 true 表示得到了一个合法数字
        if (!isLimit && isNum && memo[i][mask] != -1)
            return memo[i][mask];
        int res = 0;
        if (!isNum) // 可以跳过当前数位
            res = f(i + 1, mask, false, false);
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的数字都和 n 的一样，那么这一位至多填数字 s[i]（否则就超过 n 啦）
        for (int d = isNum ? 0 : 1; d <= up; ++d) // 枚举要填入的数字 d
            if ((mask >> d & 1) == 0) // d 不在 mask 中
                res += f(i + 1, mask | (1 << d), isLimit && d == up, true);
        if (!isLimit && isNum)
            memo[i][mask] = res;
        return res;
    }


    class SegTree {
        public SegNode build(int left, int right) {
            SegNode node = new SegNode(left, right);
            if (left == right) {
                return node;
            }
            int mid = (left + right) / 2;
            node.lchild = build(left, mid);
            node.rchild = build(mid + 1, right);
            return node;
        }

        public int count(SegNode root, int left, int right) {
            //没有交集
            if (left > root.hi || right < root.lo) {
                return 0;
            }
            //完整的一段
            if (left <= root.lo && root.hi <= right) {
                return root.add;
            }
            //向下递归
            return count(root.lchild, left, right) + count(root.rchild, left, right);
        }

        public void insert(SegNode root, int val) {
            root.add++;
            if (root.lo == root.hi) {
                return;
            }
            int mid = (root.lo + root.hi) / 2;
            if (val <= mid) {
                insert(root.lchild, val);
            } else {
                insert(root.rchild, val);
            }
        }
    }

    class SegNode {
        int lo, hi, add;
        SegNode lchild, rchild;

        public SegNode(int left, int right) {
            lo = left;
            hi = right;
            add = 0;
            lchild = null;
            rchild = null;
        }
    }

    //树状数组板子
    public class FenwickTree {

        /**
         * 预处理数组
         */
        private int[] tree;
        private int len;

        public FenwickTree(int n) {
            this.len = n;
            tree = new int[n + 1];
        }

        /**
         * 单点更新
         *
         * @param i     原始数组索引 i
         * @param delta 变化值 = 更新以后的值 - 原始值
         */
        public void update(int i, int delta) {
            // 从下到上更新，注意，预处理数组，比原始数组的 len 大 1，故 预处理索引的最大值为 len
            while (i <= len) {
                tree[i] += delta;
                i += lowbit(i);
            }
        }

        //区间更新
        void update(int x, int y, int k) {
            update(x, k);
            update(y + 1, -k);
        }

        /**
         * 查询前缀和
         *
         * @param i 前缀的最大索引，即查询区间 [0, i] 的所有元素之和
         */
        public int query(int i) {
            // 从右到左查询
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= lowbit(i);
            }
            return sum;
        }

        public int lowbit(int x) {
            return x & (-x);
        }
    }

    public class SegmentTree {
        private SegmentTreeNode root;

        public SegmentTree(int[] arr) {
            this.root = build(arr);
        }

        public SegmentTreeNode getRoot() {
            return this.root;
        }

        private SegmentTreeNode build(int[] arr) {
            if (arr == null || arr.length == 0) {
                return null;
            }

            return buildHelpler(arr, 0, arr.length - 1);
        }

        private SegmentTreeNode buildHelpler(int[] arr, int start, int end) {
            if (start > end) {
                return null;
            }

            SegmentTreeNode root = new SegmentTreeNode(start, end);
            if (start == end) {
                root.min = arr[start];
                root.max = arr[start];
                root.sum = arr[start];
                return root;
            }

            int mid = start + (end - start) / 2;
            root.left = buildHelpler(arr, start, mid);
            root.right = buildHelpler(arr, mid + 1, end);
            root.min = Math.min(root.left.min, root.right.min);
            root.max = Math.max(root.left.max, root.right.max);
            root.sum = root.left.sum + root.right.sum;
            return root;
        }

        public int queryMin(int start, int end) {
            return queryMin(this.root, start, end);
        }

        private int queryMin(SegmentTreeNode root, int start, int end) {
            if (root == null) {
                return 0;
            }

            // case 1: search range is same with the range of root node
            if (root.start == start && root.end == end) {
                return root.min;
            }

            int mid = root.start + (root.end - root.start) / 2;
            if (end <= mid) {
                // case 2: search range is in the range of left child node
                return queryMin(root.left, start, end);
            } else if (start > mid) {
                // case 3: search range is in the range of right child node
                return queryMin(root.right, start, end);
            } else {
                //case 4: search range crosses both left and right children
                int leftmin = queryMin(root.left, start, mid);
                int rightmin = queryMin(root.right, mid + 1, end);
                return Math.min(leftmin, rightmin);
            }
        }

        public int queryMax(int start, int end) {
            return queryMax(this.root, start, end);
        }

        public int queryMax(SegmentTreeNode root, int start, int end) {
            if (root == null) {
                return 0;
            }

            // case 1: search range is same with the range of root node
            if (root.start == start && root.end == end) {
                return root.max;
            }

            int mid = root.start + (root.end - root.start) / 2;
            if (end <= mid) {
                // case 2: search range is in the range of left child node
                return queryMax(root.left, start, end);
            } else if (start > mid) {
                // case 3: search range is in the range of right child node
                return queryMax(root.right, start, end);
            } else {
                //case 4: search range crosses both left and right children
                int leftmax = queryMax(root.left, start, mid);
                int rightmax = queryMax(root.right, mid + 1, end);
                return Math.max(leftmax, rightmax);
            }
        }

        public int querySum(int start, int end) {
            return querySum(this.root, start, end);
        }

        public int querySum(SegmentTreeNode root, int start, int end) {
            if (root == null) {
                return 0;
            }

            // case 1: search range is same with the range of root node
            if (root.start == start && root.end == end) {
                return root.sum;
            }

            int mid = root.start + (root.end - root.start) / 2;
            if (end <= mid) {
                // case 2: search range is in the range of left child node
                return querySum(root.left, start, end);
            } else if (start > mid) {
                // case 3: search range is in the range of right child node
                return querySum(root.right, start, end);
            } else {
                //case 4: search range crosses both left and right children
                int leftsum = querySum(root.left, start, mid);
                int rightsum = querySum(root.right, mid + 1, end);
                return leftsum + rightsum;
            }
        }

        public void modify(int index, int value) {
            modify(this.root, index, value);
        }

        private void modify(SegmentTreeNode root, int index, int value) {
            if (root == null) {
                return;
            }

            if (root.start == root.end && root.start == index) {
                root.min = value;
                root.max = value;
                root.sum = value;
                return;
            }

            int mid = root.start + (root.end - root.start) / 2;
            if (index <= mid) {
                modify(root.left, index, value);
            } else {
                modify(root.right, index, value);
            }

            root.min = Math.min(root.left.min, root.right.min);
            root.max = Math.max(root.left.max, root.right.max);
            root.sum = root.left.sum + root.right.sum;
        }
    }

    public class SegmentTreeNode {
        public int start, end;
        public int max, min, sum; // You can add additional attributes
        public SegmentTreeNode left, right;

        public SegmentTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
            this.left = null;
            this.right = null;
        }
    }
}
