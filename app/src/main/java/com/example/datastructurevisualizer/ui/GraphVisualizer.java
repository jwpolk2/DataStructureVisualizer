package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;


public class GraphVisualizer extends Fragment {


    public GraphVisualizer() {
        // Required empty public constructor
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
        return view;
    }
}