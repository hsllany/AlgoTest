package com.company;

/**
 * Created by leeco on 17/6/30.
 */
public class RedBlackIntervalTree {

    protected Node root;

    public void insert(int min, int max) {
        insert(Int.of(min, max));
    }

    public void insert(Int t) {
        Node tNode = new Node();
        tNode.interval = t;
        tNode.color = Color.Red;

        insert(tNode);
    }

    public void insert(Node t) {

        t.interval.maxOfChild = t.interval.max;
        t.interval.minOfChild = t.interval.min;

        Node current = root;
        Node tParent = null;

        while (current != null) {
            tParent = current;
            current.interval.maxOfChild = max(current.interval.maxOfChild, t.interval.max);
            if (t.interval.compareTo(current.interval) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        t.parent = tParent;
        if (t.parent != null) {
            if (t.interval.compareTo(tParent.interval) < 0) {
                tParent.left = t;
            } else {
                tParent.right = t;
            }
        } else {
            root = t;
        }

        t.color = Color.Red;

        afterInsert(t);

    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
        // calculate the x and y's min and max
        calculateMinAndMax(x);
        calculateMinAndMax(y);
    }

    private void rightRotate(Node y) {
        Node x = y.left;

        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null) {
            this.root = x;
        } else {
            if (y == y.parent.right)
                y.parent.right = x;
            else
                y.parent.left = x;
        }

        x.right = y;
        y.parent = x;

        calculateMinAndMax(y);
        calculateMinAndMax(x);
    }

    private void calculateMinAndMax(Node node) {
        if (node.left != null) {
            node.interval.minOfChild = node.left.interval.minOfChild;
        } else {
            node.interval.minOfChild = node.interval.min;
        }

        calculateMax(node);
    }

    private void calculateMax(Node node) {
        int leftMax = node.left != null ? node.left.interval.maxOfChild : Integer.MIN_VALUE;
        int rightMax = node.right != null ? node.right.interval.maxOfChild : Integer.MIN_VALUE;
        node.interval.maxOfChild = max(leftMax, rightMax, node.interval.max);
    }

    private static int max(Integer... x) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < x.length; i++) {
            int xI = x[i];
            if (max < xI) {
                max = xI;
            }
        }

        return max;
    }

    private static int min(Integer... x) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < x.length; i++) {
            int xI = x[i];
            if (min > xI) {
                min = xI;
            }
        }

        return min;
    }


    private void afterInsert(Node node) {
        Node parent;
        Node grandParent;

        while ((parent = node.parent) != null && parent.color == Color.Red) {
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;
                if (uncle != null && uncle.color == Color.Red) {
                    uncle.color = Color.Black;
                    parent.color = Color.Black;
                    grandParent.color = Color.Red;
                    node = grandParent;
                    continue;
                }

                if (parent.right == node) {
                    Node tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                parent.color = Color.Black;
                grandParent.color = Color.Red;
                rightRotate(grandParent);
            } else {
                Node uncle = grandParent.left;
                if (uncle != null && uncle.color == Color.Red) {
                    uncle.color = Color.Black;
                    parent.color = Color.Black;
                    grandParent.color = Color.Red;
                    node = grandParent;
                    continue;
                }

                if (parent.left == node) {
                    rightRotate(parent);
                    Node tmp = parent;
                    parent = node;
                    node = tmp;
                }

                parent.color = Color.Black;
                grandParent.color = Color.Red;
                leftRotate(grandParent);
            }
        }
        root.color = Color.Black;
    }

    public int overlap(int i) {
        return overlap(i, root);
    }

    public static int overlap(int i, Node node) {
        Node current = node;

        int count = 0;

        if (current.interval.contain(i)) {
            count++;
        }

        if (current.left != null && i >= current.interval.minOfChild && i < current.left.interval.maxOfChild) {
            count += overlap(i, current.left);
        }

        if (i > current.interval.min && current.right != null && i >= current.right.interval.minOfChild && i < current.right.interval.maxOfChild) {
            count += overlap(i, current.right);
        }


        return count;
    }


    public static class Node {
        public Int interval;
        public Node left;
        public Node right;
        public Node parent;
        Color color;
    }

    private enum Color {
        Red, Black
    }

    public static class Int implements Comparable<Int> {

        public Int(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int max;
        public int min;
        public int maxOfChild;
        public int minOfChild;

        public boolean contain(int i) {
            return min <= i && i < max;
        }

        @Override
        public String toString() {
            return '[' + String.valueOf(min) + "," + String.valueOf(max) + "] with MaxInterval[" + String.valueOf(minOfChild) + "," + String.valueOf(maxOfChild) + "]";
        }

        public static Int of(int min, int max) {
            return new Int(min, max);
        }

        @Override
        public int compareTo(Int o) {
            return this.min - o.min;
        }
    }
}
