package com.example.datastructurevisualizer.ui;
import com.example.datastructurevisualizer.ui.Visualizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.datastructurevisualizer.R;

public class DialogSave extends DialogFragment {
    private EditText fileName;
    private Button saveBtn;
    private Button cancelBtn;
    private Visualizer visualizer;

    public DialogSave() {

    }

    public static DialogSave newInstance(String title) {
        DialogSave saveDialog = new DialogSave();
        Bundle args = new Bundle();
        args.putString("save",title);
        saveDialog.setArguments(args);
        return saveDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_save, container);
        fileName = view.findViewById(R.id.saveDialog_fileName);
        saveBtn = view.findViewById(R.id.saveDialog_saveBtn);
        cancelBtn = view.findViewById(R.id.saveDialog_cancelBtn);

//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                visualizer.save();
//            }
//        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}