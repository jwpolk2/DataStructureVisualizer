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

public class Code extends Fragment {
    private String dataStructureType;

    public Code(String dataStructureType) {
        this.dataStructureType = dataStructureType;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        switch (dataStructureType) {
            case "Binary Search Tree":
                view = inflater.inflate(R.layout.code_bst, container, false);

                break;
            case "Red Black Tree":
                view = inflater.inflate(R.layout.code_rbt, container, false);

                break;
            case "Balanced Search Tree":
                view = inflater.inflate(R.layout.code_avl, container, false);

                break;
            case "Graph":
                view = inflater.inflate(R.layout.code_graph, container, false);

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
