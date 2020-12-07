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

/**
 * The Dialog for renaming a file
 */
public class DialogRename extends DialogFragment {
    private EditText fileName;
    private Button renameConfirm;
    private Button cancelBtn;
    private String origFileName;
    private DialogFileAction parentFrag;

    /**
     * Sets the String variable of the original filename with the files original name.
     * @param origFileName
     */
    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }

    /**
     * Sets the parentFrag of this Dialog to be returned to after action is complete.
     * @param parentFrag
     */
    public void setParentFrag(DialogFileAction parentFrag) {
        this.parentFrag = parentFrag;
    }

    /**
     * Default constructor for this class.
     */
    public DialogRename() {    }

//    public static DialogRename newInstance(String title) {
//        DialogRename renameDialog = new DialogRename();
//        Bundle args = new Bundle();
//        args.putString("save",title);
//        renameDialog.setArguments(args);
//        return renameDialog;
//    }

    @Override
    /**
     * Creates the view of the rename dialog
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rename_file, container);
        fileName = view.findViewById(R.id.renameEditText);
        renameConfirm = view.findViewById(R.id.renameConfirmBtn);
        cancelBtn = view.findViewById(R.id.renameCancelBtn);

        //Sets the action for when the rename button is pressed
        renameConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The user did not enter a new name for the file
                if (fileName.getText().length() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Please enter a valid file name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else {
                    String renameResult = rename();
                    //If the file is renamed successfully
                    if (renameResult != null && renameResult.equals("Renamed Successfully")) {
                        Toast toast = Toast.makeText(getActivity(), "File Renamed Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                        parentFrag.dismiss();
                        dismiss();
                    }
                    //If the file is not renamed successfully
                    else {
                        Toast toast = Toast.makeText(getActivity(), "Error: " + renameResult, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    }

                }

            }
        });

        //Action when the cancel button is pressed
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    /**
     * Method which renames the file when the rename button is pressed and the user has entered a string.
     * @return A message about the success or error encountered when renaming the file
     */
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