package com.example.datastructurevisualizer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;

/**
 * Superclass for all visualizers that use Nodes.
 * Stores the nodeWidth and has method for drawing a Node.
 */
public class NodeVisualizer extends DataStructureVisualizer {

    // Current highlighted Node.
    Node highlightedNode;

    /**
     * Draws a Node. Nodes are circles of width nodeWidth with their numerical
     * values printed over them.
     *
     * @param node the Node to draw.
     */
    protected void drawNode(Node node, Canvas canvas) {
        Paint colour = new Paint();

        // Draws the Node.
        colour.setARGB(255, node.r, node.g, node.b);
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
        canvas.drawCircle(
                node.position[0], node.position[1],
                AnimationParameters.NODE_RADIUS * AnimationParameters.scaleFactor, colour);
        canvas.drawText(String.valueOf(node.key), node.position[0], node.position[1]+15, textPaint);

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
    protected void nodeSelectAnimation(Node node, Canvas canvas) {

        // Highlights the Node and re-renders the data-structure.
        setHighlightedNode(node);
        render(canvas);

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
    private void nodeMoveAnimation(Canvas canvas, int currFrame) {
        float movementFraction;
        ArrayList<Node> nodes = getAllNodes();

        // Determines the fraction distance to move while interpolating.
        movementFraction = (AnimationParameters.MOVEMENT_FRAMES - currFrame);

        // Moves every Node towards its destination by the movementFraction.
        for (Node node : nodes) {
            node.position[0] += (node.destination[0] - node.position[0]) / movementFraction;
            node.position[1] += (node.destination[1] - node.position[1]) / movementFraction;

        }

        // Renders the frame.
        render(canvas);

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

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                MainActivity.getVisualizer().getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the Node is highlighted.
         */
        HighlightNode(Node node) {
            canvas = new Canvas(bmp);
            nodeSelectAnimation(node, canvas);

        }

        /**
         * Highlights this Node.
         */
        @Override
        public void run() {

            // Draws the frame.
            MainActivity.getVisualizer().getCanvas().drawBitmap(
                    bmp, MainActivity.getVisualizer().getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            try {
                Thread.sleep((long) (AnimationParameters.ANIM_TIME / AnimationParameters.animSpeed));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        // Canvas and bitmap to store the frame.
        Canvas canvas[] = new Canvas[AnimationParameters.MOVEMENT_FRAMES];
        Bitmap bmp[] = new Bitmap[AnimationParameters.MOVEMENT_FRAMES];

        /**
         * Constructor for this item. Maps Nodes to their current and destination
         * positions.
         */
        MoveNodes(ArrayList<Node> nodes) {

            // Renders each frame for the animation.
            for (int i = 0; i < AnimationParameters.MOVEMENT_FRAMES; ++i) {

                // Initializes bmp and Canvas.
                bmp[i] = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                        MainActivity.getVisualizer().getCanvas().getHeight(),
                        Bitmap.Config.ARGB_8888);
                canvas[i] = new Canvas(bmp[i]);

                // Performs the Node movement animation.
                nodeMoveAnimation(canvas[i], i);

            }
        }

        /**
         * Moves the nodes.
         */
        @Override
        public void run() {

            // Renders each frame.
            for (int i = 0; i < AnimationParameters.MOVEMENT_FRAMES; ++i) {

                // Draws the frame.
                MainActivity.getVisualizer().getCanvas().drawBitmap(
                        bmp[i], MainActivity.getVisualizer().getCanvas().getClipBounds(),
                        canvas[i].getClipBounds(), new Paint());
                MainActivity.getVisualizer().render();

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
         * Reverses the movement.
         */
        @Override
        public void reverse() {

            // Renders each frame backwards.
            for (int i = AnimationParameters.MOVEMENT_FRAMES - 1; i >= 0; --i) {

                // Draws the frame.
                MainActivity.getVisualizer().getCanvas().drawBitmap(
                        bmp[i], MainActivity.getVisualizer().getCanvas().getClipBounds(),
                        canvas[i].getClipBounds(), new Paint());

                // Sleeps a while.
                try {
                    Thread.sleep((long) (AnimationParameters.ANIM_TIME /
                            (AnimationParameters.animSpeed * AnimationParameters.MOVEMENT_FRAMES)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}