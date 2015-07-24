package alba_manager.albamanager.DB_Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author 김대경
 * 생성일 : 2015. 07. 20.
 * ScheduleDBModel
 */

//Table 스케쥴
@Table(name = "Schedule")
public class ScheduleDBModel extends Model {

    //date (날짜)
    @Column(name = "schedule_date")
    public String schedule_date;

    //name (스케쥴 이름)
    @Column(name = "schedule_name")
    public String schedule_name;

    //color (색상)
    @Column(name = "schedule_color")
    public String schedule_color;

    //date (startTime)
    @Column(name = "schedule_startTime")
    public String schedule_startTime;

    //date (endTime)
    @Column(name = "schedule_endTime")
    public String schedule_endTime;

    //pay (시급)
    @Column(name = "schedule_pay")
    public int schedule_pay;

    //date (알바 DB ID)
    @Column(name = "schedule_albaDBId")
    public Long schedule_albaDBId;

    //date (메모)
    @Column(name = "schedule_memo")
    public String schedule_memo;

    //date (출근 확인)
    @Column(name = "schedule_check")
    public int schedule_check;


    public ScheduleDBModel() {
        super();
    }


}