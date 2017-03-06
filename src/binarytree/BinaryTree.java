/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Thomas
 */
public class BinaryTree<T extends Comparable> {
    private BinaryNode root;
    private int size;
    
    public BinaryTree() {
        size = 0;
    }
    
    public void add(int n) {
        add(new BinaryNode(new BinaryValue(n)));
    }
    
    public void add(BinaryNode n) {
        if(size() == 0) {
            root = n;
        } else {
            FindAppendPoint(root, (T) n.val()).addChild(n);
        }
        size++;
    }
    
    public int size() {
        return size;
    }
    
    /**
     * Function to engage a search for a node with given content
     * @param content   Content to search for
     * @return          The node that was found
     */
    public BinaryNode Search(T content) {
        return Find(root, content);
    }
    
    /**
     * Search algorithm to identify node with a given content starting from the given node
     * @param node      The node to start searching from
     * @param content   The content to search for
     * @return          The node that was found
     */
    public BinaryNode Find(BinaryNode node, T content) {
        BinaryNode left = node.leftChild();
        BinaryNode right = node.rightChild();
        
        // If the values match, we have found the first node containing this value, returning
        if(node.val().compareTo(content) == 0) {
            return node;
        }
        
        // If the node has a right child, check to see if the node's content is greater than in value compared to the right child
        // And from there continue to search
        if(right != null) {
            if(node.val().compareTo(content) < 0) {
                return Find(right, content);
            }
        }
        
        // If the node has a right child, check to see if the node's content is less than in value compared to the left child
        // And from there continue to search
        if(left != null) {
            if(node.val().compareTo(content) > 0) {
                return Find(left, content);
            }
        }
        
        // If none of these cases match, return null. 
        // This means that the node we are searching for does not exist in the tree
        return null;
    }
    
    /**
     * Function to find the point at which we should insert a node with a given value
     * Similar to the search algorithm, but it returns the last occurance of the content value
     * if it exists, or the closest value to append on
     * @param node      The node to begin search from
     * @param content   The content to search for
     * @return          The node that was found
     */
    public BinaryNode FindAppendPoint(BinaryNode node, T content) {
        if(node.leftChild() != null && node.val().compareTo(content) > 0) {
            // Search from left tree
            return FindAppendPoint(node.leftChild(), content);
        } else if(node.rightChild() != null && node.val().compareTo(content) <= 0) {
            // Seach from right tree
            return FindAppendPoint(node.rightChild(), content);
        } else {
            return node;
        }
    }
    
    /**
     * Traversing the tree pre-order starting from a given node, and performs a callback function at each step
     * @param node  Start node
     * @param cb    Callback function
     */
    public void TraversePreOrder(BinaryNode node, TraverseCallback cb) {
        // If the current node is null, there is no node to do operations on, return
        if(node == null || cb == null)
            return;
        
        // Run callback function
        cb.run(node);
        
        // Continue traversing
        if(node.leftChild() != null) {
            TraversePreOrder(node.leftChild(), cb);
        }
        
        if(node.rightChild() != null) {
            TraversePreOrder(node.rightChild(), cb);
        }
    }
    
    /**
     * Alias function to start traversing from root
     * @param cb    Callback function
     */
    public void TraversePreOrder(TraverseCallback cb) {
        TraversePreOrder(root, cb);
    }
    
    /**
     * Traverse the tree post order starting from a given node and perform a callback function at each step
     * @param node  The node to start from
     * @param cb    Callback function
     */
    public void TraversePostOrder(BinaryNode node, TraverseCallback cb) {
        if(node.leftChild() != null) {
            TraversePostOrder(node.leftChild(), cb);
        }
        
        if(node.rightChild() != null) {
            TraversePostOrder(node.rightChild(), cb);
        }
        cb.run(node);
    }
    
    /**
     * Alias function to traverse post order from root
     * @param cb    Callback function
     */
    public void TraversePostOrder(TraverseCallback cb) {
        TraversePostOrder(root, cb);
    }
    
    /**
     * Traverse tree in order from a given node, performing a callback function at each step
     * @param node  The node to start from
     * @param cb    Callback function
     */
    public void TraverseInOrder(BinaryNode node, TraverseCallback cb) {
        if(node.leftChild() != null) {
            TraverseInOrder(node.leftChild(), cb);
        }
        cb.run(node);
        
        if(node.rightChild() != null) {
            TraverseInOrder(node.rightChild(), cb);
        }
    }
    
    /**
     * Alias function to start traversing from root
     * @param cb    Callback function
     */
    public void TraverseInOrder(TraverseCallback cb) {
        TraverseInOrder(root, cb);
    }
    
    /**
     * Returns the root node of the tree
     * @return  The root node of the Binary Tree
     */
    public BinaryNode root() {
        return root;
    }
    
    /**
     * Shortcut function to remove a node with a given int value
     * @param v     The integer value to remove
     */
    public void Remove(int v) {
        BinaryNode<T> valNode = new BinaryNode(new BinaryValue(v));
        Remove(valNode.val());
    }
    
    /**
     * Remove a node with a given value
     * @param v     The value to remove
     */
    public void Remove(T v) {
        BinaryNode<T> n = Find(root, v);
        
        // Could not find node with this value
        if(n == null) {
            System.out.println("Cannot find node with value " + v);
            return;
        }
        BinaryNode p = n.getParent();
        
        // If the node we are removing does not have a parent node, we are removint the
        // root node, and need to handle this special case
        if(p == null) {
            System.out.println("Attempting to remove parent node, special case!");
            // New root node
            BinaryNode newRoot = n.rightChild();
            
            if(newRoot == null) {
                // There are no more nodes on the right side, so the new root simply becomes the left child
                if(n.leftChild() != null) {
                    root = n.leftChild();
                    root.setParent(null);
                } else // If there are no children at all, we have removed all nodes
                    root = null;
                return;
            }
            
            // Tree is empty
            if(newRoot == null) {
                System.out.println("No more nodes");
                n.removeChild(BinaryNode.ChildType.LEFT);
                n.removeChild(BinaryNode.ChildType.RIGHT);
                return;
            }
            
            BinaryNode leftChild = n.leftChild();
            
            // If there is a left child
            if(leftChild != null) {
                // Where to put the left child?
                // As far down the left tree from the new root as possible
                BinaryNode lowestPoint = findLowestValue(newRoot);

                // Attach the old roots left child to the lowest point in the tree
                leftChild.setParent(lowestPoint);
                lowestPoint.addChild(leftChild, BinaryNode.ChildType.LEFT);
            }
            // Register the new root node
            newRoot.setParent(null);
            root = newRoot;
        } else {
            System.out.println("----------- DELETE OPERATION -------------\nTHERE IS A BUG HERE!");
            // 0. Identidy parent node
            // 1. Identify the node to delete
            // 2. Find the node that should take its place (right child) - Not always the right child?
            // 3. Identify deleted node's left child
            // 4. Move the left child as far down to the left of node in pt2 as possible
            BinaryNode parent_node = p;
            BinaryNode node_to_delete = n;
            BinaryNode replacement_node = node_to_delete.rightChild();
            BinaryNode child_to_move = node_to_delete.leftChild();

            System.out.println("Identified node to delete with value " + v);
            System.out.println("Identified parent node with value " + p.val());
            
            // Remove the node's reference from its parent
            p.removeChild(node_to_delete);
            
            // If we find a replacement node, meaning there are more nodes after this
            if(replacement_node != null) {
                System.out.println("Identified replacement node with value " + replacement_node.val());
                int side = 0;
                
                // What side to put the replacement on?
                if(replacement_node.val().compareTo(parent_node.val()) > 0) {
                    side = BinaryNode.ChildType.RIGHT;
                } else {
                    side = BinaryNode.ChildType.LEFT;
                }
                parent_node.addChild(replacement_node, side);
                replacement_node.setParent(parent_node);
            } else {
                // If there are no nodes to the right of the one we are removing, the parent has to be the new root from this point on
                replacement_node = p;
            }
            
            // If the node had left children, we need to move them to the lowest point in the tree
            if(child_to_move != null) {
                // Something else should happen if the parent is the root node
                // If the parent is the root node, we need to check if the replacement note is greater than or less than the parent node
                if(false && parent_node == root && child_to_move.val().compareTo(parent_node.val()) > 0) {
                    // In this case, add the child as right child of the root
                    parent_node.addChild(child_to_move, BinaryNode.ChildType.RIGHT);
                } else {
                    System.out.println("Identified node to move with value " + child_to_move.val());
                    
                    if(child_to_move.val().compareTo(replacement_node.val()) > 0) { // And there is no right child?
                        // Add on right side
                        System.out.println("Should add to right side...?");
                        replacement_node.addChild(child_to_move, BinaryNode.ChildType.RIGHT);
                        child_to_move.setParent(replacement_node);
                    } else {
                        BinaryNode lowestPoint = findLowestValue(replacement_node);
                        child_to_move.setParent(lowestPoint);
                        lowestPoint.addChild(child_to_move, BinaryNode.ChildType.LEFT);
                    }
                }
            } else {
                System.out.println("Child to move is null");
            }
        }
    }
    
    /**
     * Returns the node with the lowest value from a given point in the tree
     * @param n     The node to start from
     * @return      The node that contains the lowest value
     */
    public BinaryNode findLowestValue(BinaryNode n) {
        if(n.leftChild() == null)
            return n;
        
        return findLowestValue(n.leftChild());
    }
}

/**
 * A functional interface to describes the callback function to run while traversing the tree
 * @author Thomas
 */
@FunctionalInterface
interface TraverseCallback {
    public void run(BinaryNode node);
}
