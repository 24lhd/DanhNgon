package com.d.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.d.activity.MainActivity;
import com.lhd.danhngon.R;

import duong.AppLog;

/**
 * Created by d on 29/01/2017.
 */

public class FmCaiDat extends Fragment{
    private MainActivity mainActivity;
    private View rootView;
    private Switch mSwitchNofity;
    private Switch mSwitchWindow;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity= (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.layout_cai_dat, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorFrey));
        mSwitchNofity= (Switch) rootView.findViewById(R.id.sw_notify);
        mSwitchWindow= (Switch) rootView.findViewById(R.id.sw_window);
        
        mSwitchWindow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) (new AppLog()).putValueByName(getActivity(),"sw_op","sw_window","1");
                else (new AppLog()).putValueByName(getActivity(),"sw_op","sw_window","0");
            }
        });
        mSwitchNofity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) (new AppLog()).putValueByName(getActivity(),"sw_op","sw_notify","1");
                else (new AppLog()).putValueByName(getActivity(),"sw_op","sw_notify","0");
            }
        });
        if ((new AppLog()).getValueByName(getActivity(),"sw_op","sw_window").contains("1")) mSwitchWindow.setChecked(true);
        if ((new AppLog()).getValueByName(getActivity(),"sw_op","sw_notify").contains("1")) mSwitchNofity.setChecked(true);
        return rootView;
    }

}
