package com.example.datastructurevisualizer;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;


public class BinarySearchTreeTest extends TestCase {
    private BinarySearchTree bst = new BinarySearchTree();

//    @BeforeEach
//        create canvas? could help with insertAnim()
//    }
//
//    @AfterEach {
//    }

    public void testGetNumChildren_empty() {
        // should always be two, even if null
        assertEquals(2, bst.getNumChildren());
    }

    public void testGetNumChildren_filled() {
        bst.insertNoAnim(5);
        bst.insertNoAnim(10);
        bst.insertNoAnim(15);
        assertEquals(2, bst.getNumChildren());
    }


//    public void testGetNodeHeight_Filled() {
//       // TODO
//    }


    public void testRemoveNoAnim_filled() {
        bst.insertNoAnim(5);
        bst.insertNoAnim(6);
        bst.removeNoAnim(5);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

    //test remove on empty tree to ensure no null pointer error
    //not realistic case because nodes must be in canvas for the option to delete
    public void testRemoveNoAnim_empty() {
        bst.removeNoAnim(5);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(0, keyArrl.size());
    }

    public void testRemoveNoAnim_empty2() {
        bst.insertNoAnim(5);
        bst.removeNoAnim(5);
        bst.removeNoAnim(5);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(0, keyArrl.size());
    }

    public void testRemoveAnim() {
        bst.insertNoAnim(5);
        bst.removeAnim(5);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(0, keyArrl.size());
    }

    public void testInsertNoAnim() {
        try {
            bst.insertNoAnim(5);
            bst.insertNoAnim(10);

            ArrayList<Integer> keyArrl = bst.getAllKeys();
            assertEquals(2, keyArrl.size());
        }
        catch(NullPointerException e) {
            System.out.println("Error: nothing inserted");
        }
    }

    public void testInsertNoAnim_duplicate() {
        try {
            bst.insertNoAnim(5);
            bst.insertNoAnim(10);
            bst.insertNoAnim(5);

            ArrayList<Integer> keyArrl = bst.getAllKeys();
            assertEquals(2, keyArrl.size());
        }
        catch(NullPointerException e) {
            System.out.println("Error: nothing inserted");
        }
    }

    public void testInsertAnim() {
            bst.insertAnim(5);
            bst.insertAnim(10);

            ArrayList<Integer> keyArrl = bst.getAllKeys();
            assertEquals(2, keyArrl.size());
    }

    public void testInsertAnim_duplicate() {
        bst.insertAnim(5);
        bst.insertAnim(10);
        bst.insertAnim(5);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testInsertManyRemoveNoAnim(){
        bst.insertAnim(5);
        bst.insertAnim(3);
        bst.insertAnim(1);
        bst.insertAnim(2);
        bst.insertAnim(6);
        bst.insertAnim(8);

        bst.insertAnim(14);
        bst.insertAnim(16);
        bst.insertAnim(10);
        bst.insertAnim(4);
        bst.insertAnim(9);
        bst.insertAnim(12);

        bst.removeNoAnim(1);
        bst.removeNoAnim(8);
        bst.removeNoAnim(5);
        bst.removeNoAnim(4);
        bst.removeNoAnim(10);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertNoAnimManyRemoveNoAnim(){
        bst.insertNoAnim(5);
        bst.insertNoAnim(3);
        bst.insertNoAnim(1);
        bst.insertNoAnim(2);
        bst.insertNoAnim(6);
        bst.insertNoAnim(8);

        bst.insertNoAnim(14);
        bst.insertNoAnim(16);
        bst.insertNoAnim(10);
        bst.insertNoAnim(4);
        bst.insertNoAnim(9);
        bst.insertNoAnim(12);

        bst.removeNoAnim(1);
        bst.removeNoAnim(8);
        bst.removeNoAnim(5);
        bst.removeNoAnim(4);
        bst.removeNoAnim(10);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(7, keyArrl.size());
    }

    public void testInsertManyRemoveAnim(){
        bst.insertAnim(5);
        bst.insertAnim(3);
        bst.insertAnim(1);
        bst.insertAnim(2);
        bst.insertAnim(6);
        bst.insertAnim(8);

        bst.insertAnim(14);
        bst.insertAnim(16);
        bst.insertAnim(10);
        bst.insertAnim(4);
        bst.insertAnim(9);
        bst.insertAnim(12);

        bst.removeAnim(1);
        bst.removeAnim(8);
        bst.removeAnim(5);
        bst.removeAnim(4);
        bst.removeAnim(10);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(7, keyArrl.size());
    }
}