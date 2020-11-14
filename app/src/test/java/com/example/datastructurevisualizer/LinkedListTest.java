package com.example.datastructurevisualizer;

import android.graphics.Canvas;

import junit.framework.TestCase;

import java.util.ArrayList;


public class LinkedListTest extends TestCase {
    private LinkedList llist = new LinkedList();

    public void testGelNumChildren_empty() {
        assertEquals(1, llist.getNumChildren());
    }

    public void testGelNumChildren_filled() {
        llist.stackInsert(1);

        assertEquals(1, llist.getNumChildren());
    }

    public void testStackInsert_filled() {
        llist.stackInsert(1);
        llist.stackInsert(2);
        Node testNode = llist.root;

        assertEquals(2, testNode.key);
    }

    // linked list is allowed to have duplicates, unlike trees
    public void testStackInsert_duplicate() {
        llist.stackInsert(1);
        llist.stackInsert(1);

        ArrayList<Integer> keyArrl = llist.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testPriorityQueueInsert_nullRoot() {
        llist.priorityQueueInsert(1, 1);
        llist.priorityQueueInsert(2, 2);

        ArrayList<Integer> keyArrl = llist.getAllKeys();
        assertEquals(2, keyArrl.size());
    }

    public void testPriorityQueueInsert_notNullroot() {
        llist.priorityQueueInsert(1, 1);
        Node testRoot = llist.root;
        llist.priorityQueueInsert(2, 2);

        assertEquals(2, testRoot.children[0].key);
    }

    public void testQueueInsert_root() {
        llist.queueInsert(1);
        
        assertEquals(1, llist.root.key);
    }

    public void testQueueInsert_nonRoot() {
        llist.queueInsert(1);
        Node testNode = llist.getNode(1);
        llist.queueInsert(2);

        assertEquals(2, testNode.children[0].key);
    }

    public void testPop_empty() {
        assertEquals(-1, llist.pop());
    }

    public void testPop_filled() {
        llist.queueInsert(1);
        llist.queueInsert(2);
        llist.pop();

        ArrayList<Integer> keyArrl = llist.getAllKeys();
        assertEquals(1, keyArrl.size());
    }

//    public void testRender() {}   ???

}