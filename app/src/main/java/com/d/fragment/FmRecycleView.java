package com.d.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.activity.MainActivity;
import com.d.adaptor.AdaptorDanhNgon;
import com.d.danhngon.R;
import com.d.object.Category;
import com.d.object.DanhNgon;

import java.util.ArrayList;

/**
 * Created by d on 20/01/2017.
 */

public class FmRecycleView extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorFrey));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Category category = (Category) getArguments().getSerializable(FmDanhNgon.ARG_SECTION_NUMBER);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll((ArrayList<DanhNgon>) getArguments().getSerializable(MainActivity.LIST_DATA));
        recyclerView.setAdapter(new AdaptorDanhNgon(recyclerView, objects, category, 10, (MainActivity) getActivity()));
        return recyclerView;
    }
}