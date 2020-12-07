package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

/**
 */
public class Home extends Fragment {

    private Button binarySearchTree;
    private Button redBlackTree;
    private Button balancedSearchTree;
    private Button graph;
    private Button files;
    private Button about;
    private ImageButton bstInfo;
    private ImageButton rbtInfo;
    private ImageButton avlInfo;
    private ImageButton graphInfo;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        MainActivity.actionBar.hide();
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity.actionBar.hide();
        binarySearchTree = view.findViewById(R.id.bstButton);
        redBlackTree = view.findViewById(R.id.rbtButton);
        balancedSearchTree = view.findViewById(R.id.avlButton);
        graph = view.findViewById(R.id.graphButton);
        files = view.findViewById(R.id.filesButton);
        //about = view.findViewById(R.id.aboutButton);
        bstInfo = view.findViewById(R.id.bstInfoButton);
        rbtInfo = view.findViewById(R.id.rbtInfoButton);
        avlInfo = view.findViewById(R.id.avlInfoButton);
        graphInfo = view.findViewById(R.id.graphInfoButton);

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
                MainActivity.openFragment(new Visualizer("AVL Tree"), true);
            }
        });
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Visualizer("Graph"), true);
            }
        });
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Files(), true);
            }
        });
//        about.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity.openFragment(new AboutPage(), false);
//            }
//        });
        bstInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage("Binary Search Tree"), true);
            }
        });
        rbtInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage("Red Black Tree"), true);
            }
        });
        avlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage("AVL Tree"), true);
            }
        });
        graphInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage("Graph"), true);
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