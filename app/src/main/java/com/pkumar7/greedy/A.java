package com.pkumar7.greedy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Created by Pankaj Kumar on 13/August/2020
 */
class A {
    public static void main(String[] args) {
        A curr = new A();
        boolean status = curr.isTransformable("4941","1494");
        System.out.println("status = " + status);
    }

    /* 1632. Rank Transform of a Matrix
     * https://leetcode.com/problems/rank-transform-of-a-matrix/
     * */
    public int[][] matrixRankTransform(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> graphs = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = matrix[i][j];
                graphs.putIfAbsent(val, new HashMap<Integer, ArrayList<Integer>>());
                HashMap<Integer, ArrayList<Integer>> graph = graphs.get(val);
                graph.putIfAbsent(i, new ArrayList<>());
                graph.putIfAbsent(~j, new ArrayList<>());
                graph.get(i).add(~j);
                graph.get(~j).add(i);
            }
        }
        int[][] seen = new int[m][n];
        TreeMap<Integer, List<List<int[]>>> value2Index = new TreeMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(seen[i][j] == 1)continue;
                seen[i][j] = 1;
                int val = matrix[i][j];
                HashMap<Integer, ArrayList<Integer>> g = graphs.get(val);
                List<int[]> points = new ArrayList<>();
                points.add(new int[]{i, j});
                Queue<int[]> q = new LinkedList<>();
                q.offer(new int[]{i, j});
                while (!q.isEmpty()){
                    int[] curr = q.poll();
                    int row = curr[0];
                    for(int col : g.get(row)){
                        if(seen[row][~col] == 0){
                            seen[row][~col] = 1;
                            points.add(new int[]{row, ~col});
                            q.offer(new int[]{row, ~col});
                        }
                    }
                    int currCol = curr[1];
                    for(int r : g.get(~curr[1])){
                        if(seen[r][currCol] == 0){
                            seen[r][currCol] = 1;
                            points.add(new int[]{r, currCol});
                            q.offer(new int[]{r, currCol});
                        }
                    }
                }
                value2Index.putIfAbsent(val, new ArrayList<>());
                value2Index.get(val).add(points);
            }
        }
        int[][] answer = new int[m][n];
        int[] rowMax = new int[m];
        int[] colMax = new int[n];
        for(int v : value2Index.keySet()){
            for(List<int[]> point : value2Index.get(v)){
                int rank = 1;
                for(int[] p : point){
                    rank = Math.max(rank, Math.max(rowMax[p[0]], colMax[p[1]]) + 1);
                }
                for(int[] p : point){
                    answer[p[0]][p[1]] = rank;
                    rowMax[p[0]] = Math.max(rowMax[p[0]], rank);
                    colMax[p[1]] = Math.max(colMax[p[1]], rank);
                }
            }
        }
        return answer;
    }

    /* 455. Assign Cookies
     * https://leetcode.com/problems/assign-cookies/
     * */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int pointerG = 0, pointerS = 0;
        while (pointerG < g.length && pointerS < s.length){
            if(g[pointerG] <= s[pointerS]){
                pointerG++;
            }
            pointerS++;
        }
        return pointerG;
    }

    /* 1599. Maximum Profit of Operating a Centennial Wheel
     * https://leetcode.com/problems/maximum-profit-of-operating-a-centennial-wheel/
     * */
    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        int rotations = 0;
        int cust_rem = 0;
        int total_cust = 0;
        int profit = Integer.MIN_VALUE;
        int res = 0;
        for (int i = 0; i < customers.length; i++) {
            cust_rem += customers[i];
            if(cust_rem >= 4){
                total_cust += 4;
                cust_rem -= 4;
            }else{
                total_cust += cust_rem;
                cust_rem = 0;
            }
            int curr_profit = (total_cust * boardingCost - ++rotations * runningCost);
            if(curr_profit > profit){
                res = rotations;
                profit = curr_profit;
            }
        }
        while (cust_rem > 0){
            if(cust_rem >= 4){
                total_cust += 4;
                cust_rem -= 4;
            }else{
                total_cust += cust_rem;
                cust_rem = 0;
            }
            int curr_profit = (total_cust * boardingCost - ++rotations * runningCost);
            if(curr_profit > profit){
                res = rotations;
                profit = curr_profit;
            }
        }
        if(profit > 0){
            return res;
        }
        return -1;
    }

    /*
    * 1585. Check If String Is Transformable With Substring Sort Operations
    * https://leetcode.com/problems/check-if-string-is-transformable-with-substring-sort-operations/
    * */
    public boolean isTransformable(String s, String t) {
        ArrayDeque<Integer>[] dq = new ArrayDeque[10];
        for (int i = 0; i < 10; i++) {
            dq[i] = new ArrayDeque<>();
        }
        for (int i = 0; i < s.length(); i++) {
            int x = s.charAt(i) - '0';
            dq[x].addLast(i);
        }
        for(char ch : t.toCharArray()){
            int x = ch - '0';
            if(dq[x].isEmpty()) return false;
            for (int i = 0; i < x; i++) {
                if(!dq[i].isEmpty() && dq[i].peekFirst() < dq[x].peekFirst()){
                    return false;
                }
            }
            dq[x].pollFirst();
        }
        return true;
    }

    /* 1567. Maximum Length of Subarray With Positive Product
     * https://leetcode.com/problems/maximum-length-of-subarray-with-positive-product/
     * */
    public int getMaxLen(int[] nums) {
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            while (i < n && nums[i] == 0) i++;
            int j = i;
            while (j < n && nums[j] != 0)j++;

            if(i < j){
                int ne = 0;
                int first = -1;
                int last = -1;
                for (int k = i; k < j; k++) {
                    if(nums[k] < 0) {
                        ne++;
                        if(first == -1) first = k;
                        last = k;
                    }
                }
                if(ne % 2 == 0){
                    res = Math.max(res, j - i);
                }else{
                    // Max from first neg to j or i to last neg
                    res = Math.max(res, last - i);
                    res = Math.max(res, j - (first + 1));
                }
            }
            i = j;
        }
        return res;
    }


    /* 1247. Minimum Swaps to Make Strings Equal
    * https://leetcode.com/problems/minimum-swaps-to-make-strings-equal/
    */
    public int minimumSwap(String s1, String s2) {
        int count1 = 0, count2 = 0;
        int n1 = s1.length();
        int n2 = s2.length();
        if(n1 != n2){
            return -1;
        }

        for(int i = 0; i < n1; i++){
            if(s1.charAt(i) != s2.charAt(i)){
                if(s1.charAt(i) == 'x'){
                    count1++;
                }else{
                    count2++;
                }
            }
        }
        if((count1 + count2) % 2 == 1) return -1;

        int res = 0;
        res += count1/2 + count2/2;
        if( count1 % 2 == 1 && count2 % 2 == 1 ) return res + 2;
        return res;
    }

    /*
    * 846. Hand of Straights
    * https://leetcode.com/problems/hand-of-straights/
    * Similar : https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
    * */
    public boolean isNStraightHand(int[] hand, int W) {
        if(hand == null || hand.length == 0 || (hand.length % W != 0) ){
            return false;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < hand.length; i++){
            map.put(hand[i], map.getOrDefault(hand[i], 0) + 1);
        }
        Arrays.sort(hand);

        for(int i = 0; i < hand.length; i++){
            if(map.get(hand[i]) > 0){
                for(int j = 0; j < W; j++){
                    int next = hand[i] + j;
                    if(!map.containsKey(next)){
                        return false;
                    }
                    if(map.get(next) < 0){
                        return false;
                    }

                    map.put(next, map.getOrDefault(next, 0) - 1);
                }
            }
        }
        return true;
    }
    /*
    * 1296. Divide Array in Sets of K Consecutive Numbers
    * https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
    * */
    public boolean isPossibleDivide(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k == 0)
            return false;

        if(nums.length % k != 0){
            return false;
        }
        Arrays.sort(nums);

        Map<Integer, Integer> freq = new HashMap<>();

        for(int ele : nums){
            freq.put(ele, freq.getOrDefault(ele, 0) + 1 );
        }

        for(int i = 0; i < nums.length; i++){
            if(freq.get(nums[i]) > 0){
                for(int j = 0; j < k; j++){
                    int next = nums[i] + j;
                    if(!freq.containsKey(next)){
                        return false;
                    }
                    if(freq.get(next) <= 0){
                        return false;
                    }
                    freq.put(next, freq.get(next) -1);
                }
            }
        }
        return true;
    }
}
