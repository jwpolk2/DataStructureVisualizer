package com.example.datastructurevisualizer;

/**
 * Represents an Edge between two Nodes.
 * An ArrayList of edges is stored in extraData[0] in Graph Nodes.
 */
public class Edge implements Comparable {
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
