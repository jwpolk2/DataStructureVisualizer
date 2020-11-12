package com.example.datastructurevisualizer.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.datastructurevisualizer.AVLTree;
import com.example.datastructurevisualizer.AnimationParameters;
import com.example.datastructurevisualizer.BinarySearchTree;
import com.example.datastructurevisualizer.Graph;
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

/**
 * Fragment class for the data structure visualization.
 */
public class Visualizer extends Fragment {

    //Tree Visualizer view object variables
    private EditText insertNumber;
    private ImageButton insertButton;
    private Button saveButton;
    private Button loadButton;
    private Button clearButton;
    private ImageButton undoButton;
    private ImageButton redoButton;
    private Button autopopulateButton;
    private Button saveDialogSave;

    //Graph Visuaizer view object variables
    private Button display;
    private Button button2;
    private EditText startNode;
    private EditText endNode;

    //Error Page view object variables
    private Button home;


    //Shared Visualizer view object variables
    private VisualizerCanvas visualizerCanvas;
    private ImageButton infoButton;
    private static TextView displayExec;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton previous;
    private ImageButton next;
    private Spinner traversalsSpinner;
    private static ScrollView displayExecScroll;

    //Class variables
    private static String dataStructureType;
    private TreeVisualizer tree;
    private Graph graph;
    private ArrayList<String> traversals;

    public Visualizer() {
        // Required empty public constructor
    }

    /**
     * Constucuter, sets the data structure type for the class.
     * @param dataStructureType
     */
    public Visualizer(String dataStructureType){

        this.dataStructureType = dataStructureType;
    }

    @Override
    /**
     * When onResume is called in the fragments lifecycle we want to make sure the canvas gets set
     * and the action bar is visible.
     */
    public void onResume() {
        super.onResume();
        MainActivity.setVisualizerCanvas(visualizerCanvas);
        MainActivity.actionBar.show();
    }

    @Override
    /**
     * When the view is created this method attaches the view object variable with those defined
     * in the XML. Additionally the onClick events for buttons are defined and remaining class
     * variables initialized. Returns the view created.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        
        switch(dataStructureType) {
            case "Binary Search Tree":
            case "Red Black Tree":
            case "Balanced Search Tree":
                view = inflater.inflate(R.layout.fragment_visualizer, container, false);
                initTreeVisualizer(view);
                //Initialize dataStructureType variable
                initDataStructure();
                //Initialize drop-down menu for traversal selection
                initTreeSpinner();
                break;
            case "Graph":
                view = inflater.inflate(R.layout.fragment_graph_visualizer, container, false);
                initGraphVisualizer(view);
                //Initialize dataStructureType variable
                initDataStructure();
                //Initialize drop-down menu for traversal selection
                initGraphSpinner();
                break;
            default:
                view = inflater.inflate(R.layout.error_page, container, false);
                home = view.findViewById(R.id.home_button);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Home(), false);
                    }
                });

        }
        // Inflate the layout for this fragment


        return view;
    }

    private void initGraphVisualizer(View view) {
        display = view.findViewById(R.id.display_button);
        button2 = view.findViewById(R.id.button2_button);
        displayExec = view.findViewById(R.id.printout_textview);
        visualizerCanvas = view.findViewById(R.id.graph_visualizer);
        visualizerCanvas.setParent(this);
        infoButton = view.findViewById(R.id.button_info);
        play = view.findViewById(R.id.button_play);
        pause = view.findViewById(R.id.button_pause);
        next = view.findViewById(R.id.button_next);
        previous = view.findViewById(R.id.button_previous);
        traversalsSpinner = view.findViewById(R.id.spinner_traversal);
        startNode = view.findViewById(R.id.start_value);
        endNode = view.findViewById(R.id.end_value);
        //TODO remove when graph object is no longer needed in this method
        graph = new Graph();

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage(dataStructureType), true);
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCanvas();
                Toast.makeText(getContext(),"Display Button Pressed", Toast.LENGTH_LONG)
                        .show();

                // TODO temp graph render
                graph.render();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCanvas();
                Toast.makeText(getContext(), "Button 2 Pressed", Toast.LENGTH_LONG)
                        .show();
                java.util.Random rand = new java.util.Random();

                // TODO some functionality
                graph.breadthFirstPathfind(1, 20);

            }
        });

        // TODO remove
        // Inserts some Nodes and edges if the graph is empty.
        int k = 0;
        if (graph.getAllNodes().isEmpty()) {
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 5; ++j) {
                    graph.insertGraphNode(k,100 + j * 200, 100 + i * 200);
                    if (k != 0) graph.insertDirectedEdge(k - 1, k, 10);
                    ++k;

                }
            }
        }
        graph.render();
    }

    private void initTreeVisualizer(View view) {
        //Connect variables to XML
        loadButton = view.findViewById(R.id.button_load);
        saveButton = view.findViewById(R.id.button_save);
        insertNumber = view.findViewById(R.id.input_nodes);
        clearButton = view.findViewById(R.id.button_clear);
        insertButton = view.findViewById(R.id.button_insert);
        undoButton = view.findViewById(R.id.button_undo);
        redoButton = view.findViewById(R.id.button_redo);
        autopopulateButton = view.findViewById((R.id.button_autopopulate));
        displayExec = view.findViewById(R.id.printout_textview);
        displayExecScroll = view.findViewById(R.id.printout_scroll);
        traversalsSpinner = view.findViewById(R.id.spinner_traversal);
        play = view.findViewById(R.id.button_play);
        pause = view.findViewById(R.id.button_pause);
        next = view.findViewById(R.id.button_next);
        previous = view.findViewById(R.id.button_previous);
        saveDialogSave = view.findViewById(R.id.saveDialog_saveBtn);
        infoButton = (ImageButton) view.findViewById(R.id.button_info);
        visualizerCanvas = view.findViewById(R.id.view_visualizer);
        visualizerCanvas.setParent(this);


        //BUTTON ON CLICK
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.openFragment(new InformationPage(dataStructureType),true);
            }
        });
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

                DialogSave saveDialog = new DialogSave();
                saveDialog.setTree(tree);
                saveDialog.setDataStructureType(dataStructureType);
                saveDialog.show(ft, "save");
//                saveDialogSave.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        save();
//                    }
//                });
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity.openFragment(new Files(), true);
//                Fragment filesFrag = new Files();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.visualizer_fragment, filesFrag);
//                ft.addToBackStack(null);
//                ft.commit();


            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
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
    }

    /**
     * Clears the canvas and resets the class tree object.
     */
    private void clear() {
        tree.clear();
        visualizerCanvas.clearCanvas();
        checkCanvas();
        displayExec.setText("");
    }


    /**
     * Initializes the drop-down menu used for the tree-traversals
     */
    private void initTreeSpinner() {
        //Array List of the drop-down items
        traversals = new ArrayList<>();
        traversals.add("Select Traversal");
        traversals.add("In-Order");
        traversals.add("Post-Order");
        traversals.add("Pre-Order");
        traversals.add("Breadth-First");
        traversals.add("Value Search");

        //Attaches an adapter to the array list and then to the spinner object
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, traversals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        traversalsSpinner.setAdapter(adapter);

        //Sets up what happens when the different options are selected
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
                        tree.search(10); // TODO this should be modifiable
                        break;
                    case "Breadth-First":
                        checkCanvas();
                        tree.breadthFirstTraversal();
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

    /**
     * Initializes the drop-down menu used for the graph-traversals
     */
    private void initGraphSpinner() {
        //Array List of the drop-down items
        traversals = new ArrayList<>();
        traversals.add("Select Traversal");
        traversals.add("Prim's MST");
        traversals.add("Dijkstra's Shortest Path");
        traversals.add("Kruskal's MST");
        traversals.add("Breadth-First Path");

        //Attaches an adapter to the array list and then to the spinner object
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, traversals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        traversalsSpinner.setAdapter(adapter);

        //Sets up what happens when the different options are selected
        traversalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(parent.getItemAtPosition(position).toString()) {
                    case "Prim's MST":
                        displayMessage("Prim's Minimum Spanning Tree selected.");
                        displayMessage("Please enter a start node.");
                        startNode.setVisibility(View.VISIBLE);
                        endNode.setVisibility(View.INVISIBLE);
                        break;
                    case "Dijkstra's Shortest Path":
                        displayMessage("Dijkstra's Shortest Path selected.");
                        displayMessage("Please enter a start node and end node");
                        startNode.setVisibility(View.VISIBLE);
                        endNode.setVisibility(View.VISIBLE);
                        break;
                    case "Kruskal's MST":
                        displayMessage("Kruskal's Minimum Spanning Tree selected.");
                        startNode.setVisibility(View.INVISIBLE);
                        endNode.setVisibility(View.INVISIBLE);
                        break;
                    case "Breadth-First Path":
                        displayMessage("Breadth-First Path selected.");
                        displayMessage("Please enter a start node.");
                        startNode.setVisibility(View.VISIBLE);
                        endNode.setVisibility(View.INVISIBLE);
                        break;

                    case "Select Traversal":
                        startNode.setVisibility(View.INVISIBLE);
                        endNode.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Undoes the previous action and resets the canvas to it's previous state.
     */
    private void undo() {
        tree.undo();
    }

    /**
     * Redoes an action previously undone. This resets the canvas to the previously state which
     * was undone.
     */
    private void redo() {
        tree.redo();
    }


    /**
     * Initializes the data structure that is going to be animated.
     */
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
                Log.d("ACTION BAR", "In Balanced Search Tree");
                MainActivity.actionBar.setTitle("AVL Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                tree = new AVLTree();
                break;
            case "Graph":
                Log.d("ACTION BAR", "In Graph");
                MainActivity.actionBar.setTitle("Graph");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                //graph = new Graph(); TODO uncomment when graph no longer initizalized in graphVisualizer
                break;
        }
    }

    /**
     * This method is called when the insert button is pressed. It adds the value to the tree
     * and resets the text on the insert line.
     */
    private void insert() {
        checkCanvas();
        initTreeSpinner();
        tree.insert(Integer.parseInt(String.valueOf(insertNumber.getText().toString())));
        insertNumber.setText("");
    }

    /**
     * This method is called when the auto button is pressed. It auto-populates the data structure.
     */
    private void autoPopulate(){
        checkCanvas();
        int[] array = {50, 30, 70, 20, 80, 60, 20, 40, 90};
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i< array.length; i++) arr.add(array[i]);
        tree.insert(arr);
    }


    /**
     * This method sets the canvas bitmap.
     * @param bitmap
     */
    public void setCanvas(Bitmap bitmap) {
        visualizerCanvas.setBackground(new BitmapDrawable(bitmap));
    }

    /**
     * This method is used to check if the canvas has been initialized. If not it creates a canvas.
     */
    public void checkCanvas() {
        if (visualizerCanvas.canvas == null) {
            int vHeight = visualizerCanvas.getHeight();
            int vWidth = visualizerCanvas.getWidth();
            visualizerCanvas.setDimensions(vHeight, vWidth);
        }
    }


    /**
     * This method is called when save is pressed, it opens up the save dialog which handles
     * how the file is named and saved.
     */
    public void save() {
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
                treeObj = tree.createJSON("file 1", "AVL Tree");
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

    /**
     * This method is called when the load button is pressed.
     */
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
        final String message2 = message;
        displayExec.post(new Runnable() {
            @Override
            public void run() {
                String hMessage = displayExec.getText().toString();
                displayExec.setText(String.format("%s\n%s", hMessage, message2));
                displayExecScroll.scrollTo(0,displayExecScroll.getBottom());
            }
        });
    }
}