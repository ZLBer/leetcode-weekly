package weekly;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class wk390 {
    //滑动窗口
    public int maximumLengthSubstring(String s) {
        int[] count = new int[26];
        int left = 0;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            count[c - 'a']++;

            while (count[c - 'a'] > 2) {
                count[s.charAt(left++) - 'a']--;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    //枚举k
    public int minOperations(int k) {
        int ans = k - 1;
        for (int i = 1; i <= k; i++) {
            int tmp = (i - 1);
            tmp += (k / i - 1) + (k % i > 0 ? 1 : 0);
            ans = Math.min(ans, tmp);
        }
        return ans;
    }


    //懒删除
    static public long[] mostFrequentIDs(int[] nums, int[] freq) {
        Map<Long, Long> map = new HashMap<>();
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>((a, b) -> -Long.compare(a[0], b[0]));
        long[] ans = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];
            Long count = map.getOrDefault(num, 0L) + freq[i];
            map.put(num, count);
            priorityQueue.add(new long[]{count, num});

            while (map.get(priorityQueue.peek()[1]) != priorityQueue.peek()[0]) {
                priorityQueue.poll();
            }
            ans[i] = priorityQueue.peek()[0];
        }
        return ans;
    }


    //字典树
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        Trie t = new Trie();
        for (int i = 0; i < wordsContainer.length; i++) {
            t.insert(wordsContainer[i], i);
        }
        int[] ans = new int[wordsQuery.length];
        for (int i = 0; i < wordsQuery.length; i++) {
            ans[i] = t.search(wordsQuery[i]);
        }
        return ans;
    }

    class Trie {
        Trie[] children;
        int maxIndex = -1;
        int minLen = Integer.MAX_VALUE;

        public Trie() {
            children = new Trie[26];
        }

        public void insert(String word, int wIndex) {
            Trie cur = this;
            if (word.length() < cur.minLen) {
                cur.minLen = word.length();
                cur.maxIndex = wIndex;
            }
            Trie[] branches = cur.children;
            char[] chs = word.toCharArray();
            for (int i = chs.length - 1; i >= 0; i--) {
                int index = chs[i] - 'a';
                if (branches[index] == null) {
                    branches[index] = new Trie();
                }
                cur = branches[index];
                branches = cur.children;
                if (word.length() < cur.minLen) {
                    cur.minLen = word.length();
                    cur.maxIndex = wIndex;
                }
            }
        }

        public int search(String word) {
            Trie cur = this;
            Trie[] branches = children;
            char[] chs = word.toCharArray();
            for (int i = chs.length - 1; i >= 0; i--) {
                int index = chs[i] - 'a';
                if (branches[index] != null) {
                    cur = branches[index];
                    branches = cur.children;
                } else {
                    return cur.maxIndex;
                }
            }
            return cur.maxIndex;
        }
    }


    public static void main(String[] args) {
        mostFrequentIDs(new int[]{5, 5, 3}, new int[]{2, -2, 1});
    }
}
