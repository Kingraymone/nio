import connector.utils.Constant;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Test {
    public static void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public static int permutation(int[] nums, int s, int e, int target, int cur, StringBuffer sb) {
        if (s == e) {
            System.out.println(Arrays.toString(nums));
            if (++cur == target) {
                for (int i : nums) {
                    sb.append(i);
                }
            }
            return cur;
        } else {
            for (int i = s; i < e; i++) {
                if (i != s) {
                    swap(nums, i, s);
                }
                cur = permutation(nums, s + 1, e, target, cur, sb);
                if (cur == target) {
                    return cur;
                }
                if (i != s) {
                    swap(nums, i, s);
                }
            }
            return cur;
        }
    }

    public static boolean dictPermutation(int[] nums) {
        int point = nums.length - 1;
        int index = point;
        //从右向左查找第一个递增元素
        while (point > 0 && nums[point] <= nums[point - 1]) {
            point--;
        }
        if (point < 1) {
            return false;
        }
        //寻找比point位置小的第一个元素
        while (index > 0 && nums[index] < nums[point - 1]) {
            index--;
        }
        //交换
        swap(nums, point - 1, index);
        //逆序index之后的元素
        for (int i = point, j = nums.length - 1; i < j; i++, j--) {
            swap(nums, i, j);
        }
        return true;
    }

    public int findCircleNum2(int[][] M) {
        int count = 0;
        int[] visit = new int[M.length];
        for (int i = 0; i < visit.length; i++) {
            if (visit[i] == 0) {
                dfs(M, visit, i);
                count++;
            }
        }
        return count;
    }

    public void dfs(int[][] m, int[] v, int i) {
        for (int j = 0; j < m.length; j++) {
            if (m[i][j] == 1 && v[j] == 0) {
                v[j] = 1;
                dfs(m, v, j);
            }
        }
    }

    public static int findSet(int[] g, int i) {
        //如果当前不为根则当前节点连接到根
        return i == g[i] ? i : (g[i] = findSet(g, g[i]));
    }

    public static void union(int[] g, int i, int j) {
        int p1 = findSet(g, i);
        int p2 = findSet(g, j);
        //如果当前两个节点联通直接返回
        if (p1 == p2) {
            return;
        }
        g[p1] = p2;
        N--;
    }

    public static int N;

    public static int findCircleNum(int[][] M) {
        int len = M.length;
        //并查集返回连接图数
        //初始化组别
        int[] g = new int[len];
        for (int i = 0; i < len; i++) {
            g[i] = i;
        }
        int N = len;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (M[i][j] == 1) {
                    union(g, i, j);
                }
            }
        }
        return N;
    }
    public static int[][] merge(int[][] intervals) {
        int len = intervals.length;
        if (len < 1) {
            return intervals;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int cur = 0;
        int lastStart = intervals[0][0], lastEnd = intervals[0][1];
        for (int i = 1; i < len; i++) {
            int curStart = intervals[i][0];
            int curEnd = intervals[i][1];
            boolean judge = Math.max(lastStart, curStart) <= Math.min(lastEnd, curEnd);
            if (judge) {
                //合并
                lastEnd = Math.max(curEnd, lastEnd);
                lastStart = Math.min(curStart, lastStart);
            } else {
                intervals[cur][0] = lastStart;
                intervals[cur++][1] = lastEnd;
                lastStart = curStart;
                lastEnd = curEnd;
            }
        }
        boolean judge = (cur > 0 && intervals[cur - 1][1] != lastEnd) || (cur == 0);
        if (judge) {
            intervals[cur][0] = lastStart;
            intervals[cur++][1] = lastEnd;
        }
        int[][] result = new int[cur][2];
        for (int i = 0; i < result.length; i++) {
            result[i] = intervals[i];
        }
        return result;
    }
    static class A implements MapTest.EntryTest {


        @Override
        public void entry() {

        }
    }
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Properties properties = System.getProperties();
        properties.list(System.out);

    }
}
