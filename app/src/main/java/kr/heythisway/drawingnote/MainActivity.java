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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    RadioGroup radioGroup;
    SeekBar seekBar;

    final static int GREEN = 1;
    final static int BLUE = 2;
    final static int RED = 3;

    int currentColor = 1;
    int currentWidth = 10;

    Board board, board1, board2, board3, boardSample;
    Paint paint;

    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Board> boards = new ArrayList<>();
        board = new Board(getBaseContext());
        board.setPath(currentColor, currentWidth);
//        boards.add(board);

        frameLayout = (FrameLayout) findViewById(R.id.layout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (group.getId() == R.id.radioGroup) {
                    switch (checkedId) {
                        case R.id.radioBtnGreen:
                            if (currentColor != GREEN) {
                                currentColor = GREEN;
                                board = new Board(getBaseContext());
                                boardSample = board;
                                boardSample.setPath(currentColor, currentWidth);
                                Toast.makeText(MainActivity.this, "녹색을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                                frameLayout.addView(boardSample);
                            }
                            break;
                        case R.id.radioBtnBlue:
                            if (currentColor != BLUE) {
                                currentColor = BLUE;
                                board = new Board(getBaseContext());
                                boardSample = board;
                                boardSample.setPath(currentColor, currentWidth);
                                Toast.makeText(MainActivity.this, "파랑색을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                                frameLayout.addView(boardSample);
                            }
                            break;
                        case R.id.radioBtnRed:
                            if (currentColor != RED) {
                                currentColor = RED;
                                board = new Board(getBaseContext());
                                boardSample = board;
                                boardSample.setPath(currentColor, currentWidth);
                                Toast.makeText(MainActivity.this, "빨강색을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                                frameLayout.addView(boardSample);
                            }
                            break;
                    }
                }
            }
        });

        frameLayout.addView(board);

    }

    class Board extends View {
        Paint paint;
        Path path;
//        List<Path> paths = new ArrayList<>();


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
//            paths.add(path);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            for (Path path : paths) {
            canvas.drawPath(path, paint);
//            }
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
