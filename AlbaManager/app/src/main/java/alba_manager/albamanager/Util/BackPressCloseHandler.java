package alba_manager.albamanager.Util;

import android.app.Activity;
import android.widget.Toast;

import alba_manager.albamanager.R;
/**
 * @author 김대경
 *         날짜 : 2015. 07. 16.
 *         Back 두번눌럿을때 동작 처리용 클레스
 */
public class BackPressCloseHandler {

    //시간체크용 변수
    private long backKeyPressedTime = 0;

    //토스트 제어를 하기위해 객체 선언
    private Toast toast;

    private Activity activity;

    //생성자
    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    //두번 눌럿을 경우 처리
    public void onBackPressed() {
        //처음 눌럿을 경우
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        //두번 째 눌럿을 경우
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    //토스트 출력용 메서드
    private void showGuide() {
        toast = Toast.makeText(activity, activity.getResources().getString(R.string.toast_back_key), Toast.LENGTH_SHORT);
        toast.show();
    }
}