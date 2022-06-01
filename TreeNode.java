package nodes;

import data.Doctor;

public class TreeNode {
    private Doctor date;
    private int height;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Doctor date){
        this.date = date;
    }

    public Doctor getDate() {
        return date;
    }

    public int getHeight() {
        return height;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDate(Doctor date) {
        this.date = date;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
