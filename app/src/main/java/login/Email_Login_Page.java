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
import android.widget.TextView;
import android.widget.Toast;

import app_controller.SQLiteHandler;
import app_controller.SessionManager;
import rest.ApiClient;
import rest.ApiInterface;
import rest.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultra.seed.eggslide.R;

public class Email_Login_Page extends Activity {

    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_login_page);

        Set_FullSize_Background(this);

        //EmailLogin
        session = new SessionManager(getApplicationContext());

        final EditText email_edit_box = (EditText)findViewById(R.id.email_edit_box);      //email입력창
        final EditText pw_edit_box = (EditText)findViewById(R.id.pw_edit_box);        //비밀번호

        Button login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email_str = email_edit_box.getText().toString();
                String password_str = pw_edit_box.getText().toString();

                if( email_str.equals("") || password_str.equals("") ){
                    Toast.makeText(getApplicationContext(), "정보를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if( !email_str.contains("@") || !(email_str.contains(".com")||email_str.contains(".net")) ){
                    Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                }else if( (password_str.length()<6) ){
                    Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("user", email_str);
                    Log.d("user", password_str);
                    //로그인 POST
                    if(session.isLoggedIn()){
                        Toast.makeText(getApplicationContext(), "이미 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        PostRegisterUser(null, null, email_str, null, password_str, null, null, null, null);
                    }
                }
            }
        });


        //EmailPassword Find
        TextView link_to_search_id_pw_link = (TextView)findViewById(R.id.link_to_search_id_pw_link);  //아이디비번찾기 클릭시 넘기기]
        link_to_search_id_pw_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Find_EmailPassword.class);
                startActivity(intent);
            }
        });
    }

    //EmailLogin
    private void PostRegisterUser(String fb_id, String kt_id, String email, String name, String password, String gender, String nick_name, String phone_number, String profile_img){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<UserResponse> call = apiService.postRegister("login", fb_id, kt_id, name, gender, email, nick_name, password, phone_number, profile_img);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse user1 = response.body();

                //로그 및 메세지
                Toast.makeText(getApplicationContext(), user1.getError(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), user1.getError_msg(),Toast.LENGTH_SHORT).show();
                Log.e("tag", user1.getUser().getUid());
                Log.e("tag", user1.getUser().getFb_id());
                Log.e("tag", user1.getUser().getKt_id());
                Log.e("tag", user1.getUser().getName());
                Log.e("tag", user1.getUser().getGender());
                Log.e("tag", user1.getUser().getEmail());
                Log.e("tag", user1.getUser().getNick_name());
                Log.e("tag", user1.getUser().getPhone_num());
                Log.e("tag", user1.getUser().getProfile_img());
                Log.e("tag", user1.getUser().getCreated_at());
                Log.e("tag", user1.getError());

                //로그인 성공 시 세션 유지
                session.setLogin(true);
//                //로그인 성공 시 DB에 정보저장(추후 저장정보 추가예정)
//                db = new SQLiteHandler(getApplicationContext());
//                db.addUser( user1.getUser().getUid()
//                            , user1.getUser().getFb_id()
//                            , user1.getUser().getKt_id()
//                            , user1.getUser().getName()
//                            , user1.getUser().getGender()
//                            , user1.getUser().getEmail()
//                            , user1.getUser().getNick_name()
//                            , user1.getUser().getPhone_num()
//                            , user1.getUser().getProfile_img()
//                            , user1.getUser().getCreated_at() );

            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
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
        ViewGroup pager_child = (ViewGroup)findViewById(R.id.email_login_page_layout);
        pager_child.setBackground(d);
    }

}
