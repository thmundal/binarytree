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
            Find(root, (T) n.val()).addChild(n);
            System.out.println("DET ER EN BUG HER I ADD, FIKS DETTE FØR DU LEVERER!");
            //BinaryNode _n = Find(root, (T) n.val(), true);
            //_n.addChild(n);
            //System.out.println("Adding " + n.val() + " after Node val:" + _n.val());
        }
        size++;
    }
    
    public int size() {
        return size;
    }
    
    /**
     * Funksjon som setter igang et søk etter en node gjennom dens innhold
     * @param content
     * @return 
     */
    public BinaryNode Search(T content) {
        return Find(root, content);
    }
    
    
    public BinaryNode Find(BinaryNode node, T content) {
        BinaryNode left = node.leftChild();
        BinaryNode right = node.rightChild();
        
        if(right != null) {
            if(node.val().compareTo(content) < 0) {
                return Find(right, content);
            }
        }
        
        if(left != null) {
            if(node.val().compareTo(content) > 0) {
                return Find(left, content);
            }
        }
        //System.out.println("Inspecting " + node.val() + (left!=null?" leftchild is " + left.val():""));
        return node;
    }
    /*
    public BinaryNode Find(BinaryNode node, T content) {
        if(node.val().compareTo(content) == 0) {
            return node;
        } else if(node.leftChild() != null && node.val().compareTo(content) > 0) {
            // Search from right tree
            return Find(node.leftChild(), content);
        } else if(node.rightChild() != null && node.val().compareTo(content) < 0) {
            // Seach from left tree
            return Find(node.rightChild(), content);
        } else {
            return node;
        }
    }*/
    
    
    public void TraversePreOrder(BinaryNode node, TraverseCallback cb) {
        cb.run(node);
        
        if(node.leftChild() != null) {
            TraversePreOrder(node.leftChild(), cb);
        }
        
        if(node.rightChild() != null) {
            TraversePreOrder(node.rightChild(), cb);
        }
    }
    
    public void TraversePreOrder(TraverseCallback cb) {
        TraversePreOrder(root, cb);
    }
    
    public void TraversePostOrder(BinaryNode node, TraverseCallback cb) {
        if(node.leftChild() != null) {
            TraversePostOrder(node.leftChild(), cb);
        }
        
        if(node.rightChild() != null) {
            TraversePostOrder(node.rightChild(), cb);
        }
        cb.run(node);
    }
    
    public void TraversePostOrder(TraverseCallback cb) {
        TraversePostOrder(root, cb);
    }
    
    public void TraverseInOrder(BinaryNode node, TraverseCallback cb) {
        if(node.leftChild() != null) {
            TraverseInOrder(node.leftChild(), cb);
        }
        cb.run(node);
        
        if(node.rightChild() != null) {
            TraverseInOrder(node.rightChild(), cb);
        }
    }
    
    public void TraverseInOrder(TraverseCallback cb) {
        TraverseInOrder(root, cb);
    }
    
    public void Traverse(BinaryNode node, TraverseCallback cb, int t) {
    }
    
    public BinaryNode root() {
        return root;
    }
    
    public void Remove(int v) {
        BinaryNode<T> valNode = new BinaryNode(new BinaryValue(v));
        Remove(valNode.val());
    }
    
    public void Remove(T v) {
        BinaryNode<T> n = Find(root, v);
        
        if(n == null) {
            System.out.println("Cannot find node with value " + v);
            return;
        }
        BinaryNode p = n.getParent();
        
        // If the node we are removing does not have a parent node, we are removint the
        // root node, and need to handle this special case
        if(p == null) {
            // New root node
            BinaryNode newRoot = n.rightChild();
            BinaryNode leftChild = n.leftChild();
            // Where to put the left child?
            // As far down the left tree from the new root as possible
            BinaryNode lowestPoint = findLowestValue(newRoot);
            
            // Attach the old roots left child to the lowest point in the tree
            leftChild.setParent(lowestPoint);
            lowestPoint.addChild(leftChild, BinaryNode.ChildType.LEFT);
            
            // Register the new root node
            root = newRoot;
        } else {
            System.out.println("----------- DELETE OPERATION -------------");
            // 0. Identidy parent node
            // 1. Identify the node to delete
            // 2. Find the node that should take its place (right child)
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
                parent_node.addChild(replacement_node, BinaryNode.ChildType.RIGHT);
                replacement_node.setParent(parent_node);
            } else {
                // If there are no nodes to the right of the one we are removing, the parent has to be the new root from this point on
                replacement_node = p;
            }
            
            // If the node had left children, we need to move them to the lowest point in the tree
            if(child_to_move != null) {
                System.out.println("Identified node to move with value " + child_to_move.val());
                BinaryNode lowestPoint = findLowestValue(replacement_node);
                child_to_move.setParent(lowestPoint);
                lowestPoint.addChild(child_to_move, BinaryNode.ChildType.LEFT);
            }
        }
    }
    
    public BinaryNode findLowestValue(BinaryNode n) {
        if(n.leftChild() == null)
            return n;
        
        return findLowestValue(n.leftChild());
    }
}

@FunctionalInterface
interface TraverseCallback {
    public void run(BinaryNode node);
}
