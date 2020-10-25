package com.example.datastructurevisualizer;

import java.util.concurrent.Semaphore;

/**
 * Class that stores visualization parameters.
 */
public class AnimationParameters {

    // Default time taken for any given animation.
    static final int ANIM_TIME = 1000;

    // Default number of steps for any Node movement animation.
    static final int MOVEMENT_FRAMES = 20;

    // Default colour of a vector.
    static final int VEC_R = 0;
    static final int VEC_G = 0;
    static final int VEC_B = 0;

    // Default colour of text.
    static final int TEXT_R = 0;
    static final int TEXT_G = 0;
    static final int TEXT_B = 0;

    // Current position in the Canvas.
    static float xPos = 0;
    static float yPos = 0;

    // All animation times will be divided by animSpeed.
    static float animSpeed = 1;

    // All sizes and distances will be multiplied by the scaleFactor.
    static float scaleFactor = (float) 1.75;

    // Default depth between layers of a tree.
    static float depthLen = 120;

    // Mutex that prevents animations from occurring concurrently.
    private static Semaphore mutex = new Semaphore(1);

    /**
     * Locks the animation mutex to stop concurrent animation.
     */
    public static void beginAnimation() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            mutex.release();
        }
    }

    /**
     * Unlocks the animation mutex to allow another animation to occur.
     */
    public static void stopAnimation() {
        mutex.release();

    }
}
