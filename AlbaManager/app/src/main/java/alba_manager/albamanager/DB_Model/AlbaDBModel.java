package alba_manager.albamanager.DB_Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author 김대경
 * 날짜 : 2015. 07. 20.
 * AlbaDBModel
 */

//Table 이름
@Table(name = "Alba")
public class AlbaDBModel extends Model{

    //name (아르바이트 이름)
    @Column(name = "alba_name")
    public String alba_name;

    //color (색상)
    @Column(name = "alba_color")
    public String alba_color;

    //시급 (시급)
    @Column(name = "alba_pay")
    public int alba_pay;

    //startTime (시작 시간)
    @Column(name = "alba_startTime")
    public String alba_startTime;

    //endTime (종료 시간)
    @Column(name = "alba_endTime")
    public String alba_endTime;


    public AlbaDBModel() {
        super();
    }


}
