package com.example.datastructurevisualizer;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class GraphTest extends TestCase {
    private Graph graph = new Graph();

    public void testInsertGraphNode(){
        try{
            graph.insertGraphNode(1,5,10);
            graph.insertGraphNode(2,10,15);

            ArrayList<Node> keyArrl = graph.getAllNodes();
            assertEquals(2, keyArrl.size());
        }
        catch(NullPointerException e) {
            System.out.println("Error: nothing inserted");
        }

        }

     public void testInsertDirectedEdge(){
        try{
            graph.insertDirectedEdge(1,2,10);
            graph.insertDirectedEdge(2,3,15);
        }
        catch(NullPointerException e){
            System.out.println("Error: nothing inserted");
         }
    }

    public void testRemoveNoAnim(){
            graph.insertGraphNode(1,5,10);
            graph.insertGraphNode(2,10,15);
            graph.removeNoAnim(1);
            graph.removeNoAnim(2);

            ArrayList<Node> keyArrl = graph.getAllNodes();
            assertEquals(0, keyArrl.size());

    }

    public void testRemoveAnim(){
        graph.insertGraphNode(1,5,10);
        graph.insertGraphNode(2,10,15);
        graph.removeAnim(1);
        graph.removeAnim(2);

        ArrayList<Node> keyArrl = graph.getAllNodes();
        assertEquals(0, keyArrl.size());

        graph.


    }



    }



