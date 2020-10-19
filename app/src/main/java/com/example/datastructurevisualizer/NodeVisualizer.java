package com.example.datastructurevisualizer;
import android.graphics.HardwareRenderer;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;

/**
 * Superclass for all visualizers that use Nodes.
 * Stores the nodeWidth and has method for drawing a Node.
 */
public class NodeVisualizer {

    // Width of a Node.
    // TODO modify
    static final float NODE_WIDTH =20f;

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
        colour.setARGB(255, node.r, node.g, node.b);
        MainActivity.getCanvas().drawCircle(
                node.position[0], node.position[1],
                NODE_WIDTH * AnimationParameters.scaleFactor, colour);

    }

    /**
     * Highlights a Node by incrementing all of its colour values by 20.
     *
     * TODO may be reworked by merely editing drawNode
     *
     * @param node the Node to highlight.
     */
    private void highlight(Node node) {
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
    private void unHighlight(Node node) {
        node.r -= 20;
        node.g -= 20;
        node.b -= 200;

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
        Log.e("CURRNODE", "[" + highlightedNode.position[0] + ", " + highlightedNode.position[1] + "]");
        Log.e("COLOUR", "[" + highlightedNode.r + ", " + highlightedNode.g + ", " + highlightedNode.b + "]");
        render(); // TODO this render does nothing since child threads cannot change the screen.

        // Sleeps for a little while.
        try {
            Thread.sleep((long) (AnimationParameters.ANIM_TIME * AnimationParameters.animSpeed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders the data structure. Should be overriden.
     */
    public void render() {}

}