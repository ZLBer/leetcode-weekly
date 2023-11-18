package weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class wk371 {
    //暴力
 /*   public int maximumStrongPairXor(int[] nums) {
        int ans = -1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (Math.abs(nums[i] - nums[j]) <= Math.min(nums[i], nums[j])) {
                    ans = Math.max(nums[i] ^ nums[j], ans);
                }

            }
        }
        return ans;
    }*/

    //模拟
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        List<int[]> list = new ArrayList<>(access_times.size());
        for (int i = 0; i < access_times.size(); i++) {
            list.add(new int[]{i, Integer.parseInt(access_times.get(i).get(1))});
        }

        Collections.sort(list, (a, b) -> a[1] - b[1]);

        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String emp = access_times.get(list.get(i)[0]).get(0);
            int time = list.get(i)[1];
            if (!map.containsKey(emp)) map.put(emp, new ArrayList<>());
            map.get(emp).add(time);
        }

        List<String> res = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            List<Integer> value = entry.getValue();
            for (int i = 2; i < value.size(); i++) {
                if (help(value.get(i - 2), value.get(i)) <= 60) {
                    res.add(entry.getKey());
                    break;

                }
            }
        }
        return res;

    }


    int help(int a, int b) {
        if (a / 100 == b / 100) {
            return b - a;
        } else if ((b / 100) - (a / 100) == 1) {
            return b % 100 + (60 - a % 100);
        } else return Integer.MAX_VALUE;
    }


    //贪心
    public int minOperations(int[] nums1, int[] nums2) {
        int v1 = help(nums1, nums2, nums1[nums1.length - 1], nums2[nums2.length - 1]);
        int v2 = help(nums1, nums2, nums2[nums1.length - 1], nums1[nums2.length - 1]) + 1;
        int v = Math.min(v1, v2);
        if (v >= (int) 1e9 + 7) return -1;
        return v;
    }

    int help(int[] nums1, int[] nums2, int a, int b) {
        int ans = 0;
        for (int i = 0; i < nums1.length - 1; i++) {
            if (a >= nums1[i] && b >= nums2[i]) {

            } else if (b >= nums1[i] && a >= nums2[i]) {
                ans++;
            } else {
                return (int) 1e9 + 7;
            }
        }
        return ans;
    }


    public int maximumStrongPairXor(int[] nums) {
        Arrays.sort(nums);
        int left = 0, right = 0;
        int ans = 0;
        Trie t = new Trie();

        for (int i = 0; i < nums.length; i++) {
            t.insert(nums[i]);
            int x = nums[i];
            while (nums[left] < (x + 1) / 2) {
                t.remove(nums[left]);
                left++;
            }
            ans=Math.max(ans,t.maxXor(nums[i]));
        }
        return ans;
    }


    class Node {
        Node[] children = new Node[2];
        int cnt; // 子树大小
    }

    class Trie {
        private static final int HIGH_BIT = 19;
        private Node root = new Node();

        // 添加 val
        public void insert(int val) {
            Node cur = root;
            for (int i = HIGH_BIT; i >= 0; i--) {
                int bit = (val >> i) & 1;
                if (cur.children[bit] == null) {
                    cur.children[bit] = new Node();
                }
                cur = cur.children[bit];
                cur.cnt++; // 维护子树大小
            }
        }

        // 删除 val，但不删除节点
        // 要求 val 必须在 trie 中
        public void remove(int val) {
            Node cur = root;
            for (int i = HIGH_BIT; i >= 0; i--) {
                cur = cur.children[(val >> i) & 1];
                cur.cnt--; // 维护子树大小
            }
        }

        // 返回 val 与 trie 中一个元素的最大异或和
        // 要求 trie 不能为空
        public int maxXor(int val) {
            Node cur = root;
            int ans = 0;
            for (int i = HIGH_BIT; i >= 0; i--) {
                int bit = (val >> i) & 1;
                // 如果 cur.children[bit^1].cnt == 0，视作空节点
                // 判断有没有相异的节点
                if (cur.children[bit ^ 1] != null && cur.children[bit ^ 1].cnt > 0) {
                    ans |= 1 << i; //相异直接+1即可
                    bit ^= 1;
                }
                //这里省略了相同不加
                cur = cur.children[bit];
            }
            return ans;
        }
    }

}
