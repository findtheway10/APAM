package alba_manager.albamanager.Model;

import java.io.Serializable;

/**
 * 스케쥴 아이템 모델
 */
public class ScheduleItemModel implements Serializable{

    private String schedule_date;
    private String schedule_name;
    private String schedule_color;
    private String schedule_startTime;
    private String schedule_endTime;
    private int schedule_pay;
    private Long schedule_albaDBId;
    private String schedule_memo;
    private int schedule_check;

    public ScheduleItemModel() {
    }

    public ScheduleItemModel(String schedule_date, String schedule_name, String schedule_color,
                             String schedule_startTime, String schedule_endTime, int schedule_pay,
                             Long schedule_albaDBId, String schedule_memo, int schedule_check) {

        this.schedule_date = schedule_date;
        this.schedule_name = schedule_name;
        this.schedule_color = schedule_color;
        this.schedule_startTime = schedule_startTime;
        this.schedule_endTime = schedule_endTime;
        this.schedule_pay = schedule_pay;
        this.schedule_albaDBId = schedule_albaDBId;
        this.schedule_memo = schedule_memo;
        this.schedule_check = schedule_check;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_color() {
        return schedule_color;
    }

    public void setSchedule_color(String schedule_color) {
        this.schedule_color = schedule_color;
    }

    public String getSchedule_startTime() {
        return schedule_startTime;
    }

    public void setSchedule_startTime(String schedule_startTime) {
        this.schedule_startTime = schedule_startTime;
    }

    public String getSchedule_endTime() {
        return schedule_endTime;
    }

    public void setSchedule_endTime(String schedule_endTime) {
        this.schedule_endTime = schedule_endTime;
    }

    public int getSchedule_pay() {
        return schedule_pay;
    }

    public void setSchedule_pay(int schedule_pay) {
        this.schedule_pay = schedule_pay;
    }

    public Long getSchedule_albaDBId() {
        return schedule_albaDBId;
    }

    public void setSchedule_albaDBId(Long schedule_albaDBId) {
        this.schedule_albaDBId = schedule_albaDBId;
    }

    public String getSchedule_memo() {
        return schedule_memo;
    }

    public void setSchedule_memo(String schedule_memo) {
        this.schedule_memo = schedule_memo;
    }

    public int getSchedule_check() {
        return schedule_check;
    }

    public void setSchedule_check(int schedule_check) {
        this.schedule_check = schedule_check;
    }
}
