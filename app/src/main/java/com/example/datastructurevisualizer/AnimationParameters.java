package com.example.datastructurevisualizer;

import com.example.datastructurevisualizer.ui.Visualizer;

import java.util.concurrent.Semaphore;

/**
 * Class that stores visualization parameters.
 *
 * Also contains methods to begin and end an animation (lock mutex and empty
 * visualizer text).
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

    // Default colour of background.
    static final int BACK_R = 255;
    static final int BACK_G = 255;
    static final int BACK_B = 255;

    // Default colour of Node.
    static final int NODE_R = 200;
    static final int NODE_G = 0;
    static final int NODE_B = 0;

    // Default colour of a selected Node.
    static final int SEL_NODE_R = 0;
    static final int SEL_NODE_G = 0;
    static final int SEL_NODE_B = 200;

    // Default colour of a highlighted Node.
    static final int HIL_NODE_R = 0;
    static final int HIL_NODE_G = 80;
    static final int HIL_NODE_B = 0;

    // Current position in the Canvas.
    // TODO remove
    static float xPos = 0;
    static float yPos = 0;

    // Width of a Node.
    static final float NODE_RADIUS = 20f;

    // All sizes and distances will be multiplied by the scaleFactor.
    static float scaleFactor = (float) 1.75;

    // Default depth between layers of a tree.
    static float depthLen = 120;

    // All animation times will be divided by animSpeed.
    static float animSpeed = 1;

    // Mutex that prevents animations from occurring concurrently.
    private static Semaphore mutex = new Semaphore(1);

    /**
     * Locks the animation mutex to stop concurrent animation.
     */
    public static void beginAnimation() {
        try {
            pause();
            mutex.acquire();
            Visualizer.displayMessage("");
        } catch (InterruptedException e) {
            e.printStackTrace();
            mutex.release();
        }
    }

    /**
     * Attempts to begin an animation.
     *
     * @return true if the mutex is acquired, otherwise false.
     */
    public static boolean tryBeginAnimation() {
        return mutex.tryAcquire();

    }

    /**
     * Unlocks the animation mutex to allow another animation to occur.
     */
    public static void stopAnimation() {
        Visualizer.displayMessage("");
        mutex.release();

    }

    // Pause variable.
    static boolean paused = false;

    /**
     * Pauses the animation.
     */
    public static void pause() {
        paused = true;

    }

    /**
     * Unpauses the animation.
     */
    public static void unpause() {
        paused = false;

    }

    /**
     * Tells the user whether or not the Animation is paused.
     *
     * @return true if paused, otherwise false.
     */
    public static boolean isPaused() {
       return paused;

    }
}
