package com.example.mdp_android_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Grid extends View {
    private int numColumns=20;
    private int numRows=20;
    private float cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private boolean[][] cellChecked;
    private final int padding = 20;
    private final int border = 5;


    public Grid(Context context) {
        this(context, null);
    }

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blackPaint.setColor(Color.DKGRAY);
        blackPaint.setTextSize(20);
        blackPaint.setTextAlign(Paint.Align.CENTER);
    }

    /*
    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }
    */




    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        cellWidth = (float) (width - padding*2 - border*2) / (numColumns + 1);
        cellHeight = (float) (height - padding*2 - border*2) / (numRows + 1);

        cellChecked = new boolean[numColumns][numRows];

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        float offsetX = padding + border + cellWidth;
        float offsetY = padding + border;

        calculateDimensions();
        if (numColumns == 0 || numRows == 0) {
            return;
        }


/*
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (cellChecked[i][j]) {

                    canvas.drawRect(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight,
                            blackPaint);
                }
            }
        } */

        //Line drawing for grid
        for (int i = 0; i <= numColumns; i++) {
            canvas.drawLine(offsetX + i * cellWidth, offsetY, offsetX + i * cellWidth, offsetY + cellHeight * numRows, blackPaint);
        }
        for (int i = 0; i <= numRows; i++) {
            canvas.drawLine(offsetX, offsetY + i * cellHeight, offsetX + cellWidth * (numColumns), offsetY + i * cellHeight, blackPaint);
        }


        //Coordinates text
        float textSize = this.blackPaint.getTextSize();
        for (int i = 0; i < numColumns; i++){
            canvas.drawText(String.valueOf(i), (float) (offsetX + this.cellWidth * (i+1 - 0.5)), offsetY +
                    this.cellHeight * (float) (numRows + 0.7), this.blackPaint);
        }
        for (int i = 0; i < numRows; i++){
            canvas.drawText(String.valueOf(i), offsetX - this.cellWidth/2, (float) (offsetY +
                    this.cellHeight * (numRows - (i+1) + 0.5) + textSize/2), this.blackPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            cellChecked[column][row] = !cellChecked[column][row];
            invalidate();
        }

        return true;
    }
}
