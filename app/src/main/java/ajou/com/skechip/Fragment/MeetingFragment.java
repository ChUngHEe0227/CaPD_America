package ajou.com.skechip.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ajou.com.skechip.R;

import ajou.com.skechip.R;

public class MeetingFragment extends Fragment {


    public static MeetingFragment newInstance(Bundle bundle) {
        MeetingFragment fragment = new MeetingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
//            roomContext = (RoomContext) bundle.getSerializable("roomContext");
//            broadcastID = bundle.getString("broadcast_id", broadcastID);
//            bundle.putBoolean("isGuest", true);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting, container, false);
    }



}
