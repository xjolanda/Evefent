package kzooevefent.com.evefent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by G on 10/19/15.
 */
public class SlidingFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpPager(view);
        setUpTabColor();
    }
    void setUpPager(View view){
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new TabsPagerAdapter(getActivity()));
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
    void setUpTabColor(){
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                // TODO Auto-generated method stub
                return SlidingFragment.this.getResources().getColor(android.R.color.holo_purple);
            }


            public int getDividerColor(int position) {
                // TODO Auto-generated method stub
                return SlidingFragment.this.getResources().getColor(android.R.color.holo_purple);
            }
        });
    }

}
