package kr.heythisway.drawingnote;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    int currentColor = 1;
    int currentWidth = 10;

    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Board 객체에 Paint 속성 설정한 후 프레임 레이아웃 뷰에 추가하기
        frameLayout = (FrameLayout) findViewById(R.id.layout);
        board = new Board(getBaseContext());
        board.setPath(currentColor, currentWidth);
        frameLayout.addView(board);

        // 라디오 버튼 (선의 색상)
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                selectRadio(group, checkedId);
            }
        });

        // 시크바 (선의 두께)
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekProcess = (TextView) findViewById(R.id.seekProcess);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentWidth = progress;
                seekProcess.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setPaintProperty(currentColor, currentWidth);
            }
        });
    }

    // 라디오 버튼 선택 분기 메서드
    private void selectRadio(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.radioGroup) {
            switch (checkedId) {
                case R.id.radioBtnGreen:
                    setPaintProperty(GREEN, currentWidth);
                    break;
                case R.id.radioBtnBlue:
                    setPaintProperty(BLUE, currentWidth);
                    break;
                case R.id.radioBtnRed:
                    setPaintProperty(RED, currentWidth);
                    break;
            }
        }
    }

    // 새로운 Board 객체를 생성해서 라디오 버튼 색상의 Paint 속성을 설정 후 프레임 레이아웃 뷰에 추가
    private void setPaintProperty(int color, int width) {
        Log.e("seek", "=======================  seek바가 움직임에따라 setPaintProperty가 실행되었습니다.");
        currentColor = color;
        currentWidth = width;

        board = new Board(getBaseContext());
        board.setPath(currentColor, currentWidth);
        frameLayout.addView(board);
    }


}
