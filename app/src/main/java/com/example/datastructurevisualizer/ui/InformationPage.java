package com.example.datastructurevisualizer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.datastructurevisualizer.MainActivity;
import com.example.datastructurevisualizer.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationPage extends Fragment {
    private String dataStructureType;
    private ViewPager2 mPager;
    private PagerAdapter pagerAdapter;

    public InformationPage() {
        // Required empty public constructor
    }

    public InformationPage(String dataStructureType){
        this.dataStructureType = dataStructureType;
    }


    public static InformationPage newInstance(String param1, String param2) {
        InformationPage fragment = new InformationPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        switch (dataStructureType){
            case "Binary Search Tree":
                view = inflater.inflate(R.layout.fragment_information_page, container, false);
                MainActivity.actionBar.setTitle("Binary Search Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case "Red Black Tree":
                view = inflater.inflate(R.layout.fragment_information_page, container, false);
                MainActivity.actionBar.setTitle("Red Black Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case "Balanced Search Tree":
                view = inflater.inflate(R.layout.fragment_information_page, container, false);
                MainActivity.actionBar.setTitle("AVL Tree");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            case "Graph":
                view = inflater.inflate(R.layout.fragment_information_page, container, false);
                MainActivity.actionBar.setTitle("Graph");
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_information_page, container, false);
                Button home = view.findViewById(R.id.visualize_button);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.openFragment(new Home(), false);
                    }
                });
        }

        mPager = (ViewPager2) view.findViewById(R.id.view_pager);
        mPager.setAdapter(new InformationPagerAdapter(this, dataStructureType));


        TabLayout tabLayout = view.findViewById(R.id.informationTabs);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, mPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("Overview");
                        break;
                    case 1:
                        tab.setText("Complexities");
                        break;
                    case 2:
                        tab.setText("Code");
                        break;
                }
            }
        }
        );
        tabLayoutMediator.attach();

        return view;

    }

}