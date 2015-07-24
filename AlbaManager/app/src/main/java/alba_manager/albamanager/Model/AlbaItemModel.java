package alba_manager.albamanager.Model;

import java.io.Serializable;

/**
 * 알바 아이템 모델
 */
public class AlbaItemModel implements Serializable{

    private Long alba_dbID;
    private String alba_name;
    private String alba_color;
    private int alba_pay;
    private String alba_startTime;
    private String alba_endTime;

    public AlbaItemModel() {
    }

    public AlbaItemModel(Long alba_dbID, String alba_name, String alba_color, int alba_pay, String alba_startTime, String alba_endTime) {
        this.alba_dbID = alba_dbID;
        this.alba_name = alba_name;
        this.alba_color = alba_color;
        this.alba_pay = alba_pay;
        this.alba_startTime = alba_startTime;
        this.alba_endTime = alba_endTime;
    }

    public Long getAlba_dbID() {
        return alba_dbID;
    }

    public void setAlba_dbID(Long alba_dbID) {
        this.alba_dbID = alba_dbID;
    }

    public String getAlba_name() {
        return alba_name;
    }

    public void setAlba_name(String alba_name) {
        this.alba_name = alba_name;
    }

    public String getAlba_color() {
        return alba_color;
    }

    public void setAlba_color(String alba_color) {
        this.alba_color = alba_color;
    }

    public int getAlba_pay() {
        return alba_pay;
    }

    public void setAlba_pay(int alba_pay) {
        this.alba_pay = alba_pay;
    }

    public String getAlba_startTime() {
        return alba_startTime;
    }

    public void setAlba_startTime(String alba_startTime) {
        this.alba_startTime = alba_startTime;
    }

    public String getAlba_endTime() {
        return alba_endTime;
    }

    public void setAlba_endTime(String alba_endTime) {
        this.alba_endTime = alba_endTime;
    }



}
