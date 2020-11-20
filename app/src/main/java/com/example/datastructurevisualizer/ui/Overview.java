package com.example.datastructurevisualizer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

public class Overview extends Fragment {
    private String dataStructureType;
    private Button visualize;

    public Overview(String dataStructureType) {
        this.dataStructureType = dataStructureType;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        switch (dataStructureType) {
            case "Binary Search Tree":
                view = inflater.inflate(R.layout.overview_bst, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "Red Black Tree":
                view = inflater.inflate(R.layout.overview_rbt, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "AVL Tree":
                view = inflater.inflate(R.layout.overview_avl, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "Graph":
                view = inflater.inflate(R.layout.overview_graph, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer("Graph"), true);
                    }
                });

                break;
            default:
                view = inflater.inflate(R.layout.error_page, container, false);
                Button home = view.findViewById(R.id.home_button);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Home(), false);
                    }
                });
        }

        return view;
    }
}
