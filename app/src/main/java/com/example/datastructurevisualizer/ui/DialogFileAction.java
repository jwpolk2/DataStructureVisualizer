package com.example.datastructurevisualizer.ui;
import android.content.Context;
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

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.TreeVisualizer;
import com.example.datastructurevisualizer.VisualizerCanvas;
import com.example.datastructurevisualizer.MainActivity;

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
    private TextView fileName;
    private Button renameBtn;
    private Button loadBtn;
    private Button deleteBtn;
    private Button cancelBtn;
    private String mFile;

    public void setTree(TreeVisualizer tree) {
        this.tree = tree;
    }

    private TreeVisualizer tree;

    public void setVisualizerCanvas(VisualizerCanvas visualizerCanvas) {
        this.visualizerCanvas = visualizerCanvas;
    }
    private VisualizerCanvas visualizerCanvas;
    public DialogFileAction() {

    }

    public static DialogFileAction newInstance(String file) {
        DialogFileAction fileActionDialog = new DialogFileAction();
        Bundle args = new Bundle();
        args.putString("file",file);
        fileActionDialog.setArguments(args);
        return fileActionDialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFile = getArguments().getString("file");
        View view = inflater.inflate(R.layout.dialog_file_action, container);
        fileName = view.findViewById(R.id.fileDialog_fileName);
        fileName.setText(mFile);
        renameBtn = view.findViewById(R.id.fileDialog_renameBtn);
        loadBtn = view.findViewById(R.id.fileDialog_loadBtn);
        deleteBtn = view.findViewById(R.id.fileDialog_deleteBtn);
        cancelBtn = view.findViewById(R.id.fileDialog_cancelBtn);

        renameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                try {
                    //read from file into a JSON format string
                    Log.i("Read from", context.getFilesDir().toString());
                    File file = new File(context.getFilesDir(), mFile);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append("\n");
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    // This response will have Json Format String
                    String response = stringBuilder.toString();

                    //parse string to JSONObject
                    JSONObject obj = new JSONObject(response);

                    //get json array of values from the object
                    JSONArray array = obj.optJSONArray("Values");

                    // Deal with the case of a non-array value.
                    if (array == null) {
                        return;
                    }

                    //remove all the nodes from the tree if there are any so the new ones can be loaded in
                    tree.clear();
                    if (visualizerCanvas.canvas == null) {
                        int vHeight = visualizerCanvas.getHeight();
                        int vWidth = visualizerCanvas.getWidth();
                        visualizerCanvas.setDimensions(vHeight, vWidth);
                    }

                    // Insert numbers from JSONArray into the tree.
                    ArrayList<Integer> arr = new ArrayList<Integer>();
                    for (int i = 0; i < array.length(); ++i) arr.add(array.optInt(i));
                    tree.insert(arr);

                }
                catch(IOException | JSONException e){
                    Log.e("Exception", "File read failed: " + e.toString());
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//    }
}