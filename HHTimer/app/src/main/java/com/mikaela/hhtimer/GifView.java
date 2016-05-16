package com.mikaela.hhtimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;
import java.util.Random;


public class GifView extends View {

    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth, movieHeight;
    private long movieDuration;
    private long movieStart;


    public GifView(Context context){
        super(context);
        init(context);

    }

    public GifView(Context context, AttributeSet attrs){
        super(context);
        init(context);

    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context);
        init(context);

    }

    private void init(Context context){
        setFocusable(true);


        gifInputStream = context.getResources().openRawResource(+R.drawable.e);
        gifMovie = Movie.decodeStream(gifInputStream);
        movieWidth = gifMovie.width();
        movieHeight = gifMovie.height();
        movieDuration = gifMovie.duration();


    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension(movieWidth, movieHeight);
    }

    public int getMovieWidth(){
        return movieWidth;
    }

    public int getMovieHeight(){
        return movieHeight;
    }

    public long getMovieDuration(){
        return movieDuration;
    }

    protected void onDraw(Canvas canvas){

        long now = SystemClock.uptimeMillis();

        if(movieStart == 0){
            movieStart = now;
        }

        if(gifMovie != null){
            int dur = gifMovie.duration();
            if(dur == 0){
                dur = 10000;

            }

            int relTime = (int)((now - movieStart) % dur);
            gifMovie.setTime(relTime);

            gifMovie.draw(canvas, 0, 0);
            invalidate();

        }

    }
}
