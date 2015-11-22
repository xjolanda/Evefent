package kzooevefent.com.evefent;


import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by G on 10/19/15.
 */
public class TabsPagerAdapter extends PagerAdapter {
    String tabs[]={"Schedule","Map","Attendees","Registration", "Social Media"};
    Activity activity;
    public TabsPagerAdapter(Activity activity){
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return tabs.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources
        View view=null;
        if(position%2==0){
            view = activity.getLayoutInflater().inflate(R.layout.fragmet_layout1,container, false);
        }else if (position%2==1){
            view = activity.getLayoutInflater().inflate(R.layout.fragmet_layout2,container, false);
        }else if (position%2==2){
            view = activity.getLayoutInflater().inflate(R.layout.fragmet_layout3,container, false);
        }else if (position%2==3){
            view = activity.getLayoutInflater().inflate(R.layout.fragmet_layout4,container, false);
        }else if (position%2==4){
            view = activity.getLayoutInflater().inflate(R.layout.fragmet_layout5,container, false);
        }

        // Add the newly created View to the ViewPager
        container.addView(view);


        // Retrieve a TextView from the inflated View, and update it's text
        TextView title = (TextView) view.findViewById(R.id.fragment1);
        title.setText(tabs[position]);


        // Return the View
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
