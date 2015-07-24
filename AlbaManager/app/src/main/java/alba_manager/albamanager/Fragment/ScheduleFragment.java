package alba_manager.albamanager.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import alba_manager.albamanager.Activity.ScheduleActivity;
import alba_manager.albamanager.R;
import alba_manager.albamanager.Util.AlbaLibrary;
import alba_manager.albamanager.Util.UtilClass;

@SuppressLint("SimpleDateFormat")
public class ScheduleFragment extends Fragment {

    //날짜 하기위해 유틸 클레스 선언
    UtilClass utilClass;

    //달럭 생성성
    private CaldroidFragment scheduleCaldroidFragment;

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();

        return fragment;
    }

    public ScheduleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Util Class 생성
        utilClass = new UtilClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        //데이터 포멧 객체 생성
        final SimpleDateFormat formatter = new SimpleDateFormat(AlbaLibrary.DATE_FORMAT);

        //CaldroidFragment 생성
        scheduleCaldroidFragment = new ScheduleCaldroidFragment();

        if (savedInstanceState != null) {
            scheduleCaldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }

        else {
            Bundle args = new Bundle();
            Calendar calendar = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            scheduleCaldroidFragment.setArguments(args);
        }

        // 리스너 설정
        final CaldroidListener scheduleListener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                //창 호출 하기위해 인텐드 생성
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);

                //String 으로 날짜 변환
                String inputTime = utilClass.getTime(date.getTime(), AlbaLibrary.DATE_FORMAT);

                //날짜 입력
                intent.putExtra(AlbaLibrary.KEY_INTENT_SCHEDULE_DATE, inputTime);

                //엑티비티 시작
                startActivityForResult(intent, AlbaLibrary.REQUEST_CODE_ALBA_EDIT_DEFAULT);

            }

        };

        // 리스너 할당
        scheduleCaldroidFragment.setCaldroidListener(scheduleListener);

        // 부모 뷰에 프레그먼트 붙임
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.scheduleCalendar, scheduleCaldroidFragment);
        t.commit();

        return v;
    }

}
