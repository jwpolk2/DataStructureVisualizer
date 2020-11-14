package com.example.datastructurevisualizer;

import junit.framework.TestCase;
import java.util.ArrayList;

public class AVLTreeTest extends TestCase {
    private AVLTree avl = new AVLTree();

    public void testGetNumChildren_empty() {
        // should always be two, even if null
        assertEquals(2, avl.getNumChildren());
    }

    public void testGetNumChildren_filled() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);
        avl.insertNoAnim(3);
        avl.insertNoAnim(4);

        assertEquals(2, avl.getNumChildren());
    }





    public void testContains_yes() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);

        assertTrue(avl.contains(2));
    }

    public void testContains_no() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);

        assertFalse(avl.contains(3));
    }

    public void testInsertNoAnim() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testInsertNoAnim_duplicate() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(1);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

//    negative values are allowed in the structure,
//    but by the UI settings are not allowed to be inserted
//
//    public void testInsertNoAnim_negative() {
//        avl.insertNoAnim(-1);
//        avl.insertNoAnim(-2);
//
//        ArrayList<Integer> keyArrl = avl.getAllKeys();
//        assertEquals(0, keyArrl.size());
//    }

    public void testInsertAnim() {
        avl.insertAnim(1);
        avl.insertAnim(2);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testInsertAnim_duplicate() {
        avl.insertAnim(1);
        avl.insertAnim(1);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    public void testRemoveNoAnim_exists() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);
        avl.removeNoAnim(1);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    public void testRemoveNoAnim_nonExistant() {
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);
        avl.removeNoAnim(3);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

//    public void testRemoveAnim_exists() {
//        avl.insertNoAnim(1);
//        avl.insertNoAnim(2);
//        avl.removeAnim(1);
//
//        ArrayList<Integer> keyArrl = avl.getAllKeys();
//        assertEquals(1, keyArrl.size());
//    }

    public void testRemoveAnim_nonExistant() {
        avl.insertAnim(1);
        avl.insertAnim(2);
        avl.removeAnim(3);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(2, keyArrl.size());
    }
}