package alba_manager.albamanager.Activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.StringTokenizer;

import alba_manager.albamanager.DB_Manager.AlbaDBManager;
import alba_manager.albamanager.DB_Model.AlbaDBModel;
import alba_manager.albamanager.Model.AlbaItemModel;
import alba_manager.albamanager.R;
import alba_manager.albamanager.Util.AlbaLibrary;
import alba_manager.albamanager.Util.UtilClass;
import alba_manager.albamanager.Util.colorpicker.ColorPickerDialog;
import alba_manager.albamanager.Util.colorpicker.ColorPickerSwatch;
import alba_manager.albamanager.Util.colorpicker.Utils;

/**
 * @author 김대경
 *         날짜 : 2015. 06. 29.
 *         Type 편집용 Fragment
 */
public class AlbaEditActivity extends AppCompatActivity {

    //메모 유형 정보를 저장할 모델 객체 생성
    private AlbaItemModel albaItemModel = new AlbaItemModel();
    //메모 유형 DB 정보를 저장할 모댈 객체 생성
    private AlbaDBModel albaDBModel = new AlbaDBModel();
    //컬러 값을 저장할 변수 선언
    private String colorCode = AlbaLibrary.ALBA_NO_COLOR;

    //insert flag : true 신규, false 수정
    private boolean insert_flag;

    //View
    private Button buttonSetColor;
    private Button buttonSetWorkDay;
    private EditText editTextEditAlbaName;
    private EditText editTextEditAlbaPay;
    private TextView textViewStartTime;
    private TextView textViewEndTime;
    private LinearLayout linearLayoutSetWorkDay;


    //시급
    private int pay;

    //시간 설정용 변수
    private int startTimeHour ;
    private int startTimeMin ;
    private int endTimeHour;
    private int endTimeMin;
    private String startTime = AlbaLibrary.DEFAULT_START_TIME;
    private String endTime = AlbaLibrary.DEFAULT_END_TIME;

    //ResultCode
    private int albaEditResultCode = AlbaLibrary.RESULT_CODE_ALBA_EDIT_DEFAULT;

    //intent 받아 오기 위한 객체
    private Intent intent;

    //DB Manager
    private AlbaDBManager albaDBManager;

    //유틸 클레스 선언
    private UtilClass utilClass;

    //리스너 인터페이스
    public interface AlbaEditCallBackListener {
        //유형이 업데이트 되었을경우
        void onUpdate(AlbaItemModel itemModel);

        //유형이 삭제되었을 경우
        void onDelete();
    }

    public interface AlbaInsertCallBackListener {
        //유형이 추가 되었을 경우
        void onInsert(AlbaItemModel itemModel);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_edit);

        //DB 접근용, DB 메니져 생성
        albaDBManager = new AlbaDBManager();

        //유틸 클레스 생성
        utilClass = new UtilClass();

        //인텐트 얻음
        intent = getIntent();
        //아이템 정보 얻음
        if (intent.getSerializableExtra(AlbaLibrary.KEY_INTENT_ALBA_PUT_ITEM_MODEL) != null) {
            albaItemModel = (AlbaItemModel)
                    intent.getSerializableExtra(AlbaLibrary.KEY_INTENT_ALBA_PUT_ITEM_MODEL);
        }

        //플레그 획득
        if (intent.getSerializableExtra(AlbaLibrary.KEY_INTENT_ALBA_INSERT_FLAG) != null) {
            insert_flag =
                    intent.getBooleanExtra(AlbaLibrary.KEY_INTENT_ALBA_INSERT_FLAG, true);
        }

        //View 연결
        buttonSetColor = (Button) findViewById(R.id.albaEditButtonSetColor);
        buttonSetWorkDay = (Button) findViewById(R.id.alblEditSetWorkDayButton);
        textViewStartTime = (TextView) findViewById(R.id.albaEditStartTimeTextView);
        textViewEndTime = (TextView) findViewById(R.id.albaEditEndTimeTextView);
        editTextEditAlbaName = (EditText) findViewById(R.id.albaEditNameEditText);
        editTextEditAlbaPay = (EditText) findViewById(R.id.albaEditPayEditText);
        linearLayoutSetWorkDay = (LinearLayout) findViewById(R.id.albaEditLinearLayout01);

        //일정 입력 숨김
        linearLayoutSetWorkDay.setVisibility(View.INVISIBLE);

        //밑줄 색상변경
        editTextEditAlbaName.getBackground().setColorFilter(
                getResources().getColor(R.color.color_baseBlue), PorterDuff.Mode.SRC_ATOP);

        //수정시 버튼 색상
        if(!insert_flag) {
            //모델에서 컬러 정보 얻음
            colorCode = albaItemModel.getAlba_color();
        }
        //버튼 색 변경
        buttonSetColor.setBackgroundColor(Color.parseColor(colorCode));

        //onClickListener
        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //시간 설정 다이얼로그 생성
                new TimePickerDialog(AlbaEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        //시간 재설정(String 생성)
                        startTime = utilClass.getStringTime(hour,min);
                        startTimeHour = hour;
                        startTimeMin = min;

                        textViewStartTime.setText(startTime);
                    }
                }, startTimeHour, startTimeMin, false).show();
            }
        });

        //시간 설정 다이얼로그 생성
        textViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AlbaEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        endTime = utilClass.getStringTime(hour, min);
                        endTimeHour = hour;
                        endTimeMin = min;

                        textViewEndTime.setText(endTime);
                    }
                }, endTimeHour, endTimeMin, false).show();
            }
        });

        buttonSetColor.setOnClickListener(new View.OnClickListener() {

            //커러 배열을 얻음
            int[] mColor = colorChoice(getApplicationContext());

            @Override
            public void onClick(View v) {
                //ColorPickerDialog 생성
                ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                        R.string.regialba_color_title,
                        mColor,
                        Color.parseColor(colorCode),
                        4,
                        Utils.isTablet(getApplicationContext())
                                ? ColorPickerDialog.SIZE_LARGE : ColorPickerDialog.SIZE_SMALL);

                //리스너 설정
                colorcalendar.setOnColorSelectedListener(
                        new ColorPickerSwatch.OnColorSelectedListener() {

                            @Override
                            public void onColorSelected(int color) {
                                //선택한 값을 받아서 컬러 코드 저장 변수에 반환
                                //String으로 변환
                                colorCode = String.format("#%06X", (0xFFFFFF & color));
                                //버튼색 갱신
                                buttonSetColor.setBackgroundColor(color);
                            }

                        });
                //ColorPickerDialog 창을 팝업
                colorcalendar.show(getSupportFragmentManager(), "cal");
            }
        });



        if(insert_flag==false){
            //수정일 경우


            //MemoModel 얻음
            albaDBModel = albaDBManager.getAlbaModelByDBID(albaItemModel.getAlba_dbID());

            editTextEditAlbaName.setText(albaDBModel.alba_name);
            //리스너 연결

            //DB에서 값 가져옴
            startTime = albaDBModel.alba_startTime;
            endTime = albaDBModel.alba_endTime;
            pay = albaDBModel.alba_pay;

            //시급 설정
            editTextEditAlbaPay.setText("" + pay);

            //일정 입력 표시
            linearLayoutSetWorkDay.setVisibility(View.VISIBLE);

        }else{
            //no action

        }

        //시간 입력
        //String 토크나이저로 시간 분리
        StringTokenizer stStart = new StringTokenizer(startTime, AlbaLibrary.TOKENIZER_SEPARATER);
        startTimeHour = Integer.parseInt(stStart.nextToken());
        startTimeMin = Integer.parseInt(stStart.nextToken());

        StringTokenizer stEnd = new StringTokenizer(endTime, AlbaLibrary.TOKENIZER_SEPARATER);
        endTimeHour = Integer.parseInt(stEnd.nextToken());
        endTimeMin = Integer.parseInt(stEnd.nextToken());

        textViewStartTime.setText(startTime);
        textViewEndTime.setText(endTime);

    }




    // 뒤로가기 누를 시
    @Override
    public void onBackPressed() {
        closeActvity();
    }


    //액션바 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflater함수를 이용해서 menu 리소스를 menu로 변환.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.regialba, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem actionCancel = menu.findItem(R.id.action_cancel);
        MenuItem actionExecute = menu.findItem(R.id.action_execute);
        if(insert_flag==false){
            actionCancel.setTitle(getString(R.string.regi_alba_edit_update_cancel));
            actionExecute.setTitle(getString(R.string.sregi_alba_edit_update_execute));
        }else{
            actionCancel.setTitle(getString(R.string.regi_alba_edit_insert_cancel));
            actionExecute.setTitle(getString(R.string.regi_alba_edit_insert_execute));
        }

        return super.onPrepareOptionsMenu(menu);

    }


    //액션바 버튼 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_cancel:
                // 취소버튼
                if(insert_flag==false){
                    deleteType();
                }else{
                    onClickOff();
                }

                return true;

            case R.id.action_execute:
                //실행 버튼
                updateType();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //유형 삭제
    private void deleteType() {
        albaEditResultCode = AlbaLibrary.RESULT_CODE_ALBA_EDIT_DELETE;
        AlbaItemModel tempAlbaItemModel = new AlbaItemModel(
                albaItemModel.getAlba_dbID(),
                editTextEditAlbaName.getText().toString(),
                colorCode,
                Integer.parseInt(editTextEditAlbaPay.getText().toString()),
                textViewStartTime.getText().toString(),
                textViewEndTime.getText().toString()
        );
        intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_GET_ITEM_MODEL, tempAlbaItemModel);

        setResult(albaEditResultCode, intent);
        finish();
    }

    private void closeActvity() {
        albaEditResultCode = AlbaLibrary.RESULT_CODE_ALBA_EDIT_DEFAULT;
        setResult(albaEditResultCode, intent);
        //창 종료
        finish();
    }

    //유형 편집
    private void updateType(){
        //공백 확인
        if (!utilClass.isBlankOrSpacing(editTextEditAlbaName.getText().toString())) {

            if(!utilClass.isBlankOrSpacing(editTextEditAlbaPay.getText().toString())){

                if((startTimeHour*60 + startTimeMin) < (endTimeHour * 60 + endTimeMin)){
                    //공백이 아닐 경우 입력 받은 이름과 색상으로 객체를 생성
                    AlbaItemModel tempAlbaItemModel = new AlbaItemModel(
                            albaItemModel.getAlba_dbID(),
                            editTextEditAlbaName.getText().toString(),
                            colorCode,
                            Integer.parseInt(editTextEditAlbaPay.getText().toString()),
                            textViewStartTime.getText().toString(),
                            textViewEndTime.getText().toString()
                    );
                    if(insert_flag==true){
                        //신규 리스너에 객체 전달
                        albaEditResultCode = AlbaLibrary.RESULT_CODE_ALBA_EDIT_INSERT;
                    }else {
                        //업데이트 리스너에 객체 전달
                        albaEditResultCode = AlbaLibrary.RESULT_CODE_ALBA_EDIT_UPDATE;

                    }

                    intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_GET_ITEM_MODEL, tempAlbaItemModel);
                    setResult(albaEditResultCode, intent);
                    finish();
                }else {
                    //토스트 출력
                    Toast.makeText(
                            this,
                            getResources().getString(R.string.toast_time_input_error),
                            Toast.LENGTH_LONG
                    ).show();
                }


            }else {
                //토스트 출력
                Toast.makeText(
                        this,
                        getResources().getString(R.string.toast_no_pay_at_edit_text),
                        Toast.LENGTH_LONG
                ).show();
            }
        }else{
            //토스트 출력
            Toast.makeText(
                    this,
                    getResources().getString(R.string.toast_no_name_at_edit_text),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    //종료 버튼
    private void onClickOff() {
        albaEditResultCode = AlbaLibrary.RESULT_CODE_ALBA_EDIT_DEFAULT;
        setResult(albaEditResultCode, intent);
        finish();
    }

    //색상 배열 선언용
    public static int[] colorChoice(Context context){

        //배열 선언
        int[] mColorChoices=null;

        //미리 설정된 배열에서 색상 배열을 가져옴
        String[] color_array = context.getResources().
                getStringArray(R.array.default_color_choice_values);

        //컬러 값을 변환
        if (color_array!=null && color_array.length>0) {
            mColorChoices = new int[color_array.length];
            for (int i = 0; i < color_array.length; i++) {
                mColorChoices[i] = Color.parseColor(color_array[i]);
            }
        }
        return mColorChoices;
    }
}
