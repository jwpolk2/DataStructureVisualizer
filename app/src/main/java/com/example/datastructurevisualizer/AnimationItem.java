package com.example.datastructurevisualizer;

import android.util.Log;

import com.example.datastructurevisualizer.ui.Visualizer;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Class representing an individual animation. Should be inherited and used
 * within DataStructureVisualizer and its child classes.
 *
 * Stores a message which will be rendered during run and reverse.
 */
public abstract class AnimationItem {
    private String message;

    /**
     * Stores a message to display while running.
     *
     * @param message the message to display while running.
     */
    public AnimationItem(String message) {
        this.message = message;

    }

    /**
     * Runs the animation. Should be overridden.
     * This method displays message and should be called in all child classes.
     */
    public void run() {
        Visualizer.displayMessage(message);

    }

    /**
     * Runs the animation in reverse. Should be overridden.
     * This method displays message and should be called in all child classes.
     *
     * Note: This is defunct. Though reverse is still called, it always calls run.
     */
    public void reverse() {
        Visualizer.displayMessage(message);

    }

    /**
     * Sleeps for a certain number of milliseconds.
     */
    protected void sleep(long time) {
        for (long start = System.currentTimeMillis();
             System.currentTimeMillis() - start < time &&
                !AnimationParameters.isPaused(););

    }
}