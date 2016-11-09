package app_controller;

public class AppConfig {

    public static String SERVER_IP = "http://ustserver.cafe24.com/ust/";//서버 아이피  한번에 바꾸기 좋음

    public String DATABASE_NAME = "egg_slide";

    public String get_SERVER_IP(){
        return this.SERVER_IP;
    }
    public String getDATABASE_NAME(){
        return this.DATABASE_NAME;
    }
}