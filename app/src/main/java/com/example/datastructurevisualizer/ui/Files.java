package com.example.datastructurevisualizer.ui;

import android.app.Dialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Files#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Files extends Fragment implements DialogFileAction.DialogFileActionListener{
    private Button testButton;
    public Files() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static Files newInstance(String param1, String param2) {
        Files fragment = new Files();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_files, null);
        testButton = (Button) view.findViewById(R.id.fileDialogTestBtn);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        DialogFileAction dialogFileAction = new DialogFileAction();
        dialogFileAction.show(getFragmentManager(), "file action dialog");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        MainActivity.actionBar.setTitle("Files");
        MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
        return view;

    }
}