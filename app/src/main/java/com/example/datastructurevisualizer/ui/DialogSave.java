package com.example.datastructurevisualizer.ui;
import com.example.datastructurevisualizer.TreeVisualizer;
import com.example.datastructurevisualizer.ui.Visualizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.datastructurevisualizer.R;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogSave extends DialogFragment {
    private EditText fileName;
    private Button saveBtn;
    private Button cancelBtn;
    private Visualizer visualizer;

    public static void setDataStructureType(String dataStructureType) {
        DialogSave.dataStructureType = dataStructureType;
    }

    private static String dataStructureType;

    public void setTree(TreeVisualizer tree) {
        this.tree = tree;
    }

    private TreeVisualizer tree;

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

       saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().length() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Please enter a valid file name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
                else {
                    String saveResult = save();
                    if(saveResult!= null && saveResult.equals("Saved Successfully")){
                        Toast toast = Toast.makeText(getActivity(), "File Saved Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                        dismiss();
                    }
                    else{
                        Toast toast = Toast.makeText(getActivity(), "Error: " + saveResult, Toast.LENGTH_LONG);
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

    public String save() {
        String saveMessage = "Saved Successfully";

        //get the context for storing files in correct directory
        Context context = getContext();
        JSONObject treeObj = new JSONObject();

        //get the date the save message was called
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        switch (dataStructureType) {
            case "Binary Search Tree":
                treeObj = tree.createJSON(dateStr, "Binary Search Tree");
                break;
            case "Red Black Tree":
                treeObj = tree.createJSON(dateStr, "Red Black Tree");
                //TODO
                break;
            case "Balanced Search Tree":
                treeObj = tree.createJSON(dateStr, "Balanced Search Tree");
                //TODO
                break;
        }
        if(treeObj == null || treeObj.equals(null)){
            Log.i("Message", "Tried to save empty JSONObject");
            return "Cannot save empty tree";
        }
        // Convert JsonObject to String Format
        String userString = treeObj.toString();
        // Define the File Path and its Name
        try {
            //Write JSON format string into a file
            File file = new File(context.getFilesDir(), fileName.getText().toString());
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
            Log.i("Saved", userString);
            Log.i("Save Location", context.getFilesDir().toString());
            Log.i("FileName", fileName.getText().toString());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return "File IO Exception";
        }
        return saveMessage;
    }
}