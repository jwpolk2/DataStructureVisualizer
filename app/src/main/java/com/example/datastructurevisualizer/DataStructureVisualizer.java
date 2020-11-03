package com.example.datastructurevisualizer;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * TODO comment
 */
public class DataStructureVisualizer {

    // Log of additions and deletions into this tree.
    ArrayList<StructureAction> log = new ArrayList<StructureAction>();

    // Current position within the log.
    int logIndex = 0;

    // Whether or not the log can be edited.
    boolean logAvailable = true;

    // Log of animations that have happened during the most recent animation.
    static ArrayList<AnimationItem> animationLog = new ArrayList<AnimationItem>();

    /**
     * Inserts a Node into the tree and plays no animation. Should be overridden.
     *
     * @param key the key to be inserted.
     */
    public void insertNoAnim(int key) {}

    /**
     * Inserts a Node into the tree and plays an animation. Should be overridden.
     *
     * @param key the key to be inserted.
     */
    protected void insertAnim(int key) {}

    /**
     * Removes a Node from the tree abd plays no animation. Should be overridden.
     *
     * @param key the key to be removed.
     */
    protected void removeNoAnim(int key) {}

    /**
     * Removes a Node from the tree and plays an animation. Should be overridden.
     *
     * @param key the key to be removed.
     */
    protected void removeAnim(int key) {}

    /**
     * Runs an insert animation.
     */
    public class RunInsert implements Runnable {
        int key;
        @Override
        public void run() {
            beginAnimation();
            insertAnim(key);
            animate();
            stopAnimation();

        }
    }

    /**
     * Inserts a Node into the tree.
     *
     * @param key the key to be inserted.
     */
    public void insert(int key) {
        RunInsert run = new RunInsert();
        run.key = key;
        new Thread(run).start();

    }

    /**
     * Runs a removal animation.
     */
    public class RunRemove implements Runnable {
        int key;
        @Override
        public void run() {
            beginAnimation();
            removeAnim(key);
            animate();
            stopAnimation();

        }
    }

    /**
     * Removes a Node from the tree.
     *
     * @param key the key to be removed.
     */
    public void remove(int key) {
        RunRemove run = new RunRemove();
        run.key = key;
        new Thread(run).start();

    }

    /**
     * Clears the data structure. Should be overridden.
     */
    protected void clear() {}

    /**
     * Class representing an addition or deletion performed in the Tree.
     */
    private class StructureAction {
        int key;

        /**
         * @param key the key for this action.
         */
        StructureAction(int key) { this.key = key; }

        /**
         * Performs this TreeAction's action. Should be overridden.
         */
        void action() {}

    }

    /**
     * Class representing an insertion into the Tree.
     */
    private class KeyAdd extends StructureAction {
        KeyAdd(int key) { super(key); }

        /**
         * Inserts the stored key into the Tree.
         */
        void action() { insertNoAnim(key); }

    }

    /**
     * Class representing a deletion from the Tree.
     */
    private class KeyRemove extends StructureAction {
        KeyRemove(int key) { super(key); }

        /**
         * Removes the stored key from the Tree.
         */
        void action() { removeNoAnim(key); }

    }

    /**
     * Logs an addition to the Tree.
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
     * Logs a deletion from the Tree.
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
     * Redoes an action.
     */
    public void redo() {

        // Will redo if and only if there is something to be redone.
        if (logIndex < log.size()) {

            // Marks the log as unavailable.
            logAvailable = false;

            // Clears the DataStructure.
            clear();

            // Rebuilds the tree.
            ++logIndex;
            for (int i = 0; i < logIndex; ++i) log.get(i).action();

            // Renders the DataStructure.
            finalRender();

            // Marks the log as available.
            logAvailable = true;

        }
    }

    /**
     * Undoes the previous action.
     */
    public void undo() {

        // Marks the log as unavailable.
        logAvailable = false;

        // Clears the tree.
        clear();

        // Rebuilds the tree.
        logIndex = logIndex - 1 < 0 ? 0 : logIndex - 1;
        for (int i = 0; i < logIndex; ++i) log.get(i).action();

        // Renders the DataStructure.
        finalRender();

        // Marks the log as available.
        logAvailable = true;

    }

    /**
     * Performs all animations in the animation queue, then empties the queue.
     */
    public void animate() {
        for (AnimationItem item : animationLog) item.run();
        finalRender();

    }

    /**
     * Begins an animation in the DataStructure. Locks the animation mutex and
     * clears the AnimationLog.
     */
    protected void beginAnimation() {
        AnimationParameters.beginAnimation();
        animationLog.clear();

    }

    /**
     * Stops an animation in the DataStructure. Unlocks the animation mutex.
     */
    protected void stopAnimation() {
        AnimationParameters.stopAnimation();

    }

    /**
     * Renders the data structure and does a handful of other things. Should be overridden.
     */
    protected void finalRender() {}

    /**
     * Renders the data structure to the given Canvas. Should be overridden.
     */
    public void render(Canvas canvas) { MainActivity.getVisualizer().render(); }

    /**
     * Renders the data structure to the default Canvas.
     */
    public void render() {
        if(MainActivity.getVisualizer() != null)
            render(MainActivity.getVisualizer().getCanvas());

    }
}
