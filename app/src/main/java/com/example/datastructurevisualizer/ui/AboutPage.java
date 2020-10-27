package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutPage extends Fragment {

    private Button backHome;

    public AboutPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static AboutPage newInstance(String param1, String param2) {
        AboutPage fragment = new AboutPage();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about_page, container, false);
        backHome = view.findViewById(R.id.back_home);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openFragment(new Home(), false);
            }
        });
        return view;
    }
}