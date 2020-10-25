package com.example.datastructurevisualizer.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.datastructurevisualizer.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Visualizer extends Fragment {

    private ImageButton infoButton;
    private ImageButton homeButton;
    private static String dataStructureType;
    private TextView dataStructureHeader;


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
        infoButton = (ImageButton) view.findViewById(R.id.button_info);
        homeButton = (ImageButton) view.findViewById(R.id.button_home);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutPage aboutFragment = new AboutPage();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.view_visualizer, aboutFragment);
                transaction.commit();
            }
        });

        dataStructureHeader = view.findViewById(R.id.visualizerHeader);
        setHeaderText();
        return view;
    }

    private void setHeaderText() {
        switch (dataStructureType) {
            case "Binary Search Tree":
                dataStructureHeader.setText("Binary Search Tree");
                break;
            case "Red Black Tree":
                dataStructureHeader.setText("Red Black Tree");
                break;
            case "Balanced Search Tree":
                dataStructureHeader.setText("Balanced Search Tree");
                break;
        }
    }
}