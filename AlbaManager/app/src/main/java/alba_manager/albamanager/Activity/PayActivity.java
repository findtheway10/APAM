package alba_manager.albamanager.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import alba_manager.albamanager.R;
import alba_manager.albamanager.Util.AlbaLibrary;

//TODO 주석달것 퍼블릭은 주석 필드로 달것
public class PayActivity extends AppCompatActivity {

    //날짜 저장
    private String inputDate;

    //뷰
    TextView payDate;

    //TODO 접근 제한자 설정할것
    /*    Day_Pay Class구조의 동적배열을 Today_pay변수로 선언
    *
    * */
    ArrayList<Day_pay> Today_pay;

    /*
          일별 근무 정보에 들어가는 금액, 근무확인 상태, 직종, 색상으로 구성된 구조체 class
          */
    class Day_pay {
        int color;
        String job;
        String day_pay;    //일당 금액
        boolean check_duty; //근무상태

        Day_pay(int scolor, String sjob, String sday_pay, boolean scheck_duty) {
            color = scolor;
            job = sjob;
            day_pay = sday_pay;
            check_duty = scheck_duty;
        }
    }

    //TODO 주석달고, 변수명 통일해주고 불필요한 소스코드제거
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //파라미터 입력
        //Intent 객체 생성후 인텐트 얻음
        Intent intent = getIntent();

        //월일 얻음
        inputDate = intent.getExtras().getString(AlbaLibrary.KEY_INTENT_SCHEDULE_DATE);

        //뷰 연결
        payDate = (TextView) findViewById(R.id.textViewPayDate);
        payDate.setText(inputDate);

        /*추후 DB에서 받아온 정보를 직접 할당해야 하는 부분
        * */
        Today_pay = new ArrayList<Day_pay>(); // 동적배열 생성;
        Day_pay day_pay;    //Day_pay 객체 생성

        day_pay = new Day_pay(Color.argb(255, 255, 217, 250), "편의점", "45000", true);    //DB에서 받아온 값을 저장하고
        Today_pay.add(day_pay); //배열에 추가
        day_pay = new Day_pay(Color.argb(255, 217, 229, 255), "서빙", "20000", false);
        Today_pay.add(day_pay);
        day_pay = new Day_pay(Color.argb(255, 255, 217, 250), "편의점", "45000", true);
        Today_pay.add(day_pay);
        day_pay = new Day_pay(Color.argb(255, 217, 229, 255), "서빙", "20000", false);
        Today_pay.add(day_pay);

        /*Adapter객체에 listView와 배열List를 연결
        * */
        Day_pay_Adapter adapter = new Day_pay_Adapter(this, R.layout.pay_item, Today_pay);

        ListView list;
        list = (ListView) findViewById(R.id.day_alba_list);

        /*scroll.xml을 연결하여 ListView에 ScrollView 연결
        * */
        LayoutInflater li = getLayoutInflater();
        //TODO 해당 부분 사용안하고도 구현가능, 연구해서 삭제 할것
        RelativeLayout Rll = (RelativeLayout) li.inflate(R.layout.scroll, null);
        list.addFooterView(Rll);

        //listView에 custom adapter연결
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    *    CustomAdapter 구현 부분
    */
    //TODO 주석 전부달고 ArrayAdater로변경할것
    class Day_pay_Adapter extends BaseAdapter {
        Context con;
        LayoutInflater inflater;
        ArrayList<Day_pay> Today;
        int layout;

        public Day_pay_Adapter(Context scon, int slayout, ArrayList<Day_pay> sToday) {
            con = scon;
            inflater = (LayoutInflater) scon.getSystemService(scon.LAYOUT_INFLATER_SERVICE);
            Today = sToday;
            layout = slayout;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(layout, parent, false);
            }
            TextView TakeColor = (TextView) convertView.findViewById(R.id.JobColor);
            TakeColor.setBackgroundColor(Today.get(position).color);

            TextView TakeJob = (TextView) convertView.findViewById(R.id.Job_Name);
            TakeJob.setText(Today.get(position).job);


            TextView TakePay = (TextView) convertView.findViewById(R.id.Day_Pay);
            TakePay.setText(Today.get(position).day_pay);

            CheckBox TakeCheck = (CheckBox) convertView.findViewById(R.id.Check_Duty);
            TakeCheck.setChecked(Today.get(position).check_duty);

            return convertView;
        }

        @Override
        public int getCount() {
            return Today.size();
        }

        @Override
        public Object getItem(int position) {
            return Today.get(position).job;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


    }
}
