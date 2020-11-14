package com.example.datastructurevisualizer;

// https://github.com/williamfiset/data-structures/blob/master/com/williamfiset/datastructures/balancedtree/RedBlackTree.java
// TODO this tree is distributed under the MIT license
// TODO implement MIT license requirements
// http://www.codebytes.in/2014/10/red-black-tree-java-implementation.html

/**
 * This file contains an implementation of a Red-Black tree. A RB tree is a special type of binary
 * tree which self balances itself to keep operations logarithmic.
 *
 * For this tree Node.key is the integer key and Node.value is the boolean red/black value.
 *
 * <p>Great visualization tool: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
 *
 * For this tree Node.key is the integer key, Node.value is the colour, and
 * extraData[0] is a Node's parent.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class RedBlackTree extends TreeVisualizer {

    // Definitions for RED and BLACK.
    public static final int RED = 1;
    public static final int BLACK = 0;

    // Node used internally.
    private final Node nil = new Node(Integer.MIN_VALUE, getNumChildren());

    // Number of children per node in this tree.
    static final int numChildren = 2;

    // Tracks the number of nodes inside the tree.
    private int nodeCount = 0;

    /**
     * Return numChildren, which is 2. Used in TreeVisualize.
     */
    @Override
    int getNumChildren() { return numChildren; }

    /**
     * Inserts a key into this Red Black Tree. Performs no animation.
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {

        // If there is a duplicate, returns without performing an action.
        if(!checkInsert(key)) return;
        // If there is no duplicate, logs the insertion.
        else logAdd(key);

        // No root node.
        if (root == null) {
            root = new Node(key, numChildren);
            root.extraData = new Object[1];
            insertionRelabel(root);
            nodeCount++;
            return;

        }

        for (Node node = root; ; ) {

            // Left subtree.
            if (key < node.key) {
                if (node.children[ChildNames.LEFT.i] == null) {
                    node.children[ChildNames.LEFT.i] = new Node(key, numChildren);
                    node.children[ChildNames.LEFT.i].extraData = new Object[1];
                    node.children[ChildNames.LEFT.i].extraData[0] = node;
                    node.children[ChildNames.LEFT.i].value = RED;
                    insertionRelabel(node.children[ChildNames.LEFT.i]);
                    nodeCount++;
                    finalRender();
                    return;

                }
                node = node.children[ChildNames.LEFT.i];


            }
            // Right subtree.
            else if (key > node.key) {
                if (node.children[ChildNames.RIGHT.i] == null) {
                    node.children[ChildNames.RIGHT.i] = new Node(key, numChildren);
                    node.children[ChildNames.RIGHT.i].extraData = new Object[1];
                    node.children[ChildNames.RIGHT.i].extraData[0] = node;
                    node.children[ChildNames.RIGHT.i].value = RED;
                    insertionRelabel(node.children[ChildNames.RIGHT.i]);
                    nodeCount++;
                    finalRender();
                    return;
                }
                node = node.children[ChildNames.RIGHT.i];

                // The value we're trying to insert already exists in the tree.
            } else {
                finalRender();
                return;
            }
        }
    }

    /**
     * Inserts a key into this Red Black Tree and performs an animation.
     *
     * @param key the key to be inserted.
     */
    @Override
    protected void insertAnim(int key) {

        // If there is a duplicate, returns without performing an action.
        if(!checkInsert(key)) return;
        // If there is no duplicate, logs the insertion.
        else logAdd(key);

        // No root node.
        if (root == null) {

            // Creates root.
            root = new Node(key, numChildren);
            root.extraData = new Object[1];
            insertionRelabelAnim(root);
            nodeCount++;

            // Animates root insertion.
            placeTreeNodes();
            queueNodeMoveAnimation("Creating root",
                    AnimationParameters.ANIM_TIME);
            return;

        }

        // Selects root Node.
        queueNodeSelectAnimation(root, "Starting at root " + root.key,
                AnimationParameters.ANIM_TIME);

        for (Node node = root; ; ) {

            // Left subtree.
            if (key < node.key) {

                // Inserts Node if left child is null.
                if (node.children[ChildNames.LEFT.i] == null) {

                    // Creates the Node.
                    node.children[ChildNames.LEFT.i] = new Node(key, numChildren);
                    node.children[ChildNames.LEFT.i].extraData = new Object[1];
                    node.children[ChildNames.LEFT.i].extraData[0] = node;
                    node.children[ChildNames.LEFT.i].value = RED;

                    // Animates the Node's placement.
                    placeTreeNodes();
                    queueNodeMoveAnimation(key + " < " + node.key +
                            ", placing " + key + " as left child",
                            AnimationParameters.ANIM_TIME);

                    // Performs rotations.
                    insertionRelabelAnim(node.children[ChildNames.LEFT.i]);
                    nodeCount++;
                    return;

                }

                // Continues traversal if left child is non-null.
                queueNodeSelectAnimation(node.children[ChildNames.LEFT.i], key + " < " + node.key +
                        ", exploring left subtree", AnimationParameters.ANIM_TIME);
                node = node.children[ChildNames.LEFT.i];


            }
            // Right subtree.
            else if (key > node.key) {

                // Inserts Node if right child is null.
                if (node.children[ChildNames.RIGHT.i] == null) {

                    // Creates the Node.
                    node.children[ChildNames.RIGHT.i] = new Node(key, numChildren);
                    node.children[ChildNames.RIGHT.i].extraData = new Object[1];
                    node.children[ChildNames.RIGHT.i].extraData[0] = node;
                    node.children[ChildNames.RIGHT.i].value = RED;

                    // Animates the Node's placement.
                    placeTreeNodes();
                    queueNodeMoveAnimation(key + " > " + node.key +
                            ", placing " + key + " as right child",
                            AnimationParameters.ANIM_TIME);

                    // Performs rotations.
                    insertionRelabelAnim(node.children[ChildNames.RIGHT.i]);
                    nodeCount++;
                    return;

                }

                // Continues traversal if right child is non-null.
                queueNodeSelectAnimation(node.children[ChildNames.RIGHT.i], key + " > " + node.key +
                        ", exploring right subtree", AnimationParameters.ANIM_TIME);
                node = node.children[ChildNames.RIGHT.i];

            }
            // The value we're trying to insert already exists in the tree.
            else return;

        }
    }

    /**
     * Moves up the tree and animates the recolouring/rotation of Tree Nodes.
     *
     * TODO animate swapcolours?
     *
     * @param node the node to relabel.
     */
    private void insertionRelabelAnim(Node node) {
        Node parent = (Node)node.extraData[0];

        // Root node case.
        if (parent == null) {
            node.rbSetColour(BLACK);
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
            parent.rbSetColour(BLACK);
            grandParent.rbSetColour(RED);
            uncle.rbSetColour(BLACK);

        }
        // At this point the parent node is red and so is the new child node.
        // We need to re-balance somehow because no two red nodes can be
        // adjacent to one another.
        else {

            // Parent node is a left child.
            if (parentIsLeftChild) {

                // Left-left case.
                if (nodeIsLeftChild) {

                    // Right rotation.
                    grandParent = rightRotate(grandParent);
                    swapColors(grandParent, grandParent.children[ChildNames.RIGHT.i]);
                    if (grandParent.extraData[0] == null) root = grandParent;
                    placeTreeNodes();
                    queueNodeMoveAnimation("LL Right rotation",
                            AnimationParameters.ANIM_TIME);

                }
                // Left-right case.
                else {

                    // Left rotation.
                    grandParent.children[ChildNames.LEFT.i] = leftRotate(grandParent.children[ChildNames.LEFT.i]);
                    if (grandParent.extraData[0] == null) root = grandParent;
                    placeTreeNodes();
                    queueNodeMoveAnimation("LR Left rotation",
                            AnimationParameters.ANIM_TIME);

                    // Right rotation.
                    grandParent = rightRotate(grandParent);
                    swapColors(grandParent, grandParent.children[ChildNames.RIGHT.i]);
                    if (grandParent.extraData[0] == null) root = grandParent;
                    placeTreeNodes();
                    queueNodeMoveAnimation("LR Right rotation",
                            AnimationParameters.ANIM_TIME);

                }
            }
            // Parent node is a right child.
            else {

                // Right-left case.
                if (nodeIsLeftChild) {

                    // Right rotation.
                    grandParent.children[ChildNames.RIGHT.i] = rightRotate(grandParent.children[ChildNames.RIGHT.i]);
                    if (grandParent.extraData[0] == null) root = grandParent;
                    placeTreeNodes();
                    queueNodeMoveAnimation("RL Right rotation",
                            AnimationParameters.ANIM_TIME);

                    // Left rotation.
                    grandParent = leftRotate(grandParent);
                    swapColors(grandParent, grandParent.children[ChildNames.LEFT.i]);
                    if (grandParent.extraData[0] == null) root = grandParent;
                    placeTreeNodes();
                    queueNodeMoveAnimation("RL Left rotation",
                            AnimationParameters.ANIM_TIME);

                }
                // Right-right case.
                else {

                    // Left rotation.
                    grandParent = leftRotate(grandParent);
                    swapColors(grandParent, grandParent.children[ChildNames.LEFT.i]);
                    if (grandParent.extraData[0] == null) root = grandParent;
                    placeTreeNodes();
                    queueNodeMoveAnimation("RR Left rotation",
                            AnimationParameters.ANIM_TIME);

                }
            }
        }

        // Recursively relabels and rotates the tree.
        insertionRelabelAnim(grandParent);

    }

    private void insertionRelabel(Node node) {
        Node parent = (Node)node.extraData[0];

        // Root node case.
        if (parent == null) {
            node.rbSetColour(BLACK);
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
            parent.rbSetColour(BLACK);
            grandParent.rbSetColour(RED);
            uncle.rbSetColour(BLACK);

        }
        // At this point the parent node is red and so is the new child node.
        // We need to re-balance somehow because no two red nodes can be
        // adjacent to one another.
        else {

            // Parent node is a left child.
            if (parentIsLeftChild) {

                // Left-left case.
                if (nodeIsLeftChild) grandParent = leftLeftCase(grandParent);
                // Left-right case.
                else grandParent = leftRightCase(grandParent);

            }
            // Parent node is a right child.
            else {

                // Right-left case.
                if (nodeIsLeftChild) grandParent = rightLeftCase(grandParent);
                // Right-right case.
                else grandParent = rightRightCase(grandParent);

            }
        }

        insertionRelabel(grandParent);
    }

    // Adds nils.
    private void nils() {
        nil.rbSetColour(BLACK);
        nil.extraData = new Object[1];
        for (Node node : getAllNodes()) {
            if (node.key == Integer.MIN_VALUE) continue;
            if (node.extraData[0] == null) node.extraData[0] = nil;
            if (node.children[ChildNames.LEFT.i] == null)
                node.children[ChildNames.LEFT.i] = nil;
            if (node.children[ChildNames.RIGHT.i] == null)
                node.children[ChildNames.RIGHT.i] = nil;

        }
    }

    // Remove nils.
    private void unNils() {
        for (Node node : getAllNodes()) {
            if (node.key == Integer.MIN_VALUE) continue;
            if (node.extraData[0] == nil) node.extraData[0] = null;
            if (node.children[ChildNames.LEFT.i] == nil)
                node.children[ChildNames.LEFT.i] = null;
            if (node.children[ChildNames.RIGHT.i] == nil)
                node.children[ChildNames.RIGHT.i] = null;

        }
    }

    /**
     * TODO comment
     * TODO implement
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeNoAnim(int key) {
        if (!contains(key)) return;

        // Logs removal.
        logRemove(key);

        nils();
        Node del = getNode(key);
        if (del != null) removeNoAnim(del);
        unNils();

        // Renders after removal.
        finalRender();

    }

    /**
     * TODO comment
     * TODO animate
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeAnim(int key) {
        if (!contains(key)) return;

        // Logs removal.
        logRemove(key);

        nils();
        Node del = getNode(key);
        if (del != null) removeAnim(del);
        unNils();

        // Renders after removal.
        finalRender();

    }

    //This operation doesn't care about the new Node's connections
    //with previous node's left and right. The caller has to take care
    //of that.
    void transplant(Node target, Node with){
        if(target.extraData[0] == nil){
            root = with;
        }else if(target == ((Node)target.extraData[0]).children[ChildNames.LEFT.i]){
            ((Node)target.extraData[0]).children[ChildNames.LEFT.i] = with;
        }else ((Node)target.extraData[0]).children[ChildNames.RIGHT.i] = with;
        with.extraData[0] = target.extraData[0];
    }

    boolean removeNoAnim(Node z) {
        Node x;
        Node y = z; // temporary reference y
        int y_original_color = y.value;

        if(z.children[ChildNames.LEFT.i] == nil){
            x = z.children[ChildNames.RIGHT.i];
            transplant(z, z.children[ChildNames.RIGHT.i]);
        }else if(z.children[ChildNames.RIGHT.i] == nil){
            x = z.children[ChildNames.LEFT.i];
            transplant(z, z.children[ChildNames.LEFT.i]);
        }else{
            y = treeMinimum(z.children[ChildNames.RIGHT.i]);
            y_original_color = y.value;
            x = y.children[ChildNames.RIGHT.i];
            if(y.extraData[0] == z)
                x.extraData[0] = y;
            else{
                transplant(y, y.children[ChildNames.RIGHT.i]);
                y.children[ChildNames.RIGHT.i] = z.children[ChildNames.RIGHT.i];
                y.children[ChildNames.RIGHT.i].extraData[0] = y;
            }
            transplant(z, y);
            y.children[ChildNames.LEFT.i] = z.children[ChildNames.LEFT.i];
            y.children[ChildNames.LEFT.i].extraData[0] = y;
            y.rbSetColour(z.value);
        }
        if(y_original_color==BLACK)
            deleteFixup(x);
        return true;
    }

    boolean removeAnim(Node z) {
        Node x;
        Node y = z;
        int y_original_color = y.value;

        if (z.children[ChildNames.LEFT.i] == nil &&
                z.children[ChildNames.RIGHT.i] == nil) {
            x = z.children[ChildNames.RIGHT.i];
            transplant(z, z.children[ChildNames.RIGHT.i]);
            placeTreeNodes();
            queueNodeMoveAnimation("Delete childless Node",
                    AnimationParameters.ANIM_TIME);

        }
        else if(z.children[ChildNames.LEFT.i] == nil){
            x = z.children[ChildNames.RIGHT.i];
            transplant(z, z.children[ChildNames.RIGHT.i]);
            placeTreeNodes();
            queueNodeMoveAnimation("Delete Node with only right child",
                    AnimationParameters.ANIM_TIME);
        }else if(z.children[ChildNames.RIGHT.i] == nil){
            x = z.children[ChildNames.LEFT.i];
            transplant(z, z.children[ChildNames.LEFT.i]);
            placeTreeNodes();
            queueNodeMoveAnimation("Delete Node with only left child",
                    AnimationParameters.ANIM_TIME);
        }else{
            queueNodeSelectAnimation(z.children[ChildNames.RIGHT.i],
                    "Finding successor from " + z.children[ChildNames.RIGHT.i].key,
                    AnimationParameters.ANIM_TIME);
            y = treeMinimumAnim(z.children[ChildNames.RIGHT.i]);
            y_original_color = y.value;
            x = y.children[ChildNames.RIGHT.i];
            if(y.extraData[0] == z)
                x.extraData[0] = y;
            else{
                transplant(y, y.children[ChildNames.RIGHT.i]);
                y.children[ChildNames.RIGHT.i] = z.children[ChildNames.RIGHT.i];
                y.children[ChildNames.RIGHT.i].extraData[0] = y;

            }
            transplant(z, y);
            y.children[ChildNames.LEFT.i] = z.children[ChildNames.LEFT.i];
            y.children[ChildNames.LEFT.i].extraData[0] = y;
            y.rbSetColour(z.value);
            placeTreeNodes();
            queueNodeMoveAnimation("Placing successor",
                    AnimationParameters.ANIM_TIME);
        }
        if(y_original_color==BLACK)
            deleteFixupAnim(x);
        return true;
    }

    Node treeMinimum(Node subTreeRoot){
        while(subTreeRoot.children[ChildNames.LEFT.i] != nil){
            subTreeRoot = subTreeRoot.children[ChildNames.LEFT.i];
        }
        return subTreeRoot;
    }

    Node treeMinimumAnim(Node subTreeRoot){
        while(subTreeRoot.children[ChildNames.LEFT.i] != nil){
            subTreeRoot = subTreeRoot.children[ChildNames.LEFT.i];
            if (subTreeRoot != null) queueNodeSelectAnimation(subTreeRoot,
                    "Searching left child " + subTreeRoot.key,
                    AnimationParameters.ANIM_TIME);
        }
        return subTreeRoot;
    }

    void deleteFixupAnim(Node x){
        while(x!=root && x.value == BLACK){
            if(x == ((Node)x.extraData[0]).children[ChildNames.LEFT.i]){
                Node w = ((Node)x.extraData[0]).children[ChildNames.RIGHT.i];
                if(w.value == RED){
                    w.rbSetColour(BLACK);
                    ((Node)x.extraData[0]).rbSetColour(RED);
                    rotateLeft((Node)x.extraData[0]);
                    w = ((Node)x.extraData[0]).children[ChildNames.RIGHT.i];
                    placeTreeNodes();
                    queueNodeMoveAnimation("Rotate left",
                            AnimationParameters.ANIM_TIME);

                }
                if(w.children[ChildNames.LEFT.i].value == BLACK && w.children[ChildNames.RIGHT.i].value == BLACK){
                    w.rbSetColour(RED);
                    x = (Node)x.extraData[0];
                    continue;
                }
                else if(w.children[ChildNames.RIGHT.i].value == BLACK){
                    w.children[ChildNames.LEFT.i].rbSetColour(BLACK);
                    w.rbSetColour(RED);
                    rotateRight(w);
                    w = ((Node)x.extraData[0]).children[ChildNames.RIGHT.i];
                    placeTreeNodes();
                    queueNodeMoveAnimation("Rotate right",
                            AnimationParameters.ANIM_TIME);

                }
                if(w.children[ChildNames.RIGHT.i].value == RED){
                    w.rbSetColour(((Node)x.extraData[0]).value);
                    ((Node)x.extraData[0]).rbSetColour(BLACK);
                    w.children[ChildNames.RIGHT.i].rbSetColour(BLACK);
                    rotateLeft((Node)x.extraData[0]);
                    x = root;
                    placeTreeNodes();
                    queueNodeMoveAnimation("Rotate left",
                            AnimationParameters.ANIM_TIME);

                }
            }else{
                Node w = ((Node)x.extraData[0]).children[ChildNames.LEFT.i];
                if(w.value == RED){
                    w.rbSetColour(BLACK);
                    ((Node)x.extraData[0]).rbSetColour(RED);
                    rotateRight((Node)x.extraData[0]);
                    w = ((Node)x.extraData[0]).children[ChildNames.LEFT.i];
                    placeTreeNodes();
                    queueNodeMoveAnimation("Rotate right",
                            AnimationParameters.ANIM_TIME);

                }
                if(w.children[ChildNames.RIGHT.i].value == BLACK && w.children[ChildNames.LEFT.i].value == BLACK){
                    w.rbSetColour(RED);
                    x = (Node)x.extraData[0];
                    continue;
                }
                else if(w.children[ChildNames.LEFT.i].value == BLACK){
                    w.children[ChildNames.RIGHT.i].rbSetColour(BLACK);
                    w.rbSetColour(RED);
                    rotateLeft(w);
                    w = ((Node)x.extraData[0]).children[ChildNames.LEFT.i];
                    placeTreeNodes();
                    queueNodeMoveAnimation("Rotate left",
                            AnimationParameters.ANIM_TIME);

                }
                if(w.children[ChildNames.LEFT.i].value == RED){
                    w.rbSetColour(((Node)x.extraData[0]).value);
                    ((Node)x.extraData[0]).rbSetColour(BLACK);
                    w.children[ChildNames.LEFT.i].rbSetColour(BLACK);
                    rotateRight((Node)x.extraData[0]);
                    x = root;
                    placeTreeNodes();
                    queueNodeMoveAnimation("Rotate right",
                            AnimationParameters.ANIM_TIME);

                }
            }
        }
        x.rbSetColour(BLACK);
    }

    void deleteFixup(Node x){
        while(x!=root && x.value == BLACK){
            if(x == ((Node)x.extraData[0]).children[ChildNames.LEFT.i]){
                Node w = ((Node)x.extraData[0]).children[ChildNames.RIGHT.i];
                if(w.value == RED){
                    w.rbSetColour(BLACK);
                    ((Node)x.extraData[0]).rbSetColour(RED);
                    rotateLeft((Node)x.extraData[0]);
                    w = ((Node)x.extraData[0]).children[ChildNames.RIGHT.i];
                }
                if(w.children[ChildNames.LEFT.i].value == BLACK && w.children[ChildNames.RIGHT.i].value == BLACK){
                    w.rbSetColour(RED);
                    x = (Node)x.extraData[0];
                    continue;
                }
                else if(w.children[ChildNames.RIGHT.i].value == BLACK){
                    w.children[ChildNames.LEFT.i].rbSetColour(BLACK);
                    w.rbSetColour(RED);
                    rotateRight(w);
                    w = ((Node)x.extraData[0]).children[ChildNames.RIGHT.i];
                }
                if(w.children[ChildNames.RIGHT.i].value == RED){
                    w.rbSetColour(((Node)x.extraData[0]).value);
                    ((Node)x.extraData[0]).rbSetColour(BLACK);
                    w.children[ChildNames.RIGHT.i].rbSetColour(BLACK);
                    rotateLeft((Node)x.extraData[0]);
                    x = root;
                }
            }else{
                Node w = ((Node)x.extraData[0]).children[ChildNames.LEFT.i];
                if(w.value == RED){
                    w.rbSetColour(BLACK);
                    ((Node)x.extraData[0]).rbSetColour(RED);
                    rotateRight((Node)x.extraData[0]);
                    w = ((Node)x.extraData[0]).children[ChildNames.LEFT.i];
                }
                if(w.children[ChildNames.RIGHT.i].value == BLACK && w.children[ChildNames.LEFT.i].value == BLACK){
                    w.rbSetColour(RED);
                    x = (Node)x.extraData[0];
                    continue;
                }
                else if(w.children[ChildNames.LEFT.i].value == BLACK){
                    w.children[ChildNames.RIGHT.i].rbSetColour(BLACK);
                    w.rbSetColour(RED);
                    rotateLeft(w);
                    w = ((Node)x.extraData[0]).children[ChildNames.LEFT.i];
                }
                if(w.children[ChildNames.LEFT.i].value == RED){
                    w.rbSetColour(((Node)x.extraData[0]).value);
                    ((Node)x.extraData[0]).rbSetColour(BLACK);
                    w.children[ChildNames.LEFT.i].rbSetColour(BLACK);
                    rotateRight((Node)x.extraData[0]);
                    x = root;
                }
            }
        }
        x.rbSetColour(BLACK);
    }

    void rotateLeft(Node node) {
        if (node.extraData[0] != nil) {
            if (node == ((Node)node.extraData[0]).children[ChildNames.LEFT.i]) {
                ((Node)node.extraData[0]).children[ChildNames.LEFT.i] = node.children[ChildNames.RIGHT.i];
            } else {
                ((Node)node.extraData[0]).children[ChildNames.RIGHT.i] = node.children[ChildNames.RIGHT.i];
            }
            node.children[ChildNames.RIGHT.i].extraData[0] = node.extraData[0];
            node.extraData[0] = node.children[ChildNames.RIGHT.i];
            if (node.children[ChildNames.RIGHT.i].children[ChildNames.LEFT.i] != nil) {
                node.children[ChildNames.RIGHT.i].children[ChildNames.LEFT.i].extraData[0] = node;
            }
            node.children[ChildNames.RIGHT.i] = node.children[ChildNames.RIGHT.i].children[ChildNames.LEFT.i];
            ((Node)node.extraData[0]).children[ChildNames.LEFT.i] = node;
        } else {//Need to rotate root
            Node right = root.children[ChildNames.RIGHT.i];
            root.children[ChildNames.RIGHT.i] = right.children[ChildNames.LEFT.i];
            right.children[ChildNames.LEFT.i].extraData[0] = root;
            root.extraData[0] = right;
            right.children[ChildNames.LEFT.i] = root;
            right.extraData[0] = nil;
            root = right;
        }
    }

    void rotateRight(Node node) {
        if (node.extraData[0] != nil) {
            if (node == ((Node)node.extraData[0]).children[ChildNames.LEFT.i]) {
                ((Node)node.extraData[0]).children[ChildNames.LEFT.i] = node.children[ChildNames.LEFT.i];
            } else {
                ((Node)node.extraData[0]).children[ChildNames.RIGHT.i] = node.children[ChildNames.LEFT.i];
            }

            node.children[ChildNames.LEFT.i].extraData[0] = node.extraData[0];
            node.extraData[0] = node.children[ChildNames.LEFT.i];
            if (node.children[ChildNames.LEFT.i].children[ChildNames.RIGHT.i] != nil) {
                node.children[ChildNames.LEFT.i].children[ChildNames.RIGHT.i].extraData[0] = node;
            }
            node.children[ChildNames.LEFT.i] = node.children[ChildNames.LEFT.i].children[ChildNames.RIGHT.i];
            ((Node)node.extraData[0]).children[ChildNames.RIGHT.i] = node;
        } else {//Need to rotate root
            Node left = root.children[ChildNames.LEFT.i];
            root.children[ChildNames.LEFT.i] = root.children[ChildNames.LEFT.i].children[ChildNames.RIGHT.i];
            left.children[ChildNames.RIGHT.i].extraData[0] = root;
            root.extraData[0] = left;
            left.children[ChildNames.RIGHT.i] = root;
            left.extraData[0] = nil;
            root = left;
        }
    }

    private void swapColors(Node a, Node b) {
        int tmpColor = a.value;
        a.rbSetColour(b.value);
        b.rbSetColour(tmpColor);
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
        Node child = parent.children[ChildNames.RIGHT.i];

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
}