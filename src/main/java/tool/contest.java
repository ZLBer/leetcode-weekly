package tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class contest {

    int[] help(int[] temp) {
        int[] ans = new int[temp.length];
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] > temp[i - 1]) {
                ans[i] = 1;
            } else if (temp[i] < temp[i - 1]) {
                ans[i] = -1;
            } else {
                ans[i] = 0;
            }
        }
        return ans;
    }

    public int temperatureTrend(int[] temperatureA, int[] temperatureB) {
        int[] a = help(temperatureA);
        int[] b = help(temperatureB);
        int ans = 0;
        int count = 0;
        for (int i = 1; i < temperatureA.length; i++) {
            System.out.println(temperatureA[i] + " " + temperatureB[i]);
            if (a[i] == b[i]) {
                count++;
            } else {
                count = 0;
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }

    public int transportationHub(int[][] path) {
        int[][] dp = new int[1000 + 1][2];
        Set<Integer> set = new HashSet<>();

        for (int[] ints : path) {
            dp[ints[0]][0]++;
            dp[ints[1]][1]++;
            set.add(ints[0]);
            set.add(ints[1]);
        }

        for (int i = 0; i < dp.length; i++) {
            if (dp[i][1] == set.size() - 1 && dp[i][0] == 0) {
                return i;
            }
        }
        return -1;

    }

    public int[][] ballGame(int num, String[] plate) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < plate.length; i++) {
            for (int j = 0; j < plate[i].length(); j++) {
                if (plate[i].charAt(j) == 'O') {
                    list.add(new int[]{i, j});
                }
            }
        }
        int[][] moves = new int[][]{
                {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };
        int[][][][] v = new int[plate.length][plate[0].length()][3][3];

        List<int[]> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int[] begin = list.get(i);
            for (int[] move : moves) {

                int[] a = check(true, begin[0], begin[1], move[0], move[1], plate, num, v);
                if (a != null) {
                    if ((a[0] == 0 && a[1] == 0) || (a[0] == 0 && a[1] == plate[0].length() - 1) || (a[0] == plate.length - 1 && a[1] == 0) || (a[0] == plate.length - 1 && a[1] == plate[0].length() - 1)) {
                        continue;
                    }
                    temp.add(a);
                }
            }
        }
        int[][] res = new int[temp.size()][2];
        for (int i = 0; i < temp.size(); i++) {
            res[i] = temp.get(i);
        }
        return res;

    }

    int[] check(boolean init, int x, int y, int up, int left, String[] plate, int num, int[][][][] visited) {

        if (num < 0) {
            return null;
        }
        if (visited[x][y][up + 1][left + 1]>=num) {
            return null;
        }
        visited[x][y][up + 1][left + 1] = num;


        //逆时针
        if (plate[x].charAt(y) == 'E') {
            if (up != 0) {
                if (up > 0) {
                    left = 1;
                } else {
                    left = -1;
                }
                up = 0;
            } else {
                if (left > 0) {
                    up = -1;
                } else {
                    up = 1;
                }
                left = 0;
            }
            //顺时针
        } else if (plate[x].charAt(y) == 'W') {
            if (up != 0) {
                if (up > 0) {
                    left = -1;
                } else {
                    left = 1;
                }
                up = 0;
            } else {
                if (left > 0) {
                    up = 1;
                } else {
                    up = -1;
                }
                left = 0;
            }
        } else if (!init && plate[x].charAt(y) == 'O') {
            return null;
        }

        int nx = x + up;
        int ny = y + left;
        if (nx < 0 || nx >= plate.length || ny < 0 || ny >= plate[0].length()) {
            if (plate[x].charAt(y) == '.') {
                return new int[]{x, y};
            } else {
                return null;
            }
        }

        return check(false, nx, ny, up, left, plate, num - 1, visited);
    }

}
