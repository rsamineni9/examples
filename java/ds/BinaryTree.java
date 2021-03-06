package ds;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jules on 1/19/16.
 * Borrowed and modified from http://www.newthinktank.com/2013/03/binary-tree-in-java/
 *
 */
public class BinaryTree {
    BNode root;
    int numOfNodes = 0;

    /**
     * Constructor
     */
    public BinaryTree() {
        root = null;
    }

    /**
     * increment the number of items in the tree
     */
    public void inc() { numOfNodes++;}

    /**
     * decrement the number of items in the tree
     */
    public void dec() { numOfNodes--;}

    /** getter
     *
     * @return number of nodes in the tree
     */
    public int getNumOfNodes() { return numOfNodes; }

    public BNode getRoot() { return root; }
    /**
     * Add node traversing the tree until you find the right place for insertion.
     * @param key
     * @param name
     */
    public void addNode(int key, String name) {
        // Create a new Node and initialize it
        BNode newNode = new BNode(key, name);
        // If there is no root this becomes root
        if (root == null) {
            root = newNode;
            inc();
        } else {
            // Set root as the Node we will start
            // with as we traverse the tree
            BNode focusNode = root;
            // Future parent for our new Node
            BNode parent;
            while (true) {
                // root is the top parent so we start
                // there
                parent = focusNode;
                // Check if the new node should go on
                // the left side of the parent node
                if (key < focusNode.getKey()) {
                    // Switch focus to the left child
                    focusNode = focusNode.getLeftChild();
                    // If the left child has no children
                    if (focusNode == null) {
                        // then place the new node on the left of it
                        parent.setLeftChild(newNode);
                        inc();
                        return; // All Done
                    }
                } else { // If we get here put the node on the right
                    focusNode = focusNode.getRightChild();
                    // If the right child has no children
                    if (focusNode == null) {
                        // then place the new node on the right of it
                        parent.setRightChild(newNode);
                        inc();
                        return; // All Done
                    }
                }
            }
        }
    }

    /**
     * Traverse in the inorder. This is the sorted order.
     * 1. Go down the left's nodes' children recursively until no children
     * 2. Print Node's key
     * 3. Go down the right nodes' children recursively until no children
     * Recursion is used to go to one node and
     * then go to its child nodes and so forth
     * @param focusNode, the root node sent to it
     */
    public void inOrderTraverseTree(BNode focusNode) {
        if (focusNode != null) {
            // Traverse the left node's children recursively
            inOrderTraverseTree(focusNode.getLeftChild());
            // Visit the currently focused on node
            System.out.println(focusNode);
            // Traverse the right node
            inOrderTraverseTree(focusNode.getRightChild());
        }
    }

    /**
     * Travere in the preorder
     * 1. Print Node's key
     * 2. Go down the left's nodes' children recursively until no children
     * 3. Go down the right nodes' children recursively until no children
     * @param focusNode
     */
    public void preorderTraverseTree(BNode focusNode) {
        if (focusNode != null) {
            System.out.println(focusNode);
            preorderTraverseTree(focusNode.getLeftChild());
            preorderTraverseTree(focusNode.getRightChild());
        }
    }

    /**
     * traverse postorder
     * 1. Go down the left's nodes' children recursively until no children
     * 2. Go down the right nodes' children recursively until no children
     * 3. Print Node's key
     * @param focusNode
     * @param focusNode
     */
    public void postOrderTraverseTree(BNode focusNode) {
        if (focusNode != null) {
            postOrderTraverseTree(focusNode.getLeftChild());
            postOrderTraverseTree(focusNode.getRightChild());
            System.out.println(focusNode);
        }
    }

    /**
     * Find the height of the tree or given node. The height of a root or a given node is the longest path with number of edges from itself
     * to the leaf node. For example, the height of leaf node is 0, the height of root node with one child is 1, and with only one node,
     * itself, would be 0.
     * @param root
     * @return
     */
    public int findHeight(BNode root) {
        if (root == null) {
            return -1;
        } else
            return Math.max(findHeight(root.getLeftChild()), findHeight(root.getRightChild())) + 1;
    }
    /**
     * Using recursion, find Mininum value in the tree. Since all values less than root will be in the left subtree, recurse till we find
     * the leaf nodes containing least value.
     * @param root
     * @return value or -1 if tree is empty
     */
    public int findMinValue(BNode root) {
        if (root == null) {
            return -1;
        } else if (root.getLeftChild() == null) {
            return root.getKey();
        } else {
            return findMinValue(root.getLeftChild());
        }
    }

    /**
     * Using recursion, find the Maximum value in the tree. Since all values greater than root will be in the right subtree, recurse until we
     * find the leaf node containing that value.
     * @param root
     * @return
     */
    public int findMaxValue(BNode root) {
        if (root == null) {
            return -1;
        } else if (root.getRightChild() == null) {
            return root.getKey();
        } else {
            return findMaxValue(root.getRightChild());
        }
    }

    /**
     * Find if the key exists in the tree. This is 0(N/k), where k is the heigth of the tree.
     * @param key
     * @return return Bnode if found otherwise null
     */
    public BNode findNode(int key) {
        // Start at the top of the tree
        BNode focusNode = root;
        // check if empty tree
        if (root == null) {
            return null;
        }
        // While we haven't found the Node
        // keep looking
        while ( focusNode.getKey() != key)  {
            // If we should search to the left
            if (key < focusNode.getKey()) {
                // Shift the focus Node to the left child
                focusNode = focusNode.getLeftChild();
            } else {
                // Shift the focus Node to the right child
                focusNode = focusNode.getRightChild();
            }
            // The node wasn't found
            if (focusNode == null)
                return null;
        }
        return focusNode;
    }

    /**
     * Given a key, locate the appropriate node to replace and do all the magic of reassigning and replacing the right
     * and letf child nodes.
     * This is the iterative version of delete. Check the recursive version below, much elegant thanks to
     * https://gist.githubusercontent.com/mycodeschool/9465a188248b624afdbf/raw/ff5842220d84629535509adec5dda82af13c16a7/BSTDeletion_CPP.cpp
     * @param key
     * @return true if deleted, false otherwise
     */
    public boolean remove(int key) {
        // Start at the top of the tree
        BNode focusNode = root;
        BNode parent = root;

        // special case if the tree is empty
        if (focusNode == null) {
            return false;
        }
        // When searching for a Node this will
        // tell us whether to search to the
        // right or left
        boolean isItALeftChild = true;
        // While we haven't found the Node
        // keep looking
        while (focusNode.getKey() != key) {
            parent = focusNode;
            // If we should search to the left
            if (key < focusNode.getKey()) {
                isItALeftChild = true;
                // Shift the focus Node to the left child
                focusNode = focusNode.getLeftChild();
            } else {
                // Greater than focus node so go to the right
                isItALeftChild = false;
                // Shift the focus Node to the right child
                focusNode = focusNode.getRightChild();
            }
            // The node wasn't found
            if (focusNode == null)
                return false;
        }
        // If Node doesn't have children delete it
        if (focusNode.getLeftChild() == null && focusNode.getRightChild() == null) {
            // If root delete it
            if (focusNode == root)
                root = null;
                // If it was marked as a left child
                // of the parent delete it in its parent
            else if (isItALeftChild)
                parent.setLeftChild(null);
                // Vice versa for the right child
            else
                parent.setRightChild(null);
        }
        // If no right child
        else if (focusNode.getRightChild() == null) {
            if (focusNode == root)
                root = focusNode.getLeftChild();
                // If focus Node was on the left of parent
                // move the focus Nodes left child up to the
                // parent node
            else if (isItALeftChild)
                parent.setLeftChild(focusNode.getLeftChild());
                // Vice versa for the right child
            else
                parent.setRightChild(focusNode.getLeftChild());
        }
        // If no left child
        else if (focusNode.getLeftChild() == null) {
            if (focusNode == root)
                root = focusNode.getRightChild();
                // If focus Node was on the left of parent
                // move the focus Nodes right child up to the
                // parent node
            else if (isItALeftChild)
                parent.setLeftChild(focusNode.getRightChild());
                // Vice versa for the left child
            else
                parent.setRightChild(focusNode.getRightChild());

        }
        // Two children so I need to find the targeted deleted nodes'
        // replacement
        else {
            BNode replacement = getReplacementNode(focusNode);
            // If the focusNode is root replace root
            // with the replacement
            if (focusNode == root)
                root = replacement;
                // If the deleted node was a left child
                // make the replacement the left child
            else if (isItALeftChild)
                parent.setLeftChild(replacement);
                // Vice versa if it was a right child
            else
                parent.setRightChild(replacement);
            replacement.setLeftChild(focusNode.getLeftChild());
        }
        dec();
        return true;
    }

    /**
     * Find the replacement node. We have identified the node to delete, find which node in the tree
     * should replace this targeted node (to be deleted)
     * @param replacedNode
     * @return BNode or null;
     */
    public BNode getReplacementNode(BNode replacedNode) {
        BNode replacementParent = replacedNode;
        BNode replacement = replacedNode;

        BNode focusNode = replacedNode.getRightChild();
        // While there are no more left children
        while (focusNode != null) {
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.getLeftChild();
        }
        // If the replacement isn't the right child
        // move the replacement into the parent's
        // leftChild slot and move the replaced nodes
        // right child into the replacements rightChild
        if (replacement != replacedNode.getRightChild()) {
            replacementParent.setLeftChild(replacement.getRightChild());
            replacement.setRightChild(replacedNode.getRightChild());
        }
        return replacement;
    }


    /**
     * Given root of the tree or subtree, find its minimum. Note that Btree's left subtree leaf node will
     * always have the smallest value, whereas the right most subtree will have have the largest value.
     * @param root
     * @return
     */
    public BNode findMin(BNode root)
    {
        BNode focusNode = root;
        while (focusNode.getLeftChild() != null) {
            focusNode = focusNode.getLeftChild();
        }
        return focusNode;
    }

    /**
     * Recursive version of the deletion.
     * @param root
     * @param data
     * @return the deleted node
     */
    public BNode delete(BNode root, int data) {
        if (root == null)  {
            return root;
        } else if(data < root.getKey()) {
            // traverse recursively the left side
            root.setLeftChild(delete(root.getLeftChild(), data));
            // travers recursively the right side
        } else if (data > root.getKey())  {
            root.setRightChild(delete(root.getRightChild(), data));
        } else {
            // Wohoo... I found you, Get ready to be deleted
            // check the three cases
            // Case 1:  No child, the easy case
            if(root.getLeftChild() == null && root.getRightChild() == null) {
                root = null;
            }
            //Case 2: One child
            else if(root.getLeftChild() == null) {
                BNode temp = root;
                root = root.getRightChild();
            } else if(root.getRightChild() == null) {
                BNode temp = root;
                root = root.getLeftChild();
            }
            // case 3: 2 children
            else {
                BNode temp = findMin(root.getRightChild());
                root.setKey(temp.getKey());
                root.setRightChild(delete(root.getRightChild(), temp.getKey()));
            }
        }
        return root;
    }

    /**
     * Check if this tree an a binary tree
     * @param root
     * @return true or false
     */
    boolean isBinarySearchTree(BNode root) {
        int rootKey = root.getKey();
        if (root == null) {
            return true;
        } else if (findMinValue(root.getLeftChild()) < rootKey && findMaxValue(root.getRightChild()) > rootKey) {
            return true;
        } else return false;
    }
    /**
     * Driver for the program
     * @param args
     */
    public static void main(String[] args) {
        BinaryTree theTree = new BinaryTree();
        //generate random numbers so we don't get an unbalanced tree
        int maxKeys = 4;
        for (int i = 0 ; i < maxKeys; i++) {
            int key = ThreadLocalRandom.current().nextInt(1, 85);
            //only insert if no key already exits
            if (theTree.findNode(key) == null) {
                theTree.addNode(key, Integer.toString(key));
            } else {
                // got a match don't count that key.
                System.out.println("Key "+ key + " in already in the tree. Not added");
                maxKeys++;
            }
        }
        // Different ways to traverse binary trees
        System.out.println("In-order Traversal: ");
        System.out.println("Total Number of Nodes:" + theTree.getNumOfNodes());
        System.out.println("Root key: "+ theTree.getRoot().getKey());
        System.out.println("Max Value: " + theTree.findMaxValue(theTree.getRoot()));
        System.out.println("Min Value: " + theTree.findMinValue(theTree.getRoot()));
        System.out.println("Binary Tree Height: " + theTree.findHeight(theTree.getRoot()));
        System.out.println("Is Binary Search Tree: " + theTree.isBinarySearchTree(theTree.getRoot()));
        theTree.inOrderTraverseTree(theTree.root);
        System.out.println("\nPre-order Traversal: ");
        theTree.preorderTraverseTree(theTree.root);
        System.out.println("\nPost-order Traversal: ");
        theTree.postOrderTraverseTree(theTree.root);
        // Add and Find the node with key 75
        theTree.addNode(75, "75");
        System.out.println("\nNode with the key 75");
        System.out.println(theTree.findNode(75));
        //remove root
        System.out.println("Removing the root key: "+ theTree.getRoot().getKey());
        theTree.delete(theTree.getRoot(), theTree.getRoot().getKey());
        theTree.dec();
        System.out.println("In-order Traversal: ");
        System.out.println("Root key: "+ theTree.getRoot().getKey());
        theTree.inOrderTraverseTree(theTree.root);
        theTree.remove(75);
        System.out.println("In-order Traversal: ");
        theTree.inOrderTraverseTree(theTree.root);

        for (int i = 0 ; i < maxKeys; i++) {
            int key = ThreadLocalRandom.current().nextInt(1, 85);
            //only delete if key  exits
            if (theTree.findNode(key) != null) {
                BNode del = theTree.delete(theTree.getRoot(), key);
                if (del != null) {
                    theTree.dec();
                    System.out.println("Key " + del.getKey() + " Deleted!.");
                }
            } else {
                // got a match don't count that key.
                System.out.println("Key "+ key + " Not found.");
            }
        }
        System.out.println("In-order Traversal: ");
        System.out.println("Total Number of Nodes:" + theTree.getNumOfNodes());
        System.out.println("Root key: "+ theTree.getRoot().getKey());
        System.out.println("Max Value:" + theTree.findMaxValue(theTree.getRoot()));
        System.out.println("Min Value:" + theTree.findMinValue(theTree.getRoot()));
        System.out.println("Binary Tree Height: " + theTree.findHeight(theTree.getRoot()));
        theTree.inOrderTraverseTree(theTree.root);

        int[] array = {49, 46, 41, 48, 79};
        BinaryTree tree2 = new BinaryTree();
        for (int i = 0; i < array.length; i++) {
            tree2.addNode(array[i], Integer.toString(array[i]));
        }
        System.out.println("New Tree:");
        System.out.println("In-order Traversal: ");
        tree2.inOrderTraverseTree(tree2.getRoot());
        System.out.println("\nPre-order Traversal: ");
        tree2.preorderTraverseTree(tree2.getRoot());
        System.out.println("\nPost-order Traversal: ");
        tree2.postOrderTraverseTree(tree2.root);

    }
}
