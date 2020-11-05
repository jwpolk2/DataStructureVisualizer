package com.example.datastructurevisualizer;

import java.util.ArrayList;

/**
 * TODO comment
 *
 * TODO implement special graph functionality (pathfinding?)
 */
public class Graph extends NodeVisualizer {
    ArrayList<Node> nodes = new ArrayList<>();

    /**
     * Inserts a graph into the space.
     * TODO handle node children/weights
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {
        nodes.add(new Node(key, 100));

    }

    /**
     * There are no graph insert animations.
     *
     * @param key the key to be inserted.
     */
    @Override
    protected void insertAnim(int key) {
        insertNoAnim(key);

    }

    /**
     * Removes a graph Node from the tree.
     *
     * TODO handle removing children in other nodes
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeNoAnim(int key) {
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
}