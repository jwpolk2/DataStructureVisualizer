package com.example.datastructurevisualizer;

/**
 * Generic node to be used across all data structures.
 * Contains int key and int value for identification.
 * Contains int[2] position and int[2] destination for movement, xy.
 * Contains char[3] colour to indicate node colour, rgb.
 * Contains Node[] children for children.
 * Contains Object[] extraData in case extra data is needed.
 */
public class Node {
    int key;
    int value;
    int r, g, b;
    int[] position;
    int[] destination;
    Node[] children;
    Object[] extraData;

    /**
     * Default constructor for this Node. Initializes key to the inputed key and
     * children to an array of size numChildren.
     * position and destination will be initialized to arrays of size 2, xy.
     * rgb is grey by default. TODO change
     * value, and extraData will not be initialized, and must be initialized ad-hoc.
     *
     * @param key the key for this Node.
     * @param numChildren number of children for this Node.
     */
    Node(int key, int numChildren) {
        this.key = key;
        this.r = 60;
        this.g = 60;
        this.b = 60;
        this.position = new int[2];
        this.destination = new int[2];
        this.children = new Node[numChildren];

    }
}

/**
 * Enum representing names of children in the array children.
 */
enum ChildNames {
    LEFT(0),
    RIGHT(1);

    public final int i;
    ChildNames(int i) {
        this.i = i;

    }
}