package com.example.datastructurevisualizer;

import android.graphics.Canvas;

/**
 * LinkedList implementation to be used by other Data Structures.
 *
 * Not that insertAnim and insertNoAnim are not defined, TODO comment
 * Since LinkedList is only used as a smaller part of larger data structures,
 * it includes some special functionality. For example, it stores xPos and
 * yPos in order to guarantee correct placement. It also overrides render so as
 * not to clear the screen before rendering.
 */
public class LinkedList extends TreeVisualizer {

    // This LinkedList will have only one child.
    static final int numChildren = 1;

    // Position of this LinkedList on the Canvas.
    int xPos = (int)(AnimationParameters.NODE_RADIUS * 1.5);
    int yPos = (int)(AnimationParameters.NODE_RADIUS * 1.5);

    /**
     * Return numChildren per node, which is 1. Used in TreeVisualize.
     */
    @Override
    int getNumChildren() { return numChildren; }

    /**
     * Checks whether this LinkedList is empty.
     *
     * @return true if the LinkedList is empty, otherwise false.
     */
    protected boolean isEmpty() {
        return root == null;

    }

    /**
     * Sets the desired position of the root Node of this LinkedList.
     * This method does not actually move the Nodes to their proper positions.
     *
     * @param xPos x position of root Node.
     * @param yPos y position of root Node
     */
    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

    }

    /**
     * Inserts the key into the LinkedList at the root.
     *
     * @param key the key to be inserted.
     */
    public void stackInsert(int key) {
        Node node = new Node(key, getNumChildren());
        node.children[0] = root;
        root = node;

        // Places the Nodes at their desired positions.
        placeTreeNodes(xPos, yPos);
        placeNodesAtDestination();

    }

    /**
     * Inserts a key into the LinkedList in the fashion of a priority queue.
     * Smaller Nodes will be placed at the head.
     *
     * @param key the key to be inserted.
     * @param value the value of the Node.
     */
    public void priorityQueueInsert(int key, int value) {
        Node node = new Node(key, getNumChildren());
        node.value = value;

        // Places the new Node at the root if the queue is empty or if it is
        // the least Node in the queue.
        if (root == null || node.value < root.value) {
            node.children[0] = root;
            root = node;
            placeTreeNodes(xPos, yPos);
            placeNodesAtDestination();
            return;

        }

        // Finds the appropriate place for the Node in the queue.
        Node currNode = root;
        while (currNode.children[0] != null &&
                currNode.children[0].value < node.value)
            currNode = currNode.children[0];

        // Places the Node.
        node.children[0] = currNode.children[0];
        currNode.children[0] = node;

        // Places the Nodes at their desired positions.
        placeTreeNodes(xPos, yPos);
        placeNodesAtDestination();

    }

    /**
     * Inserts the key into the LinekdList at the tail.
     *
     * @param key the key to be inserted.
     */
    public void queueInsert(int key) {
        Node node = new Node(key, getNumChildren());

        // Places the Node at the root if the LinkedList is empty.
        if (root == null) {
            root = node;

        }
        // Places the Node at the tail if the LinkedList is non-empty.
        else {
            Node currNode = root;
            while (currNode.children[0] != null) currNode = currNode.children[0];
            currNode.children[0] = node;

        }

        // Places the Nodes at their desired positions.
        placeTreeNodes(xPos, yPos);
        placeNodesAtDestination();

    }

    /**
     * Deletes the root and returns its key. If root is null, returns -1.
     *
     * @return the key of root or -1.
     */
    public int pop() {
        Node node = root;

        // Returns -1 if the root is null.
        if (root == null) return -1;

        // If the root is not null, replaces root and returns its key.
        root = root.children[0];
        placeTreeNodes(xPos, yPos);
        placeNodesAtDestination();
        return node.key;

    }

    /**
     * Returns the key of the root. If root is null, returns -1.
     *
     * @return the key of the root or -1.
     */
    public int peek() {

        // Returns -1 if the root is null.
        if (root == null) return -1;

        // If the root is not null, returns its key.
        return root.key;

    }

    /**
     * Renders the LinkedList. Unlike other renderers, this will not clear the canvas.
     *
     * @param canvas the Canvas to draw in.
     */
    @Override
    public void render(Canvas canvas) {
        drawTreeRecursive(root, canvas);

    }
}