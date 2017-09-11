package com.d.adaptor;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d.activity.MainActivity;
import com.d.object.DanhNgon;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;
import com.lhd.danhngon.R;

import java.util.List;
import java.util.Random;

import duong.Communication;
import duong.Conections;
import duong.DiaLogThongBao;
import duong.ScreenShort;
import duong.adaptor.AdaptorResycleViewADS;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.NativeExpressAdView;

/**
 * Created by d on 20/01/2017.
 */

public class AdaptorDanhNgon extends AdaptorResycleViewADS {
    private MainActivity activity;
    private List<Object> listObject;
    private AlertDialog alertDialog;
    private View view;
    private Random random;


    class DanhNgonHover extends ViewHolderA {
        TextView content, author;
        ImageView imView;

        public DanhNgonHover(View itemView) {
            super(itemView);
//            this.stt = (TextView) itemView.findViewById(R.id.tv_stt);
            this.content = (TextView) itemView.findViewById(R.id.tv_content);
            this.author = (TextView) itemView.findViewById(R.id.tv_author);
            this.imView = (ImageView) itemView.findViewById(R.id.im_bn_dn);

        }
    }

    public AdaptorDanhNgon(RecyclerView recyclerView,
                           List<Object> listObject,
                           Object doiTuongCanThem,
                           int viTriThem, MainActivity activity) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem, activity);
        this.listObject = listObject;
        this.activity = activity;
        random = new Random();
    }


    class FBAds extends ViewHolderAds {
        NativeAd nativeAd;

        public FBAds(final View itemView) {
            super(itemView);
            loadAds();
        }

        private void loadAds() {
            nativeAd = new NativeAd(activity, activity.getResources().getString(R.string.facebook_ads_id_in_list_video));
            nativeAd.setAdListener(new AdListener() {

                @Override
                public void onError(Ad ad, AdError error) {
                    // Ad error callback
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    View adView = NativeAdView.render(activity, nativeAd, NativeAdView.Type.HEIGHT_300);
                    LinearLayout nativeAdContainer = (LinearLayout) itemView.findViewById(R.id.native_ad_container_in_list);
                    nativeAdContainer.addView(adView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            // Request an ad
            nativeAd.loadAd();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case B:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_ads_fb_in_list, parent, false);
                return new FBAds(nativeExpressLayoutView);//new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case A:
                View menuItemLayouthView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.card_danh_ngon, parent, false);
                return new DanhNgonHover(menuItemLayouthView);
        }
    }

    @Override
    public void ViewHolderAds(AdaptorResycleViewADS.ViewHolderAds viewHolder, int position) {

    }


    @Override
    public void setViewHolderA(ViewHolderA viewHolder, int position) {
        DanhNgonHover danhNgonHover = (DanhNgonHover) viewHolder;
        final DanhNgon danhNgon = (DanhNgon) getListObject().get(position);
        String linkImg = activity.getRandomImage();
//        danhNgonHover.stt.setText("" + (listObject.indexOf(danhNgon) + 1));
        danhNgonHover.content.setText(danhNgon.getContent());
        danhNgonHover.author.setText(danhNgon.getAuthor());
        if (Conections.isOnline(activity))
            Glide.with(activity).load(linkImg).into(danhNgonHover.imView);
        else Glide.with(activity).load(R.drawable.a151).into(danhNgonHover.imView);
//        Glide.with(activity).load(activity.getImages().get(random.nextInt(activity.getImages().size()))).into(danhNgonHover.imView);
        danhNgonHover.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.getAdsFull().showADSFull();
                LayoutInflater layoutInflater = LayoutInflater.from(activity);
                view = (View) layoutInflater.inflate(R.layout.view_danh_ngon, null);
                TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
                TextView tvAuthor = (TextView) view.findViewById(R.id.tv_author);
                ImageView img = (ImageView) view.findViewById(R.id.im_bn_dn);
                tvContent.setText(danhNgon.getContent());
                tvAuthor.setTextColor(activity.getColorApp());
                tvAuthor.setText(danhNgon.getAuthor());
                if (Conections.isOnline(activity))
                    Glide.with(activity).load(activity.getRandomImage()).into(img);
                else {
                    Glide.with(activity).load(R.drawable.a151).into(img);
                    Communication.showToast(activity, activity.getResources().getString(R.string.toast_offline));
                }
                String favorite = "Yêu thích";
                if (danhNgon.getFavorite().contains("1")) favorite = "Bỏ thích";
                alertDialog = DiaLogThongBao.createDiaLogCustemView(activity, view, null,
                        "Chia sẻ ảnh", "Gim thẻ", favorite, activity.getColorApp(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ScreenShort screenShort = new ScreenShort();
                                screenShort.shareImageByFile(screenShort.takeSreenShortByView(view, activity), activity);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (danhNgon.getFavorite().contains("1"))
                                    activity.getDuLieu().updateDanhNgonUnfarvorite(danhNgon);
                                else activity.getDuLieu().updateDanhNgonFarvorite(danhNgon);
                                notifyDataSetChanged();
                                alertDialog.dismiss();
//                                activity.getAdsFull().showADSFull();

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

            }
        });

    }


}
