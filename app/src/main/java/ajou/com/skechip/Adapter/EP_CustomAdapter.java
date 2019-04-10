package ajou.com.skechip.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ajou.com.skechip.Fragment.bean.Cell;
import ajou.com.skechip.Fragment.bean.ColTitle;
import ajou.com.skechip.Fragment.bean.RowTitle;
import ajou.com.skechip.R;

import ajou.com.skechip.Fragment.bean.Cell;
import ajou.com.skechip.Fragment.bean.RowTitle;
import cn.zhouchaoyuan.excelpanel.BaseExcelPanelAdapter;

public class EP_CustomAdapter extends BaseExcelPanelAdapter <RowTitle, ColTitle, Cell> {

    private Context context;
    private View.OnClickListener blockListener;

    public EP_CustomAdapter(Context context, View.OnClickListener blockListener) {
        super(context);
        this.context = context;
        this.blockListener = blockListener;
    }

    //=========================================content's cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_normal_cell, parent, false);
        CellHolder cellHolder = new CellHolder(layout);
        return cellHolder;
    }

    @Override
    public void onBindCellViewHolder(RecyclerView.ViewHolder holder, int verticalPosition, int horizontalPosition) {
        Cell cell = getMajorItem(verticalPosition, horizontalPosition);
        if (null == holder || !(holder instanceof CellHolder) || cell == null) {
            return;
        }
        CellHolder viewHolder = (CellHolder) holder;
        viewHolder.cellContainer.setTag(cell);
        viewHolder.cellContainer.setOnClickListener(blockListener);
        if (cell.getStatus() == 0) {
            viewHolder.bookingName.setText("");
            viewHolder.channelName.setText("");
            viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            viewHolder.bookingName.setText(cell.getBookingName());
            viewHolder.channelName.setText(cell.getChannelName());
            if (cell.getStatus() == 1) {
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject1));
            } else if (cell.getStatus() == 2) {
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject2));
            } else if(cell.getStatus() == 3){
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject3));
            }
            else if(cell.getStatus() == 4){
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject4));
            }
            else if(cell.getStatus() == 5){
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject5));
            }
            else if(cell.getStatus() == 6){
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject6));
            }
            else if(cell.getStatus() == 7){
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.Subject7));
            }
        }
    }

    static class CellHolder extends RecyclerView.ViewHolder {

        public final TextView bookingName;
        public final TextView channelName;
        public final LinearLayout cellContainer;

        public CellHolder(View itemView) {
            super(itemView);
            bookingName = (TextView) itemView.findViewById(R.id.subject_name);
            channelName = (TextView) itemView.findViewById(R.id.memo);
            cellContainer = (LinearLayout) itemView.findViewById(R.id.pms_cell_container);
        }
    }


    //=========================================top cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateTopViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_top_header_item, parent, false);
        TopHolder topHolder = new TopHolder(layout);
        return topHolder;
    }

    @Override
    public void onBindTopViewHolder(RecyclerView.ViewHolder holder, int position) {
        RowTitle rowTitle = getTopItem(position);
        if (null == holder || !(holder instanceof TopHolder) || rowTitle == null) {
            return;
        }
        TopHolder viewHolder = (TopHolder) holder;
        viewHolder.roomWeek.setText(rowTitle.getWeekString());
        viewHolder.roomDate.setText(rowTitle.getDateString());
    }

    static class TopHolder extends RecyclerView.ViewHolder {

        public final TextView roomDate;
        public final TextView roomWeek;

        public TopHolder(View itemView) {
            super(itemView);
            roomDate = (TextView) itemView.findViewById(R.id.date_label);
            roomWeek = (TextView) itemView.findViewById(R.id.week_label);
        }
    }

    //=========================================left cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateLeftViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_left_header_item, parent, false);
        LeftHolder leftHolder = new LeftHolder(layout);
        return leftHolder;
    }

    @Override
    public void onBindLeftViewHolder(RecyclerView.ViewHolder holder, int position) {
        ColTitle colTitle = getLeftItem(position);
        if (null == holder || !(holder instanceof LeftHolder) || colTitle == null) {
            return;
        }
        LeftHolder viewHolder = (LeftHolder) holder;
        viewHolder.alphabetLabel.setText(colTitle.getAlphabetName());
        viewHolder.timeRangeLabel.setText(colTitle.getTimeRangeName());
        ViewGroup.LayoutParams lp = viewHolder.root.getLayoutParams();
        viewHolder.root.setLayoutParams(lp);
    }

    static class LeftHolder extends RecyclerView.ViewHolder {

        public final TextView alphabetLabel;
        public final TextView timeRangeLabel;
        public final View root;

        public LeftHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            alphabetLabel = itemView.findViewById(R.id.alphabet_time);
            timeRangeLabel = itemView.findViewById(R.id.time_range);
        }
    }

    //=========================================left-top cell===========================================
    @Override
    public View onCreateTopLeftView() {
        return LayoutInflater.from(context).inflate(R.layout.room_status_left_header_item, null);
    }
}
