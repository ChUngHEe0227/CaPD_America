package ajou.com.skechip.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ajou.com.skechip.Adapter.Card_Recycler_Adapter;
import ajou.com.skechip.Fragment.bean.MyData;
import ajou.com.skechip.R;

import java.util.ArrayList;

public class MeetingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<MyData> myDataset = new ArrayList<MyData>();


//    public static MeetingFragment newInstance(Bundle bundle) {
//        MeetingFragment fragment = new MeetingFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        if(bundle != null){
//            fragment.setArguments(bundle);
//        }
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataset.add(new MyData("#insideout", R.drawable.sample1));
        myDataset.add(new MyData("#Harry Potter", R.drawable.sample));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_meeting,container,false);

        mRecyclerView = root.findViewById(R.id.meeting_card_list);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Card_Recycler_Adapter(myDataset);
        if(!myDataset.isEmpty()) {
            root.findViewById(R.id.meeting_d).setVisibility(View.GONE);
            mRecyclerView.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
        }
        mRecyclerView.setAdapter(mAdapter);


        return root;
    }


}
