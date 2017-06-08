package kr.heythisway.drawingnote;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView seekProcess;

    final static int GREEN = 1;
    final static int BLUE = 2;
    final static int RED = 3;
    int current_width = 10;
    int current_color = Color.GREEN;

    Paint current_brush;    // 현재 세팅된 브러쉬

    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 아이디 찾기
        frameLayout = (FrameLayout) findViewById(R.id.layout);
        // 라디오 버튼 (색 설정)
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtnGreen:
                        setBrush(Color.GREEN, current_width);
                        break;
                    case R.id.radioBtnBlue:
                        setBrush(Color.BLUE, current_width);
                        break;
                    case R.id.radioBtnRed:
                        setBrush(Color.RED, current_width);
                        break;
                }
            }
        });
        // 시크바 (선 두께 설정)
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current_width = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // setBrush 호출
                setBrush(current_color, current_width);
            }
        });

        // 1. 보드 생성
        board = new Board(getBaseContext());
        // 2. 생성한 보드를 화면에 붙이기
        frameLayout.addView(board);
        // 3. 기본 브러쉬 세팅
        setBrush(current_color, current_width);
    }

    private void setBrush(int color, int width) {
        // 1. 붓(Paint)을 만든다.
        Paint paint = new Paint();
        // 2. 색상을 설정한다.
        paint.setColor(color);

        // 3. 선의 스트로크 스타일 설정
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);

        // 4. 선의 두께 설정
        paint.setStrokeWidth(width);

        // 5. 보드에 붓(Paint)을 담는다.
        Board.setPaint(paint);
    }
}
