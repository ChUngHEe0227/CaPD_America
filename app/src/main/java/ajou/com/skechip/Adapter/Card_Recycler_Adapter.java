package ajou.com.skechip.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ajou.com.skechip.Fragment.bean.MyData;
import ajou.com.skechip.R;

import java.util.ArrayList;

public class Card_Recycler_Adapter extends RecyclerView.Adapter<Card_Recycler_Adapter.ViewHolder> {
    private ArrayList<MyData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;
        public ListView mListView;
        public Meeting_Recycler_Adapter mAdapter;
        public ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.image);
            mTextView = view.findViewById(R.id.meeting_name);
//            mListView = view.findViewById(R.id.meeting_schedule_list);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Card_Recycler_Adapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Card_Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meeting_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.findViewById(R.id.cardview).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getTitle());
        holder.mImageView.setImageResource(mDataset.get(position).getProfile_img());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

