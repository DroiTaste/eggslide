package ultra.seed.eggslide;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;

import login.kakao_login_support.Kakao_SDK_Adapter;

/**
 * Created by morion on 2016-10-16.
 */

public class GlobalApplication extends Application {

    //KakaoLogin
    //KakaoSDKAdaptert_관련변수
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //KakaoLogin
        //KakaoSDKAdaptert 생성
        obj = this;
        KakaoSDK.init(new Kakao_SDK_Adapter());
    }

    //KakaoLogin
    //Kakao_SDK_Adapter GetSet
    public static GlobalApplication getMainApplicationContext() {
        return obj;
    }
    public static Activity getCurrentActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        //KakaoLogin
        //애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
        obj = null;
    }
}
