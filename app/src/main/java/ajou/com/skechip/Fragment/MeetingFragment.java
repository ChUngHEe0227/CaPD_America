package ajou.com.skechip.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.friends.response.model.AppFriendInfo;

import ajou.com.skechip.Adapter.GroupEntity_Recycler_Adapter;
import ajou.com.skechip.Fragment.bean.FriendEntity;
import ajou.com.skechip.Fragment.bean.GroupEntity;
import ajou.com.skechip.GroupDetailActivity;
import ajou.com.skechip.R;
import ajou.com.skechip.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MeetingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<GroupEntity> groupEntities = new ArrayList<>();
    private Button groupCreateBtn;

    private List<String> friendsNickname_list = new ArrayList<>();
    private String kakaoUserImg;
    private String kakaoUserName;
    private Long kakaoUserID;
    private List<AppFriendInfo> kakaoFriendInfo_list;
    private ArrayList<FriendEntity> friendEntities;

    private Bundle bundle;

    public static MeetingFragment newInstance(Bundle bundle) {
        MeetingFragment fragment = new MeetingFragment();
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
        bundle = getArguments();
        if(bundle != null){
            kakaoUserID = bundle.getLong("kakaoUserID");
            kakaoUserName = bundle.getString("kakaoUserName");
            kakaoUserImg = bundle.getString("kakaoUserImg");
            kakaoFriendInfo_list = bundle.getParcelableArrayList("kakaoFriendInfo_list");
            friendsNickname_list = bundle.getStringArrayList("friendsNickname_list");
        }

        groupEntities.add(new GroupEntity("캡디아메리카", R.drawable.sample1));
        groupEntities.add(new GroupEntity("모임모임모임모임모임모임모임", R.drawable.sample));
        groupEntities.add(new GroupEntity("1111", R.drawable.sample));
        groupEntities.add(new GroupEntity("2222", R.drawable.sample));
        groupEntities.add(new GroupEntity("3333", R.drawable.sample));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meeting,container,false);

        if(groupEntities.isEmpty()) {
            groupCreateBtn = rootView.findViewById(R.id.group_create_btn);
            groupCreateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO : 모임 생성 액티비티 이동

                    Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
                    intent.putExtra("isNewGroup", true);
                    intent.putExtra("kakaoBundle", bundle);
                    startActivity(intent);

                }
            });

        }
        else {
            rootView.findViewById(R.id.initial_card).setVisibility(View.GONE);

            mRecyclerView = rootView.findViewById(R.id.group_card_list_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new GroupEntity_Recycler_Adapter(groupEntities);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getActivity(), position + "번 째 아이템 클릭 : " + groupEntities.get(position).getGroupTitle(), Toast.LENGTH_SHORT).show();
                            //TODO : 해당 모임 상세 액티비티 이동

                            Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
                            intent.putExtra("isNewGroup", false);
                            intent.putExtra("kakaoBundle", bundle);
                            startActivity(intent);

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            Toast.makeText(getActivity(), position + "번 째 아이템 롱 클릭", Toast.LENGTH_SHORT).show();
                        }
                    }));
        }


        return rootView;
    }


}
