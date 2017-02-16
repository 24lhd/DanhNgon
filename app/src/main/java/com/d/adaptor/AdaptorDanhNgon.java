package com.d.adaptor;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d.activity.MainActivity;
import com.d.object.DanhNgon;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.lhd.danhngon.R;

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
    private AlertDialog alertDialog;
    private View view;
    private Random random;
    class NativeExpressAdViewHolder extends ViewHolderB {
         NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_vua);
        }
    }
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
            case B:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.native_express_ad_vua, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case A:
                View menuItemLayouthView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.card_danh_ngon, parent, false);
                return new DanhNgonHover(menuItemLayouthView);
        }
    }
    @Override
    public void setViewHolderB(ViewHolderB viewHolder, int position) {
        NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) viewHolder;
        nativeExpressAdViewHolder.nativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }
    @Override
    public void setViewHolderA( ViewHolderA viewHolder, int position) {
        DanhNgonHover danhNgonHover= (DanhNgonHover) viewHolder;
         final DanhNgon danhNgon= (DanhNgon) getListObject().get(position);
        danhNgonHover.stt.setText(""+(listObject.indexOf(danhNgon)+1));
        danhNgonHover.content.setText(danhNgon.getContent());
        danhNgonHover.author.setText(danhNgon.getAuthor());
        danhNgonHover.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getAdsFull().showADSFull();
                LayoutInflater layoutInflater=LayoutInflater.from(activity);
                view= (View) layoutInflater.inflate(R.layout.view_danh_ngon,null);
                TextView tvContent= (TextView) view.findViewById(R.id.tv_content);
                TextView tvAuthor= (TextView) view.findViewById(R.id.tv_author);
                ImageView img= (ImageView) view.findViewById(R.id.im_bn_dn);
                tvContent.setText(danhNgon.getContent());
                tvAuthor.setTextColor(activity.getColorApp());
                tvAuthor.setText(danhNgon.getAuthor());
                Glide.with(activity).load(activity.getRandomImage()).into(img);
                String favorite="Yêu thích";
                if (danhNgon.getFavorite().contains("1")) favorite="Bỏ thích";
                alertDialog=DiaLogThongBao.createDiaLogCustemView(activity, view,null,
                        "Chia sẻ ảnh","Gim thẻ", favorite, activity.getColorApp(), new View.OnClickListener() {
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




}
