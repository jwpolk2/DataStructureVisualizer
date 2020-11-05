package com.example.datastructurevisualizer;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.datastructurevisualizer.ui.Visualizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Superclass for all trees. Enables code reuse during tree visualization.
 *
 * Contains numChildren, a field indicating the number of children per node for a given tree.
 * Contains root, the root node for a given tree. It is located here so as to be used by
 * shared visualization methods.
 * Contains pre-order, post-order, in-order, and breadth-first traversals.
 * Contains placeNodes, which places each Node in the tree at an appropriate
 * position. Will place the root at the inputed position, or at the upper
 * center of the screen if no position is inputed.
 * Redefines getAllNodes and getNode to function over a Tree.
 * Defines getDepth and checkInsert to prevent invalid entries into the tree.
 * Redefines render to recursively render Tree Nodes and edges between them.
 * Contains a method createJSON which is used to save the Tree.
 */
public class TreeVisualizer extends NodeVisualizer {

    // Root of this tree.
    Node root;

    /**
     * This method is used to get the number of children in a tree.
     * Each tree will override it to return its own numChildren.
     */
    int getNumChildren() { return 0; }

    /**
     * Performs a pre-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted and the stack of explored Nodes.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treePreOrderTraversal(final Node currNode) {
        int numChildren = getNumChildren();

        // Returns if currNode is null.
        if (currNode == null) return;

        // Adds this Node to the stack.
        queueStackAddAnimation(currNode, "Exploring " + currNode.key);

        // Highlights the current Node.
        queueNodeSelectAnimation(currNode, "Current Node " + currNode.key);

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePreOrderTraversal(currNode.children[i]);

        // Removes this Node from the stack.
        queueListPopAnimation("Finished exploring " + currNode.key);

    }

    /**
     * Runs a pre-order traversal.
     */
    private class RunPreOrder implements Runnable {
        @Override
        public void run() {
            beginAnimation();
            treePreOrderTraversal(root);
            animate();
            stopAnimation();

        }
    }

    /**
     * Begins a pre-order traversal.
     */
    public void preOrderTraversal() {
        RunPreOrder run = new RunPreOrder();
        new Thread(run).start();

    }

    /**
     * Performs a post-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted and the stack of explored Nodes.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treePostOrderTraversal(Node currNode) {
        int numChildren = getNumChildren();

        // Returns if currNode is null.
        if (currNode == null) return;

        // Adds this Node to the stack.
        queueStackAddAnimation(currNode, "Exploring " + currNode.key);

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treePostOrderTraversal(currNode.children[i]);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treePostOrderTraversal(currNode.children[i]);

        // Highlights the current Node.
        queueNodeSelectAnimation(currNode, "Current Node " + currNode.key);

        // Removes this Node from the stack.
        queueListPopAnimation("Finished exploring " + currNode.key);

    }

    /**
     * Runs a post-order traversal.
     */
    private class RunPostOrder implements Runnable {
        @Override
        public void run() {
            beginAnimation();
            treePostOrderTraversal(root);
            animate();
            stopAnimation();

        }
    }

    /**
     * Begins a post-order traversal.
     */
    public void postOrderTraversal() {
        RunPostOrder run = new RunPostOrder();
        new Thread(run).start();

    }

    /**
     * Performs an in-order traversal over a tree. Will perform an animation
     * indicating the current node being targeted and the stack of explored Nodes.
     *
     * @param currNode the node currently targeted by the traversal.
     */
    private void treeInOrderTraversal(Node currNode) {
        int numChildren = getNumChildren();

        // Returns if currNode is null.
        if (currNode == null) return;

        // Adds this Node to the stack.
        queueStackAddAnimation(currNode, "Exploring " + currNode.key);

        // Explores left subtree.
        for (int i = 0; i < numChildren / 2; ++i)
            treeInOrderTraversal(currNode.children[i]);

        // Highlights the current Node.
        queueNodeSelectAnimation(currNode, "Current Node " + currNode.key);

        // Explores right subtree.
        for (int i = numChildren / 2; i < numChildren; ++i)
            treeInOrderTraversal(currNode.children[i]);

        // Removes this Node from the stack.
        queueListPopAnimation("Finished exploring " + currNode.key);

    }

    /**
     * Runs a post-order traversal.
     */
    private class RunInOrder implements Runnable {
        @Override
        public void run() {
            beginAnimation();
            treeInOrderTraversal(root);
            animate();
            stopAnimation();

        }
    }

    /**
     * Begins an in-order traversal.
     */
    public void inOrderTraversal() {
        RunInOrder run = new RunInOrder();
        new Thread(run).start();

    }

    /**
     * Performs a breadth-first traversal over a tree. Will perform an animation
     * indicating the current node being targeted and the queue of Nodes to explore.
     *
     * @param currNode the first Node in the traversal.
     */
    private void treeBreadthFirstTraversal(Node currNode) {
        java.util.LinkedList<Node> queue = new java.util.LinkedList<Node>();

        // Highlights the first Node.
        queueNodeSelectAnimation(currNode, "Exploring " + currNode.key);

        // Explores Nodes until the queue is empty.
        while (currNode != null) {

            // Marks that this Node's children should be explored.
            for (int i = 0; i < getNumChildren(); ++i) {
                if (currNode.children[i] != null) {
                    queue.addLast(currNode);
                    queueQueueAddAnimation(currNode.children[i], "Queueing " + currNode.key);

                }
            }

            // Pops the next Node from the queue.
            currNode = queue.pop();
            queueListPopAnimation("Popped " + currNode.key);
            queueNodeSelectAnimation(currNode, "Exploring " + currNode.key);

        }
    }

    /**
     * Runs a breadth-first traversal.
     */
    private class RunBreadthFirst implements Runnable {
        @Override
        public void run() {
            beginAnimation();
            treeBreadthFirstTraversal(root);
            animate();
            stopAnimation();

        }
    }

    /**
     * Begins a breadth-first traversal.
     */
    public void breadthFirstTraversal() {
        RunBreadthFirst run = new RunBreadthFirst();
        new Thread(run).start();

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
    private void placeTreeNodesRecursive(float width, int depth, Node currNode) {
        float currX, currY;
        int numChildren;

        // Returns if the bottom of the Tree has been reached.
        if (depth <= 0 || currNode == null) return;

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
     *
     * @param xStart the x position of the root.
     * @param yStart the y position of the root.
     */
    public void placeTreeNodes(int xStart, int yStart) {
        int treeWidth = 0;
        int numChildren = getNumChildren();
        int depth = getDepth();
        float width;

        // Will not execute if the tree is empty.
        if (root == null) return;

        // Tree will retain width 0 when empty so that null pointer is not called in test cases.
        if (MainActivity.getVisualizer() != null)
            treeWidth = MainActivity.getVisualizer().getCanvas().getWidth();

        // Initializes position of root.
        root.destination[0] = xStart;
        root.destination[1] = yStart;

        // Calculates the width between children of the root Node.
        width = (float)treeWidth / numChildren;

        // If rendering a LinkedList, sets width to 0 for convenience.
        if (numChildren == 1) width = 0;

        // Begins recursively placing the Tree Nodes.
        placeTreeNodesRecursive(width, depth, root);

    }

    /**
     * Places Nodes in the tree. Starts at the center of the tree, depthlen pixels from the top.
     */
    public void placeTreeNodes() {
        placeTreeNodes(MainActivity.getVisualizer().getCanvas().getWidth() / 2,
                (int)AnimationParameters.depthLen / 5);

    }

    /**
     * Recursively draws Nodes in a tree. Does so by drawing the vectors
     * between currNode and its children, then drawing currNode, then
     * drawing currNode's children.
     *
     * @param currNode the current Node to be drawn.
     */
    protected void drawTreeRecursive(Node currNode, Canvas canvas) {

        // Returns if currNode is null.
        if (currNode == null) return;

        // Draws vectors between this Node and all child Nodes.
        Paint colour = new Paint();
        colour.setARGB(255, AnimationParameters.VEC_R, AnimationParameters.VEC_G, AnimationParameters.VEC_B);
        for (int i = 0; i < getNumChildren(); ++i) {
            if (currNode.children[i] != null) {
                canvas.drawLine(
                        currNode.position[0], currNode.position[1],
                        currNode.children[i].position[0], currNode.children[i].position[1],
                        colour);

            }
        }

        // Draws the current Node.
        drawNode(currNode, canvas);

        // Draws all child Nodes.
        for (int i = 0; i < getNumChildren(); ++i)
            drawTreeRecursive(currNode.children[i], canvas);

    }

    /**
     * Renders the tree to the inputed canvas, starting at the root.
     * Will also render the nodeList.
     *
     * @param canvas the Canvas to draw in.
     */
    @Override
    public void render(Canvas canvas) {

        // Makes the entire Canvas White.
        canvas.drawRGB(AnimationParameters.BACK_R,
                AnimationParameters.BACK_G, AnimationParameters.BACK_B);

        // Draws the Tree over the Canvas.
        drawTreeRecursive(root, canvas);

        // Draws the nodeList over the Canvas.
        nodeList.render(canvas);

        // Renders this frame to the Canvas.
        super.render(canvas);

    }

    /**
     * Quickly places all nodes and renders the tree.
     * To be used at the end of insertions, deletions, and traversals.
     */
    @Override
    protected void finalRender() {
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
     * Recursively parses through the tree until the Node with the inputed key
     * is found.
     *
     * @param currNode the current Node being viewed.
     * @return the Node matching the desired key.
     */
    private Node getNodeRecursive(int key, Node currNode) {
        Node node;

        // Returns null if this Node is null.
        if (currNode == null) return null;

        // Returns this Node if it is the desired one.
        if (currNode.key == key) return currNode;

        // Parses through the subtrees of this Node.
        for (int i = 0; i < getNumChildren(); ++i) {
            node = getNodeRecursive(key, currNode.children[i]);
            if (node != null) return node;

        }

        // Returns null if the Node is not found in this Node's subtrees.
        return null;

    }

    /**
     * Parses through the tree in order to find the Node with the inputed key.
     *
     * @return the Node containing the given Key.
     */
    public Node getNode(int key) {
        return getNodeRecursive(key, root);

    }

    /**
     * Returns an ArrayList containing all keys in this data structure.
     *
     * @return an ArrayList containing all keys in this data structure.
     */
    public ArrayList<Integer> getAllKeys() {
        ArrayList<Node> currNodes = getAllNodes();
        ArrayList<Integer> keyArrl = new ArrayList<Integer>();

        for(int i = 0; i < currNodes.size(); i++){
            keyArrl.add(currNodes.get(i).key);
        }
        return keyArrl;

    }

    /**
     * Sets the root to null so that the tree is empty.
     */
    @Override
    public void clear() {
        root = null;

    }

    /**
     * Returns an ArrayList containing all keys in this data structure.
     *
     * @param fileName what the user wants the file to be named
     * @param type what type of data structure is being saved
     * @return a JSONObject to be stored as a JSON file
     */
    public JSONObject createJSON(String fileName, String type){
        ArrayList<Integer> keyArrl = getAllKeys();
        //try to create a JSONObject
        try {
            JSONObject jObj = new JSONObject();
            //insert the file name and type
            jObj.put("FileName", fileName);
            jObj.put("Type", type);

            //check to make sure not saving empty file
            if(keyArrl == null || keyArrl.isEmpty()){
                return null;
            }

            //insert JSONArray of the keys
            JSONArray jsArray = new JSONArray(keyArrl);
            jObj.put("Values", jsArray);

            //return the JSONObject
            return jObj;

            //catch the error if JSONObject not made
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
            return null;
        }
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
}