package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationPage extends Fragment {
    private String dataStructureType;

    public InformationPage() {
        // Required empty public constructor
    }

    public InformationPage(String dataStructureType){
        this.dataStructureType = dataStructureType;
    }


    public static InformationPage newInstance(String param1, String param2) {
        InformationPage fragment = new InformationPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        switch (dataStructureType){
            case "Binary Search Tree":
                view = inflater.inflate(R.layout.fragment_information_bst, container, false);
                MainActivity.actionBar.setTitle("Data Structure Information");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                treeInit(view);
                bstInit(view);
                break;
            case "Red Black Tree":
                view = inflater.inflate(R.layout.fragment_information_rbt, container, false);
                MainActivity.actionBar.setTitle("Data Structure Information");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                treeInit(view);
                rbtInit(view);
                break;
            case "Balanced Search Tree":
                view = inflater.inflate(R.layout.fragment_information_avl, container, false);
                MainActivity.actionBar.setTitle("Data Structure Information");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                treeInit(view);
                avlInit(view);
                break;
            case "Graph":
                view = inflater.inflate(R.layout.fragment_information_graph, container, false);
                MainActivity.actionBar.setTitle("Data Structure Information");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                graphInit(view);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_information_page, container, false);
                Button home = view.findViewById(R.id.visualize_button);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Home(), false);
                    }
                });
        }


        return view;

    }


    private void treeInit(View view) {
        TextView header = view.findViewById(R.id.info_datastructure_header);
        header.setText(dataStructureType);
        Button visualize = view.findViewById(R.id.visualize_button);
        visualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Visualizer(dataStructureType), true);
            }
        });
    }

    private void bstInit(View view) {
    }
    private void rbtInit(View view) {
    }
    private void avlInit(View view) {
    }
    private void graphInit(View view) {
        Button visualize = view.findViewById(R.id.visualize_button);
        visualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new GraphVisualizer(), true);
            }
        });

        TextView header = view.findViewById(R.id.info_datastructure_header);
        header.setText("Graph");
    }
}