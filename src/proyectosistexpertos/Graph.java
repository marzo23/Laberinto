/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosistexpertos;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author L440
 */
public class Graph {
    public final int lenght;
    //public List<Node> nodeList = new ArrayList();
    public Node[][] nodes;
    
    public Graph(int lenght){
        this.lenght = lenght;
        nodes = new Node[lenght][lenght];
    }
    
    public void initializeMatrix(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                nodes[i][j] = new Node(i,j);
            }
        }
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                addRelation(i,j, i,j-1);
                addRelation(i,j, i,j+1);
                addRelation(i,j, i+1,j);
                addRelation(i,j, i-1,j);
            }
        }
    }
    
    public void initializeMatrix2(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                nodes[i][j] = new Node(i,j);
            }
        }
        
    }
    
    public void RandomForest(Node n, List<Node> evaluated, List<Node> toEvaluate){
        if(evaluated == null)
            evaluated = new ArrayList();
        if(toEvaluate == null)
            toEvaluate = new ArrayList();
        
        if(evaluated.contains(n))
            return;
        
        n.visited = true;
        evaluated.add(n);
        toEvaluate.remove(n);
        
        for (int i = 0; i < option.length; i++) {
            Node aux = null;
            try{
                aux = nodes[n.x+option[i][0]][n.y+option[i][1]];
            }catch(Exception e){}
            if(aux!=null){
                if(aux.prev!=null){
                    if(!aux.relations.contains(aux.prev) && aux.relations.size()<2){
                        aux.relations.add(aux.prev);
                        aux.prev.relations.add(aux);
                    }
                }else
                    aux.prev = n;
                if(!toEvaluate.contains(aux) && !evaluated.contains(aux))
                    toEvaluate.add(aux);
                
            }
        }
        
        Node next = null;
        Random rand = new Random();
        boolean flag = false;
        while(next==null){
            List<Integer> nums = new ArrayList();
            for (int i = 0; i < option.length; i++) {
                nums.add(i);
            }
            for (int i = 0; i < nums.size() && next==null; i++) {

                Integer r = nums.get(rand.nextInt(nums.size()));
                nums.remove(r);
                i=0;
                int xN = n.x+option[r][0];
                int yN = n.y+option[r][1];
                try{
                    next = nodes[xN][yN];
                }catch(Exception e){}
                if(evaluated.contains(next))
                    next=null;
            }
            if(next==null){
                if(toEvaluate.isEmpty())
                    break;
                n = toEvaluate.get(0);
                toEvaluate.remove(0);
                flag = true;
            }
            else{
                toEvaluate.remove(next);
            }
            
        }
        
        if(next!=null){
            for (int i = 0; i < n.relations.size(); i++) {
                Node aux = n.relations.get(i);
                if(aux.visited!=true && aux != next){
                    aux.relations.remove(n);
                    n.relations.remove(aux);
                }
            }
            RandomForest(next, evaluated, toEvaluate);
        }else if(flag){
            n.relations.add(n.prev);
            n.prev.relations.add(n);
            RandomForest(n, evaluated, toEvaluate);
        }
        
        
    }
    
    int[][] option = { {0,1}, {1,0}, {0,-1}, {-1,0} };
    
    public void getFromFile(String f) throws IOException{
        File file = new File(f); 
  
        BufferedReader br = new BufferedReader(new FileReader(file)); 

        String st; 
        List<String[]> lines = new ArrayList();
        while ((st = br.readLine()) != null){
            String[] tmp = st.split("::");
            lines.add(tmp);
        }
        
        for (int i = 0; i < lenght*lenght; i++) {
            for (int j = i; j < lenght*lenght; j++) {
                if(lines.get(i)[j].equals("1")){
                    addRelation(i%lenght, i/lenght, j%lenght, j/lenght);
                }
            }
        }
    }
    
    public Node setDestiny(Node actual){
        List<Node> evaluated = new ArrayList();
        
        Node next = actual;
        do{
            evaluated.add(next);
            Node tmp = null;
            for (int i = 0; i < next.relations.size(); i++) {
                if(!evaluated.contains(next.relations.get(i)))
                    tmp = next.relations.get(i);
            }
            if(tmp==null)
                return next;
            else
                next = tmp;
        }while(true);
    }
    
    public void setRelationsByNode(Node treeRoot){
        for (int i = 0; i < treeRoot.relations.size(); i++) {
            nodes[treeRoot.x][treeRoot.y].relations.add(nodes[treeRoot.relations.get(i).x][treeRoot.relations.get(i).y]);
            setRelationsByNode(treeRoot.relations.get(i));
        }
    }
    
    public void Prim(Node treeRoot, List<Node> evaluated, List<Node> toEvaluate)
    {
        if (treeRoot != null)
        {
            if (evaluated == null)
            {
                evaluated = new ArrayList();
            }
            
            if (toEvaluate == null)
            {
                toEvaluate = new ArrayList();
            }
            
            Node aux = nodes[treeRoot.x][treeRoot.y];
            if (aux != null){
                evaluated.add(aux);
                Random rand = new Random();

                aux.visited = true;
                Node next = null;
                System.out.println("AUX-------------------------------- X: "+aux.x+" Y: "+aux.y);
                toEvaluate.addAll(aux.relations);
                while(next==null){
                    List<Integer> nums = new ArrayList();
                    for (int i = 0; i < option.length; i++) {
                        nums.add(i);
                    }
                    for (int i = 0; i < nums.size() && next==null; i++) {
                        
                        Integer r = nums.get(rand.nextInt(nums.size()));
                        nums.remove(r);
                        i=0;
                        int xN = aux.x+option[r][0];
                        int yN = aux.y+option[r][1];
                        try{
                            next = nodes[xN][yN];
                        }catch(Exception e){}
                        if(evaluated.contains(next))
                            next=null;
                    }
                    if(next==null){
                        if(toEvaluate.isEmpty())
                            break;
                        aux = toEvaluate.get(0);
                        toEvaluate.remove(0);
                    }
                    else{
                        toEvaluate.remove(next);
                    }
                    System.out.println("aux X: "+aux.x+" Y: "+aux.y);
                }
                if(next==null)
                    return;
                System.out.println("next X: "+next.x+" Y: "+next.y);
                Node newN = new Node(next.x, next.y);
                next.prev = aux;
                newN.prev = treeRoot;
                treeRoot.relations.add(newN);
                Prim(newN, evaluated, toEvaluate);
            }
        }
    }
    
    
    public Node FindTreeNodeById(Node treeRoot, int x, int y, List<Node> procesed)
    {
        if (treeRoot.x == x && treeRoot.y==y)
            return treeRoot;
        else
        {
            procesed.add(treeRoot);
            for (int i = 0; i < treeRoot.relations.size(); i++)
            {
                if (!procesed.contains(treeRoot.relations.get(i)))
                {
                    Node aux = FindTreeNodeById(treeRoot.relations.get(i), x, y, procesed);
                    if (aux != null)
                        return aux;
                }                    
            }
        }
        return null;
    }
    
    public Node find(Node treeRoot, Node n, Stack<Node> toValidate, List<Node> validated){
            Node aux = null;
            if (toValidate == null)
                toValidate = new Stack<Node>();
            if (validated == null)
            {
                validated = new ArrayList();
                resetPrev();
            }
            if (treeRoot != null)
            {
                if (treeRoot==n)
                    return treeRoot;
                validated.add(treeRoot);
                for (int i = 0; i < treeRoot.relations.size(); i++)
                {
                    

                    if (!validated.contains(treeRoot.relations.get(i))){
                        toValidate.push(treeRoot.relations.get(i));
                        if(treeRoot.relations.get(i).prev==null)
                            treeRoot.relations.get(i).prev = treeRoot;
                    }
                }
            }
            if (toValidate.size() > 0)
                aux = find(toValidate.pop(), n, toValidate, validated);
            return aux;
    }
    
    void resetPrev(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                nodes[i][j].prev = null;
            }
        }
    }
    
    void printRout(Node n){
        List<Node> validated = new ArrayList();
        while(n.prev!=null){
            if(validated.contains(n))
                continue;
            validated.add(n);
            System.out.println("x: "+n.x+" y: "+n.y);
            n= n.prev;
        }
    }
    
    private Node addRelation(int xOrigin, int yOrigin, int xDestination, int yDestination){
        if(xDestination>=0 && yDestination>=0 && xDestination<lenght && yDestination<lenght)
        if(!nodes[xOrigin][yOrigin].relations.contains(nodes[xDestination][yDestination])){
            nodes[xOrigin][yOrigin].relations.add(nodes[xDestination][yDestination]);
            nodes[xDestination][yDestination].relations.add(nodes[xOrigin][yOrigin]);
            return nodes[xDestination][yDestination];
        }
        return null;
    }
    
    
    
    public void printRelations(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                System.out.println("NODE: x-"+nodes[i][j].x+" y-"+nodes[i][j].y);
                for (int k = 0; k < nodes[i][j].relations.size(); k++) {
                    System.out.println("rel "+k+": x-"+nodes[i][j].relations.get(k).x+" y-"+nodes[i][j].relations.get(k).y);
                }
            }
        }
    }
    
    public void drawMaze(int screenSize, Node destiny){
        Test t = new Test(nodes, lenght, screenSize, destiny);
        t.show();
        
    }
}

class Test extends JFrame implements Runnable{
    public Node[][] nodes;
    public int lenght;
    public int screenSize;
    public Node destiny;
    
    Test(Node[][] nodes, int lenght, int screenSize, Node destiny){
        super();
        this.destiny = destiny;
        this.nodes = nodes;
        this.lenght = lenght;
        this.screenSize = screenSize;
        setSize(screenSize, screenSize);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        show();
        run();
    }
    
    public void paint(Graphics g){
        if(destiny==toDraw)
            update(g);
        
        System.out.println("rx: "+toDraw.x+" ry: "+toDraw.y);
        g.setColor(Color.green);
        g.fillRect(toDraw.y*(screenSize/lenght), toDraw.x*(screenSize/lenght),(screenSize/lenght), (screenSize/lenght));
    }
    
    public void update(Graphics g){
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenSize, screenSize);
        
        g.setColor(Color.WHITE);
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                Node aux = nodes[i][j];
                
                int width = screenSize/lenght-(screenSize/lenght/5);
                int height = screenSize/lenght-(screenSize/lenght/5);
                for (int k = 0; k < aux.relations.size(); k++) {
                    Node aux2 = nodes[i][j].relations.get(k);
                    
                    if(aux2.x==i && aux2.y==(j+1)){
                        width = screenSize/lenght;
                    }else if(aux2.x==(i+1) && aux2.y==j){
                        height = screenSize/lenght;
                    }
                    
                }
                
                g.fillRect(j*(screenSize/lenght), i*(screenSize/lenght), width, height);
            }
        }
        g.setColor(Color.YELLOW);
        g.fillRect(destiny.y*(screenSize/lenght), destiny.x*(screenSize/lenght), screenSize/lenght, screenSize/lenght);
    }
    boolean flag = false;
    Node toDraw = null;
    
    void PrintSearch(Node n){
        
        List<Node> validated = new ArrayList();
        while(n.prev!=null){
            toDraw = n;
            repaint();
            if(validated.contains(n))
                continue;
            validated.add(n);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
            n= n.prev;
        }
        
    }
    
    void search(Node n, List<Node> evaluated){
        toDraw = n;
        if(n==destiny)
            return;
        
        if(evaluated == null)
            evaluated = new ArrayList();
        
        if(evaluated.contains(n))
            return;
        
        evaluated.add(n);
        Node tmp = null;
        for (int i = 0; i < n.relations.size(); i++) {
            if(!evaluated.contains(n.relations.get(i)))
                tmp = n.relations.get(i);
        }
        
        
        if(tmp!=null)
            search(n, evaluated);
        
    }
    
    @Override
    public void run() {
        
        PrintSearch(destiny);
        //search(nodes[0][0], null);
    }
}

