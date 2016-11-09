package login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import rest.ApiClient;
import rest.ApiInterface;
import rest.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ultra.seed.eggslide.R;

/**
 * Created by morion on 2016-10-20.
 */

public class Find_EmailPassword extends Activity {

    UserResponse user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_emailpassword);


        Set_FullSize_Background(this);

        final EditText phone_num_edit_box = (EditText)findViewById(R.id.phone_num_edit_box);  //폰넘버 edittext
        final EditText email_edit_box = (EditText)findViewById(R.id.email_edit_box);  //이메일 edittext
        Button find_id_btn = (Button)findViewById(R.id.find_id_btn);  //아이디 찾기 버튼
        Button find_pw_btn = (Button)findViewById(R.id.find_pw_btn);  //비밀번호 이메일 전송 버튼

        //이메일 찾기 버튼 이벤트
        find_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone_number = phone_num_edit_box.getText().toString();

                if(phone_number.equals("")){
                    Toast.makeText(getApplicationContext(), "정보를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(phone_number.contains("-") || (phone_number.length()!=11)){
                    Toast.makeText(getApplicationContext(), "올바른 폰번호 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                }else{

                    //DB에서 폰번호로 이메일 주소 검색해서 toast박스 띄우기
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<UserResponse> call = apiService.postRegister("find", null, null, null, null, null, null, null, phone_number, null);
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                            user1 = (UserResponse)response.body();

                            Toast.makeText(getApplicationContext(), "이멜 찾기 에러 메세지 : " + user1.getError(), Toast.LENGTH_SHORT).show();

                            //처리한 결과 user1.getError()가 'FALSE'-> 이메일 찾음
                            if(user1.getError().equals("FALSE") || user1.getError().equals("false")) {


                                Toast.makeText(getApplicationContext(), user1.getError_msg(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), user1.getUser().getEmail(), Toast.LENGTH_SHORT).show();

                                //Log.e("tag111", user1.getError());
                                //Log.e("tag111", user1.getError_msg());

                            //처리한 결과 user1.getError()가 'TRUE'-> 이메일 못 찾음
                            } else if(user1.getError().equals("TRUE") || user1.getError().equals("true")){
                                Toast.makeText(getApplicationContext(), user1.getError_msg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            // Log error here since request failed
                            Log.e("tag_fail", t.toString());
                        }

                    });

                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }
        });

        //패스워드 이메일 전송 버튼 이벤트
        find_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email_str = email_edit_box.getText().toString();

                if(email_str.equals("")){
                    Toast.makeText(getApplicationContext(), "정보를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if( !email_str.contains("@") || !(email_str.contains(".com")||email_str.contains(".net")) ){
                    Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                }else{

                    //DB에서 이메일 주소 해당하는 사람 검색하기
                    //PW임의 생성해서 DB저장 해두기
                    //임의 생성한 PW 이메일로 전송하기
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    //임시비번
                    String new_password = "kokoko";

                    Call<UserResponse> call = apiService.postRegister("find", null, null, email_str, new_password, null, null, null, null, null);

                    Toast.makeText(getApplicationContext(), "[비번 찾기] [1]", Toast.LENGTH_SHORT).show();

                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            user1 = (UserResponse)response.body();

                            Toast.makeText(getApplicationContext(), "[비번 찾기] [2]", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "비번 찾기 에러 메세지 : " + user1.getError(), Toast.LENGTH_SHORT).show();

                            //처리한 결과 user1.getError()가 'FALSE'-> 비번 이메일로 발송 성공
                            if(user1.getError().equals("FALSE")) {

                                Toast.makeText(getApplicationContext(), email_str + " 로 이메일을 발송했습니다.", Toast.LENGTH_SHORT).show();

                            //처리한 결과 user1.getError()가 'TRUE'-> 비번 이메일로 발송 실패
                            } else if(user1.getError().equals("TRUE")){
                                Toast.makeText(getApplicationContext(), user1.getError_msg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            // Log error here since request failed

                            Toast.makeText(getApplicationContext(), "[비번 찾기] [3]", Toast.LENGTH_SHORT).show();

                            Log.e("tag", t.toString());
                        }
                    });

                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }
        });

    }

    private void Set_FullSize_Background(Context context){
        int w;
        int h;
        Display display;
        display = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        w = display.getWidth();
        h = display.getHeight();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.email_login_background);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, w, h, true);

        Drawable d = new BitmapDrawable(getResources(), resized);
        ViewGroup pager_child = (ViewGroup)findViewById(R.id.find_emailpassword_layout);
        pager_child.setBackground(d);
    }

}
