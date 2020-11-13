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

//    public void testSize_empty() {
//        assertEquals(0, rbt.size());
//    }
//
//    public void testSize_filled() {
//        rbt.insertNoAnim(1);
//        rbt.insertNoAnim(2);
//        rbt.insertNoAnim(3);
//
//        assertEquals(3, rbt.size());
//    }
//
//    public void testIsEmpty_empty() {
//        assertTrue(rbt.isEmpty());
//    }
//
//    public void testIsEmpty_filled() {
//        rbt.insertNoAnim(1);
//
//        assertFalse(rbt.isEmpty());
//    }
//
//    public void testContains_empty() {
//        assertFalse(rbt.contains(1));
//    }
//
//    public void testContains_filled() {
//        rbt.insertNoAnim(1);
//        rbt.insertNoAnim(2);
//
//        assertTrue(rbt.contains(1) && rbt.contains(2));
//    }

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


//    public void testRemoveNoAnim_exists() {
//        rbt.insertNoAnim(1);
//        rbt.insertNoAnim(2);
//        rbt.removeNoAnim(1);
//
//        ArrayList<Integer> keyArrl = rbt.getAllKeys();
//        assertEquals(1, keyArrl.size());
//    }
//
//    public void testRemoveNoAnim_nonExistant() {
//        rbt.insertNoAnim(1);
//        rbt.insertNoAnim(2);
//        rbt.removeNoAnim(3);
//
//        ArrayList<Integer> keyArrl = rbt.getAllKeys();
//        assertEquals(2, keyArrl.size());
//    }
//
//    public void testRemoveAnim_exists() {
//        rbt.insertNoAnim(1);
//        rbt.insertNoAnim(2);
//        rbt.removeAnim(1);
//
//        ArrayList<Integer> keyArrl = rbt.getAllKeys();
//        assertEquals(1, keyArrl.size());
//    }
//
//    public void testRemoveAnim_nonExistant() {
//        rbt.insertAnim(1);
//        rbt.insertAnim(2);
//        rbt.removeAnim(3);
//
//        ArrayList<Integer> keyArrl = rbt.getAllKeys();
//        assertEquals(2, keyArrl.size());
//    }

    public void testSwapColors() {
        rbt.insertNoAnim(1);
        Node testNode1 = rbt.getNode(1);
        rbt.insertNoAnim(2);
        rbt.insertNoAnim(3);

        assertEquals(1, testNode1.value);
    }
}