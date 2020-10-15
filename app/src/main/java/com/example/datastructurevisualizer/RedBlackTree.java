package com.example.datastructurevisualizer;

// https://github.com/williamfiset/data-structures/blob/master/com/williamfiset/datastructures/balancedtree/RedBlackTree.java
// TODO this tree is distributed under the MIT license
// TODO implement MIT license requirements
// TODO note that for this tree key is the integer key and value is the boolean red/black value
    // TODO could use colour to store the red/black value implicity
// TODO note that this tree's Nodes use extraData[0] to store parent.

// TODO !!I M P O R T A N T!! this tree contains no remove method.
// TODO traversals should be implemented in TreeVisualize.

/**
  * This file contains an implementation of a Red-Black tree. A RB tree is a special type of binary
  * tree which self balances itself to keep operations logarithmic.
  *
  * For this tree Node.key is the integer key and Node.value is the boolean red/black value.
  *
  * <p>Great visualization tool: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
  *
  * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class RedBlackTree extends TreeVisualize {

    // Definitions for RED and BLACK.
    public static final int RED = 1;
    public static final int BLACK = 0;

    // Number of children per node in this tree.
    static final int numChildren = 2;

    // Tracks the number of nodes inside the tree.
    private int nodeCount = 0;

    /**
     * Return numChildren, which is 2. Used in TreeVisualize.
     */
    @Override
    int getNumChildren() { return numChildren; }

    // Returns the number of nodes in the tree.
    public int size() {
        return nodeCount;
    }

    // Returns whether or not the tree is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(int key) {
        Node node = root;

        if (node == null) return false;

        while (node != null) {

            // Dig into left subtree.
            if (key < node.key) node = node.children[ChildNames.LEFT.i];
            // Dig into right subtree.
            else if (key > node.key) node = node.children[ChildNames.RIGHT.i];
            // Found value in tree.
            else return true;

        }

        return false;
    }

    public boolean insert(int key) {

        // No root node.
        if (root == null) {
            root = new Node(key, numChildren);
            root.extraData = new Object[1];
            insertionRelabel(root);
            nodeCount++;
            return true;

        }

        for (Node node = root; ; ) {

            // Left subtree.
            if (key < node.key) {
                if (node.children[ChildNames.LEFT.i] == null) {
                    node.children[ChildNames.LEFT.i] = new Node(key, numChildren);
                    node.children[ChildNames.LEFT.i].extraData[0] = node;
                    insertionRelabel(node.children[ChildNames.LEFT.i]);
                    nodeCount++;
                    return true;

                }
                node = node.children[ChildNames.LEFT.i];


            }
            // Right subtree.
            else if (key > node.key) {
                if (node.children[ChildNames.RIGHT.i] == null) {
                    node.children[ChildNames.RIGHT.i] = new Node(key, numChildren);
                    node.children[ChildNames.RIGHT.i].extraData[0] = node;
                    insertionRelabel(node.children[ChildNames.RIGHT.i]);
                    nodeCount++;
                    return true;
                }
                node = node.children[ChildNames.RIGHT.i];

                // The value we're trying to insert already exists in the tree.
            } else return false;
        }
    }

    private void insertionRelabel(Node node) {
        Node parent = (Node)node.extraData[0];

        // Root node case.
        if (parent == null) {
            node.value = BLACK;
            root = node;
            return;
        }

        Node grandParent = (Node)parent.extraData[0];
        if (grandParent == null) return;

        // The red-black tree invariant is already satisfied.
        if (parent.value == BLACK || node.value == BLACK) return;

        boolean nodeIsLeftChild = (parent.children[ChildNames.LEFT.i] == node);
        boolean parentIsLeftChild = (parent == grandParent.children[ChildNames.LEFT.i]);
        Node uncle = parentIsLeftChild ? grandParent.children[ChildNames.RIGHT.i] : grandParent.children[ChildNames.LEFT.i];
        int uncleIsRedNode = (uncle == null) ? BLACK : uncle.value;

        if (uncleIsRedNode != 0) {

            parent.value = BLACK;
            grandParent.value = RED;
            uncle.value = BLACK;

            // At this point the parent node is red and so is the new child node.
            // We need to re-balance somehow because no two red nodes can be
            // adjacent to one another.
        } else {

            // Parent node is a left child.
            if (parentIsLeftChild) {

                // Left-left case.
                if (nodeIsLeftChild) {
                    grandParent = leftLeftCase(grandParent);

                    // Left-right case.
                } else {
                    grandParent = leftRightCase(grandParent);
                }

                // Parent node is a right child.
            } else {

                // Right-left case.
                if (nodeIsLeftChild) {
                    grandParent = rightLeftCase(grandParent);

                    // Right-right case.
                } else {
                    grandParent = rightRightCase(grandParent);
                }
            }
        }

        insertionRelabel(grandParent);
    }

    private void swapColors(Node a, Node b) {
        int tmpColor = a.value;
        a.value = b.value;
        b.value = tmpColor;
    }

    private Node leftLeftCase(Node node) {
        node = rightRotate(node);
        swapColors(node, node.children[ChildNames.RIGHT.i]);
        return node;
    }

    private Node leftRightCase(Node node) {
        node.children[ChildNames.LEFT.i] = leftRotate(node.children[ChildNames.LEFT.i]);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        node = leftRotate(node);
        swapColors(node, node.children[ChildNames.LEFT.i]);
        return node;
    }

    private Node rightLeftCase(Node node) {
        node.children[ChildNames.RIGHT.i] = rightRotate(node.children[ChildNames.RIGHT.i]);
        return rightRightCase(node);
    }

    private Node rightRotate(Node parent) {

        Node grandParent = (Node)parent.extraData[0];
        Node child = parent.children[ChildNames.LEFT.i];

        parent.children[ChildNames.LEFT.i] = child.children[ChildNames.RIGHT.i];
        if (child.children[ChildNames.RIGHT.i] != null) child.children[ChildNames.RIGHT.i].extraData[0] = parent;

        child.children[ChildNames.RIGHT.i] = parent;
        parent.extraData[0] = child;

        child.extraData[0] = grandParent;
        updateParentChildLink(grandParent, parent, child);

        return child;
    }

    private Node leftRotate(Node parent) {

        Node grandParent = (Node)parent.extraData[0];
        Node child = parent.children[ChildNames.LEFT.i];

        parent.children[ChildNames.RIGHT.i] = child.children[ChildNames.LEFT.i];
        if (child.children[ChildNames.LEFT.i] != null) child.children[ChildNames.LEFT.i].extraData[0] = parent;

        child.children[ChildNames.LEFT.i] = parent;
        parent.extraData[0] = child;

        child.extraData[0] = grandParent;
        updateParentChildLink(grandParent, parent, child);

        return child;
    }

    // Sometimes the left or right child node of a parent changes and the
    // parent's reference needs to be updated to point to the new child.
    // This is a helper method to do just that.
    private void updateParentChildLink(Node parent, Node oldChild, Node newChild) {
        if (parent != null) {
            if (parent.children[ChildNames.LEFT.i] == oldChild) {
                parent.children[ChildNames.LEFT.i] = newChild;
            } else {
                parent.children[ChildNames.RIGHT.i] = newChild;
            }
        }
    }

    // Helper method to find the leftmost node (which has the smallest value)
    private Node findMin(Node node) {
        while (node.children[ChildNames.LEFT.i] != null) node = node.children[ChildNames.LEFT.i];
        return node;
    }

    // Helper method to find the rightmost node (which has the largest value)
    private Node findMax(Node node) {
        while (node.children[ChildNames.RIGHT.i] != null) node = node.children[ChildNames.RIGHT.i];
        return node;
    }

    // Returns as iterator to traverse the tree in order.
    // TODO remove and put in TreeVisualize.
    /*@Override
    public java.util.Iterator<int> iterator() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                Node node = stack.pop();

                if (node.children[ChildNames.RIGHT.i] != null) {
                    stack.push(node.children[ChildNames.RIGHT.i]);
                    trav = node.children[ChildNames.RIGHT.i];
                }

                return node.key;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }*/

    // Example usage of RB tree:
    // TODO remove
    public static void main(String[] args) {

        int[] values = {5, 8, 1, -4, 6, -2, 0, 7};
        RedBlackTree rbTree = new RedBlackTree();
        for (int v : values) rbTree.insert(v);

        System.out.printf("RB tree contains %d: %s\n", 6, rbTree.contains(6));
        System.out.printf("RB tree contains %d: %s\n", -5, rbTree.contains(-5));
        System.out.printf("RB tree contains %d: %s\n", 1, rbTree.contains(1));
        System.out.printf("RB tree contains %d: %s\n", 99, rbTree.contains(99));
    }
}