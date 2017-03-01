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
            FindInsertPoint(root, (T) n.val()).addChild(n);
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
        return Find(node, content, false);
    }
    
    public BinaryNode FindInsertPoint(BinaryNode node, T content) {
        BinaryNode left = node.leftChild();
        BinaryNode right = node.rightChild();
        
        if(right != null) {
            if(node.val().compareTo(content) < 0) {
                return FindInsertPoint(right, content);
            }
        }
        
        if(left != null) {
            if(node.val().compareTo(content) > 0) {
                return FindInsertPoint(left, content);
            }
        }
        //System.out.println("Inspecting " + node.val() + (left!=null?" leftchild is " + left.val():""));
        return node;
    }
    
    public BinaryNode Find(BinaryNode node, T content, boolean add) {
        if(content.compareTo(node.val()) == 0) {
            //System.out.println("Same..");
            return node;
        } else if(node.leftChild() != null && content.compareTo(node.val()) > 0) {
            // Search from right tree
            //System.out.println("Larger");
            return Find(node.leftChild(), content, add);
        } else if(node.rightChild() != null && content.compareTo(node.val()) < 0) {
            //System.out.println("Smaller");
            // Seach from left tree
            return Find(node.rightChild(), content, add);
        } else {
            if(!add)
                return null;
            else
                return node;
        }
    }
    
    
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
        Remove((T) new BinaryValue(v));
    }
    
    public void Remove(T v) {
        BinaryNode n = Find(root, v);
        
        BinaryNode p = n.getParent();
        
        //if(p == null) {
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
        //}
    }
    
    public BinaryNode findLowestValue(BinaryNode n) {
        if(n.leftChild() == null)
            return n;
        
        return findLowestValue(n.leftChild());
    }
}

class TraverseType {
    public static final int IN_ORDER = 1;
    public static final int PRE_ORDER = 2;
    public static final int POST_ORDER = 3;
}

@FunctionalInterface
interface TraverseCallback {
    public void run(BinaryNode node);
}
