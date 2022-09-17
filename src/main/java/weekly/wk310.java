package weekly;

import tool.tool;

import java.util.Arrays;
import java.util.PriorityQueue;

public class wk310 {

    //ranking: 243 / 6081


    //简单题，排序+统计连续相等的数字数目
    //hash表也可以
    public int mostFrequentEven(int[] nums) {
        Arrays.sort(nums);
        int pre = nums[0];
        int count = 1;
        int ans = -1;
        int c = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == pre) {
                count++;
            } else {
                if (pre % 2 == 0 && count > c) {
                    c = count;
                    ans = pre;
                }
                pre = nums[i];
                count = 1;
            }
        }
        if (pre % 2 == 0 && count > c) {
            c = count;
            ans = pre;
        }
        return ans;
    }


    //贪心，统计次数,碰到一样大的就重新计数
    public int partitionString(String s) {
        int[] count = new int[26];
        int ans = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count[c - 'a'] > 0) {
                ans++;
                count = new int[26];
            }
            count[c - 'a']++;
        }
        return ans;
    }


    //贪心+排序
    //按照左区间+右区间从小到大排序
    //然后用最小堆记录，每次取最小的一个，若不满足就要加新的，满足就弹出并加新的
    //也可以差分数组
    public int minGroups(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i < intervals.length; i++) {
            if (priorityQueue.isEmpty() || priorityQueue.peek() < intervals[i][0]) {
                priorityQueue.poll();
            }
            priorityQueue.add(intervals[i][1]);
        }
        return priorityQueue.size();
    }


    //线段树优化, 区间查询最大值
    public int lengthOfLIS(int[] nums, int k) {
        if (nums.length == 0) return 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }
        int[] arr = new int[max + 1];
        int ans=1;
        SegmentTree segmentTree = new SegmentTree(arr);
        for (int i = 0; i < nums.length; i++) {
            int left = Math.max(0, nums[i] - k);
            int right = Math.max(0, nums[i] - 1);
            int len = segmentTree.queryMax(left, right);
            segmentTree.modify(nums[i],len+1);
            ans=Math.max(ans,len+1);
        }
        return ans;
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
    

}
