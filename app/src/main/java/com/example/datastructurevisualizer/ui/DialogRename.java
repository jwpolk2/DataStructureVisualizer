package com.example.datastructurevisualizer.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;;
import androidx.fragment.app.DialogFragment;
import com.example.datastructurevisualizer.R;
import java.io.File;

public class DialogRename extends DialogFragment {
    private EditText fileName;
    private Button renameConfirm;
    private Button cancelBtn;
    private String origFileName;
    private DialogFileAction parentFrag;


    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }

    public void setParentFrag(DialogFileAction parentFrag) {
        this.parentFrag = parentFrag;
    }





    public DialogRename() {

    }

    public static DialogRename newInstance(String title) {
        DialogRename renameDialog = new DialogRename();
        Bundle args = new Bundle();
        args.putString("save",title);
        renameDialog.setArguments(args);
        return renameDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rename_file, container);
        fileName = view.findViewById(R.id.renameEditText);
        renameConfirm = view.findViewById(R.id.renameConfirmBtn);
        cancelBtn = view.findViewById(R.id.renameCancelBtn);


        renameConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().length() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Please enter a valid file name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else {
                    String renameResult = rename();
                    if (renameResult != null && renameResult.equals("Renamed Successfully")) {
                        Toast toast = Toast.makeText(getActivity(), "File Renamed Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                        parentFrag.dismiss();
                        dismiss();
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "Error: " + renameResult, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    }

                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private String rename(){
        String renameResult = "Renamed Successfully";
        Context context = getContext();
        // Define the File Path and its Name
        try {
            //if file is the same name it previously was, just do nothing and return success message
            if(origFileName.equals(fileName.getText().toString())){
                return renameResult;
            }
            File oldFile = new File(context.getFilesDir(), origFileName);
            File newFile = new File(context.getFilesDir(), fileName.getText().toString());
            //Make sure not overwriting a saved file
            if(newFile.exists()){
                //if it already does, throw an error
                renameResult = "File " + fileName.getText().toString() + " already exists. Please choose a different name.";
                return renameResult;
            }
            boolean success = oldFile.renameTo(newFile);
            if(success){
                return renameResult;
            }
            else{
                return "Unable to rename file. Please restart app and try again";
            }
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return "Unexpected Exception. Please restart app and try again";
        }
    }


}