package com.example.datastructurevisualizer;

/**
 * Generic node to be used across all data structures.
 * Contains int key and int value for identification.
 * Contains int[2] position and int[2] destination for movement.
 * Contains Node[] children for children.
 * Contains Object[] extradata in case extra data is needed.
 */
public class Node {
    int key;
    int value;
    int[] position;
    int[] destination;
    Node[] children;
    Object[] extraData;

}
