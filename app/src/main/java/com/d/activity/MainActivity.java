package com.d.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.d.database.DuLieu;
import com.d.fragment.FmCaiDat;
import com.d.fragment.FmDanhNgon;
import com.d.fragment.FmRecycleView;
import com.d.object.ADSFull;
import com.d.object.Category;
import com.d.object.DanhNgon;
import com.d.service.AdsService;
import com.d.task.TaskGetCategory;
import com.d.task.TaskGetDanhNgon;
import com.d.task.TaskGetImage;
import com.lhd.danhngon.R;

import java.util.ArrayList;
import java.util.Random;

import duong.AppLog;
import duong.ChucNangPhu;
import duong.Communication;
import duong.Conections;
import duong.DiaLogThongBao;
import io.fabric.sdk.android.Fabric;

import static com.d.database.DuLieu.PATH_DB;
import static com.d.fragment.FmDanhNgon.ARG_SECTION_NUMBER;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String LIST_DATA = "list_data";
    public static final String THIS = "this";
    public static final String STATE_UI = "state_ui";
    public static final String IMAGE = "image";
    private static final String DANH_NGON = "danh ngon";
    private static final String CATE_GORY = "cate_gory";
    private static final String LIST_IMAGE = "images";
    public static String DATA = "data";
    private Toolbar toolbar;
    private ArrayList<Category> categories;
    private ArrayList<DanhNgon> danhNgons;
    //    private ProgressDialog progressDialog;
    private DuLieu duLieu;
    private int tabUISelect;
    private TabLayout tabUI;
    private FmDanhNgon fmDanhNgon;
    private FrameLayout frameLayout;
    private AppLog appLog;
    private ImageView headIm;
    private TextView headContent;
    private TextView headAuthor;
    private FmCaiDat fmCaiDat;
    private ArrayList<String> images;
    private DrawerLayout drawer;
    private ADSFull adsFull;
    private AlertDialog alertDialog;
    private Random random;
    private NavigationView navigationView;
    private GradientDrawable tab_unselecter;
    private GradientDrawable tab_selecter;

    public int getColorApp() {
        return colorApp;
    }

    private int colorApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        /**
         * nếu bị null là khi thay đổi ngang màn hình nên bị null
         * vì mk đã lưu nó lại nên lấy nó ra
         *
         */
        if (savedInstanceState != null) {
            images = (ArrayList<String>) savedInstanceState.getSerializable(LIST_IMAGE);
            danhNgons = (ArrayList<DanhNgon>) savedInstanceState.getSerializable(DANH_NGON);
            categories = (ArrayList<Category>) savedInstanceState.getSerializable(CATE_GORY);
        }
        initViewIntro();
    }

    /**
     * khỏi tạo layout intro và chạy animation
     * khởi tạo data lấy list danh ngôn, category, ảnh
     */
    private void initViewIntro() {
        setContentView(R.layout.layout_intro);
        appLog = new AppLog();
        appLog.openLog(this, STATE_UI);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.fm_layout_intro);
        if (!appLog.getValueByName(this, STATE_UI, "toolbar").equals(""))
            relativeLayout.setBackgroundColor(Integer.parseInt(appLog.getValueByName(this, STATE_UI, "toolbar")));
        Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.img_amin);
        ((ImageView) findViewById(R.id.im_intro)).startAnimation(myRotation);
        initData();
//        setContentView(R.layout.activity_main);
    }

    private void initViewMain() {
        setContentView(R.layout.activity_main);
        ChucNangPhu.showLog("" + images.size());
        adsFull = new ADSFull(this);
        random = new Random();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        frameLayout = (FrameLayout) findViewById(R.id.frame_fm);
        tab_unselecter = (GradientDrawable) getResources().getDrawable(R.drawable.tab_unselect);
        tab_selecter = (GradientDrawable) getResources().getDrawable(R.drawable.tab_select);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        colorApp = getResources().getColor(R.color.colorPrimary);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        Glide.with(this).load(R.drawable.a151).into(headIm);
//        getRanRomDanhNgon(headContent,headAuthor,headIm);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                try {
                    getRanRomDanhNgon(headContent, headAuthor, headIm);
                } catch (Exception e) {
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawer.setDrawerListener(toggle);
        View v = navigationView.getHeaderView(0);
        headIm = (ImageView) v.findViewById(R.id.header_im_bn_dn);
        headContent = (TextView) v.findViewById(R.id.header_tv_content);
        headAuthor = (TextView) v.findViewById(R.id.header_tv_author);
        ChucNangPhu.showLog("header_im_bn_dn " + (headIm instanceof ImageView));
        toggle.syncState();
        setUI(appLog.getValueByName(this, STATE_UI, "StatusBar"),
                appLog.getValueByName(this, STATE_UI, "toolbar"),
                appLog.getValueByName(this, STATE_UI, "tab_selecter"),
                appLog.getValueByName(this, STATE_UI, "tab_unselecter"),
                appLog.getValueByName(this, STATE_UI, "frameLayout"));
        setViewDanhNgon();
    }

    /**
     * khi từ đã lưu khi thay đổi trạng tháy ta sẽ lấy dữ liệu đẫ lưu ra
     *
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        images = (ArrayList<String>) savedInstanceState.getSerializable(LIST_IMAGE);
        danhNgons = (ArrayList<DanhNgon>) savedInstanceState.getSerializable(DANH_NGON); // gét lấy ra
        categories = (ArrayList<Category>) savedInstanceState.getSerializable(CATE_GORY);
    }

    /**
     * khỏi tạo các dữ liệu lấy trong sqlite
     */
    private void initData() {
        duLieu = new DuLieu(this);
        if (!AdsService.isRunning(this)) {
            Intent intent1 = new Intent(this, AdsService.class);
            this.startService(intent1);
        }
        DanhNgon danhNgon = (DanhNgon) getIntent().getSerializableExtra("like");
        if (danhNgon instanceof DanhNgon) duLieu.updateDanhNgonFarvorite(danhNgon);
        try {
            if (duLieu.checkDB()) {
                startImages();
                startGetCategory();
                startGetDanhNgon();
            } else { // k có thì copy database và khỏi tạo lại
                duLieu.getDuongSQLite().copyDataBase(this, PATH_DB, "danhngon_db.sqlite");
                initData();
            }
        } catch (Exception e) {
        }
    }

    public void startImages() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                images = new ArrayList<>();
                images = (ArrayList<String>) msg.obj;
            }
        };
        TaskGetImage taskGetImage = new TaskGetImage(this, handler);
        taskGetImage.execute();
    }

    public void startGetCategory() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if ((ArrayList<Category>) msg.obj != null)
                    setCategories((ArrayList<Category>) msg.obj);
                else initViewIntro();
            }
        };
        TaskGetCategory taskGetCategory = new TaskGetCategory(this, handler);
        taskGetCategory.execute();
    }

    public void startGetDanhNgon() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ArrayList<DanhNgon> danhNgons = (ArrayList<DanhNgon>) msg.obj;
                if (danhNgons != null) {
                    setDanhNgons(danhNgons);
                    initViewMain();
//                    hideDialogLoad();
                } else initViewIntro();

            }
        };
        TaskGetDanhNgon getDanhNgon = new TaskGetDanhNgon(this, handler);
        getDanhNgon.execute();
    }


//    public void hideDialogLoad() {
//        progressDialog.dismiss();
//    }

    public void showDialogLoad(Context context, String msg) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_intro);
        dialog.show();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage(msg);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    public ArrayList<Category> getCategories() {
        if (categories == null) {
            startGetCategory();
        }
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


    public DuLieu getDuLieu() {
        return duLieu;
    }

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            adsFull.showADSFull();
            finish();
        }

//            ChucNangPhu.finishDoubleCick(this);

    }

    public ADSFull getAdsFull() {
        return adsFull;
    }

    /**
     * khi item navvigation dc chọn
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        toolbar.setTitle(item.getTitle());
//        try {
//            getRanRomDanhNgon(headContent,headAuthor,headIm);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (id == R.id.danh_ngon) {
            setViewDanhNgon();
        } else if (id == R.id.cai_dat) {
            adsFull.showADSFull();
            caiDat();
        } else if (id == R.id.danh_gia) {
            danhGiaApp();
        } else if (id == R.id.chia_se) {
            chiaSeApp();
        } else if (id == R.id.more_app) {
            moreApp();
        } else if (id == R.id.thay_doi_ui) {
            adsFull.showADSFull();
            showDialogSetUI();
        } else if (id == R.id.yeu_thich) {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FmRecycleView fmRecycleView = new FmRecycleView();
            Bundle args = new Bundle();
            args.putSerializable(ARG_SECTION_NUMBER, categories.get(0));
            args.putSerializable(MainActivity.LIST_DATA, duLieu.getDanhNgonFavorites());
            fmRecycleView.setArguments(args);
            transaction.replace(R.id.frame_fm, fmRecycleView);
            transaction.commit();
        } else if (id == R.id.mn_dev) {
            showDialogDev();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogDev() {
        String str = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                " <style type=\"text/css\" media=\"screen\">" +
                " h2,#tenpg,#dev{" +
                " color: #4267b2" +
                " }" +
                " </style> " +
                "</head>" +
                "<body>" +
                "<h2>" +
                getString(R.string.app_name) +
                "</h2>" +
                "<p>Phần mềm được phát triển qua fanpage Facebook : <span id=\"tenpg\">1001 Câu Danh Ngôn Ý Nghĩa</span><p>Địa chỉ: fb.com/1001CauDanhNgon.quote<p>Developer " +
                "<span id=\"dev\"> : #Ken</span><p>Xin chân thành cảm ơn sự ủng hộ của các bạn!</p>" +
                "</body>" +
                "</html>";
        WebView wb = new WebView(this);
        wb.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
        AlertDialog.Builder turnOnLoactionDialog = new AlertDialog.Builder(this);
        turnOnLoactionDialog.setTitle("Thông tin phát triển");
        turnOnLoactionDialog.setPositiveButton("xem trang", null);
        AlertDialog alertDialog = turnOnLoactionDialog.create();
        alertDialog.setView(wb);
        alertDialog.show();
        Button yes = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(colorApp);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/1001CauDanhNgon.quote"));
                startActivity(browserIntent);
            }
        });


    }

    private void caiDat() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fmCaiDat = new FmCaiDat();
//        getRanRomDanhNgon(headContent,headAuthor,headIm);
        transaction.replace(R.id.frame_fm, fmCaiDat);
        transaction.commit();
    }

    private void moreApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=pub:Duong Studio"));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://play.google.com/store/search?q=pub:Duong Studio"));
            startActivity(intent);
        }
    }

    private void chiaSeApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Link tải phần mềm Danh Ngôn");
            String sAux = "Ứng dụng Danh Ngôn ";

            sAux = sAux + "https://play.google.com/store/apps/details?id=com.lhd.danhngon";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
        }
    }

    private void danhGiaApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void showDialogSetUI() {
        alertDialog = null;
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_change_ui, null);
        tabUI = (TabLayout) view.findViewById(R.id.tab_ui_layout);
        tabUI.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabUI.setTabGravity(TabLayout.GRAVITY_FILL);
        tabUI.setSelectedTabIndicatorHeight(0);
        ((ImageButton) view.findViewById(R.id.imb_close_change_ui)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        TabLayout.Tab tabStatus = tabUI.newTab();
        tabStatus.setText("Status Bar");
        tabUI.addTab(tabStatus);

        TabLayout.Tab tabBar = tabUI.newTab();
        tabBar.setText("Action Bar");
        tabUI.addTab(tabBar);

        TabLayout.Tab tab = tabUI.newTab();
        tab.setText("Tab Unselect");
        tabUI.addTab(tab);

        TabLayout.Tab tabCategory = tabUI.newTab();
        tabCategory.setText("Tab Select");
        tabUI.addTab(tabCategory);

        TabLayout.Tab tabBg = tabUI.newTab();
        tabBg.setText("Background");
        tabUI.addTab(tabBg);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < tabUI.getTabCount(); i++) {
            View viewTab = inflater.inflate(R.layout.tab_custem, null);
            TextView tv2 = (TextView) viewTab.findViewById(R.id.tv_tab);
            tv2.setText(tabUI.getTabAt(i).getText());
            tabUI.getTabAt(i).setCustomView(tv2);
        }
        tabUI.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabUISelect = tab.getPosition();
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
        tabUI.getTabAt(0).select();
        tabUISelect = 0;
        alertDialog = DiaLogThongBao.createDiaLogView(this, view, null, null, null,
                getResources().getColor(R.color.colorPrimary), null, null);
        alertDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Communication.showToast(this, "Chức năng này đang được phát triển");
        return super.onOptionsItemSelected(item);
    }

    /**
     * set view danh ngôn
     */
    private void setViewDanhNgon() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fmDanhNgon = new FmDanhNgon();
        getRanRomDanhNgon(headContent, headAuthor, headIm);
        transaction.replace(R.id.frame_fm, fmDanhNgon);
        transaction.commit();
    }

    /**
     * lấy 1 random dnah ngôn set header navigation
     *
     * @param tvContent nội dung danh ngôn
     * @param tvAuthor  tác giả
     * @param image     ảnh
     */
    public void getRanRomDanhNgon(TextView tvContent, TextView tvAuthor, ImageView image) {
        if (Conections.isOnline(this))
            Glide.with(this).load(images.get(random.nextInt(images.size()))).into(image);
        else Glide.with(this).load(R.drawable.a151).into(image);
        DanhNgon danhNgonRd = danhNgons.get(random.nextInt(danhNgons.size()));
        tvContent.setText(danhNgonRd.getContent());
        tvAuthor.setTextColor(colorApp);
        tvAuthor.setText(danhNgonRd.getAuthor());
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * phương thức nơi các button màu dc chọn và set  cho các đối tượng
     *
     * @param v
     */
    public void onSelectUI(View v) {
        Button button = (Button) v;
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();

        switch (tabUISelect) {
            case 1:
                toolbar.setBackgroundColor(buttonColor.getColor());
                colorApp = buttonColor.getColor();
                appLog.putValueByName(this, STATE_UI, "toolbar", "" + buttonColor.getColor());
                break;
            case 2:
                GradientDrawable tab_unselecter = (GradientDrawable) getResources().getDrawable(R.drawable.tab_unselect);
                tab_unselecter.setColor(buttonColor.getColor());
                appLog.putValueByName(this, STATE_UI, "tab_unselecter", "" + buttonColor.getColor());
                loadColorTab();
                break;
            case 3:
                GradientDrawable tab_selecter = (GradientDrawable) getResources().getDrawable(R.drawable.tab_select);
                tab_selecter.setColor(buttonColor.getColor());
                appLog.putValueByName(this, STATE_UI, "tab_selecter", "" + buttonColor.getColor());
                loadColorTab();
                break;
            case 4:
                frameLayout.setBackgroundColor(buttonColor.getColor());
                appLog.putValueByName(this, STATE_UI, "frameLayout", "" + buttonColor.getColor());
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(buttonColor.getColor());
                    appLog.putValueByName(this, STATE_UI, "StatusBar", "" + buttonColor.getColor());
                } else Communication.showToastCenter(this, "Phiên bản hệ điều hành không hỗ trợ");
                break;
        }
    }

    private void setUI(String status, String bar, String tabs, String tabu, String bg) {
        if (!status.equals("")) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Integer.parseInt(status));
        if (!bar.equals("")) {
            toolbar.setBackgroundColor(Integer.parseInt(bar));
            colorApp = Integer.parseInt(bar);
        }
        if (!tabu.equals("")) tab_unselecter.setColor(Integer.parseInt(tabu));
        if (!tabs.equals("")) tab_selecter.setColor(Integer.parseInt(tabs));
        if (!bg.equals("")) frameLayout.setBackgroundColor(Integer.parseInt(bg));
    }

    private void loadColorTab() {
        try {
            for (int i = 0; i < tabUI.getTabCount(); i++) {
                if (tabUI.getTabAt(i).isSelected())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        tabUI.getTabAt(i).getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_select));
                    else Communication.showToastCenter(this, "Phiên bản hệ điều hành không hỗ trợ");
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    tabUI.getTabAt(i).getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_unselect));
                else Communication.showToastCenter(this, "Phiên bản hệ điều hành không hỗ trợ");
            }
            for (int i = 0; i < fmDanhNgon.getTabLayout().getTabCount(); i++) {
                if (fmDanhNgon.getTabLayout().getTabAt(i).isSelected())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        fmDanhNgon.getTabLayout().getTabAt(i).getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_select));
                    else Communication.showToastCenter(this, "Phiên bản hệ điều hành không hỗ trợ");
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    fmDanhNgon.getTabLayout().getTabAt(i).getCustomView().setBackground(getResources().getDrawable(R.drawable.tab_unselect));
                else Communication.showToastCenter(this, "Phiên bản hệ điều hành không hỗ trợ");

            }
        } catch (NullPointerException e) {
        }
    }

    /**
     * lưu trạng thái khi mk xoay màn hình
     * put dữ liệu cần lưu vào savedInstanceState
     *
     * @param savedInstanceState dùng để lưu trữ dữ liệu
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putSerializable(DANH_NGON, getDanhNgons());
        savedInstanceState.putSerializable(CATE_GORY, getCategories());
        savedInstanceState.putSerializable(IMAGE, getImages());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public ArrayList<String> getImages() {
        return images;
    }

    /*
        lấy một image bất kì tỏng mảng
         */
    public String getRandomImage() {
        return images.get((new Random()).nextInt(images.size()));
    }
}
