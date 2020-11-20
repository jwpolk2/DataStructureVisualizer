package com.example.datastructurevisualizer.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datastructurevisualizer.File;
import com.example.datastructurevisualizer.MainActivity;
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
    private TextView fileNameTextView;
    private String fileNameText;
    private int filePosition;
    private ArrayList<Integer> vals;
    private String dsType;
    private boolean isDefault;
    private Files parentFrag;

    public void setFileNameText(String fileNameText) {
        this.fileNameText = fileNameText;
    }


    public void setParentFrag(Files parentFrag) {
        this.parentFrag = parentFrag;
    }



    public void setFilePosition(int filePosition) {
        this.filePosition = filePosition;
    }

    public void setVals(ArrayList<Integer> vals) {
        this.vals = vals;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }



    public DialogFileAction() {

    }

    public static DialogFileAction newInstance(String title) {
        DialogFileAction fileActionDialog = new DialogFileAction();
        Bundle args = new Bundle();
        args.putString("save",title);
        fileActionDialog.setArguments(args);
        return fileActionDialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        // Reload current fragment
        Fragment frg = parentFrag;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_file_action, container);
        renameBtn = view.findViewById(R.id.fileDialog_renameBtn);
        loadBtn = view.findViewById(R.id.fileDialog_loadBtn);
        deleteBtn = view.findViewById(R.id.fileDialog_deleteBtn);
        cancelBtn = view.findViewById(R.id.fileDialog_cancelBtn);

        fileNameTextView = view.findViewById(R.id.fileDialog_fileName);
        fileNameTextView.setText(fileNameText);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.openFragment(new Visualizer(dsType, vals), true);
                dismiss();
                Log.d("Dialog file action vals", vals.toString());
                Visualizer vis1 = new Visualizer(dsType, vals);
                MainActivity.openFragment(vis1, true);
               // vis1.arrayListInsert(vals);
//                try {
//                    Thread.sleep(10 * 1000);
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                }
//                vis1.autoPopulate();

            }
        });
        if(isDefault) {
            deleteBtn.setVisibility(View.INVISIBLE);
            renameBtn.setVisibility(View.INVISIBLE);
        }
        else {
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("final delete");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    DialogFileDelete deleteFileDialog = new DialogFileDelete();
                    deleteFileDialog.setFileNameText(fileNameText);
                    deleteFileDialog.show(ft, "final delete");
                    deleteFileDialog.setParentFrag(DialogFileAction.this);

                }
            });

            renameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("rename");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    DialogRename renameDialog = new DialogRename();
                    renameDialog.setParentFrag(DialogFileAction.this);
                    renameDialog.setOrigFileName(fileNameText);
                    renameDialog.show(ft, "rename");
                }
            });
        }
        return view;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//    }
}