package com.pkumar7.dp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Created by Pankaj Kumar on 20/December/2020
 */
class B {
    public static void main(String[] args) {
        B current = new B();
        current.findNumberOfLIS(new int[]{1, 3, 5, 4, 7});
    }

    /* 1771. Maximize Palindrome Length From Subsequences
     * https://leetcode.com/problems/maximize-palindrome-length-from-subsequences/
     * */
    public int longestPalindrome(String word1, String word2) {
        String s = word1 + word2;
        int n = s.length();
        int[][] dp = longestPalindromeSubseqDP(s);
        int res = 0;
        for (int i = 0; i < word1.length(); i++) {
            for (int j = word2.length() - 1; j >= 0; --j) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    res = Math.max(res, dp[i][j + word1.length()]);
                }
            }
        }
        return res;
    }

    public int[][] longestPalindromeSubseqDP(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp;
    }


    /*
     * https://leetcode.com/problems/maximum-score-from-performing-multiplication-operations/
     * */
    public int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length;
        int i = 0, j = n - 1;
        int m = multipliers.length;
        dp = new int[m + 1][m + 1];
        return dfs(nums, i, 0, multipliers);
    }

    int[][] dp;

    public int dfs(int[] nums, int left, int currIdx, int[] multiplier) {
        if (currIdx >= multiplier.length) return 0;
        int n = nums.length;

        int right = n - (currIdx - left) - 1;
        if (currIdx == multiplier.length - 1) {
            return Math.max(nums[left] * multiplier[currIdx], nums[right] * multiplier[currIdx]);
        }
        if (dp[left][currIdx] != 0) return dp[left][currIdx];
        int start = (nums[left] * multiplier[currIdx]) + dfs(nums, left + 1, currIdx + 1, multiplier);
        int end = (nums[right] * multiplier[currIdx]) + dfs(nums, left, currIdx + 1, multiplier);
        return dp[left][currIdx] = Math.max(start, end);
    }

    /* 413. Arithmetic Slices
     * https://leetcode.com/problems/arithmetic-slices/
     * */
    public int numberOfArithmeticSlices(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 0);
        if (n <= 2) return 0;
        for (int i = 2; i < arr.length; i++) {
            int first = arr[i - 1] - arr[i - 2];
            int second = arr[i] - arr[i - 1];
            if (first == second) {
                dp[i] = 1 + dp[i - 1];
            }
        }
        return Arrays.stream(dp).sum();
    }

    /* 1745. Palindrome Partitioning IV
     * https://leetcode.com/problems/palindrome-partitioning-iv/
     * */
    public boolean checkPartitioning(String s) {
        boolean[][] isPal = new boolean[2001][2001];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                isPal[i][j] = s.charAt(i) == s.charAt(j) && (i + 1 >= j || isPal[i + 1][j - 1]);
            }
        }
        for (int i = 2; i < s.length(); i++) {
            if(isPal[i][s.length() -1]){
                for (int j = 1; j < i; j++) {
                    if(isPal[j][i-1] && isPal[0][j-1]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* 673. Number of Longest Increasing Subsequence
    * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
    * */
    public int findNumberOfLIS(int[] nums) {
        int len[] = new int[nums.length];
        int cnt[] = new int[nums.length];

        int max_len = 0, res=  0;
        for(int i = 0; i < nums.length; i++){
            len[i] = 1;
            cnt[i] = 1;
            for(int j = 0; j < i; j++){
                if(nums[j] < nums[i]){
                    if(len[i] == len[j] + 1) {
                        cnt[i] += cnt[j];
                    }else if(len[i] < len[j] + 1){
                        len[i] = len[j] + 1;
                        cnt[i] = cnt[j];
                    }
                }
            }

            if(max_len == len[i]) {
                res += cnt[i];
            }else if(max_len < len[i]){
                max_len = len[i];
                res = cnt[i];
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/constrained-subsequence-sum/
    * 1425. Constrained Subsequence Sum
    * */
    public int constrainedSubsetSum(int[] nums, int k) {
        Deque<Integer> deq = new ArrayDeque<>();
        int maxSum = nums[0];
        for(int i = 0; i < nums.length; ++i){
            nums[i] += !deq.isEmpty() ? nums[deq.peekFirst()] : 0;
            maxSum = Math.max(maxSum, nums[i]);
            while(!deq.isEmpty() && deq.peekFirst() < i - k + 1){
                deq.pollFirst();
            }
            while(!deq.isEmpty() && nums[deq.peekLast()] < nums[i]){
                deq.pollLast();
            }
            if(nums[i] > 0){
                deq.offer(i);
            }
        }
        return maxSum;
    }
}
