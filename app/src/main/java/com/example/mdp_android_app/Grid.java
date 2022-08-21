package com.example.mdp_android_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Grid extends View {
    private int numColumns=20;
    private int numRows=20;
    private float cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private Paint whiteNumber = new Paint();
    private boolean[][] cellChecked;
    private final int padding = 20;
    private final int border = 5;
    private float offsetX = padding + border + cellWidth;
    private float offsetY = padding + border;

    private Bitmap robotBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.robot_icon);


    public Grid(Context context) {
        this(context, null);
    }

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blackPaint.setColor(Color.DKGRAY);
        blackPaint.setTextSize(20);
        blackPaint.setTextAlign(Paint.Align.CENTER);
        whiteNumber.setColor(Color.WHITE);
        whiteNumber.setTextSize(20);
        whiteNumber.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);


        calculateDimensions();
        drawGrid(canvas);
        drawObstacle(canvas);
        drawRobot(canvas);

        //Line drawing for grid

    }

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

    private void drawGrid (Canvas canvas) {

        //Draw the Arena layout
        for (int i = 0; i <= numColumns; i++) {
            canvas.drawLine(offsetX + i * cellWidth, offsetY, offsetX + i * cellWidth, offsetY + cellHeight * numRows, blackPaint);
        }
        for (int i = 0; i <= numRows; i++) {
            canvas.drawLine(offsetX, offsetY + i * cellHeight, offsetX + cellWidth * (numColumns), offsetY + i * cellHeight, blackPaint);
        }


        //Draw the coordinate text
        float textSize = this.blackPaint.getTextSize();
        for (int i = 1; i <= numColumns; i++){
            canvas.drawText(String.valueOf(i), (float) (offsetX + this.cellWidth * (i - 0.5)), offsetY +
                    this.cellHeight * (float) (numRows + 0.7), this.blackPaint);
        }
        for (int i = 1; i <= numRows; i++){
            canvas.drawText(String.valueOf(i), offsetX - this.cellWidth/2, (float) (offsetY +
                    this.cellHeight * (numRows - i + 0.5) + textSize/2), this.blackPaint);
        }
    }

    private void drawObstacle(Canvas canvas) {
        //for now juz set a hardcoded obstacle
        int x = 5, y = 6;
        float textSize = this.whiteNumber.getTextSize();

        Paint mTextPaint = new Paint();
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;

        //default all obstacles when spawned shall face north

        //Obstacles shall all be recorded in an Arraylist

        //for loop - get obstacle coordinates + obstacle direction

        canvas.drawRect(offsetX + (x - 1) * cellWidth, offsetY + cellHeight * (numRows - y), offsetX + x * cellWidth, offsetY + cellHeight * (numRows - y + 1), blackPaint);

        canvas.drawText("1", offsetX + (float) (x - 1)*(cellWidth) + cellWidth/2, offsetY + (cellHeight * (numRows - y)) + (cellHeight - textHeight)/2 + textHeight, whiteNumber);

        //switch statement for yellow line depending on direction
    }

    private void drawRobot(Canvas canvas) {

        //hardcode coordinate of robot
        int col = 2, row = 3;

        Matrix m = new Matrix();
        Bitmap robotBitmap = Bitmap.createBitmap(this.robotBitmap,0,0, this.robotBitmap.getWidth(),
                this.robotBitmap.getHeight(),m,true);

        canvas.drawBitmap(robotBitmap,null, new RectF(offsetX + (col - 1) * cellWidth,
                offsetY + (numRows - row- 2) * cellHeight, offsetX + (col + 2) * cellWidth,
                offsetY + (numRows - row + 1) * cellHeight), null);

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
