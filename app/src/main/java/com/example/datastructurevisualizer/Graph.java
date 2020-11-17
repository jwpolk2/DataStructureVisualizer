package com.example.datastructurevisualizer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

/**
 * Class representing a Graph.
 *
 * Node.extraData[0] stores an ArrayList of Edges to other Nodes, children[0]
 * stores the previous Node in the current pathfind, and value is used for
 * algorithms.
 *
 * Graphs have special insertions, so insertAnim and insertNoAnim are not defined.
 * Use insertGraphNode instead.
 * Contains methods and AnimationItems to select and highlight Edges.
 * Includes breadth first and dijkstra pathfinds, as well as Prim's and Kruskal's
 * minimum spanning tree algorithms.
 * Overriddes getNode and getAllNodes to work for a graph.
 * Overrides render to work for a graph.
 */
public class Graph extends NodeVisualizer {

    // ArrayList containing all nodes in the graph.
    private ArrayList<Node> nodes = new ArrayList<>();

    // Current selected Node. Head of a spanning tree algorithm.
    // Colour is blue.
    private Edge selectedEdge;

    // ArrayList of highlighted Edges in the graph. Used for spanning tree algorithms.
    // Colour is green.
    private ArrayList<Edge> highlightedEdges = new ArrayList<Edge>();

    /**
     * Inserts a Node into the graph at the specified position.
     *
     * @param key the key to be inserted.
     * @param xPos the xPos of the Node to be inserted.
     * @param yPos the yPos of the Node to be inserted.
     */
    public void insertGraphNode(int key, int xPos, int yPos) {
        Node node = new Node(key, 1);
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
     * Unused insertion method.
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {
        insertGraphNode(key, 0, 0);

    }

    /**
     * Unused animated insertion method.
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
    public void removeNoAnim(int key) {

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
     * @param startKey the Node to begin the pathfind at.
     * @param endKey the Node to end the pathfind at.
     */
    public void breadthFirstPathfindAnim(int startKey, int endKey) {
        java.util.LinkedList<Node> queue = new java.util.LinkedList<Node>();
        java.util.LinkedList<Node> explored = new java.util.LinkedList<Node>();
        Node start = getNode(startKey);
        Node dest = getNode(endKey);

        // Returns if the either the start or end are invalid.
        if (start == null || dest == null || start == dest) return;

        // Adds the start to the queue of Nodes to explore.
        queue.add(start);
        explored.add(start);
        start.children[0] = null;
        queueQueueAddAnimation(start, "Begin exploring at start",
                AnimationParameters.ANIM_TIME);

        // Parses through the queue of Nodes until the destination Node is found
        // or the queue is empty.
        Node currNode;
        while (!queue.isEmpty()) {

            // Explores the first Node in the queue.
            currNode = queue.pop();
            queueListPopAnimation(null, 0);

            // Continues traversal if the destination Node is not found.
            queueNodeSelectAnimation(currNode, "Explore " + currNode.key,
                    AnimationParameters.ANIM_TIME);

            // Adds each unexplored Node to the queue.
            for (Edge edge : (ArrayList<Edge>)currNode.extraData[0]) {
                if (!explored.contains(edge.dest)) {
                    edge.dest.value = currNode.value + edge.weight;
                    queue.add(edge.dest);
                    explored.add(edge.dest);
                    edge.dest.children[0] = currNode;

                    // Finishes the traversal if the final Node is found.
                    if (edge.dest == dest) {
                        queueNodeExploreAnimation(currNode, null, 0);
                        queueQueueAddAnimation(edge.dest, "Add " + edge.dest.key + " to queue",
                                AnimationParameters.ANIM_TIME);
                        queueNodeSelectAnimation(edge.dest,edge.dest.key + " is the destination Node",
                                AnimationParameters.ANIM_TIME);
                        queueHighlightPathAnimation(edge.dest, "Final path cost : " + edge.dest.value,
                                AnimationParameters.ANIM_TIME);
                        return;

                    }
                    // Continues the traversal.
                    else {
                        queueQueueAddAnimation(edge.dest, "Add " + edge.dest.key + " to queue",
                                AnimationParameters.ANIM_TIME);
                        queueHighlightPathAnimation(edge.dest, "Path cost : " + edge.dest.value,
                                AnimationParameters.ANIM_TIME);

                    }
                }
            }

            // Marks the Node as explored.
            queueNodeExploreAnimation(currNode, null, 0);

        }

        // If the destination Node is not found, prints a message saying so.
        queueNodeSelectAnimation(null, "Destination not found",
                AnimationParameters.ANIM_TIME);

    }

    /**
     * Begins a breadth first pathfind.
     *
     * @param startKey the Node to begin the pathfind at.
     * @param endKey the Node to end the pathfind at.
     */
    public void breadthFirstPathfind(int startKey, int endKey) {
        beginAnimation();
        breadthFirstPathfindAnim(startKey, endKey);
        stopAnimation();

    }

    /**
     * Animates a Dijkstra pathfind. Dijkstra pathfinds rely only on gValue
     * to determine exploration priority.
     *
     * @param startKey the Node to begin the pathfind at.
     * @param endKey the Node to end the pathfind at.
     */
    private void dijkstraPathfindAnim(int startKey, int endKey) {
        java.util.LinkedList<Node> explored = new java.util.LinkedList<Node>();
        Node start = getNode(startKey);
        Node dest = getNode(endKey);

        // Returns if the either the start or end are invalid.
        if (start == null || dest == null || start == dest) return;

        // Adds the start to the queue of Nodes to explore.
        explored.add(start);
        start.value = 0;
        start.children[0] = null;
        queuePriorityQueueAddAnimation(start, "Begin exploring at start",
                AnimationParameters.ANIM_TIME);

        // Parses through the queue of Nodes until the destination Node is found
        // or the queue is empty.
        Node currNode;
        while (!nodeList.isEmpty()) {

            // Explores the first Node in the queue.
            currNode = getNode(nodeList.peek());
            queueListPopAnimation(null, 0);

            // Continues traversal if the destination Node is not found.
            queueNodeSelectAnimation(currNode, "Explore " + currNode.key,
                    AnimationParameters.ANIM_TIME);

            // Adds each unexplored Node to the queue.
            for (Edge edge : (ArrayList<Edge>)currNode.extraData[0]) {
                if (!explored.contains(edge.dest)) {
                    edge.dest.value = currNode.value + edge.weight;
                    explored.add(edge.dest);
                    edge.dest.children[0] = currNode;

                    // Finishes the traversal if the final Node is found.
                    if (edge.dest == dest) {
                        queueNodeExploreAnimation(currNode, null, 0);
                        queueQueueAddAnimation(edge.dest, "Add " + edge.dest.key + " to queue",
                                AnimationParameters.ANIM_TIME);
                        queueNodeSelectAnimation(edge.dest,edge.dest.key + " is the destination Node",
                                AnimationParameters.ANIM_TIME);
                        queueHighlightPathAnimation(edge.dest, "Final path cost : " + edge.dest.value,
                                AnimationParameters.ANIM_TIME);
                        return;

                    }
                    // Continues the traversal.
                    else {
                        queueQueueAddAnimation(edge.dest, "Add " + edge.dest.key + " to queue",
                                AnimationParameters.ANIM_TIME);
                        queueHighlightPathAnimation(edge.dest, "Path cost : " + edge.dest.value,
                                AnimationParameters.ANIM_TIME);

                    }
                }
            }

            // Marks the Node as explored.
            queueNodeExploreAnimation(currNode, null, 0);

        }

        // If the destination Node is not found, prints a message saying so.
        queueNodeSelectAnimation(null, "Destination not found",
                AnimationParameters.ANIM_TIME);

    }

    /**
     * Begins a Dijkstra pathfind.
     *
     * @param startKey the Node to begin the pathfind at.
     * @param endKey the Node to end the pathfind at.
     */
    public void dijkstraPathfind(int startKey, int endKey) {
        beginAnimation();
        dijkstraPathfindAnim(startKey, endKey);
        stopAnimation();

    }

    /**
     * Greedy minimum spanning tree algorithm that constructs a minimum spanning
     * tree by repeatedly adding the Node at the end of the least Edge attached
     * to the current tree. The tree starts containing only startKey.
     *
     * Will not work if given a nonexistent key.
     *
     * @param startKey the starting key of the tree.
     */
    private void primsAlgorithmAnim(int startKey) {
        Edge currEdge;
        ArrayList<Node> explored = new ArrayList<Node>();

        // Returns if the start is invalid.
        Node start = getNode(startKey);
        if (start == null) return;

        // Starts at the start Node.
        explored.add(start);
        queueNodeExploreAnimation(start, "Start exploring at " + start.key,
                AnimationParameters.ANIM_TIME);

        // Adds the least edge to the list of explored edges until there
        // are no more available edges.
        while (true) {

            // Explores the least edge from the list of explored Nodes.
            currEdge = new Edge(null, null, Integer.MAX_VALUE);
            for (Node node : explored) {
                for (Edge edge : (ArrayList<Edge>)node.extraData[0]) {
                    if (!explored.contains(edge.dest)) {
                        currEdge = currEdge.weight < edge.weight ? currEdge : edge;

                    }
                }
            }

            // If no least edge is found, exits the loop.
            if (currEdge.weight == Integer.MAX_VALUE) break;

            // If a least edge is found, highlights it.
            queueNodeExploreAnimation(currEdge.dest, null, 0);
            queueEdgeHighlightAnimation(currEdge, "Adding the edge between " +
                    currEdge.start.key + " and " + currEdge.dest.key,
                    AnimationParameters.ANIM_TIME);
            explored.add(currEdge.dest);

        }
    }

    /**
     * Begins Prim's algorithm.
     *
     * @param startKey the Node to begin the algorithm at.
     */
    public void primsAlgorithm(int startKey) {
        beginAnimation();
        primsAlgorithmAnim(startKey);
        stopAnimation();

    }

    /**
     * Minimum spanning tree algorithm that repeatedly adds the smallest available
     * Edge to the tree.
     */
    private void kruskalsAlgorithmAnim() {
        ArrayList<Edge> chosen = new ArrayList<Edge>();
        Edge edge;
        int currTree = 0;
        int tree, replace;

        // Creates a sorted list of all edges for convenience.
        java.util.LinkedList<Edge> edges = new java.util.LinkedList<Edge>();
        for (Node node : nodes) edges.addAll((ArrayList<Edge>)node.extraData[0]);
        java.util.Collections.sort(edges);

        // Sets the value of all Nodes to -1, connoting that none are part of a tree.
        for (Node node : nodes) node.value = -1;

        // Iterates over all edges.
        while (!edges.isEmpty()) {

            // Attempts to add the smallest Edge to the tree.
            edge = edges.pop();

            // Will not add the edge if it connects two Nodes of the same tree.
            if (edge.start.value >= 0 && edge.dest.value >= 0 &&
                    edge.start.value == edge.dest.value) {
                queueEdgeSelectAnimation(edge, "Edge connects two Nodes in the same tree",
                        AnimationParameters.ANIM_TIME);
                continue;

            }

            // Adds the two Nodes to the same tree.
            tree = edge.start.value < 0 ? ++currTree : edge.start.value;
            replace = edge.dest.value;

            // Reassigns the tree of each Node.
            if (replace >= 0) {
                for (Node node : nodes)
                    if (node.value == replace)
                        node.value = tree;

            }
            // Reassigns the tree of the two Nodes.
            else {
                edge.start.value = tree;
                edge.dest.value = tree;

            }

            // Animates the connection.
            highlightEdge(edge);
            queueNodeExploreAnimation(edge.start, null, 0);
            queueNodeExploreAnimation(edge.dest, null, 0);
            queueEdgeSelectAnimation(edge, "Connecting Nodes " +
                    edge.start.key + " and " + edge.dest.key,
                    2 * AnimationParameters.ANIM_TIME);

        }
    }

    /**
     * Begins Kruskal's algorithm.
     */
    public void kruskalsAlgorithm() {
        beginAnimation();
        kruskalsAlgorithmAnim();
        stopAnimation();

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
     * Selects an Edge.
     *
     * @param edge the Edge to select.
     */
    private void select(Edge edge) {
        selectedEdge = edge;

    }

    /**
     * Un-Selects an Edge.
     */
    private void unSelect() {
        selectedEdge = null;

    }

    /**
     * Sets the selected Edge. Selection could be made more complicated so this
     * is kept even though it is superfluous.
     *
     * @param edge the Edge to select.
     */
    protected void setSelectedEdge(Edge edge) {

        // UnSelects the previous Edge.
        if (selectedEdge != null) unSelect();

        // Selects the new Edge.
        select(edge);

    }

    /**
     * Animates the selection of an Edge.
     *
     * @param edge the Edge to highlight.
     * @param canvas the Canvas to render in.
     */
    private void edgeSelectAnimation(Edge edge, Canvas canvas) {

        // Selects the Edge.
        setSelectedEdge(edge);
        render(canvas);

    }

    /**
     * Queues an animation to select the inputed Edge and wait for a small time.
     *
     * @param edge the edge to select.
     * @param message the message to animate with.
     * @param time the total unscaled time in milliseconds for the animation.
     */
    private void queueEdgeSelectAnimation(Edge edge, String message, int time) {
        animationLog.add(new SelectEdge(edge, message, time));

    }

    /**
     * Highlights all Edges in a path.
     *
     * @param node the Node to highlight the path from.
     */
    private void highlightPath(Node node) {

        // Highlights the path.
        for (Node currNode = node;; currNode = currNode.children[0]) {

            // Breaks when there is no edge to highlight.
            if (currNode == null || currNode.children[0] == null) break;

            // Highlights the edge on the path.
            for (Edge edge : (ArrayList<Edge>)currNode.children[0].extraData[0])
                if (edge.dest == currNode) highlightEdge(edge);

        }
    }

    /**
     * UnHighlights all Edges in a path.
     *
     * @param node the Node to highlight the path for.
     */
    private void unHighlightPath(Node node) {

        // UnHighlights the path.
        for (Node currNode = node;; currNode = currNode.children[0]) {

            // Breaks when there is no edge to UnHighlight.
            if (currNode == null || currNode.children[0] == null) break;

            // UnHighlights the edge on the path.
            for (Edge edge : (ArrayList<Edge>)currNode.children[0].extraData[0])
                if (edge.dest == currNode) unHighlightEdge(edge);

        }
    }

    /**
     * Highlights this Node's path.
     *
     * @param node the Node to highlight the path for.
     * @param canvas the Canvas to render in.
     */
    private void highlightPathAnimation(Node node, Canvas canvas) {

        // Highlights the path and renders.
        highlightPath(node);
        render(canvas);

        // UnHighlights the path.
        unHighlightPath(node);

    }

    /**
     * Queues an animation to highlight a Node's path.
     *
     * @param node the Node to highlight the path for.
     * @param message the message to animate with.
     * @param time the total unscaled time in milliseconds for the animation.
     */
    private void queueHighlightPathAnimation(Node node, String message, int time) {
        animationLog.add(new HighlightPath(node, message, time));

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
     * @param time the total unscaled time in milliseconds for the animation.
     */
    private void queueEdgeHighlightAnimation(Edge edge, String message, int time) {
        animationLog.add(new HighlightEdge(edge, message, time));

    }

    /**
     * Calculates the unit normal vector of the inputed vector.
     *
     * Used to place text describing edge weights and to place vector arrows.
     *
     * @param x the x magnitude of the vector.
     * @param y the y magnitude of the vector.
     * @return an array of two floats representing the unit normal vector.
     */
    private float[] calcUnitNormal(float x, float y) {
        float tot = Math.abs(x) + Math.abs(y);
        float[] vec = new float[2];

        // Creates the unit normal vector.
        vec[0] = y == 0.0 ? (float) 0.0 : -y / tot;
        vec[1] = x == 0.0 ? (float) 0.0 : -x / tot;

        // Returns the unit normal.
        return vec;

    }

    /**
     * Calculates the unit vector perpendicular to the inputed vector.
     *
     * Does so by taking the unit normal and inverting it if it points upwards.
     * Used to place text describing edge weights.
     *
     * @param x the x magnitude of the vector.
     * @param y the y magnitude of the vector.
     * @return an array of two ints representing the unit perpendicular vector.
     */
    private float[] calcUnitPerpendicular(float x, float y) {

        // Gets the unit normal.
        float[] vec = calcUnitNormal(x, y);

        // Inverts the vector if it points downwards.
        if (vec[1] < 0) {
            vec[0] = vec[0] == 0.0 ? (float) 0.0 : -vec[0];
            vec[1] = vec[1] == 0.0 ? (float) 0.0 : -vec[1];

        }

        // Returns the upward-facing perpendicular vector.
        return vec;

    }

    /**
     * Draws a particular edge of the Graph.
     *
     * The selectedEdge is coloured blue and highlightedEdges are coloured
     * green.
     *
     * @param edge the edge to draw.
     * @param canvas the Canvas to draw in.
     */
    private void drawEdge(Edge edge, Canvas canvas) {
        double magnitude;
        float[] norm;
        float[] norm2 = new float[2];
        float[] vec = new float[2];
        Paint colour = new Paint();
        colour.setStrokeWidth(6);

        // Stores the Edge vector for convenience.
        float[] eVec = {edge.dest.position[0] - edge.start.position[0],
                edge.dest.position[1] - edge.start.position[1]};

        // Stores the Edge magnitude for convenience.
        magnitude = Math.sqrt(Math.pow(edge.dest.position[0] - edge.start.position[0], 2.0) +
                Math.pow(edge.dest.position[1] - edge.start.position[1], 2.0));

        // Sets the Edge's colour based its highlight status.
        if (edge == selectedEdge) colour.setARGB(255, AnimationParameters.SEL_VEC_R,
                AnimationParameters.SEL_VEC_G, AnimationParameters.SEL_VEC_B);
        else if (highlightedEdges.contains(edge)) colour.setARGB(255, AnimationParameters.HIL_VEC_R,
                    AnimationParameters.HIL_VEC_G, AnimationParameters.HIL_VEC_B);
        else colour.setARGB(255, AnimationParameters.VEC_R,
                    AnimationParameters.VEC_G, AnimationParameters.VEC_B);

        // Draws the Edge.
        canvas.drawLine(edge.start.position[0], edge.start.position[1],
                edge.dest.position[0], edge.dest.position[1], colour);

        // Finds an upwards facing unit vector perpendicular to the vector
        // between the edge's Nodes.
        norm = calcUnitNormal(eVec[0], eVec[1]);

        // Enlarges the perpendicular vector.
        norm[0] *= AnimationParameters.NODE_RADIUS / 1.3;
        norm[1] *= AnimationParameters.NODE_RADIUS / 1.3;

        // Calculates the position of the weight. It will be placed above the
        // center of the edge.
        vec[0] = (float) (norm[0] + edge.start.position[0] + (eVec[0] / 2.0));
        vec[1] = (float) (norm[1] + edge.start.position[1] + (eVec[1] / 2.0));

        // Draws the weight.
        colour.setTextAlign(Paint.Align.CENTER);
        colour.setTextSize((int)(AnimationParameters.NODE_RADIUS));
        canvas.drawText(String.valueOf(edge.weight), vec[0], vec[1], colour);

        // Finds the unit normal vector.
        norm = calcUnitPerpendicular(eVec[0], eVec[1]);

        // Enlarges the normal vector.
        norm[0] *= AnimationParameters.NODE_RADIUS / 2;
        norm[1] *= AnimationParameters.NODE_RADIUS / 2;

        // Finds an starting position at which to place the pointer.
        magnitude = 1.0 - AnimationParameters.NODE_RADIUS / magnitude;
        vec[0] = (float) (edge.start.position[0] + (eVec[0] * magnitude));
        vec[1] = (float) (edge.start.position[1] + (eVec[1] * magnitude));

        // Draws the left part of the pointer.
        norm2[0] = (float) (norm[0] * Math.cos(0.7853982) - norm[1] * Math.sin(0.7853982));
        norm2[1] = (float) (norm[0] * Math.sin(0.7853982) + norm[1] * Math.cos(0.7853982));
        canvas.drawLine(vec[0], vec[1], vec[0] + norm2[0], vec[1] + norm2[1], colour);

        // Draws the right part of the pointer.
        norm2[0] = (float) (norm[0] * Math.cos(3 * 0.7853982) - norm[1] * Math.sin(3 * 0.7853982));
        norm2[1] = (float) (norm[0] * Math.sin(3 * 0.7853982) + norm[1] * Math.cos(3 * 0.7853982));
        canvas.drawLine(vec[0], vec[1], vec[0] + norm2[0], vec[1] + norm2[1], colour);

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
     * Quickly places all nodes and renders the tree.
     * To be used at the end of pathfinds and spanning tree algorithms.
     */
    @Override
    protected void finalRender() {
        finishTraversalAnimation();
        unSelect();
        unHighlightAllEdges();
        render();

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
     * Animation item for highlighting a path.
     */
    private class HighlightPath extends AnimationItem {

        // Animation time.
        int time;

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getCanvas().getWidth(),
                MainActivity.getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the Node's path is
         * highlighted.
         *
         * @param node the Node to start at.
         * @param message the message to animate with.
         * @param time the total unscaled time in milliseconds for the animation.
         */
        HighlightPath(Node node, String message, int time) {
            super(message);
            this.time = time;
            canvas = new Canvas(bmp);
            highlightPathAnimation(node, canvas);

        }

        /**
         * Displays the frame wherein the the path is highlighted.
         */
        @Override
        public void run() {
            super.run();

            // Draws the frame.
            MainActivity.getCanvas().drawBitmap(
                    bmp, MainActivity.getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep(time);

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Animation item for selecting an Edge.
     */
    private class SelectEdge extends AnimationItem {

        // Animation time.
        int time;

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getCanvas().getWidth(),
                MainActivity.getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the inputed edge is
         * selected.
         *
         * @param edge the Edge to select.
         * @param message the message to animate with.
         * @param time the total unscaled time in milliseconds for the animation.
         */
        SelectEdge(Edge edge, String message, int time) {
            super(message);
            this.time = time;
            canvas = new Canvas(bmp);
            edgeSelectAnimation(edge, canvas);

        }

        /**
         * Displays the frame wherein the the inputed edge is selected.
         */
        @Override
        public void run() {
            super.run();

            // Draws the frame.
            MainActivity.getCanvas().drawBitmap(
                    bmp, MainActivity.getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep(time);

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }

    /**
     * Animation item for highlighting an Edge.
     */
    private class HighlightEdge extends AnimationItem {

        // Animation time.
        int time;

        // Canvas and bitmap to store the frame.
        Canvas canvas;
        Bitmap bmp = Bitmap.createBitmap(MainActivity.getCanvas().getWidth(),
                MainActivity.getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        /**
         * Constructor for this item. Stores a frame wherein the inputed edge is
         * highlighted.
         *
         * @param edge the Edge to highlight.
         * @param message the message to animate with.
         * @param time the total unscaled time in milliseconds for the animation.
         */
        HighlightEdge(Edge edge, String message, int time) {
            super(message);
            this.time = time;
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
            MainActivity.getCanvas().drawBitmap(
                    bmp, MainActivity.getCanvas().getClipBounds(),
                    canvas.getClipBounds(), new Paint());

            // Sleeps for a little while.
            sleep(time);

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }
}