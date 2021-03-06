package me.yanglw.android.spi.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.yanglw.android.spi.ServiceLoader;
import me.yanglw.android.spi.demo.service.home.HomePage;

public class MainActivity extends AppCompatActivity {
    private List<HomePage> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.viewpager);

        TabLayout bottomGroup = findViewById(R.id.bottom_group);
        ServiceLoader<HomePage> homePages = ServiceLoader.load(HomePage.class);
        mList = new ArrayList<>();
        for (HomePage item : homePages) {
            mList.add(item);
            bottomGroup.addTab(bottomGroup.newTab()
                                          .setText(item.getName(this)));
        }
        bottomGroup.setupWithViewPager(viewPager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position).getContent(MainActivity.this);
            }

            @Override
            public int getCount() {
                return mList == null ? 0 : mList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mList.get(position).getName(MainActivity.this);
            }

        });
    }
}
