package com.example.datastructurevisualizer.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.datastructurevisualizer.R;

//public class DialogSave extends AppCompatDialogFragment {
//    @NonNull
//    public static DialogSave newInstance(int title) {
//        DialogSave dialogSave = new DialogSave();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        dialogSave.setArguments(args);
//        return dialogSave;
//    }
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_save, null); //TODO: Check if correct args
//
//        builder.setView(view).setTitle("save").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        return super.onCreateDialog(savedInstanceState);
//    }
//}

public class DialogSave extends DialogFragment {
    private EditText fileName;

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
        return inflater.inflate(R.layout.dialog_save, container);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view,savedInstanceState);
//        fileName = (EditText) view.findViewById(R.id.saveDialog_fileName);
//        String title = getArguments().getString("save","Save data structure?");
//        getDialog().setTitle("save");
//
//
//    }
}