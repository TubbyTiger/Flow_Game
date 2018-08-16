package edu.msu.kyekevin.examkyekevin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kevin on 4/11/2018.
 */

public class Flow {
    public Flow(Context context_) {
        context = context_;
        addPieces(context);
        activity = (Activity)context;
    }
    private void addPieces(Context context){
        int randx,randy,randx2,randy2;
        Random random = new Random(System.currentTimeMillis());
        randx2 = random.nextInt(10);
        randy2 = random.nextInt(10);
        randx = randx2;
        randy = randy2;
        while((randy ==randy2)&&(randx== randx2)){
            randx = random.nextInt(10);
            randy = random.nextInt(10);
        }
        pieces.add(new FlowPiece(context,randx,randy,0));
        pieces.add(new FlowPiece(context,randx2,randy2,1));

    }
    private Context context;
    private Activity activity;
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float width,marginy;
    private ArrayList<FlowPiece> pieces = new ArrayList<>();
    private FlowPiece dragging = null;
    /**
     * Most recent relative X touch when dragging
     */

    /**
     * Most recent relative Y touch when dragging
     */
    private boolean winBoolean = false;
    public boolean onTouchEvent(View view, MotionEvent event){
        float relX = (event.getX()) / width;
        float relY = (event.getY() - marginy) / width;
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                return onTouched(relX,relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(dragging!=null){
                    dragging.clearArrays();
                    dragging = null;
                    if(!winBoolean){
                        Toast.makeText(activity,R.string.lost, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        winBoolean = false;
                    }
                }
                view.invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:
                if(dragging != null) {
                    if(!drawSquare(relX, relY,dragging,view)){
                        Toast.makeText(activity,R.string.lost, Toast.LENGTH_SHORT).show();
                        dragging = null;
                    }
                    view.invalidate();
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean drawSquare(float xs, float ys,FlowPiece fp,View view){
        fp.setDragged(true);
        xs*= 10;
        ys*=10;
        xs = (int)Math.floor(xs);
        ys = (int)Math.floor(ys);
        xs/= 10;
        ys/= 10;
        if((ys==pieces.get(Math.abs(fp.getId()-1)).getY())&&(xs==pieces.get(Math.abs(fp.getId()-1)).getX())){
            Toast.makeText(activity,R.string.won, Toast.LENGTH_SHORT).show();
            restart(view);
            winBoolean = true;
            return true;
        }
        if((fp.getGridX().get(0)==xs)&&(fp.getGridY().get(0)==ys)){
            return true;
        }
        for(int i = 1; i < fp.getGridX().size() ; ++i ){
            if((ys>0.9)||(ys<0.0)||(xs<0.0)||(xs>0.9)||((fp.getGridX().get(i)==xs)&&(fp.getGridY().get(i)==ys))){
                fp.clearArrays();
                return false;
            }
        }
        fp.getGridX().add(0,xs);
        fp.getGridY().add(0,ys);
        return true;
    }
    // Project 1
    private boolean onTouched(float x, float y) {
        // Check each piece to see if it has been hit
        for(int p=pieces.size()-1; p>=0;  p--) {
            if(pieces.get(p).hit(x, y, (int)width)) {
                // We hit a piece!
                dragging = pieces.get(p);
                return true;
            }
        }
        return false;
    }

    public void restart(View view){
        pieces.clear();
        addPieces(context);
        view.invalidate();
    }

    public void draw(Canvas canvas) {
        width = canvas.getWidth();
        drawBoard(canvas);
        drawPieces(canvas);
        marginy = (width-(int)width)/2;
    }

    private void drawPieces(Canvas canvas){
        pieces.get(0).draw(canvas);
        pieces.get(1).draw(canvas);
    }

    private void drawBoard(Canvas canvas){
        linePaint.setColor(Color.GREEN);
        canvas.drawRect(0,0,width,width,linePaint);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(1);
        float startx,starty,endx,endy;
        startx = 0;
        starty = 0;
        endx = 0;
        endy= 0;
        // for drawing the board
        for(int i =0 ; i < 11 ; ++i){
            canvas.drawLine(0,starty,width,endy,linePaint);
            canvas.drawLine(startx,0,endx,width,linePaint);
            startx += width/10;
            endx += width/10;
            starty += width/10;
            endy += width/10;
        }
        // drawing the circle tracker
        for(FlowPiece p : pieces){
            if(p.getDragged()){
                for(int i = 0; i < p.getGridX().size(); ++i){
                    canvas.drawRect(p.getGridX().get(i)*width,p.getGridY().get(i)*width,p.getGridX().get(i)*width+width/10,p.getGridY().get(i)*width+width/10,linePaint);
                }
            }
        }
    }
}
