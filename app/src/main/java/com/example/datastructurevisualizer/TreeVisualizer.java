package com.example.datastructurevisualizer;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

/**
 * Superclass for all trees. Enables code reuse among tree visualization.
 * Contains numChildren, a field indicating the number of children per node for a given tree.
 * Contains root, the root node for a given tree. It is located here so as to be used by
 * shared visualization methods.
 * Contains pre-order, post-order, and in-order traversals.
 * Contains a function for placing each Node in the tree at an appropriate position.
 */
public class TreeVisualizer extends NodeVisualizer {

    // Root of this tree.
    Node root;

    // Log of additions and deletions into this tree.
    ArrayList<TreeAction> log = new ArrayList<TreeAction>();

    // Current position within the log.
    int logIndex = 0;

    // Whether or not the log can be edited.
    boolean logAvailable = true;

    // Log of animations that have happened during the most recent animation.
    ArrayList<AnimationItem> animationLog = new ArrayList<AnimationItem>();

    /**
     * This method is used to get the number of children in a tree.
     * Each tree will override it to return its own numChildren.
     */
    int getNumChildren() { return 0; }

    /**
     * Inserts a Node into the tree and plays no animation. Should be overridden.
     *
     * @param key the key to be inserted.
     */
    public void insertNoAnim(int key) {}

    /**
     * Inserts a Node into the tree and plays an animation. Should be overridden.
     *
     * @param key the key to be inserted.
     */
    protected void insertAnim(int key) {}

    /**
     * Removes a Node from the tree abd plays no animation. Should be overridden.
     *
     * @param key the key to be removed.
     */
    protected void removeNoAnim(int key) {}

    /**
     * Removes a Node from the tree and plays an animation. Should be overridden.
     *
     * @param key the key to be removed.
     */
    protected void removeAnim(int key) {}

    /**
     * Runs an insert animation.
     */
    public class RunInsert implements Runnable {
        int key;
        @Override
        public void run() {
            AnimationParameters.beginAnimation();
            insertAnim(key);
            quickRender();
            //postOrderTraversal();
            AnimationParameters.stopAnimation();

        }
    }

    /**
     * Inserts a Node into the tree.
     *
     * @param key the key to be inserted.
     */
    public void insert(int key) {
        RunInsert run = new RunInsert();
        run.key = key;
        new Thread(run).start();

    }

    /**
     * Runs a removal animation.
     */
    public class RunRemove implements Runnable {
        int key;
        @Override
        public void run() {
            AnimationParameters.beginAnimation();
            removeAnim(key);
            quickRender();
            AnimationParameters.stopAnimation();

        }
    }

    /**
     * Removes a Node from the tree.
     *
     * @param key the key to be removed.
     */
    public void remove(int key) {
        RunRemove run = new RunRemove();
        run.key = key;
        new Thread(run).start();

    }

    /**
     * Performs a pre-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treePreOrderTraversal(final Node currNode) {
        int numChildren = getNumChildren();

        // Returns if currNode is null.
        if (currNode == null) return;

        // Highlights the current Node.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final Node NODE = currNode;
                nodeSelectAnimation(NODE);
            }
        });

        // Sleeps for a little while.
        try {
            if (!Thread.currentThread().equals(Looper.getMainLooper().getThread()));{
            Thread.sleep((long) (AnimationParameters.ANIM_TIME * AnimationParameters.animSpeed));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePreOrderTraversal(currNode.children[i]);

    }

    /**
     * Runs a pre-order traversal.
     */
    public class RunPreOrder implements Runnable {
        @Override
        public void run() {
            treePreOrderTraversal(root);

        }
    }

    /**
     * Begins a pre-order traversal.
     */
    void preOrderTraversal() {
        RunPreOrder run = new RunPreOrder();
        new Thread(run).start();

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

        // Returns if currNode is null.
        if (currNode == null) return;

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Highlights the current Node.
        nodeSelectAnimation(currNode);

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

        // Returns if currNode is null.
        if (currNode == null) return;

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Highlights the current Node.
        nodeSelectAnimation(currNode);

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
     * @param currNode the Node whose children should be placed.
     */
    void placeTreeNodesRecursive(float width, int depth, Node currNode) {
        float currX, currY;
        int numChildren;

        // Returns if the bottom of the Tree has been reached.
        if (depth == 0 || currNode == null) return;

        // Stores the number of children for measurement.
        numChildren = getNumChildren();

        // Starts from the current position.
        currX = currNode.destination[0];
        currY = currNode.destination[1];

        // Offsets currX to the leftmost Node.
        // Note: offsets slightly more than appropriate so the for loop below is easier to write.
        currX -= (int)((width * (1.0 + numChildren)) / 2.0);

        // Offsets currY by depthLen.
        currY += AnimationParameters.depthLen;

        // Recursively places each child Node.
        for (int i = 0; i < numChildren; ++i) {
            currX += width;

            // Will only place non-null nodes.
            if (currNode.children[i] != null) {
                currNode.children[i].destination[0] = (int)currX;
                currNode.children[i].destination[1] = (int)currY;
                placeTreeNodesRecursive(width / numChildren, depth - 1, currNode.children[i]);

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
     */
    public void placeTreeNodes() {
        System.out.println("Canvas");
        System.out.println(MainActivity.getVisualizer().getCanvas());
        System.out.println("Visualizer");
        System.out.println(MainActivity.getVisualizer());
        int treeWidth = MainActivity.getVisualizer().getCanvas().getWidth();
        //int treeWidth = MainActivity.getCanvas().getWidth();
        int numChildren = getNumChildren();
        int depth = getDepth();
        float width;

        // Initializes position of root.
        root.destination[0] = treeWidth / 2;
        root.destination[1] = 40;

        // Calculates the width between children of the root Node.
        width = (float)treeWidth / numChildren;

        // If rendering a LinkedList, sets width to 0 for convenience.
        if (numChildren == 1) width = 0;

        // Begins recursively placing the Tree Nodes.
        placeTreeNodesRecursive(width, depth, root);

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
        colour.setARGB(255, AnimationParameters.VEC_R, AnimationParameters.VEC_G, AnimationParameters.VEC_B);
        for (int i = 0; i < getNumChildren(); ++i) {
            if (currNode.children[i] != null) {
                MainActivity.getVisualizer().getCanvas().drawLine(
                        currNode.position[0], currNode.position[1],
                        currNode.children[i].position[0], currNode.children[i].position[1],
                        colour);
                MainActivity.getVisualizer().getCanvas().save(); //TODO test this
//                MainActivity.getCanvas().drawLine(
//                        currNode.position[0], currNode.position[1],
//                        currNode.children[i].position[0], currNode.children[i].position[1],
//                        colour);
//                MainActivity.getCanvas().save(); //TODO test this

            }
        }

        // Draws the current Node.
        drawNode(currNode);

        // Draws all child Nodes.
        for (int i = 0; i < getNumChildren(); ++i)
            drawTreeRecursive(currNode.children[i]);

    }

    /**
     * Renders the tree starting at the root.
     */
    @Override
    public void render() {
        super.render();

        // Makes the entire Canvas White.
        MainActivity.getVisualizer().getCanvas().drawRGB(AnimationParameters.BACK_R,
                AnimationParameters.BACK_G, AnimationParameters.BACK_B);

        // Draws the Tree over the Canvas.
        drawTreeRecursive(root);

    }

    /**
     * Quickly places all nodes and renders the tree.
     * To be used at the end of insertions, deletions, and traversals.
     */
    protected void quickRender() {
        finishTraversalAnimation();
        placeTreeNodes();
        placeNodesAtDestination();
        render();

    }

    /**
     * Recursively parses through the tree to fill an ArrayList of nodes.
     *
     * @param currNode the current Node being viewed.
     * @return an ArrayList containing all children in this Node's subtrees.
     */
    private ArrayList<Node> getAllNodesRecursive(Node currNode) {
        ArrayList<Node> nodes = new ArrayList<Node>();

        // Returns an empty arrayList if this Node is null.
        if (currNode == null) return new ArrayList<Node>();

        // Adds all subtrees to nodes.
        for (int i = 0; i < getNumChildren(); ++i) {
            nodes.addAll(getAllNodesRecursive(currNode.children[i]));

        }

        // Adds this node to nodes.
        nodes.add(currNode);

        // Returns the ArrayList of Nodes.
        return nodes;

    }

    /**
     * Returns an ArrayList containing all Nodes in this data structure.
     *
     * @return an ArrayList containing all Nodes in this data structure.
     */
    public ArrayList<Node> getAllNodes() {
        return getAllNodesRecursive(root);

    }

    /**
     * Checks whether the key being inserted is a duplicate
     *
     * @param key the value being inserted
     * @return true if key is not a duplicate, false if it is a duplicate
     */
    public boolean checkInsert(int key){
        ArrayList<Node> currNodes = getAllNodes();

        //loop through all nodes in data structure to see if duplicate exists. return false if so
        for (int i = 0; i < currNodes.size(); i++) {
            if(currNodes.get(i).key == key){
                return false;
            }
        }
        //made it this far, there must be no duplicates, return true
        return true;
    }

    /**
     * Recursively parses through the tree to calculate its maximum depth.
     *
     * @param currNode the current Node being viewed.
     * @return the maximum depth of this Node's subtree.
     */
    private int getDepthRecursive(Node currNode) {
        int max = 0;
        int val;

        // Return 0 if this Node is null.
        if (currNode == null) return 0;

        // Finds the maximum depth of this Node's subtrees.
        for (int i = 0; i < getNumChildren(); ++i) {
            val = getDepthRecursive(currNode.children[i]);
            max = max < val ? val : max;

        }

        // Returns the maximum depth of this Node's subtree plus one.
        return max + 1;

    }

    /**
     * Returns the depth of this tree.
     */
    public int getDepth() {
        return getDepthRecursive(root);

    }

    /**
     * Class representing an addition or deletion performed in the Tree.
     */
    private class TreeAction {
        int key;

        /**
         * @param key the key for this action.
         */
        TreeAction(int key) { this.key = key; }

        /**
         * Performs this TreeAction's action. Should be overridden.
         */
        void action() {}

    }

    /**
     * Class representing an insertion into the Tree.
     */
    private class TreeAdd extends TreeAction {
        TreeAdd(int key) { super(key); }

        /**
         * Inserts the stored key into the Tree.
         */
        void action() { insertNoAnim(key); }

    }

    /**
     * Class representing a deletion from the Tree.
     */
    private class TreeRemove extends TreeAction {
        TreeRemove(int key) { super(key); }

        /**
         * Removes the stored key from the Tree.
         */
        void action() { insertNoAnim(key); }

    }

    /**
     * Logs an addition to the Tree.
     *
     * @param key the key that has been added.
     */
    protected void logAdd(int key) {

        // Will not add to the log if it is unavailable.
        if (!logAvailable) return;

        // Removes any items which are ahead of the current index.
        while (logIndex < log.size()) log.remove(logIndex);

        // Adds the item to the log.
        ++logIndex;
        log.add(new TreeAdd(key));

    }

    /**
     * Logs a deletion from the Tree.
     *
     * @param key the key that has been removed.
     */
    protected void logRemove(int key) {

        // Will not add to the log if it is unavailable.
        if (!logAvailable) return;

        // Adds the item to the log.
        ++logIndex;
        log.add(new TreeRemove(key));

    }

    /**
     * Redoes an action.
     */
    public void redo() {

        // Will redo if and only if there is something to be redone.
        if (logIndex < log.size()) {

            // Marks the log as unavailable.
            logAvailable = false;

            // Clears the tree.
            root = null;

            // Rebuilds the tree.
            ++logIndex;
            for (int i = 0; i < logIndex; ++i) log.get(i).action();

            // Marks the log as available.
            logAvailable = true;

        }
    }

    /**
     * Undoes the previous action.
     */
    public void undo() {

        // Marks the log as unavailable.
        logAvailable = false;

        // Clears the tree.
        root = null;

        // Rebuilds the tree.
        logIndex = logIndex - 1 < 0 ? 0 : logIndex - 1;
        for (int i = 0; i < logIndex; ++i) log.get(i).action();

        // Marks the log as available.
        logAvailable = true;

    }

    /**
     * Animation item for highlighting a Node.
     */
    private class HighlightNode implements AnimationItem {
        Node highlighted;

        /**
         * Highlights this Node.
         */
        @Override
        public void run() { nodeSelectAnimation(highlighted); }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Animation item for highlighting a Node.
     */
    private class MoveTree implements AnimationItem {
        Map<Integer, Integer[][]> keyPos;

        // Defs to help with movement.
        static final int pos = 0;
        static final int dest = 1;
        static final int x = 0;
        static final int y = 1;
        static final int arrSize = 2;

        /**
         * Moves the tree.
         */
        @Override
        public void run() {

            // Sets each key

            nodeMoveAnimation();

        }

        /**
         * Reverses the movement.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Performs all animations in the animation queue.
     */
    public void animate() {

    }
}