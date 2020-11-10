package com.example.datastructurevisualizer;

import android.graphics.Canvas;

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
 * Includes breadth first and dijkstra pathfinds, // TODO more algorithms
 * Overriddes getNode and getAllNodes to work for a graph.
 * Overrides render to work for a graph.
 */
public class Graph extends NodeVisualizer {
    private ArrayList<Node> nodes = new ArrayList<>();

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
        ((ArrayList<Edge>)start.extraData[0]).add(new Edge(dest, weight));

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
        for (int i = 0; i < nodes.size(); ++i)
            if (nodes.get(i).key == key) nodes.remove(i);

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
                    queueQueueAddAnimation(currNode, "Add " + edge.dest.value + " to queue");

                }
            }

            // Marks the Node as explored.
            queueNodeExploreAnimation(currNode, currNode.key + " has been explored");

        }

        // If the destination Node is not found, prints a message saying so.
        queueNodeSelectAnimation(null, "Destination not found");

    }

    /**
     * Animates a dijkstra style pathfind.
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
                    queuePriorityQueueAddAnimation(currNode, "Add " + edge.dest.value + " to queue");

                }
            }

            // Marks the Node as explored.
            queueNodeExploreAnimation(currNode, currNode.key + " has been explored");

        }

        // If the destination Node is not found, prints a message saying so.
        queueNodeSelectAnimation(null, "Destination not found");

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

        // Renders each Node.
        for (Node node : nodes) drawNode(node, canvas);

        // Draws the nodeList over the Canvas.
        nodeList.render(canvas);

        // Renders this frame to the Canvas.
        super.render(canvas);

    }
}