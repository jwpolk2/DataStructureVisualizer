package com.example.datastructurevisualizer.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;

import com.example.datastructurevisualizer.AVLTree;
import com.example.datastructurevisualizer.BinarySearchTree;
import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.RedBlackTree;
import com.example.datastructurevisualizer.TreeVisualizer;
import com.example.datastructurevisualizer.VisualizerCanvas;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Visualizer extends Fragment {

    private EditText insertNumber;
    private ImageButton insertButton;
    private Button saveButton;
    private Button loadButton;
    private ImageButton undoButton;
    private Button redoButton;
    private Button autopopulateButton;
    private ImageButton infoButton;
    private ImageButton homeButton;
    private static String dataStructureType;
    private TextView dataStructureHeader;
    private VisualizerCanvas visualizerCanvas;
    private TreeVisualizer tree;


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

        loadButton = view.findViewById(R.id.button_load);
        saveButton = view.findViewById(R.id.button_save);
        insertNumber = view.findViewById(R.id.input_nodes);
        insertButton = view.findViewById(R.id.button_insert);
        undoButton = view.findViewById(R.id.button_undo);
        //redoButton = view.findViewById(R.id.button_redo);
        autopopulateButton = view.findViewById((R.id.button_autopopulate));
        //infoButton = (ImageButton) view.findViewById(R.id.button_info);
       // homeButton = (ImageButton) view.findViewById(R.id.button_home);

//        infoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AboutPage aboutFragment = new AboutPage();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, aboutFragment);
//                transaction.commit();
//            }
//        });


//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Home homeFragment = new Home();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, homeFragment);
//                transaction.commit();
//            }
//        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        autopopulateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoPopulate();
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });
//        redoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                redo();
//            }
//        });


       // dataStructureHeader = view.findViewById(R.id.visualizerHeader);
        visualizerCanvas = view.findViewById(R.id.view_visualizer);
        visualizerCanvas.setParent(this);
        MainActivity.setVisualizerCanvas(visualizerCanvas);
        MainActivity.actionBar.show();
        initDataStructure();
        return view;
    }

    private void undo() {
        tree.undo();
    }

    private void redo() {
        tree.redo();
    }


    private void initDataStructure() {
        switch (dataStructureType) {
            case "Binary Search Tree":
                tree = new BinarySearchTree();
                MainActivity.actionBar.setTitle("Binary Search Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case "Red Black Tree":
                MainActivity.actionBar.setTitle("Red Black Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                tree = new RedBlackTree();
                break;
            case "Balanced Search Tree":
                MainActivity.actionBar.setTitle("Balanced Search Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                tree = new AVLTree();
                break;
        }
    }

    private void insert() {
        if (visualizerCanvas.canvas == null) {
            int vHeight = visualizerCanvas.getHeight();
            int vWidth = visualizerCanvas.getWidth();
            visualizerCanvas.setDimensions(vHeight, vWidth);
        }
        tree.insert(Integer.parseInt(String.valueOf(insertNumber.getText().toString())));
        insertNumber.setText("");
    }

    private void autoPopulate(){
        if (visualizerCanvas.canvas == null) {
            int vHeight = visualizerCanvas.getHeight();
            int vWidth = visualizerCanvas.getWidth();
            visualizerCanvas.setDimensions(vHeight, vWidth);
        }
        int[] array = {50, 30, 70, 20, 80, 60, 20, 40, 90};
        for (int i = 0; i< array.length; i++) {
            tree.insert(array[i]);
        }
    }


    public void setCanvas(Bitmap bitmap) {
        visualizerCanvas.setBackground(new BitmapDrawable(bitmap));
    }

    private void save() {
        //TODO
    }

    private void load() {
        //TODO
    }



}