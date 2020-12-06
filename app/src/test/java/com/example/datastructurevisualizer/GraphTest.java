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

    public void testBreadthFirstPathFindAnim(){
       // try{
            graph.insertGraphNode(1,5,10);
            graph.insertGraphNode(2,10,15);
            graph.insertGraphNode(4,7,8);
            graph.insertGraphNode(3,9,11);
            graph.insertGraphNode(5,6,4);

            graph.insertDirectedEdge(1, 2, 4);
            graph.insertDirectedEdge(1, 4, 3);
            graph.insertDirectedEdge(2, 5, 4);
            graph.insertDirectedEdge(4, 3, 1);
            graph.insertUnDirectedEdge(1, 5, 10);
            graph.breadthFirstPathfindAnim(1, 2);
            graph.breadthFirstPathfindAnim(1, 4);
            graph.primsAlgorithm(1);
            graph.breadthFirstPathfind(1, 2);
            graph.breadthFirstPathfind(1, 4);
            graph.dijkstraPathfind(1, 5);
            graph.kruskalsAlgorithm();
            //assertEquals(2, keyArrl.size());
       // }
       // catch(NullPointerException e) {
       //     System.out.println("Error: nothing inserted");
        //}

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



    }

    public void testGetNode(){
        graph.insertGraphNode(1,5,10);
        graph.insertGraphNode(2,10,15);

        Node node = graph.getNode(2);
        assertEquals(2, node.key);


    }


    }



