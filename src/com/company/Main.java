package com.company;

public class Main {

    public static void main(String[] args) {
        SegmentSegmentTree segmentTree = new SegmentSegmentTree();
//        segmentTree.insert(0, 3);
//        segmentTree.insert(5, 8);
//        segmentTree.insert(6, 10);
//        segmentTree.insert(8, 9);
//        segmentTree.insert(15, 23);
//        segmentTree.insert(16, 21);
//        segmentTree.insert(17, 19);
//        segmentTree.insert(19, 20);
//        segmentTree.insert(25, 30);
//        segmentTree.insert(26, 26);
        segmentTree.insert(0, 4);
        segmentTree.insert(5, 7);
        segmentTree.insert(2, 8);

        System.out.println("overlap 1 count is " + segmentTree.overlap(1));
        System.out.println("overlap 9 count is " + segmentTree.overlap(9));
        System.out.println("overlap 4 count is " + segmentTree.overlap(4));
        System.out.println("overlap 3 count is " + segmentTree.overlap(3));


    }

    public static class SegmentSegmentTree extends RedBlackSegmentTree {

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

        public void insert(int min, int max) {
            super.insert(Int.of(min, max));
        }
    }


    public static boolean overlap(int i) {
        return false;
    }
}
