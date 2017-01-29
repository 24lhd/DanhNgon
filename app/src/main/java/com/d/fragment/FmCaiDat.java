package com.d.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.activity.MainActivity;
import com.d.danhngon.R;

/**
 * Created by d on 29/01/2017.
 */

public class FmCaiDat extends Fragment{
    private MainActivity mainActivity;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        this.inflater=inflater;
        mainActivity= (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.layout_cai_dat, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorFrey));
        return rootView;
    }

}
