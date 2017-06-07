package kr.heythisway.drawingnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    RadioGroup radioGroup;
    SeekBar seekBar;

    final static int GREEN = 1;
    final static int BLUE = 2;
    final static int RED = 3;

    Board board;
    Paint paint;

    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.layout);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtnGreen:
                        color = 1;
                        board.setPaint(paint, color);
                        break;
                    case R.id.radioBtnBlue:
                        color = 2;
                        board.setPaint(paint, color);
                        break;
                    case R.id.radioBtnRed:
                        color = 3;
                        board.setPaint(paint, color);
                        break;
                }
            }
        });


        board = new Board(getBaseContext());
        //붓을 만들어 보드에 담는다.
        paint = new Paint();
        board.setPaint(paint, color);

        frameLayout.addView(board);

    }

    class Board extends View {
        Paint paint;
        Path path;


        public Board(Context context) {
            super(context);
            path = new Path();
        }

        public void setPaint(Paint paint, int color) {
            this.paint = paint;
            switch (color) {
                case GREEN:
                    paint.setColor(Color.GREEN);
                    paint.setStrokeWidth(10);
                    break;
                case BLUE:
                    paint.setColor(Color.BLUE);
                    paint.setStrokeWidth(20);
                    break;
                case RED:
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(30);
                    break;
            }
            paint.setStyle(Paint.Style.STROKE);
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
}
