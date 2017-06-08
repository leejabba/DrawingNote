package kr.heythisway.drawingnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static kr.heythisway.drawingnote.MainActivity.BLUE;
import static kr.heythisway.drawingnote.MainActivity.GREEN;
import static kr.heythisway.drawingnote.MainActivity.RED;

/**
 * Created by SMARTHINK_MBL13 on 2017. 6. 8..
 */

public class Board extends View {
    Paint paint;
    Path path;

    public Board(Context context) {
        super(context);
    }

    public void setPath(int color, int width) {
        path = new Path();
        paint = new Paint();
        switch (color) {
            case GREEN:
                paint.setColor(Color.GREEN);
                break;
            case BLUE:
                paint.setColor(Color.BLUE);
                break;
            case RED:
                paint.setColor(Color.RED);
                break;
        }
        paint.setStrokeWidth(width);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //내가 터치한 좌표를 꺼낸다.
        float x = event.getX();
        float y = event.getY();

        Log.e("log", "======================= onTouchEvent : x = " + x + ", y = " + y);

        //액션 체크
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);  //그리지 않고 이동
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);  //바로 이전점과 현재점 사이에 줄을 그어준다.
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Toast.makeText(getContext(), "언제찍히니?", Toast.LENGTH_SHORT).show();
        }

        invalidate();   //참고로 서브스트림에서는 postInvalidate()를 사용한다.

        return true;
    }
}
