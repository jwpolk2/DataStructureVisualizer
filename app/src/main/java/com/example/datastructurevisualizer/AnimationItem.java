package com.example.datastructurevisualizer;

/**
 * Class representing an individual animation. Should be inherited and used
 * within Visualizer classes.
 */
public interface AnimationItem {

    // Runs the animation.
    void run();

    // Runs the animation backwards.
    void reverse();

}