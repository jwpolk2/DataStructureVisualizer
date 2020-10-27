package com.example.datastructurevisualizer;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Superclass for all visualizers that use Nodes.
 * Stores the nodeWidth and has method for drawing a Node.
 */
public class NodeVisualizer {

    // Log of animations that have happened during the most recent animation.
    ArrayList<AnimationItem> animationLog = new ArrayList<AnimationItem>();

    // Width of a Node.
    // TODO modify
    static final float NODE_WIDTH = 20f;

    // Current highlighted Node.
    Node highlightedNode;

    /**
     * Draws a Node. Nodes are circles of width nodeWidth with their numerical
     * values printed over them.
     *
     * @param node the Node to draw.
     */
    protected void drawNode(Node node) {
        Paint colour = new Paint();

        // Draws the Node.
        colour.setARGB(255, node.r, node.g, node.b);
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
        MainActivity.getVisualizer().getCanvas().drawCircle(
                node.position[0], node.position[1],
                NODE_WIDTH * AnimationParameters.scaleFactor, colour);
        MainActivity.getVisualizer().getCanvas().drawText(String.valueOf(node.key), node.position[0], node.position[1]+15, textPaint);
    }

    /**
     * Highlights a Node by incrementing all of its colour values by 20.
     *
     * TODO may be reworked by merely editing drawNode
     *
     * @param node the Node to highlight.
     */
    protected void highlight(Node node) {
        highlightedNode = node;
        highlightedNode.r -= 200;
        highlightedNode.b += 200;

    }

    /**
     * UnHighlights a Node by decrementing all of its colour values by 20.
     *
     * TODO may be reworked by merely editing drawNode.
     */
    protected void unHighlight() {
        highlightedNode.r += 200;
        highlightedNode.b -= 200;
        highlightedNode = null;

    }

    /**
     * Sets the highlighted Node.
     *
     * @param node the Node to highlight.
     */
    protected void setHighlightedNode(Node node) {

        // Un-highlights the previous Node.
        if (highlightedNode != null) unHighlight();

        // Highlights the new Node.
        highlight(node);

    }

    /**
     * Sets the highlighted Node and waits for a small time.
     *
     * @param node the Node to animate.
     */
    protected void nodeSelectAnimation(Node node) {

        // Highlights the Node and re-renders the data-structure.
        setHighlightedNode(node);
        render();

        // Sleeps for a little while.
        try {
            Thread.sleep((long) (AnimationParameters.ANIM_TIME / AnimationParameters.animSpeed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the highlighted Node and waits for a small time.
     *
     * @param node the Node to animate.
     */
    protected void queueNodeSelectAnimation(Node node) {
        animationLog.add(new HighlightNode(node));

    }

    /**
     * Animates movement of Nodes to their destination positions.
     */
    protected void nodeMoveAnimation() {
        float movementFraction;
        ArrayList<Node> nodes = getAllNodes();

        // Performs movement over several iterations.
        for (int i = 0; i < AnimationParameters.MOVEMENT_FRAMES; ++i) {

            // Determines the fraction distance to move while interpolating.
            movementFraction = (AnimationParameters.MOVEMENT_FRAMES - i);

            // Moves every Node towards its destination by the movementFraction.
            for (Node node : nodes) {
                node.position[0] += (node.destination[0] - node.position[0]) / movementFraction;
                node.position[1] += (node.destination[1] - node.position[1]) / movementFraction;

            }

            // Renders the item.
            render();

            // Sleeps a while.
            try {
                Thread.sleep((long) (AnimationParameters.ANIM_TIME /
                        (AnimationParameters.animSpeed * AnimationParameters.MOVEMENT_FRAMES)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Animates movement of Nodes to their destination positions.
     */
    protected void queueNodeMoveAnimation() {
        animationLog.add(new MoveNodes(getAllNodes()));

    }

    /**
     * Unhighlights the current highlighted Node.
     */
    public void finishTraversalAnimation() {
        if (highlightedNode != null) unHighlight();

    }

    /**
     * Sets the all Nodes' positions to their destinations.
     */
    public void placeNodesAtDestination() {
        ArrayList<Node> nodes = getAllNodes();

        // Places all Nodes at their destinations.
        for (Node node : nodes) {
            node.position[0] = node.destination[0];
            node.position[1] = node.destination[1];

        }
    }

    /**
     * Renders the data structure. Should be overridden.
     */
    public void render() {
        MainActivity.getVisualizer().render();
    }

    /**
     * Renders the data structure and does a handful of other things. Should be overridden.
     */
    protected void finalRender() {}

    /**
     * Returns an ArrayList containing all Nodes in this data structure.
     * Should be overriden.
     *
     * @return an ArrayList containing all Nodes in this data structure.
     */
    public ArrayList<Node> getAllNodes() { return null; }

    /**
     * Animation item for highlighting a Node.
     */
    private class HighlightNode implements AnimationItem {
        Node highlighted;

        /**
         * Constructor for this item. Stores the Node to highlight.
         */
        HighlightNode(Node node) { highlighted = node; }

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
     * Animation item for moving Nodes.
     */
    private class MoveNodes implements AnimationItem {
        Map<Node, Integer[][]> keyPos;

        // Defs to help with movement.
        static final int pos = 0;
        static final int dest = 1;
        static final int x = 0;
        static final int y = 1;
        static final int arrSize = 2;

        /**
         * Constructor for this item. Maps Nodes to their current and destination
         * positions.
         */
        MoveNodes(ArrayList<Node> nodes) {
            keyPos = new TreeMap<Node, Integer[][]>();

            // Places each and its position/destination into the tree.
           for (Node node : nodes) {
               Integer[][] coords = new Integer[arrSize][arrSize];
               coords[pos][x] = node.position[x];
               coords[pos][y] = node.position[y];
               coords[dest][x] = node.destination[x];
               coords[dest][y] = node.destination[y];
               keyPos.put(node, coords);

           }
        }

        /**
         * Moves the nodes.
         */
        @Override
        public void run() {

            // Sets each Node's position and destination.
            for (Map.Entry<Node, Integer[][]> currNode : keyPos.entrySet()) {
                currNode.getKey().position[x] = (int)currNode.getValue()[pos][x];
                currNode.getKey().position[y] = (int)currNode.getValue()[pos][y];
                currNode.getKey().destination[x] = (int)currNode.getValue()[dest][x];
                currNode.getKey().destination[y] = (int)currNode.getValue()[dest][y];

            }

            // Performs the Node movement animation.
            nodeMoveAnimation();

        }

        /**
         * Reverses the movement.
         */
        @Override
        public void reverse() {

            // Sets each Node's position and destination.
            for (Map.Entry<Node, Integer[][]> currNode : keyPos.entrySet()) {
                currNode.getKey().destination[x] = (int)currNode.getValue()[pos][x];
                currNode.getKey().destination[y] = (int)currNode.getValue()[pos][y];
                currNode.getKey().position[x] = (int)currNode.getValue()[dest][x];
                currNode.getKey().position[y] = (int)currNode.getValue()[dest][y];

            }

            // Performs the Node movement animation.
            nodeMoveAnimation();

        }
    }

    /**
     * Performs all animations in the animation queue, then empties the queue.
     */
    public void animate() {
        for (AnimationItem item : animationLog) item.run();
        animationLog.clear();
        finalRender();

    }
}