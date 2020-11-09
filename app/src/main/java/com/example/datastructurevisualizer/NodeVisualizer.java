package com.example.datastructurevisualizer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Superclass for all visualizers that use Nodes.
 *
 * Contains the selectedNode a list of highlightedNodes, and a list of explored
 * Nodes, as well as some methods to interact with them.
 * Uses drawNode to place Nodes into an inputed Canvas. The selectedNode is
 * blue and highlightedNodes are green.
 * Includes AnimationItems to select nodes, add them to nodeList as a
 * stack/queue, pop nodes from nodeList, and moveNodes. Also contains animation
 * methods for these items.
 * Contains getAllNodes and getNode, to be overridden by individual
 * implementations of NodeVisualizers.
 */
public class NodeVisualizer extends DataStructureVisualizer {

    // Current selected Node. Head of a traversal.
    // Colour is blue.
    private Node selectedNode;

    // Current highlighted Nodes. Used in traversal/pathfinding.
    // Colour is light green.
    private ArrayList<Node> highlightedNodes = new ArrayList<Node>();

    // Current explored Nodes. Used in pathfinding.
    // Colour is dark green.
    private ArrayList<Node> exploredNodes = new ArrayList<Node>();

    /**
     * Draws a Node. Nodes are circles of width nodeWidth with their numerical
     * values printed over them. Nodes will be recoloured if they are the
     * selectedNode, if the are among the highlightedNodes, or if they are among
     * the exploredNodes.
     *
     * selectedNodes are blue, highlightedNodes are light green, exploredNodes
     * are light green. See AnimationParameters to make changes.
     *
     * @param node the Node to draw.
     * @param canvas to Canvas to render in.
     */
    protected void drawNode(Node node, Canvas canvas) {
        Paint colour = new Paint();

        // The Node will be coloured depending upon its highlight status.
        if (node == selectedNode) colour.setARGB(255, AnimationParameters.SEL_NODE_R,
                AnimationParameters.SEL_NODE_G, AnimationParameters.SEL_NODE_B);
        else if (highlightedNodes.contains(node)) colour.setARGB(255, AnimationParameters.HIL_NODE_R,
                AnimationParameters.HIL_NODE_G, AnimationParameters.HIL_NODE_B);
        else if (exploredNodes.contains(node)) colour.setARGB(255, AnimationParameters.EXP_NODE_R,
                AnimationParameters.EXP_NODE_G, AnimationParameters.EXP_NODE_B);
        else colour.setARGB(255, node.r, node.g, node.b);

        // Displays the key on the Node.
        Paint textPaint = new Paint();
        textPaint.setARGB(255, AnimationParameters.TEXT_R, AnimationParameters.TEXT_G,
                AnimationParameters.TEXT_B);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(2 * (int)AnimationParameters.NODE_RADIUS);

        // Draws the Node and text.
        canvas.drawCircle(
                node.position[0], node.position[1],
                AnimationParameters.NODE_RADIUS * AnimationParameters.scaleFactor, colour);
        canvas.drawText(String.valueOf(node.key), node.position[0], node.position[1]+15, textPaint);

    }

    /**
     * Selects a Node.
     *
     * @param node the Node to select.
     */
    private void select(Node node) {
        selectedNode = node;

    }

    /**
     * Un-Selects a Node.
     */
    private void unSelect() {
        selectedNode = null;

    }

    /**
     * Sets the selected Node. Selection could be made more complicated so this
     * is kept even though it is superfluous.
     *
     * @param node the Node to select.
     */
    protected void setSelectedNode(Node node) {

        // UnSelects the previous Node.
        if (selectedNode != null) unSelect();

        // Selects the new Node.
        select(node);

    }

    /**
     * Highlights a Node.
     *
     * @param node the Node to highlight.
     */
    private void highlightNode(Node node) {
        highlightedNodes.add(node);

    }

    /**
     * UnHighlights a Node.
     *
     * @param node the Node to unhighlight.
     */
    private void unHighlightNode(Node node) {
        highlightedNodes.remove(node);

    }

    /**
     * UnHighlights all Nodes.
     */
    private void unHighlightAllNodes() {
        highlightedNodes.clear();

    }

    /**
     * Adds a Node to exploredNodes.
     *
     * @param node the explored Node.
     */
    private void exploreNode(Node node) {
        exploredNodes.add(node);

    }

    /**
     * Removes a Node from exploredNodes.
     *
     * @param node the Node to remove from explored.
     */
    private void unExploreNode(Node node) {
        exploredNodes.remove(node);

    }

    /**
     * Removes all Nodes from exploredNodes.
     */
    private void unExploreAllNodes() {
        exploredNodes.clear();

    }

    /**
     * Sets the selectedNode and renders a frame.
     *
     * @param node the Node to select.
     * @param canvas the Canvas to render in.
     */
    void nodeSelectAnimation(Node node, Canvas canvas) {

        // Highlights the Node and re-renders the data-structure.
        setSelectedNode(node);
        render(canvas);

    }

    /**
     * Queues an animation to change the selected Node and wait for a small time.
     *
     * @param node the Node to select.
     */
    protected void queueNodeSelectAnimation(Node node, String message) {
        animationLog.add(new SelectNode(node, message));

    }

    /**
     * Animates the addition of a node to the stack. The node will be highlighted.
     *
     * @param node the Node to add to the stack.
     * @param canvas the Canvas to render in.
     */
    private void stackAddAnimation(Node node, Canvas canvas) {

        // Highlights node and adds it to the stack.
        highlightNode(node);
        nodeList.stackInsert(node.key);
        render(canvas);

    }

    /**
     * Queues an animation to add the selected Node to the stack. The Node will be
     * highlighted.
     *
     * @param node the Node to add to the stack.
     * @param message the message to animate with.
     */
    protected void queueStackAddAnimation(Node node, String message) {
        animationLog.add(new StackAddNode(node, message));

    }

    /**
     * Animates the addition of a node to the queue. The node will be highlighted.
     *
     * @param node the Node to add to the stack.
     * @param canvas the Canvas to render in.
     */
    private void queueAddAnimation(Node node, Canvas canvas) {

        // Highlights node and adds it to the queue.
        highlightNode(node);
        nodeList.queueInsert(node.key);
        render(canvas);

    }

    /**
     * Queues an animation to add the selected Node to the stack. The Node will be
     * highlighted.
     *
     * @param node the Node to add to the stack.
     * @param message the message to animate with.
     */
    protected void queueQueueAddAnimation(Node node, String message) {
        animationLog.add(new QueueAddNode(node, message));

    }

    /**
     * Animates removing a Node from the list. Will also UnHighlight said Node
     * in the tree.
     *
     * @param canvas the Canvas to render in.
     */
    private void listPopAnimaton(Canvas canvas) {

        // UnHighlights the first node in the list and pops it.
        unHighlightNode(getNode(nodeList.pop()));
        render(canvas);

    }

    /**
     * Queues an animation to remove the first Node from nodeList.
     */
    protected void queueListPopAnimation(String message) {
        animationLog.add(new ListPopNode(message));

    }

    /**
     * Animates movement of Nodes to their destination positions.
     *
     * @param currFrame the current frame of this animation.
     * @param canvas the Canvas to render in.
     */
    private void nodeMoveAnimation(int currFrame, Canvas canvas) {
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
    protected void queueNodeMoveAnimation(String message) {
        animationLog.add(new MoveNodes(message));

    }

    /**
     * UnSelects the current selected Node, UnHighlights all Nodes, and clears
     * the nodeList.
     */
    public void finishTraversalAnimation() {
        if (selectedNode != null) unSelect();
        unHighlightAllNodes();
        nodeList.clear();

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
     * Should be overridden.
     *
     * @return an ArrayList containing all Nodes in this data structure.
     */
    public ArrayList<Node> getAllNodes() { return null; }

    /**
     * Returns the Node containing the inputed key if it exists.
     * Should be overridden.
     *
     * @return the Node containing the given key or null.
     */
    public Node getNode(int key) { return null; }

    /**
     * Animation item for selecting a Node.
     */
    private class SelectNode extends AnimationItem {

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                MainActivity.getVisualizer().getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the inputed Node is selected.
         *
         * @param node the Node to select.
         * @param message the message to animate with.
         */
        SelectNode(Node node, String message) {
            super(message);
            canvas = new Canvas(bmp);
            nodeSelectAnimation(node, canvas);

        }

        /**
         * Displays the frame wherein the the inputed Node is selected.
         */
        @Override
        public void run() {
            super.run();

            // Draws the frame.
            MainActivity.getVisualizer().getCanvas().drawBitmap(
                    bmp, MainActivity.getVisualizer().getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep((int) (AnimationParameters.ANIM_TIME / AnimationParameters.animSpeed));

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Animation item for adding a Node to nodeList as a stack.
     */
    private class StackAddNode extends AnimationItem {

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                MainActivity.getVisualizer().getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the inputed Node is added
         * to the stack.
         *
         * @param node the Node to add to the stack.
         * @param message the message to animate with.
         */
        StackAddNode(Node node, String message) {
            super(message);
            canvas = new Canvas(bmp);
            stackAddAnimation(node, canvas);

        }

        /**
         * Displays the frame wherein the the inputed Node is added to the stack.
         */
        @Override
        public void run() {
            super.run();

            // Draws the frame.
            MainActivity.getVisualizer().getCanvas().drawBitmap(
                    bmp, MainActivity.getVisualizer().getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep((int) (AnimationParameters.ANIM_TIME / AnimationParameters.animSpeed));

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Animation item for adding a Node to nodeList as a queue.
     */
    private class QueueAddNode extends AnimationItem {

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                MainActivity.getVisualizer().getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the inputed Node is added
         * to a queue.
         *
         * @param node the Node to add to the queue.
         * @param message the message to animate with.
         */
        QueueAddNode(Node node, String message) {
            super(message);
            canvas = new Canvas(bmp);
            queueAddAnimation(node, canvas);

        }

        /**
         * Displays the frame wherein the the inputed Node is added to the queue.
         */
        @Override
        public void run() {
            super.run();

            // Draws the frame.
            MainActivity.getVisualizer().getCanvas().drawBitmap(
                    bmp, MainActivity.getVisualizer().getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep((int) (AnimationParameters.ANIM_TIME / AnimationParameters.animSpeed));

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Animation item for adding popping a Node from nodeList. Popping is the
     * same across stacks and queues.
     */
    private class ListPopNode extends AnimationItem {

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                MainActivity.getVisualizer().getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the Node is popped.
         *
         * @param message the message to animate with.
         */
        ListPopNode(String message) {
            super(message);
            canvas = new Canvas(bmp);
            listPopAnimaton(canvas);

        }

        /**
         * Displays the frame wherein the the Node is popped.
         */
        @Override
        public void run() {
            super.run();

            // Draws the frame.
            MainActivity.getVisualizer().getCanvas().drawBitmap(
                    bmp, MainActivity.getVisualizer().getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep((int) (AnimationParameters.ANIM_TIME / AnimationParameters.animSpeed));

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
    private class MoveNodes extends AnimationItem {

        // Canvas and bitmap to store the frame.
        Canvas[] canvas = new Canvas[AnimationParameters.MOVEMENT_FRAMES];
        Bitmap[] bmp = new Bitmap[AnimationParameters.MOVEMENT_FRAMES];

        /**
         * Constructor for this item. Maps Nodes to their current and destination
         * positions.
         *
         * @param message the message to animate with.
         */
        MoveNodes(String message) {
            super(message);

            // Renders each frame for the animation.
            for (int i = 0; i < AnimationParameters.MOVEMENT_FRAMES; ++i) {

                // Initializes bmp and Canvas.
                bmp[i] = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                        MainActivity.getVisualizer().getCanvas().getHeight(),
                        Bitmap.Config.ARGB_8888);
                canvas[i] = new Canvas(bmp[i]);

                // Performs the Node movement animation.
                nodeMoveAnimation(i, canvas[i]);

            }
        }

        /**
         * Moves the nodes.
         */
        @Override
        public void run() {
            super.run();

            // Renders each frame.
            for (int i = 0; i < AnimationParameters.MOVEMENT_FRAMES; ++i) {

                // Draws the frame.
                MainActivity.getVisualizer().getCanvas().drawBitmap(
                        bmp[i], MainActivity.getVisualizer().getCanvas().getClipBounds(),
                        canvas[i].getClipBounds(), new Paint());
                MainActivity.getVisualizer().render();

                // Sleeps a while.
                sleep((int) (AnimationParameters.ANIM_TIME /
                        (AnimationParameters.animSpeed * AnimationParameters.MOVEMENT_FRAMES)));

            }
        }

        /**
         * Reverses the movement.
         */
        @Override
        public void reverse() {
            super.reverse();

            // Renders each frame backwards.
            for (int i = AnimationParameters.MOVEMENT_FRAMES - 1; i >= 0; --i) {

                // Draws the frame.
                MainActivity.getVisualizer().getCanvas().drawBitmap(
                        bmp[i], MainActivity.getVisualizer().getCanvas().getClipBounds(),
                        canvas[i].getClipBounds(), new Paint());

                // Sleeps a while.
                sleep((int) (AnimationParameters.ANIM_TIME /
                        (AnimationParameters.animSpeed * AnimationParameters.MOVEMENT_FRAMES)));

            }
        }
    }
}