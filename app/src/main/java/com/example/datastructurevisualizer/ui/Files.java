package com.example.datastructurevisualizer.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Dialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
public class Files extends Fragment {
    private Button testButton;
    public Files() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void openDialog() {
        DialogFragment dialogFileAction = new DialogFileAction();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        MainActivity.openDialog(dialogFileAction, "file_action", ft);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        testButton = view.findViewById(R.id.fileDialogTestBtn);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Was shutting down my application
//                DialogFragment dialogFileAction = new DialogFileAction();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                MainActivity.openDialog(dialogFileAction, "file_action", ft);
            }
        });
        MainActivity.actionBar.setTitle("Files");
        MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
        MainActivity.actionBar.show();
        return view;
    }
}