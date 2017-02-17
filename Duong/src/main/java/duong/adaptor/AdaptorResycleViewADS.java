package duong.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import duong.Conections;

/**
 * Created by d on 17/01/2017.
 *
 * ý tưởng đặt quảng cáo khi offline sẽ ẩn là k cần v\dùng for đế add chỉ cần bắt online thì return getItemViewType vào đúng vị trí cố định
 * khi off line thì k return
 */

public abstract class AdaptorResycleViewADS extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  Context context;
    private RecyclerView recyclerView;
    private List<Object> listObject;
    private Object doiTuongCanThem;
    public static final int A=1;
    public static final int B=0;
    private int viTriThem=6;
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public List<Object> getListObject() {
        return listObject;
    }
    public Object getDoiTuongCanThem() {
        return doiTuongCanThem;
    }
    public static int getA() {
        return A;
    }
    public static int getB() {
        return B;
    }
        public static void addNativeExpressAds(ArrayList<Object> objects,
                                    Object doiTuongCanThem,
                                    int viTriAdd) {
        for (int i = viTriAdd; i <= objects.size(); i += viTriAdd) {
            objects.add(i, doiTuongCanThem);
        }
    }
    public AdaptorResycleViewADS(RecyclerView recyclerView , List<Object> listObject, Object doiTuongCanThem, int viTriThem, Context context) {
        this.recyclerView = recyclerView;
        this.listObject = listObject;
        this.doiTuongCanThem = doiTuongCanThem;
        this.viTriThem = viTriThem;
        this.context = context;
//        if (Conections.isOnline(context)){
//            for (int i = viTriThem; i <= listObject.size(); i += viTriThem)
//                listObject.add(i, doiTuongCanThem);
//        }


    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    @Override
    public int getItemViewType(int position) {
        return (position % viTriThem == 0&&position>0&& Conections.isOnline(context)) ? B : A;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case B:
                ViewHolderB viewHolderB= (ViewHolderB) holder;
                setViewHolderB(viewHolderB,position);
                return;
            default:
            case A:
                ViewHolderA viewHolderA= (ViewHolderA) holder;
                setViewHolderA(viewHolderA,position);
                return;
        }
    }


    @Override
    public int getItemCount() {
        return listObject.size();
    }


    public int getViTriThem() {
        return viTriThem;
    }

    public void setViTriThem(int viTriThem) {
        this.viTriThem = viTriThem;
    }
    public abstract void setViewHolderB(ViewHolderB viewHolder, int position);
    public abstract void setViewHolderA(ViewHolderA viewHolder, int position);
    public class ViewHolderA extends RecyclerView.ViewHolder{
        public ViewHolderA(View itemView) {
            super(itemView);
        }
    }
    public class ViewHolderB extends RecyclerView.ViewHolder{
        public ViewHolderB(View itemView) {
            super(itemView);
        }
    }
}
