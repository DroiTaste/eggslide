package login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import ultra.seed.eggslide.R;

public class Login_Page extends FragmentActivity {

    //Viewpager
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    //Facebook
    private CallbackManager callbackManager;
    private String id="";
    private String name="";
    private String email="";
    private String gender="";
    //KakaoLogin
    private KakaoSessionCallback kakaoCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login_page);

        initView();

        //KakaoLogin
        kakaoCallback = new KakaoSessionCallback();
        Session.getCurrentSession().addCallback(kakaoCallback);
//        Session.getCurrentSession().checkAndImplicitOpen();
//        onClickKakaoLogout();
    }

    private void initView(){
        //viewpager
        mViewPager = (ViewPager) findViewById(R.id.intro_pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        //하단 동그라미, 텍스트 초기화
        final ImageView select_circle1 = (ImageView) findViewById(R.id.circle_1);
        final ImageView select_circle2 = (ImageView) findViewById(R.id.circle_2);
        final TextView intro_title = (TextView) findViewById(R.id.intro_title);
        final TextView intro_text = (TextView) findViewById(R.id.intro_text);
        final ViewGroup login_layout = (ViewGroup) findViewById(R.id.login_layout);     //로그인 버튼들 레이아웃

        //이메일 로그인 버튼
        Button login_email_btn = (Button)findViewById(R.id.login_email_btn);
        login_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Email_Login_Page.class));
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        //이메일 회원가입 버튼
        Button register_email_btn = (Button)findViewById(R.id.register_email_btn);
        register_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Email_Register_Page.class));
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                mViewPager.setCurrentItem(position);
                mViewPager.setOffscreenPageLimit(2);
                login_layout.setVisibility(View.GONE);

                switch (position) {
                    case 0:
                        select_circle1.setImageResource(R.drawable.select_circle);
                        select_circle2.setImageResource(R.drawable.no_select_circle);
                        intro_title.setText(R.string.intro1_title);
                        intro_text.setText(R.string.intro1_text);
                        break;
                    case 1:
                        select_circle1.setImageResource(R.drawable.no_select_circle);
                        select_circle2.setImageResource(R.drawable.select_circle);
                        intro_title.setText(R.string.intro2_title);
                        intro_text.setText(R.string.intro2_text);
                        //마지막 화면에서만 로그인 버튼들 노출시킴
                        login_layout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //facebook login
        //LoginManager.getInstance().logOut(); -> logout
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_btn);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {//로그인이 성공되었을때 호출
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        try {

                                            id = (String) response.getJSONObject().get("id");//페이스북 아이디값
                                            name = (String) response.getJSONObject().get("name");//페이스북 이름
                                            email = (String) response.getJSONObject().get("email");//이메일
                                            gender = (String) response.getJSONObject().get("gender");//성별
                                            Toast.makeText(getApplicationContext(),id + name + email + gender, Toast.LENGTH_SHORT).show();
                                            if (gender.equals("male")) {
                                                gender = "남자";
                                            } else if (gender.equals("female")) {
                                                gender = "여자";
                                            }
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                            email = "";//아직 이유는 모르겠지만 일부 계정의 이메일을 못받아오는 경우가 있음. 그래서 임시로 빈값을 넣어줌
                                            gender = "";
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        /**
                         * 여기서 부터는 페이스북에서 가져온 정보(id(fb_id)로 db 조회를 하여 존재하면 이미 가입된 계정이므로 자동로그인, 존재하지 않으면
                         * 신규가입 화면으로 진입 되게 설정해야함
                         */
                        //일단은 무조건 회원가입 화면으로 진입되게 해둠(패스워드도 아직 스펙이 정해지지 않아 일단은 페북 고유아이디로 넘김)
                        //다음 화면으로 진입
                        Intent intent = new Intent(getApplicationContext(), Register_Page2.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", id);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login_Page.this, "로그인을 취소 하였습니다!", Toast.LENGTH_SHORT).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Login_Page.this, "에러가 발생하였습니다", Toast.LENGTH_SHORT).show();
                        // App code
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //KakaoLogin
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) { return; }

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //KakaoLogin
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성합니다.
            return IntroFragment.create(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                android.support.v4.app.FragmentManager fm = fragment.getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                //this.notifyDataSetChanged();
                ft.commitAllowingStateLoss();
            }
        }
        @Override
        public int getCount() {
            return 2;  // 총 2개의 page를 반환
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    //KakaoLogin
    //세션의 체크시 상태 변경에 따른 콜백.
    //세션이 오픈되었을 때, 세션이 닫혔을 때 세션 콜백을 넘기게 된다.
    private class KakaoSessionCallback implements ISessionCallback {

        //access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태.
        //일반적으로 로그인 후의 다음 activity로 이동한다.
        @Override
        public void onSessionOpened() {

//            Toast.makeText(Login_Page.this, "세션오픈되어있음!", Toast.LENGTH_SHORT).show();

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onSuccess(UserProfile userProfile) {

                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    long kakaoId = 0; kakaoId = userProfile.getId();
                    String nickName = userProfile.getNickname();
                    String profileImagePath = userProfile.getProfileImagePath();
                    String thumbnailImagePath = userProfile.getThumbnailImagePath();

//                    Toast.makeText(Login_Page.this, kakaoId + " " + nickName + " " + profileImagePath + " " + thumbnailImagePath, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login_Page.this, Email_Register_Page.class);
                    if( !(kakaoId == 0) ) { intent.putExtra("kt_id", kakaoId); }
                    if( !(nickName == null) ) { intent.putExtra("name", nickName); }
                    if( !(profileImagePath == null) ) { intent.putExtra("profile_img", profileImagePath); }

                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    finish();
                }
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);
                }
                @Override
                public void onSessionClosed(ErrorResult errorResult) { }
                @Override
                public void onNotSignedUp() { }

            });
        }

        //memory와 cache에 session 정보가 전혀 없는 상태.
        //일반적으로 로그인 버튼이 보이고 사용자가 클릭시 동의를 받아 access token 요청을 시도한다.
        @Override
        public void onSessionOpenFailed(KakaoException exception) {

            if(exception != null) {
                Logger.e(exception);
            }
        }
    }
}
