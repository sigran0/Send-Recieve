package com.sigran0.sendreceive.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.fragments.BaseFragment;
import com.sigran0.sendreceive.fragments.MyInfoFragment;
import com.sigran0.sendreceive.fragments.SendStartFragment;
import com.sigran0.sendreceive.pagerAdapter.ReceiverMainPagerAdapter;
import com.sigran0.sendreceive.views.LockableViewPager;

import butterknife.BindView;

public class ReceiverMainActivity extends BaseActivity {

    ReceiverMainPagerAdapter receiverMainPagerAdapter;

    @BindView(R.id.a_receiver_main_lvp)
    LockableViewPager viewPager;

    @BindView(R.id.a_receiver_main_navigation)
    BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.n_receiver_send:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.n_receiver_dashboard:
                    viewPager.setCurrentItem(1, true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_receiver_main;
    }

    @Override
    protected void initializeLayout(){
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BaseFragment sendFragment = new SendStartFragment();
        BaseFragment myInfoFragment = new MyInfoFragment();

        receiverMainPagerAdapter = new ReceiverMainPagerAdapter(getSupportFragmentManager());

        receiverMainPagerAdapter.addPage(sendFragment);
        receiverMainPagerAdapter.addPage(myInfoFragment);

        viewPager.setAdapter(receiverMainPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setSwipeLocked(true);
    }

}
