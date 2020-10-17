package com.example.datastructurevisualizer;

/**
 * Superclass for all trees. Enables code reuse among tree visualization.
 * Contains numChildren, a field indicating the number of children per node for a given tree.
 * Contains root, the root node for a given tree. It is located here so as to be used by
 * shared visualization methods.
 * Contains pre-order, post-order, and in-order traversals.
 * Contains a function for placing each Node in the tree at an appropriate position.
 */
public class TreeVisualize {
    Node root;

    /**
     * This method is used to get the number of children in a tree.
     * Each tree will override it to return its own numChildren.
     * TODO not sure if inheritance will prefer this method or methods defined in
     * TODO child. Should prioritize methods defined in child, otherwise something
     * TODO stupid will need to be done.
     */
    int getNumChildren() { return 0; }

    /**
     * Begins a pre-order traversal.
     */
    void preOrderTraversal() {
        treePreOrderTraversal(root);

    }

    /**
     * Performs a pre-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treePreOrderTraversal(Node currNode) {
        int numChildren = getNumChildren();

        // TODO animation cue

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePreOrderTraversal(currNode.children[i]);

    }

    /**
     * Begins a post-order traversal.
     */
    void postOrderTraversal() {
        treePostOrderTraversal(root);

    }

    /**
     * Performs a post-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treePostOrderTraversal(Node currNode) {
        int numChildren = getNumChildren();

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // TODO animation cue

    }

    /**
     * Begins an in-order traversal.
     */
    void inOrderTraversal() {
        treeInOrderTraversal(root);

    }

    /**
     * Performs an in-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treeInOrderTraversal(Node currNode) {
        int numChildren = getNumChildren();

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // TODO animation cue

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePreOrderTraversal(currNode.children[i]);

    }

    /**
     * Recursively places each node in the Tree. Each successive layer will have
     * width / numChildren horizontal distance between Nodes and depthLen vertical
     * distance between Nodes.
     *
     * @param width horizontal distance between Nodes.
     * @param depth current depth within the Tree.
     * @param depthLen vertical distance between Nodes.
     * @param currNode the Node whose children should be placed.
     */
    void placeTreeNodesRecursive(float width, int depth, int depthLen, Node currNode) {
        int currX = currNode.position[0];
        int currY = currNode.position[1];
        int numChildren = getNumChildren();

        // Returns if the bottom of the Tree has been reached.
        if (depth == 0 || currNode == null) return;

        // Offsets currX to the leftmost Node.
        // Note: offsets slightly more than appropriate so the for loop below is easier to write.
        currX -= (int)((width * (1.0 + numChildren)) / 2.0);

        // Offsets currY by depthLen.
        currY += depthLen;

        // Recursively places each child Node.
        for (int i = 0; i < numChildren; ++i) {
            currX += width;
            root.children[i].position[0] = currX;
            root.children[i].position[1] = currY;
            placeTreeNodesRecursive(width / numChildren, depth - 1, depthLen, root.children[i]);

        }
    }

    /**
     * Places all Nodes. Does so by using the Tree's depth and desired width to
     * calculate the appropriate width between the Nodes on the first layer, then
     * recursively calculating the appropriate width between Nodes in every
     * successive layer.
     *
     * This method can be used for Trees with any fixed number of children (that
     * includes LinkedLists).
     *
     * // TODO this was haphazardly adapted from C++ code and has not been debugged
     *
     * @param depth the current maximum depth of the Tree.
     * @param depthLen the vertical distance between layers in the Tree.
     * @param treeWidth the total width of the Tree.
     */
    public void placeTreeNodes(int depth, int depthLen, int treeWidth) {
        int currX, currY;
        int numChildren = getNumChildren();
        float width;

        // Initializes position of root.
        // TODO make this compatible with moving about Tree (offset relative to some global currPosX).
        root.position[0] = treeWidth / 2;
        root.position[1] = 0;

        // Calculates the width between children of the root Node.
        width = (float)(Math.pow(numChildren, depth - 1) * ((float)treeWidth / (Math.pow(numChildren, depth) - 1.0)));

        // If rendering a LinkedList, sets width to 0 for convenience.
        // TODO if LinkedList entered (float)treeWidth / (Math.pow(numChildren, depth) - 1.0) should divide by 0
        // TODO not sure if it will do an exception or produce infinity
        if (width == Double.POSITIVE_INFINITY) width = 0;

        // Begins recursively placing the Tree Nodes.
        placeTreeNodesRecursive(width, depth, depthLen, root);

    }
}
