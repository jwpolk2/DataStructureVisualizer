package com.example.datastructurevisualizer;

import com.example.datastructurevisualizer.ui.Visualizer;

import java.util.concurrent.TimeUnit;

/**
 * Class representing an individual animation. Should be inherited and used
 * within DataStructureVisualizer and its child classes.
 *
 * Stores a message which will be rendered during run and reverse.
 */
public class AnimationItem {
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
     */
    public void reverse() {
        Visualizer.displayMessage(message);

    }

    /**
     * Sleeps for a certain number of milliseconds.
     */
    protected void sleep(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep((int)(time / AnimationParameters.animSpeed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}