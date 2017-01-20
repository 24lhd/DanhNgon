package com.d.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.danhngon.R;
import com.d.object.DanhNgon;

import java.util.List;

import duong.adaptor.AdaptorResycleViewADS;

/**
 * Created by d on 20/01/2017.
 */

public class AdaptorDanhNgon extends AdaptorResycleViewADS {

    private List<Object> listObject;
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
                           int viTriThem ) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem);
        this.listObject = listObject;
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
        DanhNgon danhNgon= (DanhNgon) getListObject().get(position);
        danhNgonHover.stt.setText(""+(listObject.indexOf(danhNgon)+1));
        danhNgonHover.content.setText(danhNgon.getContent());
        danhNgonHover.author.setText("~ "+danhNgon.getAuthor()+" ~");

    }

}
