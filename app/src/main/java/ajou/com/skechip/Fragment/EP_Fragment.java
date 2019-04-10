package ajou.com.skechip.Fragment;

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

import ajou.com.skechip.R;

import java.util.Arrays;
import java.util.List;

import ajou.com.skechip.R;

public class EP_Fragment extends Fragment {
    private ViewPager pager;
    private TabLayout tabLayout;
    private Button button;
    private Bundle bundle = new Bundle();
    private FragmentManager fragmentManager;
    private boolean cur_type; // true == Table, false == Planner
    private List<ajou.com.skechip.Fragment.TimeTableFragment> t_list = Arrays.asList(new ajou.com.skechip.Fragment.TimeTableFragment(),
            new ajou.com.skechip.Fragment.TimeTableFragment(), new ajou.com.skechip.Fragment.TimeTableFragment(),
            new ajou.com.skechip.Fragment.TimeTableFragment(), new ajou.com.skechip.Fragment.TimeTableFragment(),
            new ajou.com.skechip.Fragment.TimeTableFragment());
    private List<ajou.com.skechip.Fragment.PlannerFragment> p_list = Arrays.asList(new ajou.com.skechip.Fragment.PlannerFragment());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_time_table,container,false);
        cur_type = true;
        pager = v.findViewById(R.id.pager);
        tabLayout = v.findViewById(R.id.tab_layout);
        button = v.findViewById(R.id.reflect);
        fragmentManager= getActivity().getSupportFragmentManager();
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur_type = false;
                pager.setAdapter(new ExcelPagerAdapter(fragmentManager));
                tabLayout.setupWithViewPager(pager);
                tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
            }
        });
        pager.setAdapter(new ExcelPagerAdapter(fragmentManager));
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

        return v;
    }

    class ExcelPagerAdapter extends FragmentStatePagerAdapter {

        public ExcelPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Button button = getActivity().findViewById(R.id.reflect);
            if (cur_type) {
                if (position == 0) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
                return t_list.get(position);
            } else {
                button.setVisibility(View.GONE);
                return p_list.get(position);
            }
        }

        @Override
        public int getCount() {
            if (cur_type) return t_list.size();
            else return p_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Friend" + position;
        }
    }
}
