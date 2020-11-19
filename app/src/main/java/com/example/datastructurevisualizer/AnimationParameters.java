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
    public static final int ANIM_TIME = 1000;

    // Default number of steps for any Node movement animation.
    public static final int MOVEMENT_FRAMES = 20;

    // Default colour of a vector.
    public static final int VEC_R = 0;
    public static final int VEC_G = 0;
    public static final int VEC_B = 0;

    // DEfault colour of a selected vector.
    public static final int SEL_VEC_R = 0;
    public static final int SEL_VEC_G = 0;
    public static final int SEL_VEC_B = 200;

    // Default colour of a highlighted vector.
    public static final int HIL_VEC_R = 0;
    public static final int HIL_VEC_G = 120;
    public static final int HIL_VEC_B = 0;

    // Default colour of text.
    public static final int TEXT_R = 255;
    public static final int TEXT_G = 255;
    public static final int TEXT_B = 255;

    // Default colour of background.
    public static final int BACK_R = 255;
    public static final int BACK_G = 255;
    public static final int BACK_B = 255;

    // Default colour of Node.
    public static final int NODE_R = 200;
    public static final int NODE_G = 0;
    public static final int NODE_B = 0;

    // Default colour of a selected Node.
    public static final int SEL_NODE_R = 0;
    public static final int SEL_NODE_G = 0;
    public static final int SEL_NODE_B = 200;

    // Default colour of a highlighted Node.
    public static final int HIL_NODE_R = 0;
    public static final int HIL_NODE_G = 120;
    public static final int HIL_NODE_B = 0;

    // Default colour of an explored Node.
    public static final int EXP_NODE_R = 0;
    public static final int EXP_NODE_G = 40;
    public static final int EXP_NODE_B = 0;

    // Width of a Node.
    public static final float NODE_RADIUS = 35f;

    // All sizes and distances will be multiplied by the scaleFactor.
    static float scaleFactor = (float) 1;

    // Default depth between layers of a tree.
    static float depthLen = 120;

    // All animation times will be divided by animSpeed.
    // TODO unused, remove?
    static float animSpeed = 1;

    // Mutex that prevents animations from occurring concurrently.
    private static final Semaphore mutex = new Semaphore(1);

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
