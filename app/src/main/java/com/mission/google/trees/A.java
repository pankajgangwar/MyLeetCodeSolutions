package com.mission.google.trees;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 12/August/2020
 */
class A {

    /*
    * 662. Maximum Width of Binary Tree
    * https://leetcode.com/problems/maximum-width-of-binary-tree/
    */
    public int widthOfBinaryTree(TreeNode root) {
        if(root == null) return 0;

        Queue<TreeNode> q = new LinkedList<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        map.put(root, 1);
        q.offer(root);
        int maxWidth = 0;
        while(!q.isEmpty()){
            int size = q.size();
            int start = 0;
            int end = 0;
            for(int i = 0; i < size; i++){
                TreeNode curr = q.poll();
                if(i == 0) start = map.get(curr);
                if(i == size - 1) end = map.get(curr);
                if(curr.left != null) {
                    q.offer(curr.left);
                    map.put(curr.left, 2*map.get(curr));
                }
                if(curr.right != null) {
                    q.offer(curr.right);
                    map.put(curr.right, 2*map.get(curr) + 1);
                }
            }
            int currMax = end - start + 1;
            maxWidth = Math.max(currMax, maxWidth);
        }
        return maxWidth;
    }

    /* 814. Binary Tree Pruning
    * https://leetcode.com/problems/binary-tree-pruning/
    * */
    public TreeNode pruneTree(TreeNode root) {
        if(root == null) return root;

        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);

        if(root.left == null && root.right == null && root.val == 0){
            return null;
        }
        return root;
    }

    /*
    * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
    * 889. Construct Binary Tree from Preorder and Postorder Traversal
    */
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        //return helper(pre, post);
        for (int i = 0; i < post.length; i++) {
            postMap.put(post[i], i);
        }
        return dfs(pre, 0, pre.length -1, post, 0, post.length -1);
    }

    HashMap<Integer, Integer> postMap = new HashMap<>();
    public TreeNode dfs(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd){
        if(preStart > preEnd) return null;
        TreeNode root = new TreeNode(pre[preStart]);
        if(preStart == preEnd) return root;
        int len = postMap.get(pre[preStart + 1]) - postStart + 1;
        root.left = dfs(pre, preStart + 1, preStart + len, post, postStart, postMap.get(pre[preStart + 1]) );
        root.right = dfs(pre, preStart + len + 1, preEnd, post, postMap.get(pre[preStart + 1]) + 1, postEnd - 1);
        return root;
    }

    int postIndex = 0, preIndex = 0;
    public TreeNode helper(int[] pre, int[] post){
        TreeNode root = new TreeNode(pre[preIndex++]);
        if(root.val != post[postIndex]){
            root.left = helper(pre, post);
        }
        if(root.val != post[postIndex]){
            root.right = helper(pre, post);
        }
        postIndex++;
        return root;
    }

    /* 331. Verify Preorder Serialization of a Binary Tree
    * https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/
    * */
    public boolean isValidSerialization(String preorder) {
        return sol1(preorder);
    }

    public boolean sol2(String preorder){
        String[] pre = preorder.split(",");
        int need = 1;
        for(String val : pre){
            if(need == 0) return false;
            need -= " #".indexOf(val);
        }
        return need == 0;
    }

    public boolean sol1(String preorder){
        String[] pre = preorder.split(",");
        int count = 1;
        for(String node : pre){
            if(count == 0) return false;
            if(!node.equals("#")) count += 1;
            else{
                count -= 1;
            }
        }
        return count == 0;
    }

    /* 776. Split BST
    * https://leetcode.com/problems/split-bst/
    * */
    public TreeNode[] splitBST(TreeNode root, int V) {
        return helper(root, V);
    }

    public TreeNode[] helper(TreeNode root, int v){
        TreeNode[] res;
        if(root == null) {
            return new TreeNode[]{null, null};
        }
        if(root.val <= v){
            res = helper(root.right, v);
            root.right = res[0];
            res[0] = root;
        }else {
            res = helper(root.left, v);
            root.left = res[1];
            res[1] = root;
        }
        return res;
    }

    /*
    1382. Balance a Binary Search Tree
    https://leetcode.com/problems/balance-a-binary-search-tree/
    */
    public TreeNode balanceBST(TreeNode root) {
        return null;
    }

    class NodeTree {
        public int val;
        public List<NodeTree> children;
        public NodeTree() {
            children = new ArrayList<NodeTree>();
        }
        public NodeTree(int _val) {
            val = _val;
            children = new ArrayList<NodeTree>();
        }
        public NodeTree(int _val, ArrayList<NodeTree> _children) {
            val = _val;
            children = _children;
        }
    }

    /*
    1490. Clone N-ary Tree
    https://leetcode.com/problems/clone-n-ary-tree/
    */
    public NodeTree cloneTree(NodeTree root) {
        if(root == null) return root;
        
        ArrayList<NodeTree> children = new ArrayList<>();
        NodeTree copy = new NodeTree(root.val, children);
        
        for(int i = 0; i < root.children.size(); i++){
            children.add(cloneTree(root.children.get(i)));
        }
        return copy;
    }

    
    class Node {
        int val;
        Node left;
        Node right;
        Node random;
        Node() {}
        Node(int val) { this.val = val; }
        Node(int val, Node left, Node right, Node random) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.random = random;
        }
    }
    class NodeCopy {
        int val;
        NodeCopy left;
        NodeCopy right;
        NodeCopy random;
        NodeCopy() {}
        NodeCopy(int val) { this.val = val; }
        NodeCopy(int val, NodeCopy left, NodeCopy right, NodeCopy random) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.random = random;
        }
    }

    /*
    1485. Clone Binary Tree With Random Pointer
    https://leetcode.com/problems/clone-binary-tree-with-random-pointer/
    */
    HashMap<Node, NodeCopy> visited = new HashMap<Node, NodeCopy>();
    public NodeCopy copyRandomBinaryTree(Node root) {
        if(root == null) return null;
        
        if(visited.containsKey(root)) return visited.get(root);
        
        NodeCopy cp = new NodeCopy(root.val, null, null, null);
        visited.put(root, cp);
        
        cp.left = copyRandomBinaryTree(root.left);
        cp.right = copyRandomBinaryTree(root.right);
        
        cp.random = copyRandomBinaryTree(root.random);
        
        return cp;
    }
}
