package com.example.datastructurevisualizer;

import android.app.Dialog;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.datastructurevisualizer.ui.DialogNodeAction;

public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    public static ActionBar actionBar;
    private static VisualizerCanvas drawImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);


      fragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
//                        Log.d("FRAGMENT", "outside Switch");
//
//
//                        Log.d("FRAGMENT", String.format("%d", getSupportFragmentManager().getBackStackEntryAt(0).getId()));
//                        Log.d("FRAGMENT", String.format("About %d", R.id.about_fragment));
//                        Log.d("FRAGMENT", String.format("Home %d", R.id.home_fragment));
//                        Log.d("FRAGMENT", String.format("Files %d", R.id.files_fragment));
//                        Log.d("FRAGMENT", String.format("Visualizer %d", R.id.visualizer_fragment));
//                        Log.d("FRAGMENT", String.format("Container %d", R.id.fragment_container));
//
//                        switch (getSupportFragmentManager().getBackStackEntryAt(0).getId()) {
//                            case R.id.about_fragment:
//                            case R.id.visualizer_fragment:
//                            case R.id.files_fragment:
//                                Log.d("FRAGMENT", "here");
//                                actionBar.show();
//                                break;
//                            case R.id.home_fragment:
//                                Log.d("FRAGMENT", "hereHome");
//                                getSupportActionBar().hide();
//                                break;
//                        }
                    }
                });
    }

    public static void openFragment(Fragment fragment, boolean actionBar) {
        if(actionBar) {
            MainActivity.actionBar.show();
        }
        else {
            MainActivity.actionBar.hide();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "visible_fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void openDialog(Dialog dialog, String title) {
        dialog.show();
    }

    public static void setVisualizerCanvas(VisualizerCanvas visualizerCanvas){
        drawImage = visualizerCanvas;
//        vWidth = drawImage.getWidth();
//        vHeight = drawImage.getHeight();
//        drawImage.setDimensions(vHeight, vWidth);
    }

    public static VisualizerCanvas getVisualizer() {
        return drawImage;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
//                Log.d("FRAGMENT", String.format(getSupportFragmentManager().getBackStackEntryAt(0).getName()));
//                Log.d("FRAGMENT", String.format("About %d", R.id.about_fragment));
//                Log.d("FRAGMENT", String.format("Home %d", R.id.home_fragment));
//                Log.d("FRAGMENT", String.format("Files %d", R.id.files_fragment));
//                Log.d("FRAGMENT", String.format("Visualizer %d", R.id.visualizer_fragment));
//                Log.d("FRAGMENT", String.format("Container %d", R.id.fragment_container));
            if (fragmentManager.getBackStackEntryCount()>0){
                drawImage = null;
                fragmentManager.popBackStack();
            }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}