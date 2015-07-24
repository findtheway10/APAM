package alba_manager.albamanager.Util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import alba_manager.albamanager.Model.AlbaItemModel;
import alba_manager.albamanager.R;

    /**
     * @author 김대경
     * 날짜 : 2015. 06.22.
     * 메모 유형 리스트뷰를 만들기 위한 Adapter
     */

//리스트 뷰 어뎁터
    public class RegiAlbaAdapter extends ArrayAdapter<AlbaItemModel> {

        //layout 전개용 inflater
        private LayoutInflater inflater;
        //메모 타입 모델 저장용 ArrayList
        private ArrayList<AlbaItemModel> albaArrayList = new ArrayList<AlbaItemModel>();

        //holder Class
        class ViewHolder {
            //뷰
            private TextView textViewName;
            private RelativeLayout rowLayout;
        }

        //생성자
        public RegiAlbaAdapter(Context context, ArrayList<AlbaItemModel> inputAlbaItemsArrayList) {
            super(context, R.layout.alba_row_01);
            this.inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.albaArrayList = inputAlbaItemsArrayList;
        }

        @Override
        public int getCount() {
            return albaArrayList.size();
        }

        @Override
        public AlbaItemModel getItem(int position) {
            return albaArrayList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //뷰홀더 선언
            ViewHolder holder;

            //컨버트 뷰가 없을 경우
            if (convertView == null) {

                //레이아웃 할당
                //sdk 버전 확인
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1){
                    //진저 브레드 이하
                    convertView = inflater.inflate(R.layout.alba_row_02, parent, false);
                }else {
                    //진저브레드 초과
                    convertView = inflater.inflate(R.layout.alba_row_01, parent, false);
                }


                //뷰 홀더 선언
                holder = new ViewHolder();

                //뷰 연결
                holder.textViewName = (TextView) convertView.findViewById(R.id.type_name);
                holder.rowLayout = (RelativeLayout) convertView.findViewById(R.id.type_layout_01);

                //테그 설정
                convertView.setTag(holder);
            }else {
                //컨버트 뷰가 있을 경우 태그 받음
                holder = (ViewHolder) convertView.getTag();
            }

            //텍스트 및 컬러 할당
            holder.textViewName.setText(albaArrayList.get(position).getAlba_name());
            holder.rowLayout.setBackgroundColor(
                    Color.parseColor(albaArrayList.get(position).getAlba_color()));

            return convertView;
        }
    }