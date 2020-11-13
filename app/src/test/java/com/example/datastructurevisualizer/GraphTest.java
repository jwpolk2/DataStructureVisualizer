package com.example.datastructurevisualizer;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class GraphTest {
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

    }



