package com.example.datastructurevisualizer;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;


public class BinarySearchTreeTest extends TestCase {
    BinarySearchTree bst = new BinarySearchTree();

//    @BeforeEach
//    public void setUp() throws Exception {
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//    }


    //Test that the number of children per node is 2
    public void testGetNumChildren() {
        bst.insertNoAnim(5);
        bst.insertNoAnim(10);
        bst.insertNoAnim(15);
        assertEquals(2, bst.getNumChildren());
    }


//    public void testGetTreeData() {
//        bst.insertNoAnim(5);
//        bst.insertNoAnim(10);
//        bst.insertNoAnim(15);
//
//        ArrayList<Integer> keyArrl = bst.getAllKeys();
//        assertEquals(keyArrl, bst.getTreeData());
//    }

//    Unused
//    public void testSetTreeData() {
//    }

    public void testGetRoot_empty() {
        assertEquals(null, bst.getRoot());
    }

    public void testGetRoot_singleNode() {
        bst.insertNoAnim(1);
        System.out.print(bst.getRoot());
//        assertEquals(1, bst.getRoot().key);
//
    }
    public void testGetRoot_threeNodes() {
        bst.insertNoAnim(1);
        bst.insertNoAnim(2);
        bst.insertNoAnim(3);
        assertEquals(1, bst.getRoot().key);
    }

//    Unused
//    public void testSetRoot() {
//    }

    public void testTreeHeight() {
        Node root = new Node(1, 2);
        assertEquals(1, bst.treeHeight(root));
    }

    public void testIsBalancedNaive() {
        Node root = new Node(1, 2);
        assertTrue(bst.isBalancedNaive(root));
    }

    public void testGetNodeHeight_Empty() {
        Node root = null;
        assertEquals(0, bst.getNodeHeight(root, root));
    }

    public void testGetNodeHeight_Filled() {
       // TODO
    }

//    Unused
//    public void testIsIsomorphic() {
//    }

//    Unused
//    public void testIsSymmetric() {
//    }

//    Unused
//    public void testIsEqual() {
//    }

//    Unused
//    public void testReverse() {
//    }

//    Unused
//    public void testGetMaxWidth() {
//    }

//    Unused
//    public void testSetMaxWidth() {
//    }

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
        TreeVisualizer bst = new BinarySearchTree();
        bst.removeNoAnim(5);

        ArrayList<Integer> keyArrl = bst.getAllKeys();
        assertEquals(0, keyArrl.size());
    }

    public void testRemoveAnim() {
        TreeVisualizer bst = new BinarySearchTree();
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

//    TODO: animations need some sort of mocked canvas
//    public void testInsertAnim() {
//            bst.insertAnim(5);
//            bst.insertAnim(10);
//
//            ArrayList<Integer> keyArrl = bst.getAllKeys();
//            assertEquals(2, keyArrl.size());
//
//    }
}