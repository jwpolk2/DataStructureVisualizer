package com.example.datastructurevisualizer;

import junit.framework.TestCase;


public class EdgeTest extends TestCase {
    private Node sNode = new Node(1, 2);
    private Node fNode = new Node(2, 2);
    private int weight = 2;
    private Edge edge = new Edge(sNode, fNode, weight, false);

    public void testCompareTo_lessWeight() {
        Edge compEdge = new Edge(fNode, sNode, 3, false);

        assertEquals(-1, edge.compareTo(compEdge));
    }

    public void testCompareTo_moreWeight() {
        Edge compEdge = new Edge(fNode, sNode, 1, false);

        assertEquals(1, edge.compareTo(compEdge));
    }

    public void testCompareTo_sameWeight() {
        Edge compEdge = new Edge(fNode, sNode, 2, false);

        assertEquals(0, edge.compareTo(compEdge));
    }
}