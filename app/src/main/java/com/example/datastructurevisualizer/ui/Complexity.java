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

/**
 * This class displays the information for the complexity tab on the information page
 */
public class Complexity extends Fragment {
    private String dataStructureType;
    private Button visualize;

    /**
     * Constructor which sets the data structure type of the information page.
     * @param dataStructureType
     */
    public Complexity(String dataStructureType) {
        this.dataStructureType = dataStructureType;
    }


    @Nullable
    @Override
    /**
     * This inflates the view of the tab respective to the data structure type of the information page.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        switch (dataStructureType) {
            case "Binary Search Tree":
                view = inflater.inflate(R.layout.complexity_bst, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "Red Black Tree":
                view = inflater.inflate(R.layout.complexity_rbt, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "AVL Tree":
                view = inflater.inflate(R.layout.complexity_avl, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "Graph":
                view = inflater.inflate(R.layout.complexity_graph, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer("Visualizer"), true);
                    }
                });

                break;
            default:
                //Used as an error page early on when some data structure types did not yet have layout files
                view = inflater.inflate(R.layout.error_page, container, false);
                Button home = view.findViewById(R.id.home_button);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Home(), false);
                    }
                });
        }

        //returns the view created
        return view;
    }
}
