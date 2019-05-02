package ajou.com.skechip.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kakao.friends.response.model.AppFriendInfo;

import ajou.com.skechip.CalendarActivity;
import ajou.com.skechip.Fragment.bean.FriendEntity;
import ajou.com.skechip.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ajou.com.skechip.R;
import ajou.com.skechip.UploadingActivity;

import static android.content.Context.MODE_PRIVATE;

public class EP_Fragment extends Fragment {
    private ViewPager pager;
    private TabLayout tabLayout;
    private Button button;
    private FragmentManager fragmentManager;
    private List<ajou.com.skechip.Fragment.TimeTableFragment> t_list = Arrays.asList(new ajou.com.skechip.Fragment.TimeTableFragment(),
            new ajou.com.skechip.Fragment.TimeTableFragment(), new ajou.com.skechip.Fragment.TimeTableFragment(),
            new ajou.com.skechip.Fragment.TimeTableFragment(), new ajou.com.skechip.Fragment.TimeTableFragment(),
            new ajou.com.skechip.Fragment.TimeTableFragment());
    private List<String> friendsNickname_list = new ArrayList<>();
    private String kakaoUserImg;
    private String kakaoUserName;
    private Long kakaoUserID;
    private List<AppFriendInfo> kakaoFriendInfo_list;
    private List<FriendEntity> friendEntities = new ArrayList<>();

    private Boolean timeTableUploaded;

    public static EP_Fragment newInstance(Bundle bundle) {
        EP_Fragment fragment = new EP_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            kakaoUserID = bundle.getLong("kakaoUserID");
            kakaoUserName = bundle.getString("kakaoUserName");
            kakaoUserImg = bundle.getString("kakaoUserImg");
            kakaoFriendInfo_list = bundle.getParcelableArrayList("kakaoFriendInfo_list");
            friendsNickname_list = bundle.getStringArrayList("friendsNickname_list");

//            timeTableUploaded = bundle.getBoolean("timeTableUploaded");
            timeTableUploaded = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        if(timeTableUploaded) { // for 정흠
            view = inflater.inflate(R.layout.fragment_time_table, container, false);
            pager = view.findViewById(R.id.pager);
            tabLayout = view.findViewById(R.id.tab_layout);
            fragmentManager = getActivity().getSupportFragmentManager();
            button = view.findViewById(R.id.reflect);
            button.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CalendarActivity.class);
                    button.setVisibility(View.GONE);
                    startActivity(intent);
                }
            });
            pager.setAdapter(new ExcelPagerAdapter(fragmentManager));
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                @Override
                public void onPageSelected(int i) {
                    if (i == 0) {
                        button.setVisibility(View.VISIBLE);
                    } else {
                        button.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onPageScrollStateChanged(int i) {
                }
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }
            });
            tabLayout.setupWithViewPager(pager);
            tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
            pager.setAdapter(new ExcelPagerAdapter(fragmentManager));
            tabLayout.setupWithViewPager(pager);
            tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        }
        else{ // for 충희
            view = inflater.inflate(R.layout.fragment_upload_timetable, container, false);

            Button uploadBtn = view.findViewById(R.id.upload_button);
            uploadBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO : 업로드 액티비티 띄우고 갤러리 이미지 불러온 뒤 선택하게 하기
                    Intent intent = new Intent(getActivity(), UploadingActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    class ExcelPagerAdapter extends FragmentStatePagerAdapter {

        public ExcelPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return t_list.get(position);
        }


        @Override
        public int getCount() {
            return t_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Friend" + position;
        }
    }

}
