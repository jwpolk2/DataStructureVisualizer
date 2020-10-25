package com.example.datastructurevisualizer;

// https://github.com/tgvdinesh/binary-search-tree
// TODO this tree is distributed under the MIT license
// TODO implement MIT license requirements
// TODO note that for this tree key is the integer key

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
public class BinarySearchTree extends TreeVisualize {
    private int maxWidth = 0;
    private List<Integer> treeData;

    // Number of children per node in this tree.
    static final int numChildren = 2;

    public BinarySearchTree() {
        root = null;
        treeData = new LinkedList<>();
    }

    /**
     * Return numChildren, which is 2. Used in TreeVisualize.
     */
    @Override
    int getNumChildren() { return numChildren; }

    List<Integer> getTreeData() {
        return treeData;
    }

    public void setTreeData(List<Integer> treeData) {
        this.treeData = treeData;
    }

    Node getRoot() {
        return root;
    }

    void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Height of tree	-	The height of a tree is the height of its root node.
     *
     * @param root Tree from root
     * @return Returns tree height from root to it's longest edge path to leaf node
     */
    int treeHeight(Node root) {
        if (root == null) return 0;
        return (1 + Math.max(treeHeight(root.children[ChildNames.LEFT.i]),
                treeHeight(root.children[ChildNames.RIGHT.i])));

    }

    /**
     * If difference between left and right height of tree at any given branch is greater than 1
     * then the tree is unbalanced at which case, false will be returned
     * <a href="http://algorithms.tutorialhorizon.com/find-whether-if-a-given-binary-tree-is-balanced/">
     * Find whether if a Given Binary Tree is Balanced?</a>
     *
     * @param node Pass root node
     * @return Returns true if balance and false if not balances
     */
    boolean isBalancedNaive(Node node) {
        if (root == null) return true;
        int heightDifference = treeHeight(node.children[ChildNames.LEFT.i]) - treeHeight(node.children[ChildNames.RIGHT.i]);
        return Math.abs(heightDifference) <= 1 && isBalancedNaive(node.children[ChildNames.LEFT.i]) && isBalancedNaive(node.children[ChildNames.RIGHT.i]);
    }

    private int getNodeHeight(Node root, Node node, int height) {
        if (root == null) return 0;
        if (root == node) return height;

        //Check if node is present in the left sub tree
        int level = getNodeHeight(root.children[ChildNames.LEFT.i], node, height + 1);
        /*System.out.println("Left : " + level + " , height : " + height);*/
        if (level != 0) return level;

        //Check if node is present in the right sub tree
        level = getNodeHeight(root.children[ChildNames.RIGHT.i], node, height + 1);
        /*System.out.println("Right : " + level + " , height : " + height);*/
        return level;
    }

    /**
     * Height of node	-	The height of a node is the number of edges on the longest path between that node and a leaf.
     * <p>
     * Finding the level of the node. The node passes along with root must have all the predecessors
     * i.e all of children as in main tree. So it is better to get the node when inserting into tree
     * <a href="http://algorithms.tutorialhorizon.com/get-the-height-of-a-node-in-a-binary-tree/">Get height of node in binary tree</a>
     *
     * @param root Root element
     * @param node Node object starting from root till node
     * @return Returns height of the node
     */
    int getNodeHeight(Node root, Node node) {
        if (root == null) return 0;
        return getNodeHeight(root, node, 1);
    }

    /**
     * Breadth first Traversal aka Level order traversal
     * <a href="http://algorithms.tutorialhorizon.com/breadth-first-searchtraversal-in-a-binary-tree/">
     * Breadth-First Search/Traversal in a Binary Tree</a>
     * <a href="https://youtu.be/AmG20guDrPw">Level Order Traversal</a>
     *
     * TODO remove?
     *
     * @param root Root element
     */
    private void breadthFirstTraversal(Node root) {
        Queue<Node> q = new LinkedList<>();
        int levelNodes;
        if (root == null)
            return;
        q.add(root);
        while (!q.isEmpty()) {
            levelNodes = q.size();
            if (maxWidth < levelNodes) {
                maxWidth = levelNodes;
            }
            Node n = q.remove();
            if (n.children[ChildNames.LEFT.i] != null) {
                q.add(n.children[ChildNames.LEFT.i]);
                System.out.println("");
            }
            if (n.children[ChildNames.RIGHT.i] != null)
                q.add(n.children[ChildNames.RIGHT.i]);
            --levelNodes;
        }
    }

    /**
     * Is the given tree isomorphic?
     * <a href="http://www.geeksforgeeks.org/tree-isomorphism-problem/">Is given tree isomorphic</a>
     * <a href="http://stackoverflow.com/a/742698/4541133">What is Isomorphic property of a tree?</a>
     *
     * @return true if isomorphic false otherwise
     */
    boolean isIsomorphic() {
        return isIsomorphic(getRoot().children[ChildNames.LEFT.i], getRoot().children[ChildNames.RIGHT.i]);
    }

    /**
     * Checks whether the key being inserted is a duplicate
     *
     * @param key the value being inserted
     * @return true if key is not a duplicate, false if it is a duplicate
     */


    /**
     * <a href="http://www.geeksforgeeks.org/tree-isomorphism-problem/">Is given tree isomorphic</a>
     *
     * @param n1 Left node of root or vice versa
     * @param n2 Right node of root or vice versa
     * @return true if isomorphic false otherwise
     */
    boolean isIsomorphic(Node n1, Node n2) {
        // Both roots are NULL, trees isomorphic by definition
        if (n1 == null && n2 == null)
            return true;

        // Exactly one of the n1 and n2 is NULL, trees not isomorphic
        if (n1 == null || n2 == null)
            return false;

        if (n1.key != n2.key)
            return false;

        // There are two possible cases for n1 and n2 to be isomorphic
        // Case 1: The subtrees rooted at these nodes have NOT been
        // "Flipped".
        // Both of these subtrees have to be isomorphic.
        // Case 2: The subtrees rooted at these nodes have been "Flipped"
        return (isIsomorphic(n1.children[ChildNames.LEFT.i], n2.children[ChildNames.LEFT.i]) &&
                isIsomorphic(n1.children[ChildNames.RIGHT.i], n2.children[ChildNames.RIGHT.i]))
                || (isIsomorphic(n1.children[ChildNames.LEFT.i], n2.children[ChildNames.RIGHT.i]) &&
                isIsomorphic(n1.children[ChildNames.RIGHT.i], n2.children[ChildNames.LEFT.i]));
    }

    boolean isSymmetric(Node root) {
        return isSymmetric(root.children[ChildNames.LEFT.i], root.children[ChildNames.RIGHT.i]);
    }

    boolean isSymmetric(Node left, Node right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        return isSymmetric(left.children[ChildNames.LEFT.i], right.children[ChildNames.RIGHT.i]) &&
                isSymmetric(left.children[ChildNames.RIGHT.i], right.children[ChildNames.LEFT.i]);
    }

    /**
     * Checks if two Binary Trees are equal.
     * Source: <a href="https://www.youtube.com/watch?v=A8oZEXtVB_Q">Check If Two Binary Trees Are Equal - Phyley CS</a>
     *
     * @param firstTree
     * @param secondTree
     * @return true if binary trees are equal else return false.
     */
    boolean isEqual(Node firstTree, Node secondTree) {
        if (firstTree == null && secondTree == null) return true;
        if (firstTree == null || secondTree == null) return false;
        if (firstTree.key != secondTree.key) return false;
        return isEqual(firstTree.children[ChildNames.LEFT.i], secondTree.children[ChildNames.LEFT.i]) &&
                isEqual(firstTree.children[ChildNames.RIGHT.i], secondTree.children[ChildNames.RIGHT.i]);
    }

    /**
     * <a href="https://stackoverflow.com/questions/9460255/reverse-a-binary-tree-left-to-right">Reverse a Binary Tree</a>
     *
     * @param root Reference to the root node of Binary Tree
     */
    void reverse(Node root) {
        Node temp = root.children[ChildNames.RIGHT.i];
        root.children[ChildNames.RIGHT.i] = root.children[ChildNames.LEFT.i];
        root.children[ChildNames.LEFT.i] = temp;
        if (root.children[ChildNames.LEFT.i] != null) reverse(root.children[ChildNames.LEFT.i]);
        if (root.children[ChildNames.RIGHT.i] != null) reverse(root.children[ChildNames.RIGHT.i]);
    }

    int getMaxWidth() {
        breadthFirstTraversal(root);
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Find if data is present in a tree
     *
     * @param id Data to be searched for in tree
     * @return Returns true if data is present else will return false
     */
    boolean find(int id) {
        Node current = root;
        while (current != null) {
            if (current.key == id) {
                return true;
            } else if (current.key > id) {
                current = current.children[ChildNames.LEFT.i];
            } else {
                current = current.children[ChildNames.RIGHT.i];
            }
        }
        return false;
    }

    /**
     * TODO comment
     * TODO animate
     *
     * @param id the key for the new Node.
     */
    protected void removeAnim(int id) {
        Node parent = root;
        Node current = root;
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

    // TODO remove
    /*Node insertWithArray(int... tree) {
        Node fullTree = null;
        for (int i :
                tree) {
            fullTree = insertNoAnim(i);
        }
        return fullTree;
    }*/

    /**
     * This is not balanced binary search tree insert implementation
     * TODO remove
     *
     * @param id
     * @return
     */
    @Override
    public void insertNoAnim(int id) {
        Node newNode = new Node(id, numChildren);
        if (root == null) {
            root = newNode;
            quickRender();
            return;
        }

        //first check if there is a duplicate before inserting
        if(!checkInsert(id)){
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
                    quickRender();
                    return;
                }
            } else {
                current = current.children[ChildNames.RIGHT.i];
                if (current == null) {
                    parent.children[ChildNames.RIGHT.i] = newNode;
                    quickRender();
                    return;
                }
            }
        }
    }

    /**
     * Inserts a Node into the tree. Will animate the traversal that finds the
     * appropriate place for the Node.
     *
     * @param id the ID of the node to insert.
     */
    @Override
    protected void insertAnim(int id) {
        Node newNode = new Node(id, numChildren);
        if (root == null) {
            root = newNode;
            return;
        }
        //first check if there is a duplicate before inserting
        if(!checkInsert(id)){
            return;
        }

        Node current = root;
        nodeSelectAnimation(current);
        Node parent;
        while (true) {
            parent = current;
            if (id < current.key) {
                current = current.children[ChildNames.LEFT.i];
                if (current == null) {
                    parent.children[ChildNames.LEFT.i] = newNode;
                    return;

                }
                else nodeSelectAnimation(current);
            } else {
                current = current.children[ChildNames.RIGHT.i];
                if (current == null) {
                    parent.children[ChildNames.RIGHT.i] = newNode;
                    return;

                }
                else nodeSelectAnimation(current);
            }
        }
    }
}