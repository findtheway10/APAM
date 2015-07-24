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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import alba_manager.albamanager.Model.MyData;
import alba_manager.albamanager.R;
import alba_manager.albamanager.Util.AlbaLibrary;

//TODO 퍼블릭 매서드 주석 필드 달것
public class ScheduleActivity extends AppCompatActivity {

    //날짜 저장
    private String inputDate;

    //TODO 변수들 접근제한자 설정하고 주석 달것
    int num = 1;
    ListView list;
    MyAdapter adapter;
    ArrayList<MyData> arrData;
    TextView dateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //TODO 엑티비티내도 왠만해선 주석 달것
        Intent intent = getIntent();
        //날짜 얻음
        inputDate = intent.getExtras().getString(AlbaLibrary.KEY_INTENT_SCHEDULE_DATE);

        dateText = (TextView)findViewById(R.id.dateText);
        dateText.setText(inputDate);


        //리스트에 보여줄 데이터를 세팅한다.
        setData();

        //어댑터 생성
        adapter = new MyAdapter(this, arrData);

        //리스트뷰에 어댑터 연결
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);


    }

    /*
    List에 데이터를 입력하여 객체 생성
     */
    //TODO 테스트용 메서드(삭제 예정)
    private void setData(){
        arrData = new ArrayList<MyData>();
        arrData.add(new MyData(Color.RED ,num + "번 알바", "편의점", "15:00 ~", "24:00", "편의점가기싫다"));
        arrData.add(new MyData(Color.BLUE ,num+1 + "번 알바", "주차장", "10:00 ~", "21:00", "주차장주차장"));
        arrData.add(new MyData(Color.GREEN, num + 2 + "번 알바", "PC방", "24:00 ~", "09:00", "야간알바힘들엉"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO ArrayAdapter로 통일
    public class MyAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<MyData> arrData;
        private LayoutInflater inflater;

        public MyAdapter(Context c, ArrayList<MyData> arr) {
            this.context = c;
            this.arrData = arr;
            inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return arrData.size();
        }

        public Object getItem(int position) {
            return arrData.get(position).getName();
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_layout, parent, false);
            }

            TextView space = (TextView)convertView.findViewById(R.id.colorSpace);
            space.setBackgroundColor(arrData.get(position).getColor());

            TextView num = (TextView)convertView.findViewById(R.id.numText);
            num.setText(arrData.get(position).getNum());

            TextView name = (TextView)convertView.findViewById(R.id.nameText);
            name.setText(arrData.get(position).getName());

            TextView time1 = (TextView)convertView.findViewById(R.id.time1Text);
            time1.setText(arrData.get(position).getTime1());

            TextView time2 = (TextView)convertView.findViewById(R.id.time2Text);
            time2.setText(arrData.get(position).getTime2());

            TextView memo = (TextView)convertView.findViewById(R.id.memoText);
            memo.setText(arrData.get(position).getMemo());


            return convertView;
        }

    }
}
