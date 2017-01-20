package com.d.adaptor;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.d.danhngon.R;
import com.d.object.DanhNgon;

import java.util.List;

import duong.DiaLogThongBao;
import duong.ScreenShort;
import duong.adaptor.AdaptorResycleViewADS;

/**
 * Created by d on 20/01/2017.
 */

public class AdaptorDanhNgon extends AdaptorResycleViewADS {

    private  FragmentActivity activity;
    private List<Object> listObject;
    private WebView webView;

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
                           int viTriThem, FragmentActivity activity) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem);
        this.listObject = listObject;
        this.activity = activity;

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
    public void setViewHolderA(ViewHolderA viewHolder, int position) {
        DanhNgonHover danhNgonHover= (DanhNgonHover) viewHolder;
        final DanhNgon danhNgon= (DanhNgon) getListObject().get(position);
        danhNgonHover.stt.setText(""+(listObject.indexOf(danhNgon)+1));
        danhNgonHover.content.setText(danhNgon.getContent());
        danhNgonHover.author.setText("~ "+danhNgon.getAuthor()+" ~");
        danhNgonHover.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str="<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<meta charset=\"utf-8\">" +
                        "<style type=\"text/css\">" +
                        "  #author{" +
                        "    text-align: right;" +
                        "    color: blue;" +
                        "    margin-right: 10px;" +
                        "  }" +
                        "  #content{" +
                        "    " +
                        "    margin:0;" +
                        "    padding: 0;" +
                        "  }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<table>" +
                        "  <tr>" +
                        "<h3 id=\"content\">" +
                        danhNgon.getContent() +
                        "</h3>" +
                        "<p id=\"author\">~ " +
                        danhNgon.getAuthor() +
                        " ~</p>" +
                        "  </tr>" +
                        "</table>" +
                        " </body>" +
                        "</html>";
                webView=new WebView(activity);
                webView.loadDataWithBaseURL(null, str, "text/html", "utf-8",null);
                DiaLogThongBao.createDiaLogCustemView(activity, webView,null, "Chia sẻ", "Yêu thích", activity.getResources().getColor(R.color.colorAccent), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScreenShort screenShort=new ScreenShort();
                        screenShort.shareImageByFile( screenShort.takeSreenShortByView(webView,activity),activity);
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
