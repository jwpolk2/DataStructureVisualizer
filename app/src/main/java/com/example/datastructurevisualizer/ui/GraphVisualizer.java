package com.example.datastructurevisualizer.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.datastructurevisualizer.Graph;
import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.VisualizerCanvas;
import java.util.Random;

public class GraphVisualizer extends Fragment {
    private Button display;
    private Button button2;
    private ImageButton infoButton;
    private static VisualizerCanvas visualizerCanvas;
    private String dataStructureType;
    private Graph graph;


    public GraphVisualizer() {
        dataStructureType = "Graph";
        graph = new Graph();

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setVisualizerCanvas(visualizerCanvas);
        MainActivity.actionBar.show();

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
        MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);


        display = view.findViewById(R.id.display_button);
        button2 = view.findViewById(R.id.button2_button);
        visualizerCanvas = view.findViewById(R.id.graph_visualizer);
        visualizerCanvas.setParentGraph(this);
        infoButton = view.findViewById(R.id.button_info);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage(dataStructureType), true);
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCanvas();
                Toast.makeText(getContext(),"Display Button Pressed", Toast.LENGTH_LONG)
                        .show();

                // TODO temp graph render
                graph.render();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCanvas();
                Toast.makeText(getContext(), "Button 2 Pressed", Toast.LENGTH_LONG)
                        .show();
                java.util.Random rand = new java.util.Random();

                // TODO some functionality

            }
        });

        // TODO remove
        // Inserts some Nodes and edges if the graph is empty.
        int k = 0;
        if (graph.getAllNodes().isEmpty()) {
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 5; ++j) {
                    graph.insertGraphNode(k,100 + j * 200, 100 + i * 200);
                    if (k != 0) graph.insertDirectedEdge(k - 1, k, 10);
                    ++k;

                }
            }
        }
        graph.render();


        return view;
    }

    /**
     * This method is used to check if the canvas has been initialized. If not it creates a canvas.
     */
    public void checkCanvas() {
        if (visualizerCanvas.canvas == null) {
            int vHeight = visualizerCanvas.getHeight();
            int vWidth = visualizerCanvas.getWidth();
            visualizerCanvas.setDimensions(vHeight, vWidth);
        }
    }

    /**
     * This method sets the canvas bitmap.
     * @param bitmap
     */
    public void setCanvas(Bitmap bitmap) {
        visualizerCanvas.setBackground(new BitmapDrawable(bitmap));
    }
}