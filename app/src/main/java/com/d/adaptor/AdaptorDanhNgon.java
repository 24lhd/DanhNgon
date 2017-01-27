package com.d.adaptor;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d.activity.MainActivity;
import com.d.danhngon.R;
import com.d.object.DanhNgon;

import java.util.List;
import java.util.Random;

import duong.DiaLogThongBao;
import duong.ScreenShort;
import duong.adaptor.AdaptorResycleViewADS;

/**
 * Created by d on 20/01/2017.
 */

public class AdaptorDanhNgon extends AdaptorResycleViewADS {

    private  MainActivity activity;
    private List<Object> listObject;
    private WebView webView;
    private AlertDialog alertDialog;
    private View view;
    private Random random;

    class DanhNgonHover extends ViewHolderA{
        TextView stt,content,author;

        public DanhNgonHover(View itemView) {
            super(itemView);
            this.stt= (TextView) itemView.findViewById(R.id.tv_stt);
            this.content= (TextView) itemView.findViewById(R.id.tv_content);
            this.author= (TextView) itemView.findViewById(R.id.tv_author);

        }
    }
    public AdaptorDanhNgon(RecyclerView recyclerView,
                           List<Object> listObject,
                           Object doiTuongCanThem,
                           int viTriThem, MainActivity activity) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem);
        this.listObject = listObject;
        this.activity = activity;
        random=new Random();

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case AdaptorResycleViewADS.B:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).
                        inflate(
                                R.layout.card_ads,
                        parent, false);
                return new ViewHolderB(nativeExpressLayoutView);
            default:
            case A:
                View menuItemLayouthView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.card_danh_ngon, parent, false);
                return new DanhNgonHover(menuItemLayouthView);
        }
    }
    @Override
    public void setViewHolderB(ViewHolderB viewHolder, int position) {

    }
    @Override
    public void setViewHolderA( ViewHolderA viewHolder, int position) {
        DanhNgonHover danhNgonHover= (DanhNgonHover) viewHolder;
         final DanhNgon danhNgon= (DanhNgon) getListObject().get(position);
        danhNgonHover.stt.setText(""+(listObject.indexOf(danhNgon)+1));
        danhNgonHover.content.setText(danhNgon.getContent());
        danhNgonHover.author.setText("~ "+danhNgon.getAuthor()+" ~");
        danhNgonHover.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater=LayoutInflater.from(activity);
                view= (View) layoutInflater.inflate(R.layout.view_danh_ngon,null);
                TextView tvContent= (TextView) view.findViewById(R.id.tv_content);
                TextView tvAuthor= (TextView) view.findViewById(R.id.tv_author);
                ImageView img= (ImageView) view.findViewById(R.id.im_bn_dn);
                tvContent.setText(danhNgon.getContent());
                tvAuthor.setText("~ "+danhNgon.getAuthor()+" ~");
                Glide.with(activity).load(draw[random.nextInt(draw.length-1)]).into(img);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                    Picasso.with(activity).load(draw[random.nextInt(draw.length-1)]).into(img);
                String favorite="Yêu thích";
                if (danhNgon.getFavorite().contains("1")) favorite="Bỏ thích";
                alertDialog=DiaLogThongBao.createDiaLogCustemView(activity, view,null, "Chia sẻ ảnh","Gim thẻ", favorite, activity.getColorApp(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScreenShort screenShort=new ScreenShort();
                        screenShort.shareImageByFile( screenShort.takeSreenShortByView(view,activity),activity);
                    }
                },new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (danhNgon.getFavorite().contains("1")) activity.getDuLieu().updateDanhNgonUnfarvorite(danhNgon);
                        else activity.getDuLieu().updateDanhNgonFarvorite(danhNgon);
                        notifyDataSetChanged();
                        alertDialog.dismiss();

                    }
                },new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });

    }



    public static int [] draw={
            R.drawable.a001,
            R.drawable.a002,
            R.drawable.a003,
            R.drawable.a004,
            R.drawable.a005,
            R.drawable.a006,
            R.drawable.a007,
            R.drawable.a008,
            R.drawable.a009,
            R.drawable.a010,
            R.drawable.a011,
            R.drawable.a012,
            R.drawable.a013,
            R.drawable.a014,
            R.drawable.a015,
            R.drawable.a016,
            R.drawable.a017,
            R.drawable.a018,
            R.drawable.a019,
            R.drawable.a020,
            R.drawable.a021,
            R.drawable.a022,
            R.drawable.a023,
            R.drawable.a024,
            R.drawable.a026,
            R.drawable.a027,
            R.drawable.a028,
            R.drawable.a029,
            R.drawable.a030,
            R.drawable.a031,
            R.drawable.a032,
            R.drawable.a033,
            R.drawable.a034,
            R.drawable.a034,
            R.drawable.a036,
            R.drawable.a037,
            R.drawable.a038,
            R.drawable.a039,
            R.drawable.a040,
            R.drawable.a041,
            R.drawable.a042,
            R.drawable.a043,
            R.drawable.a044,
            R.drawable.a045,
            R.drawable.a046,
            R.drawable.a047,
            R.drawable.a048,
            R.drawable.a049,
            R.drawable.a050,
            R.drawable.a051,
            R.drawable.a052,
            R.drawable.a053,
            R.drawable.a054,
            R.drawable.a055,
            R.drawable.a057,
            R.drawable.a057,
            R.drawable.a058,
            R.drawable.a059,
            R.drawable.a060,
            R.drawable.a061,
            R.drawable.a062,
            R.drawable.a063,
            R.drawable.a064,
            R.drawable.a065,
            R.drawable.a066,
            R.drawable.a067,
            R.drawable.a068,
            R.drawable.a069,
            R.drawable.a070,
            R.drawable.a071,
            R.drawable.a072,
            R.drawable.a073,
            R.drawable.a074,
            R.drawable.a075,
            R.drawable.a076,
            R.drawable.a077,
            R.drawable.a078,
            R.drawable.a079,
            R.drawable.a080,
            R.drawable.a081,
            R.drawable.a082,
            R.drawable.a083,
            R.drawable.a084,
            R.drawable.a085,
            R.drawable.a086,
            R.drawable.a087,
            R.drawable.a088,
            R.drawable.a089,
            R.drawable.a090,
            R.drawable.a091,
            R.drawable.a092,
            R.drawable.a093,
            R.drawable.a094,
            R.drawable.a095,
            R.drawable.a096,
            R.drawable.a097,
            R.drawable.a098,
            R.drawable.a099,
            R.drawable.a100,
            R.drawable.a101,
            R.drawable.a102,
            R.drawable.a103,
            R.drawable.a104,
            R.drawable.a105,
            R.drawable.a106,
            R.drawable.a107,
            R.drawable.a108,
            R.drawable.a109,
            R.drawable.a110,
            R.drawable.a111,
            R.drawable.a112,
            R.drawable.a113,
            R.drawable.a114,
            R.drawable.a115,
            R.drawable.a116,
            R.drawable.a117,
            R.drawable.a118,
            R.drawable.a119,
            R.drawable.a120,
            R.drawable.a121,
            R.drawable.a122,
            R.drawable.a123,
            R.drawable.a124,
            R.drawable.a125,
            R.drawable.a127,
            R.drawable.a128,
            R.drawable.a129,
            R.drawable.a111,
            R.drawable.a112,
            R.drawable.a113,
            R.drawable.a114,
            R.drawable.a115,
            R.drawable.a116,
            R.drawable.a117,
            R.drawable.a118,
            R.drawable.a119,
            R.drawable.a120,
            R.drawable.a121,
            R.drawable.a122,
            R.drawable.a123,
            R.drawable.a124,
            R.drawable.a125,
            R.drawable.a127,
            R.drawable.a128,
            R.drawable.a129,
            R.drawable.a130,
            R.drawable.a131,
            R.drawable.a132,
            R.drawable.a133,
            R.drawable.a134,
            R.drawable.a135,
            R.drawable.a136,
            R.drawable.a137,
            R.drawable.a138,
            R.drawable.a139,
            R.drawable.a140,
            R.drawable.a141,
            R.drawable.a142,
            R.drawable.a143,
            R.drawable.a144,
            R.drawable.a145,
            R.drawable.a146,
            R.drawable.a147,
            R.drawable.a148,
            R.drawable.a149,
            R.drawable.a150,
            R.drawable.a151,
            R.drawable.a152,
            R.drawable.a153,
            R.drawable.a154,
            R.drawable.a155,
            R.drawable.a156,
            R.drawable.a157,
            R.drawable.a158,
    };
}
