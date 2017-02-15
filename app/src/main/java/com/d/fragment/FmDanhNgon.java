package com.d.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.activity.MainActivity;
import com.d.object.Category;
import com.d.object.DanhNgon;
import com.lhd.danhngon.R;

import java.util.ArrayList;

/**
 * Created by d on 19/01/2017.
 */


public class FmDanhNgon extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";
    private MainActivity mainActivity;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private LayoutInflater inflater;
    private ArrayList<Category> categories;
    private ArrayList<DanhNgon> danhNgons;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        this.inflater=inflater;
        mainActivity= (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.content_danh_ngon, container, false);
        categories=mainActivity.getCategories();
        danhNgons=mainActivity.getDanhNgons();
        initView();
        return rootView;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    private void initView() {
        tabLayout= (TabLayout) rootView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(mainActivity.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);
        for (Category category:mainActivity.getCategories()) {
            View view=inflater.inflate(R.layout.tab_custem,null);
            TextView tv= (TextView) view.findViewById(R.id.tv_tab);
            tv.setText(""+category.getCategory());
            tabLayout.getTabAt(mainActivity.getCategories().indexOf(category)).setCustomView(tv);
        }
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorFrey));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_select));
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_unselect));
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_select));
            }
        });
        tabLayout.getTabAt(0).select();

        mViewPager.setCurrentItem(0);
    }
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            FmRecycleView fragment = new FmRecycleView();
            Bundle args = new Bundle();
            ArrayList<DanhNgon> danhNgonCategory=new ArrayList<>();
            for (DanhNgon danhNgon:danhNgons) if (danhNgon.getCategory().equals(categories.get(position).getMa())) danhNgonCategory.add(danhNgon);
            TextView tv= (TextView) tabLayout.getTabAt(position).getCustomView();
            tv.setText( categories.get(position).getCategory()+" ("+danhNgonCategory.size()+")");
            args.putSerializable(ARG_SECTION_NUMBER, categories.get(position));
            args.putSerializable(MainActivity.LIST_DATA,danhNgonCategory);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public int getCount() {
            try {
                return mainActivity.getCategories().size()>0?mainActivity.getCategories().size():0;
            }catch (NullPointerException e){
                return 0;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
                    return "Danh ngôn";
        }
    }

}
