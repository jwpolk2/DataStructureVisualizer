package com.example.datastructurevisualizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.datastructurevisualizer.ui.Visualizer;

public class VisualizerCanvas extends SurfaceView {

    public Bitmap bitmap;
    public static Canvas canvas;
    private int vWidth;
    private int vHeight;
    AttributeSet attrs;


    public VisualizerCanvas(Context context) {
        super(context);
        init(null);


    }

    public VisualizerCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public VisualizerCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    //Requires different API level
//    public VisualizerCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(attrs);
//    }

    public void render() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Visualizer.setCanvas(bitmap);
            }
        });
        Log.d("Rendering", "Render Method Called" );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        this.canvas = canvas;
//        this.canvas.setBitmap(bitmap);
//        this.canvas.drawColor(Color.WHITE);

    }

    public void init(AttributeSet attrs) {


        this.attrs = attrs;


    }


//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        vWidth = w;
//        vHeight = h;
//        bitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
//    }

    public Canvas getCanvas(){return canvas;}

    public void setDimensions(int vHeight, int vWidth) {
        this.vHeight = vHeight;
        this.vWidth = vWidth;
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            canvas = new Canvas();
            canvas.setBitmap(bitmap);
            canvas.drawColor(Color.WHITE);
        }
    }
}
