package com.d.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.d.danhngon.R;
import com.d.database.DuLieu;
import com.d.fragment.FmDanhNgon;
import com.d.object.Category;
import com.d.object.DanhNgon;
import com.d.task.TaskGetCategory;
import com.d.task.TaskGetDanhNgon;

import java.util.ArrayList;

import duong.AppLog;
import duong.ChucNangPhu;
import duong.Communication;
import duong.DiaLogThongBao;

import static com.d.danhngon.R.drawable.shape_no;
import static com.d.database.DuLieu.PATH_DB;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener  {

    public static final String LIST_DATA = "list_data";
    public static final String THIS = "this";
    public static final String STATE_UI = "state_ui";
    private Toolbar toolbar;
    private ArrayList<Category> categories;
    private ArrayList<DanhNgon> danhNgons;
    private ProgressDialog progressDialog;
    private DuLieu duLieu;
    private int tabUISelect;
    private TabLayout tabUI;
    private FmDanhNgon fmDanhNgon;
    private FrameLayout frameLayout;
    private AppLog appLog;
    private Window window;

    public int getColorApp() {
        return colorApp;
    }

    private int colorApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDialogLoad(this, "Đang khởi tạo dữ liệu...");
        initData();
    }
    private void initData() {
        duLieu = new DuLieu(this);
        try {
                if (duLieu.checkDB()){
                    ChucNangPhu.showLog("if "+duLieu.checkDB());
                    startGetCategory();
                }else {
                    ChucNangPhu.showLog("else");
                    duLieu.getDuongSQLite().copyDataBase(this, PATH_DB,"danhngon_db.sqlite");
                    initData();
                }
            } catch (Exception e) {
                ChucNangPhu.showLog("Exception");
            }

    }
        public void startGetCategory() {
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if ((ArrayList<Category>) msg.obj != null){
                    startGetDanhNgon();
                    setCategories((ArrayList<Category>) msg.obj);
                }else{
                    initData();
                }

            }
        };
        TaskGetCategory taskGetCategory=new TaskGetCategory(this,handler);
        taskGetCategory.execute();
    }
    public void startGetDanhNgon() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ArrayList<DanhNgon> danhNgons = (ArrayList<DanhNgon>) msg.obj;
                if (danhNgons != null){
                    initView();
                    hideDialogLoad();
                    setDanhNgons(danhNgons);
                    setViewDanhNgon();
                }else{
                    initData();
                }

            }
        };
        TaskGetDanhNgon getDanhNgon = new TaskGetDanhNgon(this, handler);
        getDanhNgon.execute();
    }

    public void hideDialogLoad() {
        progressDialog.dismiss();
    }

    public void showDialogLoad(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<DanhNgon> getDanhNgons() {
        return danhNgons;
    }

    public void setDanhNgons(ArrayList<DanhNgon> danhNgons) {
        this.danhNgons = danhNgons;
    }

    private void initView() {
        appLog=new AppLog();
        appLog.openLog(this,STATE_UI);
         window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        frameLayout= (FrameLayout) findViewById(R.id.frame_fm);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        colorApp=getResources().getColor(R.color.colorPrimary);
        fmDanhNgon=new FmDanhNgon();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUI(appLog.getValueByName(this,STATE_UI,"StatusBar"),
                appLog.getValueByName(this,STATE_UI,"toolbar"),
                appLog.getValueByName(this,STATE_UI,"shape_yes"),
                appLog.getValueByName(this,STATE_UI,"shape_no"),
                appLog.getValueByName(this,STATE_UI,"frameLayout"));
    }

    public DuLieu getDuLieu() {
        return duLieu;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else
            ChucNangPhu.finishDoubleCick(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        toolbar.setTitle(item.getTitle());
        if (id == R.id.danh_ngon) {
          setViewDanhNgon();
        } else if (id == R.id.chia_se) {
            initData();
        } else if (id == R.id.more_app) {

        }else if (id == R.id.thay_doi_ui) {
            showDialogSetUI();
           }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogSetUI() {
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_select_ui,null);
        tabUI= (TabLayout) view.findViewById(R.id.tab_ui_layout);
        tabUI.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabUI.setTabGravity(TabLayout.GRAVITY_FILL);
        tabUI.setSelectedTabIndicatorHeight(0);

        TabLayout.Tab tabStatus=tabUI.newTab();
        tabStatus.setText("Status Bar");
        tabUI.addTab(tabStatus);

        TabLayout.Tab tabBar=tabUI.newTab();
        tabBar.setText("Action Bar");
        tabUI.addTab(tabBar);

        TabLayout.Tab tab=tabUI.newTab();
        tab.setText("Tab Unselect");
        tabUI.addTab(tab);

        TabLayout.Tab tabCategory=tabUI.newTab();
        tabCategory.setText("Tab Select");
        tabUI.addTab(tabCategory);

        TabLayout.Tab tabBg=tabUI.newTab();
        tabBg.setText("Background");
        tabUI.addTab(tabBg);
        LayoutInflater inflater=getLayoutInflater();
        for (int i = 0; i < tabUI.getTabCount(); i++) {
            View viewTab=inflater.inflate(R.layout.tab_custem,null);
            TextView tv2= (TextView) viewTab.findViewById(R.id.tv_tab);
            tv2.setText(tabUI.getTabAt(i).getText());
            tabUI.getTabAt(i).setCustomView(tv2);
        }
        tabUI.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabUISelect=tab.getPosition();
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.shape_yes));
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(shape_no));
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.shape_yes));
            }
        });
        tabUI.getTabAt(0).select();
        tabUISelect=0;
        DiaLogThongBao.createDiaLogView(this, view, null , null, null, getResources().getColor(R.color.colorPrimary),null, null).show();

    }

    private void setViewDanhNgon() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fmDanhNgon=new FmDanhNgon();
        transaction.replace(R.id.frame_fm, fmDanhNgon);
        transaction.commit();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void onSelectUI(View v) {
        FloatingActionButton floatingActionButton= (FloatingActionButton) v;
        switch (tabUISelect){
            case 1:
                toolbar.setBackgroundColor(floatingActionButton.getBackgroundTintList().getDefaultColor());
                 colorApp=floatingActionButton.getBackgroundTintList().getDefaultColor();
                appLog.putValueByName(this,STATE_UI,"toolbar",""+floatingActionButton.getBackgroundTintList().getDefaultColor());
                break;
            case 2:
                GradientDrawable shape_no= (GradientDrawable) getResources().getDrawable(R.drawable.shape_no);
                shape_no.setColor(floatingActionButton.getBackgroundTintList().getDefaultColor());
                appLog.putValueByName(this,STATE_UI,"shape_no",""+floatingActionButton.getBackgroundTintList().getDefaultColor());
                loadColorTab();
                break;
            case 3:
                GradientDrawable shape_yes= (GradientDrawable) getResources().getDrawable(R.drawable.shape_yes);
                shape_yes.setColor(floatingActionButton.getBackgroundTintList().getDefaultColor());
                appLog.putValueByName(this,STATE_UI,"shape_yes",""+floatingActionButton.getBackgroundTintList().getDefaultColor());
                loadColorTab();
                break;
            case 4:
                frameLayout.setBackgroundColor(floatingActionButton.getBackgroundTintList().getDefaultColor());
                appLog.putValueByName(this,STATE_UI,"frameLayout",""+floatingActionButton.getBackgroundTintList().getDefaultColor());
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(floatingActionButton.getBackgroundTintList().getDefaultColor());
                    appLog.putValueByName(this,STATE_UI,"StatusBar",""+floatingActionButton.getBackgroundTintList().getDefaultColor());
                }else {
                    Communication.showToastCenter(this,"Phiên bản hệ điều hành không hỗ trợ");
                }
                break;
        }

//        setTheme(R.style.AppTheme2) ;
//        TypedValue typedValue = new TypedValue();
//        Resources.Theme theme = this.getTheme();
//        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
//        int color = typedValue.data;



    }

    private void setUI(String status, String bar, String tabs, String tabu, String bg) {
        if (!status.equals(""))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) window.setStatusBarColor(Integer.parseInt(status));
        if (!bar.equals("")){
            toolbar.setBackgroundColor(Integer.parseInt(bar));
            colorApp=Integer.parseInt(bar);
        }
        GradientDrawable shape_no= (GradientDrawable) getResources().getDrawable(R.drawable.shape_no);
        if (!tabu.equals(""))
            shape_no.setColor(Integer.parseInt(tabu));
                GradientDrawable shape_yes= (GradientDrawable) getResources().getDrawable(R.drawable.shape_yes);
        if (!tabs.equals(""))
                shape_yes.setColor(Integer.parseInt(tabs));
        if (!bg.equals(""))
                frameLayout.setBackgroundColor(Integer.parseInt(bg));
    }

    private void loadColorTab() {
        try {
            for (int i = 0; i < tabUI.getTabCount(); i++) {
                if (tabUI.getTabAt(i).isSelected()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        tabUI.getTabAt(i).getCustomView().setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    else Communication.showToastCenter(this,"Phiên bản hệ điều hành không hỗ trợ");
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        tabUI.getTabAt(i).getCustomView().setBackground(getResources().getDrawable(shape_no));
                    else Communication.showToastCenter(this,"Phiên bản hệ điều hành không hỗ trợ");
                }
            }
            for (int i = 0; i < fmDanhNgon.getTabLayout().getTabCount(); i++) {
                if (fmDanhNgon.getTabLayout().getTabAt(i).isSelected()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        fmDanhNgon.getTabLayout().getTabAt(i).getCustomView().setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    else Communication.showToastCenter(this,"Phiên bản hệ điều hành không hỗ trợ");
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        fmDanhNgon.getTabLayout().getTabAt(i).getCustomView().setBackground(getResources().getDrawable(shape_no));
                    else Communication.showToastCenter(this,"Phiên bản hệ điều hành không hỗ trợ");
                }
            }
        }catch (NullPointerException e){

        }

    }
}
