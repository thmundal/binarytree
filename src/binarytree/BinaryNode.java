/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytree;

import java.util.List;

/**
 *
 * @author Thomas
 */

public class BinaryNode<T extends Comparable> {
    /**
     * A struct to give meaning to what it means to be a child on the right side or the left side
     */
    public class ChildType {
        public static final int LEFT = 1;
        public static final int RIGHT = 2;
    };
    
    private BinaryNode _parent;
    private BinaryNode leftChild;
    private BinaryNode rightChild;
    private int childType;
    
    public T content;
    
    /**
     * Construct a new BinaryNode with a given value
     * @param c     The content of this node
     */
    public BinaryNode(T c) {
        content = c;
    }
    
    /**
     * Set the Child Type of this node, so that it knows wether or not it is a right child or a left child, if it is a child
     * @param t     The child type to set
     */
    public void setType(int t) {
        childType = t;
    }
    
    /**
     * Returns this node's child type
     * @return The child type
     */
    public int childType() {
        return childType;
    }
    
    /**
     * Add a child to this node
     * @param child     The node to add
     * @param t         The type of child; right or left
     * @return          Returns the child that was added
     */
    public BinaryNode addChild(BinaryNode child, int t) {
        child.setParent(this);
        child.setType(t);
        if(t == ChildType.LEFT) {
            System.out.println("Add " + child.val()+ " as left child on " + val());
            leftChild = child;
        } 
        if(t == ChildType.RIGHT) {
            System.out.println("Add " + child.val()+ " as right child on " + val());
            rightChild = child;
        }
        
        return child;
    }
    
    /**
     * Shortcut function for adding a child
     * @param child     The node to add as child
     */
    public void addChild(BinaryNode child) {
        if(child.val().compareTo(this.val()) >= 0) {
            addChild(child, ChildType.RIGHT);
        } else {
            addChild(child, ChildType.LEFT);
        }
    }
    
    /**
     * Remove a child based on a node
     * @param child     The node to remove
     */
    public void removeChild(BinaryNode child) {
        if(child.childType() == ChildType.LEFT) {
            leftChild = null;
        } else {
            rightChild = null;
        }
    }
    
    /**
     * Remove a child based on its side relative to this node
     * @param side      The side to remove; right or left
     */
    public void removeChild(int side) {
        if(side == ChildType.LEFT) {
            leftChild = null;
        } else {
            rightChild = null;
        }
    }
    
    /**
     * Set the content of this node
     * @param c     The content to set
     */
    public void setContent(T c) {
        content = c;
    }
    
    /**
     * Set the parent node of this node
     * @param p     The node to set as parent
     */
    public void setParent(BinaryNode p) {
        _parent = p;
    }
    
    /**
     * Return this node's parent node
     * @return  The node that is the parent of this node
     */
    public BinaryNode getParent() {
        return _parent;
    }
    
    /**
     * Return the value of this node
     * @return  The value of this node
     */
    public T val() {
        return content;
    }
    
    /**
     * Return the node that is this node's left child
     * @return  The node that is the left child
     */
    public BinaryNode leftChild() {
        return leftChild;
    }
    
    /**
     * Return the node that is this node's right child
     * @return The node that is the right child
     */
    public BinaryNode rightChild() {
        return rightChild;
    }
}
