package ajou.com.skechip;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import ajou.com.skechip.Fragment.EP_Fragment;
import ajou.com.skechip.Fragment.FriendListFragment;
import ajou.com.skechip.Fragment.HomeFragment;
import ajou.com.skechip.Fragment.MeetingFragment;
import ajou.com.skechip.Fragment.TimeTableFragment;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.friends.response.model.AppFriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ajou.com.skechip.Fragment.EP_Fragment;
import ajou.com.skechip.R;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ssss.MainActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();

//    private HomeFragment homeFragment = new HomeFragment();
    private TimeTableFragment timeTableFragment = new TimeTableFragment();
    private FriendListFragment friendListFragment = new FriendListFragment();
    private MeetingFragment meetingFragment = new MeetingFragment();
    private EP_Fragment epFragment = new EP_Fragment();

    private Fragment active;

    private Long kakaoUserID;
    private String kakaoUserProfileImg;
    private String kakaoUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestMe();

        //for bottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.frame_layout, meetingFragment).hide(meetingFragment);
        transaction.add(R.id.frame_layout, friendListFragment).hide(friendListFragment);
        transaction.add(R.id.frame_layout, epFragment);
        transaction.commit();

        active = epFragment;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        transaction.replace(R.id.frame_layout,homeFragment).commitAllowingStateLoss();
//                        break;
                    case R.id.navigation_timetable:
                        transaction.hide(active).show(epFragment);
                        transaction.commit();
                        active = epFragment;
                        break;
                    case R.id.navigation_meeting:
                        transaction.hide(active).show(meetingFragment);
                        transaction.commit();
                        active = meetingFragment;
                        break;
                    case R.id.navigation_friends:
                        transaction.hide(active).show(friendListFragment);
                        transaction.commit();
                        active = friendListFragment;
                        break;
                }
                return true;
            }
        });


    }


    private void requestMe() {
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<String> keys = new ArrayList<>();
                keys.add("properties.nickname");
                keys.add("properties.profile_image");
                keys.add("kakao_account.email");

                UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        String message = "failed to get user info. msg=" + errorResult;
                        Logger.d(message);
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
//                redirectLoginActivity();
                    }

                    @Override
                    public void onSuccess(MeV2Response response) {
                        kakaoUserName = response.getNickname();
                        kakaoUserID = response.getId();
                        kakaoUserProfileImg = response.getProfileImagePath();

                        Log.d(TAG, "user id :" + kakaoUserID);
                        Log.d(TAG, "user name :" + kakaoUserName);
                        Log.d(TAG, "user profile img : " + kakaoUserProfileImg);
                    }

//            @Override
//            public void onNotSignedUp() {
//                showSignup();
//            }
                });
            }
        });
    }


    public void onclick(View view) {
        
    }
}
