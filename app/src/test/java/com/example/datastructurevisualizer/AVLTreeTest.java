package com.example.datastructurevisualizer;

import junit.framework.TestCase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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

    public void testInsertManyRemoveNoAnim(){
        avl.insertAnim(5);
        avl.insertAnim(3);
        avl.insertAnim(1);
        avl.insertAnim(2);
        avl.insertAnim(6);
        avl.insertAnim(8);

        avl.insertAnim(14);
        avl.insertAnim(16);
        avl.insertAnim(10);
        avl.insertAnim(4);
        avl.insertAnim(9);
        avl.insertAnim(12);

        avl.removeNoAnim(1);
        avl.removeNoAnim(8);
        avl.removeNoAnim(5);
        avl.removeNoAnim(4);
        avl.removeNoAnim(10);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertNoAnimManyRemoveNoAnim(){
        avl.insertNoAnim(5);
        avl.insertNoAnim(3);
        avl.insertNoAnim(1);
        avl.insertNoAnim(2);
        avl.insertNoAnim(6);
        avl.insertNoAnim(8);

        avl.insertNoAnim(14);
        avl.insertNoAnim(16);
        avl.insertNoAnim(10);
        avl.insertNoAnim(4);
        avl.insertNoAnim(9);
        avl.insertNoAnim(12);

        avl.removeNoAnim(1);
        avl.removeNoAnim(8);
        avl.removeNoAnim(5);
        avl.removeNoAnim(4);
        avl.removeNoAnim(10);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertManyRemoveAnim(){
        avl.insertAnim(5);
        avl.insertAnim(3);
        avl.insertAnim(1);
        avl.insertAnim(2);
        avl.insertAnim(6);
        avl.insertAnim(8);

        avl.insertAnim(14);
        avl.insertAnim(16);
        avl.insertAnim(10);
        avl.insertAnim(4);
        avl.insertAnim(9);
        avl.insertAnim(12);

        avl.removeAnim(1);
        avl.removeAnim(8);
        avl.removeAnim(5);
        avl.removeAnim(4);
        avl.removeAnim(10);

        ArrayList<Integer> keyArrl = avl.getAllKeys();
        assertEquals(7, keyArrl.size());
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