package alba_manager.albamanager.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import alba_manager.albamanager.Activity.AlbaEditActivity;
import alba_manager.albamanager.DB_Manager.AlbaDBManager;
import alba_manager.albamanager.Model.AlbaItemModel;
import alba_manager.albamanager.R;
import alba_manager.albamanager.Util.AlbaLibrary;
import alba_manager.albamanager.Util.RegiAlbaAdapter;

public class RegiAlbarFragment extends Fragment {

    //DB Manager
    private AlbaDBManager albaDBManager;

    //뷰
    private ListView regiAlbaListView;
    private Button regiAlbaButtonInsertAlba;
    private ImageView regiAlbaImageView;

    //리스트 뷰 사용을 위한 ArrayList
    private ArrayList<AlbaItemModel> albaArrayList = new ArrayList<AlbaItemModel>();

    //모델 아이템
    private AlbaItemModel albaItemModel = new AlbaItemModel();

    //리스트 뷰 사용을 위한 Adapter
    private RegiAlbaAdapter albaAdapter;

    //생성자 호출 메서드
    public static RegiAlbarFragment newInstance() {
        RegiAlbarFragment fragment = new RegiAlbarFragment();

        return fragment;
    }

    public RegiAlbarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_regi_albar, container, false);

        //DB 접근용, DB 메니져 생성
        albaDBManager = new AlbaDBManager();

        //뷰 연결
        regiAlbaListView = (ListView) v.findViewById(R.id.regiAlbaListView);
        regiAlbaButtonInsertAlba = (Button) v.findViewById(R.id.regiAlbaButtonInsertAlba);
        regiAlbaImageView = (ImageView) v.findViewById(R.id.regiAlbaImageViewPlus);

        //DB에서 ArrayList 얻음
        albaArrayList = albaDBManager.getAlbaList();

        //어뎁터 생성
        albaAdapter = new RegiAlbaAdapter(getActivity().getApplicationContext(), albaArrayList);

        //ListView에 Adapter 연결
        regiAlbaListView.setAdapter(albaAdapter);

        //onClickListener
        regiAlbaButtonInsertAlba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메모 타입 입력 메서드
                insertAlba();
            }
        });

        //이미지 뷰에도 리스너 할당
        regiAlbaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메모 타입 입력 메서드
                insertAlba();
            }
        });

        //리스트 뷰 아이템 클릭 리스너
        regiAlbaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent, View view, final int position, long id) {

                final AlbaItemModel albaItemModel = albaAdapter.getItem(position);

                //창 호출 하기위해 인텐드 생성
                Intent intent = new Intent(getActivity(), AlbaEditActivity.class);

                //인덱스 입력
                intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_INDEX, position);

                //Flag 입력
                intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_INSERT_FLAG, false);

                //아이템 입력
                intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_PUT_ITEM_MODEL, albaItemModel);

                //변경사항이 있을경우 리스트 뷰 갱신을 위해 result로 보냄
                //클릭한 포지션도 같이 보냄

                //엑티비티 시작
                startActivityForResult(intent, AlbaLibrary.REQUEST_CODE_ALBA_EDIT_DEFAULT);
            }
        });


        return v;
    }

    //메모 유형 추가 창 팝업용 메서드
    private void insertAlba() {

        //창 호출 하기위해 인텐드 생성
        Intent intent = new Intent(getActivity(), AlbaEditActivity.class);

        //인덱스 입력
        intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_INDEX,
                AlbaLibrary.ALBA_EDIT_INSERT_MODE);

        //Flag 입력
        intent.putExtra(AlbaLibrary.KEY_INTENT_ALBA_INSERT_FLAG, true);


        //변경사항이 있을경우 리스트 뷰 갱신을 위해 result로 보냄
        //클릭한 포지션도 같이 보냄

        //엑티비티 시작
        startActivityForResult(intent, AlbaLibrary.REQUEST_CODE_ALBA_EDIT_DEFAULT);
    }

    //핸들러에게 메세지 보내는 메서드
    public void handleMessage(int what, int position, AlbaItemModel item) {

        //메세지 객체 선언
        Message alba_message = albaHandler.obtainMessage();
        //메세지 연결
        alba_message.what = what;

        //자료 전송용 번들 객체 선언
        Bundle args = new Bundle();
        //인덱스 입력
        args.putInt(AlbaLibrary.KEY_LIST_VIEW_INDEX, position);
        //MemoDBModel 입력
        args.putSerializable(AlbaLibrary.KEY_ALBA_LIST_VIEW_ITEM, item);
        //데이터 입력
        alba_message.setData(args);
        //메세지 전송
        albaHandler.sendMessage(alba_message);
    }

    //UI 제어용 핸들러
    private Handler albaHandler = new Handler() {
        public void handleMessage(Message msg) { //인터페이스구현

            switch (msg.what) { //전달 받은 메세지 분류
                case AlbaLibrary.INSERT_ALBA: //0번 타입 입력
                    //신규 유형 처리
                    insertAlba(msg);
                    break;

                case AlbaLibrary.UPDATE_ALBA: //1번 타입 수정
                    //메모 수정이 있을 경우
                    updateAlba(msg);
                    break;

                case AlbaLibrary.DELETE_ALBA: //2번 타입 삭제
                    //메모가 지워졌을 경우
                    deleteAlba(msg);
                    break;
            }
        }

        //리스트뷰 갱신
        //신규 메모
        private void insertAlba(Message msg) {

            //메세지로부터 넘어온 객체 할당
            AlbaItemModel Item
                    = (AlbaItemModel)msg.getData()
                    .getSerializable(AlbaLibrary.KEY_ALBA_LIST_VIEW_ITEM);

            //모델 정보를 저장할 변수 선언
            Long db_id;
            String albaName = null;
            String albaColor = null;
            int albaPay;
            String albaStartTime = null;
            String albaEndTime = null;


            //모델에서 정보 받음
            albaName = Item.getAlba_name();
            albaColor = Item.getAlba_color();
            albaPay = Item.getAlba_pay();
            albaStartTime = Item.getAlba_startTime();
            albaEndTime = Item.getAlba_endTime();

            //DB에서 DBID얻음
            db_id = albaDBManager.insertAlba(albaName, albaColor,
                    albaPay, albaStartTime, albaEndTime);

            //리스트 뷰에 반영하기 위해 해당 아이템 추가
            albaArrayList.add(new AlbaItemModel(
                    db_id,
                    albaName,
                    albaColor,
                    albaPay,
                    albaStartTime,
                    albaEndTime));

            //리스트뷰 새로 고침
            refreshListView();

            //Toast 출력
            Toast.makeText(getActivity().getApplication(), getResources().getString(
                    R.string.toast_alba_insert), Toast.LENGTH_LONG).show();
        }

        //타입 업데이트
        private void updateAlba(Message msg) {

            //메세지로 부터 데이터 얻음
            int index = msg.getData().getInt(AlbaLibrary.KEY_LIST_VIEW_INDEX);

            //메세지로부터 넘어온 객체 할당
            AlbaItemModel Item
                    = (AlbaItemModel)msg.getData()
                    .getSerializable(AlbaLibrary.KEY_ALBA_LIST_VIEW_ITEM);

            //리스트에 업데이트
            albaArrayList.set(index, Item);

            //DB에 입력
            albaDBManager.albaUpdate(
                    Item.getAlba_dbID(),
                    Item.getAlba_name(),
                    Item.getAlba_color(),
                    Item.getAlba_pay(),
                    Item.getAlba_startTime(),
                    Item.getAlba_endTime()
            );

            //리스트뷰 새로고침
            refreshListView();
            //Toast 출력
            Toast.makeText(getActivity().getApplication(), getResources()
                    .getString(R.string.toast_alba_update), Toast.LENGTH_LONG).show();
        }

        //타입 삭제
        private void deleteAlba(Message msg) {

            //메세지로부터 지울 위치 얻음
            int index = msg.getData().getInt(AlbaLibrary.KEY_LIST_VIEW_INDEX);
            //리스트에서 메모 삭제
            albaArrayList.remove(index);

            //지울 메모 유형의 객체 얻음
            AlbaItemModel Item
                    = (AlbaItemModel)msg.getData()
                    .getSerializable(AlbaLibrary.KEY_ALBA_LIST_VIEW_ITEM);

            //객체로부터 DB ID 얻은 후 삭제
            albaDBManager.albaDeleteByID(Item.getAlba_dbID());

            //리스트뷰 새로고침
            refreshListView();

            //Toast 출력
            Toast.makeText(getActivity().getApplication(), getResources()
                    .getString(R.string.toast_alba_delete), Toast.LENGTH_LONG).show();
        }

        //리스트 갱신 메서드
        private void refreshListView() {

            albaAdapter.notifyDataSetChanged();
            regiAlbaListView.refreshDrawableState();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //resultcode에서 객체 얻음
        AlbaItemModel item = (AlbaItemModel)
                data.getSerializableExtra(AlbaLibrary.KEY_INTENT_ALBA_GET_ITEM_MODEL);

        //눌럿던 위치 정보 얻음
        int position = data.getIntExtra(AlbaLibrary.KEY_INTENT_ALBA_INDEX, 0);


        //ResultCode 처리 스위치문
        switch (resultCode){
            case AlbaLibrary.RESULT_CODE_ALBA_EDIT_DEFAULT :
                break;

            case AlbaLibrary.RESULT_CODE_ALBA_EDIT_INSERT :
                handleMessage(AlbaLibrary.INSERT_ALBA, position, item);
                break;

            case AlbaLibrary.RESULT_CODE_ALBA_EDIT_UPDATE :
                handleMessage(AlbaLibrary.UPDATE_ALBA, position, item);
                break;

            case AlbaLibrary.RESULT_CODE_ALBA_EDIT_DELETE :
                handleMessage(AlbaLibrary.DELETE_ALBA, position, item);
                break;
        }
    }
}

