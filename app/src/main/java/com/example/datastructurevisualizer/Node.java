package com.example.datastructurevisualizer;

/**
 * Generic node to be used across all data structures.
 * Contains int key and int value for identification.
 * Contains int[2] position and int[2] destination for movement.
 * Contains Node[] children for children.
 * Contains Object[] extraData in case extra data is needed.
 */
public class Node {
    int key;
    int value;
    int[] position;
    int[] destination;
    Node[] children;
    Object[] extraData;

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