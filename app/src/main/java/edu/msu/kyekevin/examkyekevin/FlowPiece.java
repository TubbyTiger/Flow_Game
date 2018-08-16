package edu.msu.kyekevin.examkyekevin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Kevin on 4/11/2018.
 */

public class FlowPiece {
    private Bitmap piece;
    private float x = 0;
    private float y = 0;
    private float scaleFactor;
    private int id;
    private ArrayList<Float> gridsPastx = new ArrayList<>();
    private ArrayList<Float> gridsPasty = new ArrayList<>();
    public int getId(){
        return id;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public ArrayList<Float> getGridX(){
        return gridsPastx;
    }
    public ArrayList<Float> getGridY(){
        return gridsPasty;
    }
    public boolean getDragged(){
        return dragged;
    }
    public void setDragged(boolean b){
        dragged = b;
    }
    private float width;
    private boolean dragged = false;

    public FlowPiece(Context context, int cx, int cy,int id) {
        this.x = (float)cx/10;
        this.y = (float)cy/10;
        this.id = id;
        piece = BitmapFactory.decodeResource(context.getResources(), R.drawable.white);
        gridsPastx.add(0,x);
        gridsPasty.add(0,y);
    }


    public void clearArrays(){
        gridsPastx.clear();
        gridsPasty.clear();
        gridsPastx.add(0,x);
        gridsPasty.add(0,y);
        setDragged(false);
    }
    //proejct 1
    public boolean hit(float testX, float testY,
                       int puzzleSize) {
        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * puzzleSize / scaleFactor);
        int pY = (int)((testY - y) * puzzleSize / scaleFactor);

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    public void draw(Canvas canvas){
        //.5 scale factor means shrink it  by half.
        width = canvas.getWidth();
        scaleFactor =  (width)/piece.getWidth()/10;
        canvas.save();
        canvas.translate(x*(width),y*(width));
        canvas.scale(scaleFactor,scaleFactor);
        canvas.drawBitmap(piece,0,0,null);
        canvas.restore();
    }
}
