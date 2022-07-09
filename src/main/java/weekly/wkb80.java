package weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class wkb80 {


       //ranking: 409 / 3949

    //简单题，就挨着检验每个条件即可
    public boolean strongPasswordCheckerII(String password) {

        boolean[] check = new boolean[6];
        String s = "!@#$%^&*()-+";
        check[0] = password.length() >= 8;
        check[5] = true;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                check[1] = true;
            } else if (Character.isUpperCase(c)) {
                check[2] = true;
            } else if (Character.isDigit(c)) {
                check[3] = true;
            } else {
                for (char c1 : s.toCharArray()) {
                    if (c1 == c) {
                        check[4] = true;
                        break;
                    }
                }
            }

            if (i > 0 && c == password.charAt(i - 1)) {
                check[5] = false;
            }

        }

        for (int i = 0; i < check.length; i++) {
            if (!check[i]) {
                System.out.println(i);
                return false;
            }
        }
        return true;
    }


    //中等题，二分找最小的符合条件的位置
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int[] res = new int[spells.length];
        Arrays.sort(potions);
        for (int i = 0; i < spells.length; i++) {
            int left = 0, right = potions.length - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (potions[mid] * ((long) spells[i]) >= success) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            res[i] = potions.length - left;
            //注意要判断下是不是真的符合条件
            if (potions[left] * ((long) spells[i]) < success) res[i]--;
        }
        return res;
    }


    //中等题，遍历
    public boolean matchReplacement(String s, String sub, char[][] mappings) {
        //记录替换对
        Map<Character, Set<Character>> map = new HashMap<>();
        for (char[] mapping : mappings) {
            if (!map.containsKey(mapping[0])) {
                map.put(mapping[0], new HashSet<>());
            }
            map.get(mapping[0]).add(mapping[1]);
        }

        //一次遍历子串
        for (int i = 0; i <= s.length() - sub.length(); i++) {
            String need = s.substring(i, i + sub.length());
            boolean flag = true;
            for (int j = 0; j < need.length(); j++) {
                char a = sub.charAt(j);
                char b = need.charAt(j);
                if (a == b) continue;
                else if (map.containsKey(a) && map.get(a).contains(b)) {
                    continue;
                } else {
                    flag = false;
                    break;
                }
            }

            if (flag) return true;
        }
        return false;
    }


    //困难题，考虑滑动窗口解法, 然后计算以i结尾的子串数量
    public long countSubarrays(int[] nums, long k) {
        long sum = 0;
        int left = 0;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            //必须包含位置i
            sum += nums[i];
            //往左滑动
            while (sum * (i - left + 1) >= k) {
                sum -= nums[left++];
            }
            //子串计数
            ans += (i - left + 1);
        }
        return ans;
    }
}
