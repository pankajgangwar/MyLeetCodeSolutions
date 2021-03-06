package com.pkumar7.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

import javax.sound.sampled.Line;

/**
 * Created by Pankaj Kumar on 19/August/2020
 */
class A {
    public static void main(String[] args) {
        A cur = new A();
        String res = cur.convertToTitle(52);
        System.out.println("num = " + res);
    }

    /*
    * https://leetcode.com/problems/count-good-numbers/
    * 1922. Count Good Numbers
    * */
    public int countGoodNumbers(long n) {
        int ans = 0;
        int mod = (int)1e9 + 7;
        long evenPos = n / 2;
        long oddPos = n / 2;
        if(n % 2 != 0){
            evenPos += 1;
        }
        ans = (int)((power(5, evenPos) % mod) * (power(4, oddPos) % mod) % mod);
        return ans;
    }


    public long power(long x, long y) {
        long temp;
        int mod = (int)1e9 + 7;
        if (y == 0){
            return 1;
        }
        temp = power(x, y / 2) % mod;
        if (y % 2 == 0){
            return temp * temp;
        } else {
            if (y > 0)
                return x * temp * temp;
            else
                return (temp * temp) / x;
        }
    }

    /* 1904. The Number of Full Rounds You Have Played
       : Time
    * https://leetcode.com/problems/the-number-of-full-rounds-you-have-played/
    * */
    public int numberOfRounds(String startTime, String finishTime) {
        String[] st = startTime.split(":");
        int shr = Integer.parseInt(st[0]);
        int smin = Integer.parseInt(st[1]);

        String[] et = finishTime.split(":");
        int ehr = Integer.parseInt(et[0]);
        int emin = Integer.parseInt(et[1]);
        int start = 60*shr + smin;
        int finish = 60*ehr + emin;
        if(start > finish) {
            finish += 60*24;
        }
        return Math.max(0 , finish / 15 - (start + 14) / 15);
    }

    /*
     * https://leetcode.com/problems/get-biggest-three-rhombus-sums-in-a-grid/
     * */
    public int[] getBiggestThree(int[][] g) {
        TreeSet<Integer> s = new TreeSet<>(Collections.reverseOrder());
        for (int i = 0; i < g.length; ++i)
            for (int j = 0; j < g[0].length; ++j)
                for (int sz = 0; i + sz < g.length && i - sz >= 0 && j + 2 * sz < g[0].length; ++sz) {
                    int x = i, y = j, r_sum = 0;
                    do r_sum += g[x++][y++]; while (x < i + sz);
                    if (sz > 0) {
                        do r_sum += g[x--][y++]; while (y < j + 2 * sz);
                        do r_sum += g[x--][y--]; while (x > i - sz);
                        do r_sum += g[x++][y--]; while (x < i);
                    }
                    s.add(r_sum);
                }

        return s.stream().limit(3).mapToInt(Integer::intValue).toArray();

    }

    /* 1860. Incremental Memory Leak
    * https://leetcode.com/problems/incremental-memory-leak/
    * */
    public int[] memLeak(int memory1, int memory2) {
        int second = 1;
        while (memory1 >= second || memory2 >= second){
            if(memory1 >= memory2){
                memory1 -= second;
            }else{
                memory2 -= second;
            }
            second++;
        }
        return new int[]{second, memory1, memory2};
    }

    /* 1688. Count of Matches in Tournament
     * https://leetcode.com/problems/count-of-matches-in-tournament/
     * */
    public int numberOfMatches(int n) {
        int res = 0;
        while (n > 1){
            if(n % 2 == 0){
                res += n / 2;
                n = n / 2;
            }else{
                res += (n - 1) / 2;
                n = ((n - 1) / 2) + 1;
            }
        }
        return res;
    }


    /* 910. Smallest Range II
     * https://leetcode.com/problems/smallest-range-ii/
     * */
    public int smallestRangeII(int[] arr, int k) {
        int n = arr.length;
        if(n == 1 ) return 0;
        Arrays.sort(arr);
        int res = arr[n - 1] - arr[0];
        int min = arr[0] + k, max = arr[n - 1] - k;
        for (int i = 0; i < n - 1; i++) {
            int curr_min = Math.min(min, arr[i + 1] - k);
            int curr_max = Math.max(max, arr[i] + k);
            res = Math.min(res, curr_max - curr_min);
        }
        return res;
    }

    /*
     * 1680. Concatenation of Consecutive Binary Numbers
     * https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/
     */
    public int concatenatedBinary(int n) {
        return solution3(n);
    }

    public int solution3(int n) {
        long res = 0;
        int mod = (int)1e9+7;
        for (int i = 1; i <= n; i++) {
            String binary = Integer.toBinaryString(i);
            res = res * (int)Math.pow(2, binary.length()) + i;
            res = res % mod;
        }
        return (int)res;
    }

    public int solution2(int n){
        int res = 0;
        int mod = (int)1e9+7;
        StringBuilder out = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            String binary  = Integer.toBinaryString(i);
            for (int j = 0; j < binary.length(); j++) {
                res = res * 2 + binary.charAt(j) - '0';
                res = res % mod;
            }
        }
        return res;
    }

    public int solution1(int n){
        int mod = (int)1e9+7;
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = (sum * (int)Math.pow(2, Integer.toBinaryString(i).length()) + i) % mod;
        }
        return (int)sum;
    }

    /* 168. Excel Sheet Column Title
    * https://leetcode.com/problems/excel-sheet-column-title/
    * Revisit
    * */
    public String convertToTitle(int n) {
        //return iterative(n);
        return dfs(n);
    }
    public String dfs(int n){
        return n == 0 ? "" : dfs(--n / 26) + (char)('A' + (n % 26));
    }
    public String iterativeI(int n) {
        char[] arr = new char[26];
        char ch = 'A';
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ch++;
        }
        StringBuilder out = new StringBuilder();
        while (n > 0){
            n = n - 1;
            out.append(arr[n % 26]);
            n = n >> 1;
        }
        return out.toString();
    }

    public String iterative(int n) {
        HashMap<Integer, Character> map = new HashMap<>();
        char ch = 'A';
        for (int i = 1; i <= 26 ; i++) {
            map.put(i, ch++);
        }
        StringBuilder builder = new StringBuilder();
        while (n > 0){
            n--;
            builder.insert(0, map.get(n % 26 + 1));
            n = n / 26;
        }
        return builder.toString();
    }

    /* 640. Solve the Equation
    * https://leetcode.com/problems/solve-the-equation/
    * */
    public String solveEquation(String equation) {
        String no_sol = "No solution";
        String infinite_sol = "Infinite solutions";
        if(!equation.contains("=")){
            return no_sol;
        }
        String[] arr = equation.split("=");
        int[] lhs = evaluateExpression(arr[0]);
        int[] rhs = evaluateExpression(arr[1]);
        lhs[0] -= rhs[0];
        lhs[1] = rhs[1] - lhs[1];
        if(lhs[0] == 0 && lhs[1] == 0) return infinite_sol;
        if(lhs[0] == 0) return no_sol;
        return "x=" + lhs[1] / lhs[0];
    }

    public int[] evaluateExpression(String exp){
        String[] tokens = exp.split("(?=[-+])");
        int[] res = new int[2];
        for (String token : tokens){
            if(token.equals("+x") || token.equals("x")) res[0] += 1;
            else if(token.equals("-x")) res[0] -= 1;
            else if(token.contains("x")) res[0] += Integer.parseInt(token.substring(0, token.indexOf("x")));
            else res[1] += Integer.parseInt(token);
        }
        return res;
    }

    /* 1180. Count Substrings with Only One Distinct Letter
     * https://leetcode.com/problems/count-substrings-with-only-one-distinct-letter/
     * */
    public int countLetters(String s) {
        int j = 0;
        int res = 0;
        for (int i = 0; i < s.length();) {
            j = i;
            while (j < s.length() && s.charAt(i) == s.charAt(j)){
                j++;
            }
            int n = j - i;
            res += (n * (n + 1) / 2);
            i = j;
        }
        return res;
    }

    /* 1610. Maximum Number of Visible Points
     * https://leetcode.com/problems/maximum-number-of-visible-points/
     */
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int overlapWithSelf = 0;
        List<Double> angles = new ArrayList<>();
        for(List<Integer> point : points){
            if(location.get(0) == point.get(0) && location.get(1) == point.get(1)){
                overlapWithSelf++;
            }else {
                angles.add(getAngle(location, point));
                angles.add(getAngle(location, point) + 360);
            }
        }
        Collections.sort(angles);
        int res = 0;
        Queue<Double> q = new LinkedList<>();
        for(Double ang : angles){
            q.offer(ang);
            while (ang - q.peek() > angle){
                q.poll();
            }
            res = Math.max(res, q.size());
        }
        return res + overlapWithSelf;
    }

    private double getAngle(List<Integer> loc, List<Integer> point) {
        double angle = Math.toDegrees(Math.atan2(loc.get(1) - point.get(1), loc.get(0) - point.get(0)));
        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    /* 263. Ugly Number
    * https://leetcode.com/problems/ugly-number/
    */
    public boolean isUgly(int n) {
        return isUglyIterative(n);
    }
    public boolean isUglyIterative(int n) {
        if(n <= 0) return false;
        for(int i = 2; i <= 5; i++){
            while(n % i == 0){
                n = n / i;
            }
        }
        return n == 1;
    }

    public boolean isUglyRecursive(int n) {
        if(n <= 0) return false;
        if(n == 1) return true;
        if(n % 2 == 0){
            return isUgly(n / 2);
        }else if(n % 3 == 0){
            return isUgly(n / 3);
        }else if(n % 5 == 0){
            return isUgly(n / 5);
        }
        return false;
    }

    /* 592. Fraction Addition and Subtraction
    * https://leetcode.com/problems/fraction-addition-and-subtraction/
    * */
    public String fractionAddition(String exp) {
        String[] fracs = exp.split("(?=[-+])");
        List<Long> num = new ArrayList<>();
        List<Long> den = new ArrayList<>();
        for (int i = 0; i < fracs.length; i++) {
            String[]frac = fracs[i].split("/");
            long a = Long.parseLong(frac[0]);
            long b = Long.parseLong(frac[1]);
            num.add(a);
            den.add(b);
        }

        long lcm = (long)den.get(0);
        for(int j = 1; j < den.size(); j++){
            long second = (long)den.get(j);
            lcm  = lcm(lcm, second);
        }
        long denominator = lcm;
        long numerator = 0;
        for(int j = 0; j < num.size(); j++){
            long b = denominator / (long)den.get(j);
            numerator = (numerator + num.get(j) * b);
        }
        long commonFactor = gcd(Math.abs(numerator), denominator);
        numerator = numerator / commonFactor;
        denominator = denominator /commonFactor;
        StringBuilder out = new StringBuilder();
        out.append(numerator);
        out.append("/");
        out.append(denominator);
        return out.toString();
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        if(a == 0){
            return b;
        }
        return gcd(b % a, a);
    }

    /* 1041. Robot Bounded In Circle
    * https://leetcode.com/problems/robot-bounded-in-circle/
    * */
    public boolean isRobotBounded(String instructions) {
        //return isCircular(instructions);
        return isRobotBounded1(instructions);
    }

    public boolean isRobotBounded1(String instructions) {
        int[][] dirs = new int[][]{ {0, 1}, {1, 0}, {0, -1}, { -1, 0} };
        int x = 0, y = 0;
        char[] instr = instructions.toCharArray();
        int dir = 0;
        for (char ch : instr) {
            if (ch == 'R') {
                dir = (dir + 1) % 4;
            } else if (ch == 'L') {
                dir = (dir + 3) % 4;
            } else {
                x += dirs[dir][0];
                y += dirs[dir][1];
            }
        }
        return x == 0 && y == 0 || dir > 0;// If robot is not facing north
    }

    public boolean isCircular(String instructions) {
        char[] path = instructions.toCharArray();
        int x = 0, y = 0;
        int dir = 0;
        int run = 4;
        while(run-- > 0){
            for (int i=0; i < path.length; i++) {
                char move = path[i];
                if (move == 'R')
                    dir = (dir + 1) % 4;
                else if (move == 'L')
                    dir = (dir + 3) % 4;
                else {
                    if (dir == 0)
                        y++;
                    else if (dir == 1)
                        x++;
                    else if (dir == 2)
                        y--;
                    else
                        x--;
                }
            }
            run--;
        }
        return (x == 0 && y == 0);
    }
}
