package com.example.datastructurevisualizer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.TreeVisualizer;

/**
 * This class creates the dialog for when a node is clicked on the visualizer canvas.
 * The actions include seeing the value of the node, deleting the node and cancelling the action.
 */
public class DialogNodeAction extends DialogFragment {
    private TextView nodeValue;
//    private TextView nodeParent;
//    private TextView nodeLChild;
//    private TextView nodeRChild;
//    private TextView nodeHeight;
    private Button deleteBtn;
    private Button cancelBtn;
    private TreeVisualizer tree;

    /**
     * Default constructor for this class.
     */
    public DialogNodeAction() {   }

    /**
     * Constructor which takes a tree as input and sets the class tree variable.
     * @param tree
     */
    public DialogNodeAction(TreeVisualizer tree) {
        this.tree = tree;
    }

    /**
     * Creates a new instance of this dialog and sets the tree variable with the passed in
     * TreeVisualizer object.
     * @param tree
     * @param key
     * @return
     */
    public static DialogNodeAction newInstance(TreeVisualizer tree, int key) {
        DialogNodeAction nodeAction = new DialogNodeAction(tree);
        Bundle args = new Bundle();
        args.putInt("key", key);
        nodeAction.setArguments(args);
        return nodeAction;
    }

    @Override
    /**
     * Creates the view of the NodeActionDialog. This includes the node value and setting the
     * delete and cancel button actions.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int mKey = getArguments().getInt("key");
        View view = inflater.inflate(R.layout.dialog_node_action, container);
        nodeValue = view.findViewById(R.id.nodeActionLabelValue);
        nodeValue.setText("Node value: "+mKey);
//        nodeParent = view.findViewById(R.id.nodeActionLabelParent);
//        nodeLChild = view.findViewById(R.id.nodeActionLabelLeft);
//        nodeRChild = view.findViewById(R.id.nodeActionLabelRight);
//        nodeHeight = view.findViewById(R.id.nodeActionLabelHeight);
        deleteBtn = view.findViewById(R.id.nodeActionDelete);
        cancelBtn = view.findViewById(R.id.nodeActionCancel);

        //Sets the action for the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tree.remove(mKey);
               dismiss();
            }
        });
        //Sets the action for the cancel button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
