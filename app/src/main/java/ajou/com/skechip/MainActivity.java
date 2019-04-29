package ajou.com.skechip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import ajou.com.skechip.Fragment.EP_Fragment;
import ajou.com.skechip.Fragment.FriendListFragment;
import ajou.com.skechip.Fragment.MeetingFragment;
import ajou.com.skechip.Fragment.TimeTableFragment;
import ajou.com.skechip.Fragment.bean.FriendEntity;

import com.kakao.friends.AppFriendContext;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ssss.MainActivity";

    //for Fragment
    private FragmentManager fragmentManager = getSupportFragmentManager();
    //    private TimeTableFragment timeTableFragment = new TimeTableFragment();
//    private FriendListFragment friendListFragment = new FriendListFragment();
//    private MeetingFragment meetingFragment = new MeetingFragment();
//    private EP_Fragment epFragment = new EP_Fragment();
    private TimeTableFragment timeTableFragment;
    private FriendListFragment friendListFragment;
    private MeetingFragment meetingFragment;
    private EP_Fragment epFragment;

    private Fragment curActivatedFragment;

    //for kakao user info
    private Long kakaoUserID;
    private String kakaoUserImg;
    private String kakaoUserName;
    private MeV2Response kakaoUserInfo;

    //for friend list
    final AppFriendContext friendContext = new AppFriendContext(true, 0, 10, "asc");
    private List<AppFriendInfo> kakaoFriendInfo_list = new ArrayList<>();
    private List<String> friendsNickname_list = new ArrayList<>();
//    private ArrayList<FriendEntity> friendEntities = new ArrayList<>();

    //for Gallery - 충희
    private int GET_GALLERY_IMAGE = 200;
    private Mat img_input;
    private Mat img_output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_Main();
    }


    /*
    * init_Main() 함수
    * 1. kakao 계정의 친구 목록을 불러온다
    * 2. kakao 계정의 유저 정보를 불러온다
    * 3. 위 정보를 불러온 뒤에, fragment를 초기화하고 navigationView를 등록한다.
    * */
    public void init_Main() {
        KakaoTalkService.getInstance().requestAppFriends(friendContext,
                new TalkResponseCallback<AppFriendsResponse>() {
                    @Override
                    public void onNotKakaoTalkUser() {
                        //KakaoToast.makeToast(getApplicationContext(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
                        Logger.e("onNotKakao");
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //redirectLoginActivity();
                        Logger.e("onSessionClosed");
                    }

                    @Override
                    public void onNotSignedUp() {
                        //redirectSignupActivity();
                        Logger.e("onNotSignededup");
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e("onFailure: " + errorResult.toString());
                    }

                    @Override
                    public void onSuccess(AppFriendsResponse result) {
                        // 친구 목록
                        Logger.e("onSucess " + result.getFriends().toString());

                        Iterator iter = result.getFriends().iterator();
                        while (iter.hasNext()) {
                            AppFriendInfo next = (AppFriendInfo) iter.next();
                            friendsNickname_list.add(next.getProfileNickname());
                            kakaoFriendInfo_list.add(next);
//                            friendEntities.add(new FriendEntity(next.getId(), next.getProfileNickname(), next.getProfileThumbnailImage()));
                        }

                        if (friendContext.hasNext()) {
                            init_Main();
                        } else {
                            Logger.e("No next pages");
                            // 모든 페이지 요청 완료.

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

                                }

                                @Override
                                public void onSuccess(MeV2Response response) {
                                    kakaoUserInfo = response;
                                    initiateFragmentsAndNavigation();
                                }
                            });
                        }
                    }
                });
    }

    private void initiateFragmentsAndNavigation() {
        //for 8 between MainActivity <-> Fragments
        Bundle bundle = new Bundle();

        bundle.putString("kakaoUserProfileImg", kakaoUserInfo.getProfileImagePath());
        bundle.putString("kakaoUserName", kakaoUserInfo.getNickname());
        bundle.putLong("kakaoUserID", kakaoUserInfo.getId());

        bundle.putParcelableArrayList("kakaoFriendInfo_list", (ArrayList<? extends Parcelable>) kakaoFriendInfo_list);
        bundle.putStringArrayList("friendsNickname_list", (ArrayList<String>) friendsNickname_list);
//        bundle.putParcelableArrayList("friendEntities", (ArrayList<? extends Parcelable>) friendEntities);

        meetingFragment = MeetingFragment.newInstance(bundle);
        friendListFragment = FriendListFragment.newInstance(bundle);

        boolean timeTableUploaded = getPreferencesBoolean("timeTableUploaded");
        bundle.putBoolean("timeTableUploaded", timeTableUploaded);

        epFragment = EP_Fragment.newInstance(bundle);

        //for bottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, meetingFragment).hide(meetingFragment);
        transaction.add(R.id.frame_layout, friendListFragment).hide(friendListFragment);
        transaction.add(R.id.frame_layout, epFragment);
        transaction.commit();

        curActivatedFragment = epFragment;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_timetable:
                        transaction.hide(curActivatedFragment).show(epFragment);
                        transaction.commit();
                        curActivatedFragment = epFragment;
                        break;
                    case R.id.navigation_meeting:
                        transaction.hide(curActivatedFragment).show(meetingFragment);
                        transaction.commit();
                        curActivatedFragment = meetingFragment;
                        break;
                    case R.id.navigation_friends:
                        transaction.hide(curActivatedFragment).show(friendListFragment);
                        transaction.commit();
                        curActivatedFragment = friendListFragment;
                        break;
                }
                return true;
            }
        });
    }

    public void savePreferencesBoolean(String key, boolean value){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value).apply();
    }

    public boolean getPreferencesBoolean(String key){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //다른 액티비티가 main 액티비티 위에 올라갔을 때
    }


    @Override
    protected void onResume() {
        super.onResume();
        //다시 main 액티비티가 화면에 보일 때

    }


}
