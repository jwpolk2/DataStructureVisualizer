package com.example.datastructurevisualizer.ui;

import com.example.datastructurevisualizer.TreeVisualizer;
import android.content.Context;
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
import com.example.datastructurevisualizer.R;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class which inflates the view when the user presses the save button. Here the user can enter
 * a filename and save, or cancel the action.
 */
public class DialogSave extends DialogFragment {
    private EditText fileName;
    private Button saveBtn;
    private Button cancelBtn;
    private static String dataStructureType;
    private TreeVisualizer tree;

    /**
     * Sets the class data structure type stored with the saved file
     * @param dataStructureType
     */
    public static void setDataStructureType(String dataStructureType) {
        DialogSave.dataStructureType = dataStructureType;
    }

    /**
     * Sets the tree variable with the tree to be saved from the Visualizer page.
     * @param tree
     */
    public void setTree(TreeVisualizer tree) {
        this.tree = tree;
    }

    /**
     * Default constructor for this class.
     */
    public DialogSave() {   }

//    public static DialogSave newInstance(String title) {
//        DialogSave saveDialog = new DialogSave();
//        Bundle args = new Bundle();
//        args.putString("save",title);
//        saveDialog.setArguments(args);
//        return saveDialog;
//    }

    @Override
    /**
     * Creates the view for the save dialog and sets the actions for the buttons save and cancel.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_save, container);
        fileName = view.findViewById(R.id.saveDialog_fileName);
        saveBtn = view.findViewById(R.id.saveDialog_saveBtn);
        cancelBtn = view.findViewById(R.id.saveDialog_cancelBtn);

        //Sets the action for the save button
       saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User must enter a filename into the field
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

       //Pressing the cancel button dismisses the dialog
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    /**
     * Method called when the user presses save and has entered a filename into the text field
     * @return
     */
    public String save() {
        String saveMessage = "Saved Successfully";

        //get the context for storing files in correct directory
        Context context = getContext();
        JSONObject treeObj = new JSONObject();

        //get the date the save message was called
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        switch (dataStructureType) {
            case "Binary Search Tree":
                treeObj = tree.createJSON(dateStr, "Binary Search Tree");
                break;
            case "Red Black Tree":
                treeObj = tree.createJSON(dateStr, "Red Black Tree");
                break;
            case "AVL Tree":
                treeObj = tree.createJSON(dateStr, "AVL Tree");
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
            //Make sure not overwriting a saved file
            if(file.exists()){
                //if it already does, throw an error
                saveMessage = "File " + fileName.getText().toString() + " already exists. Please choose a different name.";
                return saveMessage;
            }
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