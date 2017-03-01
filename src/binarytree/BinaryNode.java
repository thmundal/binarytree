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
    public class ChildType {
        public static final int LEFT = 1;
        public static final int RIGHT = 2;
    };
    
    private BinaryNode _parent;
    private BinaryNode leftChild;
    private BinaryNode rightChild;
    private int childType;
    
    public T content;
    
    public BinaryNode() {
        
    }
    
    public BinaryNode(T c) {
        content = c;
        //_parent = p;
        //_parent.addChild(this, ChildType.LEFT);
    }
    
    public void setType(int t) {
        childType = t;
    }
    public int childType() {
        return childType;
    }
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
    public void addChild(BinaryNode child) {
        if(child.val().compareTo(this.val()) >= 0) {
            addChild(child, ChildType.RIGHT);
        } else {
            addChild(child, ChildType.LEFT);
        }
    }
    
    public void setContent(T c) {
        content = c;
    }
    
    public void setParent(BinaryNode p) {
        _parent = p;
    }
    
    public BinaryNode getParent() {
        return _parent;
    }
    
    public T val() {
        return content;
    }
    
    public BinaryNode leftChild() {
        return leftChild;
    }
    
    public BinaryNode rightChild() {
        return rightChild;
    }
}
