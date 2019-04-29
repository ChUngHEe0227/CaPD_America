package ajou.com.skechip.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ajou.com.skechip.Adapter.FriendListAdapter;
import ajou.com.skechip.R;
import com.kakao.friends.response.model.AppFriendInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment {
    private final String TAG = "#FriendListFragment: ";

    private List<String> friendsNickname_list = new ArrayList<>();
    private String kakaoUserImg;
    private String kakaoUserName;
    private Long kakaoUserID;
    private List<AppFriendInfo> kakaoFriendInfo_list;
//    private List<FriendEntity> friendEntities = new ArrayList<>();

    private Boolean isForGroupCreation;

    private FriendListAdapter friendListAdapter;

    public static FriendListFragment newInstance(Bundle bundle) {
        FriendListFragment fragment = new FriendListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            kakaoUserID = bundle.getLong("kakaoUserID");
            kakaoUserName = bundle.getString("kakaoUserName");
            kakaoUserImg = bundle.getString("kakaoUserImg");
            kakaoFriendInfo_list = bundle.getParcelableArrayList("kakaoFriendInfo_list");
            friendsNickname_list = bundle.getStringArrayList("friendsNickname_list");

            isForGroupCreation = bundle.getBoolean("isForGroupCreation");
        }



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view;
        if(isForGroupCreation){
            view = inflater.inflate(R.layout.fragment_friend_list_for_group_create, container, false);

            friendListAdapter = new FriendListAdapter(getActivity(), kakaoFriendInfo_list);
            ListView selectableListView = view.findViewById(R.id.selectableListView);
            selectableListView.setAdapter(friendListAdapter);

            selectableListView.findViewById(R.id.checkBox);

            selectableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                    Toast.makeText(getActivity(), friendEntity.getProfileNickname() +  ": checked!", Toast.LENGTH_SHORT).show();

                }
            });

        }
        else{
            view = inflater.inflate(R.layout.fragment_friend_list, container, false);
            ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, friendsNickname_list);
            ListView listView = view.findViewById(R.id.listView);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = String.valueOf(parent.getItemAtPosition(position));

                        }
                    }
            );
        }

        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }




}
