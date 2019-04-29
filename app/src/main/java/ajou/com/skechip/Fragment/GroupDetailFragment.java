package ajou.com.skechip.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ajou.com.skechip.R;

public class GroupDetailFragment extends Fragment {

    private Bundle bundle;

//    private List<String> friendsNickname_list = new ArrayList<>();
//    private String kakaoUserImg;
//    private String kakaoUserName;
//    private Long kakaoUserID;
//    private List<AppFriendInfo> kakaoFriendInfo_list;


    public static GroupDetailFragment newInstance(Bundle bundle) {
        GroupDetailFragment fragment = new GroupDetailFragment();
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
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if(bundle != null){
//            kakaoUserID = bundle.getLong("kakaoUserID");
//            kakaoUserName = bundle.getString("kakaoUserName");
//            kakaoUserImg = bundle.getString("kakaoUserImg");
//            kakaoFriendInfo_list = bundle.getParcelableArrayList("kakaoFriendInfo_list");
//            friendsNickname_list = bundle.getStringArrayList("friendsNickname_list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_detail, container, false);
    }






}
