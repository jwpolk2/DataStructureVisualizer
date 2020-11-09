package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.datastructurevisualizer.Graph;
import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

import java.util.Random;


public class GraphVisualizer extends Fragment {
    private Button display;
    private Button button2;

    // TODO remove?
    Graph graph;


    public GraphVisualizer() {
        graph = new Graph();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_visualizer, container, false);
        MainActivity.actionBar.setTitle("Graph");


        display = view.findViewById(R.id.display_button);
        button2 = view.findViewById(R.id.button2_button);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Display Button Pressed", Toast.LENGTH_LONG)
                        .show();

                // TODO temp graph render
                graph.render();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button 2 Pressed", Toast.LENGTH_LONG)
                        .show();
                java.util.Random rand = new java.util.Random();

                // TODO temp graph insert
//                graph.insertGraphNode(Math.abs(rand.nextInt()) % 100,
//                        Math.abs(rand.nextInt()) % 100,Math.abs(rand.nextInt()) % 100);
                graph.insertGraphNode(60, 60, 60);

            }
        });


        return view;
    }
}