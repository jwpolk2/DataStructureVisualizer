package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datastructurevisualizer.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Visualizer extends Fragment {

    private static String dataStructureType;


    public Visualizer() {
        // Required empty public constructor
    }

    public Visualizer(String dataStructureType){
        this.dataStructureType = dataStructureType;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visualizer, container, false);
        return view;
    }
}