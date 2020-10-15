package com.example.datastructurevisualizer;

/**
 * Superclass for all trees. Enables code reuse among tree visualization.
 * Contains numChildren, a field indicating the number of children per node for a given tree.
 * Contains root, the root node for a given tree. It is located here so as to be used by
 * shared visualization methods.
 * Contains TODO traversals
 */
public class TreeVisualize {
    Node root;

    /**
     * This method is used to get the number of children in a tree.
     * Each tree will override it to return its own numChildren.
     * TODO not sure if inheritance will prefer this method or methods defined in
     * TODO child. SHould prioritize methods defined in child, otherwise something
     * TODO stupid will need to be done.
     */
    int getNumChildren() {
        return 0;

    }

    /**
     * TODO implement
     */
    void preOrderTraversal() {

    }

    /**
     * TODO implement
     */
    void postOrderTraversal() {

    }

    /**
     * TODO implement
     */
    void inOrderTraversal() {

    }
}
