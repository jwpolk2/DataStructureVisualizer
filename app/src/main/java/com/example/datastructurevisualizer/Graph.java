package com.example.datastructurevisualizer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * TODO comment
 *
 * Node.extraData[0] stores an ArrayList of Edges to other Nodes.
 *
 * Graphs do not have insertion or removal animations, so insertAnim and
 * removeAnim call insertNoAnim and removeNoAnim respectively.
 * Since there is no algorithm for determining where a Graph Node should be
 * placed, insertGraphNode accepts integer coordinates. This method should be
 * used instead of insert/insertAnim/insertNoAnim.
 * TODO edge highlighting
 * Includes breadth first and dijkstra pathfinds, as well as Prim's and Kruskal's
 * minimum spanning tree algorithms.
 * Overriddes getNode and getAllNodes to work for a graph.
 * Overrides render to work for a graph.
 */
public class Graph extends NodeVisualizer {

    // ArrayList containing all nodes in the graph.
    private ArrayList<Node> nodes = new ArrayList<>();

    // ArrayList of highlighted Edges in the graph. Used for Prims and Kruskal's.
    // algorithms.
    // Colour is TODO
    ArrayList<Edge> highlightedEdges = new ArrayList<Edge>();

    /**
     * Inserts a Node into the graph at the specified position.
     *
     * @param key the key to be inserted.
     * @param xPos the xPos of the Node to be inserted.
     * @param yPos the yPos of the Node to be inserted.
     */
    public void insertGraphNode(int key, int xPos, int yPos) {
        Node node = new Node(key, 0);
        node.position[0] = xPos;
        node.position[1] = yPos;
        node.extraData = new Object[1];
        node.extraData[0] = new ArrayList<Edge>();
        nodes.add(node);

    }

    /**
     * Inserts a directed edge into the Graph.
     *
     * @param startKey key of the starting Node.
     * @param destKey key of the destination Node.
     * @param weight weight of the Edge.
     */
    public void insertDirectedEdge(int startKey, int destKey, int weight) {
        Node start = getNode(startKey);
        Node dest = getNode(destKey);

        // Exits if either Node was not found.
        if (start == null || dest == null) return;

        // Creates the directed Edge.
        ((ArrayList<Edge>)start.extraData[0]).add(new Edge(start, dest, weight));

    }

    /**
     * Inserts a Node into the graph at (0, 0). insertGraphNode should be used
     * instead of this method.
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {
        Node node = new Node(key, 0);
        node.extraData = new Object[1];
        node.extraData[0] = new ArrayList<Edge>();
        nodes.add(node);

    }

    /**
     * There are no graph insert animations. insertGraphNode should be used
     * instead of this method.
     *
     * @param key the key to be inserted.
     */
    @Override
    protected void insertAnim(int key) {
        insertNoAnim(key);

    }

    /**
     * Removes a Node from the Graph.
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeNoAnim(int key) {

        // Removes all edges to this Node.
        for (Node currNode : nodes) {
            ArrayList<Edge> edges = (ArrayList<Edge>)currNode.extraData[0];
            for (int i = 0; i < edges.size(); ++i) {
                if (edges.get(i).dest.key == key) {
                    edges.remove(i);

                }
            }
        }

        // Removes this Node.
        nodes.remove(getNode(key));

    }

    /**
     * There are no graph remove animations.
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeAnim(int key) {
        removeNoAnim(key);

    }

    /**
     * Animates a breadth-first pathfind. Breadth first traversals run as follows:
     *
     * Add the inputed start to a queue of Nodes to explore
     * Pop a Node from the queue:
     *  if the Node has dest as its key, return
     *  if the Node is not dest, add all of its child Nodes to the queue
     * continue until the queue is empty
     *
     * TODO this has not been debugged
     *
     * @param startKey the Node to begin the traversal at.
     * @param endKey the Node to end the traversal at.
     */
    public void breadthFirstPathfind(int startKey, int endKey) {
        java.util.LinkedList<Node> queue = new java.util.LinkedList<Node>();
        java.util.LinkedList<Node> explored = new java.util.LinkedList<Node>();
        Node start = getNode(startKey);
        Node dest = getNode(endKey);

        // Returns if the either the start or end are invalid.
        if (start == null || dest == null) return;

        // Adds the start to the queue of Nodes to explore.
        queue.add(start);
        explored.add(start);
        queueQueueAddAnimation(start, "Begin exploring at start");

        // Parses through the queue of Nodes until the destination Node is found
        // or the queue is empty.
        Node currNode;
        while (!queue.isEmpty()) {

            // Explores the first Node in the queue.
            currNode = queue.pop();
            queueListPopAnimation("Pop the first Node from the queue");
            queueNodeSelectAnimation(currNode, "Explore " + currNode.key);

            // Returns if the destination Node is found.
            if (currNode == dest) {
                queueNodeSelectAnimation(currNode,
                        currNode.key + " is the destination Node");
                return;

            }
            // Continues traversal if the destination Node is not found.
            else queueNodeSelectAnimation(currNode, "Explore " + currNode.key);

            // Adds each unexplored Node to the queue.
            for (Edge edge : (ArrayList<Edge>)currNode.extraData[0]) {
                if (!explored.contains(edge.dest)) {
                    queue.add(edge.dest);
                    explored.add(edge.dest);
                    queueQueueAddAnimation(currNode, "Add " + edge.dest.key + " to queue");

                }
            }

            // Marks the Node as explored.
            queueNodeExploreAnimation(currNode, currNode.key + " has been explored");

        }

        // If the destination Node is not found, prints a message saying so.
        queueNodeSelectAnimation(null, "Destination not found");

    }

    /**
     * Animates a Dijkstra pathfind.
     *
     * TODO comment
     * TODO debug
     * TODO make Node comparable again
     *
     * @param startKey the Node to begin the traversal at.
     * @param endKey the Node to end the traversal at.
     */
    public void dijkstraPathfind(int startKey, int endKey) {
        java.util.PriorityQueue<Node> queue = new java.util.PriorityQueue<Node>();
        java.util.LinkedList<Node> explored = new java.util.LinkedList<Node>();
        Node start = getNode(startKey);
        Node dest = getNode(endKey);

        // Returns if the either the start or end are invalid.
        if (start == null || dest == null) return;

        // Adds the start to the queue of Nodes to explore.
        queue.add(start);
        explored.add(start);
        start.value = 0;
        queuePriorityQueueAddAnimation(start, "Begin exploring at start");

        // Parses through the queue of Nodes until the destination Node is found
        // or the queue is empty.
        Node currNode;
        while (!queue.isEmpty()) {

            // Explores the first Node in the queue.
            currNode = queue.poll();
            queueListPopAnimation("Pop the first Node from the queue");
            queueNodeSelectAnimation(currNode, "Explore " + currNode.key);

            // Returns if the destination Node is found.
            if (currNode == dest) {
                queueNodeSelectAnimation(currNode,
                        currNode.key + " is the destination Node");
                return;

            }
            // Continues traversal if the destination Node is not found.
            else queueNodeSelectAnimation(currNode, "Explore " + currNode.key);

            // Adds each unexplored Node to the queue.
            for (Edge edge : (ArrayList<Edge>)currNode.extraData[0]) {
                if (!explored.contains(edge.dest)) {
                    queue.add(edge.dest);
                    explored.add(edge.dest);
                    edge.dest.value = currNode.value + edge.weight;
                    queuePriorityQueueAddAnimation(currNode, "Add " + edge.dest.key + " to queue");

                }
            }

            // Marks the Node as explored.
            queueNodeExploreAnimation(currNode, currNode.key + " has been explored");

        }

        // If the destination Node is not found, prints a message saying so.
        queueNodeSelectAnimation(null, "Destination not found");

    }

    /**
     * Greedy traversal that finds the minimum spanning tree of the graph,
     * starting from the given key.
     *
     * @param startKey the starting key of the tree.
     */
    public void primsAlgorithm(int startKey) {
        Edge currEdge;
        ArrayList<Node> explored = new ArrayList<Node>();

        // Returns if the start is invalid.
        Node start = getNode(startKey);
        if (start == null) return;

        // Starts at the start Node.
        explored.add(start);
        queueNodeExploreAnimation(start, "Start exploring at " + start.key);

        // Adds the least edge to the list of explored edges until there
        // are no more available edges.
        while (true) {

            // Explores the least edge from the list of explored Nodes.
            currEdge = new Edge(null, null, -1);
            for (Node node : explored) {
                for (Edge edge : (ArrayList<Edge>)node.extraData[0]) {
                    if (!explored.contains(edge.dest)) {
                        currEdge = currEdge.weight < edge.weight ? currEdge : edge;

                    }
                }
            }

            // If no least edge is found, exits the loop.
            if (currEdge.weight < 0) break;

            // If a least edge is found, highlights it.
            // TODO highlight weight animation
            // queueEdgeHighlightAnimation(edge)

        }

        // TODO finish

    }

    public void kruskalsAlgorithm() {

    }

    /**
     * Returns the ArrayList containing all Nodes in this data structure.
     * The ArrayList should not be modified.
     *
     * @return an ArrayList containing all Nodes in this data structure.
     */
    public ArrayList<Node> getAllNodes() { return nodes; }

    /**
     * Returns the Node containing the inputed key if it exists. Does so by
     * parsing through nodes until a Node with the key is found.
     *
     * @return the Node containing the given key or null.
     */
    @Override
    public Node getNode(int key) {
        for (Node node : nodes) if (node.key == key) return node;
        return null;

    }

    /**
     * Highlights an Edge.
     *
     * @param edge the Edge to highlight.
     */
    private void highlightEdge(Edge edge) {
        highlightedEdges.add(edge);

    }

    /**
     * UnHighlights an Edge.
     *
     * @param edge the Edge to unhighlight.
     */
    private void unHighlightEdge(Edge edge) {
        highlightedEdges.remove(edge);

    }

    /**
     * UnHighlights all Edges.
     */
    private void unHighlightAllEdges() {
        highlightedEdges.clear();

    }

    /**
     * Animates the highlighting of an Edge.
     *
     * @param edge the Edge to highlight.
     * @param canvas the Canvas to render in.
     */
    private void edgeHighlightAnimation(Edge edge, Canvas canvas) {

        // Highlights the edge.
        highlightEdge(edge);
        render(canvas);

    }

    /**
     * Queues an animation to highlight the inputed Edge and wait for a small time.
     *
     * @param edge the edge to highlight.
     * @param message the message to animate with.
     */
    private void queueEdgeHighlightAnimation(Edge edge, String message) {
        animationLog.add(new HighlightEdge(edge, message));

    }

    /**
     * Calculates the unit 'normal' of the vector between two Nodes.
     * If said unit normal points downwards in the y direction, it will be inverted.
     * Used to place text describing edge weights.
     *
     * @param x the x magnitude of the vector.
     * @param y the y magnitude of the vector.
     * @return an array of two ints representing the normal vector <x,y>.
     */
    private float[] calcUnitNormal(float x, float y) {
        float tot = Math.abs(x) + Math.abs(y);
        float[] vec = new float[2];

        // Inverts the vector if it points downwards.
        if (x <= 0) {
            vec[0] = y;
            vec[1] = x;

        }
        // Keeps the normal vector if it does not point downwards.
        else {
            vec[0] = -y;
            vec[1] = -x;

        }

        // Normalizes the vector.
        vec[0] /= tot;
        vec[1] /= tot;

        // Returns the upward-facing perpendicular vector.
        return vec;

    }

    /**
     * Draws a particular edge of the Graph.
     *
     * highlightedEdges are coloured green.
     *
     * @param edge the edge to draw.
     * @param canvas the Canvas to draw in.
     */
    private void drawEdge(Edge edge, Canvas canvas) {
        float[] vec;
        Paint colour = new Paint();

        // Sets the Edge's colour based its highlight status.
        if (highlightedEdges.contains(edge)) colour.setARGB(255, AnimationParameters.HIL_VEC_R,
                    AnimationParameters.HIL_VEC_G, AnimationParameters.HIL_VEC_B);
        else colour.setARGB(255, AnimationParameters.VEC_R,
                    AnimationParameters.VEC_G, AnimationParameters.VEC_B);

        // Draws the Edge.
        canvas.drawLine(edge.start.position[0], edge.start.position[1],
                edge.dest.position[0], edge.dest.position[1], colour);

        // Finds an upwards facing unit vector perpendicular to the vector
        // between the edge's Nodes.
        vec = calcUnitNormal(edge.dest.position[0] - edge.start.position[0],
                edge.dest.position[1] - edge.start.position[1]);

        // Enlarges the perpendicular vector.
        vec[0] *= AnimationParameters.NODE_RADIUS;
        vec[1] *= AnimationParameters.NODE_RADIUS;

        // Calculates the position of the weight. It will be placed above the
        // center of the edge.
        vec[0] += (float) (edge.start.position[0] +
                (edge.dest.position[0] - edge.start.position[0]) / 2.0);
        vec[1] += (float) (edge.start.position[1] +
                (edge.dest.position[1] - edge.start.position[1]) / 2.0);

        // Draws the weight.
        colour.setTextAlign(Paint.Align.CENTER);
        colour.setTextSize(AnimationParameters.NODE_RADIUS);
        canvas.drawText(String.valueOf(edge.weight), vec[0], vec[1], colour);

        Log.e("vec", "vec = {" + vec[0] + ", " + vec[1] + "}");

    }

    /**
     * Draws each edge of the Graph.
     *
     * @param canvas the Canvas to draw in.
     */
    private void drawEdges(Canvas canvas) {

        // Draws each edge for each Node.
        for (Node node : nodes) {
            for (Edge edge : (ArrayList<Edge>)node.extraData[0]) {
                drawEdge(edge, canvas);

            }
        }
    }

    /**
     * Renders the Graph to the inputed canvas, starting at the root.
     * Will also render the nodeList.
     *
     * @param canvas the Canvas to draw in.
     */
    @Override
    public void render(Canvas canvas) {

        // Makes the entire Canvas White.
        canvas.drawRGB(AnimationParameters.BACK_R,
                AnimationParameters.BACK_G, AnimationParameters.BACK_B);

        // Renders each edge.
        drawEdges(canvas);

        // Renders each Node.
        for (Node node : nodes) drawNode(node, canvas);

        // Draws the nodeList over the Canvas.
        nodeList.render(canvas);

        // Renders this frame to the Canvas.
        super.render(canvas);

    }

    /**
     * Animation item for highlighting an Edge.
     */
    private class HighlightEdge extends AnimationItem {

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getVisualizer().getCanvas().getWidth(),
                MainActivity.getVisualizer().getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the inputed edge is
         * highlighted.
         *
         * @param edge the Edge to highlight.
         * @param message the message to animate with.
         */
        HighlightEdge(Edge edge, String message) {
            super(message);
            canvas = new Canvas(bmp);
            edgeHighlightAnimation(edge, canvas);

        }

        /**
         * Displays the frame wherein the the inputed edge is highlighted.
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
}