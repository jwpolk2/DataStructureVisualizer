package com.example.datastructurevisualizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity {
    private static Canvas canvas;
    private ViewAnimator viewAnimator;
    private static VisualizerCanvas drawImage;
    private Paint paint;
    private final int OFFSET = 120;
    private int mOffset = OFFSET;
    private Bitmap bitmap;
    private int vWidth;
    private int vHeight;
    private int circleOffset;
    private Button DrawBST;
    private Button InsertNode;
    BinarySearchTree bst ;
    private static FragmentManager fragmentManager;

    public int[] bst_array = {5, 8, 10, 3, 1, 6, 9, 7, 2, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
//        drawImage = findViewById(R.id.animatorImage);
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        DrawBST = (Button) findViewById(R.id.drawButton);
//        InsertNode = (Button) findViewById(R.id.InsertButtonMain);


//        DrawBST.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawRedCircle(drawImage);
//
//
//            }
//        });
//
//        InsertNode.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InsertIntoBST();
//
//            }
//        }));


    }

    public static void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Method used to test the functionality of Canvas. Draws a circle when the button is pressed.
     * TODO I'm using this to debug now
     */
    public void drawRedCircle(View view) {
        bst = new BinarySearchTree();

        //Random random = new Random();
        for (int i = 0; i< bst_array.length; i++){
            bst.insertNoAnim(bst_array[i]);
        }


        /*
        bst.insert(50);

        for (int i = 0; i < 20; ++i) {
            bst.insertNoAnim(random.nextInt() % 100);

        }
        */


        // render
        bst.placeTreeNodes();
        bst.placeNodesAtDestination();
        //bst.render();

        // Inserts with an animation.
        //bst.insert(random.nextInt() % 100);
        bst.render();

        // does some traversals
        //bst.preOrderTraversal();



    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        vWidth = drawImage.getWidth();
//        vHeight = drawImage.getHeight();
//        drawImage.setDimensions(vHeight, vWidth);
//    }

    public static void setCanvas(Bitmap bitmap) {

        drawImage.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }



    /**
     * Method used to test the functionality of Canvas. Draws a circle when the button is pressed.
     * TODO delete this
     */

    public void InsertIntoBST(){
        bst.insert(50);

    }
    public void drawBlueCircle(View view) {}


//    /**
//     * Method used to test the functionality of Canvas. Draws a circle when the button is pressed.
//     * TODO delete this
//     */
//    public void drawBlueCircle(View view) {
//
//        Canvas canvas2 = getCanvas();
//
////        paint.setColor(Color.BLUE);
////        canvas2.drawCircle(300f + circleOffset,300f + circleOffset,100f, paint);
////        circleOffset += 20;
//    }

//    public static Canvas getCanvas() {
//        return drawImage.canvas;
//    }

    public static VisualizerCanvas getVisualizer() {
        return drawImage;
    }

}