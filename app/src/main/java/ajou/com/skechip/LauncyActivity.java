package ajou.com.skechip;;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ajou.com.skechip.MainActivity;
import ajou.com.skechip.R;

public class LauncyActivity extends AppCompatActivity {

    private final String TAG = "ssss.LaunchActivity";

    private Long kakaoUserID;
    private String kakaoUserProfileImg;
    private String kakaoUserName;

    //for kakao API
    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcy);

        //for kakao API
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
        //kakao

        String idx = getPreferences("idx");
        String username = getPreferences("username");
        String password = getPreferences("password");
        Boolean login = getPreferencesBoolean("login");

        if(idx != "" && username != "" && login)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            intent.putExtra("idx", idx);

            finish();
            startActivity(intent);
        }
    }//onCreate

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    //for kakao API
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            //로그인 완료 직 후 session 열림
            Log.d(TAG, "onSessionOpened");
            redirectSignupActivity();
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    //로그인 완료 후 시작할 액티비티
    protected void redirectSignupActivity() {
        Log.d(TAG, "redirectSignupActivity");
        final Intent intent = new Intent(this, MainActivity.class);

        //MainActivity에 가져갈 intent 생성 - kakao 본인 프로필 정보 포함
//        requestMe();
        //TODO: 현재 requestMe()를 통해 로그인 후 프로필 정보를 얻어오는 것 까지는 구현, but requestSignup을 통해 앱 연결 해야함

        //onClickSignup을 해야 사용자의 고유한 ID가 부여된다고 써있음(kakao developers), 근데 구현 안해도 사용자 ID 불러옴 why?
//        onClickSignup();

//        Log.d(TAG, "user id :" + kakaoUserID);
//        Log.d(TAG, "user name :" + kakaoUserName);
//        Log.d(TAG, "user profile img : " + kakaoUserProfileImg);

        startActivity(intent);
        finish();
    }

    private void onClickSignup() {
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put("nickname", "leo");
        properties.put("age", "33");

        UserManagement.getInstance().requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(TAG, "onSessionClosed");
            }

            @Override
            public void onNotSignedUp() {
                Log.d(TAG, "onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.d(TAG, "onClickSignup - ID :" + result);
            }
        }, properties);

    }


    private void requestMe() {
        Log.d(TAG, "requestMe");
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d(TAG, message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response response) {
                Log.d(TAG,"requestMe().onSuccess");
                Log.d(TAG,"user id : " + response.getId());
                Log.d(TAG,"profile image: " + response.getProfileImagePath());

                kakaoUserName = response.getNickname();
                kakaoUserID = response.getId();
                kakaoUserProfileImg = response.getProfileImagePath();
            }

//            @Override
//            public void onNotSignedUp() {
//                showSignup();
//            }
        });
    }
    //kakao API, LJH


    protected void showErrorDialog(String message) { }

    // 값(Key Data) 삭제하기
    private void removePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hi");
        editor.commit();
    }

    public void savePreferences(String key, String value)
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value).commit();
    }

    public void savePreferencesBoolean(String key, boolean value){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value).commit();
    }

    public String getPreferences(String key)
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public boolean getPreferencesBoolean(String key){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }





}//LauncyActivity
