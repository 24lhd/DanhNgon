package duong.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by d on 17/01/2017.
 */

public abstract class AdaptorResycleViewADS extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView recyclerView;
    private int itemA;
    private int itemB;
    private List<Object> listObject;
    private Object doiTuongCanThem;
    private static final int A=1;
    private static final int B=0;
    private int viTriThem=10;

//    public static void addNativeExpressAds(ArrayList<Object> objects,
//                                    Object doiTuongCanThem,
//                                    int viTriAdd) {
//        for (int i = viTriAdd; i <= objects.size(); i += viTriAdd) {
//            objects.add(i, doiTuongCanThem);
//        }
//    }

    public AdaptorResycleViewADS(RecyclerView recyclerView, int itemA, int itemB, List<Object> listObject, Object doiTuongCanThem, int viTriThem) {
        this.recyclerView = recyclerView;
        this.itemA = itemA;
        this.itemB = itemB;
        this.listObject = listObject;
        this.doiTuongCanThem = doiTuongCanThem;
        this.viTriThem = viTriThem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case B:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemB, parent, false);
                return new ViewHolderB(nativeExpressLayoutView);
            default:
            case A:
                View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemA, parent, false);
                return new ViewHolderA(menuItemLayoutView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position % viTriThem == 0&&position>0) ? B : A;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case B:
                ViewHolderB viewHolderB = (ViewHolderB) holder;
                setViewHolderB(viewHolderB);
                return;
            default:
            case A:
                ViewHolderA viewHolderA = (ViewHolderA) holder;
                setViewHolderA(viewHolderA);
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
    public abstract void setViewHolderB(ViewHolderB viewHolderB);
    public abstract void setViewHolderA(ViewHolderA viewHolderA);
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
