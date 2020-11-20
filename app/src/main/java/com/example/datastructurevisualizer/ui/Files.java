package com.example.datastructurevisualizer.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.app.Dialog;

import androidx.appcompat.app.AppCompatDialogFragment;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datastructurevisualizer.File;
import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


/**
 * A simple {//@link Fragment} subclass.
 * Use the {//@link Files#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Files extends Fragment {
    private RecyclerView fileRecyclerView;
    private FileAdapter fileAdapter;
    private RecyclerView.LayoutManager fileLayoutManager;
    private String dataStructureType;
    private ArrayList<File> mFile;

    public Files() {
        // Required empty public constructor
        dataStructureType = null;
    }

    public Files(String dataStructureType) {
        this.dataStructureType = dataStructureType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void openDialog() {
        DialogFragment dialogFileAction = new DialogFileAction();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        MainActivity.openDialog(dialogFileAction, "file_action", ft);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        java.io.File[] jsonFiles = extractFiles();
        mFile = tomFile(jsonFiles);

        ArrayList<Integer> unbalanced_BST = new ArrayList(Arrays.asList(2, 3, 4, 5, 1));
        ArrayList<Integer> balanced_BST = new ArrayList(Arrays.asList(4, 2, 1, 3, 5, 6));
        ArrayList<Integer> complete_BST = new ArrayList(Arrays.asList(4, 2, 3, 1, 6, 5));
        ArrayList<Integer> full_RBT = new ArrayList(Arrays.asList(2, 1, 4, 3, 6, 5, 7));
        ArrayList<Integer> perfect_BST = new ArrayList(Arrays.asList(5, 3, 4, 2, 7, 6, 8));

        mFile.add(new File("default_unbalancedBST", "Binary Search Tree", "n/a", unbalanced_BST, true));
        mFile.add(new File("default_balancedBST", "Binary Search Tree", "n/a", balanced_BST, true));
        mFile.add(new File("default_completeTree", "Binary Search Tree", "n/a", complete_BST, true));
        mFile.add(new File("default_fullTree", "AVL Tree", "n/a", full_RBT, true));
        mFile.add(new File("default_perfectTree", "Binary Search Tree", "n/a", perfect_BST, true));

        if (dataStructureType != null) {
            MainActivity.actionBar.setTitle(dataStructureType + " Files");
        if (mFile != null &&
                mFile.size() > 0) {
            for (int i = 0; i < mFile.size(); i++) {
                System.out.println(mFile.get(i).getFileName());
                if (mFile.get(i).getStructureType() == null ||
                        !mFile.get(i).getStructureType().equals(dataStructureType)) {
                    mFile.remove(mFile.get(i));
                    i--;
                }
            }
        }} else {
            MainActivity.actionBar.setTitle("Files");
        }


        final View view = inflater.inflate(R.layout.fragment_files, container, false);
        Context context = getContext();
        com.example.datastructurevisualizer.File file = new com.example.datastructurevisualizer.File();
        com.example.datastructurevisualizer.File file2 = new com.example.datastructurevisualizer.File();


        fileRecyclerView = view.findViewById(R.id.filesRecycle);
        fileRecyclerView.setHasFixedSize(true);
        fileLayoutManager = new LinearLayoutManager(getActivity());
        fileAdapter = new FileAdapter(mFile);

        fileRecyclerView.setLayoutManager(fileLayoutManager);
        fileRecyclerView.setAdapter(fileAdapter);

        fileAdapter.setOnItemClickListener(new FileAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("File Action");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFileAction fileActionDialog = new DialogFileAction();
                File dsFile = fileAdapter.getItem(position);
                fileActionDialog.setVals(dsFile.getValues());
                fileActionDialog.setDsType(dsFile.getStructureType());
                fileActionDialog.setFileNameText(dsFile.getFileName());
                fileActionDialog.setFilePosition(position);
                fileActionDialog.setIsDefault(mFile.get(position).getIsDefault());
                fileActionDialog.setParentFrag(Files.this);
                //fileActionDialog.setVals(fileAdapter);
//                saveDialog.setTree(tree);
//                saveDialog.setDataStructureType(dataStructureType);
                fileActionDialog.show(ft, "File Action");
            }
        });

        MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
        MainActivity.actionBar.show();
        return view;
    }

    /**
     * open up the files directory and place all saved data structure files into an array
     *
     * @return an array of saved files
     */
    public java.io.File[] extractFiles() {
        Context context = getContext();
        String path = context.getFilesDir().toString();
        Log.d("Files", "Path: " + path);
        java.io.File directory = new java.io.File(path);
        java.io.File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "FileName:" + files[i].getName());
        }
        return files;
    }

    public ArrayList<File> tomFile(java.io.File[] javaFiles) {
        ArrayList<File> mFile = new ArrayList<File>();
        for (int i = 0; i < javaFiles.length; i++) {
            String dsType = null;
            String dsDateModified = null;
            com.example.datastructurevisualizer.File file = new com.example.datastructurevisualizer.File();
            Log.d("Files", "FileName:" + javaFiles[i].getName());
            try {
                FileReader fileReader = new FileReader(javaFiles[i]);
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
                Log.d("JSON obj", obj.toString());

                //get json array of values from the object
                JSONArray array = obj.optJSONArray("Values");
                Log.d("Files", "FileName:" + javaFiles[i].getName());

                if (obj.optString("Type") != null) {
                    dsType = obj.getString("Type");
                } else {
                    Log.d("Null Files type", "FileName:" + javaFiles[i].getName());
                }
                if (obj.optString("Date Modified") != null) {
                    dsDateModified = obj.optString("Date Modified");
                } else {
                    Log.d("Null date", "FileName:" + javaFiles[i].getName());
                }

                // Deal with the case of a non-array value.
                if (array == null) {
                    return null;
                }
                //add all values to arraylist arr
                ArrayList<Integer> arr = new ArrayList<Integer>();
                for (int j = 0; j < array.length(); ++j) {
                    arr.add(array.optInt(j));
                }
                //set array to file
                file.setValues(arr);

                //set data structure type to file
                if (dsType != null) {
                    file.setStructureType(dsType);
                }

                //set date modified to file
                if (dsDateModified != null) {
                    file.setDate(dsDateModified);
                }

                //set filename to dsvisualizer file
                file.setFileName(javaFiles[i].getName());

            } catch (IOException | JSONException e) {
                Log.e("Exception", "File read failed: " + e.toString());
            }

            //now all fields of file are filled, add that to file arraylist
            mFile.add(file);

        }
        return mFile;
    }
}