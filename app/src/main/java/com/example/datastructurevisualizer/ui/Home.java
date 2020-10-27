package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

/**
 */
public class Home extends Fragment {

    private Button binarySearchTree;
    private Button redBlackTree;
    private Button balancedSearchTree;
    private Button files;
    private Button about;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binarySearchTree = view.findViewById(R.id.bstButton);
        redBlackTree = view.findViewById(R.id.rbtButton);
        balancedSearchTree = view.findViewById(R.id.avlButton);
        files = view.findViewById(R.id.filesButton);
        about = view.findViewById(R.id.aboutButton);

        binarySearchTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Visualizer("Binary Search Tree"), true);
            }
        });
        redBlackTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Visualizer("Red Black Tree"), true);
            }
        });
        balancedSearchTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Visualizer("Balanced Search Tree"), true);
            }
        });
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Files(), false);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new AboutPage(), false);
            }
        });



        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            MainActivity.actionBar.hide();
        }
    }
}