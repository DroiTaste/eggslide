package ultra.seed.eggslide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import login.Login_Page;

public class test extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test, container, false);

        //현재 로그인/회원가입/자동로그인 부분이 구현되지 않은 상태라 일단은 로그인 버튼을 누르면 로그인화면으로 이동되게 해둠.
        Button login_btn = (Button)v.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login_Page.class));
            }
        });
        return v;
    }

}
