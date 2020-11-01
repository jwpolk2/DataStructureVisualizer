package com.example.datastructurevisualizer;

/**
 * LinkedList implementation to be used by the HashTable.
 * Includes the bare minimum insertion and deletion methods.
 */
public class LinkedList extends TreeVisualizer {

    // This LinkedList will have only one child.
    static final int numChildren = 1;

    /**
     * Return numChildren per node, which is 1. Used in TreeVisualize.
     */
    @Override
    int getNumChildren() { return numChildren; }

    /**
     * Inserts the key into the LinkedList at the root.
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {
        Node node = new Node(key, getNumChildren());
        node.children[0] = root;
        root = node;

        // Renders the tree after insertion.
        finalRender();

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
    protected  void removeAnim(int key) {

    }
}