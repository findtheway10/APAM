package alba_manager.albamanager.Util;

/**
 * 상수, String 저장
 */
public interface AlbaLibrary {

    //TypeSettingActivity Handler Key
    public final static int INSERT_ALBA = 0;
    public final static int UPDATE_ALBA = 1;
    public final static int DELETE_ALBA = 2;

    //Alba ListView 기본값
    public final static String ALBA_NO_COLOR = "#cccccc";


    //bundle Key
    public final static String KEY_ALBA_LIST_VIEW_ITEM = "key_alba_list_view_item";
    public final static String KEY_LIST_VIEW_INDEX = "key_list_view_index";

    //IntentKEy
    public final static String KEY_INTENT_ALBA_PUT_ITEM_MODEL = "key_intent_alba_put_item_model";
    public final static String KEY_INTENT_ALBA_GET_ITEM_MODEL = "key_intent_alba_getitem_model";
    public final static String KEY_INTENT_ALBA_INDEX = "key_intent_alba_index";
    public final static String KEY_INTENT_ALBA_INSERT_FLAG = "key_intent_alba_insert_flag";
    public final static String KEY_INTENT_SCHEDULE_DATE = "key_intent_schedule_date";

    //When Insert Mode Position
    public final static int ALBA_EDIT_INSERT_MODE = -1;

    //resultcode
    public final static int RESULT_CODE_ALBA_EDIT_DEFAULT = 0;
    public final static int RESULT_CODE_ALBA_EDIT_INSERT = 1;
    public final static int RESULT_CODE_ALBA_EDIT_UPDATE = 2;
    public final static int RESULT_CODE_ALBA_EDIT_DELETE = 3;

    //requestcode
    public final static int REQUEST_CODE_ALBA_EDIT_DEFAULT = 0;


    //DB기본값
    //메모 유형 버튼 눌럿을 경우 기본값
    public final static Long ALBA_DEFAULT_SELECT = 1L;

    //날짜 포멧(달력 타이틀)
    public final static String DATE_FORMAT = "yyyy년 M월 d일";
    public final static String RECETNT_SEARCH_DATE_FORMAT = "yyyy/MMdd";
    public final static String TOKENIZER_SEPARATER = ":";
    public final static String DEFAULT_START_TIME = "00:00";
    public final static String DEFAULT_END_TIME = "00:00";

    //프레그먼트 이름
    public final static String DIALOG_FRAGMENT_MANAGER_ALBA_INSERT = "fragment_dialog_alba_insert";
    public final static String DIALOG_FRAGMENT_MANAGER_ALBA_EDIT = "fragment_dialog_alba_edit";
}
