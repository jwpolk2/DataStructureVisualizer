package com.example.datastructurevisualizer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.datastructurevisualizer.ui.Home;

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
        openFragment(new Home(), false);
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

    public static void openDialog(DialogFragment dialog, String title, FragmentTransaction ft) {
        dialog.show(ft, title);
    }

    public static void setVisualizerCanvas(VisualizerCanvas visualizerCanvas){
        drawImage = visualizerCanvas;
    }

    public static VisualizerCanvas getVisualizer() {
        return drawImage;
    }

    /**
     * @return the Canvas associated with the visualizer, else a dummy Canvas.
     */
    public static Canvas getCanvas() {

        // Returns the Canvas.
        if (drawImage != null && drawImage.getCanvas() != null) return drawImage.getCanvas();
        // Returns the dummy.
        else return new Canvas(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
            if (fragmentManager.getBackStackEntryCount()>0){
                drawImage = null;
                fragmentManager.popBackStack();
            }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}