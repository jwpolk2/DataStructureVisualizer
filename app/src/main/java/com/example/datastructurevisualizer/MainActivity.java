package com.example.datastructurevisualizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static Canvas canvas;
    private ViewAnimator viewAnimator;
    private ImageView drawImage;
    private Paint paint;
    private final int OFFSET = 120;
    private int mOffset = OFFSET;
    private Bitmap bitmap;
    private int vWidth;
    private int vHeight;
    private int circleOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawImage = findViewById(R.id.animatorImage);
        paint = new Paint();
        paint.setColor(Color.RED);
    }


    /**
     * Method used to test the functionality of Canvas. Draws a circle when the button is pressed.
     * TODO I'm using this to debug now
     */
    public void drawRedCircle(View view) {

        BinarySearchTree bst = new BinarySearchTree();
        Random random = new Random();
        bst.insert(50);
        for (int i = 0; i < 60; ++i) {
            bst.insert(random.nextInt());
        }

        // render
        bst.placeTreeNodes(1000, 20);
        bst.drawTree();

        /*paint.setColor(Color.RED);
        canvas.drawCircle(300f + circleOffset,300f + circleOffset,100f, paint);
        circleOffset += 20;*/

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        vWidth = drawImage.getWidth();
        vHeight = drawImage.getHeight();

        bitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        drawImage.setImageBitmap(bitmap);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);
    }


    /**
     * Method used to test the functionality of Canvas. Draws a circle when the button is pressed.
     * TODO delete this
     */
    public void drawBlueCircle(View view) {

        Canvas canvas2 = getCanvas();

        paint.setColor(Color.BLUE);
        canvas2.drawCircle(300f + circleOffset,300f + circleOffset,100f, paint);
        circleOffset += 20;
    }

    public static Canvas getCanvas() {
        return canvas;
    }
}