package com.example.datastructurevisualizer.ui;

import android.app.AlertDialog;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructurevisualizer.File;
import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {//@link Fragment} subclass.
 * Use the {//@link Files#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Files extends Fragment {
    private RecyclerView fileRecyclerView;
    private FileAdapter fileAdapter;
    private RecyclerView.LayoutManager fileLayoutManager;
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
       final View view = inflater.inflate(R.layout.fragment_files, container, false);
       Context context = getContext();
       com.example.datastructurevisualizer.File file = new com.example.datastructurevisualizer.File();
        com.example.datastructurevisualizer.File file2 = new com.example.datastructurevisualizer.File();
        ArrayList<File> mFile = new ArrayList<File>();
        ArrayList<Integer> values = new ArrayList<>();
        values.add(2);
        values.add(3);
        values.add(5);
        file.setValues(values);
        file.setFileName("Joe's File");
        file.setStructureType("Binary Search Tree");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        file.setDate(dateFormat.format(date));
        file2.setFileName("File 2");
        file2.setStructureType("Binary Search Tree");
        file2.setDate(dateFormat.format(date));
        mFile.add(file);
        mFile.add(file2);
       // testButton = view.findViewById(R.id.fileDialogTestBtn);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO Was shutting down my application
////                DialogFragment dialogFileAction = new DialogFileAction();
////                FragmentTransaction ft = getFragmentManager().beginTransaction();
////                MainActivity.openDialog(dialogFileAction, "file_action", ft);
//            }
//        });

        //final View view = inflater.inflate(R.layout.fragment_manage_apiaries, container, false);
        fileRecyclerView = view.findViewById(R.id.filesRecycle);
        fileRecyclerView.setHasFixedSize(true);
        fileLayoutManager = new LinearLayoutManager(getActivity());
        fileAdapter = new FileAdapter(mFile);

        fileRecyclerView.setLayoutManager(fileLayoutManager);
        fileRecyclerView.setAdapter(fileAdapter);

        fileAdapter.setOnItemClickListener(new FileAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("File Action");
                if (prev != null) {
                    ft.remove(prev);
                } ft.addToBackStack(null);

                DialogFileAction fileActionDialog = new DialogFileAction();
//                saveDialog.setTree(tree);
//                saveDialog.setDataStructureType(dataStructureType);
                fileActionDialog.show(ft, "File Action");
            }
        });
        MainActivity.actionBar.setTitle("Files");
        MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
        MainActivity.actionBar.show();
        return view;
    }
}