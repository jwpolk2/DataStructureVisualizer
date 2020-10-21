package com.example.datastructurevisualizer;

/**
 * Class that stores visualization parameters.
 */
public class AnimationParameters {

    // Default time taken for any given animation.
    static final int ANIM_TIME = 1000;

    // Default colour of a vector.
    static final int VEC_R = 0;
    static final int VEC_G = 0;
    static final int VEC_B = 0;

    // Current position in the Canvas.
    static float xPos = 0;
    static float yPos = 0;

    // All animation times will be divided by animSpeed.
    static float animSpeed = 1;

    // All sizes and distances will be multiplied by the scaleFactor.
    static float scaleFactor = 1;

}
