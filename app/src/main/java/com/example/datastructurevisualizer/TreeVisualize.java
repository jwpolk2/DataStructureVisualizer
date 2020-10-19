package com.example.datastructurevisualizer;
import android.graphics.Paint;

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

    // TODO move to superclass
    float nodeWidth = 5f;

    /**
     * This method is used to get the number of children in a tree.
     * Each tree will override it to return its own numChildren.
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
        int currX, currY;
        int numChildren;

        // Returns if the bottom of the Tree has been reached.
        if (depth == 0 || currNode == null) return;

        // Stores the number of children for measurement.
        numChildren = getNumChildren();

        // Starts from the current position.
        currX = currNode.position[0];
        currY = currNode.position[1];

        // Offsets currX to the leftmost Node.
        // Note: offsets slightly more than appropriate so the for loop below is easier to write.
        currX -= (int)((width * (1.0 + numChildren)) / 2.0);

        // Offsets currY by depthLen.
        currY += depthLen;

        // Recursively places each child Node.
        for (int i = 0; i < numChildren; ++i) {
            currX += width;

            // Will only place non-null nodes.
            if (currNode.children[i] != null) {
                currNode.children[i].position[0] = currX;
                currNode.children[i].position[1] = currY;
                placeTreeNodesRecursive(width / numChildren, depth - 1, depthLen, currNode.children[i]);

            }
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
     * @param depth the current maximum depth of the Tree. // TODO make function to calculate this inside
     * @param depthLen the vertical distance between layers in the Tree.
     */
    public void placeTreeNodes(int depth, int depthLen) {
        int treeWidth = MainActivity.getCanvas().getWidth();
        int numChildren = getNumChildren();
        float width;

        // Initializes position of root.
        root.position[0] = treeWidth / 2;
        root.position[1] = 20;

        // Calculates the width between children of the root Node.
        width = (float)treeWidth / numChildren;

        // If rendering a LinkedList, sets width to 0 for convenience.
        if (numChildren == 1) width = 0;

        // Begins recursively placing the Tree Nodes.
        placeTreeNodesRecursive(width, depth, depthLen, root);

    }

    /**
     * Draws a Node. Nodes are circles of width nodeWidth with their numerical
     * values printed over them.
     *
     * TODO add to superclass
     * TODO add values
     *
     * @param node the Node to draw.
     */
    protected void drawNode(Node node) {
        Paint colour = new Paint();
        colour.setARGB(255, node.r, node.g, node.b);
        MainActivity.getCanvas().drawCircle(
                node.position[0], node.position[1], nodeWidth, colour);
    }

    /**
     * Recursively draws Nodes in a tree. Does so by drawing the vectors
     * between currNode and its children, then drawing currNode, then
     * drawing currNode's children.
     *
     * @param currNode the current Node to be drawn.
     */
    private void drawTreeRecursive(Node currNode) {

        // Returns if currNode is null.
        if (currNode == null) return;

        // Draws vectors between this Node and all child Nodes.
        Paint colour = new Paint();
        colour.setARGB(255, 255, 255, 255); // TODO change vector colour
        for (int i = 0; i < getNumChildren(); ++i) {
            if (currNode.children[i] != null) {
                MainActivity.getCanvas().drawLine(
                        currNode.position[0], currNode.position[1],
                        currNode.children[i].position[0], currNode.children[i].position[1],
                        colour);

            }
        }

        // Draws the current Node.
        drawNode(currNode);

        // Draws all child Nodes.
        for (int i = 0; i < getNumChildren(); ++i)
            drawTreeRecursive(currNode.children[i]);

    }

    /**
     * Draws this tree starting at the root.
     */
    public void drawTree() {
        drawTreeRecursive(root);

    }
}