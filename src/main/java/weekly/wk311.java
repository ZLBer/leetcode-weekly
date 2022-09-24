package weekly;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class wk311 {

    //ranking: 1157 / 6710

    //可以遍历
  /*  public int smallestEvenMultiple(int n) {
        int ans = Integer.MAX_VALUE;
        for (int i = n; i <= n * 2; i++) {
            if (i % 2 == 0 && i % n == 0) {
                ans = Math.min(ans, i);
            }
        }
        return ans;
    }*/

    //也找规律吧
    public int smallestEvenMultiple(int n) {
      return  (n % 2 + 1) * n;
    }

    //滑动窗口
    public int longestContinuousSubstring(String s) {
        char[] chars = s.toCharArray();
        int max = 1;
        int ans = 1;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] - chars[i - 1] == 1) {
                ans++;
            } else {
                ans = 1;
            }
            max = Math.max(max, ans);
        }
        return max;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //层序遍历,注意只交换值即可
    public TreeNode reverseOddLevels(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<TreeNode> cur = new ArrayList<>();
            while (size-- > 0) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.add(poll.left);
                    cur.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                    cur.add(poll.right);
                }
            }
            if (step % 2 == 0) {
                for (int i = 0; i < cur.size()/2; i++) {
                    int a = cur.get(i).val;
                    int b = cur.get(cur.size() - i - 1).val;
                    cur.get(i).val=b;
                    cur.get(cur.size() - i - 1).val=a;
                }
            }
            step++;
        }
        return root;
    }


    class Trie {
        class Node {
            //前缀统计
            int preCount;
            Node[] childrens = new Node[26];
        }

        Node root;

        public Trie() {
            root = new Node();
        }

        public void insert(String word) {
            Node node = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (node.childrens[c - 'a'] == null) node.childrens[c - 'a'] = new Node();
                node = node.childrens[c - 'a'];
                node.preCount++;
            }
        }

        /**
         * Returns if the word is in the trie.
         */
        public int search(String word) {
            Node node = root;
            int sum=0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                node = node.childrens[c - 'a'];
                if (node == null) return 0;
                sum+=node.preCount;
            }
            return sum;
        }

    }

    //经典字典树
    public int[] sumPrefixScores(String[] words) {
        Trie t = new Trie();
        for (int i = 0; i < words.length; i++) {
            t.insert(words[i]);
        }
        int[] ans = new int[words.length];
        for (int i = 0; i < words.length; i++) {
           ans[i]=t.search(words[i]);
        }
        return ans;
    }



    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(-1));
    }
}
