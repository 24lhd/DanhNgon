package com.d.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.activity.MainActivity;
import com.d.danhngon.R;

/**
 * Created by d on 19/01/2017.
 */

public class FmDanhNgon extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private MainActivity mainActivity;
//    public static FmDanhNgon newInstance(int sectionNumber) {
//        FmDanhNgon fragment = new FmDanhNgon();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater=inflater;
        View rootView = inflater.inflate(R.layout.layout_danh_ngon, container, false);
        mainActivity= (MainActivity) getArguments().getSerializable("fm");
        tabLayout= (TabLayout) rootView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(mainActivity.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setSelected(false);
        for (int i = 0; i <tabLayout.getTabCount(); i++) {
            View view=inflater.inflate(R.layout.tab_custem,null);
            TextView tv= (TextView) view.findViewById(R.id.tv_tab);
            tv.setText(""+i);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.shape_yes));
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_selecter));
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.shape_yes));
            }
        });
        tabLayout.getTabAt(0).select();
        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }
        @Override
        public int getCount() {
            return 10;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                default:
                    return "SECTION 4";
            }
        }
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            RecyclerView recyclerView=new RecyclerView(getActivity());
//            recyclerView.setBackgroundColor(Color.BLACK);
            return rootView;
        }
    }
}
