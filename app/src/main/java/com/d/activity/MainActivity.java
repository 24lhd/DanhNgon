package com.d.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.d.danhngon.R;
import com.d.database.DuLieu;
import com.d.fragment.FmDanhNgon;

import java.io.Serializable;

import duong.ChucNangPhu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("faker",getPackageName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DuLieu duLieu=new DuLieu(this);
        setViewDanhNgon();
    }
    /**
     * frangment custem
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
////            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
////            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
////            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            RecyclerView recyclerView=new RecyclerView(getActivity());
//            return recyclerView;
//        }
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            ChucNangPhu.finishDoubleCick(this);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.danh_ngon) {
             setViewDanhNgon();
        } else if (id == R.id.chia_se) {
             android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
             transaction.replace(R.id.frame_fm, new FmDanhNgon.PlaceholderFragment());
             transaction.commit();
             Log.e("faker","int");
        } else if (id == R.id.more_app) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setViewDanhNgon() {
        android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        FmDanhNgon fmDanhNgon=new FmDanhNgon();
        Bundle args = new Bundle();
        args.putSerializable("fm", MainActivity.this);
        fmDanhNgon.setArguments(args);
        transaction.replace(R.id.frame_fm, fmDanhNgon);
        transaction.commit();//
    }

}
