package duong.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 17/01/2017.
 */

public abstract class AdaptorResycleViewADS extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView recyclerView;
    private List<Object> listObject;
    private Object doiTuongCanThem;
    public static final int A=1;
    public static final int B=0;
    private int viTriThem=10;
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

    public AdaptorResycleViewADS(RecyclerView recyclerView , List<Object> listObject, Object doiTuongCanThem, int viTriThem) {
        this.recyclerView = recyclerView;
        this.listObject = listObject;
        this.doiTuongCanThem = doiTuongCanThem;
        this.viTriThem = viTriThem;
        for (int i = viTriThem; i <= listObject.size(); i += viTriThem) {
            listObject.add(i, doiTuongCanThem);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
//    {
//        switch (viewType) {
//            case B:
//                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemB, parent, false);
//                return new ViewHolderB(nativeExpressLayoutView);
//            default:
//            case A:
//                View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemA, parent, false);
//                return new ViewHolderA(menuItemLayoutView);
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        return (position % viTriThem == 0&&position>0) ? B : A;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
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
