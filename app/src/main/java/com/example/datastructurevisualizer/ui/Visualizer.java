package com.example.datastructurevisualizer.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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

import java.util.ArrayList;

/**
 * Fragment class for the data structure visualization.
 */
public class Visualizer extends Fragment {

    //Tree Visualizer view object variables
    private EditText insertNumber;
    private ImageButton insertButton;
    private ImageButton undoButton;
    private ImageButton redoButton;
    private Spinner treesSpinner;

    //Graph Visualizer view object variables
    private EditText startNode;
    private EditText endNode;
    private Spinner graphOptions;

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
    private Button saveButton;
    private Button loadButton;
    private Button clearButton;

    //Class variables
    private static String dataStructureType;
    public static TreeVisualizer tree;
    private Graph graph;
    private ArrayList<String> traversals;
    private ArrayList<String> graphs;
    private ArrayList<String> trees;

    private ArrayList<Integer> loadedFile;


    public Visualizer() {
        // Required empty public constructor
    }

    /**
     * Constucuter, sets the data structure type for the class.
     *
     * @param dataStructureType
     */
    public Visualizer(String dataStructureType){
        this.dataStructureType = dataStructureType;
        this.loadedFile = null;
    }

    /**
     * Constucuter, sets the data structure type for the class.
     * @param dataStructureType
     */
    public Visualizer(String dataStructureType,  ArrayList<Integer> values){

        this.dataStructureType = dataStructureType;
        this.loadedFile = values;
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

        switch (dataStructureType) {
            case "Binary Search Tree":
            case "Red Black Tree":
            case "AVL Tree":
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
        loadButton = view.findViewById(R.id.button_load);
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
        displayExecScroll = view.findViewById(R.id.printout_scroll);
        graphOptions = view.findViewById(R.id.spinner_graphOptions);
        visualizerCanvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                visualizerCanvas.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                checkCanvas();
            }
        });
        visualizerCanvas.setParent(this);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new InformationPage(dataStructureType), true);
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graph != null && graph.getAllNodes().size() > 0) {
                    graph.render();
                } else {
                    Toast.makeText(getContext(), "Please select a graph from the drop-down list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.animationPause();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.animationNext();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.animationPrev();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AnimationParameters.beginAnimation();
                        graph.animate();
                        AnimationParameters.stopAnimation();
                    }
                }).start();
            }
        });
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
        displayExec = view.findViewById(R.id.printout_textview);
        displayExecScroll = view.findViewById(R.id.printout_scroll);
        traversalsSpinner = view.findViewById(R.id.spinner_traversal);
        treesSpinner = view.findViewById(R.id.spinner_tree_options);
        play = view.findViewById(R.id.button_play);
        pause = view.findViewById(R.id.button_pause);
        next = view.findViewById(R.id.button_next);
        previous = view.findViewById(R.id.button_previous);
        infoButton = (ImageButton) view.findViewById(R.id.button_info);
        visualizerCanvas = view.findViewById(R.id.view_visualizer);
        visualizerCanvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                visualizerCanvas.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                checkCanvas();
                if (loadedFile != null && !loadedFile.isEmpty()) {
                    arrayListInsert(loadedFile);
                }
            }
        });
        visualizerCanvas.setParent(this);

        //BUTTON ON CLICK
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.openFragment(new InformationPage(dataStructureType), true);
            }
        });
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertNumber.getText().length() == 0 || Integer.parseInt(insertNumber.getText().toString()) > 999) {
                    Toast.makeText(getActivity(), "Please insert a value between 0-999", Toast.LENGTH_SHORT).show();

                } else {
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
                }
                ft.addToBackStack(null);

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
                MainActivity.openFragment(new Files(dataStructureType), true);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AnimationParameters.beginAnimation();
                        tree.animate();
                        AnimationParameters.stopAnimation();
                    }
                }).start();
            }
        });

        visualizerCanvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int node = tree.getClickedNode(x, y);
                if (node != -1) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("node_action");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    DialogNodeAction nodeAction = DialogNodeAction.newInstance(tree, node);
                    nodeAction.show(ft, "node_action");
                }
                return false;
            }
        });

    }

    /**
     * Clears the canvas and resets the class tree object.
     */
    private void clear() {
        if (tree != null) {
            tree.clear();
        }
        if (graph != null) {
            graph.clear();
        }
        if (visualizerCanvas != null) {
            visualizerCanvas.clearCanvas();
            checkCanvas();
            displayExec.setText("");
        }
    }


    /**
     * Initializes the drop-down menu used for the tree-traversals
     */
    private void initTreeSpinner() {
        //ArrayList of the tree drop-down items
        trees = new ArrayList<>();
        trees.add("Select Insertion");
        trees.add("Balanced Insertion");
        trees.add("Unbalanced Insertion");
        trees.add("Auto Insertion");

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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        traversalsSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapterTrees = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, trees);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        treesSpinner.setAdapter(adapterTrees);

        //Sets up what happens when the different options are selected
        traversalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getItemAtPosition(position).toString()) {
                    case "In-Order":
                        displayMessage("In-Order Traversal selected. Press play to begin animation.");
                        tree.inOrderTraversal();
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AnimationParameters.beginAnimation();
                                        tree.animate();
                                        AnimationParameters.stopAnimation();
                                    }
                                }).start();
                            }
                        });
                        break;
                    case "Post-Order":
                        displayMessage("Post-Order Traversal selected. Press play to begin animation.");
                        tree.postOrderTraversal();
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AnimationParameters.beginAnimation();
                                        tree.animate();
                                        AnimationParameters.stopAnimation();
                                    }
                                }).start();
                            }
                        });
                        break;
                    case "Pre-Order":
                        displayMessage("Pre-Order Traversal selected. Press play to begin animation.");
                        tree.preOrderTraversal();
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AnimationParameters.beginAnimation();
                                        tree.animate();
                                        AnimationParameters.stopAnimation();
                                    }
                                }).start();
                            }
                        });
                        break;
                    case "Value Search":
                        displayMessage("Value Search Selected. Please enter a value and press play.");
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(insertNumber != null && insertNumber.getText().toString().length() != 0) {
                                    int searchNum = Integer.parseInt(insertNumber.getText().toString());
                                    tree.search(searchNum);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AnimationParameters.beginAnimation();
                                            tree.animate();
                                            AnimationParameters.stopAnimation();
                                        }
                                    }).start();
                                }
                            }
                        });
                        break;
                    case "Breadth-First":
                        displayMessage("Breadth-First Traversal selected. Press play to begin animation.");
                        tree.breadthFirstTraversal();
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AnimationParameters.beginAnimation();
                                        tree.animate();
                                        AnimationParameters.stopAnimation();
                                    }
                                }).start();
                            }
                        });
                        break;
                    case "Select Traversal":
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        treesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int inserted = 0;
                ArrayList<Integer> toInsert = new ArrayList();
                java.util.Random rand = new java.util.Random();
                switch (parent.getItemAtPosition(position).toString()) {
                    case "Select Insertion":
                        break;
                    case "Balanced Insertion":
                        displayMessage("Balanced Tree insertion selected");
                        clear();
                            while (inserted < 15) {
                                int insertValue = rand.nextInt(67) * (inserted + 1);
                                if (!tree.getAllKeys().contains(insertValue) && insertValue < 999) {
                                    toInsert.add(insertValue);
                                    inserted++;
                                }
                            }
                            tree.insert(toInsert);

                        break;
                    case "Unbalanced Insertion":
                        clear();
                        displayMessage("Unbalanced Tree insertion selected");
                            while (inserted < 7) {
                                int insertValue = rand.nextInt(150) * (inserted + 1);
                                if (!tree.getAllKeys().contains(insertValue) && insertValue < 999) {
                                    toInsert.add(insertValue);
                                    inserted++;
                                }
                            }
                            tree.insert(toInsert);
                        break;
                    case "Auto Insertion":
                        clear();
                            toInsert.add(rand.nextInt(50) + 475);
                            while (inserted < 15) {
                                int insertValue = rand.nextInt(1000);
                                if (!tree.getAllKeys().contains(insertValue)) {
                                    toInsert.add(insertValue);
                                    inserted++;
                                }
                            }
                            tree.insert(toInsert);
                        displayMessage("Auto Tree selected");
                        break;
                    default:
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
        //Array List of the drop-down graph options
        graphs = new ArrayList<>();
        graphs.add("Select Graph");
        graphs.add("Directed Arbitrary");
        graphs.add("Directed Cyclical");
        graphs.add("Directed Forest");
        graphs.add("Undirected Arbitrary");
        graphs.add("Undirected Cyclical");
        graphs.add("Undirected Forest");
        graphs.add("Mixed Graph");

        //Array List of the drop-down items
        traversals = new ArrayList<>();
        traversals.add("Select Traversal");
        traversals.add("Prim's MST");
        traversals.add("Dijkstra's Shortest Path");
        traversals.add("Kruskal's MST");
        traversals.add("Breadth-First Path");

        //Attaches an adapter to the array list and then to the spinner object
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, traversals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        traversalsSpinner.setAdapter(adapter);

        //Attaches an adapter to the array list for
        ArrayAdapter<String> adapterGraphOptions = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, graphs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        graphOptions.setAdapter(adapterGraphOptions);

        //Sets up what happens when the different options are selected
        traversalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getItemAtPosition(position).toString()) {
                    case "Prim's MST":
                        displayMessage("Prim's Minimum Spanning Tree selected.");
                        displayMessage("Please enter a start node.");
                        startNode.setVisibility(View.VISIBLE);
                        endNode.setVisibility(View.INVISIBLE);

                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int start;
                                if (graph != null && !graph.getAllNodes().isEmpty()) {
                                    if (startNode != null && startNode.getText().toString().length() != 0) {
                                        start = Integer.parseInt(startNode.getText().toString());
                                        if (graph.getKeys().contains(start)) {
                                            graph.primsAlgorithm(start);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    AnimationParameters.beginAnimation();
                                                    graph.animate();
                                                    AnimationParameters.stopAnimation();
                                                }
                                            }).start();
                                        } else {
                                            Toast.makeText(getContext(), "Please enter a different start key", Toast.LENGTH_SHORT).show();
                                            displayMessage(String.format("The value %d does not exist in the graph", start));
                                            displayMessage("Please enter a different value");
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Please enter start value", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getContext(), "Please select a graph from the dropdown", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case "Dijkstra's Shortest Path":
                        displayMessage("Dijkstra's Shortest Path selected.");
                        displayMessage("Please enter a start node and end node");
                        startNode.setVisibility(View.VISIBLE);
                        endNode.setVisibility(View.VISIBLE);
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int start;
                                int end;
                                if (graph != null && !graph.getAllNodes().isEmpty()) {
                                    if (startNode != null && startNode.getText().toString().length() != 0) {
                                        start = Integer.parseInt(startNode.getText().toString());
                                        if (endNode != null && endNode.getText().toString().length() != 0) {
                                            end = Integer.parseInt(endNode.getText().toString());
                                            if (graph.getKeys().contains(start)) {
                                                if (graph.getKeys().contains(end)) {
                                                    graph.dijkstraPathfind(start, end);
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            AnimationParameters.beginAnimation();
                                                            graph.animate();
                                                            AnimationParameters.stopAnimation();
                                                        }
                                                    }).start();
                                                } else {
                                                    Toast.makeText(getContext(), "Please enter a different end key", Toast.LENGTH_SHORT).show();
                                                    displayMessage(String.format("The value %d does not exist in the graph", end));
                                                    displayMessage("Please enter a different value");
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Please enter a different start key", Toast.LENGTH_SHORT).show();
                                                displayMessage(String.format("The value %d does not exist in the graph", start));
                                                displayMessage("Please enter a different value");
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Please enter end value", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Please enter start value", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getContext(), "Please select a graph from the dropdown", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case "Kruskal's MST":
                        displayMessage("Kruskal's Minimum Spanning Tree selected.");
                        startNode.setVisibility(View.INVISIBLE);
                        endNode.setVisibility(View.INVISIBLE);
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (graph != null && !graph.getAllNodes().isEmpty()) {
                                    graph.kruskalsAlgorithm();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AnimationParameters.beginAnimation();
                                            graph.animate();
                                            AnimationParameters.stopAnimation();
                                        }
                                    }).start();
                                } else {
                                    Toast.makeText(getContext(), "Please select a graph from the dropdown", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case "Breadth-First Path":
                        displayMessage("Breadth-First Path selected.");
                        displayMessage("Please enter a start node.");
                        startNode.setVisibility(View.VISIBLE);
                        endNode.setVisibility(View.VISIBLE);
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int start;
                                int end;
                                if (graph != null && !graph.getAllNodes().isEmpty()) {
                                    if (startNode != null && startNode.getText().toString().length() != 0) {
                                        start = Integer.parseInt(startNode.getText().toString());
                                        if (endNode != null && endNode.getText().toString().length() != 0) {
                                            end = Integer.parseInt(endNode.getText().toString());
                                            if (graph.getKeys().contains(start)) {
                                                if (graph.getKeys().contains(end)) {
                                                    graph.breadthFirstPathfind(start,end);
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            AnimationParameters.beginAnimation();
                                                            graph.animate();
                                                            AnimationParameters.stopAnimation();
                                                        }
                                                    }).start();
                                                } else {
                                                    Toast.makeText(getContext(), "Please enter a different end key", Toast.LENGTH_SHORT).show();
                                                    displayMessage(String.format("The value %d does not exist in the graph", end));
                                                    displayMessage("Please enter a different value");
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Please enter a different start key", Toast.LENGTH_SHORT).show();
                                                displayMessage(String.format("The value %d does not exist in the graph", start));
                                                displayMessage("Please enter a different value");
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Please enter end value", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Please enter start value", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getContext(), "Please select a graph from the dropdown", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

        //Sets up what happens when the different options are selected
        graphOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()) {
                    case "Directed Arbitrary":
                        clear();
                        displayMessage("Directed Arbitrary graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        java.util.Random rand = new java.util.Random();
                        int k = 0;
                        if (graph.getAllNodes().isEmpty()) {
                            for (int i = 0; i < 5; ++i) {
                                for (int j = 0; j < 5; ++j) {
                                    graph.insertGraphNode(k, 100 + j * 200, 100 + i * 200);
                                    if (k >= 5)
                                        graph.insertDirectedEdge(k - 5, k, Math.abs(rand.nextInt() % 20) + 1);
                                    if (k % 5 != 0)
                                        graph.insertDirectedEdge(k - 1, k, Math.abs(rand.nextInt() % 20) + 1);
                                    ++k;

                                }
                            }
                        }
                        break;
                    case "Directed Cyclical":
                        clear();
                        displayMessage("Directed Cyclical graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        break;
                    case "Directed Forest":
                        clear();
                        displayMessage("Directed Forest graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        break;
                    case "Undirected Arbitrary":
                        clear();
                        displayMessage("Undirected Arbitrary graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        break;
                    case "Undirected Cyclical":
                        clear();
                        displayMessage("Undirected Cyclical graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        break;
                    case "Undirected Forest":
                        clear();
                        displayMessage("Undirected Forest graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        break;
                    case "Mixed Graph":
                        clear();
                        displayMessage("Mixed graph selected. Press select to load onto the screen.");
                        graph = new Graph();
                        break;
                    case "Select Graph":
                        displayMessage("No graph selected. Use the drop-down to choose a graph and press select to load onto the screen");
                        graph = new Graph();
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
            case "AVL Tree":
                MainActivity.actionBar.setTitle("AVL Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                tree = new AVLTree();
                break;
            case "Graph":
                MainActivity.actionBar.setTitle("Graph");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                graph = new Graph();
                break;
        }
    }

    /**
     * This method is called when the insert button is pressed. It adds the value to the tree
     * and resets the text on the insert line.
     */
    private void insert() {
        initTreeSpinner();
        tree.insert(Integer.parseInt(String.valueOf(insertNumber.getText().toString())));
        insertNumber.setText("");
    }

    /**
     * This method takes an arraylist and inserts all of the values into the tree
     * @param arrl an arraylist of values that needs to be inserted
     */
    public void arrayListInsert(ArrayList<Integer> arrl) {
        for (int i = 0; i < arrl.size(); i++) {
            tree.insertNoAnim(arrl.get(i));
        }
    }

    /**
     * This method is called when the auto button is pressed. It auto-populates the data structure.
     */
    private void autoPopulate() {

    }


    /**
     * This method sets the canvas bitmap.
     *
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
     * Displays a message in the displayExec text.
     *
     * @param message the message to be displayed.
     */
    public static void displayMessage(String message) {
        if (message == null) return;
        final String message2 = message;
        displayExec.post(new Runnable() {
            @Override
            public void run() {
                String hMessage = displayExec.getText().toString();
                displayExec.setText(String.format("%s\n%s", hMessage, message2));
                displayExecScroll.scrollTo(0, displayExecScroll.getBottom());
            }
        });
    }
}