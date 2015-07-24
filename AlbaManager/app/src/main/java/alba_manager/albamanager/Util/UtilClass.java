package alba_manager.albamanager.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2015-07-21.
 * 유틸 함수
 */
public class UtilClass {


    /**
     * 공백과 빈칸 을 검사
     * @return boolean
     */
    //공백 검사
    public boolean isBlankOrSpacing(String text) {
        if(text.equals("") || text.matches("\\s+")){
            return true;
        }
        return false;
    }

    /**
     * 입력 받은 시간과 분을 더해서 String으로 만들어줌
     * @return String
     */
    //공백 검사
    public String getStringTime(int hour, int min){

        String totalTime;
        String hourZero = "";
        String minZero = "";

        if(hour < 10){
            hourZero = "0";
        }
        if(min < 10){
            minZero = "0";
        }
        totalTime = hourZero + hour + AlbaLibrary.TOKENIZER_SEPARATER + minZero + min;

        return totalTime;
    }

    /**
     * 시간을 구해서 지정한 포멧에 맞춰 반환
     * @return String
     */
    //현재 시간 구하는 메서드,parm1의 값을 parm2의 형식으로 출력
    public String getTime(Long nowDate, String format){

        Long tempTime = nowDate;

        // 현재 시간을 저장 한다.
        Date date = new Date(tempTime);
        // 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat(format);
        String strCurDate = CurDateFormat.format(date);

        return strCurDate;
    }
}
