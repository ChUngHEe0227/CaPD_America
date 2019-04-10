package ajou.com.skechip.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ajou.com.skechip.R;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.friends.response.model.AppFriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ajou.com.skechip.R;

public class FriendListFragment extends Fragment {
    private final String TAG = "#FriendListFragment: ";
    SQLiteDatabase database;
    TextView textView;

    final AppFriendContext friendContext = new AppFriendContext(true, 0, 10, "asc");
//    final AppFriendInfo friendInfo = null;
    private List<AppFriendInfo> items = new ArrayList<>();
    private List<String> friends_nickname = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if(bundle != null){
//
//        }
        requestFriends();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v= inflater.inflate(R.layout.fragment_friend_list,container,false);
//        requestFriends(v);
//        get_friends_list(v);

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,friends_nickname);
        ListView listView = v.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));

                    }
                }
        );

        return v;
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



    public void get_friends_list(View v){
        String[] items = new String[] {"철수", "영희", "바둑이","철구","dd"};   //DB의 값들을 읽어옴.
        ListAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
        ListView listView = v.findViewById(R.id.listView);
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

    public void requestFriends() {
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
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
                                    AppFriendInfo next = (AppFriendInfo)iter.next();
                                    friends_nickname.add(next.getProfileNickname());
                                    items.add(next);
                                }

                                if (friendContext.hasNext()) {
                                    requestFriends();
                                } else {
                                    Logger.e("No next pages");
                                    // 모든 페이지 요청 완료.
                                }

                                //ListAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,result.getFriends().toString());
                                //lv.setAdapter(adapter);
                                // context의 beforeUrl과 afterUrl이 업데이트 된 상태.
                            }
                        });
            }
        });

    }


    public void selectData(String tableName){
        if(database != null){
            String sql = "select name, age, mobile from " + tableName;
            Cursor cursor = database.rawQuery(sql, null); //표준 자바에 접근하기 위해 result set과 유사
            println("조회된 데이터 개수 : " + cursor.getCount());

            for (int i = 0; i<cursor.getCount(); i++){
                cursor.moveToNext();
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                println("#" + i + " -> " + name + ", "+ age + ", " + mobile);
            }
            cursor.close();
        }
    }
    public void println(String data){
        textView.append(data + "\n");
    }





}
