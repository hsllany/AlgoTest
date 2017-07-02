package com.company;

public class Main {

    public static void main(String[] args) {
        RedBlackIntervalTree segmentTree = new RedBlackIntervalTree();
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
}
