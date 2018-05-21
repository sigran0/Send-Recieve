package com.sigran0.sendreceive.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.fragments.BaseFragment;
import com.sigran0.sendreceive.fragments.MyInfoFragment;
import com.sigran0.sendreceive.fragments.SendFragment;
import com.sigran0.sendreceive.fragments.SendStartFragment;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.UserManager;
import com.sigran0.sendreceive.pagerAdapter.MainPagerAdapter;
import com.sigran0.sendreceive.views.LockableViewPager;

import butterknife.BindView;
import butterknife.BindViews;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    MainPagerAdapter mainPagerAdapter;

    @BindView(R.id.a_main_lvp)
    LockableViewPager viewPager;

    @BindView(R.id.a_main_navigation)
    BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_send:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.navigation_dashboard:
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
        return R.layout.activity_main;
    }

    @Override
    protected void initializeLayout(){
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BaseFragment sendFragment = new SendStartFragment();
        BaseFragment myInfoFragment = new MyInfoFragment();

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mainPagerAdapter.addPage(sendFragment);
        mainPagerAdapter.addPage(myInfoFragment);

        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setSwipeLocked(true);
    }

}
