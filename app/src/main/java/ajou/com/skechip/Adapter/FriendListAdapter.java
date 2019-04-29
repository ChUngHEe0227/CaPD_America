package ajou.com.skechip.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.friends.response.model.AppFriendInfo;

import java.util.List;

import ajou.com.skechip.R;

public class FriendListAdapter extends BaseAdapter {

    private Context activityContext;
    private List<AppFriendInfo> friendEntities;

    public FriendListAdapter(Context activityContext, List<AppFriendInfo> friendEntities) {
        this.activityContext = activityContext;
        this.friendEntities = friendEntities;
    }

    @Override
    public int getCount() {
        return friendEntities.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < getCount()) {
            return friendEntities.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    activityContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.friend_entity_block, null);
        }

        final AppFriendInfo friendEntity = friendEntities.get(position);

        TextView friendName = convertView.findViewById(R.id.friend_name);
        friendName.setText(friendEntity.getProfileNickname());

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Toast.makeText(activityContext, friendEntity.getProfileNickname() +  ": checked!", Toast.LENGTH_SHORT).show();

            }
        });

        return convertView;
    }
}
























