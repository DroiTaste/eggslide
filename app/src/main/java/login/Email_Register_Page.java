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
import android.widget.Toast;

import rest.ApiClient;
import rest.ApiInterface;
import rest.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultra.seed.eggslide.R;

public class Email_Register_Page extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_register_page);

        Set_FullSize_Background(this);

        final EditText email_edit_box = (EditText)findViewById(R.id.email_edit_box);      //email입력창
        final EditText pw_edit_box = (EditText)findViewById(R.id.pw_edit_box);        //비밀번호
        final EditText pw_edit_box2 = (EditText)findViewById(R.id.pw_edit_box2);      //비밀번호 확인

        //다음 버튼 이벤트
        Button register_next_btn = (Button)findViewById(R.id.register_next_btn);
        register_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str = email_edit_box.getText().toString();
                String password_str = pw_edit_box.getText().toString();
                String password_str2 = pw_edit_box2.getText().toString();

                if(email_str.equals("") || password_str.equals("") || password_str2.equals("")){
                    Toast.makeText(getApplicationContext(), "정보를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if( !email_str.contains("@") || !(email_str.contains(".com")||email_str.contains(".net")) ){
                    Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                }else if((password_str.length()<6) || (password_str2.length()<6)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                }else if(!password_str.equals(password_str2)){
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
                }else{
                    //다음 화면으로 진입
                    //email, password 넘김
                    Intent intent = new Intent(getApplicationContext(), Register_Page2.class);
                    intent.putExtra("email", email_str);
                    intent.putExtra("password", password_str);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }
        });




        /*
        ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<UserResponse> call = apiService.postRegister("register","3", "3", "3", "3", "3", "3", "3", "3", "3");
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse user1 = response.body();
                        Toast.makeText(getApplicationContext(), user1.getError(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), user1.getResults().getName(),Toast.LENGTH_SHORT).show();
                        Log.e("tag111", user1.getError());
                        //Log.e("tag111", user1.getError_msg());
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("tag", t.toString());
                    }
                });
         */

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
        ViewGroup pager_child = (ViewGroup)findViewById(R.id.email_register_page_layout);
        pager_child.setBackground(d);
    }

}
