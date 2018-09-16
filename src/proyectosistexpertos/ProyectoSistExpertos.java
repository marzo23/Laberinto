/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosistexpertos;

/**
 *
 * @author L440
 */
public class ProyectoSistExpertos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph g = new Graph(16);
        g.initializeMatrix();
        
        //g.randomForest();
        //g.randomForestMatrix();
        //g.prim(g.nodes[0][0], null, null);
        /* Node root = new Node(0,0);
        g.Prim(root, null,null);
        g.initializeMatrix2();
        System.out.println("holi");
        g.setRelationsByNode(root);
        System.out.println("jdjcccc");*/
        
        g.RandomForest(g.nodes[0][0], null, null);
        //g.printRelations();
        Node n = g.nodes[15][15]; //g.setDestiny(g.nodes[0][0]);
        Node n2 = g.find(g.nodes[0][0], n, null, null);
        g.printRout(n2);
        
        g.drawMaze(700, n);
        
        //g.drawMaze(500, g.nodes[0][0]);
    }
    
}
