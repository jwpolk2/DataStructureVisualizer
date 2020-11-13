package com.example.datastructurevisualizer.ui;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.fragment.app.DialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.DataStructureVisualizer;
import com.example.datastructurevisualizer.TreeVisualizer;

public class DialogNodeAction extends DialogFragment {
    private TextView nodeValue;
//    private TextView nodeParent;
//    private TextView nodeLChild;
//    private TextView nodeRChild;
//    private TextView nodeHeight;
    private Button deleteBtn;
    private Button cancelBtn;
    private TreeVisualizer tree;

    public DialogNodeAction() {

    }

    public DialogNodeAction(TreeVisualizer tree) {
        this.tree = tree;
    }

    public static DialogNodeAction newInstance(TreeVisualizer tree, int key) {
        DialogNodeAction nodeAction = new DialogNodeAction(tree);
        Bundle args = new Bundle();
        args.putInt("key", key);
        nodeAction.setArguments(args);
        return nodeAction;
    }

    @Override
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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tree.remove(mKey);
               dismiss();
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
}
