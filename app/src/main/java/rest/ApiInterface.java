package rest;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //회원가입
    @FormUrlEncoded
    @POST("login/login.php")
    Call<UserResponse> postRegister(@Field("tag") String tag
                                    , @Field("fb_id") String fb_id
                                    , @Field("kt_id") String kt_id
                                    , @Field("name") String name
                                    , @Field("gender") String gender
                                    , @Field("email") String email
                                    , @Field("nick_name") String nick_name
                                    , @Field("password") String password
                                    , @Field("phone_number") String phone_number
                                    , @Field("profile_img") String profile_img);
}