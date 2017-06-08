package kr.heythisway.drawingnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMARTHINK_MBL13 on 2017. 6. 8..
 */

public class Board extends View {
    private static Paint paint;
//    Paint paint;
    Path current_path;
    List<Brush> brushes = new ArrayList<>();

    public Board(Context context) {
        super(context);
    }

    public static void setPaint(Paint paints) {
        paint = paints;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Brush brush : brushes )
        canvas.drawPath(brush.path, brush.paint);
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

                // 터치가 시작되는 순간 path와 paint를 포함한 붓(brush)를 생성한다.
                Brush brush = new Brush();
                current_path = new Path();  // 현재의 path를 생성한다.
                brush.path = current_path;  // 붓의 path에 현재의 path를 담는다.
                brush.paint = paint;    // MainActivity에서 받아온 Paint값을 붓에 담는다.
                brushes.add(brush);     // 완성된 브러쉬를 저장소에 담는다.

                current_path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                current_path.lineTo(x, y);  //바로 이전점과 현재점 사이에 줄을 그어준다.
                break;
            case MotionEvent.ACTION_UP:
                current_path.lineTo(x, y);
                break;
        }

        invalidate();   //참고로 서브스트림에서는 postInvalidate()를 사용한다.

        return true;
    }

    private class Brush {
        Paint paint;
        Path path;
    }
}
