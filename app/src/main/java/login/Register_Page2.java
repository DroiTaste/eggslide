package login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

public class Register_Page2 extends Activity {

    //남자/여자 선택 구분
    private boolean male_selected = false;
    private boolean female_selected = false;

    //사용자 이름
    private String name = "";
    //사용자 성별
    private String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);

        Intent intent = getIntent();
        final String email = intent.getExtras().getString("email");
        final String password = intent.getExtras().getString("password");


        Set_FullSize_Background(this);

        final EditText name_edit_box = (EditText)findViewById(R.id.name_edit_box);  //이름 edittext
        final Button male_select_btn = (Button)findViewById(R.id.male_select_btn);  //남자 선택 버튼
        final Button female_select_btn = (Button)findViewById(R.id.female_select_btn);  //여자 선택 버튼
        //남자 버튼 클릭 이벤트
        male_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_selected = true;
                female_selected = false;
                male_select_btn.setBackgroundResource(R.drawable.gender_selected_btn);
                male_select_btn.setTextColor(Color.BLACK);
                female_select_btn.setBackgroundResource(R.drawable.login_btn_transparent);
                female_select_btn.setTextColor(getResources().getColor(R.color.transparent));
                gender = "남자";
            }
        });
        //여자 버튼 클릭 이벤트
        female_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female_selected =  true;
                male_selected = false;
                female_select_btn.setBackgroundResource(R.drawable.gender_selected_btn);
                female_select_btn.setTextColor(Color.BLACK);
                male_select_btn.setBackgroundResource(R.drawable.login_btn_transparent);
                male_select_btn.setTextColor(getResources().getColor(R.color.transparent));
                gender = "여자";
            }
        });

        //다음 버튼 이벤트
        Button register_next_btn = (Button)findViewById(R.id.register_next_btn);
        register_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = name_edit_box.getText().toString();
                if((male_selected || female_selected) && !name.equals("")){
                    //email, password, gender, name 넘김
                    Intent intent = new Intent(getApplicationContext(),Register_Page3.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("gender", gender);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }else{
                    //남자 혹은 여자 선택이 안됫거나 이름이 비어있는 경우
                    Toast.makeText(getApplicationContext(),"정보를 입력해주세요", Toast.LENGTH_SHORT).show();
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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.register_page2_background);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, w, h, true);

        Drawable d = new BitmapDrawable(getResources(), resized);
        ViewGroup pager_child = (ViewGroup)findViewById(R.id.register_page2_layout);
        pager_child.setBackground(d);
    }

}
