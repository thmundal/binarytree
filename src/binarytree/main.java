/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytree;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import javax.swing.JTextField;
import jgame.*;

/**
 *
 * @author Thomas
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static Vector2 drawPos;
    public static Vector2 distance;
    public static Vector2 direction;
    
    public static float angle;
    public static float length;
    public static Hashtable<BinaryNode, Vector2> drawList;
    public static Vector2 mouse;
    public static BinaryNode inFocus;
    
    public static void main(String[] args) {
        Game game = new Game();
        mouse = new Vector2(0, 0);
        BinaryTree<BinaryValue> tree = new BinaryTree();
        
        tree.add(5);
        tree.add(4);
        tree.add(1);
        tree.add(7);
        tree.add(25);
        tree.add(3);
        tree.add(8);
        tree.add(26);
        
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
        
        
        //tree.Remove(5);
        System.out.println(tree.size());
        game.Update((g, deltaTime) -> {
            
        });
        
        int size = 50;
        
        // Traverse tree pre order
        System.out.println("Pre order:");
        tree.TraversePreOrder(node -> {
            System.out.print(node.val()+", ");
        });
        System.out.println("\n------------------");
        
        // Traverse tree in order
        System.out.println("In order");
        tree.TraverseInOrder(node -> {
            System.out.print(node.val()+", ");
        });
        System.out.println("\n------------------");

        // Traverse tree post order
        System.out.println("Post order");
        tree.TraversePostOrder(node -> {
            System.out.print(node.val()+", ");
        });
        System.out.println("\n------------------");
        
        drawList = new Hashtable();
        final Vector2 offset = new Vector2(size / 2, size / 2);
        
        game.Draw((g, deltaTime) -> {
            final float pi = (float) Math.PI;
            
            drawPos = new Vector2(game.width() / 2, 100);           // Position to draw root node
            distance = new Vector2(0, 50);                          // A sitance vector with x=0 y=50
            
            angle = pi / 4; // 45 degrees declining angle
            
            length = 200.0f;
            
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
                    Vector2 parentPos = drawList.get(_parent).add(offset);
                    Vector2 thisPos = drawPos.add(offset);
                    g.drawLine(parentPos.x, parentPos.y, thisPos.x, thisPos.y);
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
            
            public void click(MouseEvent e) {
                mouse.x = e.getX();
                mouse.y = e.getY();
                
                // check if there is a node here
                drawList.forEach((node, pos) -> {
                    if(mouse.subtract(pos.add(offset)).length() < size / 2) {
                        inFocus = node;
                    }
                });
                
                if(inFocus != null) {
                    System.out.println("Removed node with value " + inFocus.val());
                    tree.Remove((BinaryValue) inFocus.val());
                } else {
                    System.out.println("inFocus is null");
                }
            }
        });
        
        game.run();
    }
    
}
