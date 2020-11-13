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
}