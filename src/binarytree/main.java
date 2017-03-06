/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytree;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import jgame.*;

/**
 *
 * @author Thomas
 */
public class main {
    public static Vector2 drawPos;
    public static Vector2 distance;
    public static Vector2 direction;
    
    public static float angle;
    public static float length;
    public static Hashtable<BinaryNode, Vector2> drawList;
    public static Vector2 mouse;
    public static BinaryNode inFocus;
    private static BinaryTree<BinaryValue> tree;
    private static JTextArea text_output;
    
    public static void makeNodes() {
        
        tree = new BinaryTree();
        
        tree.add(5);
        tree.add(4);
        tree.add(1);
        tree.add(7);
        tree.add(25);
        tree.add(3);
        tree.add(8);
        tree.add(26);
    }
    
    public static void output(String text) {
        output(text, false);
    }
    
    public static void output(String text, boolean nl) {
        if(text_output != null) {
            Rectangle bounds = text_output.getBounds();
            String old_text = text_output.getText();
            
            if(nl) {
                old_text = old_text + "\n";
            }
            text_output.setText(old_text + text);
            text_output.setBounds(bounds);
        }
    }
    
    public static void main(String[] args) {
        System.setOut(new GameOutput(System.out));
        Game game = new Game();
        mouse = new Vector2(0, 0);
        
        makeNodes();
        
        JTextField input = new JTextField(20);
        input.setBounds(100, 100, 250, 25);
        input.addActionListener(e -> {
            String in = input.getText();
            
            try {
                int n = java.lang.Integer.parseInt(in);
                System.out.println("Adding node with value " + input.getText());
                tree.add(n);
            } catch(Exception ex) {
                System.out.println("Not a number");
            }
            
        });
        game.addComponent(input);
        
        JButton reset = new JButton("Resett tre");
        reset.setBounds(100, 50, 100, 25);
        reset.addActionListener(e -> {
            makeNodes();
        });
        game.addComponent(reset);
        
        text_output = new JTextArea();
        //text_output.setBounds(100, game.height() - 500, 300, 200);
        text_output.setEditable(false);
        
        JScrollPane text_scroll = new JScrollPane(text_output);
        text_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        text_scroll.setBounds(100, game.height() - 500, 400, 200);
        //text_scroll.add(text_output);
        //game.addComponent(text_output);
        game.addComponent(text_scroll);
        
        //tree.Remove(5);
        System.out.println(tree.size());
        game.Update((g, deltaTime) -> {
            
        });
        
        int size = 50;
        
        // Traverse tree pre order
        output("Traverse pre order:\n");
        tree.TraversePreOrder(node -> {
            output(node.val()+", ");
        });
        output("\n------------------\n");
        
        // Traverse tree in order
        output("Traverse in order\n");
        tree.TraverseInOrder(node -> {
            output(node.val()+", ");
        });
        output("\n------------------\n");

        // Traverse tree post order
        output("Traverse post order\n");
        tree.TraversePostOrder(node -> {
            output(node.val()+", ");
        });
        output("\n------------------\n");
        
        drawList = new Hashtable();
        final Vector2 offset = new Vector2(size / 2, size / 2);
        
        game.Draw((g, deltaTime) -> {
            final float pi = (float) Math.PI;
            
            drawPos = new Vector2(game.width() / 2, 10);           // Position to draw root node
            distance = new Vector2(0, 50);                          // A sitance vector with x=0 y=50
            
            angle = pi / 4; // 45 degrees declining angle
            
            length = 200.0f;
            
            drawList.clear();
            tree.TraversePreOrder(node -> {
                g.setColor(Color.black);
                float angle_left = -pi - pi/4;
                float angle_right = pi / 4;
                
                BinaryNode _parent = node.getParent();
                
                if(_parent != null && drawList.containsKey(_parent)) {
                    drawPos = drawList.get(_parent);
                }
                
                // Change to angle to draw next nodes based on what side the node should be on
                if(node.childType() != 0) {
                    if(node.childType() == BinaryNode.ChildType.LEFT) {
                        angle = angle_left;
                    } else {
                        angle = angle_right;
                    }
                }
                
                
                direction = new Vector2(angle, length);
                drawPos = drawPos.add(direction);
                drawList.put(node, drawPos);
                
                // Draw a line from this node to its parent
                if(_parent != null) {
                    Vector2 parentPos = drawList.get(_parent);
                    if(parentPos != null) {
                        parentPos = parentPos.add(offset);
                        Vector2 thisPos = drawPos.add(offset);
                        g.drawLine(parentPos.x, parentPos.y, thisPos.x, thisPos.y);
                    }
                }
                
                if(mouse.subtract(drawPos.add(offset)).length() < size / 2) {
                    inFocus = node;
                    g.setColor(Color.red);
                } else {
                    inFocus = null;
                }
                
                // Draw interface text:
                Rectangle textBoxPos = input.getBounds();
                g.drawString("Skriv inn et tall og trykk enter for å putte inn i treet", textBoxPos.x, textBoxPos.y - 10);
                
                // Draw nodes to the screen using the TraversePreOrder method
                if(drawPos != null)
                    g.fillArc((int) drawPos.x, (int) drawPos.y, size, size, 0, 360);
                
                g.setColor(Color.white);
                g.drawString(((BinaryValue) node.val()).toString(), (int) drawPos.x + size / 2 - 5, (int) drawPos.y + size / 2 + 5);
            });
        });
        
        game.onMouse(new MouseCallback() {
            public void move(MouseEvent e) {
                mouse.x = e.getX();
                mouse.y = e.getY();
            }
            
            Vector2 index;
            public void click(MouseEvent e) {
                mouse.x = e.getX();
                mouse.y = e.getY();
                
                if(drawList.isEmpty()) {
                    inFocus = null;
                }
                
                // check if there is a node here
                drawList.forEach((node, pos) -> {
                    if(mouse.subtract(pos.add(offset)).length() < size / 2) {
                        inFocus = node;
                        index = pos;
                    }
                });
                
                if(inFocus != null) {
                    System.out.println("Removed node with value " + inFocus.val() + " on position " + index);
                    
                    tree.Remove((BinaryValue) inFocus.val());
                    
                    drawList.remove(inFocus);
                    index = null;
                    inFocus = null;
                } else {
                    System.out.println("You didn't click on a node, or there are no nodes to click on");
                }
            }
        });
        
        game.run();
    }
    
    public static class GameOutput extends PrintStream {
        public GameOutput(OutputStream out) {
            super(out, true);
        }
        
        public void print(String s) {
            output(s);
        }
        
        public void println(String s) {
            output(s, true);
        }
    }
}
