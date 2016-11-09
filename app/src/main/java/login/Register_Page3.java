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

public class Register_Page3 extends Activity {

    private String fb_id="";
    private String kt_id="";
    private String email="";
    private String name="";
    private String password="";
    private String gender="";
    private String phone_number="";
    private String nick_name="";
    private String profile_img="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        name = intent.getExtras().getString("name");
        password = intent.getExtras().getString("password");
        gender = intent.getExtras().getString("gender");


        Set_FullSize_Background(this);

        final EditText nick_name_edit_box = (EditText)findViewById(R.id.nick_name_edit_box);
        final EditText phone_num_edit_box = (EditText)findViewById(R.id.phone_num_edit_box);
        Button register_next_btn = (Button)findViewById(R.id.register_next_btn);
        register_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick_name = nick_name_edit_box.getText().toString();
                phone_number = phone_num_edit_box.getText().toString();

                if(nick_name.equals("") || phone_number.equals("")){
                    Toast.makeText(getApplicationContext(),"정보를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(phone_number.contains("-") || (phone_number.length()!=11)){
                    Toast.makeText(getApplicationContext(), "올바른 폰번호 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("user", fb_id);
                    Log.d("user", kt_id);
                    Log.d("user", email);
                    Log.d("user", name);
                    Log.d("user", password);
                    Log.d("user", gender);
                    Log.d("user", nick_name);
                    Log.d("user", phone_number);
                    Log.d("user", profile_img);
                    //회원가입 POST
                    PostRegisterUser(fb_id, kt_id, email, name, password, gender, nick_name, phone_number, "123");
                }
            }
        });



    }

    private void PostRegisterUser(String fb_id, String kt_id, String email, String name, String password, String gender, String nick_name, String phone_number, String profile_img){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<UserResponse> call = apiService.postRegister("register",fb_id, kt_id, name, gender, email, nick_name, password, phone_number, profile_img);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse user1 = response.body();

                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), user1.getError(),Toast.LENGTH_SHORT).show();
                Log.e("tag", user1.getError());
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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.register_page3_background);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, w, h, true);

        Drawable d = new BitmapDrawable(getResources(), resized);
        ViewGroup pager_child = (ViewGroup)findViewById(R.id.register_page3_layout);
        pager_child.setBackground(d);
    }

}
