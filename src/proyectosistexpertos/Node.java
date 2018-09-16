/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosistexpertos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author L440
 */
public class Node {
    public int x = -1;
    public int y = -1;
    public List<Node> relations = new ArrayList();
    public Node prev;
    public boolean visited = false;
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
}
