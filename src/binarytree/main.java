/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytree;
import jgame.*;

/**
 *
 * @author Thomas
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static Vector2 startPos;
    public static Vector2 distance;
    public static Vector2 direction;
    public static void main(String[] args) {
        Game game = new Game();
        
        BinaryTree<BinaryValue> tree = new BinaryTree();
        
        tree.add(5);
        tree.add(4);
        tree.add(1);
        tree.add(7);
        tree.add(25);
        tree.add(3);
        
        tree.Remove(25);
        System.out.println(tree.size());
        game.Update((g, deltaTime) -> {
            
        });
        
        startPos = new Vector2(game.width() / 2, 100);
        distance = new Vector2(0, 50);
        
        direction = new Vector2(1.0f, 50.0f);
        
        int size = 50;
        
        game.Draw((g, deltaTime) -> {
            tree.TraversePreOrder(node -> {
                System.out.println(node.val());
                //System.out.println("traversing to node with value " + node.val());
                g.drawArc((int) startPos.x, (int) startPos.y, size, size, 0, 360);
                g.drawString(((BinaryValue) node.val()).toString(), (int) startPos.x + size / 2, (int) startPos.y + size / 2);
                
                if(node.childType() != 0) {
                    if(node.childType() == BinaryNode.ChildType.LEFT) {
                        direction = new Vector2(-1.0f, 50.f);
                    } else {
                        direction = new Vector2(1.0f, 50.f);
                    }
                } else {
                    direction = new Vector2(1.0f, 50.f);
                }
                startPos = startPos.add(direction);
            });
            
            System.out.println("In order");
            tree.TraverseInOrder(node -> {
                System.out.println(node.val());
            });
            
            System.out.println("Post order");
            tree.TraversePostOrder(node -> {
                System.out.println(node.val());
            });
        });
    }
    
}
