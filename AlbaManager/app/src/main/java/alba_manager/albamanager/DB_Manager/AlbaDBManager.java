package alba_manager.albamanager.DB_Manager;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import alba_manager.albamanager.DB_Model.AlbaDBModel;
import alba_manager.albamanager.Model.AlbaItemModel;
import alba_manager.albamanager.Util.AlbaLibrary;

/**
 *
 */
public class AlbaDBManager {


    /**
     * @return : ArrayList<MemoTypeItemModel>
     */
    //모든 유형을 반환
    public ArrayList<AlbaItemModel> getAlbaList() {

        //메모 아이템 ArrayList
        ArrayList<AlbaItemModel> arrayList = new ArrayList<AlbaItemModel>();

        //DB에서 모든 정보를 가져옴
        List<AlbaDBModel> albaList = getAllList();

        //리스트 사이즈가 0 아닐경우 리스트 정상 출력
            for (AlbaDBModel m : albaList) {

                //ArrayList에 입력
                arrayList.add(new AlbaItemModel(
                        m.getId(),
                        m.alba_name,
                        m.alba_color,
                        m.alba_pay,
                        m.alba_startTime,
                        m.alba_endTime
                ));

            }


        return arrayList;
    }

    /**
     *     @return : Long ID (DB ID)
     */
    //신규 타입 삽입 메서드 (타입 부분 나중에 추가 해야함)
    public java.lang.Long insertAlba(String name, String color,
                                     int pay, String startTime, String entTime){

        //memo 테이블 모델 생성
        AlbaDBModel albaDBModel = new AlbaDBModel();
        albaDBModel.alba_name = name;
        albaDBModel.alba_color = color;
        albaDBModel.alba_pay = pay;
        albaDBModel.alba_startTime = startTime;
        albaDBModel.alba_endTime = entTime;

        //저장
        albaDBModel.save();

        return albaDBModel.getId();

    }

    /**
     *     타입 수정용 메서드
     */
    //타입 수정용 메서드
    public void albaUpdate(Long id, String name, String color,
                           int pay, String startTime, String entTime){

        //memo 테이블 모델 생성
        AlbaDBModel albaDBModel = AlbaDBModel.load(AlbaDBModel.class, id);
        albaDBModel.alba_name = name;
        albaDBModel.alba_color = color;
        albaDBModel.alba_pay = pay;
        albaDBModel.alba_startTime = startTime;
        albaDBModel.alba_endTime = entTime;

        //저장
        albaDBModel.save();
    }

    /**
     *     입력값 : DB ID
     *     유형을 삭제
     */
    //유형 삭제용 메서드
    public void albaDeleteByID (Long id){

        //입력 받은 DB ID로 유형 모델 객체 로드
        AlbaDBModel albaDBModel = AlbaDBModel.load(AlbaDBModel.class, id);

        //모델 삭제
        albaDBModel.delete();
    }

    /**
     *     @return : Long ID (TYPE ID)
     *     입력받은 유형에 대한 정보 반환
     */
    //입력받은 유형에 대한 정보 반환
    public AlbaDBModel getAlbaModelByDBID(Long type_id){
        AlbaDBModel albaDBModel;
        try {
            //DB에서 유형 모델 객체 받아옴
            albaDBModel = getAlbaByDBID(type_id).get(0);

            return albaDBModel;

        }catch (IndexOutOfBoundsException e){
            //유형이 삭제등의 이유로 DB에 없을경우 기본값 출력
            albaDBModel = getAlbaByDBID(AlbaLibrary.ALBA_DEFAULT_SELECT).get(0);

            return albaDBModel;
        }

    }

    //유형 리스트를 가져옴
    private List<AlbaDBModel> getAllList() {

        //초기화
        return new Select()
                .from(AlbaDBModel.class)
                .execute();
    }

    //DB ID로 메모를 찾아 오는 메서드
    private List<AlbaDBModel> getAlbaByDBID (Long type_id){
        return new Select()
                .from ( AlbaDBModel.class )
                .where("ID = ?", type_id)
                .execute();

    }
}
