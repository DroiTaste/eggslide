package rest;


/**
 * 사용자의 모든 정보
 *
 */
public class User {

    private String uid;
    private String fb_id;
    private String kt_id;
    private String name;
    private String gender;
    private String email;
    private String nick_name;
    private String phone_number;
    private String profile_img;
    private String created_at;



    public User(String uid, String fb_id, String kt_id, String name, String gender, String email, String nick_name, String phone_num, String profile_img, String created_at){
        this.uid = uid;
        this.fb_id = fb_id;
        this.kt_id = kt_id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.nick_name = nick_name;
        this.phone_number = phone_num;
        this.profile_img = profile_img;
        this.created_at = created_at;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFb_id() {
        return fb_id;
    }
    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getKt_id() {
        return kt_id;
    }
    public void setKt_id(String kt_id) {
        this.kt_id = kt_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick_name() {
        return nick_name;
    }
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone_num() {
        return phone_number;
    }
    public void setPhone_num(String phone_num) {
        this.phone_number = phone_num;
    }

    public String getProfile_img() {
        return profile_img;
    }
    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}
