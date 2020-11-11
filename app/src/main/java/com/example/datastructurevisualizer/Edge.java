package com.example.datastructurevisualizer;

/**
 * Represents an Edge between two Nodes.
 * An ArrayList of edges is stored in extraData[0] in Graph Nodes.
 */
public class Edge {
    Node dest;
    Node start;
    int weight;

    /**
     * Constructor for this Node. Assigns dest and weight to the inputed dest
     * and weight.
     *
     * @param start start Node of this Edge.
     * @param dest destination Node of this Edge.
     * @param weight weight of this Edge.
     */
    Edge(Node start, Node dest, int weight) {
        this.start = start;
        this.dest = dest;
        this.weight = weight;

    }
}
