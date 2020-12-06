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
    protected static final int ANIM_TIME = 1000;

    // Default number of steps for any Node movement animation.
    protected static final int MOVEMENT_FRAMES = 20;

    // Default colour of a vector.
    protected static final int VEC_R = 0;
    protected static final int VEC_G = 0;
    protected static final int VEC_B = 0;

    // Default colour of a selected vector.
    protected static final int SEL_VEC_R = 0;
    protected static final int SEL_VEC_G = 0;
    protected static final int SEL_VEC_B = 200;

    // Default colour of a highlighted vector.
    protected static final int HIL_VEC_R = 0;
    protected static final int HIL_VEC_G = 120;
    protected static final int HIL_VEC_B = 0;

    // Default colour of text.
    protected static final int TEXT_R = 255;
    protected static final int TEXT_G = 255;
    protected static final int TEXT_B = 255;

    // Default colour of background.
    protected static final int BACK_R = 255;
    protected static final int BACK_G = 255;
    protected static final int BACK_B = 255;

    // Default colour of Node.
    protected static final int NODE_R = 200;
    protected static final int NODE_G = 0;
    protected static final int NODE_B = 0;

    // Default colour of a selected Node.
    protected static final int SEL_NODE_R = 0;
    protected static final int SEL_NODE_G = 0;
    protected static final int SEL_NODE_B = 200;

    // Default colour of a highlighted Node.
    protected static final int HIL_NODE_R = 0;
    protected static final int HIL_NODE_G = 120;
    protected static final int HIL_NODE_B = 0;

    // Default colour of an explored Node.
    protected static final int EXP_NODE_R = 0;
    protected static final int EXP_NODE_G = 40;
    protected static final int EXP_NODE_B = 0;

    // Width of a Node.
    public static final float NODE_RADIUS = 35f;

    // All sizes and distances will be multiplied by the scaleFactor.
    // UNUSED. Would have been used for zooming in and out.
    protected static float scaleFactor = (float) 1;

    // Default depth between layers of a tree.
    protected static float depthLen = 120;

    // All animation times will be divided by animSpeed.
    protected static float animSpeed = 1;

    /**
     * Sets the animSpeed to the inputed animSpeed.
     *
     * @param newSpeed the new animSpeed.
     */
    public static void setAnimSpeed(float newSpeed) {
        animSpeed = newSpeed;

    }

    // Mutex that prevents animations from occurring concurrently.
    private static final Semaphore mutex = new Semaphore(1);

    /**
     * Locks the animation mutex to stop concurrent animation.
     */
    public static void beginAnimation() {
        try {
            pause();
            mutex.acquire();
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
    private static boolean paused = false;

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
