package tool;

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
