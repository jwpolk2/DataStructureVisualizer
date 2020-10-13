package com.example.datastructurevisualizer;

// https://github.com/williamfiset/data-structures/blob/master/com/williamfiset/datastructures/balancedtree/AVLTreeRecursive.java#L366
// TODO this tree is distributed under the MIT license
// TODO implement MIT license requirements
// TODO note that for this tree key is the integer key, value is the balance factor, and extraData[0] is the height of the node.
    // TODO the above may need to be changed

/**
 * This file contains an implementation of an AVL tree. An AVL tree is a special type of binary tree
 * which self balances itself to keep operations logarithmic.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class AVLTree {

    // The root node of the AVL tree.
    public Node root;

    // Tracks the number of nodes inside the tree.
    private int nodeCount = 0;

    // The height of a rooted tree is the number of edges between the tree's
    // root and its furthest leaf. This means that a tree containing a single
    // node has a height of 0.
    public int height() {
        if (root == null) return 0;
        return (Integer)root.extraData[0];

    }

    // Returns the number of nodes in the tree.
    public int size() {
        return nodeCount;
    }

    // Returns whether or not the tree is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return true/false depending on whether a key exists in the tree.
    public boolean contains(int key) {
        return contains(root, key);
    }

    // Recursive contains helper method.
    private boolean contains(Node node, int key) {

        if (node == null) return false;

        // Dig into left subtree.
        if (key < node.key) return contains(node.children[ChildNames.LEFT.i], key);
        // Dig into right subtree.
        if (key > node.key) return contains(node.children[ChildNames.RIGHT.i], key);
        // Found key in tree.
        return true;

    }

    // Insert/add a key to the AVL tree. The key must not be null, O(log(n))
    public boolean insert(int key) {
        if (!contains(root, key)) {
            root = insert(root, key);
            nodeCount++;
            return true;
        }
        return false;
    }

    // Inserts a key inside the AVL tree.
    private Node insert(Node node, int key) {

        // Base case.
        if (node == null) {
            node = new Node();
            node.key = key;
            return node;

        }

        // Insert node in left subtree.
        if (key < node.key) {
            node.children[ChildNames.LEFT.i] = insert(node.children[ChildNames.LEFT.i], key);
            ;

            // Insert node in right subtree.
        } else {
            node.children[ChildNames.RIGHT.i] = insert(node.children[ChildNames.RIGHT.i], key);
        }

        // Update balance factor and height keys.
        update(node);

        // Re-balance tree.
        return balance(node);
    }

    // Update a node's height and balance factor.
    private void update(Node node) {

        int leftNodeHeight = (node.children[ChildNames.LEFT.i] == null) ? -1 : (Integer)node.children[ChildNames.LEFT.i].extraData[0];
        int rightNodeHeight = (node.children[ChildNames.RIGHT.i] == null) ? -1 : (Integer)node.children[ChildNames.RIGHT.i].extraData[0];

        // Update this node's height.
        node.extraData[0] = 1 + Math.max(leftNodeHeight, rightNodeHeight);

        // Update balance factor.
        node.value = rightNodeHeight - leftNodeHeight;
    }

    // Re-balance a node if its balance factor is +2 or -2.
    private Node balance(Node node) {

        // Left heavy subtree.
        if (node.value == -2) {

            // Left-Left case.
            if (node.children[ChildNames.LEFT.i].value <= 0) {
                return leftLeftCase(node);

                // Left-Right case.
            } else {
                return leftRightCase(node);
            }

            // Right heavy subtree needs balancing.
        } else if (node.value == +2) {

            // Right-Right case.
            if (node.children[ChildNames.RIGHT.i].value >= 0) {
                return rightRightCase(node);

                // Right-Left case.
            } else {
                return rightLeftCase(node);
            }
        }

        // Node either has a balance factor of 0, +1 or -1 which is fine.
        return node;
    }

    private Node leftLeftCase(Node node) {
        return rightRotation(node);
    }

    private Node leftRightCase(Node node) {
        node.children[ChildNames.LEFT.i] = leftRotation(node.children[ChildNames.LEFT.i]);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        return leftRotation(node);
    }

    private Node rightLeftCase(Node node) {
        node.children[ChildNames.RIGHT.i] = rightRotation(node.children[ChildNames.RIGHT.i]);
        return rightRightCase(node);
    }

    private Node leftRotation(Node node) {
        Node newParent = node.children[ChildNames.RIGHT.i];
        node.children[ChildNames.RIGHT.i] = newParent.children[ChildNames.LEFT.i];
        newParent.children[ChildNames.LEFT.i] = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.children[ChildNames.LEFT.i];
        node.children[ChildNames.LEFT.i] = newParent.children[ChildNames.RIGHT.i];
        newParent.children[ChildNames.RIGHT.i] = node;
        update(node);
        update(newParent);
        return newParent;
    }

    // Remove a key from this binary tree if it exists, O(log(n))
    public boolean remove(int elem) {

        if (contains(root, elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }

        return false;
    }

    // Removes a key from the AVL tree.
    private Node remove(Node node, int elem) {

        if (node == null) return null;

        // Dig into left subtree, the key we're looking
        // for is smaller than the current key.
        if (elem < node.key) {
            node.children[ChildNames.LEFT.i] = remove(node.children[ChildNames.LEFT.i], elem);

            // Dig into right subtree, the key we're looking
            // for is greater than the current key.
        } else if (elem > node.key) {
            node.children[ChildNames.RIGHT.i] = remove(node.children[ChildNames.RIGHT.i], elem);

            // Found the node we wish to remove.
        } else {

            // This is the case with only a right subtree or no subtree at all.
            // In this situation just swap the node we wish to remove
            // with its right child.
            if (node.children[ChildNames.LEFT.i] == null) {
                return node.children[ChildNames.RIGHT.i];

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.children[ChildNames.RIGHT.i] == null) {
                return node.children[ChildNames.LEFT.i];

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // key in the left subtree or the smallest key in the right
                // subtree. As a heuristic, I will remove from the subtree with
                // the greatest hieght in hopes that this may help with balancing.
            } else {

                // Choose to remove from left subtree
                if ((Integer)node.children[ChildNames.LEFT.i].extraData[0] > (Integer)node.children[ChildNames.RIGHT.i].extraData[0]) {

                    // Swap the key of the successor into the node.
                    int successorValue = findMax(node.children[ChildNames.LEFT.i]);
                    node.key = successorValue;

                    // Find the largest node in the left subtree.
                    node.children[ChildNames.LEFT.i] = remove(node.children[ChildNames.LEFT.i], successorValue);

                } else {

                    // Swap the key of the successor into the node.
                    int successorValue = findMin(node.children[ChildNames.RIGHT.i]);
                    node.key = successorValue;

                    // Go into the right subtree and remove the leftmost node we
                    // found and swapped data with. This prevents us from having
                    // two nodes in our tree with the same key.
                    node.children[ChildNames.RIGHT.i] = remove(node.children[ChildNames.RIGHT.i], successorValue);
                }
            }
        }

        // Update balance factor and height keys.
        update(node);

        // Re-balance tree.
        return balance(node);
    }

    // Helper method to find the leftmost node (which has the smallest key)
    private int findMin(Node node) {
        while (node.children[ChildNames.LEFT.i] != null) node = node.children[ChildNames.LEFT.i];
        return node.key;
    }

    // Helper method to find the rightmost node (which has the largest key)
    private int findMax(Node node) {
        while (node.children[ChildNames.RIGHT.i] != null) node = node.children[ChildNames.RIGHT.i];
        return node.key;
    }

    // Returns as iterator to traverse the tree in order.
    // TODO replace/modify so that more traversals are possible. Can (?) be copied from BST.
    /*public java.util.Iterator<T> iterator() {

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

    // Make sure all left child nodes are smaller in key than their parent and
    // make sure all right child nodes are greater in key than their parent.
    // (Used only for testing)
    // TODO DEBUG REMOVE
    /*public boolean validateBSTInvarient(Node node) {
        if (node == null) return true;
        int val = node.key;
        boolean isValid = true;
        if (node.children[ChildNames.LEFT.i] != null) isValid = isValid && node.children[ChildNames.LEFT.i].key.compareTo(val) < 0;
        if (node.children[ChildNames.RIGHT.i] != null) isValid = isValid && node.children[ChildNames.RIGHT.i].key.compareTo(val) > 0;
        return isValid && validateBSTInvarient(node.children[ChildNames.LEFT.i]) && validateBSTInvarient(node.children[ChildNames.RIGHT.i]);

    }*/
}
