package com.sigran0.sendreceive.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.fragments.BaseFragment;
import com.sigran0.sendreceive.fragments.ItemListFragment;
import com.sigran0.sendreceive.fragments.MyInfoFragment;
import com.sigran0.sendreceive.fragments.SendStartFragment;
import com.sigran0.sendreceive.pagerAdapter.ReceiverMainPagerAdapter;
import com.sigran0.sendreceive.pagerAdapter.SenderMainPagerAdapter;
import com.sigran0.sendreceive.views.LockableViewPager;

import butterknife.BindView;

public class SenderMainActivity extends BaseActivity {

    SenderMainPagerAdapter senderMainPagerAdapter;

    @BindView(R.id.a_sender_main_lvp)
    LockableViewPager viewPager;

    @BindView(R.id.a_sender_main_navigation)
    BottomNavigationView bnv;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.n_sender_item_list:
                    return true;
                case R.id.n_sender_dashboard:
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
        return R.layout.activity_sender_main;
    }

    @Override
    protected void initializeLayout(){
        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BaseFragment sendFragment = new ItemListFragment();
        BaseFragment myInfoFragment = new MyInfoFragment();

        senderMainPagerAdapter = new SenderMainPagerAdapter(getSupportFragmentManager());

        senderMainPagerAdapter.addPage(sendFragment);
        senderMainPagerAdapter.addPage(myInfoFragment);

        viewPager.setAdapter(senderMainPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setSwipeLocked(true);
    }
}
