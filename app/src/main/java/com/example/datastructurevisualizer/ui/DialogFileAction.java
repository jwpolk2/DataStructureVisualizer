package com.example.datastructurevisualizer.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datastructurevisualizer.R;

import java.util.ArrayList;

//public class DialogFileAction extends AppCompatDialogFragment {
//    @NonNull
//    public static DialogFileAction newInstance(int title) {
//        DialogFileAction dialogFileAction= new DialogFileAction();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        dialogFileAction.setArguments(args);
//        return dialogFileAction;
//    }
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_file_action, null);
//
//        builder.setView(view).setTitle("File Action").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//
//            }
//        });
//        return builder.create();
//    }
//
//    public interface DialogFileActionListener {
//
//    }
//}

public class DialogFileAction extends DialogFragment {
    private Button renameBtn;
    private Button loadBtn;
    private Button deleteBtn;
    private Button cancelBtn;

    public void setVals(ArrayList<Integer> vals) {
        this.vals = vals;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    private ArrayList<Integer> vals;
    private String dsType;

    public DialogFileAction() {

    }

    public static DialogFileAction newInstance(String title) {
        DialogFileAction fileActionDialog = new DialogFileAction();
        Bundle args = new Bundle();
        args.putString("save",title);
        fileActionDialog.setArguments(args);
        return fileActionDialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_file_action, container);
        renameBtn = view.findViewById(R.id.fileDialog_renameBtn);
        loadBtn = view.findViewById(R.id.fileDialog_loadBtn);
        deleteBtn = view.findViewById(R.id.fileDialog_deleteBtn);
        cancelBtn = view.findViewById(R.id.fileDialog_cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("final delete");
                if (prev != null) {
                    ft.remove(prev);
                } ft.addToBackStack(null);

                DialogFileDelete deleteFileDialog = new DialogFileDelete();
                deleteFileDialog.show(ft, "final delete");
            }
        });
        return view;
    }
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//    }
}