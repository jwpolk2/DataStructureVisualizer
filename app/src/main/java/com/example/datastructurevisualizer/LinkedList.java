package com.example.datastructurevisualizer;

import android.graphics.Canvas;

/**
 * LinkedList implementation to be used by the HashTable.
 * Includes the bare minimum insertion and deletion methods.
 *
 * Since LinkedList is only used as a smaller part of larger data structures,
 * it includes some special functionality. For example, it stores xPos and
 * yPos in order to guarantee correct placement.
 */
public class LinkedList extends TreeVisualizer {

    // This LinkedList will have only one child.
    static final int numChildren = 1;

    // Position of this LinkedList on the Canvas.
    int xPos = 30, yPos = 30;

    /**
     * Return numChildren per node, which is 1. Used in TreeVisualize.
     */
    @Override
    int getNumChildren() { return numChildren; }

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
     * Inserts the key into the LinekdList at the tail.
     *
     * @param key the key to be inserted.
     */
    public void queueInsert(int key) {
        Node node = new Node(key, getNumChildren());

        // Places the Node at the root if the LinkedList is empty.
        if (root == null) root = node;

        // Places the Node at the tail.
        Node currNode = root;
        while (currNode.children[0] != null) currNode = currNode.children[0];
        currNode.children[0] = node;

    }

    /**
     * Inserts the key into the LinkedList at the root.
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {
        stackInsert(key);

    }

    /**
     * Inserts the key into the LinkedList at the root. Animates the movement
     * of the node into the tree.
     *
     * @param key the key to be inserted.
     */
    @Override
    protected void insertAnim(int key) {
        Node node = new Node(key, getNumChildren());
        node.children[0] = root;
        root = node;

        // Queues a movement animation.
        placeTreeNodes(xPos, yPos);
        queueNodeMoveAnimation();

    }

    /**
     * TODO implement remove.
     *
     * @param key the key to be removed.
     */
    @Override
    public void removeNoAnim(int key) {

    }

    /**
     * TODO implement remove.
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeAnim(int key) {

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