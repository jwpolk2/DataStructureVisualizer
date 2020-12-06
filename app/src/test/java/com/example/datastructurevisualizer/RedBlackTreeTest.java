package com.example.datastructurevisualizer;

import junit.framework.TestCase;

import java.util.ArrayList;

import static com.example.datastructurevisualizer.RedBlackTree.BLACK;
import static com.example.datastructurevisualizer.RedBlackTree.RED;

public class RedBlackTreeTest extends TestCase {
    private RedBlackTree rbt = new RedBlackTree();
    private Node testRoot = new Node(5, 2);

    public void testColorValue_black() {
        assertEquals(0, testRoot.value);
    }

    public void testColorValue_red() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        Node testNode = rbt.getNode(2);
        assertEquals(1, testNode.value);
    }

    public void testGetNumChildren_empty() {
        assertEquals(2, rbt.getNumChildren());
    }

    public void testGetNumChildren_filled() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);

        assertEquals(2, rbt.getNumChildren());
    }



    public void testInsertNoAnim() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testInsertNoAnim_duplicate() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(1);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    public void testInsertAnim() {
        rbt.insertAnim(1);
        rbt.insertAnim(2);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testInsertManyRemoveNoAnim(){
        rbt.insertAnim(5);
        rbt.insertAnim(3);
        rbt.insertAnim(1);
        rbt.insertAnim(2);
        rbt.insertAnim(6);
        rbt.insertAnim(8);

        rbt.insertAnim(14);
        rbt.insertAnim(16);
        rbt.insertAnim(10);
        rbt.insertAnim(4);
        rbt.insertAnim(9);
        rbt.insertAnim(12);

        rbt.removeNoAnim(1);
        rbt.removeNoAnim(8);
        rbt.removeNoAnim(5);
        rbt.removeNoAnim(4);
        rbt.removeNoAnim(10);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertManyRemoveAnim(){
        rbt.insertAnim(5);
        rbt.insertAnim(3);
        rbt.insertAnim(1);
        rbt.insertAnim(2);
        rbt.insertAnim(6);
        rbt.insertAnim(8);

        rbt.insertAnim(14);
        rbt.insertAnim(16);
        rbt.insertAnim(10);
        rbt.insertAnim(4);
        rbt.insertAnim(9);
        rbt.insertAnim(12);

        rbt.removeAnim(1);
        rbt.removeAnim(8);
        rbt.removeAnim(5);
        rbt.removeAnim(4);
        rbt.removeAnim(10);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertNoAnimManyRemoveAnim(){
        rbt.insertNoAnim(5);
        rbt.insertNoAnim(3);
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        rbt.insertNoAnim(6);
        rbt.insertNoAnim(8);

        rbt.insertNoAnim(14);
        rbt.insertNoAnim(16);
        rbt.insertNoAnim(10);
        rbt.insertNoAnim(4);
        rbt.insertNoAnim(9);
        rbt.insertNoAnim(12);

        rbt.removeAnim(1);
        rbt.removeAnim(8);
        rbt.removeAnim(5);
        rbt.removeAnim(4);
        rbt.removeAnim(10);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertAnim_duplicate() {
        rbt.insertAnim(1);
        rbt.insertAnim(1);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    public void testInsertionRelabelAnim_root() {
        assertEquals(BLACK, testRoot.value);
    }

    public void testInsertionRelabelAnim_root_after_insert() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        Node testNode = rbt.getNode(1);
        assertEquals(BLACK, testNode.value);
    }

    public void testInsertionRelabelAnim_child_after_insert() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        Node testNode = rbt.getNode(2);
        assertEquals(RED, testNode.value);
    }


    public void testRemoveNoAnim_exists() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        rbt.removeNoAnim(1);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    public void testRemoveNoAnim_nonExistant() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        rbt.removeNoAnim(3);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testRemoveAnim_exists() {
        rbt.insertNoAnim(1);
        rbt.insertNoAnim(2);
        rbt.removeAnim(1);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    public void testRemoveAnim_nonExistant() {
        rbt.insertAnim(1);
        rbt.insertAnim(2);
        rbt.removeAnim(3);

        ArrayList<Integer> keyArrl = rbt.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

//    public void testSwapColors() {
//        rbt.insertNoAnim(1);
//        Node testNode1 = rbt.getNode(1);
//        int tempColor =
//    }
}