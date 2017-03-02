/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytree;

/**
 * A class to use as a value for the binary nodes. Pretty self-explanatory
 * @author Thomas
 */
public class BinaryValue implements Comparable<BinaryValue> {
    private int val;
    public BinaryValue(int v) {
        val = v;
    }
    
    public int val() {
        return val;
    }

    @Override
    public int compareTo(BinaryValue o) {
        return (val() == o.val()?0:(val() > o.val()?1:-1));
    }
    
    @Override
    public String toString() {
        return java.lang.Integer.toString(val);
    }
}
