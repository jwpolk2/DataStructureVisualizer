package com.example.datastructurevisualizer.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.datastructurevisualizer.AVLTree;
import com.example.datastructurevisualizer.AnimationParameters;
import com.example.datastructurevisualizer.BinarySearchTree;
import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.RedBlackTree;
import com.example.datastructurevisualizer.TreeVisualizer;
import com.example.datastructurevisualizer.VisualizerCanvas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Visualizer extends Fragment {

    private EditText insertNumber;
    private ImageButton insertButton;
    private Button saveButton;
    private Button loadButton;
    private ImageButton undoButton;
    private ImageButton redoButton;
    private Button autopopulateButton;
    private ImageButton infoButton;
    private ImageButton homeButton;
    private static String dataStructureType;
    private TextView dataStructureHeader;
    private static TextView displayExec;
    private VisualizerCanvas visualizerCanvas;
    private TreeVisualizer tree;
    private ArrayList<String> traversals;
    private Spinner traversalsSpinner;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton previous;
    private ImageButton next;


    public Visualizer() {
        // Required empty public constructor
    }

    public Visualizer(String dataStructureType){
        this.dataStructureType = dataStructureType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visualizer, container, false);

        loadButton = view.findViewById(R.id.button_load);
        saveButton = view.findViewById(R.id.button_save);
        insertNumber = view.findViewById(R.id.input_nodes);

        insertButton = view.findViewById(R.id.button_insert);
        undoButton = view.findViewById(R.id.button_undo);
        redoButton = view.findViewById(R.id.button_redo);
        autopopulateButton = view.findViewById((R.id.button_autopopulate));
        displayExec = view.findViewById(R.id.printout_textview);
        //TRAVERSAL
        traversalsSpinner = view.findViewById(R.id.spinner_traversal);
        play = view.findViewById(R.id.button_play);
        pause = view.findViewById(R.id.button_pause);
        next = view.findViewById(R.id.button_next);
        previous = view.findViewById(R.id.button_previous);
        //infoButton = (ImageButton) view.findViewById(R.id.button_info);
       // homeButton = (ImageButton) view.findViewById(R.id.button_home);

//        infoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AboutPage aboutFragment = new AboutPage();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, aboutFragment);
//                transaction.commit();
//            }
//        });


//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Home homeFragment = new Home();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, homeFragment);
//                transaction.commit();
//            }
//        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertNumber.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please insert a value", Toast.LENGTH_SHORT).show();

                }
                else {
                    insert();
                }

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("save");
                if (prev != null) {
                    ft.remove(prev);
                } ft.addToBackStack(null);

                DialogFragment saveDialog = new DialogSave();
                saveDialog.show(ft, "save");
                //TODO: set conditional for save() method from dialog window, alertdialog.builder?
                save();
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        autopopulateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoPopulate();
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });
        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redo();
            }
        });


       // dataStructureHeader = view.findViewById(R.id.visualizerHeader);
        visualizerCanvas = view.findViewById(R.id.view_visualizer);
        visualizerCanvas.setParent(this);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tree.animationPause();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tree.animationNext();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tree.animationPrev();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable () {
                    @Override
                    public void run() {
                        AnimationParameters.beginAnimation();
                        tree.animate();
                        AnimationParameters.stopAnimation();
                    }
                }).start();
            }
        });

        MainActivity.setVisualizerCanvas(visualizerCanvas);
        MainActivity.actionBar.show();
        initDataStructure();
        initSpinner();
        return view;
    }

    private void initSpinner() {
        traversals = new ArrayList<>();
        traversals.add("Select Traversal");
        traversals.add("In-Order");
        traversals.add("Post-Order");
        traversals.add("Pre-Order");
        traversals.add("Value Search");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, traversals);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;

        traversalsSpinner.setAdapter(adapter);

        traversalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(parent.getItemAtPosition(position).toString()) {
                    case "In-Order":
                        checkCanvas();
                        tree.inOrderTraversal();
                        break;
                    case "Post-Order":
                        checkCanvas();
                        tree.postOrderTraversal();
                        break;
                    case "Pre-Order":
                        checkCanvas();
                        tree.preOrderTraversal();
                        break;
                    case "Value Search":
                        checkCanvas();
                        tree.search(10);
                        break;
                    case "Select Traversal":
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void undo() {
        tree.undo();
    }

    private void redo() {
        tree.redo();
    }


    private void initDataStructure() {
        switch (dataStructureType) {
            case "Binary Search Tree":
                tree = new BinarySearchTree();
                MainActivity.actionBar.setTitle("Binary Search Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case "Red Black Tree":
                MainActivity.actionBar.setTitle("Red Black Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                tree = new RedBlackTree();
                break;
            case "Balanced Search Tree":
                MainActivity.actionBar.setTitle("Balanced Search Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                tree = new AVLTree();
                break;
        }
    }

    private void insert() {
        checkCanvas();
        tree.insert(Integer.parseInt(String.valueOf(insertNumber.getText().toString())));
        insertNumber.setText("");
    }

    private void autoPopulate(){
        if (visualizerCanvas.canvas == null) {
            int vHeight = visualizerCanvas.getHeight();
            int vWidth = visualizerCanvas.getWidth();
            visualizerCanvas.setDimensions(vHeight, vWidth);
        }
        int[] array = {50, 30, 70, 20, 80, 60, 20, 40, 90};
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i< array.length; i++) arr.add(array[i]);
        tree.insert(arr);

    }


    public void setCanvas(Bitmap bitmap) {
        visualizerCanvas.setBackground(new BitmapDrawable(bitmap));
    }

    public void checkCanvas() {
        if (visualizerCanvas.canvas == null) {
            int vHeight = 0;
            int vWidth = 0;
            visualizerCanvas.setDimensions(vHeight, vWidth);
        }
    }

    private void save() {
        Context context = getContext();
        JSONObject treeObj = new JSONObject();

        switch (dataStructureType) {

            case "Binary Search Tree":
                treeObj = tree.createJSON("file 1", "Binary Search Tree");
                break;
            case "Red Black Tree":
                treeObj = tree.createJSON("file 1", "Red Black Tree");
                //TODO
                break;
            case "Balanced Search Tree":
                treeObj = tree.createJSON("file 1", "Balanced Search Tree");
               //TODO
                break;
        }
        if(treeObj == null || treeObj.equals(null)){
            Log.i("Message", "Tried to save empty JSONObject");
            return;
        }
        // Convert JsonObject to String Format
        String userString = treeObj.toString();
        // Define the File Path and its Name
        try {
            //Write JSON format string into a file
            File file = new File(context.getFilesDir(), "fileExample");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
            Log.i("Saved", userString);
            Log.i("Save Location", context.getFilesDir().toString());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    private void load() {
        MainActivity.openFragment(new Files(), true);
//        Context context = getContext();
//        try {
//            //read from file into a JSON format string
//            Log.i("Read from", context.getFilesDir().toString());
//            File file = new File(context.getFilesDir(), "fileExample");
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            StringBuilder stringBuilder = new StringBuilder();
//            String line = bufferedReader.readLine();
//            while (line != null) {
//                stringBuilder.append(line).append("\n");
//                line = bufferedReader.readLine();
//            }
//            bufferedReader.close();
//            // This response will have Json Format String
//            String response = stringBuilder.toString();
//
//            //parse string to JSONObject
//            JSONObject obj = new JSONObject(response);
//
//            //get json array of values from the object
//            JSONArray array = obj.optJSONArray("Values");
//
//            // Deal with the case of a non-array value.
//            if (array == null) {
//                return;
//            }
//
//            //remove all the nodes from the tree if there are any so the new ones can be loaded in
//            tree.clear();
//            if (visualizerCanvas.canvas == null) {
//                int vHeight = visualizerCanvas.getHeight();
//                int vWidth = visualizerCanvas.getWidth();
//                visualizerCanvas.setDimensions(vHeight, vWidth);
//            }
//
//            // Insert numbers from JSONArray into the tree.
//            ArrayList<Integer> arr = new ArrayList<Integer>();
//            for (int i = 0; i < array.length(); ++i) arr.add(array.optInt(i));
//            tree.insert(arr);
//
//        }
//        catch(IOException | JSONException e){
//            Log.e("Exception", "File read failed: " + e.toString());
//        }
    }

    /**
     * Displays a message in the displayExec text.
     *
     * @param message the message to be displayed.
     */
    public static void displayMessage(String message) {
        displayExec.setText(message);

    }
}