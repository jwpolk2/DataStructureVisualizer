package com.example.datastructurevisualizer;

import java.util.ArrayList;
import android.graphics.Canvas;
import com.example.datastructurevisualizer.ui.Visualizer;

/**
 * Parent class for all data structures.
 *
 * Includes a log of insertions/deletions and a log of animations.
 * A LinkedList 'nodeList' is included to serve as a visible stack or queue
 * while performing traversals.
 * Incomplete insert, remove, and clear methods are defined here as templates
 * for child classes.
 * Every insertion and deletion in the tree is tracked in insert and remove.
 * These can be undone/redone with undo/redo.
 * animate, beginAnimation, and stopAnimation help mediate use of the
 * animationQueue.
 * finalRender should be overridden. It should reset a data structure's state
 * and perform a final render.
 * There are two render methods, one of which takes a Canvas as input, the other
 * takes no input and uses the default canvas. super.render(Canvas) should be
 * called in each child render method.
 * Includes animationPause, animationNext, and animationPrev to iterate through
 * animations. Pause must be called before any animation is run.
 * Includes the AnimationItem and queueing method for only displaying a message.
 */
public abstract class DataStructureVisualizer {

    // Log of additions and deletions into this tree.
    protected ArrayList<StructureAction> log = new ArrayList<StructureAction>();

    // Current position within the log.
    protected int logIndex = 0;

    // Whether or not the log can be edited.
    protected boolean logAvailable = true;

    // Log of animations that have happened during the most recent animation.
    // Static so that substructures can agglomerate animations.
    protected static ArrayList<AnimationItem> animationLog = new ArrayList<AnimationItem>();

    // Current position within the animation log.
    private static int animationIndex = 0;

    // Whether or not the last animation went forwards.
    private static boolean animationForward = true;

    // Stack/Queue of items. Used for various traversals and pathfinds.
    protected static LinkedList nodeList = new LinkedList();

    /**
     * Inserts a Node into the tree and plays no animation. Should be overridden.
     *
     * @param key the key to be inserted.
     */
    public abstract void insertNoAnim(int key);

    /**
     * Inserts a Node into the tree and plays an animation. Should be overridden.
     *
     * @param key the key to be inserted.
     */
    protected abstract void insertAnim(int key);

    /**
     * Removes a Node from the tree and plays no animation. Should be overridden.
     *
     * @param key the key to be removed.
     */
    public abstract void removeNoAnim(int key);

    /**
     * Removes a Node from the tree and plays an animation. Should be overridden.
     *
     * @param key the key to be removed.
     */
    protected abstract void removeAnim(int key);

    /**
     * Runs an insert animation.
     */
    public class RunInsert implements Runnable {
        int key;
        RunInsert(int key) { this.key = key; }
        @Override
        public void run() {
            beginAnimation();
            insertAnim(key);
            animate();
            stopAnimation();

        }
    }

    /**
     * Inserts an element into the DataStructure.
     *
     * @param key the key to be inserted.
     */
    public void insert(int key) {
        RunInsert run = new RunInsert(key);
        new Thread(run).start();

    }

    /**
     * Runs several insert animations.
     */
    public class RunInsertMany implements Runnable {
        ArrayList<Integer> keys;
        RunInsertMany(ArrayList<Integer> keys) { this.keys = keys; }
        @Override
        public void run() {
            beginAnimation();
            for (Integer curr : keys) insertAnim(curr);
            queueMessage("Finished multiple insertion.",
                    AnimationParameters.ANIM_TIME);
            animate();
            stopAnimation();

        }
    }

    /**
     * Inserts many elements into the DataStructure.
     *
     * @param keys the keys to be inserted.
     */
    public void insert(ArrayList<Integer> keys) {
        RunInsertMany run = new RunInsertMany(keys);
        new Thread(run).start();

    }

    /**
     * Runs a removal animation.
     */
    public class RunRemove implements Runnable {
        int key;
        RunRemove(int key) { this.key = key; }
        @Override
        public void run() {
            beginAnimation();
            removeAnim(key);
            animate();
            stopAnimation();

        }
    }

    /**
     * Removes an element from a DataStructure.
     *
     * @param key the key to be removed.
     */
    public void remove(int key) {
        RunRemove run = new RunRemove(key);
        new Thread(run).start();

    }

    /**
     * Checks if the inputed key is represented within this Data Structure.
     *
     * @param key the key to check for.
     * @return true if the Data Structure contains the key, otherwise false.
     */
    protected boolean contains(int key) {
        for (Integer i : getAllKeys()) if (i == key) return true;
        return false;

    }

    /**
     * Returns an ArrayList containing all keys in this data structure.
     * Should be overridden.
     *
     * @return an ArrayList containing all keys in this data structure.
     */
    public abstract ArrayList<Integer> getAllKeys();

    /**
     * Clears the data structure. This action will be logged.
     */
    public void clear() {
        beginAnimation();
        logClear();
        logAvailable = false;
        for (Integer i : getAllKeys()) removeNoAnim(i);
        logAvailable = true;
        stopAnimation();

    }

    /**
     * Clears the data structure without interrupting animations.
     */
    protected void clearNoAnim() {
        for (Integer i : getAllKeys()) removeNoAnim(i);

    }

    /**
     * Class representing an addition or deletion performed in the Tree.
     */
    private static abstract class StructureAction {
        int key;

        /**
         * @param key the key for this action.
         */
        StructureAction(int key) { this.key = key; }

        /**
         * Performs this TreeAction's action. Should be overridden.
         */
        abstract void action();

    }

    /**
     * Class representing an insertion into the Data Structure.
     */
    private class KeyAdd extends StructureAction {
        KeyAdd(int key) { super(key); }

        /**
         * Inserts the stored key into the Data Structure.
         */
        void action() { insertNoAnim(key); }

    }

    /**
     * Class representing a deletion from the Data Structure.
     */
    private class KeyRemove extends StructureAction {
        KeyRemove(int key) { super(key); }

        /**
         * Removes the stored key from the Data Structure.
         */
        void action() { removeNoAnim(key); }

    }

    /**
     * Class representing a deletion from the Data Structure.
     */
    private class StructureClear extends StructureAction {
        StructureClear(int key) { super(key); }

        /**
         * Removes the stored key from the Data Structure.
         */
        void action() { clearNoAnim(); }

    }

    /**
     * Logs an addition to the Data Structure.
     *
     * @param key the key that has been added.
     */
    protected void logAdd(int key) {

        // Will not add to the log if it is unavailable.
        if (!logAvailable) return;

        // Removes any items which are ahead of the current index.
        while (logIndex < log.size()) log.remove(logIndex);

        // Adds the item to the log.
        ++logIndex;
        log.add(new KeyAdd(key));

    }

    /**
     * Logs a deletion from the Data Structure.
     *
     * @param key the key that has been removed.
     */
    protected void logRemove(int key) {

        // Will not add to the log if it is unavailable.
        if (!logAvailable) return;

        // Adds the item to the log.
        ++logIndex;
        log.add(new KeyRemove(key));

    }

    /**
     * Logs a clearing of the Data Structure.
     */
    protected void logClear() {

        // Will not add to the log if it is unavailable.
        if (!logAvailable) return;

        // Adds the item to the log.
        ++logIndex;
        log.add(new StructureClear(0));

    }

    /**
     * Redoes an action. Does so by reconstructing the DataStructure using log.
     *
     * Will fail if it cannot lock the animation mutex.
     */
    public void redo() {

        // Will redo if and only if there is something to be redone.
        if (logIndex < log.size()) {

            // Fails if the animation mutex cannot be locked.
            if (!tryBeginAnimation()) return;

            // Marks the log as unavailable.
            logAvailable = false;

            // Clears the DataStructure.
            clearNoAnim();

            // Rebuilds the tree.
            ++logIndex;
            for (int i = 0; i < logIndex; ++i) log.get(i).action();

            // Renders the DataStructure.
            finalRender();

            // Marks the log as available.
            logAvailable = true;

            // Unlocks the animation mutex.
            stopAnimation();

        }
    }

    /**
     * Undoes the previous action. Does so by reconstructing the DataStructure
     * using log.
     *
     * Will fail if it cannot lock the animation mutex.
     */
    public void undo() {

        // Fails if the animation mutex cannot be locked.
        if (!tryBeginAnimation()) return;

        // Marks the log as unavailable.
        logAvailable = false;

        // Clears the tree.
        clearNoAnim();

        // Rebuilds the tree.
        logIndex = Math.max(logIndex - 1, 0);
        for (int i = 0; i < logIndex; ++i) log.get(i).action();

        // Renders the DataStructure.
        finalRender();

        // Marks the log as available.
        logAvailable = true;

        // Unlocks the animation mutex.
        stopAnimation();

    }

    /**
     * Performs all animations in the animation queue, then empties the queue.
     * Will return early if the animation is paused.
     */
    public void animate() {

        // Unpauses the animation.
        animationUnpause();

        // Adds a break between the previous animation and this.
        Visualizer.displayMessage("");

        // Animates every item in the animationLog.
        // animationIndex is initialized in beginAnimation.
        for (; animationIndex < animationLog.size(); ++animationIndex) {
            if (AnimationParameters.isPaused()) return;
            animationLog.get(animationIndex).run();

        }

        // Performs a finalRender of the DataStructure.
        finalRender();

        // Pauses the animation.
        animationPause();

    }

    /**
     * Begins an animation in the DataStructure. Locks the animation mutex and
     * clears the animationLog.
     */
    protected void beginAnimation() {
        AnimationParameters.beginAnimation();
        finalRender();
        animationIndex = 0;
        animationForward = true;
        animationLog.clear();

    }

    /**
     * Attempts to begin an animation in the DataStructure. Locks the animation
     * mutex and clears the animationLog.
     *
     * @return true if locking successful, otherwise false.
     */
    protected boolean tryBeginAnimation() {

        // Fails if mutex cannot be locked.
        if (!AnimationParameters.tryBeginAnimation()) return false;

        // Clears the animation log.
        animationIndex = 0;
        animationForward = true;
        animationLog.clear();

        // Returns true.
        return true;

    }

    /**
     * Stops an animation in the DataStructure. Unlocks the animation mutex.
     */
    protected void stopAnimation() {
        AnimationParameters.stopAnimation();

    }

    /**
     * Pauses animation of all data structures.
     * Written here for convenience.
     */
    public void animationPause() { AnimationParameters.pause(); }

    /**
     * Unpauses animation of all data structures.
     * Written here for convenience.
     */
    public void animationUnpause() { AnimationParameters.unpause(); }

    /**
     * Moves one step forwards in the animation.
     * Will fail if not paused or if the animation mutex cannot be locked
     * (another animation is playing).
     */
    public void animationNext() {

        // Will not attempt to animate if not paused.
        if (!AnimationParameters.isPaused()) return;

        // Attempts to lock the animation mutex.
        if (!AnimationParameters.tryBeginAnimation()) return;

        // Moves forwards one more when inverting.
        if (!animationForward) {
            ++animationIndex;
            animationForward = true;

        }

        // Will perform a finalRender if at the end of the animation.
        if (animationIndex >= animationLog.size()) {
            finalRender();
            animationIndex = animationLog.size();
            animationForward = false;

        }
        // Will otherwise perform the next animation.
        else {
            animationLog.get(animationIndex).run();
            ++animationIndex;

        }

        // Will unlock the animation mutex.
        AnimationParameters.stopAnimation();

    }

    /**
     * Moves one step backwards in the animation.
     * Will fail if not paused or if the animation mutex cannot be locked
     * (another animation is playing).
     */
    public void animationPrev() {

        // Will not attempt to animate if not paused.
        if (!AnimationParameters.isPaused()) return;

        // Attempts to lock the animation mutex.
        if (!AnimationParameters.tryBeginAnimation()) return;

        // Decrements animationIndex.
        --animationIndex;

        // Moves backwards one more when inverting.
        if (animationForward) {
            --animationIndex;
            animationForward = false;

        }

        // Will do nothing if at the beginning of the animation.
        if (animationIndex < 0) {
            animationIndex = 0;
            animationForward = true;

        }
        // Will otherwise perform the previous animation.
        else animationLog.get(animationIndex).reverse();

        // Unlock the animation mutex after successful animation.
        AnimationParameters.stopAnimation();

    }

    /**
     * Renders the data structure and does a handful of other things. Should be overridden.
     */
    protected abstract void finalRender();

    /**
     * Renders the data structure to the given Canvas. Should be overridden.
     */
    public void render(Canvas canvas) {
        if (MainActivity.getVisualizer() != null)
            MainActivity.getVisualizer().render();

    }

    /**
     * Renders the data structure to the default Canvas.
     */
    public void render() {
        render(MainActivity.getCanvas());

    }

    /**
     * Queues an animation to output a message and wait for a small time.
     *
     * @param message the message to animate with.
     * @param time the total unscaled time in milliseconds for the animation.
     */
    protected void queueMessage(String message, int time) {
        animationLog.add(new DataStructureVisualizer.AnimMessage(message, time));

    }

    /**
     * Animation item for displaying text. Does not change the Canvas.
     */
    private static class AnimMessage extends AnimationItem {

        // Animation time.
        int time;

        /**
         * Constructor for this item. Stores the text and the waiting duration.
         *
         * @param message the message to animate with.
         * @param time the total unscaled time in milliseconds for the animation.
         */
        AnimMessage(String message, int time) {
            super(message);
            this.time = time;

        }

        /**
         * Outputs the text.
         */
        @Override
        public void run() {
            super.run();
            sleep(time);

        }

        /**
         * Same as run.
         */
        @Override
        public void reverse() { run(); }

    }
}