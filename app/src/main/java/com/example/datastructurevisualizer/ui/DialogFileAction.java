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

/**
 * Creates the dialog which is used when a file is clicked on the files page. This dialog will
 * allow a user to rename a file, load a file, delete a file, or cancel the dialog.
 */
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

    /**
     * Sets the filename for the Dialog
     * @param fileNameText
     */
    public void setFileNameText(String fileNameText) {
        this.fileNameText = fileNameText;
    }

    /**
     * Sets the parent fragment for the Dialog
     * @param parentFrag
     */
    public void setParentFrag(Files parentFrag) {
        this.parentFrag = parentFrag;
    }

    /**
     * Sets the file position in the recycler view on the files page for the Dialog
     * @param filePosition
     */
    public void setFilePosition(int filePosition) {
        this.filePosition = filePosition;
    }

    /**
     * Sets the node values in the file for the Dialog
     * @param vals
     */
    public void setVals(ArrayList<Integer> vals) {
        this.vals = vals;
    }

    /**
     * Sets the data structure type of the file for the Dialog
     * @param dsType
     */
    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    /**
     * Sets the boolean for whether the Dialog is opening a default file or not.
     * If true the rename and delete buttons will be hidden from the dialog.
     * @param isDefault
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Default constructor
     */
    public DialogFileAction() {}

//    public static DialogFileAction newInstance(String title) {
//        DialogFileAction fileActionDialog = new DialogFileAction();
//        Bundle args = new Bundle();
//        args.putString("save",title);
//        fileActionDialog.setArguments(args);
//        return fileActionDialog;
//    }

    @Override
    /**
     * This method determines what happens when the dialog is dismissed by the user. Return to the
     * parent file fragment.
     */
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        // Reload current fragment
        Fragment frg = parentFrag;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    /**
     * Creates and returns the dialog view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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
        //If the file is a default file hide the rename and delete buttons
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
}