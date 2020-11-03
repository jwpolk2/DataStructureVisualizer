package com.example.datastructurevisualizer;

import junit.framework.TestCase;

import java.util.ArrayList;

public class BinarySearchTreeTest extends TestCase {

    //Test that the number of children per node is 2
    public void testGetNumChildren() {
        BinarySearchTree tree1 = new BinarySearchTree();
        assertEquals(2, tree1.getNumChildren());
    }


    public void testGetTreeData() {
    }

    public void testSetTreeData() {
    }

    public void testGetRoot() {
    }

    public void testSetRoot() {
    }

    public void testTreeHeight() {
    }

    public void testIsBalancedNaive() {
    }

    public void testGetNodeHeight() {
    }

    public void testIsIsomorphic() {
    }

    public void testTestIsIsomorphic() {
    }

    public void testIsSymmetric() {
    }

    public void testTestIsSymmetric() {
    }

    public void testIsEqual() {
    }

    public void testReverse() {
    }

    public void testGetMaxWidth() {
    }

    public void testSetMaxWidth() {
    }

    //try finding something from an empty tree
    public void testFindEmpty() {
        BinarySearchTree tree1 = new BinarySearchTree();
        assertEquals(false, tree1.find(1));
    }

    //try calling find function with on a value that hasn't been inserted
    public void testFindnoninserted() {
        BinarySearchTree tree1 = new BinarySearchTree();
        tree1.insertNoAnim(5);
        tree1.insertNoAnim(6);
        assertEquals(false, tree1.find(4));
    }

    //try calling find function on a value that hasn't been inserted
    public void testFindinserted() {
        BinarySearchTree tree1 = new BinarySearchTree();
        tree1.insertNoAnim(5);
        tree1.insertNoAnim(6);
        assertEquals(true, tree1.find(5));
    }

    public void testRemoveNoAnim() {
        TreeVisualizer tree1 = new BinarySearchTree();
        tree1.insertNoAnim(5);
        tree1.insertNoAnim(6);
        //tree1.removeNoAnim(5);

        ArrayList<Integer> keyArrl = tree1.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    //test remove on empty tree to ensure no null pointer error
    //not realistic case because nodes must be in canvas for the option to delete
    public void testRemoveNoAnimEmpty() {
        TreeVisualizer tree1 = new BinarySearchTree();

        tree1.removeNoAnim(5);

        ArrayList<Integer> keyArrl = tree1.getAllKeys();
        assertEquals(0, keyArrl.size());
    }

    public void testRemoveAnim() {
    }

    public void testInsertNoAnim() {
    }

    public void testInsertAnim() {
    }
}