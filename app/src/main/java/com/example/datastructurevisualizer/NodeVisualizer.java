package com.example.datastructurevisualizer;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Superclass for all visualizers that use Nodes.
 * Stores the nodeWidth and has method for drawing a Node.
 */
public class NodeVisualizer {

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
        textPaint.setARGB(255, 255, 255,255);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(18 * AnimationParameters.scaleFactor);
        MainActivity.getCanvas().drawCircle(
                node.position[0], node.position[1],
                NODE_WIDTH * AnimationParameters.scaleFactor, colour);
        MainActivity.getCanvas().drawText(String.valueOf(node.key), node.position[0], node.position[1], textPaint);
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
        highlightedNode.r += 20;
        highlightedNode.g += 20;
        highlightedNode.b += 200;

    }

    /**
     * UnHighlights a Node by decrementing all of its colour values by 20.
     *
     * TODO may be reworked by merely editing drawNode
     *
     * @param node the Node to unHighlight.
     */
    protected void unHighlight(Node node) {
        node.r -= 20;
        node.g -= 20;
        node.b -= 200;
        highlightedNode = null;

    }

    /**
     * Sets the highlighted Node.
     *
     * @param node the Node to highlight.
     */
    protected void setHighlightedNode(Node node) {

        // Un-highlights the previous Node.
        if (highlightedNode != null) unHighlight(highlightedNode);

        // Highlights the new Node.
        highlight(node);

    }

    /**
     * Sets the highlighted Node and waits for a small time.
     *
     * @param node the Node to animate.
     */
    public void nodeSelectAnimation(Node node) {

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
     * Unhighlights the current highlighted Node.
     */
    public void finishTraversalAnimation() {
        if (highlightedNode != null) unHighlight(highlightedNode);

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
     * Animates movement of Nodes to their destination positions.
     */
    public void nodeMoveAnimation() {
        float xMov, yMov;
        float movementFraction;
        ArrayList<Node> nodes = getAllNodes();

        // Performs movement over several iterations.
        for (int i = 0; i < AnimationParameters.MOVEMENT_FRAMES; ++i) {

            // Determines the fraction distance to move while interpolating.
            movementFraction = (AnimationParameters.MOVEMENT_FRAMES - i);

            // Moves every Node towards its destination by the movementFraction.
            for (Node node : nodes) {
                node.position[0] += (node.destination[0] - node.position[0]) / movementFraction;
                node.position[1] += (node.destination[0] - node.position[0]) / movementFraction;

            }

            // Sleeps a while.
            // TODO change value
            try {
                Thread.sleep((long) (AnimationParameters.ANIM_TIME /
                        (AnimationParameters.animSpeed * AnimationParameters.MOVEMENT_FRAMES)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Renders the data structure. Should be overriden.
     */
    public void render() {}

    /**
     * Returns an ArrayList containing all Nodes in this data structure.
     * Should be overriden.
     *
     * @return an ArrayList containing all Nodes in this data structure.
     */
    public ArrayList<Node> getAllNodes() { return null; }

}