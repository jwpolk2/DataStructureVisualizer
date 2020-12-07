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
 * This class will control the information displayed on the 'Code' tab of the information page.
 */
public class Code extends Fragment {
    private String dataStructureType;
    private Button visualize;

    /**
     * Constructuor which sets the data structure type of the code page to be displayed
     * @param dataStructureType
     */
    public Code(String dataStructureType) {
        this.dataStructureType = dataStructureType;
    }


    @Nullable
    @Override
    /**
     * This method creates the view of the code tab based on the data structure type.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        //Different layout files exist for the different data structure types
        switch (dataStructureType) {
            case "Binary Search Tree":
                view = inflater.inflate(R.layout.code_bst, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "Red Black Tree":
                view = inflater.inflate(R.layout.code_rbt, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "AVL Tree":
                view = inflater.inflate(R.layout.code_avl, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer(dataStructureType), true);
                    }
                });

                break;
            case "Graph":
                view = inflater.inflate(R.layout.code_graph, container, false);
                visualize = view.findViewById(R.id.button_visualize);
                visualize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Visualizer("Graph"), true);
                    }
                });

                break;
            default:
                //Used as an error page early on when some of the data structure pages did not yet have layouts
                view = inflater.inflate(R.layout.error_page, container, false);
                Button home = view.findViewById(R.id.home_button);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Home(), false);
                    }
                });
        }
        //returns the view created above
        return view;
    }
}
