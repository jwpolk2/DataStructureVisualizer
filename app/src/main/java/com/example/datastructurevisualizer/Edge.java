package com.example.datastructurevisualizer;

/**
 * Represents an Edge between two Nodes.
 * An ArrayList of edges is stored in extraData[0] in Graph Nodes.
 *
 * start and dest represent the Nodes that an Edge connects.
 * weight is the cost associated with traversing over the Edge.
 * render marks whether this Edge should be rendered, used to stop undirected
 * Edges from double rendering.
 */
public class Edge implements Comparable {
    Node dest;
    Node start;
    int weight;
    boolean render;

    /**
     * Constructor for this Node. Assigns start, dest, weight and render to the
     * inputed values.
     *
     * @param start start Node of this Edge.
     * @param dest destination Node of this Edge.
     * @param weight weight of this Edge.
     * @param render whether or not to render this Edge.
     */
    Edge(Node start, Node dest, int weight, boolean render) {
        this.start = start;
        this.dest = dest;
        this.weight = weight;
        this.render = render;

    }

    /**
     * Compares two Edges.
     *
     * @param input the Edge to compare to.
     * @return -1 if smaller than input, 1 if larger, else 0.
     */
    @Override
    public int compareTo(Object input) {
        Edge comp = (Edge)input;
        if (weight < comp.weight) return -1;
        else if (weight > comp.weight) return 1;
        else return 0;

    }
}
