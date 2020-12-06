package com.example.datastructurevisualizer;

// https://github.com/tgvdinesh/binary-search-tree
// TODO this tree is distributed under the MIT license
// TODO implement MIT license requirements

/**
 * Binary search tree implementation
 * <a href="https://en.wikipedia.org/wiki/Binary_search_tree">Binary search tree</a>
 * Algorithm    Average     Worst Case
 * Space		O(n)        O(n)
 * Search		O(log n)	O(n)
 * Insert		O(log n)	O(n)
 * Delete		O(log n)	O(n)
 *
 * For this tree Node.key is the integer key.
 */
public class BinarySearchTree extends TreeVisualizer {
    private int maxWidth = 0;

    // Number of children per node in this tree.
    static final int numChildren = 2;

    public BinarySearchTree() {
        root = null;
    }

    /**
     * Return numChildren per node, which is 2. Used in TreeVisualize.
     */
    @Override
    public int getNumChildren() { return numChildren; }

    /**
     * Removes a Node from the tree without any animation.
     *
     * @param id the key to be removed.
     */
    @Override
    public void removeNoAnim(int id) {

        // Logs removal.
        logRemove(id);

        Node parent = root;
        Node current = root;

        //prevents null pointer when run on tree without nodes
        //not likely for this application, but good to have
        if(current == null){
            return;
        }

        boolean isLeftChild = false;
        while (current.key != id) {
            parent = current;
            if (current.key > id) {
                isLeftChild = true;
                current = current.children[ChildNames.LEFT.i];
            } else {
                isLeftChild = false;
                current = current.children[ChildNames.RIGHT.i];
            }
            if (current == null) {
                finalRender();
                return;
            }
        }
        //if i am here that means we have found the node
        //Case 1: if node to be deleted has no children
        if (current.children[ChildNames.LEFT.i] == null && current.children[ChildNames.RIGHT.i] == null) {
            if (current == root) {
                root = null;
            }
            if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = null;
            } else {
                parent.children[ChildNames.RIGHT.i] = null;
            }
        }
        //Case 2 : if node to be deleted has only one child
        else if (current.children[ChildNames.RIGHT.i] == null) {
            if (current == root) {
                root = current.children[ChildNames.LEFT.i];
            } else if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = current.children[ChildNames.LEFT.i];
            } else {
                parent.children[ChildNames.RIGHT.i] = current.children[ChildNames.LEFT.i];
            }
        } else if (current.children[ChildNames.LEFT.i] == null) {
            if (current == root) {
                root = current.children[ChildNames.RIGHT.i];
            } else if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = current.children[ChildNames.RIGHT.i];
            } else {
                parent.children[ChildNames.RIGHT.i] = current.children[ChildNames.RIGHT.i];
            }
        } else {

            //now we have found the minimum element in the right sub tree
            Node successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = successor;
            } else {
                parent.children[ChildNames.RIGHT.i] = successor;
            }
            successor.children[ChildNames.LEFT.i] = current.children[ChildNames.LEFT.i];
        }
        finalRender();
        return;
    }

    /**
     * Removes a Node from the tree. Will animate the traversal that finds the
     * Node to remove, as well as the removal of said Node.
     *
     * @param id the key to be removed.
     */
    @Override
    protected void removeAnim(int id) {

        // Logs removal.
        logRemove(id);

        Node parent = root;
        Node current = root;

        //prevents null pointer when run on tree without nodes
        //not likely for this application, but good to have
        if(current == null){
            return;
        }

        // Begins the traversal at the root.
        queueNodeSelectAnimation(current, "Start at root " + current.key,
                AnimationParameters.ANIM_TIME);

        boolean isLeftChild = false;
        while (current.key != id) {
            parent = current;
            if (current.key > id) {
                isLeftChild = true;
                current = current.children[ChildNames.LEFT.i];
                queueNodeSelectAnimation(current, id + " < " + parent.key +
                        ", exploring left subtree", AnimationParameters.ANIM_TIME);
            } else {
                isLeftChild = false;
                current = current.children[ChildNames.RIGHT.i];
                queueNodeSelectAnimation(current, id + " > " + parent.key +
                        ", exploring right subtree", AnimationParameters.ANIM_TIME);
            }
            if (current == null) {
                queueNodeSelectAnimation(null, "Current Node null, desired Node not found",
                        AnimationParameters.ANIM_TIME);
                return;
            }
        }

        // Desired Node has been found.
        queueNodeSelectAnimation(current, id + " == " + current.key +
                ", desired Node found", AnimationParameters.ANIM_TIME);

        //if i am here that means we have found the node
        //Case 1: if node to be deleted has no children
        if (current.children[ChildNames.LEFT.i] == null && current.children[ChildNames.RIGHT.i] == null) {
            if (current == root) {
                placeTreeNodes();
                queueNodeMoveAnimation("Delete childless root", 0);
                root = null;
            }
            if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = null;
                placeTreeNodes();
                queueNodeMoveAnimation("Delete childless Node", 0);
            } else {
                parent.children[ChildNames.RIGHT.i] = null;
                placeTreeNodes();
                queueNodeMoveAnimation("Delete childless Node", 0);
            }
        }
        //Case 2 : if node to be deleted has only one child
        else if (current.children[ChildNames.RIGHT.i] == null) {
            if (current == root) {
                root = current.children[ChildNames.LEFT.i];
                placeTreeNodes();
                queueNodeMoveAnimation("Delete root with only left child",
                        AnimationParameters.ANIM_TIME);
            } else if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = current.children[ChildNames.LEFT.i];
                placeTreeNodes();
                queueNodeMoveAnimation("Delete Node with only left child",
                        AnimationParameters.ANIM_TIME);
            } else {
                parent.children[ChildNames.RIGHT.i] = current.children[ChildNames.LEFT.i];
                placeTreeNodes();
                queueNodeMoveAnimation("Delete Node with only left child",
                        AnimationParameters.ANIM_TIME);
            }
        } else if (current.children[ChildNames.LEFT.i] == null) {
            if (current == root) {
                root = current.children[ChildNames.RIGHT.i];
                placeTreeNodes();
                queueNodeMoveAnimation("Delete root with only right child",
                        AnimationParameters.ANIM_TIME);
            } else if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = current.children[ChildNames.RIGHT.i];
                placeTreeNodes();
                queueNodeMoveAnimation("Delete Node with only right child",
                        AnimationParameters.ANIM_TIME);
            } else {
                parent.children[ChildNames.RIGHT.i] = current.children[ChildNames.RIGHT.i];
                placeTreeNodes();
                queueNodeMoveAnimation("Delete Node with only right child",
                        AnimationParameters.ANIM_TIME);
            }
        } else {

            //now we have found the minimum element in the right sub tree
            Node successor = getSuccessorAnim(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.children[ChildNames.LEFT.i] = successor;
            } else {
                parent.children[ChildNames.RIGHT.i] = successor;
            }
            successor.children[ChildNames.LEFT.i] = current.children[ChildNames.LEFT.i];
            placeTreeNodes();
            queueNodeMoveAnimation("Replace Node with successor",
                    AnimationParameters.ANIM_TIME);

        }
        return;
    }

    private Node getSuccessor(Node deleteNode) {
        Node successor = null;
        Node successorParent = null;
        Node current = deleteNode.children[ChildNames.RIGHT.i];
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.children[ChildNames.LEFT.i];
        }
        //check if successor has the right child, it cannot have left child for sure
        // if it does have the right child, add it to the left of successorParent.
        // successorParent
        if (successor != deleteNode.children[ChildNames.RIGHT.i]) {
            successorParent.children[ChildNames.LEFT.i] = successor.children[ChildNames.RIGHT.i];
            successor.children[ChildNames.RIGHT.i] = deleteNode.children[ChildNames.RIGHT.i];
        }
        return successor;
    }

    /**
     * Animates the traversal to find a successor.
     *
     * @param deleteNode the Node to delete.
     * @return the successor of deleteNode.
     */
    private Node getSuccessorAnim(Node deleteNode) {
        Node successor = null;
        Node successorParent = null;
        Node current = deleteNode.children[ChildNames.RIGHT.i];
        if (current != null) queueNodeSelectAnimation(current,
                "Finding successor from " + current.key,
                AnimationParameters.ANIM_TIME);
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.children[ChildNames.LEFT.i];
            if (current != null) queueNodeSelectAnimation(current,
                    "Searching left child " + current.key,
                    AnimationParameters.ANIM_TIME);
        }
        //check if successor has the right child, it cannot have left child for sure
        // if it does have the right child, add it to the left of successorParent.
        // successorParent
        if (successor != deleteNode.children[ChildNames.RIGHT.i]) {
            successorParent.children[ChildNames.LEFT.i] = successor.children[ChildNames.RIGHT.i];
            successor.children[ChildNames.RIGHT.i] = deleteNode.children[ChildNames.RIGHT.i];

        }
        return successor;
    }

    /**
     * Inserts a Node into the tree with no animation.
     *
     * @param id the ID of the Node to be inserted.
     */
    @Override
    public void insertNoAnim(int id) {

        // If there is a duplicate, returns without performing an action.
        if(!checkInsert(id)) return;
        // If there is no duplicate, logs the insertion.
        else logAdd(id);

        Node newNode = new Node(id, numChildren);
        if (root == null) {
            root = newNode;
            finalRender();
            return;
        }

        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (id < current.key) {
                current = current.children[ChildNames.LEFT.i];
                if (current == null) {
                    parent.children[ChildNames.LEFT.i] = newNode;
                    finalRender();
                    return;
                }
            } else {
                current = current.children[ChildNames.RIGHT.i];
                if (current == null) {
                    parent.children[ChildNames.RIGHT.i] = newNode;
                    finalRender();
                    return;
                }
            }
        }
    }

    /**
     * Inserts a Node into the tree. Will animate the traversal that finds the
     * appropriate place for the Node.
     *
     * @param id the key of the Node to be inserted.
     */
    @Override
    protected void insertAnim(int id) {

        // If there is a duplicate, returns without performing an action.
        if(!checkInsert(id)) return;
        // If there is no duplicate, logs the insertion.
        else logAdd(id);

        // Tracks the Node being inserted.
        queueListPopAnimation(null, 0);
        queueQueueAddAnimation(new Node(id, 0), "Inserting " + id, 0);

        Node newNode = new Node(id, numChildren);
        if (root == null) {
            root = newNode;
            placeTreeNodes();
            queueNodeMoveAnimation("Creating root",
                    AnimationParameters.ANIM_TIME);
            return;
        }

        Node current = root;
        queueNodeSelectAnimation(current, "Start at root " + current.key,
                    AnimationParameters.ANIM_TIME);

        Node parent;
        while (true) {
            parent = current;
            if (id < current.key) {
                current = current.children[ChildNames.LEFT.i];
                if (current == null) {
                    parent.children[ChildNames.LEFT.i] = newNode;
                    placeTreeNodes();
                    queueNodeMoveAnimation(id + " < " + parent.key + ", placing "
                            + id + " as left child", AnimationParameters.ANIM_TIME);
                    return;

                }
                else {
                    queueNodeSelectAnimation(current, id + " < " + parent.key +
                            ", exploring left subtree", AnimationParameters.ANIM_TIME);
                }
            } else {
                current = current.children[ChildNames.RIGHT.i];
                if (current == null) {
                    parent.children[ChildNames.RIGHT.i] = newNode;
                    placeTreeNodes();
                    queueNodeMoveAnimation(id + " > " + parent.key + ", placing "
                            + id + " as right child", AnimationParameters.ANIM_TIME);
                    return;

                }
                else {

                    queueNodeSelectAnimation(current, id + " > " + parent.key +
                            ", exploring right subtree", AnimationParameters.ANIM_TIME);
                }
            }
        }
    }
}