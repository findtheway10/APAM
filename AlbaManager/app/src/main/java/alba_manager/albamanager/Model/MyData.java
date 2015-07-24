package alba_manager.albamanager.Model;

/**
 * 데이타 객체 생성 클래스
 */
//TODO 삭제 예정 임시 클래스
public class MyData {
    private int color;
    private String num;
    private String name;
    private String time1;
    private String time2;
    private String memo;

    // MyData 생성자
    public MyData(int color, String num, String name, String time1, String time2, String memo){
        this.color = color;
        this.num = num;
        this.name = name;
        this.time1 = time1;
        this.time2 = time2;
        this.memo = memo;
    }

    public int getColor(){ return color; }
    public String getNum() { return num;}
    public String getName() {
        return name;
    }
    public String getTime1() { return time1; }
    public String getTime2() { return time2; }
    public String getMemo() {
        return memo;
    }

}