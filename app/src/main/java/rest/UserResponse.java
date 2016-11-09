package rest;


import com.google.gson.annotations.SerializedName;

/**
 * 회원가입/로그인의 response 값들
 */
public class UserResponse {

    private User user;
    private String error;
    public String error_msg;
    //private String tag;

    public UserResponse(String tag, String error, User results){
        //this.tag = tag;
        this.error = error;
        this.user = results;
        this.error_msg = error_msg;

    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
    /*
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }*/

}
