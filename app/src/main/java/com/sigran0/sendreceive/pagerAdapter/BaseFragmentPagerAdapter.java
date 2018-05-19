package com.sigran0.sendreceive.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-05-19.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    public BaseFragmentPagerAdapter(FragmentManager fm){
        super(fm);

        fragmentManager = fm;
    }

    public void addPage(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }
}
